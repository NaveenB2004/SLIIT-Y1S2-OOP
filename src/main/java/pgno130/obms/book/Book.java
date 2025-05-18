package pgno130.obms.book;

public class Book {
    private String title;
    private String category;
    private double price;
    private String rating; // e.g., "⭐⭐⭐⭐"
    private int quantity;
    protected String id;

    public Book() {
        this.title = "title";
        this.category = "category";
        this.price = 0.0;
        this.rating = " ";
        this.quantity = 0;
        this.id = "id";
    }

    public Book(String title, String category, double price, String rating, int quantity, String id) {
        this.title = title;
        this.category = category;
        this.price = price;
        this.rating = rating;
        this.quantity = quantity;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public double getPrice() {
        return price;
    }

    public String getRating() {
        return rating;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setRating(String rating) {
        this.rating = rating;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isInStock() {
        return quantity > 0;
    }

    public void addQuantity(int count) {
        this.quantity += count;
    }

    public void reduceQuantity(int count) {
        if (this.quantity >= count) {
            this.quantity -= count;
        }
    }

    @Override
    public String toString() {
        return title + " (" + category + ") - $" + price + ", " + rating + " - " +
                (isInStock() ? "In Stock" : "Out of Stock");
    }
}


class EBook extends Book {
    private boolean isEBook;

    public EBook(String title, String category, double price, String rating, int quantity,String id) {
        super(title, category, price, rating, quantity,id);
        this.isEBook = true;
    }

    public boolean isEBook() {
        return isEBook;
    }

    @Override
    public String toString() {
        return super.toString() + " (E-Book)";
    }
}

 class PhysicalBook extends Book {
    private boolean isPhysicalBook;

    public PhysicalBook(String title, String category, double price, String rating, int quantity,String id) {
        super(title, category, price, rating, quantity,id);
        this.isPhysicalBook = true;
    }

    public boolean isPhysicalBook() {
        return isPhysicalBook;
    }

    @Override
    public String toString() {
        return super.toString() + " (Physical Book)";
    }
}