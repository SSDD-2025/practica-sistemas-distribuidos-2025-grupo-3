document.addEventListener("DOMContentLoaded", () => {
    const postContainer = document.getElementById("post-container");
    const prevBtn = document.getElementById("prev-btn");
    const nextBtn = document.getElementById("next-btn");

    let currentPage = parseInt(postContainer.dataset.initialPage || 0);

    async function cargarPosts(page) {
        try {
            const respuesta = await fetch(`https://localhost:8443/api/posts/?page=${page}`);
            const datos = await respuesta.json();

            postContainer.innerHTML = "";

            datos.content.forEach(post => {
                const div = document.createElement("div");
                div.className = "card p-3 shadow-sm";

                const fecha = new Date(post.creationDate);
                const formattedDate = `${fecha.getHours().toString().padStart(2, '0')}:${fecha.getMinutes().toString().padStart(2, '0')} ${fecha.getDate().toString().padStart(2, '0')}-${(fecha.getMonth() + 1).toString().padStart(2, '0')}-${fecha.getFullYear()}`;

                div.innerHTML = `
                    <h5><strong><em><u>${post.title}</u></em></strong></h5>
                    <p class="mb-1 text-muted">
                        <strong>${post.owner.username}</strong> en <strong><em>${post.community.name}</em></strong>
                        <span class="ms-2" style="color: darkgreen;">🕒 ${formattedDate}</span>
                    </p>
                    <p>${post.postContent}</p>
                `;

                postContainer.appendChild(div);
            });

            currentPage = datos.number;
            prevBtn.disabled = datos.first;
            nextBtn.disabled = datos.last;
        } catch (error) {
            console.error("Error al cargar los posts:", error);
        }
    }

    prevBtn.addEventListener("click", () => {
        if (currentPage > 0) {
            cargarPosts(currentPage - 1);
        }
    });

    nextBtn.addEventListener("click", () => {
        cargarPosts(currentPage + 1);
    });

    cargarPosts(currentPage);
});