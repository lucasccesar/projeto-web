const token = localStorage.getItem('token');
const collectionId = new URLSearchParams(window.location.search).get('id');

if (!collectionId) {
    alert('ID da coleção não fornecido.');
    window.location.href = './home.html';
}

async function getUser() {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        headers: { Authorization: 'Bearer ' + token },
    });
    return await res.json();
}

const user = await getUser();
if (user.type !== 'ADMINISTRATOR') {
    window.location.href = './home.html';
}

const nameInput = document.getElementById('name');
const descriptionInput = document.getElementById('description');
const booksListDiv = document.getElementById('booksList');
const form = document.getElementById('collectionForm');
let currentBooks = [];

async function loadCollection() {
    const res = await fetch(`https://projeto-web-backend.onrender.com/api/colection/${collectionId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });

    if (!res.ok) {
        alert('Erro ao carregar coleção.');
        window.location.href = './home.html';
    }

    const data = await res.json();

    nameInput.value = data.name;
    descriptionInput.value = data.description;
    currentBooks = data.books || [];

    renderBooks();
}

function renderBooks() {
    booksListDiv.innerHTML = currentBooks
        .map(
            (b) => `
            <div class="bookItem">
                <p>${b.title}</p>
                <button class="removeBtn" data-id="${b.id}">Remover</button>
            </div>
        `
        )
        .join('');

    document.querySelectorAll('.removeBtn').forEach((btn) => {
        btn.addEventListener('click', () => {
            const id = btn.getAttribute('data-id');
            currentBooks = currentBooks.filter((b) => b.id !== id);
            renderBooks();
        });
    });
}

await loadCollection();

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const body = {
        idColection: collectionId,
        user: { id: user.id },
        name: nameInput.value.trim(),
        description: descriptionInput.value.trim(),
        books: currentBooks.map((b) => ({ idBook: b.id })),
    };

    console.log(body)

    const res = await fetch(`https://projeto-web-backend.onrender.com/api/colection/${collectionId}`, {
        method: 'PUT',
        headers: {
            Authorization: 'Bearer ' + token,
            'Content-Type': 'application/json',
        },
        body: JSON.stringify(body),
    });

    if (!res.ok) {
        alert('Erro ao salvar alterações.');
        return;
    }

    alert('Coleção atualizada com sucesso!');
    window.location.href = `./collectionDetails.html?id=${collectionId}`;
});
