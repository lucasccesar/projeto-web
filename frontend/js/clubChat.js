const token = localStorage.getItem('token');

function getQuery(param) {
    return new URL(window.location.href).searchParams.get(param);
}

const clubId = getQuery('id');

const messagesBox = document.getElementById('messagesBox');
const sendBtn = document.getElementById('sendBtn');
const refreshBtn = document.getElementById('refreshBtn');
const input = document.getElementById('messageInput');

let user = null;
let canParticipate = false;

async function getUser() {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        headers: { Authorization: 'Bearer ' + token },
    });
    return res.json();
}

async function checkParticipant() {
    const res = await fetch(`https://projeto-web-backend.onrender.com/api/participantuser/byclub/${clubId}`, { headers: { Authorization: 'Bearer ' + token } });

    const data = await res.json();
    canParticipate = data.content.some((p) => p.userId === user.id);

    if (!canParticipate) {
        alert('Você não é membro deste clube.');
        window.location.href = './clubs.html';
    }
}

async function getMessageUser(id) {
    const res = await fetch(`https://projeto-web-backend.onrender.com/api/users/${id}`, { headers: { Authorization: 'Bearer ' + token } });
    return await res.json();
}

async function loadMessages() {
    const res = await fetch(`https://projeto-web-backend.onrender.com/api/clubmessage/club/${clubId}`, { headers: { Authorization: 'Bearer ' + token } });

    const data = await res.json();

    messagesBox.innerHTML = '';

    for (const msg of data.content) {
        const div = document.createElement('div');
        div.classList.add('messageBubble');

        if (msg.userId !== user.id) {
            div.classList.add('otherUser');
        }

        const userData = await getMessageUser(msg.userId);

        const userP = document.createElement('p');
        userP.classList.add('messageUser');
        userP.textContent = userData.name ?? 'Usuário';

        const textP = document.createElement('p');
        textP.classList.add('messageText');
        textP.textContent = msg.message;

        const dateP = document.createElement('p');
        dateP.classList.add('messageDate');
        dateP.textContent = formatDateMinus3Hours(msg.messageDate);

        div.appendChild(userP);
        div.appendChild(textP);
        div.appendChild(dateP);

        messagesBox.appendChild(div);

        setTimeout(() => {
            messagesBox.scrollTop = messagesBox.scrollHeight;
        }, 0);
    }

    messagesBox.scrollTop = messagesBox.scrollHeight;
}

function formatDateMinus3Hours(dateString) {
    if (!dateString) return 'Sem data';

    const date = new Date(dateString);

    date.setHours(date.getHours() - 2);

    const day = String(date.getDate()).padStart(2, '0');
    const month = String(date.getMonth() + 1).padStart(2, '0');
    const year = date.getFullYear();

    const hours = String(date.getHours()).padStart(2, '0');
    const minutes = String(date.getMinutes()).padStart(2, '0');

    return `${day}/${month}/${year}, ${hours}:${minutes}`;
}

async function sendMessage() {
    const text = input.value.trim();
    if (!text) return;

    await fetch('https://projeto-web-backend.onrender.com/api/clubmessage', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json',
            Authorization: 'Bearer ' + token,
        },
        body: JSON.stringify({
            message: text,
            user: {
                id: user.id,
            },
            club: {
                idBookClub: clubId,
            },
        }),
    });

    input.value = '';
    await loadMessages();
}

sendBtn.addEventListener('click', sendMessage);

input.addEventListener('keypress', (e) => {
    if (e.key === 'Enter') {
        e.preventDefault();
        sendMessage();
    }
});

refreshBtn.addEventListener('click', loadMessages);

(async () => {
    user = await getUser();
    await checkParticipant();
    await loadMessages();
})();
