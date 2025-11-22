const token = localStorage.getItem("token");
const bookId = new URLSearchParams(window.location.search).get("bookId");

if (!bookId) {
    alert("ID do livro não fornecido.");
    window.location.href = "./home.html";
}

async function getUser() {
    const res = await fetch("http://localhost:8080/api/users/me", {
        headers: { Authorization: `Bearer ${token}` }
    });
    const user = await res.json();
    return user;
}

const user = await getUser();
if (user.type !== "ADMINISTRATOR") {
    localStorage.removeItem("token");
    window.location.href = "./home.html";
}

const bookForm = document.getElementById("bookForm");
const titleInput = document.getElementById("title");
const synopsisInput = document.getElementById("synopsis");
const genreInput = document.getElementById("genre");
const priceInput = document.getElementById("price");

async function loadBook() {
    const res = await fetch(`http://localhost:8080/api/books/${bookId}`, {
        headers: { Authorization: `Bearer ${token}` }
    });
    if (!res.ok) {
        alert("Erro ao carregar livro.");
        window.location.href = "./home.html";
        return;
    }
    const book = await res.json();

    if (titleInput) titleInput.value = book.title || "";
    if (synopsisInput) synopsisInput.value = book.synopsis || "";
    if (genreInput) genreInput.value = book.genre || "";
    if (priceInput) priceInput.value = book.price ?? "";
}

await loadBook();

bookForm.addEventListener("submit", async e => {
    e.preventDefault();

    const title = titleInput?.value.trim();
    const synopsis = synopsisInput?.value.trim();
    const genre = genreInput?.value.trim();
    const price = parseFloat(priceInput?.value);

    if (!title || !synopsis || !genre || isNaN(price)) {
        alert("Por favor, preencha todos os campos corretamente.");
        return;
    }

    try {
        const response = await fetch(`http://localhost:8080/api/books/${bookId}`, {
            method: "PUT",
            headers: {
                "Content-Type": "application/json",
                Authorization: `Bearer ${token}`
            },
            body: JSON.stringify({
                idBook: bookId,
                title,
                synopsis,
                genre,
                price
            })
        });

        if (!response.ok) {
            alert("Falha ao atualizar o livro. Verifique os dados.");
            return;
        }

        alert("Livro atualizado com sucesso!");
        window.location.href = "./home.html";

    } catch (err) {
        console.error(err);
        alert("Erro ao conectar com o servidor.");
    }
});
