const cartItems = document.getElementById('cart-items');
const cartTotal = document.getElementById('cart-total');
const checkoutBtn = document.getElementById('checkout-btn');
const productsList = document.getElementById('products-list');

let cart = [];
let total = 0;

// Función para agregar un producto al carrito
function addToCart(productName, price) {
    const item = document.createElement('li');
    item.innerHTML = `
        <img src="product-image.jpg" alt="${productName}">
        <div>
            <h3>${productName}</h3>
            <p>$${price}</p>
        </div>
        <button class="remove-btn">Eliminar</button>
    `;
    cartItems.appendChild(item);
    
    cart.push({ name: productName, price: price });
    total += price;
    cartTotal.innerText = `$${total.toFixed(2)}`;
    
    // Agregar evento de click para eliminar productos
    item.querySelector('.remove-btn').addEventListener('click', () => {
        const index = cart.findIndex(product => product.name === productName && product.price === price);
        if (index !== -1) {
            cart.splice(index, 1);
            total -= price;
            cartTotal.innerText = `$${total.toFixed(2)}`;
            item.remove();
        }
    });
}

// Agregar eventos de click a los botones "Agregar al carrito"
const addButtons = document.querySelectorAll('.products .add-btn');
addButtons.forEach(button => {
    button.addEventListener('click', () => {
        const productName = button.parentElement.querySelector('h3').textContent;
        const productPrice = parseFloat(button.parentElement.querySelector('p').textContent.slice(1));
        addToCart(productName, productPrice);
    });
});

// Función para procesar el pago
checkoutBtn.addEventListener('click', () => {
    alert(`Total a pagar: $${total.toFixed(2)}`);
});