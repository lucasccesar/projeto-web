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
const accountType = user.type;

if (accountType != 'ADMINISTRATOR') {
    localStorage.removeItem('token');
    window.location.href = './index.html';
}

const API_URL = 'https://projeto-web-backend.onrender.com/api/books';
const bookForm = document.getElementById('bookForm');
const titleInput = document.getElementById('title');
const synopsisInput = document.getElementById('synopsis');
const genreInput = document.getElementById('genre');
const availableQuantityInput = document.getElementById('availableQuantity');
const priceInput = document.getElementById('price');
const authorInput = document.getElementById('author');

bookForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const title = titleInput.value.trim();
    const synopsis = synopsisInput.value.trim();
    const genre = genreInput.value.trim();
    const availableQuantity = parseInt(availableQuantityInput.value);
    const price = parseFloat(priceInput.value);
    let authorNames = authorInput.value
        .split(',')
        .map((a) => a.trim())
        .filter((a) => a.length > 0);

    if (!title || !synopsis || !genre || isNaN(availableQuantity) || isNaN(price) || authorNames.length === 0) {
        alert('Por favor, preencha todos os campos corretamente.');
        return;
    }

    try {
        const authors = [];

        for (const name of authorNames) {
            const res = await fetch(`${API_URL}/api/authors/name?name=${encodeURIComponent(name)}&page=0&size=1`, {
                headers: { Authorization: `Bearer ${token}` },
            });
            const data = await res.json();

            if (data.content && data.content.length > 0) {
                authors.push({ id: data.content[0].id, name: data.content[0].name });
            } else {
                const createRes = await fetch('https://projeto-web-backend.onrender.com/api/authors', {
                    method: 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: `Bearer ${token}`,
                    },
                    body: JSON.stringify({ name }),
                });
                const newAuthor = await createRes.json();
                authors.push({ id: newAuthor.id, name: newAuthor.name });
            }
        }

        const response = await fetch(API_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify({ title, synopsis, genre, availableQuantity, price, authors }),
        });

        if (!response.ok) {
            alert('Falha ao salvar o livro. Verifique os dados.');
            return;
        }

        alert('Livro salvo com sucesso!');
        window.location.href = './index.html';
    } catch (error) {
        console.error('Erro:', error);
        alert('Falha ao conectar com o servidor.');
    }
});
