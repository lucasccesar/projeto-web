const token = localStorage.getItem('token');

async function getUser() {
    const response = await fetch('http://localhost:8080/api/users/me', {
        method: 'GET',
        headers: {
            Authorization: 'Bearer ' + token,
        },
    });
    return await response.json();
}

async function getCollections(id) {
    const response = await fetch(`http://localhost:8080/api/colection/user/${id}?page=0&size=50`, {
        method: 'GET',
        headers: {
            Authorization: 'Bearer ' + token,
        },
    });
    return await response.json();
}

const user = await getUser();

const data = await getCollections(user.id);
const collections = data.content;

const list = document.getElementById('collectionsList');
const empty = document.getElementById('emptyCollections');
const createBtn = document.getElementById('createCollectionBtn');
const createFirstBtn = document.getElementById('createFirstCollection');

function openCreate() {
    window.location.href = './createCollection.html';
}

createBtn.addEventListener('click', openCreate);
createFirstBtn.addEventListener('click', openCreate);

if (collections.length === 0) {
    empty.style.display = 'flex';
} else {
    empty.style.display = 'none';
    collections.forEach((c) => {
        const item = document.createElement('div');
        item.classList.add('bookItem');
        item.setAttribute('data-id', c.idColection);

        const titleDiv = document.createElement('div');
        titleDiv.classList.add('titleDiv');

        const span = document.createElement('span');
        span.classList.add('material-symbols-rounded');
        span.textContent = 'newsstand';

        titleDiv.appendChild(span);

        const nameP = document.createElement('p');
        nameP.textContent = c.name;

        titleDiv.appendChild(nameP);

        const descP = document.createElement('p');
        descP.classList.add('lightText');
        descP.textContent = c.description;

        const booksCount = document.createElement('p');
        booksCount.classList.add('lightText');
        booksCount.textContent = `${c.books.length} livros`;

        item.appendChild(titleDiv);
        item.appendChild(descP);
        item.appendChild(booksCount);

        item.addEventListener('click', () => {
            window.location.href = `./collectionDetails.html?id=${c.id}`;
        });

        list.appendChild(item);
    });
}
