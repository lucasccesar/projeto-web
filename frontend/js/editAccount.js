const token = localStorage.getItem('token');

async function getUser() {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        headers: { Authorization: 'Bearer ' + token },
    });
    return await res.json();
}

const user = await getUser();

const form = document.getElementById('editAccountForm');
const nameInput = document.getElementById('name');
const emailInput = document.getElementById('inputEmail');
const passwordInput = document.getElementById('inputPassword');
const typeSelect = document.getElementById('inputType');

nameInput.value = user.name;
emailInput.value = user.email;
typeSelect.value = user.type;


form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const name = nameInput.value.trim();
    const email = emailInput.value.trim();
    const password = passwordInput.value.trim();
    const userType = typeSelect.value;
    console.log(userType)

    if (!name || !email || !password) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    try {
        const response = await fetch(`https://projeto-web-backend.onrender.com/api/users/${user.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + token
            },
            body: JSON.stringify({
                name,
                email,
                password,
                type: userType
            }),
        });

        if (!response.ok) {
            alert('Erro ao atualizar conta. Verifique os dados.');
            return;
        }

        alert('Dados atualizados com sucesso!');
        window.location.href = './account.html';

    } catch (err) {
        console.error(err);
        alert('Erro ao conectar com o servidor.');
    }
});
