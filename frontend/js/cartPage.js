const token = localStorage.getItem('token');
const cartBooks = JSON.parse(localStorage.getItem('cartBooks')) || [];
const cartBooksList = document.getElementById('cartBooksList');
const cartItemCount = document.getElementById('cartItemCount');
const cartTotal = document.getElementById('cartTotal');
const cartQuantity = document.getElementById('cartQuantity');
const checkoutBtn = document.getElementById('checkoutBtn');

async function getUser() {
    const response = await fetch('http://localhost:8080/api/users/me', {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token },
    });
    return await response.json();
}

const user = await getUser();

let booksData = [];

async function fetchBook(id) {
    const res = await fetch(`http://localhost:8080/api/books/${id}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    if (!res.ok) return null;
    return await res.json();
}

async function loadCart() {
    cartBooksList.innerHTML = '';
    let total = 0;
    const emptyCart = document.getElementById('emptyCart');

    if (cartBooks.length === 0) {
        emptyCart.style.display = 'flex';
        cartSummary.style.display = 'none';
        cartItemCount.textContent = `0 itens no carrinho`;
        checkoutBtn.disabled = true;
        return;
    }

    emptyCart.style.display = 'none';
    cartSummary.style.display = 'flex';

    for (let id of cartBooks) {
        const book = await fetchBook(id);
        if (!book) continue;
        booksData.push(book);

        const bookDiv = document.createElement('div');
        bookDiv.classList.add('cartBookItem');

        const infoDiv = document.createElement('div');
        infoDiv.classList.add('cartBookInfo');

        const titleP = document.createElement('p');
        titleP.textContent = book.title;

        const descP = document.createElement('p');
        descP.textContent = book.synopsis;
        descP.classList.add('lightText');

        const priceP = document.createElement('p');
        priceP.textContent = `R$${book.price.toFixed(2)}`;
        priceP.classList.add('price');

        infoDiv.append(titleP, descP, priceP);

        const removeBtn = document.createElement('button');
        removeBtn.classList.add('removeBtn');
        removeBtn.textContent = 'Remover';
        removeBtn.addEventListener('click', () => removeFromCart(book.id));

        bookDiv.append(infoDiv, removeBtn);
        cartBooksList.appendChild(bookDiv);

        total += book.price;
    }

    cartItemCount.textContent = `${cartBooks.length} ${cartBooks.length === 1 ? 'item' : 'itens'} no carrinho`;
    cartTotal.textContent = `R$${total.toFixed(2)}`;
    cartQuantity.textContent = cartBooks.length;
    checkoutBtn.disabled = false;
}

function removeFromCart(id) {
    const index = cartBooks.indexOf(id);
    if (index > -1) {
        cartBooks.splice(index, 1);
        localStorage.setItem('cartBooks', JSON.stringify(cartBooks));
        booksData = [];
        loadCart();
        updateCartCount();
    }
}

function getCart() {
    const cart = localStorage.getItem('cartBooks');
    return cart ? JSON.parse(cart) : [];
}

function updateCartCount() {
    const cart = getCart();
    document.getElementById('cartBookCount').textContent = `(${cart.length})`;
}

checkoutBtn.addEventListener('click', async () => {
    const userId = user.id;
    if (!userId || cartBooks.length === 0) return alert('Carrinho vazio ou usuário não identificado.');

    const purchaseDTO = {
        idUser: userId,
        totalValuation: booksData.reduce((sum, b) => sum + b.price, 0),
        purchaseDate: new Date().toISOString().split('T')[0],
        books: booksData.map((b) => ({ idBook: b.id, quantity: 1, unitPrice: b.price })),
    };

    const res = await fetch('http://localhost:8080/api/purchase', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + token,
        },
        body: JSON.stringify(purchaseDTO),
    });

    if (res.ok) {
        booksData.map((b) => decreaseBookQnt(b.id));
        alert('Compra finalizada com sucesso!');
        localStorage.setItem('cartBooks', JSON.stringify([]));
        loadCart();
        window.location.href = `./buyHistory.html`;
    } else {
        alert('Erro ao finalizar compra.');
    }
});

async function decreaseBookQnt(id) {
    const res = await fetch(`http://localhost:8080/api/books/${id}/decrease?amount=1`, {
        method: 'PUT',
        headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + token,
        },
    });
}

loadCart();
