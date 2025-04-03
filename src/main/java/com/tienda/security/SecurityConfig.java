package com.tienda.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.client.registration.ClientRegistrationRepository;
import org.springframework.security.oauth2.client.registration.InMemoryClientRegistrationRepository;

@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${auth0.domain}")
    private String auth0Domain;

    @Value("${auth0.client-id}")
    private String auth0ClientId;

    @Value("${auth0.client-secret}")
    private String auth0ClientSecret;

    @Value("${auth0.callback-url}")
    private String auth0CallbackUrl;

    // Configura la seguridad para usar Auth0
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
                .antMatchers("/", "/login**", "/error**").permitAll() // Permitir el acceso a la página de login y error
                .anyRequest().authenticated() // Requiere autenticación para cualquier otra solicitud
            .and()
            .oauth2Login()  // Configuración de OAuth2 para utilizar Auth0
                .loginPage("/login")  // Especifica la URL de inicio de sesión
            .and()
            .logout()
                .logoutUrl("/logout") // URL de cierre de sesión
                .logoutSuccessUrl("/");  // Redirigir a la página de inicio después de salir
    }

    @Bean
    public ClientRegistrationRepository clientRegistrationRepository() {
        ClientRegistration clientRegistration = ClientRegistration
            .withRegistrationId("auth0")
            .clientId(auth0ClientId)
            .clientSecret(auth0ClientSecret)
            .scope("openid", "profile", "email")
            .authorizationUri("https://" + auth0Domain + "/authorize")
            .tokenUri("https://" + auth0Domain + "/oauth/token")
            .userInfoUri("https://" + auth0Domain + "/userinfo")
            .redirectUri(auth0CallbackUrl)
            // Elimina logoutUri, no es necesario para la configuración de Auth0 con Spring Security
            .build();

        return new InMemoryClientRegistrationRepository(clientRegistration);
    }
}
