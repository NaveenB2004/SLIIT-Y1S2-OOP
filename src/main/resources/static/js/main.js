// Constants
const API_BASE_URL = '/api/books';
const DEFAULT_IMAGE = 'https://via.placeholder.com/200x300?text=No+Image';
const ADMIN_PASSWORD = 'admin123';

// State
let isAdmin = false;
let currentBookId = null;

// API Service
const BookService = {
    async getAllBooks() {
        const response = await fetch(API_BASE_URL);
        if (!response.ok) throw new Error('Failed to fetch books');
        return response.json();
    },

    async getBookById(id) {
        const response = await fetch(`${API_BASE_URL}/${id}`);
        if (!response.ok) throw new Error('Failed to fetch book');
        return response.json();
    },

    async addBook(book) {
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(book)
        });
        if (!response.ok) throw new Error('Failed to add book');
        return response.json();
    },

    async updateBook(id, book) {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'PUT',
            headers: { 'Content-Type': 'application/json' },
            body: JSON.stringify(book)
        });
        if (!response.ok) throw new Error('Failed to update book');
        return response.json();
    },

    async deleteBook(id) {
        const response = await fetch(`${API_BASE_URL}/${id}`, {
            method: 'DELETE'
        });
        if (!response.ok) throw new Error('Failed to delete book');
    }
};

// View Handlers
const View = {
    displayBooks(books) {
        const bookList = document.getElementById('bookList');
        bookList.innerHTML = books.map(book => this.createBookCard(book)).join('');
    },

    createBookCard(book) {
        const imageUrl = book.imageUrl || DEFAULT_IMAGE;
        return `
            <div class="col-md-4 mb-4">
                <div class="card book-card h-100" onclick="Controller.showBookDetails(${book.id})">
                    <div class="book-image-container">
                        <img src="${imageUrl}" 
                             class="card-img-top book-cover" 
                             alt="${book.title}"
                             onerror="this.src='${DEFAULT_IMAGE}'">
                    </div>
                    <div class="card-body">
                        <h5 class="card-title">${book.title}</h5>
                        <p class="card-text text-muted">${book.author.name}</p>
                    </div>
                </div>
            </div>`;
    },

    displayBookDetails(book) {
        const imageUrl = book.imageUrl || DEFAULT_IMAGE;
        document.getElementById('modalTitle').textContent = book.title;
        document.getElementById('modalBody').innerHTML = `
            <div class="row">
                <div class="col-md-4">
                    <img src="${imageUrl}" 
                         class="img-fluid" 
                         alt="${book.title}"
                         onerror="this.src='${DEFAULT_IMAGE}'"
                         style="width: 100%; border-radius: 4px;">
                </div>
                <div class="col-md-8">
                    <h6 class="fw-bold">Summary</h6>
                    <p>${book.summary}</p>
                    <h6 class="fw-bold">Author</h6>
                    <p>${book.author.name}</p>
                    <h6 class="fw-bold">Author Bio</h6>
                    <p>${book.author.bio}</p>
                    <h6 class="fw-bold">Other Books by Author</h6>
                    <p>${book.author.otherBooks.join(', ')}</p>
                </div>
            </div>`;
        
        new bootstrap.Modal(document.getElementById('bookModal')).show();
    },

    populateForm(book) {
        document.getElementById('bookId').value = book.id || '';
        document.getElementById('bookTitle').value = book.title || '';
        document.getElementById('imageUrl').value = book.imageUrl || '';
        document.getElementById('bookSummary').value = book.summary || '';
        document.getElementById('authorName').value = book.author.name || '';
        document.getElementById('authorBio').value = book.author.bio || '';
        document.getElementById('otherBooks').value = book.author.otherBooks ? book.author.otherBooks.join(', ') : '';
        document.getElementById('adminFormCard').scrollIntoView({ behavior: 'smooth' });
    },

    resetForm() {
        const form = document.getElementById('bookForm');
        form.reset();
        form.classList.remove('was-validated');
        document.getElementById('bookId').value = '';
        currentBookId = null;
    },

    showAlert(message, type) {
        const alertDiv = document.createElement('div');
        alertDiv.className = `alert alert-${type} alert-dismissible fade show`;
        alertDiv.innerHTML = `
            ${message}
            <button type="button" class="btn-close" data-bs-dismiss="alert"></button>`;
        
        const container = document.getElementById('alertContainer');
        container.appendChild(alertDiv);
        
        setTimeout(() => alertDiv.remove(), 5000);
    },

    toggleAdminControls(isEnabled) {
        document.querySelectorAll('.admin-controls').forEach(el => {
            el.style.display = isEnabled ? 'block' : 'none';
        });
        document.getElementById('toggleAdmin').textContent = isEnabled ? 'Switch to User Mode' : 'Switch to Admin Mode';
    },

    promptForPassword() {
        const password = prompt('Please enter admin password:');
        return password === ADMIN_PASSWORD;
    }
};

