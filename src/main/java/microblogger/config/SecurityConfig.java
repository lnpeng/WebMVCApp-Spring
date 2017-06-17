package microblogger.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;

@Configuration
@EnableWebMvcSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .antMatchers("/").authenticated()
                .antMatchers(HttpMethod.POST, "/blogs").authenticated()
                .anyRequest().permitAll()
                .and()
            .formLogin()
                .loginPage("/login")
                .and()
            .logout()
                .logoutSuccessUrl("/")
                .and()
            .rememberMe()
                .tokenValiditySeconds(2419200)
                .key("bloggerKey")
                .and()
            .httpBasic();
    }

    @Override
    protected void  configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
                .withUser("user").password("password").roles("USER").and()
                .withUser("admin").password("password").roles("USER", "ADMIN");
    }
}
