const token = localStorage.getItem('token');
const bookId = new URLSearchParams(window.location.search).get('bookId');

if (!bookId) {
    window.location.href = './index.html';
}

async function getUser() {
    const response = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token },
    });
    return await response.json();
}

async function getBook() {
    const response = await fetch(`https://projeto-web-backend.onrender.com/api/books/${bookId}`, {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token },
    });
    return await response.json();
}

async function getUserRating(userId) {
    const res = await fetch(`https://projeto-web-backend.onrender.com/api/ratings/all/${bookId}?page=0&size=999`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    const data = await res.json();
    return data.content.find((r) => r.user === userId);
}

const ratingForm = document.getElementById('ratingForm');
const ratingValueInput = document.getElementById('ratingValue');
const commentInput = document.getElementById('comment');
const ratingHeader = document.getElementById('ratingHeader');

let user = await getUser();
let book = await getBook();
let existingRating = await getUserRating(user.id);

ratingHeader.textContent = 'Avaliar: ' + book.title;

if (existingRating) {
    ratingValueInput.value = existingRating.ratingValue;
    commentInput.value = existingRating.comment || '';
}

ratingForm.addEventListener('submit', async (e) => {
    e.preventDefault();

    const payload = {
        user: { id: user.id },
        book: { idBook: bookId },
        comment: commentInput.value.trim(),
        ratingValue: parseInt(ratingValueInput.value),
        ratingDate: new Date().toISOString().split('T')[0],
    };

    let response;

    if (existingRating) {
        response = await fetch(`https://projeto-web-backend.onrender.com/api/ratings/${existingRating.id}`, {
            method: 'PUT',
            headers: {
                'Content-Type': 'application/json',
                Authorization: 'Bearer ' + token,
            },
            body: JSON.stringify(payload),
        });
    } else {
        response = await fetch('https://projeto-web-backend.onrender.com/api/ratings', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
                Authorization: `Bearer ${token}`,
            },
            body: JSON.stringify(payload),
        });
    }

    if (!response.ok) {
        alert('Erro ao publicar a avaliação.');
        return;
    }

    alert('Avaliação publicada com sucesso!');
    window.location.href = './book.html?id=' + bookId;
});
