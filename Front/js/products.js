document.addEventListener('DOMContentLoaded', async (event) => {
    let productos = [];

    const getProductos = async () => {
        try {
            const url = 'http://localhost:8080/burgerFrame/Controller?ACTION=PRODUCTO.FIND_ALL';
            const response = await fetch(url);
            productos = await response.json();

            createProductoDetail(productos);
        } catch (error) {
            console.error('Error fetching productos:', error);
        }
    };

    const createProductoDetail = (productos) => {
        const productoTableBody = document.querySelector('table tbody');
        productoTableBody.innerHTML = '';
        
        productos.forEach(producto => {
            const { idProducto, nombre, descripcion, precio, imagen, idCatProducto } = producto;
            
            const row = `
                <tr data-id="${idProducto}">
                    <td>${idProducto}</td>
                    <td>${nombre}</td>
                    <td>${descripcion}</td>
                    <td>${precio} $</td>
                    <td><img src="${imagen}" alt="${nombre}" style="max-width: 100px;"></td>
                    <td>${idCatProducto}</td>
                    <td>
                        <button class="edit-button" data-id="${idProducto}">Edit</button>
                        <button class="delete-button" data-id="${idProducto}">Delete</button>
                    </td>
                </tr>
            `;
            
            productoTableBody.insertAdjacentHTML('beforeend', row);
        });

    
        const deleteButtons = document.querySelectorAll('.delete-button');
        deleteButtons.forEach(button => {
            button.addEventListener('click', () => {
                const productId = button.getAttribute('data-id');
                deleteProduct(productId);
            });
        });

        const editButtons = document.querySelectorAll('.edit-button');
        editButtons.forEach(button => {
            button.addEventListener('click', () => {
                const productId = button.getAttribute('data-id');
                showEditProductForm(productId);
            });
        });
    };

    const showEditProductForm = (productId) => {
    
        const product = productos.find(p => p.idProducto == productId);
        if (!product) return;

        const editProductModal = document.getElementById('edit-product-modal');
        editProductModal.style.display = 'block';

  
        document.getElementById('edit-id-producto').value = product.idProducto;
        document.getElementById('edit-nombre').value = product.nombre;
        document.getElementById('edit-descripcion').value = product.descripcion;
        document.getElementById('edit-precio').value = product.precio;
        document.getElementById('edit-imagen').value = product.imagen;
        document.getElementById('edit-id-catproducto').value = product.idCatProducto;
    };

    const hideEditProductForm = () => {
        document.getElementById('edit-product-modal').style.display = 'none';
        document.getElementById('edit-product-form').reset();
    };

    const showAddProductForm = () => {
        document.getElementById('add-product-modal').style.display = 'block';
    };

    const hideAddProductForm = () => {
        document.getElementById('add-product-modal').style.display = 'none';
        document.getElementById('add-product-form').reset();
    };

    const addProduct = async (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const productData = {
            ID_PRODUCTO: formData.get('ID_PRODUCTO'),
            NOMBRE: formData.get('NOMBRE'),
            DESCRIPCION: formData.get('DESCRIPCION'),
            PRECIO: formData.get('PRECIO'),
            IMAGEN: formData.get('IMAGEN'),
            ID_CATPRODUCTO: formData.get('ID_CATPRODUCTO')
        };

        try {
            const queryParams = new URLSearchParams(productData).toString();
            const url = `http://localhost:8080/burgerFrame/Controller?ACTION=PRODUCTO.ADD&${queryParams}`;
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                mode: 'no-cors',
                body: JSON.stringify(productData)
            });

            const result = await response.json();
            alert(result.message);

            if (response.ok) {
                getProductos();
                hideAddProductForm();
            }
        } catch (error) {
            console.error('Error adding product:', error);
        }
    };

    const updateProduct = async (event) => {
        event.preventDefault();

        const formData = new FormData(event.target);
        const productData = {
            ID_PRODUCTO: formData.get('ID_PRODUCTO'),
            NOMBRE: formData.get('NOMBRE'),
            DESCRIPCION: formData.get('DESCRIPCION'),
            PRECIO: formData.get('PRECIO'),
            IMAGEN: formData.get('IMAGEN'),
            ID_CATPRODUCTO: formData.get('ID_CATPRODUCTO')
        };

        try {
            const queryParams = new URLSearchParams(productData).toString();
            const url = `http://localhost:8080/burgerFrame/Controller?ACTION=PRODUCTO.UPDATE&${queryParams}`;
            const response = await fetch(url, {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                mode: 'no-cors',
                body: JSON.stringify(productData)
            });

            const result = await response.json();
            alert(result.message);

            if (response.ok) {
                getProductos(); 
                hideEditProductForm();
            }
        } catch (error) {
            console.error('Error updating product:', error);
        }
    };

    const deleteProduct = async (productId) => {
        try {
            const url = `http://localhost:8080/burgerFrame/Controller?ACTION=PRODUCTO.DELETE&ID_PRODUCTO=${productId}`;
            const response = await fetch(url, {
                method: 'DELETE',
            });
    
            const result = await response.text();
            alert(result);
    
            if (response.ok) {
                getProductos(); 
            }
        } catch (error) {
            console.error('Error al eliminar el producto:', error);
        }
    };

    getProductos();

   
    const addButton = document.querySelector('.add-button');
    addButton.addEventListener('click', showAddProductForm);

 
    const addProductForm = document.getElementById('add-product-form');
    addProductForm.addEventListener('submit', addProduct);


    const editProductForm = document.getElementById('edit-product-form');
    editProductForm.addEventListener('submit', updateProduct);


    document.querySelector('#add-product-modal .close-button').addEventListener('click', hideAddProductForm);
    document.querySelector('#edit-product-modal .close-button').addEventListener('click', hideEditProductForm);
});
