package com.github.konradcz2001.medicalappointments.common;

import com.github.konradcz2001.medicalappointments.security.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
    @NotBlank
    String firstName;
    @Column(name = "last_name", nullable = false)
    @NotBlank
    String lastName;
    @Column(name = "email", unique = true, nullable = false)
    @NotBlank
    @Email
    String email;
    @Column(name = "password", nullable = false)
    @NotBlank
    @Pattern(regexp = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d).{8,}$", message = "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, and one digit")
    String password;
    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    @NotNull
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
