const token = localStorage.getItem('token');

async function getUser() {
    const res = await fetch('http://localhost:8080/api/users/me', {
        headers: { Authorization: 'Bearer ' + token },
    });
    return await res.json();
}

async function getPurchases(userId) {
    const res = await fetch(`http://localhost:8080/api/purchase/PageId/${userId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    const data = await res.json();
    return data.content || [];
}

async function fetchBookTitle(bookId) {
    try {
        const res = await fetch(`http://localhost:8080/api/books/${bookId}`, {
            headers: { Authorization: 'Bearer ' + token },
        });
        if (!res.ok) return `Livro: ${bookId}`;
        const book = await res.json();
        return `${book.title}`;
    } catch {
        return `${bookId}`;
    }
}

function formatDate(dateString) {
    const d = new Date(dateString);
    const day = String(d.getDate()).padStart(2, '0');
    const month = String(d.getMonth() + 1).padStart(2, '0');
    const year = d.getFullYear();
    return `${day}/${month}/${year}`;
}

async function renderPurchases(purchases) {
    const empty = document.getElementById('emptyHistory');
    const list = document.getElementById('purchasesList');

    list.innerHTML = '';

    if (purchases.length === 0) {
        empty.style.display = 'flex';
        return;
    }

    empty.style.display = 'none';

    for (const p of purchases) {
        const purchaseDiv = document.createElement('div');
        purchaseDiv.classList.add('purchaseItem');

        const headerDiv = document.createElement('div');
        headerDiv.classList.add('purchaseHeader');

        const dateP = document.createElement('p');
        dateP.textContent = `Data: ${formatDate(p.purchaseDate)}`;

        const totalP = document.createElement('p');
        totalP.textContent = `Total: R$${p.totalValuation.toFixed(2)}`;

        headerDiv.append(dateP, totalP);
        purchaseDiv.appendChild(headerDiv);

        const booksDiv = document.createElement('div');
        booksDiv.classList.add('booksInPurchase');

        for (const b of p.books) {
            const bookDiv = document.createElement('div');
            bookDiv.classList.add('bookInPurchase');

            const titleP = document.createElement('p');
            titleP.style.cursor = 'pointer';
            titleP.style.color = '#7A8C63';

            titleP.textContent = await fetchBookTitle(b.idBook);

            titleP.addEventListener('click', () => {
                window.location.href = `./book.html?id=${b.idBook}`;
            });

            const quantityP = document.createElement('p');
            quantityP.textContent = `Qtd: ${b.quantity} - R$${b.unitPrice.toFixed(2)}`;

            bookDiv.append(titleP, quantityP);
            booksDiv.appendChild(bookDiv);
        }

        purchaseDiv.appendChild(booksDiv);
        list.appendChild(purchaseDiv);
    }
}

const user = await getUser();
const purchases = await getPurchases(user.id);
await renderPurchases(purchases);
