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