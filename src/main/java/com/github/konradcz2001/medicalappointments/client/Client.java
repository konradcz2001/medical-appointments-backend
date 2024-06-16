package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.common.User;
import com.github.konradcz2001.medicalappointments.review.Review;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.ArrayList;
import java.util.List;


/**
 * This code snippet represents a Java class called Client, which extends the User class.
 * It is used in the context of a medical appointments application.
 * <p>
 * The Client class has the following properties:
 * - id: a unique identifier for the client
 * - firstName: the first name of the client
 * - lastName: the last name of the client
 * - email: the email address of the client
 * - password: the password of the client
 * - role: the role of the client
 * - reviews: a list of reviews associated with the client
 * <p>
 * The Client class provides the following methods:
 * - addReview(Review review): adds a review to the list of reviews
 * - removeReview(Review review): removes a review from the list of reviews
 * <p>
 * Note: The Client class inherits the methods and properties from the User class.
 */
@Entity
@Table(name = "clients")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true)
    List<Review> reviews = new ArrayList<>();


    void addReview(Review review) {
        reviews.add(review);
    }
    void removeReview(Review review){
        reviews.remove(review);
    }

}
