package pgno130.obms.book;

import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;
import java.util.List;
import java.util.UUID;
import org.springframework.web.bind.annotation.*;
import jakarta.annotation.PostConstruct;




@RestController
@RequestMapping("/books")
public class BookController {
    private BookLinkedList bookList = new BookLinkedList();

    @PostConstruct
    public void init() {
        // Adding some default books with unique IDs
        bookList.addBook(new EBook("Dune", "Fiction", 9.00, "⭐⭐⭐⭐⭐", 5, UUID.randomUUID().toString()));
        bookList.addBook(new PhysicalBook("1984", "Fiction", 10.00, "⭐⭐⭐⭐⭐",2 , UUID.randomUUID().toString()));
    }

    @GetMapping
    public List<Book> getBooks() {
        return bookList.filterBooks(null, false, false, 0, Double.MAX_VALUE);
    }

    @PostMapping
    public String addBook(@RequestBody Book book) {
        if (book.getId() == null || book.getId().isEmpty()) {
            book.setId(UUID.randomUUID().toString());  // generate id if missing
        }
        bookList.addBook(book);
        return "Book added successfully!";
    }

    @DeleteMapping("/{title}")
    public String removeBook(@PathVariable String title) {
        bookList.removeBook(title);
        return "Book removed successfully!";
    }

    @GetMapping("/search")
    public List<Book> searchBooks(@RequestParam String title) {
        return bookList.searchBooks(title);
    }

    @GetMapping("/filter")
    public List<Book> filterBooks(@RequestParam(required = false) String category,
                                  @RequestParam(required = false, defaultValue = "false") boolean isEBook,
                                  @RequestParam(required = false, defaultValue = "false") boolean isPhysical,
                                  @RequestParam(required = false, defaultValue = "0") double minPrice,
                                  @RequestParam(required = false, defaultValue = "1000000") double maxPrice) {
        return bookList.filterBooks(category, isEBook, isPhysical, minPrice, maxPrice);
    }

    @GetMapping("/count")
    public String getBookCount() {
        return "Total books: " + bookList.getBookCount();
    }
}
