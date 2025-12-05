const token = localStorage.getItem('token');

async function getUser() {
    const response = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token },
    });
    const user = await response.json();
    return user;
}

const user = await getUser();

if (!user || !user.id) {
    localStorage.removeItem('token');
    window.location.href = './index.html';
}

const API_URL = 'https://projeto-web-backend.onrender.com/api/colection';

const collectionForm = document.getElementById('collectionForm');
const nameInput = document.getElementById('name');
const descriptionInput = document.getElementById('description');

collectionForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const name = nameInput.value.trim();
    const description = descriptionInput.value.trim();
    const userId = user.id;

    if (!name || !description) {
        alert('Por favor, preencha todos os campos.');
        return;
    }

    try {
        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + token,
            },
            body: JSON.stringify({
                name,
                description,
                userId,
            }),
        });

        if (!response.ok) {
            alert('Falha ao criar coleção.');
            return;
        }

        alert('Coleção criada com sucesso!');
        window.location.href = './userCollections.html';
    } catch (error) {
        alert('Falha ao conectar com o servidor.');
    }
});
