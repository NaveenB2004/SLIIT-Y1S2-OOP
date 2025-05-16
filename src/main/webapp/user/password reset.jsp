<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reset Password | Online Bookstore</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        .password-reset-card {
            max-width: 500px;
            margin: 100px auto;
            padding: 30px;
            border-radius: 10px;
            box-shadow: 0 5px 15px rgba(0, 0, 0, 0.1);
        }
    </style>
</head>
<body>
<div class="container">
    <div class="password-reset-card">
        <h2 class="text-center mb-4">Reset Your Password</h2>

        <form action="${pageContext.request.contextPath}/auth/reset-password" method="post">
            <input type="hidden" name="token" value="${param.token}">

            <div class="mb-3">
                <label for="password" class="form-label">New Password</label>
                <input type="password" class="form-control" id="password" name="password"
                       placeholder="Enter new password" required>
            </div>

            <div class="mb-3">
                <label for="confirmPassword" class="form-label">Confirm Password</label>
                <input type="password" class="form-control" id="confirmPassword"
                       placeholder="Confirm new password" required>
            </div>

            <div class="d-grid gap-2">
                <button type="submit" class="btn btn-primary">
                    <i class="fas fa-save me-2"></i> Update Password
                </button>
            </div>
        </form>
    </div>
</div>

<!-- Password match validation -->
<script>
    document.querySelector('form').addEventListener('submit', function (e) {
        const password = document.getElementById('password').value;
        const confirmPassword = document.getElementById('confirmPassword').value;

        if (password !== confirmPassword) {
            e.preventDefault();
            alert('Passwords do not match!');
        }
    });
</script>
</body>
</html>
S