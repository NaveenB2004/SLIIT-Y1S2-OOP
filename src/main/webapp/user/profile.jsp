<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1" />
    <title>Edit Profile</title>

    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet" />

    <!-- Custom Styles -->
    <style>
        body {
            background: linear-gradient(to right, #f8f9fa, #e9ecef);
            font-family: 'Segoe UI', Tahoma, Geneva, Verdana, sans-serif;
            min-height: 100vh;
            display: flex;
            align-items: center;
            justify-content: center;
            padding: 30px;
        }

        .profile-card {
            background-color: #ffffff;
            padding: 40px 35px;
            border-radius: 16px;
            box-shadow: 0 12px 30px rgba(0, 0, 0, 0.1);
            max-width: 650px;
            width: 100%;
            transition: all 0.3s ease-in-out;
        }

        .profile-card:hover {
            transform: translateY(-3px);
            box-shadow: 0 16px 40px rgba(0, 0, 0, 0.15);
        }

        .profile-header {
            text-align: center;
            margin-bottom: 35px;
        }

        .profile-header h1 {
            font-weight: 700;
            font-size: 2rem;
            color: #212529;
        }

        .form-label {
            font-weight: 600;
        }

        .form-control:focus {
            border-color: #0d6efd;
            box-shadow: 0 0 0 0.2rem rgba(13, 110, 253, 0.25);
        }

        .btn-primary {
            background-color: #0d6efd;
            border: none;
            font-weight: 600;
            transition: background-color 0.2s ease;
        }

        .btn-primary:hover {
            background-color: #0b5ed7;
        }

        .btn-danger {
            background-color: #dc3545;
            border: none;
            font-weight: 600;
            transition: background-color 0.2s ease;
            margin-top: 20px;
        }

        .btn-danger:hover {
            background-color: #b02a37;
        }

        .form-text {
            font-size: 0.875rem;
            color: #6c757d;
        }
    </style>
</head>
<body>

<div class="profile-card">
    <div class="profile-header">
        <h1>Edit Your Profile</h1>
        <p class="text-muted">Keep your personal information up to date</p>
    </div>

    <!-- Update Form -->
    <form action="/update" method="post" novalidate>
        <!-- Full Name -->
        <div class="mb-3">
            <label for="name" class="form-label">Full Name <span class="text-danger">*</span></label>
            <input type="text" id="name" name="name" class="form-control" value="${user.name}" required />
            <div class="invalid-feedback">Please enter your full name.</div>
        </div>

        <!-- Email (Read-only) -->
        <div class="mb-3">
            <label for="email" class="form-label">Email Address</label>
            <input type="email" id="email" name="email" class="form-control" value="${user.email}" readonly />
            <div class="form-text">Your email cannot be changed.</div>
        </div>

        <!-- New Password -->
        <div class="mb-3">
            <label for="password" class="form-label">New Password</label>
            <input type="password" id="password" name="password" class="form-control"
                   placeholder="Leave blank to keep your current password" minlength="6" />
            <div class="form-text">Must be at least 6 characters long if changing.</div>
        </div>

        <!-- Address -->
        <div class="mb-3">
            <label for="address" class="form-label">Address</label>
            <textarea id="address" name="address" class="form-control" rows="3"
                      placeholder="Enter your full address">${user.address}</textarea>
        </div>

        <!-- Phone Number -->
        <div class="mb-4">
            <label for="phone" class="form-label">Phone Number</label>
            <input type="tel" id="phone" name="phone" class="form-control" value="${user.phone}"
                   placeholder="+1234567890" pattern="^\+?[0-9\s\-]{7,15}$" />
            <div class="form-text">Include your country code, e.g. +91 9876543210</div>
        </div>

        <button type="submit" class="btn btn-primary w-100">Save Changes</button>
    </form>

    <!-- Delete Form -->
    <form action="/delete" method="post" onsubmit="return confirm('Are you sure you want to delete your account? This action cannot be undone.');">
        <button type="submit" class="btn btn-danger w-100">Delete Account</button>
    </form>
</div>

<!-- Bootstrap 5 JS Bundle -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>

<!-- Client-side validation -->
<script>
    (function () {
        'use strict';
        const forms = document.querySelectorAll('form');
        Array.from(forms).forEach(function (form) {
            form.addEventListener('submit', function (event) {
                if (!form.checkValidity()) {
                    event.preventDefault();
                    event.stopPropagation();
                }
                form.classList.add('was-validated');
            }, false);
        });
    })();
</script>

</body>
</html>
