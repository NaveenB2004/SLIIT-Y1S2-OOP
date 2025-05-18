package pgno130.obms.review;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    // List all reviews
    @GetMapping
    public String getAllReviews(Model model) throws IOException {
        List<Review> reviews = reviewService.getAllReviews();
        model.addAttribute("reviews", reviews);
        return "reviews"; // reviews.html (Thymeleaf template)
    }

    // Show form to add a new review
    @GetMapping("/new")
    public String showAddReviewForm(Model model) {
        model.addAttribute("review", new Review(null, null, null, 0, "", LocalDateTime.now()));
        return "add-review"; // add-review.html
    }

    // Handle form submission to create a review
    @PostMapping
    public String addReview(@ModelAttribute Review review) throws IOException {
        review.setReviewId(UUID.randomUUID().toString());
        review.setTimestamp(LocalDateTime.now());
        reviewService.addReview(review);
        return "redirect:/reviews";
    }

    // Show form to edit a review
    @GetMapping("/edit/{id}")
    public String showEditForm(@PathVariable("id") String id, Model model) throws IOException {
        List<Review> reviews = reviewService.getAllReviews();
        Review reviewToEdit = reviews.stream()
                .filter(r -> r.getReviewId().equals(id))
                .findFirst()
                .orElse(null);
        if (reviewToEdit == null) {
            return "redirect:/reviews";
        }
        model.addAttribute("review", reviewToEdit);
        return "edit-review"; // edit-review.html
    }

    // Handle form submission to update review
    @PostMapping("/update")
    public String updateReview(@ModelAttribute Review review) throws IOException {
        review.setTimestamp(LocalDateTime.now());
        reviewService.updateReview(review);
        return "redirect:/reviews";
    }

    // Delete a review
    @GetMapping("/delete/{id}")
    public String deleteReview(@PathVariable("id") String id) throws IOException {
        reviewService.deleteReview(id);
        return "redirect:/reviews";
    }
}
