const token = localStorage.getItem('token');

async function getUser() {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token }
    });
    return await res.json();
}

async function getStatusBooks(id) {
    const res = await fetch(`https://projeto-web-backend.onrender.com/api/readingstatus/idUser/${id}`, {
        method: 'GET',
        headers: { Authorization: 'Bearer ' + token }
    });
    const data = await res.json();
    return data.content || [];
}

const user = await getUser();

document.getElementById('userName').textContent = user.name;
document.getElementById('userEmail').textContent = user.email;
document.getElementById('userType').textContent = user.type == 'ADMINISTRATOR' ? 'Administrador' : 'Cliente';

document.getElementById('editAccountBtn').addEventListener('click', () => {
    window.location.href = './editAccount.html';
});

const statusList = await getStatusBooks(user.id);

const readingNow = statusList.filter(s => s.status === 'LENDO').length;
const readFinished = statusList.filter(s => s.status === 'LIDO').length;
const wantToRead = statusList.filter(s => s.status === 'QUERO_LER').length;

document.getElementById('readingNow').textContent = `Lendo agora (${readingNow})`;
document.getElementById('readFinished').textContent = `Já li (${readFinished})`;
document.getElementById('wantToRead').textContent = `Quero ler (${wantToRead})`;
