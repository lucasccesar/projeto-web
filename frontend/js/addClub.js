const token = localStorage.getItem('token');

async function getUser() {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        headers: { Authorization: 'Bearer ' + token },
    });
    return res.json();
}

const user = await getUser();

if (user.type !== 'ADMINISTRATOR') {
    alert('Apenas administradores podem criar clubes.');
    window.location.href = './index.html';
}

async function getBooks() {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/books', {
        headers: { Authorization: 'Bearer ' + token },
    });
    return res.json();
}

async function createClub(data) {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/bookclub', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + token,
        },
        body: JSON.stringify(data),
    });
    return res.json();
}

async function createAssignment(data) {
    return await fetch('https://projeto-web-backend.onrender.com/api/bookclubassignment', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + token,
        },
        body: JSON.stringify(data),
    });
}

const bookSelect = document.getElementById('clubBook');

(async () => {
    const books = (await getBooks()).content;

    books.forEach((b) => {
        const opt = document.createElement('option');
        opt.value = b.id;
        opt.textContent = b.title;
        bookSelect.appendChild(opt);
    });
})();

const form = document.getElementById('clubForm');

form.addEventListener('submit', async (e) => {
    e.preventDefault();

    const name = document.getElementById('clubName').value.trim();
    const theme = document.getElementById('clubTheme').value.trim();
    const bookId = bookSelect.value;
    const description = document.getElementById('clubDescription').value.trim();
    const start = document.getElementById('startDate').value;
    const end = document.getElementById('endDate').value;

    if (!name || !theme || !bookId || !description || !start || !end) {
        alert('Preencha todos os campos!');
        return;
    }

    try {
        const club = await createClub({ name, theme, description });

        await createAssignment({
            bookClub: { idBookClub: club.id },
            book: { idBook: bookId },
            startDate: start,
            finishDate: end,
        });

        alert('Clube criado com sucesso!');
        window.location.href = './clubs.html';
    } catch (err) {
        console.error(err);
        alert('Erro ao criar o clube.');
    }
});
