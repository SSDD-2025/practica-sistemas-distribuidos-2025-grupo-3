function validarFormulario() {
    let email = document.getElementById("email").value;
    let password = document.getElementById("password").value;
    if (email === "" || password === "") {
        alert("Por favor, completa todos los campos.");
        return false;
    }
    return true;
}

function mostrarAlerta() {
    var toast = new bootstrap.Toast(document.getElementById('alertToast'));
    toast.show();
}

function confirmarEliminacion() {
    Swal.fire({
        title: "¿Eliminar perfil?",
        text: "Esta acción no se puede deshacer y perderás toda tu información.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#6c757d",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar",
        reverseButtons: true,
        customClass: {
            popup: 'rounded-lg shadow-lg', 
            title: 'fw-bold', 
            confirmButton: 'btn btn-danger',
            cancelButton: 'btn btn-secondary'
        }
    }).then((result) => {
        if (result.isConfirmed) {
            document.getElementById("eliminarUsuarioForm").submit();
        }
    });
}

function confirmarEliminacionComunidad(event, form) {
    event.preventDefault(); 

    Swal.fire({
        title: "¿Estás seguro?",
        text: "Esta comunidad y todo su contenido se eliminarán permanentemente.",
        icon: "warning",
        showCancelButton: true,
        confirmButtonColor: "#d33",
        cancelButtonColor: "#6c757d",
        confirmButtonText: "Sí, eliminar",
        cancelButtonText: "Cancelar"
    }).then((result) => {
        if (result.isConfirmed) {
            form.submit(); 
        }
    });
}


document.addEventListener("DOMContentLoaded", function () {
    const imageInput = document.getElementById("imageUpload");
    const profilePreview = document.getElementById("profilePreview");

    imageInput.addEventListener("change", function (event) {
        const file = event.target.files[0];

        if (file) {
            const reader = new FileReader();
            reader.onload = function (e) {
                profilePreview.src = e.target.result;
            };
            reader.readAsDataURL(file);
        }
    });
});



document.getElementById("imageUpload").addEventListener("change", function(event) {
    const file = event.target.files[0];
    if (file) {
        const reader = new FileReader();
        reader.onload = function(e) {
            document.getElementById("profilePreview").src = e.target.result;
        };
        reader.readAsDataURL(file);
    }
});

document.querySelectorAll('.collapse').forEach(el => {
    el.addEventListener('show.bs.collapse', () => {
        el.classList.add('animate__animated', 'animate__fadeIn');
    });
});
