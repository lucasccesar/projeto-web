const token = localStorage.getItem('token');

async function getUser() {
    const response = await fetch('http://localhost:8080/api/users/me', {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token },
    });
    return await response.json();
}

async function getStatusBooks(userId) {
    const response = await fetch(`http://localhost:8080/api/readingstatus/idUser/${userId}`, {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token },
    });
    const data = await response.json();
    return data.content || [];
}

function renderBooks(list) {
    const empty = document.getElementById('emptyCatalogueClient');
    const catalogue = document.getElementById('catalogue');

    catalogue.innerHTML = '';

    if (list.length === 0) {
        empty.style.display = 'flex';
        return;
    }

    empty.style.display = 'none';

    list.forEach((s) => {
        const b = s.book;

        const div = document.createElement('div');
        div.classList.add('bookItem');

        const titleDiv = document.createElement('div');
        titleDiv.classList.add('titleDiv');

        const titleP = document.createElement('p');
        titleP.textContent = b.title;

        titleDiv.appendChild(titleP);
        div.appendChild(titleDiv);

        const authorP = document.createElement('p');
        authorP.classList.add('authorP', 'lightText');
        authorP.textContent = b.authors.map((a) => a.name).join(', ');

        div.appendChild(authorP);

        const infoDiv = document.createElement('div');
        infoDiv.classList.add('infoDiv');

        const genreP = document.createElement('p');
        genreP.classList.add('lightText');
        genreP.textContent = b.genre;

        const priceP = document.createElement('p');
        priceP.classList.add('priceP');
        priceP.textContent = `R$${b.price.toFixed(2)}`;

        infoDiv.appendChild(genreP);
        infoDiv.appendChild(priceP);

        div.appendChild(infoDiv);

        div.addEventListener('click', () => {
            window.location.href = `./book.html?id=${b.idBook || b.id}`;
        });

        catalogue.appendChild(div);
    });
}

const user = await getUser();
const allStatus = await getStatusBooks(user.id);

function filterByStatus(status) {
    return allStatus.filter((s) => s.status === status);
}

renderBooks(filterByStatus('LENDO'));

document.querySelectorAll('.statusBtn').forEach((btn) => {
    btn.addEventListener('click', () => {
        document.querySelectorAll('.statusBtn').forEach((b) => b.classList.remove('active'));
        btn.classList.add('active');
        renderBooks(filterByStatus(btn.dataset.status));
        updateStatusCounters();
    });
});

function updateStatusCounters() {
    document.querySelector('[data-status="LENDO"]').textContent = `Lendo agora (${filterByStatus('LENDO').length})`;

    document.querySelector('[data-status="LIDO"]').textContent = `Já li (${filterByStatus('LIDO').length})`;

    document.querySelector('[data-status="QUERO_LER"]').textContent = `Quero ler (${filterByStatus('QUERO_LER').length})`;
}

updateStatusCounters();
