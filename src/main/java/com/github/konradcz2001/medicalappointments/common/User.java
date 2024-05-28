package com.github.konradcz2001.medicalappointments.common;

import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * This code snippet represents a Java class named "User" that implements the "UserDetails" interface.
 * It is a mapped superclass with fields representing user information such as id, first name, last name, email, password, and role.
 * The class provides getter and setter methods for these fields.
 * The "User" class also overrides methods from the "UserDetails" interface to provide user authentication and authorization functionality.
 * The "getAuthorities" method returns a collection of granted authorities based on the user's role.
 * The "getPassword" method returns the user's password.
 * The "getUsername" method returns the user's email.
 * The "isAccountNonExpired", "isAccountNonLocked", "isCredentialsNonExpired", and "isEnabled" methods return true to indicate that the user's account is active and valid.
 */
@MappedSuperclass
@Getter
@Setter
@FieldDefaults(level = AccessLevel.PRIVATE)
@NoArgsConstructor
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    @Column(name = "first_name", nullable = false)
    @NotBlank(message = "First name must not be empty")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String firstName;
    @Column(name = "last_name", nullable = false)
    @NotBlank(message = "Last name must not be empty")
    @Size(max = 100, message = "Maximum length is 100 characters")
    String lastName;
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank(message = "Email must not be empty")
    @Size(max = 100, message = "Maximum length is 100 characters")
    @Email(message = "The email must be in email format")
    String email;
    @Column(name = "password", nullable = false)
    String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @NotNull(message = "Role must not be empty")
    Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
