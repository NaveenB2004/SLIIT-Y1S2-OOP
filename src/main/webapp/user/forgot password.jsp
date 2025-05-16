<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Forgot Password | Online Bookstore</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body {
            background-color: #f8f9fa;
            height: 100vh;
            background-image: url('4.jpg');
            background-size: cover;
        }

        .password-reset-card {
            max-width: 500px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
            background-color: rgba(255, 255, 255, 0.95);
        }

        .password-reset-icon {
            font-size: 3rem;
            color: #4e73df;
            margin-bottom: 20px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="password-reset-card">
        <div class="text-center">
            <div class="password-reset-icon">
                <i class="fas fa-key"></i>
            </div>
            <h2>Forgot Your Password?</h2>
            <p class="text-muted">Enter your email and we'll send you a reset link</p>
        </div>

        <form action="${pageContext.request.contextPath}/auth/forgot-password" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <input type="email" class="form-control" id="email" name="email"
                       placeholder="your@email.com" required>
            </div>
            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-paper-plane me-2"></i> Send Reset Link
                </button>
            </div>
        </form>

        <div class="text-center mt-3">
            <a href="login.html" class="text-decoration-none">
                <i class="fas fa-arrow-left me-1"></i> Back to Login
            </a>
        </div>
    </div>
</div>

<!-- Font Awesome -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/js/all.min.js"></script>
<!-- Bootstrap 5 JS -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>