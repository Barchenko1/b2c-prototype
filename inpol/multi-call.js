const dateArray = [
    '2025-11-10', '2025-11-11', '2025-11-12', '2025-11-13', '2025-11-14',
    '2025-11-17', '2025-11-18', '2025-11-19', '2025-11-20', '2025-11-21',
    '2025-11-24', '2025-11-25', '2025-11-26', '2025-11-27', '2025-11-28',
    '2025-12-01', '2025-12-02', '2025-12-03', '2025-12-04', '2025-12-05',
    '2025-12-08', '2025-12-09', '2025-12-10', '2025-12-11', '2025-12-12',
    '2025-12-15', '2025-12-16', '2025-12-17', '2025-12-18', '2025-12-19',
    '2025-12-22',
].reverse();

// const dateArray = [
//     '2025-12-16', '2025-12-15', '2025-11-23', '2025-11-17', '2025-11-20','2025-11-21',
// ];

const inpolOrigin = "https://inpol.mazowieckie.pl";

let bearerToken = "PASTE_FRESH_JWT_HERE";
const ITERATIONS = 100;
let PAUSE_MS = 5000;
const sleep = (ms = PAUSE_MS) => new Promise(r => setTimeout(r, ms));
const trimIsoDate = s => (typeof s === "string" ? s.split("T")[0] : s);

// Marszałkowska 3/5 : 3ab99932-8e53-4dff-9abf-45b8c6286a99"
// Bankowy 3/5 : f0992a78-802d-40e7-9bd0-c0d8d46a71fd
// Al. Jerozolimskie 28 : c93674d6-fb24-4a85-9dac-61897dc8f060

const queues = [
    '3ab99932-8e53-4dff-9abf-45b8c6286a99',
    'f0992a78-802d-40e7-9bd0-c0d8d46a71fd',
    'c93674d6-fb24-4a85-9dac-61897dc8f060'
]

const reservationPayloadBase = {
    proceedingId: "c5484b74-e837-4495-b44b-9866a17876bb",
    name: "Sofiia",
    lastName: "SENYK",
    dateOfBirth: "2002-07-05T00:00:00Z"
};

function fetchWithTimeout(input, init = {}, timeoutMs) {
    const controller = new AbortController();
    const id = setTimeout(() => controller.abort(), timeoutMs);
    return fetch(input, { ...init, signal: controller.signal })
        .finally(() => clearTimeout(id));
}

async function login() {
    const url = inpolOrigin + "/identity/sign-in";
    const payload = {
        email: "s.senykk@gmail.com",
        password: "R$adrP!sTLJ8SM-",
        expiryMinutes: 0
    };

    try {
        const res = await fetch(url, {
            method: "POST",
            credentials: "include",
            headers: {
                "accept": "application/json, text/plain, */*",
                "content-type": "application/json",
                "recaptchaactionname": "sign_in"
            },
            body: JSON.stringify(payload)
        });

        const text = await res.text();
        let json; try { json = text ? JSON.parse(text) : null; } catch {}
        const headers = Object.fromEntries(res.headers.entries());
        const token = json.token;

        // return a structured response
        return {
            status: res.status,
            ok: res.ok,
            headers,
            body: json ?? text ?? null,
            token
        };
    } catch (e) {
        return { error: String(e) };
    }
}

async function cpChallengeVerify() {
    const url = inpolOrigin + "/_sec/cp_challenge/verify";
    const res = await fetch(url, {
        method: "GET",
        credentials: "include",
        headers: {
            "accept": "*/*",
            "cache-control": "no-cache",
            "pragma": "no-cache",
        }
    });

    const text = await res.text().catch(() => "");
    let json; try { json = text && JSON.parse(text); } catch {}
    const headers = Object.fromEntries(res.headers.entries());

    return {
        url,
        status: res.status,
        ok: res.ok,
        headers,
        body: json ?? text ?? null
    };
}

async function cpVerifyWithRetry(argument) {
    let attempt = 0;
    for (let i = 0; i < argument.retries; i++) {
        const r = await cpChallengeVerify();
        console.log(`[cp_challenge] attempt ${attempt} -> ${r.status}`, r.ok ? "OK" : "BLOCKED");
        if (r.ok || attempt >= r.retries) return r;
        await new Promise(r => setTimeout(r, argument.backoffMs * (attempt + 1)));
        attempt++;
    }
}

