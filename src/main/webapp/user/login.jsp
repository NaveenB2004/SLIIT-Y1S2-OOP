<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Login | Online Bookstore</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #f8f9fc;
            --dark-color: #5a5c69;
        }
        body {
            background-color: var(--secondary-color);
            background-image: url('C:/Users/adees/Desktop/OOP Project/User Management/Frontend new/3.png');
            background-size: cover;
            background-position: center;
            height: 100vh;
        }
        .login-container {
            max-width: 450px;
            margin: auto;
            padding: 2.5rem;
            background-color: rgba(255, 255, 255, 0.96);
            border-radius: 10px;
            box-shadow: 0 0 30px rgba(0, 0, 0, 0.1);
            margin-top: 5%;
        }
        .login-header {
            text-align: center;
            margin-bottom: 2rem;
        }
        .login-header img {
            width: 80px;
            margin-bottom: 1rem;
        }
        .login-header h2 {
            color: var(--dark-color);
            font-weight: 600;
        }
        .form-control:focus {
            border-color: var(--primary-color);
            box-shadow: 0 0 0 0.25rem rgba(78, 115, 223, 0.25);
        }
        .btn-login {
            background-color: var(--primary-color);
            border-color: var(--primary-color);
            padding: 0.5rem;
            font-weight: 500;
            letter-spacing: 0.5px;
        }
        .btn-login:hover {
            background-color: #3a5ccc;
            border-color: #3a5ccc;
        }
        .password-toggle {
            cursor: pointer;
            position: absolute;
            right: 10px;
            top: 50%;
            transform: translateY(-50%);
            color: var(--dark-color);
        }
        .other-options {
            text-align: center;
            margin-top: 1.5rem;
            color: var(--dark-color);
        }
        .divider {
            display: flex;
            align-items: center;
            margin: 1.5rem 0;
            color: var(--dark-color);
        }
        .divider::before, .divider::after {
            content: "";
            flex: 1;
            border-bottom: 1px solid #ddd;
        }
        .divider::before {
            margin-right: 1rem;
        }
        .divider::after {
            margin-left: 1rem;
        }
        .social-login .btn {
            margin: 5px;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="login-container">
        <div class="login-header">
            <img src="C:\Users\adees\Desktop\OOP Project\User Management\Frontend new\1.png>" alt="Book Store 130' Logo">
            <h2>Welcome Back!</h2>
            <p class="text-muted">Please login to your account</p>
        </div>
        <c:if test="${not empty error}">
            <div class="alert alert-danger">${error}</div>
        </c:if>
        <form action="/login" method="post">
            <div class="mb-3">
                <label for="email" class="form-label">Email Address</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-envelope"></i></span>
                    <input type="email" class="form-control" id="email" name="email" placeholder="sample@email.com" required>
                </div>
            </div>
            <div class="mb-3 position-relative">
                <label for="password" class="form-label">Password</label>
                <div class="input-group">
                    <span class="input-group-text"><i class="fas fa-lock"></i></span>
                    <input type="password" class="form-control" id="password" name="password" placeholder="Enter your password" required>
                    <span class="password-toggle" onclick="togglePassword('password')">
                            <i class="fas fa-eye"></i>
                        </span>
                </div>
                <div class="form-text text-end">
                    <a href="${pageContext.request.contextPath}/forgot-password">Forgot Password?</a>
                </div>
            </div>
            <div class="mb-3 form-check">
                <input type="checkbox" class="form-check-input" id="remember" name="remember">
                <label class="form-check-label" for="remember">Remember me</label>
            </div>
            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary btn-login">
                    <i class="fas fa-sign-in-alt me-2"></i> Login
                </button>
            </div>
            <div class="divider">OR</div>
            <div class="social-login text-center mb-3">
                <p class="mb-3">Login with social account</p>
                <button type="button" class="btn btn-outline-primary" onclick="alert('Google login not implemented')">
                    <i class="fab fa-google"></i>
                </button>
                <button type="button" class="btn btn-outline-primary" onclick="alert('Facebook login not implemented')">
                    <i class="fab fa-facebook-f"></i>
                </button>
                <button type="button" class="btn btn-outline-primary" onclick="alert('Twitter login not implemented')">
                    <i class="fab fa-twitter"></i>
                </button>
            </div>

        </form>
        <div class="other-options">
            <p>Don't have an account? <a href="${pageContext.request.contextPath}/register">Create one</a></p>
        </div>
    </div>
</div>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<script>
    function togglePassword(fieldId) {
        const field = document.getElementById(fieldId);
        const icon = field.nextElementSibling.querySelector('i');
        if (field.type === 'password') {
            field.type = 'text';
            icon.classList.replace('fa-eye', 'fa-eye-slash');
        } else {
            field.type = 'password';
            icon.classList.replace('fa-eye-slash', 'fa-eye');
        }
    }
    document.addEventListener('DOMContentLoaded', function() {
        document.getElementById('email').focus();
    });
</script>
</body>
</html>