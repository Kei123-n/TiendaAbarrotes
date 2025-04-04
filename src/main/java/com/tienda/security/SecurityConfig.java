package com.tienda.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    // Configura la seguridad para usar un login básico
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/login**", "/error**").permitAll()  // Permitir acceso al login y error
                .anyRequest().authenticated()  // Requiere autenticación para cualquier otra solicitud
            .and()
            .formLogin()
                .loginPage("/login") // Especifica la URL de inicio de sesión
                .defaultSuccessUrl("/productos", true)  // Redirige después del login a la página de productos
                .permitAll() // Permite el acceso a la página de login
            .and()
            .logout()
                .logoutUrl("/logout")
                .logoutSuccessUrl("/"); // Redirigir a la página de inicio después de salir

        return http.build();
    }

    // Eliminar el uso de @Value y las variables no necesarias
    @Bean
    public UserDetailsService userDetailsService() {
        return new InMemoryUserDetailsManager(
            User.withUsername("admin") // Usuario predeterminado
                .password(passwordEncoder().encode("admin123")) // Contraseña cifrada
                .roles("USER")  // Rol del usuario
                .build()
        );
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // Para cifrar la contraseña
    }
}
