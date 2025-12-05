const token = localStorage.getItem('token');

if (!token) {
    console.warn('Token não encontrado. Redirecionando para login.');
    window.location.href = "./login.html";
}

async function getUser() {
    const res = await fetch('https://projeto-web-backend.onrender.com/api/users/me', {
        headers: {
            Authorization: 'Bearer ' + token,
        },
    });
    if (!res.ok) throw new Error('Erro ao buscar usuário');
    return res.json();
}

async function fetchClubs(name) {
    let url = 'https://projeto-web-backend.onrender.com/api/bookclub';

    const res = await fetch(url, {
        headers: { Authorization: 'Bearer ' + token },
    });
    if (!res.ok) throw new Error('Erro ao buscar clubes');
    return res.json();
}

async function fetchBook(id) {
    const assignmentPage = await fetch(`https://projeto-web-backend.onrender.com/api/bookclubassignment/club/${id}`, {
        headers: { Authorization: 'Bearer ' + token },
    }).then((r) => r.json());
    const res = await fetch(`https://projeto-web-backend.onrender.com/api/books/${assignmentPage.content[0].bookId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    if (!res.ok) return null;
    return await res.json();
}

async function fetchUserParticipants(userId) {
    const res = await fetch(`https://projeto-web-backend.onrender.com/api/participantuser/byuser/${userId}`, {
        headers: { Authorization: 'Bearer ' + token },
    });
    if (!res.ok) {
        if (res.status === 404) return { content: [] };
        throw new Error('Erro ao buscar participações do usuário');
    }
    return res.json();
}

