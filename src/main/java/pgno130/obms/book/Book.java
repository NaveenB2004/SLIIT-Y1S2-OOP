package pgno130.obms.book;

import lombok.Data;

@Data
public final class Book {
    private Long id;
    private String title;
    private String author;
    private String description;
    private String image;
    private int price;
    private int rating;
}
