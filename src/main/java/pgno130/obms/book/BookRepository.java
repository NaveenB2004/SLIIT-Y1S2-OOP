package pgno130.obms.book;
import org.springframework.stereotype.Repository;

import java.io.*;
import java.util.*;

@Repository
public class BookRepository {
    private static final String FILE_PATH = "books.txt";


    public List<Book> findAll() {
        List<Book> books = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(";", 6);
                if (parts.length == 6) {
                    Book book = new Book() ;
                    book.setId(parts[0]);
                    book.setTitle(parts[1]);
                    book.setCategory(parts[2]);
                    book.setPrice(Double.parseDouble(parts[3]));
                    book.setRating(parts[4]);
                    book.setQuantity(Integer.parseInt(parts[5]));
                    books.add(book);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return books;
    }

    public void save(Book book) {
        List<Book> books = findAll();
        books.removeIf(b -> b.getId().equals(book.getId()));
        books.add(book);
        writeToFile(books);
    }

    public Book findById(String id) {
        return findAll().stream()
                .filter(b -> b.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    public void delete(String id) {
        List<Book> books = findAll();
        books.removeIf(b -> b.getId().equals(id));
        writeToFile(books);
    }

    private void writeToFile(List<Book> books) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (Book book : books) {
                bw.write(
                        book.getId() + ";" +
                                book.getTitle() + ";" +
                                book.getCategory() + ";" +
                                book.getPrice() + ";" +
                                book.getRating() + ";" +
                                book.getQuantity()
                );
                bw.newLine();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

