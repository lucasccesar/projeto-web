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
const favoriteBooks = user.favoriteBooks;

if (favoriteBooks.length === 0) {
    document.getElementById('emptyCatalogueClient').style.display = 'flex';
} else {
    document.getElementById('emptyCatalogueClient').style.display = 'none';
    const catalogue = document.getElementById('catalogue');
    const userId = user.id;

    favoriteBooks.forEach((b) => {
        const bookElement = document.createElement('div');
        bookElement.classList.add('bookItem');
        bookElement.setAttribute('data-bookId', b.id);

        const titleDiv = document.createElement('div');
        titleDiv.classList.add('titleDiv');

        const titleP = document.createElement('p');
        titleP.textContent = b.title;

        const favoriteBtn = document.createElement('button');
        favoriteBtn.classList.add('favoriteBtn', 'favorited');

        const favoriteSpan = document.createElement('span');
        favoriteSpan.classList.add('material-symbols-rounded');
        favoriteSpan.textContent = 'favorite';

        favoriteBtn.appendChild(favoriteSpan);

        favoriteBtn.addEventListener('click', async (e) => {
            e.stopPropagation();
            const response = await fetch(`https://projeto-web-backend.onrender.com/api/users/${userId}/favorites/${b.id}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    Authorization: 'Bearer ' + token,
                },
            });
            if (!response.ok) return;
            bookElement.remove();
            if (catalogue.children.length === 0) {
                document.getElementById('emptyCatalogueClient').style.display = 'flex';
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

        const avgP = document.createElement('p');
        avgP.textContent = 0;

        const quantityP = document.createElement('p');
        quantityP.textContent = '(0)';
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
    });
}