(async function init() {
    try {
        const user = await getUser();
        const userId = user.id;

        const participantsPage = await fetchUserParticipants(userId);
        const participants = participantsPage.content || [];
        const participantMap = new Map();
        participants.forEach((p) => {
            if (p.clubId && p.idParticipantUser) {
                participantMap.set(p.clubId, p.idParticipantUser);
            } else if (p.clubId && p.idParticipantUser === undefined && p.idParticipantUser === null) {
                participantMap.set(p.clubId, p.idParticipantUser || p.idParticipant || null);
            }
        });

        const clubsPage = await fetchClubs();
        const clubs = clubsPage.content || [];

        const clubsContainer = document.getElementById('clubsContainer');
        const emptyDiv = document.getElementById('emptyClubs');

        const searchForm = document.getElementById('searchForm');
        const clubNameInput = document.getElementById('clubName');

        async function renderList(list) {
            clubsContainer.innerHTML = '';
            if (!list.length) {
                emptyDiv.style.display = 'flex';
                return;
            } else {
                emptyDiv.style.display = 'none';
            }

            for (const c of list) {
                const card = document.createElement('div');
                card.className = 'clubCard';
                card.dataset.clubId = c.id;

                const titleRow = document.createElement('div');
                titleRow.className = 'clubTitleRow';

                const titleP = document.createElement('p');
                titleP.textContent = c.name || 'Clube sem nome';

                const themeP = document.createElement('p');
                themeP.className = 'clubTheme';
                themeP.textContent = c.theme || '';

                titleRow.appendChild(titleP);
                titleRow.appendChild(themeP);

                const descP = document.createElement('p');
                descP.className = 'clubDescription';
                descP.textContent = c.description || 'Sem descrição.';

                const infoSmall = document.createElement('p');
                infoSmall.className = 'infoSmall';
                const book = await fetchBook(c.id);
                infoSmall.textContent = `Livro do Mês: ${book.title}`;

                const buttonsDiv = document.createElement('div');
                buttonsDiv.className = 'clubButtons';

                const joinBtn = document.createElement('button');
                joinBtn.className = 'btn joinBtn';
                joinBtn.textContent = 'Entrar';

                const leaveBtn = document.createElement('button');
                leaveBtn.className = 'btn leaveBtn';
                leaveBtn.textContent = 'Sair';

                const isParticipant = participantMap.has(c.id);

                if (isParticipant) {
                    joinBtn.style.display = 'none';
                    leaveBtn.style.display = 'inline-block';
                    leaveBtn.dataset.participantId = participantMap.get(c.id);
                } else {
                    leaveBtn.style.display = 'none';
                    joinBtn.style.display = 'inline-block';
                }

                joinBtn.addEventListener('click', async (e) => {
                    e.stopPropagation();
                    joinBtn.disabled = true;
                    try {
                        const body = {
                            user: { id: userId },
                            club: { idBookClub: c.id },
                        };

                        const res = await fetch('https://projeto-web-backend.onrender.com/api/participantuser', {
                            method: 'POST',
                            headers: {
                                'Content-Type': 'application/json',
                                Authorization: 'Bearer ' + token,
                            },
                            body: JSON.stringify(body),
                        });

                        if (!res.ok) {
                            const text = await res.text();
                            throw new Error(text || 'Erro ao entrar no clube');
                        }

                        const created = await res.json();
                        const pid = created.idParticipantUser || created.id || null;
                        participantMap.set(c.id, pid);
                        joinBtn.style.display = 'none';
                        leaveBtn.dataset.participantId = pid;
                        leaveBtn.style.display = 'inline-block';
                    } catch (err) {
                        console.error(err);
                        alert('Não foi possível entrar no clube.');
                    } finally {
                        joinBtn.disabled = false;
                    }
                });

                leaveBtn.addEventListener('click', async (e) => {
                    e.stopPropagation();
                    leaveBtn.disabled = true;
                    try {
                        const participantId = leaveBtn.dataset.participantId;
                        if (!participantId) {
                            const resp = await fetch(`https://projeto-web-backend.onrender.com/api/participantuser/byuser/${userId}`, {
                                headers: { Authorization: 'Bearer ' + token },
                            });
                            if (!resp.ok) throw new Error();
                            const page = await resp.json();
                            const found = (page.content || []).find((p) => p.clubId === c.id);
                            if (found) {
                                await deleteParticipant(found.idParticipantUser);
                            } else {
                                throw new Error('registro de participação não encontrado');
                            }
                        } else {
                            await deleteParticipant(participantId);
                        }

                        participantMap.delete(c.id);
                        leaveBtn.style.display = 'none';
                        joinBtn.style.display = 'inline-block';
                    } catch (err) {
                        console.error(err);
                        alert('Não foi possível sair do clube.');
                    } finally {
                        leaveBtn.disabled = false;
                    }
                });

                async function deleteParticipant(pid) {
                    const res = await fetch(`https://projeto-web-backend.onrender.com/api/participantuser/${pid}`, {
                        method: 'DELETE',
                        headers: {
                            Authorization: 'Bearer ' + token,
                        },
                    });
                    if (!res.ok) throw new Error('Erro ao deletar participação');
                    return;
                }

                card.addEventListener('click', () => {
                    window.location.href = `./club.html?id=${c.id}`;
                });

                buttonsDiv.appendChild(joinBtn);
                buttonsDiv.appendChild(leaveBtn);

                card.appendChild(titleRow);
                card.appendChild(descP);
                card.appendChild(infoSmall);
                card.appendChild(buttonsDiv);

                clubsContainer.appendChild(card);
            }
        }

        renderList(clubs);

        searchForm.addEventListener('submit', (ev) => {
            ev.preventDefault();
            const q = clubNameInput.value.trim().toLowerCase();
            const filtered = clubs.filter((c) => (c.name || '').toLowerCase().includes(q));
            renderList(filtered);
        });
    } catch (err) {
        console.error(err);
        const clubsContainer = document.getElementById('clubsContainer');
        clubsContainer.innerHTML = `<div class="emptyCatalogue" style="display:flex;"><p>Erro ao carregar clubes.</p></div>`;
    }
})();
