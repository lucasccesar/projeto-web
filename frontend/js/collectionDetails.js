const token = localStorage.getItem('token');
const params = new URLSearchParams(window.location.search);
const collectionId = params.get('id');

async function getCollection() {
    const response = await fetch(`https://projeto-web-backend.onrender.com/api/colection/${collectionId}`, {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token },
    });
    const data = await response.json();
    return data;
}

const collection = await getCollection();

async function getUser() {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        headers: { Authorization: 'Bearer ' + token },
    });
    return await res.json();
}

const user = await getUser();

if(user.type === "CLIENT"){
    document.getElementById('editCollectionBtn').style.display = "none"
}

document.getElementById('collectionName').textContent = collection.name;
document.getElementById('collectionDesc').textContent = collection.description;

document.getElementById('editCollectionBtn').addEventListener('click', () => {
    window.location.href = `./editCollection.html?id=${collection.id}`;
});

const books = collection.books;

if (!books || books.length === 0) {
    document.getElementById('emptyCollection').style.display = 'flex';
} else {
    document.getElementById('emptyCollection').style.display = 'none';
    const catalogue = document.getElementById('catalogue');

    books.forEach((b) => {
        const bookElement = document.createElement('div');
        bookElement.classList.add('bookItem');
        bookElement.setAttribute('data-bookId', b.id);

        const titleDiv = document.createElement('div');
        titleDiv.classList.add('titleDiv');

        const titleP = document.createElement('p');
        titleP.textContent = b.title;

        titleDiv.appendChild(titleP);

        const authorP = document.createElement('p');
        authorP.classList.add('lightText');
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
