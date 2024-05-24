const cartItems = document.getElementById('cart-items');
const cartTotal = document.getElementById('cart-total');
const checkoutBtn = document.getElementById('checkout-btn');
const productsList = document.getElementById('products-list');

let cart = [];
let total = 0;

// Función para agregar un producto al carrito
function addToCart(productName, price) {
    const existingItem = cart.find(item => item.name === productName);
    if (existingItem) {
        // Si el producto ya está en el carrito, aumenta su contador y actualiza la cantidad total
        existingItem.quantity++;
        existingItem.totalPrice += price;
        total += price;
        // Encuentra el elemento del carrito correspondiente y actualiza su HTML
        const cartItem = cartItems.querySelector(`li[data-name="${productName}"]`);
        cartItem.querySelector('.quantity').innerText = existingItem.quantity;
        cartItem.querySelector('.total-price').innerText = `$${existingItem.totalPrice.toFixed(2)}`;
    } else {
        // Si es un nuevo producto, crea un nuevo elemento en el carrito
        const item = document.createElement('li');
        item.setAttribute('data-name', productName);
        item.innerHTML = `
            <div>
                <h3>${productName}</h3>
                <p>$${price}</p>
            </div>
            <div>
                <span class="quantity">1</span>
                <button class="remove-btn">X</button>
                <span class="total-price">$${price.toFixed(2)}</span>
            </div>
        `;
        cartItems.appendChild(item);

        cart.push({ name: productName, price: price, quantity: 1, totalPrice: price });
        total += price;
    }
    cartTotal.innerText = `$${total.toFixed(2)}`;
}

// Función para eliminar un producto del carrito
function removeFromCart(productName, price) {
    const existingItem = cart.find(item => item.name === productName);
    if (existingItem) {
        existingItem.quantity--;
        existingItem.totalPrice -= price;
        total -= price;
        const cartItem = cartItems.querySelector(`li[data-name="${productName}"]`);
        const quantityElement = cartItem.querySelector('.quantity');
        if (existingItem.quantity === 0) {
            // Si la cantidad llega a 0, eliminar el elemento del carrito
            cart.splice(cart.indexOf(existingItem), 1);
            cartItem.remove();
        } else {
            // Actualizar la cantidad y el precio total
            quantityElement.innerText = existingItem.quantity;
            cartItem.querySelector('.total-price').innerText = `$${existingItem.totalPrice.toFixed(2)}`;
        }
        cartTotal.innerText = `$${total.toFixed(2)}`;
    }
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

// Agregar eventos de click a los botones "Eliminar"
cartItems.addEventListener('click', (event) => {
    if (event.target.classList.contains('remove-btn')) {
        const productName = event.target.parentElement.parentElement.getAttribute('data-name');
        const productPrice = parseFloat(event.target.parentElement.previousElementSibling.querySelector('p').textContent.slice(1));
        removeFromCart(productName, productPrice);
    }
});

// Función para procesar el pago
checkoutBtn.addEventListener('click', () => {
    alert(`Total a pagar: $${total.toFixed(2)}`);
});
