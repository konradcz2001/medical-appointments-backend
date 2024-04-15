package com.github.konradcz2001.medicalappointments.client;

import com.github.konradcz2001.medicalappointments.User;
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

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    Set<Review> reviews = new HashSet<>();


    void addReview(Review review) {
        reviews.add(review);
    }
    void removeReview(Review review){
        reviews.remove(review);
    }

}
