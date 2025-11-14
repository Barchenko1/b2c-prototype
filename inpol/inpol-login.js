async function login() {
    const INPOL = "https://inpol.mazowieckie.pl";
    const url = INPOL + "/identity/sign-in";
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

(async () => {

    // ✅ Await the promise so you get the real response
    const response = await login();
    console.log("response:", response);

    // optional: surface token quickly
    if (response?.token) {
        console.log("token:");
        console.log(response.token);
    }
})();
