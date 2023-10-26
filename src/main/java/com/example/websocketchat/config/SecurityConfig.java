package com.example.websocketchat.config;


import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;


@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Bean
    public BCryptPasswordEncoder passwordEncoder() {
        // PasswordEncoderFactories.createDelegatingPasswordEncoder();
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/fonts/**", "/img/**", "/js/**", "/login").permitAll()
                .antMatchers("/logout").permitAll()
                .anyRequest().authenticated()
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/process_login")
                .defaultSuccessUrl("/")
                //.failureUrl("/login?error")
                .and()
                .logout()
                .logoutUrl("/logout");
    }

    @Bean
    public UserDetailsService userDetailsService(BCryptPasswordEncoder bCryptPasswordEncoder) {
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();
        manager.createUser(User.withUsername("Tokio")
                .password(bCryptPasswordEncoder.encode("Tokio"))
                .roles("USER", "ADMIN")
                .build());
        manager.createUser(User.withUsername("Berlin")
                .password(bCryptPasswordEncoder.encode("Berlin"))
                .roles("USER")
                .build());
        manager.createUser(User.withUsername("Nigga")
                .password(bCryptPasswordEncoder.encode("Nigga"))
                .roles("USER")
                .build());

        return manager;
    }

}


