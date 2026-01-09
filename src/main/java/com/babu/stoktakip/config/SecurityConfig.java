package com.babu.stoktakip.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authorizeHttpRequests((requests) -> requests
                // 1. Bu dosyalara HERKES erişebilir (Login sayfası, CSS, JS)
                .requestMatchers("/login.html", "/css/**", "/js/**", "/images/**").permitAll()
                // 2. Geri kalan her yer için GİRİŞ YAPMAK ZORUNLUDUR
                .anyRequest().authenticated()
            )
            .formLogin((form) -> form
                // Bizim özel login sayfamızın adresi:
                .loginPage("/login.html")
                // Formun verileri nereye göndereceği (HTML'deki action="/login" ile aynı olmalı)
                .loginProcessingUrl("/login")
                // Giriş başarılı olursa ana sayfaya git
                .defaultSuccessUrl("/", true)
                .permitAll()
            )
            .logout((logout) -> logout.permitAll())
            // CSRF korumasını şimdilik kapatıyoruz (API testleri kolay olsun diye)
            .csrf(csrf -> csrf.disable()); 

        return http.build();
    }

    @Bean
    public UserDetailsService userDetailsService() {
        // BURADA KULLANICIYI TANIMLIYORUZ
        UserDetails user = User.withDefaultPasswordEncoder()
            .username("babu")  // Kullanıcı Adın
            .password("1234")  // Şifren
            .roles("USER")
            .build();

        return new InMemoryUserDetailsManager(user);
    }
}