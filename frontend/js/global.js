const token = localStorage.getItem('token');

if (!token || isTokenExpired(token)) {
    localStorage.removeItem('token');
    window.location.href = './login.html';
}

function isTokenExpired(token) {
    try {
        const payloadBase64 = token.split('.')[1];
        const payloadJson = JSON.parse(atob(payloadBase64));
        const expiry = payloadJson.exp * 1000;

        return Date.now() > expiry;
    } catch (e) {
        return true;
    }
}

document.getElementById('logoutBtn').addEventListener('click', () => {
    localStorage.removeItem('token');
    window.location.href = './login.html';
});

function getCart() {
    const cart = localStorage.getItem('cartBooks');
    return cart ? JSON.parse(cart) : [];
}

function updateCartCount() {
    const cart = getCart();
    document.getElementById('cartBookCount').textContent = `(${cart.length})`;
}

updateCartCount();
