document.addEventListener('DOMContentLoaded', async () => {
    // Seleccionar el elemento productList
    const productList = document.getElementById('products-list');
    
    // Verificar si el elemento productList se seleccionó correctamente
    if (!productList) {
        console.error('No se encontró el elemento con id "products-list"');
        return;
    }

    const getProductsDetails = async () => {
        try {
            const url = 'http://localhost:8080/burgerFrame/Controller?ACTION=PRODUCTO.FIND_ALL';
            const response = await fetch(url);
            
            // Verificar si la respuesta es exitosa
            if (!response.ok) {
                throw new Error('Failed to fetch products');
            }
            
            // Convertir la respuesta a JSON
            const products = await response.json();
            
            // Imprimir los datos recibidos en la consola para verificar su estructura
            console.log('Productos recibidos:', products);
            
            // Llamar a la función para renderizar los productos en la pantalla
            renderProducts(products);
        } catch (error) {
            console.error('Error fetching products details:', error);
        }
    };

    const renderProducts = (products) => {
        productList.innerHTML = ''; 
    
        products.forEach(product => {
            const { ID_PRODUCTO, NOMBRE, DESCRIPCION, PRECIO, IMAGEN, ID_CATPRODUCTO } = product;
            
            const listItem = document.createElement('li');
            listItem.innerHTML = `
                <div>
                    <p>ID Producto: ${ID_PRODUCTO}</p>
                    <img src="${IMAGEN}" alt="${NOMBRE}" style="max-width: 100px;">
                    <p>Nombre: ${NOMBRE}</p>
                    <p>Descripción: ${DESCRIPCION}</p>
                    <p>Precio: ${PRECIO}</p>
                    <p>ID Categoría Producto: ${ID_CATPRODUCTO}</p>
                </div>
            `;
            productList.appendChild(listItem);
        });
    };
    

    // Llamar a la función para obtener los detalles de los productos
    getProductsDetails();
});
