const bookId = new URLSearchParams(window.location.search).get('id');
const token = localStorage.getItem('token');
let userId = null;
let userType = null;

async function fetchUser() {
    const res = await fetch('http://localhost:8080/api/users/me', {
        headers: { Authorization: 'Bearer ' + token },
    });
    const data = await res.json();
    userId = data.id;
    userType = data.type;
    if (userType === 'ADMINISTRATOR') {
        document.getElementById('deleteBookBtn').style.display = 'flex';
        document.getElementById('editBookBtn').style.display = 'flex';
    }
    return data;
}

const user = await fetchUser();

async function fetchBook() {
    const res = await fetch(`http://localhost:8080/api/books/${bookId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    const book = await res.json();

    document.getElementById('bookTitle').textContent = book.title;
    document.getElementById('bookAuthors').textContent = 'por ' + book.authors.map((a) => a.name).join(', ');
    document.getElementById('bookGenre').textContent = book.genre;
    document.getElementById('bookSynopsis').textContent = book.synopsis;
    document.getElementById('bookPrice').textContent = `R$${book.price.toFixed(2)}`;
    document.getElementById('rateBookLink').href = `./rateBook.html?bookId=${book.id}`;
    document.getElementById('editBookBtn').href = `./editBook.html?bookId=${book.id}`;

    const favoriteBtn = document.getElementById('favoriteBtn');
    const favoriteText = document.getElementById('favoriteText');

    const isFavorited = user.favoriteBooks.some((f) => f.id === book.id);
    favoriteText.textContent = isFavorited ? 'Desfavoritar' : 'Favoritar';
    if (isFavorited) {
        favoriteBtn.classList.add('favorited');
    }

    favoriteBtn.addEventListener('click', async () => {
        const method = favoriteText.textContent === 'Favoritar' ? 'POST' : 'DELETE';

        try {
            const response = await fetch(`http://localhost:8080/api/users/${userId}/favorites/${book.id}`, {
                method,
                headers: {
                    Authorization: 'Bearer ' + token,
                    'Content-Type': 'application/json',
                },
            });
            if (!response.ok) throw new Error();

            favoriteText.textContent = method === 'POST' ? 'Desfavoritar' : 'Favoritar';
            if (favoriteText.textContent === 'Desfavoritar') {
                favoriteBtn.classList.add('favorited');
            } else {
                favoriteBtn.classList.remove('favorited');
            }
        } catch (error) {
            alert('Erro ao atualizar favorito.');
        }
    });

    const statusRes = await fetch(`http://localhost:8080/api/readingstatus/byBookAndUser?bookId=${book.id}&userId=${userId}`, {
        method: 'GET',
        headers: {
            Authorization: 'Bearer ' + token,
        },
    });

    let readingStatus = await statusRes.json().catch(() => null);

    if (readingStatus) {
        document.getElementById('readingStatusSelect').value = readingStatus.status || '';
    }

    document.getElementById('readingStatusForm').addEventListener('submit', async (e) => {
        e.preventDefault();
        const status = document.getElementById('readingStatusSelect').value;

        try {

            if (readingStatus && readingStatus.id) {
                if (status === '') {
                    const res = await fetch(`http://localhost:8080/api/readingstatus/${readingStatus.id}`, {
                        method: 'DELETE',
                        headers: { Authorization: 'Bearer ' + token },
                    });

                    if (!res.ok) throw new Error('Erro ao deletar status');

                    alert('Status removido!');
                    readingStatus = null;
                    document.getElementById('readingStatusSelect').value = '';
                } else {
                    const res = await fetch(`http://localhost:8080/api/readingstatus/${readingStatus.id}`, {
                        method: 'PUT',
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: 'Bearer ' + token,
                        },
                        body: JSON.stringify({
                            id: readingStatus.id,
                            users: { id: userId },
                            book: { idBook: book.id },
                            status,
                        }),
                    });

                    if (!res.ok) throw new Error('Erro ao atualizar status');

                    const updatedStatus = await res.json();
                    readingStatus = updatedStatus;
                    alert('Status atualizado!');
                }
            } else {
                if (status !== '') {
                    console.log(JSON.stringify({
                            id: '',
                            users: { id: userId },
                            book: { idBook: book.id },
                            status,
                        }),)
                    const res = await fetch(`http://localhost:8080/api/readingstatus`, {
                        method: 'POST',
                        headers: {
                            'Content-Type': 'application/json',
                            Authorization: 'Bearer ' + token,
                        },
                        body: JSON.stringify({
                            id: '',
                            users: { id: userId },
                            book: { idBook: book.id },
                            status,
                        }),
                    });

                    if (!res.ok) throw new Error('Erro ao criar status');

                    const newStatus = await res.json();
                    readingStatus = newStatus;
                    alert('Status salvo!');
                } else {
                    alert('Nenhum status selecionado.');
                }
            }
        } catch (err) {
            console.error(err);
            alert(err.message || 'Erro ao atualizar status de leitura.');
        }
    });

    const collectionsRes = await fetch(`http://localhost:8080/api/colection/user/${userId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });

    const collectionList = document.getElementById('collectionList');
    const toggleCollections = document.getElementById('toggleCollections');
    const addCollectionsBtn = document.getElementById('addCollectionsBtn');

    toggleCollections.addEventListener('click', () => {
        collectionList.classList.toggle('hidden');
    });

    async function loadCollections() {
        const res = await fetch(`http://localhost:8080/api/colection/user/${userId}`, {
            headers: { Authorization: 'Bearer ' + token },
        });
        const data = await res.json();

        if (data.content.length === 0) {
            collectionList.innerHTML = `<p style="padding:6px;">Nenhuma coleção criada</p>`;
            return;
        }

        collectionList.innerHTML = data.content
            .map((c) => {
                const isChecked = c.books?.some((b) => b.id === bookId) ? 'checked' : '';
                return `
                    <label>
                        <input type="checkbox" value="${c.id}" ${isChecked}>
                        ${c.name}
                    </label>
                `;
            })
            .join('');
    }

    addCollectionsBtn.addEventListener('click', async () => {
        const allCheckboxes = [...document.querySelectorAll('#collectionList input')];

        if (allCheckboxes.length === 0) {
            alert('Nenhuma coleção encontrada.');
            return;
        }

        for (const cb of allCheckboxes) {
            const colId = cb.value;
            const colRes = await fetch(`http://localhost:8080/api/colection/${colId}`, {
                headers: { Authorization: 'Bearer ' + token },
            });
            const collection = await colRes.json();

            let books = collection.books ? collection.books.map((b) => ({ idBook: b.id })) : [];

            if (cb.checked) {
                if (!books.some((b) => b.idBook === bookId)) books.push({ idBook: bookId });
            } else {
                books = books.filter((b) => b.idBook !== bookId);
            }

            await fetch(`http://localhost:8080/api/colection/${colId}`, {
                method: 'PUT',
                headers: {
                    Authorization: 'Bearer ' + token,
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    idColection: collection.id,
                    user: { id: collection.userId },
                    name: collection.name,
                    description: collection.description,
                    books: books,
                }),
            });
        }

        alert('Coleções atualizadas com sucesso!');
        collectionList.classList.add('hidden');
    });

    loadCollections();

    const ratingsRes = await fetch(`http://localhost:8080/api/ratings/all/${book.id}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    const ratingsData = await ratingsRes.json();

    const ratingsContainer = document.getElementById('ratingsContainer');
    const emptyRatings = document.getElementById('emptyRatings');

    if (ratingsData.content.length > 0) {
        ratingsContainer.style.display = 'flex';
        emptyRatings.style.display = 'none';
        const ratingItems = await Promise.all(
            ratingsData.content.map(async (r) => {
                const userRes = await fetch(`http://localhost:8080/api/users/${r.user}`, {
                    headers: { Authorization: 'Bearer ' + token },
                });

                const user = await userRes.json();

                return `
                    <div class="ratingItem">
                        <div>
                            <p class="userRate">${user.name}</p>
                            <p class="userRate">Nota: ${r.ratingValue}/10</p>
                        </div>
                        <p>${r.comment}</p>
                    </div>
                `;
            })
        );

        ratingsContainer.innerHTML = ratingItems.join('');
    } else {
        ratingsContainer.style.display = 'none';
        emptyRatings.style.display = 'flex';
    }

    document.getElementById('ratingQuantity').textContent = `${ratingsData.content.length} Avaliações`;

    const ratingsAvgRes = await fetch(`http://localhost:8080/api/ratings/average/${book.id}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    const ratingsAvg = await ratingsAvgRes.json();

    document.getElementById('averageRating').textContent = ratingsAvg == 'NaN' ? 0 : ratingsAvg;

    function getCart() {
        const cart = localStorage.getItem('cartBooks');
        return cart ? JSON.parse(cart) : [];
    }

    function saveCart(cart) {
        localStorage.setItem('cartBooks', JSON.stringify(cart));
    }

    function updateCartCount() {
        const cart = getCart();
        document.getElementById('cartBookCount').textContent = `(${cart.length})`;
    }

    updateCartCount();

    const buyBtn = document.getElementById('buyBookBtn');
    const buyText = document.getElementById('buyBookText');

    function checkIfInCart(id) {
        const cart = getCart();
        return cart.includes(id);
    }

    function refreshBuyButton() {
        if (checkIfInCart(book.id)) {
            buyText.textContent = 'No Carrinho';
            buyBtn.disabled = true;
            buyBtn.classList.add('favorited');
            buyBtn.style.backgroundColor = 'rgba(122, 140, 99, 0.5)';
        } else {
            buyText.textContent = 'Comprar';
            buyBtn.disabled = false;
            buyBtn.classList.remove('favorited');
            buyBtn.style.backgroundColor = 'rgba(122, 140, 99, 1);';
        }
    }

    refreshBuyButton();

    buyBtn.addEventListener('click', () => {
        const cart = getCart();
        if (!cart.includes(book.id)) {
            cart.push(book.id);
            saveCart(cart);
            updateCartCount();
            refreshBuyButton();
        }
    });

    document.getElementById('bookPrice').textContent = `R$${book.price.toFixed(2)}`;
    const bookQuantityEl = document.getElementById('bookQuantity');
    bookQuantityEl.textContent = `Quantidade disponível: ${book.availableQuantity}`;

    if (book.quantity === 0) {
        buyBtn.disabled = true;
        buyText.textContent = 'Esgotado';
        buyBtn.style.backgroundColor = 'rgba(122, 140, 99, 0.5)';
    }
}

document.getElementById('deleteBookBtn').addEventListener('click', async () => {
    try {
        const res = await fetch(`http://localhost:8080/api/books/${bookId}`, {
            method: 'DELETE',
            headers: { Authorization: 'Bearer ' + token },
        });

        if (!res.ok) {
            throw new Error(`Impossível excluir livro pois viola restrição de chave estrangeira`);
        }

        alert('Livro excluído!');
        window.location.href = './home.html';
    } catch (err) {
        console.error(err);
        alert(err.message || 'Erro ao tentar excluir o livro.');
    }
});

fetchBook();
