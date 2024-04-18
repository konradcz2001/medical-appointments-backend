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
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "clients")
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class Client extends User {

    @OneToMany(mappedBy = "client", cascade = CascadeType.ALL)
    List<Review> reviews = new ArrayList<>();


    void addReview(Review review) {
        reviews.add(review);
    }
    void removeReview(Review review){
        reviews.remove(review);
    }

}
