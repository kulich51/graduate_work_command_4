package ru.skypro.homework;

import org.postgresql.ds.PGSimpleDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Configuration
public class WebSecurityConfig {

    @Value("${spring.datasource.url}")
    private String databaseUrl;

    @Value("${spring.datasource.username}")
    private String databaseUser;

    @Value("${spring.datasource.password}")
    private String databaseUserPassword;

    private static final String[] AUTH_WHITELIST = {
            "/swagger-resources/**",
            "/swagger-ui.html",
            "/v3/api-docs",
            "/webjars/**",
            "/login", "/register"
    };

    @Bean
    public InMemoryUserDetailsManager userDetailsService() {
        UserDetails user = User.withDefaultPasswordEncoder()
                .username("user@gmail.com")
                .password("password")
                .roles("USER")
                .build();
        return new InMemoryUserDetailsManager(user);
    }

    @Bean
    @Primary
    public JdbcUserDetailsManager jdbcDetailsService() {

        PGSimpleDataSource dbSource = new PGSimpleDataSource();

        // Смотри документацию к методу setServerNames. Если serverNames == null, то по умолчанию сервер localhost
        dbSource.setServerNames(null);
        dbSource.setDatabaseName(getDbName(databaseUrl));
        dbSource.setUser(databaseUser);
        dbSource.setPassword(databaseUserPassword);
        return new JdbcUserDetailsManager(dbSource);
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .csrf().disable()
                .authorizeHttpRequests((authz) ->
                        authz
                                .mvcMatchers(AUTH_WHITELIST).permitAll()
//                                .mvcMatchers("/ads/**", "/users/**").authenticated()

                )
                .cors().disable()
                .httpBasic(withDefaults());
        return http.build();
    }

    private String getDbName(String datasource) {

        return datasource.substring(datasource.lastIndexOf("/") + 1);
    }

}

