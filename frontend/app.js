// Configuration - Point this to your API Gateway
const API_GATEWAY_URL = 'http://localhost:8080';

// Global State
let state = {
    products: [],
    currentPage: 0,
    pageSize: 10,
    searchQuery: '',
    selectedProductId: null
};

// DOM Elements
const grid = document.getElementById('product-grid');
const searchInput = document.getElementById('search-input');
const searchBtn = document.getElementById('search-btn');
const prevBtn = document.getElementById('prev-btn');
const nextBtn = document.getElementById('next-btn');
const pageInfo = document.getElementById('page-info');
const modal = document.getElementById('product-modal');

// --- INITIALIZATION ---
document.addEventListener('DOMContentLoaded', () => {
    fetchProducts();
    setupEventListeners();
});

function setupEventListeners() {
    // Search
    searchBtn.addEventListener('click', () => {
        state.searchQuery = searchInput.value.trim();
        state.currentPage = 0; // Reset to first page on new search
        fetchProducts();
    });

    // Pagination
    prevBtn.addEventListener('click', () => {
        if (state.currentPage > 0) {
            state.currentPage--;
            fetchProducts();
        }
    });

    nextBtn.addEventListener('click', () => {
        state.currentPage++;
        fetchProducts();
    });

    // Modal close
    document.getElementById('close-modal').addEventListener('click', closeModal);
    window.addEventListener('click', (e) => {
        if (e.target === modal) closeModal();
    });

    // Order and AI actions
    document.getElementById('place-order-btn').addEventListener('click', placeOrder);
    document.getElementById('ask-ai-btn').addEventListener('click', askGeminiAI);
}

// --- API CALLS & RENDERING ---

async function fetchProducts() {
    try {
        // Build query string. Adjust based on how your backend expects pagination/search params
        const url = `${API_GATEWAY_URL}/product/api/v1/products?page=${state.currentPage}&size=${state.pageSize}&search=${state.searchQuery}`;

        const response = await fetch(url);
        if (!response.ok) throw new Error('Failed to fetch products');

        const data = await response.json();
        // Assuming your backend returns a paginated wrapper like Spring Data Page<T>
        // If it returns a raw array, adjust accordingly: state.products = data;
        state.products = data.content || data;

        // Update pagination UI
        pageInfo.innerText = `Page ${state.currentPage + 1}`;
        prevBtn.disabled = state.currentPage === 0;
        // nextBtn.disabled = data.last; // Uncomment if backend returns "last" boolean

        renderGrid();
    } catch (error) {
        showToast(error.message, 'error');
    }
}

function renderGrid() {
    grid.innerHTML = '';
    state.products.forEach(product => {
        const card = document.createElement('div');
        card.className = 'card';
        card.onclick = () => openModal(product);

        card.innerHTML = `
            <img src="${product.productImageUrl || 'https://via.placeholder.com/300'}" alt="${product.productName}">
            <div class="card-body">
                <div class="card-title">${product.productName}</div>
                <div class="card-price">₹${product.productPrice.toFixed(2)}</div>
                <button class="view-btn">View Details</button>
            </div>
        `;
        grid.appendChild(card);
    });
}

// --- MODAL & PRODUCT DETAILS ---

async function openModal(product) {
    state.selectedProductId = product.productId;

    // Populate basic info from state
    document.getElementById('modal-image').src = product.productImageUrl || 'https://via.placeholder.com/300';
    document.getElementById('modal-title').innerText = product.productName;
    document.getElementById('modal-price').innerText = `₹${product.productPrice.toFixed(2)}`;
    document.getElementById('modal-desc').innerText = product.productDescription;
    document.getElementById('order-qty').value = 1;
    document.getElementById('ai-response').classList.add('hidden');
    document.getElementById('ai-query').value = '';

    modal.classList.remove('hidden');

    // Fetch live inventory status
    checkInventory(product.productId);
}

function closeModal() {
    modal.classList.add('hidden');
    state.selectedProductId = null;
}

async function checkInventory(productId) {
    const stockElement = document.getElementById('modal-stock');
    const orderBtn = document.getElementById('place-order-btn');

    try {
        stockElement.innerText = 'Checking stock...';
        const response = await fetch(`${API_GATEWAY_URL}/inventory/api/v1/inventory/stock/${productId}`);

        if (response.ok) {
            const inventoryDto = await response.json();
            if (inventoryDto.availableQuantity > 0) {
                stockElement.innerText = `${inventoryDto.availableQuantity} items available in stock.`;
                stockElement.style.color = 'green';
                orderBtn.disabled = false;
            } else {
                stockElement.innerText = 'Out of Stock';
                stockElement.style.color = 'red';
                orderBtn.disabled = true;
            }
        }
    } catch (error) {
        stockElement.innerText = 'Unable to verify stock.';
    }
}

// --- ORDER PLACEMENT ---

async function placeOrder() {
    if (!state.selectedProductId) return;

    const quantity = parseInt(document.getElementById('order-qty').value);

    // Construct OrderRequestDto as per your backend schema
    const orderRequest = {
        productId: state.selectedProductId,
        productQuantity: quantity
    };

    try {
        const response = await fetch(`${API_GATEWAY_URL}/order/api/v1/orders`, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(orderRequest)
        });

        if (response.ok) {
            showToast('Order placed successfully!', 'success');
            closeModal();
            // Optionally, refresh inventory display or products
        } else {
            // Handle your ExceptionResponseDto
            const errorData = await response.json();
            showToast(`Error: ${errorData.message || 'Could not place order'}`, 'error');
        }
    } catch (error) {
        showToast('Network error while placing order.', 'error');
    }
}

// --- GEMINI AI INTEGRATION ---

async function askGeminiAI() {
    if (!state.selectedProductId) return;

    const query = document.getElementById('ai-query').value.trim();
    if (!query) return;

    const responseElement = document.getElementById('ai-response');
    responseElement.classList.remove('hidden');
    responseElement.innerText = 'Asking Gemini...';

    try {
        const url = `${API_GATEWAY_URL}/product/api/v1/products/${state.selectedProductId}/query?customerQuery=${encodeURIComponent(query)}`;
        const response = await fetch(url, { method: 'POST' });

        if (response.ok) {
            // Assuming the AI response returns plain text or a JSON with a message
            const data = await response.text();
            responseElement.innerText = data;
        } else {
            responseElement.innerText = 'Failed to get a response from AI.';
        }
    } catch (error) {
        responseElement.innerText = 'Error connecting to AI service.';
    }
}

// --- UTILITIES ---

function showToast(message, type = 'success') {
    const container = document.getElementById('toast-container');
    const toast = document.createElement('div');
    toast.className = `toast ${type}`;
    toast.innerText = message;

    container.appendChild(toast);

    setTimeout(() => {
        toast.remove();
    }, 3000);
}