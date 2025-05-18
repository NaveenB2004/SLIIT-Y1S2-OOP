package pgno130.obms.book;

import java.util.ArrayList;
import java.util.List;

public class BookLinkedList {
    private BookNode head;

    public BookLinkedList() {
        this.head = null;
    }

    public void addBook(Book book) {
        BookNode newNode = new BookNode(book);
        if (head == null) {
            head = newNode;
        } else {
            BookNode current = head;
            while (current.getNext() != null) {
                current = current.getNext();
            }
            current.setNext(newNode);
        }
    }

    public void removeBook(String title) {
        if (head == null) return;

        if (head.getBook().getTitle().equalsIgnoreCase(title)) {
            head = head.getNext();
            return;
        }

        BookNode current = head;
        while (current.getNext() != null &&
                !current.getNext().getBook().getTitle().equalsIgnoreCase(title)) {
            current = current.getNext();
        }

        if (current.getNext() != null) {
            current.setNext(current.getNext().getNext());
        }
    }

    public List<Book> filterBooks(String category, boolean isEBook, boolean isPhysical, double minPrice, double maxPrice) {
        List<Book> filteredBooks = new ArrayList<>();
        BookNode current = head;

        while (current != null) {
            Book book = current.getBook();

            if ((category == null || book.getCategory().equalsIgnoreCase(category)) &&
                    (isEBook == book instanceof EBook) &&
                    (isPhysical == book instanceof PhysicalBook) &&
                    book.getPrice() >= minPrice &&
                    book.getPrice() <= maxPrice) {
                filteredBooks.add(book);
            }

            current = current.getNext();
        }

        return filteredBooks;
    }

    public List<Book> searchBooks(String title) {
        List<Book> result = new ArrayList<>();
        BookNode current = head;

        while (current != null) {
            if (current.getBook().getTitle().toLowerCase().contains(title.toLowerCase())) {
                result.add(current.getBook());
            }
            current = current.getNext();
        }

        return result;
    }

    public int getBookCount() {
        int count = 0;
        BookNode current = head;

        while (current != null) {
            count++;
            current = current.getNext();
        }

        return count;
    }
}