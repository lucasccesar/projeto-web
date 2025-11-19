const token = localStorage.getItem("token");
if (token) {
    window.location.href = "./home.html";
}

const API_URL = "http://localhost:8080/api/auth/login";

const form = document.querySelector("form");
const emailInput = document.getElementById("inputEmail");
const passwordInput = document.querySelector('input[type="password"]');

form.addEventListener("submit", async (e) => {
    e.preventDefault();

    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();

    if (!email || !password) {
        alert("Please fill in both email and password.");
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: "POST",
            headers: {
                "Content-Type": "application/json"
            },
            body: JSON.stringify({
                email: email,
                password: password
            })
        });

        if (!response.ok) {
            alert("Incorrect email or password.");
            return;
        }

        const data = await response.json();

        if (data.token) {
            localStorage.setItem("token", data.token);
        }

        alert("Login successful!");

        window.location.href = "./home.html";

    } catch (error) {
        console.error("Error:", error);
        alert("Failed to connect to the server.");
    }
});
