package org.example.blogproject.jwt.token;

import java.util.Collection;

import org.example.blogproject.security.CustomUserDetails;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

public class JwtAuthenticationToken extends AbstractAuthenticationToken {
    private String token;
    private Object principal;
    private Object credentials;

    public JwtAuthenticationToken(Collection<? extends GrantedAuthority> authorities,
                                  Object principal, Object credentials) {
        super(authorities);
        this.principal = principal;
        this.credentials = credentials;
        this.setAuthenticated(true);
    }

    public JwtAuthenticationToken(String token) {
        super(null);
        this.token = token;
        this.setAuthenticated(false);
    }

    @Override
    public Object getCredentials() {
        return this.credentials;
    }

    @Override
    public Object getPrincipal() {
        return (CustomUserDetails)this.principal;
    }
}