package com.library.Library.service.userdetails;

import com.library.Library.model.User;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

@Builder
@AllArgsConstructor
public class CustomUserDetails implements UserDetails {
    private final String password;
    private final Collection<? extends GrantedAuthority> authorities;
    private final String username;
    @Getter
    private final Long id;
    private final boolean isEnabled;
    private final boolean credentialsNotExpired;
    private final boolean accountNotExpired;
    private final boolean accountNotLocked;

    public CustomUserDetails(User user) {
        this.id = user.getId();
        this.username = user.getUsername();
        this.password = user.getPassword();
        this.authorities = user.getRoles();
        this.isEnabled = true;
        this.credentialsNotExpired = true;
        this.accountNotExpired = true;
        this.accountNotLocked = true;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNotExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNotLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNotExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.isEnabled;
    }
}
