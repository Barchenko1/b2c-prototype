// ▶ Paste and run on inpol. Uses no window/global variables.

const dateArray = ['2025-11-01', '2025-11-02', '2025-11-03', '2025-11-04',
    '2025-11-05', '2025-11-06', '2025-11-07', '2025-11-08', '2025-11-09', '2025-11-10', '2025-11-11',
    '2025-11-12', '2025-11-13', '2025-11-14', '2025-11-15', '2025-11-16', '2025-11-17', '2025-11-18',
    '2025-11-19', '2025-11-20', '2025-11-21', '2025-11-22', '2025-11-23', '2025-11-24', '2025-11-25',
    '2025-11-26', '2025-11-27', '2025-11-28', '2025-11-29', '2025-11-30', '2025-12-01', '2025-12-02',
    '2025-12-03', '2025-12-04', '2025-12-05', '2025-12-06', '2025-12-07', '2025-12-08', '2025-12-09',
    '2025-12-10', '2025-12-11', '2025-12-12'
].reverse();

(async () => {
    // Marszałkowska 3/5 : 3ab99932-8e53-4dff-9abf-45b8c6286a99"
    // Bankowy 3/5 : f0992a78-802d-40e7-9bd0-c0d8d46a71fd
    // Al. Jerozolimskie 28 : c93674d6-fb24-4a85-9dac-61897dc8f060

    const queueId = "3ab99932-8e53-4dff-9abf-45b8c6286a99";

    // === Paste a fresh, live JWT ===
    const bearerToken = "PASTE_FRESH_JWT_HERE";

    // === Reservation payload ===
    const reservationPayloadBase = {
        proceedingId: "c5484b74-e837-4495-b44b-9866a17876bb",
        name: "Sofiia",
        lastName: "SENYK",
        dateOfBirth: "2002-07-05T00:00:00Z"
    };

    const inpolOrigin = "https://inpol.mazowieckie.pl";
    if (location.origin !== inpolOrigin) {
        console.warn("Open the case page on inpol.mazowieckie.pl and run again.");
        return;
    }
    if (!bearerToken || /null|undefined|^\s*$/i.test(bearerToken)) {
        console.error("No JWT provided. Set 'bearerToken' to a valid token.");
        return;
    }

    const sleep = ms => new Promise(r => setTimeout(r, ms));
    const PAUSE_MS = 500;

    const trimIsoDate = s => (typeof s === "string" ? s.split("T")[0] : s);

    // --- NEW: fetch dates from API as a second option ---
    async function fetchDatesFromAPI() {
        const url = `${inpolOrigin}/api/reservations/queue/${queueId}/dates`;
        const res = await fetch(url, {
            method: "POST",
            mode: "cors",
            credentials: "include",
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/json",
                "Authorization": `Bearer ${bearerToken}`,
                "recaptchaactionname": "reservations_queue_dates"
            },
            body: null
        });
        const txt = await res.text();
        let data = null; try { data = txt ? JSON.parse(txt) : null; } catch {}
        if (!Array.isArray(data)) return [];
        // strings or objects with a date-like field
        const list = data.map(x => {
            if (typeof x === "string") return trimIsoDate(x);
            if (x && typeof x === "object") {
                const d = x.date ?? x.day ?? x.slotDate ?? x.slotdate ?? x.Date ?? x.Day;
                return d ? trimIsoDate(d) : null;
            }
            return null;
        }).filter(Boolean);
        return [...new Set(list)]; // unique
    }

    // --- NEW: local fallback date generator (next N days) ---
    function generateNextDays(n = 42) {
        const out = [];
        const now = new Date();
        for (let i = 0; i < n; i++) {
            const d = new Date(now);
            d.setDate(d.getDate() + i);
            const yyyy = d.getFullYear();
            const mm = String(d.getMonth() + 1).padStart(2, "0");
            const dd = String(d.getDate()).padStart(2, "0");
            out.push(`${yyyy}-${mm}-${dd}`);
        }
        return out;
    }

    async function getDatesIfNeeded() {
        if (Array.isArray(window.array) && window.array.length) {
            return window.array.map(trimIsoDate);
        }
        // const fromApi = await fetchDatesFromAPI();
        const fromApi = dateArray;
        if (fromApi.length) return fromApi;
        console.warn("No dates from window.array or API; using local fallback (next 60 days).");
        return generateNextDays(60);
    }

    async function fetchSlotsForDate(dateStr) {
        const url = `${inpolOrigin}/api/reservations/queue/${queueId}/${dateStr}/slots`;
        const res = await fetch(url, {
            method: "POST",
            mode: "cors",
            credentials: "include",
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/json",
                "Authorization": `Bearer ${bearerToken}`,
                "recaptchaactionname": "reservations_queue_slots"
            },
            body: null
        });
        const raw = await res.text();
        let parsed = null; try { parsed = raw ? JSON.parse(raw) : null; } catch {}
        return { date: dateStr, status: res.status, ok: res.ok, body: parsed ?? raw };
    }

    async function reserveSlot(slotId) {
        const url = `${inpolOrigin}/api/reservations/queue/${queueId}/reserve`;
        const payload = { ...reservationPayloadBase, slotId };
        const res = await fetch(url, {
            method: "POST",
            mode: "cors",
            credentials: "include",
            headers: {
                "Accept": "application/json, text/plain, */*",
                "Content-Type": "application/json",
                "Authorization": `Bearer ${bearerToken}`,
                "recaptchaactionname": "reservations_make_appointment"
            },
            body: JSON.stringify(payload)
        });
        const txt = await res.text();
        let data = null; try { data = txt ? JSON.parse(txt) : null; } catch {}
        return { status: res.status, ok: res.ok, body: data ?? txt };
    }

    function getSlotId(slot) {
        if (slot == null) return null;
        if (typeof slot === "number") return slot;
        if (typeof slot === "string" && /^\d+$/.test(slot)) return Number(slot);
        if (typeof slot === "object") return slot.slotId ?? slot.id ?? slot.slotID ?? slot.SlotId ?? slot.SlotID ?? null;
        return null;
    }

    function pickSlot(slots) {
        if (!Array.isArray(slots) || !slots.length) return null;
        const copy = slots.slice();
        copy.sort((a, b) => {
            const at = a.time ?? a.start ?? a.startTime ?? a.begin ?? a.slotTime ?? a.Time ?? null;
            const bt = b.time ?? b.start ?? b.startTime ?? b.begin ?? b.slotTime ?? b.Time ?? null;
            if (!at || !bt) return 0;
            return String(at).localeCompare(String(bt));
        });
        return copy[0];
    }

    async function tryReserveFromSlots(slots, date) {
        const chosen = pickSlot(slots) ?? slots[0];
        const slotId = getSlotId(chosen);
        if (!slotId) {
            console.warn("Found slots, but no slotId field. Example:", chosen);
            return { reserved: null, continue: true };
        }
        console.log(`Attempting reservation for slotId=${slotId} on ${date}...`);
        const reserveRes = await reserveSlot(slotId);
        console.log("Reserve response:", reserveRes.status, reserveRes.body);

        if (reserveRes.ok) return { reserved: { date, slotId, response: reserveRes }, continue: false };
        if (reserveRes.status === 401) { console.error("401 on reserve: JWT invalid/expired."); return { reserved: null, continue: false }; }
        console.warn("Reservation failed. Continuing sweep...", reserveRes);
        return { reserved: null, continue: true };
    }

    // ==== MAIN ====
    const dates = await getDatesIfNeeded();
    if (!dates.length) {
        console.warn("No dates available after all strategies.");
        return;
    }

    async function checkAndReserveForDate(dateStr, results, datesWithErrors) {
        const r = await fetchSlotsForDate(dateStr);
        results.push(r);
        console.log(`[${dateStr}] -> ${r.status}`, r.body);

        if (r.status === 401) {
            console.error("401 Unauthorized: JWT missing/expired.");
            return { reserved: null, stop: true };
        }
        if (r.status !== 200) {
            reCallWithErrors.push(dateStr);
        }

        if (Array.isArray(r.body) && r.body.length) {
            const out = await tryReserveFromSlots(r.body, dateStr);
            if (out.reserved) return { reserved: out.reserved, stop: true };
        }

        await sleep(PAUSE_MS);
        return { reserved: null, stop: false };
    }

    const results = [];
    let reserved = null;
    const datesWithErrors = [];

    // Pass 1
    for (const date of dates) {
        const res = await checkAndReserveForDate(date, results, datesWithErrors);
        if (res.stop) { reserved = res.reserved; break; }
    }

    // Pass 2: your 428 logic
    for (const date of datesWithErrors) {
        if (date.status === 428) {
            console.log("before recall");
            const res = await checkAndReserveForDate(date, results, datesWithErrors);
            if (res.stop) { reserved = res.reserved; break; }
        }
    }

    console.log("Finished. Summary:");
    console.table(results.map(r => ({
        date: r.date,
        status: r.status,
        ok: r.ok,
        slotsInfo: Array.isArray(r.body) ? `slots: ${r.body.length}` :
            (r.body && typeof r.body === "object" ? "object" : String(r.body || ""))
    })));

    if (reserved) {
        console.log("Reservation SUCCESS:", reserved);
    } else {
        console.log("No reservation made.");
    }

    window.slotsResults = results;
    window.reservation = reserved;
})();


