package ca.uqtr.dmi.messagerielogic.beans;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class BeansInjector {
    @Bean
    public PasswordEncoder encoder(){
        return  new BCryptPasswordEncoder();
    }
}