// Controller
const Controller = {
    async init() {
        this.setupEventListeners();
        await this.loadBooks();
    },

    setupEventListeners() {
        // Toggle Admin Mode
        document.getElementById('toggleAdmin').addEventListener('click', () => {
            if (!isAdmin) {
                // Switching to admin mode requires password
                if (!View.promptForPassword()) {
                    View.showAlert('Incorrect password!', 'danger');
                    return;
                }
            }
            isAdmin = !isAdmin;
            View.toggleAdminControls(isAdmin);
        });

        // Form Submission
        document.getElementById('bookForm').addEventListener('submit', async (e) => {
            e.preventDefault();
            if (!e.target.checkValidity()) {
                e.stopPropagation();
                e.target.classList.add('was-validated');
                return;
            }
            await this.handleFormSubmit();
        });

        // Image URL validation
        document.getElementById('imageUrl').addEventListener('change', (e) => {
            const url = e.target.value.trim();
            if (url && !this.isValidImageUrl(url)) {
                View.showAlert('Please enter a valid image URL (http:// or https://)', 'warning');
            }
        });
    },

    isValidImageUrl(url) {
        return url.match(/^https?:\/\/.+/i);
    },

    async loadBooks() {
        try {
            const books = await BookService.getAllBooks();
            View.displayBooks(books);
        } catch (error) {
            View.showAlert('Error loading books: ' + error.message, 'danger');
        }
    },

    async showBookDetails(id) {
        try {
            const book = await BookService.getBookById(id);
            currentBookId = id;
            View.displayBookDetails(book);
        } catch (error) {
            View.showAlert('Error loading book details: ' + error.message, 'danger');
        }
    },

    showAddForm() {
        View.resetForm();
        document.getElementById('adminFormCard').scrollIntoView({ behavior: 'smooth' });
    },

    async handleFormSubmit() {
        const bookId = document.getElementById('bookId').value;
        const imageUrl = document.getElementById('imageUrl').value.trim();
        
        // Validate image URL if provided
        if (imageUrl && !this.isValidImageUrl(imageUrl)) {
            View.showAlert('Please enter a valid image URL (http:// or https://)', 'warning');
            return;
        }

        const book = {
            title: document.getElementById('bookTitle').value.trim(),
            imageUrl: imageUrl || null,
            summary: document.getElementById('bookSummary').value.trim(),
            author: {
                name: document.getElementById('authorName').value.trim(),
                bio: document.getElementById('authorBio').value.trim(),
                otherBooks: document.getElementById('otherBooks').value.split(',').map(book => book.trim()).filter(book => book)
            }
        };

        try {
            if (bookId) {
                await BookService.updateBook(bookId, book);
                View.showAlert('Book updated successfully!', 'success');
            } else {
                await BookService.addBook(book);
                View.showAlert('Book added successfully!', 'success');
            }
            View.resetForm();
            await this.loadBooks();
        } catch (error) {
            View.showAlert('Error saving book: ' + error.message, 'danger');
        }
    },

    editBook() {
        const modalBody = document.getElementById('modalBody');
        const img = modalBody.querySelector('img');
        const paragraphs = modalBody.querySelectorAll('p');
        
        const book = {
            id: currentBookId,
            title: document.getElementById('modalTitle').textContent,
            imageUrl: img.src === DEFAULT_IMAGE ? '' : img.src,
            summary: paragraphs[0].textContent,
            author: {
                name: paragraphs[1].textContent,
                bio: paragraphs[2].textContent,
                otherBooks: paragraphs[3].textContent.split(', ').filter(book => book)
            }
        };
        
        View.populateForm(book);
        bootstrap.Modal.getInstance(document.getElementById('bookModal')).hide();
    },

    async deleteBook() {
        if (!confirm('Are you sure you want to delete this book?')) return;
        
        try {
            await BookService.deleteBook(currentBookId);
            bootstrap.Modal.getInstance(document.getElementById('bookModal')).hide();
            View.showAlert('Book deleted successfully!', 'success');
            await this.loadBooks();
        } catch (error) {
            View.showAlert('Error deleting book: ' + error.message, 'danger');
        }
    },

    resetForm() {
        View.resetForm();
    }
};

// Initialize the application
document.addEventListener('DOMContentLoaded', () => Controller.init());

// Make Controller available globally for onclick handlers
window.Controller = Controller;

// Add this style block at the end of the file
document.head.insertAdjacentHTML('beforeend', `
    <style>
        .book-card {
            transition: transform 0.2s;
            height: 100%;
        }
        
        .book-card:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 8px rgba(0,0,0,0.1);
        }

        .book-image-container {
            position: relative;
            padding-top: 150%; /* 2:3 aspect ratio for portrait books */
            overflow: hidden;
        }

        .book-cover {
            position: absolute;
            top: 0;
            left: 0;
            width: 100%;
            height: 100%;
            object-fit: cover;
        }

        .card-body {
            background: #fff;
        }
    </style>
`); 