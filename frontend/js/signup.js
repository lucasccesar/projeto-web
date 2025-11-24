const API_URL = 'http://localhost:8080/api/auth/register';

const form = document.querySelector('form');
const nameInput = document.getElementById('name');
const emailInput = document.getElementById('inputEmail');
const passwordInput = document.getElementById('inputPassword');
const birthdayInput = document.getElementById('inputBirthday');
const typeSelect = document.getElementById('inputType');

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const name = nameInput.value.trim();
    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();
    const birthday = birthdayInput.value.trim();
    const userType = document.getElementById('inputType').value;

    if (!name || !email || !password || !birthday) {
        alert('Please fill in all fields.');
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify({ name, email, password, birthday, userType }),
        });

        if (!response.ok) {
            alert('Registration failed. Please check your data.');
            return;
        }

        alert('Account created successfully!');
        window.location.href = './login.html';
    } catch (error) {
        console.error('Error:', error);
        alert('Failed to connect to the server.');
    }
});
