package pgno130.obms.book;

import com.google.gson.Gson;
import org.springframework.stereotype.Repository;

import java.io.*;

@Repository
public final class BookService {
    private static LinkedList<Book> books;
    private static long bookId = 1;
    private static final File DATA_FILE = new File("Books.txt");

    public LinkedList<Book> getBooks() throws IOException {
        synchronized (BookService.class) {
            if (books == null) books = new LinkedList<>();
            if (!DATA_FILE.exists()) return books;
            try (var reader = new BufferedReader(new FileReader(DATA_FILE))) {
                String line;
                var gson = new Gson();
                while ((line = reader.readLine()) != null) {
                    Book book = gson.fromJson(line, Book.class);
                    bookId = book.getId();
                    books.add(book);
                }
                bookId++;
            }
            return books;
        }
    }

    public Book getBook(long id) throws IOException {
        if (books == null) getBooks();
        for (Book book : books) {
            if (book.getId() == id) {
                return book;
            }
        }
        return null;
    }

    public void addBook(Book book) throws IOException {
        if (books == null) getBooks();
        book.setId(bookId++);
        books.add(book);
        save();
    }

    public void updateBook(Book book) throws IOException {
        if (books == null) getBooks();
        for (Book bookX : books) {
            if (bookX.getId() == book.getId()) {
                bookX.setTitle(book.getTitle());
                bookX.setAuthor(book.getAuthor());
                bookX.setDescription(book.getDescription());
                bookX.setImage(book.getImage());
                bookX.setPrice(book.getPrice());
                bookX.setRating(book.getRating());
                save();
                return;
            }
        }
    }

    public void deleteBook(long id) throws IOException {
        if (books == null) getBooks();
        for (Book book : books) {
            if (book.getId() == id) {
                books.remove(book);
                save();
                return;
            }
        }
    }

    private void save() throws IOException {
        if (books == null) getBooks();
        synchronized (BookService.class) {
            try (var writer = new PrintWriter(DATA_FILE)) {
                var gson = new Gson();
                for (Book book : books) {
                    writer.println(gson.toJson(book));
                }
            }
        }
    }
}
