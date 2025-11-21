const token = localStorage.getItem("token");

async function getUser() {
    const response = await fetch("http://localhost:8080/api/users/me", {
        method: "GET",
        headers: {
            Authorization: "Bearer " + token
        }
    });

    const user = await response.json();
    return user
}
const user = await getUser();
const accountType = user.type
console.log(user)

document.getElementById("favoriteCount").innerText = `(${user.favoriteBooks.length})`

async function getBooks() {
    const response = await fetch("http://localhost:8080/api/books", {
        method: "GET",
        headers: {
            Authorization: "Bearer " + token
        }
    });
    
    const books = await response.json();
    return books
}

const books = (await getBooks()).content;
console.log(books)

if(accountType == "CLIENT"){
    document.querySelector('#mainHeaderWrapper a').style.display = 'none'
}

if(books == 0){
    if(accountType == "CLIENT"){
        document.getElementById(`emptyCatalogueAdmin`).style.display = 'none'
    } else{
        document.getElementById(`emptyCatalogueClient`).style.display = 'none'
    }

} else{
    document.getElementById(`emptyCatalogueAdmin`).style.display = 'none'
    document.getElementById(`emptyCatalogueClient`).style.display = 'none'

    const catalogue = document.getElementById("catalogue");
    const userId = user.id;

    books.forEach(b => {
        const bookElement = document.createElement("div");
        bookElement.classList.add("bookItem");
        bookElement.setAttribute("data-bookId", b.id);

        const titleDiv = document.createElement("div");
        titleDiv.classList.add("titleDiv");

        const titleP = document.createElement("p");
        titleP.textContent = b.title;

        const favoriteBtn = document.createElement("button");
        favoriteBtn.classList.add("favoriteBtn");

        const favoriteSpan = document.createElement("span");
        favoriteSpan.classList.add("material-symbols-rounded");
        favoriteSpan.textContent = "favorite";

        if (user.favoriteBooks.some(f => f.id === b.id)) {
            favoriteBtn.classList.add("favorited");
        }

        favoriteBtn.appendChild(favoriteSpan);

        favoriteBtn.addEventListener("click", async (e) => {
            e.stopPropagation();

            const isFavorited = favoriteBtn.classList.contains("favorited");

            try {
                const response = await fetch(`http://localhost:8080/api/users/${userId}/favorites/${b.id}`, {
                    method: isFavorited ? "DELETE" : "POST",
                    headers: {
                        "Content-Type": "application/json",
                        "Authorization": "Bearer " + token
                    }
                });

                if (!response.ok) throw new Error();

                favoriteBtn.classList.toggle("favorited");
            } catch (error) {
                alert("Erro ao atualizar seu favorito.");
            }
        });

        titleDiv.appendChild(titleP);
        titleDiv.appendChild(favoriteBtn);

        const authorP = document.createElement("p");
        authorP.classList.add("authorP", "lightText");
        // Lista de autores separados por vírgula
        authorP.textContent = b.authors.map(a => a.name).join(", ");

        const ratingDiv = document.createElement("div");
        ratingDiv.classList.add("ratingDiv");

        const starSpan = document.createElement("span");
        starSpan.classList.add("material-symbols-rounded");
        starSpan.classList.add("starSpan");
        starSpan.textContent = "star";

        const avgP = document.createElement("p");
        /* avgP.textContent = `${b.average}`; */
        avgP.textContent = 0;

        const quantityP = document.createElement("p");
        /* quantityP.textContent = `(${b.ratingQuantity})`; */
        quantityP.textContent = "(0)";
        quantityP.classList.add("lightText");

        ratingDiv.appendChild(starSpan);
        ratingDiv.appendChild(avgP);
        ratingDiv.appendChild(quantityP);

        const infoDiv = document.createElement("div");
        infoDiv.classList.add("infoDiv");

        const genreP = document.createElement("p");
        genreP.textContent = b.genre;
        genreP.classList.add("lightText");

        const priceP = document.createElement("p");
        priceP.textContent = `R$${b.price.toFixed(2)}`;
        priceP.classList.add("priceP");

        infoDiv.appendChild(genreP);
        infoDiv.appendChild(priceP);

        bookElement.appendChild(titleDiv);
        bookElement.appendChild(authorP);
        bookElement.appendChild(ratingDiv);
        bookElement.appendChild(infoDiv);

        catalogue.appendChild(bookElement);

        bookElement.addEventListener("click", () => {
            window.location.href = `./book.html?id=${b.id}`;
        })
    });
}

function updateCount(){
    document.getElementById("favoriteCount").innerText = `(${user.favoriteBooks.length})`
}
