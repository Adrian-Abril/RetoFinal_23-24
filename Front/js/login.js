
document.getElementById('loginForm').addEventListener('submit', function(event) {
    event.preventDefault();

    const formData = new FormData(this);
    const data = {
        username: formData.get('username'),
        password: formData.get('password')
    };

    fetch('URL_DEL_SERVIDOR_DE_AUTENTICACION', {
        method: 'POST',
        headers: {
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data)
    })
    .then(response => response.json())
    .then(data => {
        if (data.success) {
            window.location.href = 'ruta_a_la_pagina_principal.html';
        } else {
            alert('Login failed: ' + data.message);
        }
    })
    .catch(error => {
        console.error('Error:', error);
    });
});
