<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>User Profile | Online Bookstore</title>
    <!-- Bootstrap 5 CSS -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <!-- Font Awesome -->
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        :root {
            --primary-color: #4e73df;
            --secondary-color: #f8f9fc;
        }

        .profile-container {
            max-width: 800px;
            margin: 30px auto;
            padding: 30px;
            background-color: white;
            border-radius: 10px;
            box-shadow: 0 0 20px rgba(0, 0, 0, 0.1);
        }

        .profile-header {
            border-bottom: 1px solid #eee;
            padding-bottom: 20px;
            margin-bottom: 30px;
        }

        .profile-avatar {
            width: 120px;
            height: 120px;
            object-fit: cover;
            border-radius: 50%;
            border: 5px solid var(--secondary-color);
        }

        .nav-pills .nav-link.active {
            background-color: var(--primary-color);
        }

        .tab-content {
            padding: 20px 0;
        }
    </style>
</head>
<body>
<div class="container">
    <div class="profile-container">
        <!-- Success/Error Messages -->
        <c:if test="${not empty successMessage}">
            <div class="alert alert-success alert-dismissible fade show">
                    ${successMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>
        <c:if test="${not empty errorMessage}">
            <div class="alert alert-danger alert-dismissible fade show">
                    ${errorMessage}
                <button type="button" class="btn-close" data-bs-dismiss="alert"></button>
            </div>
        </c:if>

        <!-- Profile Header -->
        <div class="profile-header text-center">
            <img src="https://ui-avatars.com/api/?name=${user.name}&background=4e73df&color=fff&size=120"
                 alt="Profile" class="profile-avatar mb-3">
            <h3>${user.name}</h3>
            <p class="text-muted">${user.email}</p>
        </div>

        <!-- Navigation Tabs -->
        <ul class="nav nav-pills mb-4" id="profileTabs" role="tablist">
            <li class="nav-item" role="presentation">
                <button class="nav-link active" id="profile-tab" data-bs-toggle="pill"
                        data-bs-target="#profile" type="button">Profile
                </button>
            </li>
            <li class="nav-item" role="presentation">
                <button class="nav-link" id="security-tab" data-bs-toggle="pill"
                        data-bs-target="#security" type="button">Security
                </button>
            </li>
            <c:if test="${user.role == 'ADMIN'}">
                <li class="nav-item" role="presentation">
                    <button class="nav-link" id="admin-tab" data-bs-toggle="pill"
                            data-bs-target="#admin" type="button">Admin
                    </button>
                </li>
            </c:if>
        </ul>

        <!-- Tab Content -->
        <div class="tab-content" id="profileTabsContent">
            <!-- Profile Tab -->
            <div class="tab-pane fade show active" id="profile" role="tabpanel">
                <form action="${pageContext.request.contextPath}/user/update" method="post">
                    <input type="hidden" name="id" value="${user.id}">

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Full Name</label>
                            <input type="text" class="form-control" name="name" value="${user.name}" required>
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Email</label>
                            <input type="email" class="form-control" value="${user.email}" disabled>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Address</label>
                        <textarea class="form-control" name="address" rows="3">${user.address}</textarea>
                    </div>

                    <div class="row mb-3">
                        <div class="col-md-6">
                            <label class="form-label">Phone Number</label>
                            <input type="tel" class="form-control" name="phone" value="${user.phone}">
                        </div>
                        <div class="col-md-6">
                            <label class="form-label">Member Since</label>
                            <input type="text" class="form-control"
                                   value="${user.createdAt}" disabled>
                        </div>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-save me-2"></i>Update Profile
                    </button>
                </form>
            </div>

            <!-- Security Tab -->
            <div class="tab-pane fade" id="security" role="tabpanel">
                <form action="${pageContext.request.contextPath}/user/change-password" method="post">
                    <input type="hidden" name="id" value="${user.id}">

                    <div class="mb-3">
                        <label class="form-label">Current Password</label>
                        <input type="password" class="form-control" name="currentPassword" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">New Password</label>
                        <input type="password" class="form-control" name="newPassword" required>
                    </div>

                    <div class="mb-3">
                        <label class="form-label">Confirm New Password</label>
                        <input type="password" class="form-control" name="confirmPassword" required>
                    </div>

                    <button type="submit" class="btn btn-primary">
                        <i class="fas fa-lock me-2"></i>Change Password
                    </button>
                </form>
            </div>

            <!-- Admin Tab (Visible only for ADMIN) -->
            <c:if test="${user.role == 'ADMIN'}">
                <div class="tab-pane fade" id="admin" role="tabpanel">
                    <div class="d-flex justify-content-between mb-3">
                        <h5>User Management</h5>
                        <a href="${pageContext.request.contextPath}/admin/users/create"
                           class="btn btn-sm btn-success">
                            <i class="fas fa-plus me-1"></i> Add User
                        </a>
                    </div>

                    <div class="table-responsive">
                        <table class="table table-striped">
                            <thead>
                            <tr>
                                <th>ID</th>
                                <th>Name</th>
                                <th>Email</th>
                                <th>Role</th>
                                <th>Actions</th>
                            </tr>
                            </thead>
                            <tbody>
                            <c:forEach items="${users}" var="u">
                                <tr>
                                    <td>${u.id}</td>
                                    <td>${u.name}</td>
                                    <td>${u.email}</td>
                                    <td>${u.role}</td>
                                    <td>
                                        <a href="${pageContext.request.contextPath}/admin/users/edit?id=${u.id}"
                                           class="btn btn-sm btn-primary">
                                            <i class="fas fa-edit"></i>
                                        </a>
                                        <a href="${pageContext.request.contextPath}/admin/users/delete?id=${u.id}"
                                           class="btn btn-sm btn-danger"
                                           onclick="return confirm('Are you sure?')">
                                            <i class="fas fa-trash"></i>
                                        </a>
                                    </td>
                                </tr>
                            </c:forEach>
                            </tbody>
                        </table>
                    </div>
                </div>
            </c:if>
        </div>
    </div>
</div>

<!-- Bootstrap 5 JS Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
<!-- Custom JS -->
<script>
    // Password confirmation validation
    document.querySelector('form[action*="change-password"]').addEventListener('submit', function (e) {
        const newPass = this.querySelector('input[name="newPassword"]').value;
        const confirmPass = this.querySelector('input[name="confirmPassword"]').value;

        if (newPass !== confirmPass) {
            e.preventDefault();
            alert('New passwords do not match!');
        }
    });
</script>
</body>
</html>