async function fetchDatesFromAPI(bearerToken, queueId) {
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

function generateNextDays(n) {
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

async function getDatesIfNeeded(bearerToken, queueId) {
    // let fromApi = await fetchDatesFromAPI(bearerToken, queueId);
    // fromApi = fromApi.map(trimIsoDate);
    const fromApi = dateArray;
    if (fromApi.length) return fromApi;
    console.warn("No dates from window.array or API; using local fallback: ", fromApi.length);
    return generateNextDays(fromApi.length);
}

async function fetchSlotsForDate(bearerToken, queueId, dateFormated) {
    const url = `${inpolOrigin}/api/reservations/queue/${queueId}/${dateFormated}/slots`;
    const res = await fetch(url, {
        method: "POST",
        mode: "cors",
        credentials: "include",
        headers: {
            "Accept": "application/json, text/plain, */*",
            "Content-Type": "application/json",
            "Authorization": `Bearer ${bearerToken}`,
            "recaptchaactionname": "reservations_queue_slots",
            "priority": "u=9, i"
        },
        body: null
    });
    const raw = await res.text();
    let parsed = null; try { parsed = raw ? JSON.parse(raw) : null; } catch {}
    return { date: dateFormated, status: res.status, ok: res.ok, body: parsed ?? raw };
}

function logResult(results, countQueues) {
    results.forEach((r, i) => {
        const date = countQueues[i];
        if (r.status === "fulfilled") {
            console.log(`[${date}] OK`, r.value);
        } else {
            console.warn(`[${date}] FAIL`, r.reason);
        }
    });
}

async function reserveSlot(queueId, slotId) {
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

async function tryReserveFromSlots(queueId, slots, date) {
    const chosen = pickSlot(slots) ?? slots[0];
    const slotId = getSlotId(chosen);
    if (!slotId) {
        console.warn("Found slots, but no slotId field. Example:", chosen);
        return { reserved: null, continue: true };
    }
    const reserveRes = await reserveSlot(queueId, slotId);
    console.log("Reserve response:", reserveRes.status, reserveRes.body);

    if (reserveRes.ok) return { reserved: { date, slotId, response: reserveRes }, stop: true };
    console.warn("Reservation failed. Continuing sweep...", reserveRes);
    return { reserved: null, stop: false };
}

const chunk = (arr, size) => Array.from({length: Math.ceil(arr.length / size)}, (_, i) =>
    arr.slice(i * size, i * size + size)
);

async function fetchSlotsForDateAndReserve(bearerToken, queueId, date) {
    const result = await fetchSlotsForDate(bearerToken, queueId, date);
    if (result.status === 200 && result.body) {
        console.log(`[OK]  ${queueId} @ ${date}`, result.body);
        if (result.body.length) {
            console.log("tryReserveFromSlots")
            const res = await tryReserveFromSlots(queueId, result.body, date);
            if (res.stop) {
                console.log("resolved slot", res.reserved);
                return { reserved: res.reserved };
            }
        }

    } else {
        console.warn(`[REJECT] ${queueId} @ ${date}`, result);
    }
}

async function runBatchQueues(batchItems) {
    const results = await Promise.allSettled(
        batchItems.map(({ queueId, date }) =>
            fetchSlotsForDateAndReserve(bearerToken, queueId, date)
        )
    );
    await sleep(PAUSE_MS);
}

async function runBatchDates(batchItems) {
    const results = await Promise.allSettled(
        batchItems.map(({ queueId, date }) =>
            fetchSlotsForDateAndReserve(bearerToken, queueId, date)
        )
    );
    await sleep(PAUSE_MS);
}

async function startScriptByQueues() {
    const response = await login();
    console.log("response:", response);
    if (response?.token) {
        console.log("token:", response.token);
    }
    bearerToken = response.token;

    const dates = dateArray;
    const SIZE = 2;
    const chunks = chunk(dates, SIZE);
    console.log(chunks);
    console.log(chunks.length);

    for (let i = 0; i < chunks.length; i++) {
        const batchItems = [];
        const chinkSlice = chunks[i];
        chinkSlice.forEach(date => batchItems.push({queueId: queues[0], date}));
        chinkSlice.forEach(date => batchItems.push({queueId: queues[1], date}));
        chinkSlice.forEach(date => batchItems.push({queueId: queues[2], date}));
        console.log("batchItems", batchItems);
        console.log("chinkSlice", chinkSlice);
        await runBatchQueues(batchItems);
        await sleep(PAUSE_MS);
    }
}

async function runScriptQueues() {
    for (let i = 0; i < ITERATIONS; i++) {
        await startScriptByQueues();
    }
}

async function startScriptByDates(queueId) {
    const response = await login();
    console.log("response:", response);
    if (response?.token) {
        console.log("token:", response.token);
    }
    bearerToken = response.token;

    const dates = dateArray;
    const SIZE = 1;
    const chunks = chunk(dates, SIZE);
    console.log(chunks);
    console.log(chunks.length);

    for (let i = 0; i < chunks.length; i++) {
        const batchItems = [];
        const chinkSlice = chunks[i];
        chinkSlice.forEach(date => batchItems.push({queueId, date}));
        console.log("batchItems", batchItems);
        console.log("chinkSlice", chinkSlice);
        await runBatchDates(batchItems);
        await sleep(PAUSE_MS);
    }
}

async function runScriptDates(queue) {
    for (let i = 0; i < ITERATIONS; i++) {
        await startScriptByDates(queues[0]);
    }
}


// === Install BEFORE any network happens ===
(function installHttpStatusEvents() {
    const bus = new EventTarget();
    const origFetch = window.fetch;
    const OrigXHR   = window.XMLHttpRequest;

    // flag to detect our own verify calls to avoid recursion
    let inSelfVerify = false;

    function onStatus(statusOrHandler, handler) {
        if (typeof statusOrHandler === 'number') {
            bus.addEventListener(`status:${statusOrHandler}`, handler);
        } else {
            bus.addEventListener('status', statusOrHandler);
        }
    }

    function dispatch(detail) {
        try {
            bus.dispatchEvent(new CustomEvent('status', { detail }));
            if (typeof detail.status === 'number') {
                bus.dispatchEvent(new CustomEvent(`status:${detail.status}`, { detail }));
            }
        } catch (e) {
            console.warn('dispatch error', e);
        }
    }

    // ---- FETCH ----
    window.fetch = async function(...args) {
        const res = await origFetch.apply(this, args);
        const clone = res.clone();
        const reqUrl = typeof args[0] === 'string' ? args[0] : (args[0]?.url ?? '');
        const detail = {
            kind: 'fetch',
            status: res.status,
            ok: res.ok,
            reqUrl,
            resUrl: res.url,
            response: clone,
            request: args
        };
        // mark events caused by our own verify call
        if (inSelfVerify && (detail.resUrl?.includes('/_sec/cp_challenge/verify') || detail.reqUrl?.includes('/_sec/cp_challenge/verify'))) {
            detail.selfVerify = true;
        }
        dispatch(detail);
        return res;
    };

    // ---- XHR ----
    window.XMLHttpRequest = function() {
        const xhr = new OrigXHR();
        const open = xhr.open;
        xhr.open = function(method, url, ...rest) {
            this._url = url;
            return open.call(this, method, url, ...rest);
        };
        xhr.addEventListener('load', function() {
            const detail = {
                kind: 'xhr',
                status: this.status,
                ok: (this.status >= 200 && this.status < 300),
                reqUrl: this._url,
                resUrl: this.responseURL,
                xhr: this
            };
            if (inSelfVerify && (detail.resUrl?.includes('/_sec/cp_challenge/verify') || detail.reqUrl?.includes('/_sec/cp_challenge/verify'))) {
                detail.selfVerify = true;
            }
            dispatch(detail);
        });
        return xhr;
    };

    // expose + a helper to wrap your own verify call
    window.HttpStatusEvents = {
        onStatus, bus,
        withSelfVerify: async fn => {
            try { inSelfVerify = true; return await fn(); }
            finally { inSelfVerify = false; }
        }
    };
})();

HttpStatusEvents.onStatus(401, async (e) => {
    const { reqUrl, resUrl } = e.detail;

    console.warn('401 detected on:', resUrl);

    // Run your verify flow (no captcha bypass, just a check+retry path)
    const response = await login();
    console.log("response:", response);
    if (response?.token) {
        console.log("token:", response.token);
    }
    bearerToken = response.token;
});

// 2) Register listener BEFORE any network work
HttpStatusEvents.onStatus(403, async (e) => {
    const { reqUrl, resUrl } = e.detail;

    // Avoid recursion: ignore the verify call itself
    if (!resUrl.includes('/_sec/cp_challenge/verify')) return;

    console.warn('403 detected on:', resUrl);
    try {
        const body = await e.detail.response.text();
        console.log('403 body:', body);
    } catch {}

    // Run your verify flow (no captcha bypass, just a check+retry path)
    const verifyResp = await cpVerifyWithRetry({ retries: 3, backoffMs: 1000 });
    console.log('VERIFY RESPONSE:', verifyResp?.status, verifyResp?.ok ? 'OK' : 'BLOCKED');
    // await runScriptDates();
});

(async () => {

    // await runScriptQueues();
    await runScriptDates(queues[2]);
})();
