document.addEventListener('DOMContentLoaded', async () => {
    let detalles = [];

    const getDetalles = async () => {
        try {
            const url = 'http://localhost:8080/burgerFrame/Controller?ACTION=DETALLE.FIND_ALL';
            const response = await fetch(url);
            detalles = await response.json();
            createDetalleDetail(detalles);
        } catch (error) {
            console.error('Error fetching detalles:', error);
        }
    };

    const createDetalleDetail = (detalles) => {
        const detalleTableBody = document.querySelector('table#detalle-table tbody');
        detalleTableBody.innerHTML = '';

        detalles.forEach(detalle => {
            const { idDetalle, idPedido, idProducto, cantidad, precio } = detalle;
            const row = `
                <tr data-id="${idDetalle}">
                    <td>${idDetalle}</td>
                    <td>${idPedido}</td>
                    <td>${idProducto}</td>
                    <td>${cantidad}</td>
                    <td>${precio}</td>
                    <td>
                        <button class="edit-button" data-id="${idDetalle}">Edit</button>
                        <button class="delete-button" data-id="${idDetalle}">Delete</button>
                    </td>
                </tr>
            `;
            detalleTableBody.insertAdjacentHTML('beforeend', row);
        });

 
        const deleteButtons = document.querySelectorAll('.delete-button');
        deleteButtons.forEach(button => {
            button.addEventListener('click', () => {
                const detalleId = button.getAttribute('data-id');
                deleteDetalle(detalleId);
            });
        });


        const editButtons = document.querySelectorAll('.edit-button');
        editButtons.forEach(button => {
            button.addEventListener('click', () => {
                const detalleId = button.getAttribute('data-id');
                showEditDetalleForm(detalleId);
            });
        });
    };

    const showEditDetalleForm = (detalleId) => {
        const detalle = detalles.find(detalle => detalle.idDetalle == detalleId);
        if (!detalle) return;


        document.getElementById('edit-detalle-id').value = detalle.idDetalle;
        document.getElementById('edit-pedido-id').value = detalle.idPedido;
        document.getElementById('edit-producto-id').value = detalle.idProducto;
        document.getElementById('edit-cantidad').value = detalle.cantidad;
        document.getElementById('edit-precio').value = detalle.precio;

        document.getElementById('edit-detalle-form').style.display = 'block';
    };

    const hideEditDetalleForm = () => {
        document.getElementById('edit-detalle-form').style.display = 'none';
    };

    const addDetalle = async () => {
        const idDetalle = document.getElementById('add-detalle-id').value;
        const idPedido = document.getElementById('add-pedido-id').value;
        const idProducto = document.getElementById('add-producto-id').value;
        const cantidad = document.getElementById('add-cantidad').value;
        const precio = document.getElementById('add-precio').value;

        const newDetalle = {
            idDetalle: idDetalle,
            idPedido: idPedido,
            idProducto: idProducto,
            cantidad: cantidad,
            precio: precio
        };

        try {
            const url = 'http://localhost:8080/burgerFrame/Controller?ACTION=DETALLE.ADD';
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(newDetalle)
            });

            const result = await response.json();
            alert(result.message);

            if (response.ok) {
                await getDetalles();
                document.getElementById('add-detalle-form').reset(); 
            }
        } catch (error) {
            console.error('Error adding detalle:', error);
        }
    };

    const deleteDetalle = async (detalleId) => {
        try {
            const url = `http://localhost:8080/burgerFrame/Controller?ACTION=DETALLE.DELETE&idDetalle=${detalleId}`;
            const response = await fetch(url, {
                method: 'DELETE'
            });

            const result = await response.json();
            alert(result.message);


            if (response.ok) {
                await getDetalles();
            }
        } catch (error) {
            console.error('Error deleting detalle:', error);
        }
    };

    const updateDetalle = async () => {
        const idDetalle = document.getElementById('edit-detalle-id').value;
        const idPedido = document.getElementById('edit-pedido-id').value;
        const idProducto = document.getElementById('edit-producto-id').value;
        const cantidad = document.getElementById('edit-cantidad').value;
        const precio = document.getElementById('edit-precio').value;

        const updatedDetalle = {
            idDetalle: idDetalle,
            idPedido: idPedido,
            idProducto: idProducto,
            cantidad: cantidad,
            precio: precio
        };

        try {
            const url = 'http://localhost:8080/burgerFrame/Controller?ACTION=DETALLE.UPDATE';
            const response = await fetch(url, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(updatedDetalle)
            });

            const result = await response.json();
            alert(result.message);


            if (response.ok) {
                hideEditDetalleForm();
                await getDetalles();
            }
        } catch (error) {
            console.error('Error updating detalle:', error);
        }
    };


    await getDetalles();

    const addDetalleForm = document.getElementById('add-detalle-form');
    addDetalleForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        await addDetalle();
    });


    const editDetalleForm = document.getElementById('edit-detalle-form');
    editDetalleForm.addEventListener('submit', async (event) => {
        event.preventDefault();
        await updateDetalle();
    });

    const addButton = document.querySelector('.add-button');
    addButton.addEventListener('click', () => {
        document.getElementById('add-detalle-form').style.display = 'block';
    });
});
