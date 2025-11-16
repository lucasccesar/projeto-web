package br.com.bookly.Security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Autowired
    SecurityFilter securityFilter;

    @Bean
    public SecurityFilterChain securityFilterChain(final HttpSecurity http) throws Exception {
        return http
                .csrf(csrf -> csrf.disable())
                .cors(cors -> {})
                .sessionManagement(session -> session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                ))
                .authorizeHttpRequests(authorizeRequests -> authorizeRequests
                        .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/auth/register").permitAll()
                        //Users
                        .requestMatchers(HttpMethod.GET, "/api/users").permitAll()
                        .requestMatchers(HttpMethod.PUT, "/api/users/").hasRole("ADMINISTRATOR")

                        //Author
                        .requestMatchers(HttpMethod.POST, "/api/authors").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.GET, "/api/authors/**").hasRole("ADMINISTRATOR") //ID
                        .requestMatchers(HttpMethod.GET, "/api/authors/name").hasRole("CLIENT") //Nome
                        .requestMatchers(HttpMethod.GET, "/api/authors").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.PUT, "/api/authors/*").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.DELETE, "/api/authors/*").hasRole("ADMINISTRATOR")

                        //ClubMessage
                        .requestMatchers(HttpMethod.GET, "/api/clubmessage").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.GET, "/api/clubmessage/*").hasRole("CLIENT") //Id
                        .requestMatchers(HttpMethod.GET, "/api/clubmessage/user/*").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.GET, "/api/clubmessage/club/*").hasRole("ADMINISTRATOR")
                        .requestMatchers(HttpMethod.POST, "/api/clubmessage").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/api/clubmessage/*").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/clubmessage/*").hasRole("CLIENT")

                        //BookClub
                        .requestMatchers(HttpMethod.GET, "/api/bookclub").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.GET, "/api/bookclub/*").hasRole("ADMINISTRATOR") //Id
                        .requestMatchers(HttpMethod.GET, "/api/bookclub/name/*").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.POST, "/api/bookclub").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.PUT, "/api/bookclub/*").hasRole("CLIENT")
                        .requestMatchers(HttpMethod.DELETE, "/api/bookclub/*").hasRole("CLIENT")


                        .anyRequest().authenticated()
                )
                .addFilterBefore(securityFilter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();

        configuration.setAllowedOrigins(List.of(
              "http://localhost"
        ));
        configuration.setAllowedMethods(List.of(
                "GET", "POST", "PUT", "DELETE"
        ));
        configuration.setAllowedHeaders(List.of("*"));
        configuration.setAllowCredentials(true);

     UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
     source.registerCorsConfiguration("/**", configuration);
     return source;
    }
}
