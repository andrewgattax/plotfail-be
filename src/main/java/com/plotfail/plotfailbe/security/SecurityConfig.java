package com.plotfail.plotfailbe.security;

import com.plotfail.plotfailbe.repo.UtenteRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Configurazione di Spring Security con JWT.
 */
@Configuration
@EnableMethodSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final UtenteRepo utenteRepository;
    @Autowired
    private CustomUserDetailsService userDetailsService;

    /**
     * Parametri CORS configurabili dinamicamente tramite application.properties o variabili d'ambiente.
     * Esempi di proprietà:
     * - app.cors.allowed-origins=https://example.com,https://app.example.com
     * - app.cors.allowed-origin-patterns=https://*.example.com
     * - app.cors.allowed-methods=GET,POST,PUT,DELETE,OPTIONS
     * - app.cors.allowed-headers=*
     * - app.cors.allow-credentials=true
     */
    @Value("${app.cors.allowed-origins:*}")
    private String corsAllowedOrigins;

    @Value("${app.cors.allowed-origin-patterns:}")
    private String corsAllowedOriginPatterns;

    @Value("${app.cors.allowed-methods:GET,POST,PUT,DELETE,OPTIONS}")
    private String corsAllowedMethods;

    @Value("${app.cors.allowed-headers:*}")
    private String corsAllowedHeaders;

    @Value("${app.cors.allow-credentials:true}")
    private boolean corsAllowCredentials;


    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
        provider.setPasswordEncoder(passwordEncoder());
        return provider;
    }

    /**
     * Password encoder BCrypt.
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * AuthenticationManager esposto dal contesto di Spring.
     */
    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration configuration) throws Exception {
        return configuration.getAuthenticationManager();
    }

    /**
     * Configurazione della catena di filtri di sicurezza.
     */
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf(AbstractHttpConfigurer::disable)
                .cors(withDefaults -> {})
                .sessionManagement(
                        sm -> sm.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                )
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/utente/**").permitAll()
                        .requestMatchers("/v3/api-docs/**", "/swagger-ui/**", "/swagger-ui.html").permitAll()
                        .requestMatchers("/test/files/**").permitAll()
                        .requestMatchers("/ws/**").permitAll()
                        .anyRequest().authenticated()
                )
                .authenticationProvider(authenticationProvider())
                .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);
        return http.build();
    }

    /**
     * Configura la sorgente CORS in modo dinamico usando le proprietà applicative/ambientali.
     * Logica principale:
     * - Se sono definiti "allowed-origin-patterns" usa i pattern (es. https://*.example.com).
     * - Se gli origins sono "*" e allow-credentials=true, passa a allowedOriginPatterns con "*" (necessario con credenziali).
     * - Altrimenti usa l'elenco di origins esatti.
     * - Metodi e header sono letti come CSV; il valore "*" abilita tutti.
     */
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();

        // Origins / Origin Patterns
        List<String> originPatternsList = parseCsv(corsAllowedOriginPatterns);
        List<String> originsList = parseCsv(corsAllowedOrigins);
        boolean hasPatterns = !originPatternsList.isEmpty();
        boolean wildcardOrigin = originsList.size() == 1 && "*".equals(originsList.get(0));

        if (hasPatterns) {
            config.setAllowedOriginPatterns(originPatternsList);
        } else if (wildcardOrigin && corsAllowCredentials) {
            // With credentials=true, use patterns when wildcard
            config.setAllowedOriginPatterns(List.of("*"));
        } else {
            config.setAllowedOrigins(originsList.isEmpty() ? List.of("*") : originsList);
        }

        // Methods
        List<String> methodsList = parseCsv(corsAllowedMethods);
        if (methodsList.size() == 1 && "*".equals(methodsList.get(0))) {
            config.setAllowedMethods(List.of("*"));
        } else {
            config.setAllowedMethods(methodsList);
        }

        // Headers
        List<String> headersList = parseCsv(corsAllowedHeaders);
        if (headersList.size() == 1 && "*".equals(headersList.get(0))) {
            config.setAllowedHeaders(List.of("*"));
        } else {
            config.setAllowedHeaders(headersList);
        }

        config.setAllowCredentials(corsAllowCredentials);

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);
        return source;
    }

    /**
     * Converte una stringa CSV in una lista di stringhe, rimuovendo spazi e voci vuote.
     * Esempio: "a, b , ,c" -> ["a", "b", "c"]. Restituisce lista vuota se null.
     */
    private List<String> parseCsv(String value) {
        if (value == null) return List.of();
        List<String> out = Arrays.stream(value.split(","))
                .map(String::trim)
                .filter(s -> !s.isEmpty())
                .collect(Collectors.toList());
        return out;
    }
}