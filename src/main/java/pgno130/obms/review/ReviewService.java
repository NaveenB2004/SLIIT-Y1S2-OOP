package pgno130.obms.review;

import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class ReviewService {

    private static final String FILE_PATH = "reviews.csv";

    // CSV file එකේ header: reviewId,bookId,userId,rating,comment,timestamp

    // Create (Add) Review
    public void addReview(Review review) throws IOException {
        List<Review> reviews = getAllReviews();
        reviews.add(review);
        writeAllReviews(reviews);
    }

    // Read (Get) All Reviews
    public List<Review> getAllReviews() throws IOException {
        List<Review> reviews = new ArrayList<>();

        Path path = Paths.get(FILE_PATH);
        if (!Files.exists(path)) {
            Files.createFile(path);
            // Write header line
            try (BufferedWriter writer = Files.newBufferedWriter(path)) {
                writer.write("reviewId,bookId,userId,rating,comment,timestamp");
                writer.newLine();
            }
            return reviews;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line = reader.readLine(); // skip header
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",", 6);
                if (parts.length < 6) continue;

                Review review = new Review(
                        parts[0],
                        parts[1],
                        parts[2],
                        Integer.parseInt(parts[3]),
                        parts[4],
                        LocalDateTime.parse(parts[5])
                );
                reviews.add(review);
            }
        }
        return reviews;
    }

    // Update Review
    public boolean updateReview(Review updatedReview) throws IOException {
        List<Review> reviews = getAllReviews();
        boolean found = false;
        for (int i = 0; i < reviews.size(); i++) {
            if (reviews.get(i).getReviewId().equals(updatedReview.getReviewId())) {
                reviews.set(i, updatedReview);
                found = true;
                break;
            }
        }
        if (found) {
            writeAllReviews(reviews);
        }
        return found;
    }

    // Delete Review by reviewId
    public boolean deleteReview(String reviewId) throws IOException {
        List<Review> reviews = getAllReviews();
        boolean removed = reviews.removeIf(r -> r.getReviewId().equals(reviewId));
        if (removed) {
            writeAllReviews(reviews);
        }
        return removed;
    }

    // Helper method to write all reviews to file
    private void writeAllReviews(List<Review> reviews) throws IOException {
        Path path = Paths.get(FILE_PATH);
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write("reviewId,bookId,userId,rating,comment,timestamp");
            writer.newLine();
            for (Review r : reviews) {
                String line = String.join(",",
                        r.getReviewId(),
                        r.getBookId(),
                        r.getUserId(),
                        String.valueOf(r.getRating()),
                        r.getComment().replace(",", " "), // avoid comma issues
                        r.getTimestamp().toString()
                );
                writer.write(line);
                writer.newLine();
            }
        }
    }
}
