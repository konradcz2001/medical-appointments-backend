package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.Address;
import com.github.konradcz2001.medicalappointments.User;
import com.github.konradcz2001.medicalappointments.review.Review;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Client extends User {

    @Embedded
    Address address;

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    Set<Review> reviews = new HashSet<>();

    public Client(String firstName, String lastName, String email, String phoneNumber, Set<Review> reviews) {
        super(firstName, lastName, email, phoneNumber);
    }

    void addReview(Review review) {
        reviews.add(review);
    }
    void removeReview(Review review){
        reviews.remove(review);
    }

}
