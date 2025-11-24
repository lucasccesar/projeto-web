const token = localStorage.getItem('token');

async function getUser() {
    const response = await fetch('http://localhost:8080/api/users/me', {
        method: 'GET',
        headers: {
            Authorization: 'Bearer ' + token,
        },
    });

    const user = await response.json();
    return user;
}
const user = await getUser();
const accountType = user.type;

document.getElementById('favoriteCount').innerText = `(${user.favoriteBooks.length})`;

async function getBooks() {
    const response = await fetch('http://localhost:8080/api/books', {
        method: 'GET',
        headers: {
            Authorization: 'Bearer ' + token,
        },
    });

    const books = await response.json();
    return books;
}

const books = (await getBooks()).content;

if (accountType == 'CLIENT') {
    document.querySelector('#mainHeaderWrapper a').style.display = 'none';
}

if (books == 0) {
    if (accountType == 'CLIENT') {
        document.getElementById(`emptyCatalogueAdmin`).style.display = 'none';
    } else {
        document.getElementById(`emptyCatalogueClient`).style.display = 'none';
    }
} else {
    document.getElementById(`emptyCatalogueAdmin`).style.display = 'none';
    document.getElementById(`emptyCatalogueClient`).style.display = 'none';

    const catalogue = document.getElementById('catalogue');
    const userId = user.id;

    for (const b of books) {
        const bookElement = document.createElement('div');
        bookElement.classList.add('bookItem');
        bookElement.setAttribute('data-bookId', b.id);

        const titleDiv = document.createElement('div');
        titleDiv.classList.add('titleDiv');

        const titleP = document.createElement('p');
        titleP.textContent = b.title;

        const favoriteBtn = document.createElement('button');
        favoriteBtn.classList.add('favoriteBtn');

        const favoriteSpan = document.createElement('span');
        favoriteSpan.classList.add('material-symbols-rounded');
        favoriteSpan.textContent = 'favorite';

        if (user.favoriteBooks.some((f) => f.id === b.id)) {
            favoriteBtn.classList.add('favorited');
        }

        favoriteBtn.appendChild(favoriteSpan);

        favoriteBtn.addEventListener('click', async (e) => {
            e.stopPropagation();

            const isFavorited = favoriteBtn.classList.contains('favorited');

            try {
                const response = await fetch(`http://localhost:8080/api/users/${userId}/favorites/${b.id}`, {
                    method: isFavorited ? 'DELETE' : 'POST',
                    headers: {
                        'Content-Type': 'application/json',
                        Authorization: 'Bearer ' + token,
                    },
                });

                if (!response.ok) throw new Error();

                favoriteBtn.classList.toggle('favorited');
            } catch (error) {
                alert('Erro ao atualizar seu favorito.');
            }
        });

        titleDiv.appendChild(titleP);
        titleDiv.appendChild(favoriteBtn);

        const authorP = document.createElement('p');
        authorP.classList.add('authorP', 'lightText');
        authorP.textContent = b.authors.map((a) => a.name).join(', ');

        const ratingDiv = document.createElement('div');
        ratingDiv.classList.add('ratingDiv');

        const starSpan = document.createElement('span');
        starSpan.classList.add('material-symbols-rounded', 'starSpan');
        starSpan.textContent = 'star';

        const ratingsAvgRes = await fetch(`http://localhost:8080/api/ratings/average/${b.id}`, {
            headers: { Authorization: 'Bearer ' + token },
        });

        const ratingsAvg = await ratingsAvgRes.json();

        const avgP = document.createElement('p');
        avgP.textContent = ratingsAvg == 'NaN' ? '0' : ratingsAvg;

        const ratingsRes = await fetch(`http://localhost:8080/api/ratings/all/${b.id}`, {
            headers: { Authorization: 'Bearer ' + token },
        });
        const ratingsData = await ratingsRes.json();

        const quantityP = document.createElement('p');
        quantityP.textContent = `(${ratingsData.content.length})`;
        quantityP.classList.add('lightText');

        ratingDiv.appendChild(starSpan);
        ratingDiv.appendChild(avgP);
        ratingDiv.appendChild(quantityP);

        const infoDiv = document.createElement('div');
        infoDiv.classList.add('infoDiv');

        const genreP = document.createElement('p');
        genreP.textContent = b.genre;
        genreP.classList.add('lightText');

        const priceP = document.createElement('p');
        priceP.textContent = `R$${b.price.toFixed(2)}`;
        priceP.classList.add('priceP');

        infoDiv.appendChild(genreP);
        infoDiv.appendChild(priceP);

        bookElement.appendChild(titleDiv);
        bookElement.appendChild(authorP);
        bookElement.appendChild(ratingDiv);
        bookElement.appendChild(infoDiv);

        catalogue.appendChild(bookElement);

        bookElement.addEventListener('click', () => {
            window.location.href = `./book.html?id=${b.id}`;
        });
    }
}

