const token = localStorage.getItem('token');

function getQueryParam(param) {
    const url = new URL(window.location.href);
    return url.searchParams.get(param);
}

const clubId = getQueryParam('id');

const clubName = document.getElementById('clubName');
const clubDescription = document.getElementById('clubDescription');
const clubTheme = document.getElementById('clubTheme');
const clubParticipants = document.getElementById('clubParticipants');
const clubPeriod = document.getElementById('clubPeriod');

const openChatBtn = document.getElementById('openChatBtn');
const leaveClubBtn = document.getElementById('leaveClubBtn');
const joinClubBtn = document.getElementById('joinClubBtn');
const deleteClubBtn = document.getElementById('deleteClubBtn');

const bookTitle = document.getElementById('bookTitle');
const bookDescription = document.getElementById('bookDescription');
const bookRating = document.getElementById('bookRating');
const bookGenre = document.getElementById('bookGenre');
const bookAuthors = document.getElementById('bookAuthors');

let participantId = null;

async function getUser() {
    const res = await fetch('http://localhost:8080/api/users/me', {
        headers: { Authorization: 'Bearer ' + token },
    });
    return res.json();
}

const user = await getUser();

async function getClub() {
    const res = await fetch(`http://localhost:8080/api/bookclub/${clubId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    return res.json();
}

async function getParticipants() {
    const res = await fetch(`http://localhost:8080/api/participantuser/byclub/${clubId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    return res.json();
}

async function getBook(bookId) {
    const res = await fetch(`http://localhost:8080/api/books/${bookId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    const book = await res.json();
    return book;
}

async function getRating(bookId) {
    const ratingsRes = await fetch(`http://localhost:8080/api/ratings/all/${bookId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    return await ratingsRes.json();
}

async function getRatingAvg(bookId) {
    const ratingsAvgRes = await fetch(`http://localhost:8080/api/ratings/average/${bookId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    return await ratingsAvgRes.json();
}

async function joinClub() {
    const body = {
        user: { id: user.id },
        club: { idBookClub: clubId },
    };

    const res = await fetch(`http://localhost:8080/api/participantuser`, {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + token,
        },
        body: JSON.stringify(body),
    });

    if (res.ok) {
        alert('Você entrou no clube!');
        location.reload();
    } else {
        alert('Erro ao entrar no clube.');
    }
}

async function deleteClub() {
    if (!confirm('Tem certeza que deseja excluir o clube? Esta ação não pode ser desfeita!')) return;

    const res = await fetch(`http://localhost:8080/api/bookclub/${clubId}`, {
        method: 'DELETE',
        headers: { Authorization: 'Bearer ' + token },
    });

    if (res.ok) {
        alert('Clube excluído.');
        window.location.href = './clubs.html';
    } else {
        alert('Erro ao excluir clube.');
    }
}

async function leaveClub() {
    if (!participantId) return alert('Erro: você não está registrado no clube.');

    const res = await fetch(`http://localhost:8080/api/participantuser/${participantId}`, {
        method: 'DELETE',
        headers: { Authorization: 'Bearer ' + token },
    });

    if (res.ok) {
        alert('Você saiu do clube.');
        window.location.href = './clubs.html';
    } else {
        alert('Erro ao sair do clube.');
    }
}

async function renderPage() {
    const club = await getClub();
    clubName.textContent = club.name;
    clubDescription.textContent = club.description;
    clubTheme.textContent = `Tema: ${club.theme}`;

    const participants = await getParticipants();
    clubParticipants.textContent = `${participants.content.length} participantes`;

    const found = participants.content.find((p) => p.userId === user.id);

    if (found) {
        participantId = found.idParticipantUser;

        leaveClubBtn.style.display = 'flex';
        joinClubBtn.style.display = 'none';
    } else {
        leaveClubBtn.style.display = 'none';
        joinClubBtn.style.display = 'flex';
    }

    if (user.role && user.role.toUpperCase() === 'ADMIN') {
        deleteClubBtn.style.display = 'flex';
    }

    let assignment;
    try {
        const assignmentPage = await fetch(`http://localhost:8080/api/bookclubassignment/club/${clubId}`, {
            headers: { Authorization: 'Bearer ' + token },
        }).then((r) => r.json());

        assignment = assignmentPage.content[0];
    } catch {
        assignment = null;
    }

    if (assignment) {
        const start = new Date(assignment.startDate).toLocaleDateString('pt-BR');
        const end = new Date(assignment.finishDate).toLocaleDateString('pt-BR');

        clubPeriod.textContent = `Período do livro do mês: ${start} - ${end}`;

        const book = await getBook(assignment.bookId);
        bookTitle.textContent = book.title;
        bookDescription.textContent = book.synopsis;
        bookGenre.textContent = `Gênero: ${book.genre}`;
        bookAuthors.textContent = `Autores: ${book.authors.map((a) => a.name).join(', ')}`;

        const rating = await getRating(assignment.bookId);
        const ratingAvg = await getRatingAvg(assignment.bookId);
        console.log(ratingAvg)
        bookRating.textContent = `Nota média: ${ratingAvg == 'NaN' ? 0 : ratingAvg} (${rating.content.length} avaliações)`;
    } else {
        clubPeriod.textContent = 'Nenhum livro atribuído.';
    }

    openChatBtn.href = `./clubChat.html?id=${clubId}`;
}

leaveClubBtn.addEventListener('click', leaveClub);
joinClubBtn.addEventListener('click', joinClub);
deleteClubBtn.addEventListener('click', deleteClub);

renderPage();
