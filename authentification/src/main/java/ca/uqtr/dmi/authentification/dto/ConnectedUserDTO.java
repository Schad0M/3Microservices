package ca.uqtr.dmi.authentification.dto;

import ca.uqtr.dmi.authentification.models.UserCredential;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
public class ConnectedUserDTO implements UserDetails {

    private final long id;
    private String username;
    @JsonIgnore
    private String password;
    private Collection<SimpleGrantedAuthority> authorities;
    private String firstname;
    private String lastname;
    private String email;

    private String address;
    private String mobile;

    public ConnectedUserDTO(UserCredential credential) {

        this.username = credential.getUsername();
        this.password= credential.getPassword();
        this.id = credential.getUser().getNumeroID();
        this.firstname = credential.getUser().getFirstname();
        this.lastname = credential.getUser().getLastname();
        this.email = credential.getUser().getEmail();
        this.mobile = credential.getUser().getMobile();
        this.address = credential.getUser().getAddress();
        this.authorities = credential.getRole()
                .stream()
                .map(r-> new SimpleGrantedAuthority(r.getName()))
                .toList();


    }

    public long getId() {
        return id;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
