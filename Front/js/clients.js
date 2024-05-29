document.addEventListener('DOMContentLoaded', async (event) => {
    let clientes = [];

    const getClientes = async () => {
        try {
            const url = 'http://localhost:8080/burgerFrame/Controller?ACTION=CLIENTE.FIND_ALL';
            const response = await fetch(url);
            clientes = await response.json();

            createClienteDetail(clientes);
        } catch (error) {
            console.error('Error fetching clientes:', error);
        }
    };

    const createClienteDetail = (clientes) => {
        const clienteTableBody = document.querySelector('table tbody');
        clienteTableBody.innerHTML = '';
        
        clientes.forEach(cliente => {
            const { idCliente, nombre, apellidos, email, telefono } = cliente;
            
            const row = `
                <tr data-id="${idCliente}">
                    <td>${idCliente}</td>
                    <td>${nombre}</td>
                    <td>${apellidos}</td>
                    <td>${email}</td>
                    <td>${telefono}</td>
                    <td>
                        <button class="delete-button" data-id="${idCliente}">Delete</button>
                    </td>
                </tr>
            `;
            
            clienteTableBody.insertAdjacentHTML('beforeend', row);
        });


        const deleteButtons = document.querySelectorAll('.delete-button');
        deleteButtons.forEach(button => {
            button.addEventListener('click', () => {
                const clienteId = button.getAttribute('data-id');
                deleteCliente(clienteId);
            });
        });
    };

    const deleteCliente = async (clienteId) => {
        try {
            const url = `http://localhost:8080/burgerFrame/Controller?ACTION=CLIENTE.DELETE&ID_CLIENTES=${clienteId}`;
            const response = await fetch(url, {
                method: 'DELETE',
            });
    
            const result = await response.text();
            alert(result);
    
            if (response.ok) {
                getClientes(); 
            }
        } catch (error) {
            console.error('Error al eliminar el cliente:', error);
        }
    };

    const addClientModal = document.getElementById('add-client-modal');
    const addClientForm = document.getElementById('add-client-form');
    const closeButton = document.querySelector('.close-button');

    const showAddClientForm = () => {
        addClientModal.style.display = 'flex';
    };

    const hideAddClientForm = () => {
        addClientModal.style.display = 'none';
        addClientForm.reset();
    };

    const addButton = document.querySelector('.add-button');
    if (addButton) {
        addButton.addEventListener('click', showAddClientForm);
    }

    if (closeButton) {
        closeButton.addEventListener('click', hideAddClientForm);
    }

    addClientForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        const clientId = document.getElementById('client-id').value;
        const firstName = document.getElementById('first-name').value;
        const lastName = document.getElementById('last-name').value;
        const email = document.getElementById('email').value;
        const phone = document.getElementById('phone').value;

        try {
            const url = 'http://localhost:8080/burgerFrame/Controller?ACTION=CLIENTE.ADD';
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify({
                    idCliente: clientId,
                    nombre: firstName,
                    apellidos: lastName,
                    email: email,
                    telefono: phone
                })
            });

            const result = await response.text();
            alert(result);

            if (response.ok) {
                getClientes();
                hideAddClientForm();
            }
        } catch (error) {
            console.error('Error adding cliente:', error);
        }
    });

    getClientes();
});
