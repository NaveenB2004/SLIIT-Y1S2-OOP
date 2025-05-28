package pgno130.obms.book;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.io.IOException;
import java.util.Iterator;

@Controller
@RequestMapping("/book")
@RequiredArgsConstructor
public final class BookController {
    private final BookService bookService;

    @GetMapping("")
    public String getAllBooks(Model model,
                              @RequestParam(required = false) String sortBy) throws IOException {
        LinkedList<Book> books = bookService.getBooks();
        Book[] bookArr = new Book[books.getNodeCount()];
        Iterator<Book> iterator = books.iterator();
        for (int i = 0; i < books.getNodeCount(); i++) bookArr[i] = iterator.next();
        if (sortBy != null) QuickSort.sort(bookArr, 0, bookArr.length - 1, sortBy.equals("price"));
        for (Book book : bookArr) System.out.println(book.getPrice());
        model.addAttribute("books", bookArr);
        return "book/all-book";
    }

    @GetMapping("/get-book")
    public String getBookById(Model model,
                              @RequestParam int id,
                              @RequestParam boolean forEdit) throws IOException {
        model.addAttribute("book", bookService.getBook(id));
        return forEdit ? "book/edit-book" : "book/one-book";
    }

    @GetMapping("/delete-book")
    public String deleteBookById(@RequestParam int id) throws IOException {
        bookService.deleteBook(id);
        return "redirect:/book";
    }

    @PostMapping("/update-book")
    public String updateBook(@RequestParam(required = false) Long id,
                             @RequestParam String title,
                             @RequestParam String author,
                             @RequestParam String description,
                             @RequestParam String image,
                             @RequestParam int price,
                             @RequestParam int rating) throws IOException {
        Book book = new Book();
        book.setId(id);
        book.setTitle(title);
        book.setAuthor(author);
        book.setDescription(description);
        book.setImage(image);
        book.setPrice(price);
        book.setRating(rating);
        if (id != null) {
            bookService.updateBook(book);
        } else {
            bookService.addBook(book);
        }
        return "redirect:/book";
    }
}