function updateCount() {
    document.getElementById('favoriteCount').innerText = `(${user.favoriteBooks.length})`;
}

const searchForm = document.getElementById('searchForm');
const bookTitleInput = document.getElementById('bookTitle');
const catalogue = document.getElementById('catalogue');

searchForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const title = bookTitleInput.value.trim();

    catalogue.innerHTML = '';

    try {
        let url = 'http://localhost:8080/api/books';
        if (title) {
            url += `?title=${encodeURIComponent(title)}`;
        }

        const response = await fetch(url, {
            method: 'GET',
            headers: {
                Authorization: 'Bearer ' + token,
            },
        });

        if (!response.ok) throw new Error('Erro ao buscar livros');

        const booksData = await response.json();
        const books = booksData.content;

        if (books.length === 0) {
            catalogue.innerHTML = `<p>Nenhum livro encontrado.</p>`;
            return;
        }

        for (const b of books) {
            const bookElement = document.createElement('div');
            bookElement.classList.add('bookItem');
            bookElement.setAttribute('data-bookId', b.id);

            const titleDiv = document.createElement('div');
            titleDiv.classList.add('titleDiv');

            const titleP = document.createElement('p');
            titleP.textContent = b.title;

            const favoriteBtn = document.createElement('button');
            favoriteBtn.classList.add('favoriteBtn');

            const favoriteSpan = document.createElement('span');
            favoriteSpan.classList.add('material-symbols-rounded');
            favoriteSpan.textContent = 'favorite';

            if (user.favoriteBooks.some((f) => f.id === b.id)) {
                favoriteBtn.classList.add('favorited');
            }

            favoriteBtn.appendChild(favoriteSpan);
            favoriteBtn.addEventListener('click', async (ev) => {
                ev.stopPropagation();
                const isFavorited = favoriteBtn.classList.contains('favorited');

                try {
                    const res = await fetch(`http://localhost:8080/api/users/${user.id}/favorites/${b.id}`, {
                        method: isFavorited ? 'DELETE' : 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: 'Bearer ' + token,
                        },
                    });

                    if (!res.ok) throw new Error();
                    favoriteBtn.classList.toggle('favorited');
                } catch {
                    alert('Erro ao atualizar seu favorito.');
                }
            });

            titleDiv.appendChild(titleP);
            titleDiv.appendChild(favoriteBtn);

            const authorP = document.createElement('p');
            authorP.classList.add('authorP', 'lightText');
            authorP.textContent = b.authors.map((a) => a.name).join(', ');

            const priceP = document.createElement('p');
            priceP.textContent = `R$${b.price.toFixed(2)}`;
            priceP.classList.add('priceP');

            bookElement.appendChild(titleDiv);
            bookElement.appendChild(authorP);
            bookElement.appendChild(priceP);

            bookElement.addEventListener('click', () => {
                window.location.href = `./book.html?id=${b.id}`;
            });

            catalogue.appendChild(bookElement);
        }
    } catch (error) {
        console.error(error);
        catalogue.innerHTML = `<p>Erro ao buscar livros.</p>`;
    }
});
