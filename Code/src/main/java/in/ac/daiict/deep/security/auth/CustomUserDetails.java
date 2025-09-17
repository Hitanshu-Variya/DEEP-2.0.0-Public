package in.ac.daiict.deep.security.auth;

import in.ac.daiict.deep.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class CustomUserDetails implements UserDetails {
    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority(user.getRole()));
    }

    public String getPassword(){
        return user.getPassword();
    }

    public String getUsername(){
        return user.getUsername();
    }

    public String getEmail(){
        return user.getEmail();
    }
}
