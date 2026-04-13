package com.plotfail.plotfailbe.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.List;

/**
 * Filtro JWT che estrae il token dall'header Authorization e imposta il contesto di sicurezza.
 */
@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request,
                                    @NonNull HttpServletResponse response,
                                    @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(7);
        final String username;
        try {
            username = jwtService.estraiUsername(jwt);
        } catch (Exception e) {
            SecurityContextHolder.clearContext();
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.setHeader("X-Flag-Unauthorized", "true");
            return;
        }
        if (username != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            // Estrai dettagli dal token senza accedere al DB
            final String ruolo = jwtService.estraiRuolo(jwt);
            final Long userId = jwtService.estraiUserId(jwt);
            List<SimpleGrantedAuthority> authorities = ruolo != null
                    ? List.of(new SimpleGrantedAuthority("ROLE_" + ruolo))
                    : List.of();

            UserDetails principal = new JwtUserPrincipal(userId, username, authorities);

            if (jwtService.isTokenValido(jwt, principal)) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        principal,
                        null,
                        principal.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }else {
                SecurityContextHolder.clearContext();
                // aggiungi header X-Flag-Unauthorized: true alla risposta
                // serve lato frontend in interceptor per sloggare immediatamente la sessione
                response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                response.setHeader("X-Flag-Unauthorized", "true");
                return;
            }
        }
        filterChain.doFilter(request, response);
    }
}
