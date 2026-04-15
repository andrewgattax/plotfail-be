package com.plotfail.plotfailbe.model;

import jakarta.persistence.*;
import lombok.Data;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Utente implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;

    @Column(nullable = false)
    private String passwordHash;

    @OneToMany(mappedBy = "user")
    private Set<SalvataggioTemplate> templatesSalvati;

    @OneToMany(mappedBy = "autore")
    private Set<Storia> storieCreate;

    private Instant createdAt = Instant.now();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }

    public boolean equals(Utente u) {
        return u.getUsername().equals(this.getUsername());
    }

    @Override
    public @Nullable String getPassword() {
        return passwordHash;
    }
}
