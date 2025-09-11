package com.example.Enotes.config.security;

import com.example.Enotes.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {

    private User user;  //User form entity class

    public CustomUserDetails(User user) {
        super();
        this.user = user;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Returns roles/permissions granted to the user.

        List<SimpleGrantedAuthority> autority = new ArrayList<>();
        user.getRoles().forEach(c -> autority.add(
                new SimpleGrantedAuthority("ROLE_"+c.getName())));  // ROLE_ADMIN
        return autority;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public String getPassword() {
//        Returns the hashed password stored in your database.
//        Used during authentication (login check).
//        Spring Security matches this with the password provided at login.
        return user.getPassword();
    }

    @Override
    public String getUsername() {
//        Returns the unique identifier of the user.
        return user.getEmail();
    }

    @Override
    public boolean isAccountNonExpired() {
//        Controls whether the account is still valid (not expired).
//        If false, the user cannot log in.
//        Example use case: disabling old trial accounts.
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
//        Determines if the account is locked (e.g., too many failed login attempts).
//        If false, login is blocked.
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
//        Checks if the password/credentials are expired.
//        Some systems force users to reset their password after X days.
        return true;
    }

    @Override
    public boolean isEnabled() {
//        Controls whether the account is active.
//        If false, login is rejected.
//        Often mapped to a field like user.isActive.
        return true;
    }
}
