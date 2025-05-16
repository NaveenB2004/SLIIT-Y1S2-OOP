package pgno130.obms.user;

import java.time.LocalDateTime;

public class User {
    private String id;
    private String name;
    private String email;
    private String password;
    private String address;
    private String phone;
    private LocalDateTime createdAt;
    private String useOrAdmin;
    private String rememberMe;

    //Default Constructor
    public User() {
        this.id = "A000";
        this.name = "Sample User";
        this.email = "sample@gmail.com";
        this.password = "password";
        this.address = "Address";
        this.phone = "+94 123 456 789";
        this.createdAt = null;
        this.useOrAdmin = "true";
        this.rememberMe = "false";
    }

    //Parameterized Constructor
    public User(String name, String email, String password, String address, String phone) {
        this.name = name;
        this.email = email;
        this.password = password;
        this.address = address;
        this.phone = phone;
        this.createdAt = LocalDateTime.now();
        this.useOrAdmin = "User";
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getUseOrAdmin() {
        return useOrAdmin;
    }

    public void setUseOrAdmin(String useOrAdmin) {
        this.useOrAdmin = useOrAdmin;
    }

    public String getRememberMe() {
        return rememberMe;
    }

    public void setRememberMe(String rememberMe) {
        this.rememberMe = rememberMe;
    }

    //Business method
    public boolean isAdmin() {
        return "Admin".equals(useOrAdmin);
    }
}
