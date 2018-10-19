package uav.Security.Authentification.DataModel;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import uav.Security.Authentification.UserInfo;

import java.util.*;

public class UserPrincipal implements UserDetails {
    private UserInfo userInfo;
    private Collection<UserRole> roles;
    private Boolean isValid = true;

    public UserPrincipal() {}
    public UserPrincipal(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }
    public UserInfo getUserInfo() {
        return this.userInfo;
    }
    public Collection<UserRole> getRoles() {
        return roles;
    }
    public void setRoles(Collection<UserRole> roles) {
        this.roles = roles;
    }
    public void setIsValid(Boolean isValid) {
        this.isValid = isValid;
    }
    @Override
    public String getPassword() {
        return userInfo.getPassword();
    }
    @Override
    public String getUsername() {
        return userInfo.getFirstName();
    }
    @Override
    public Set<GrantedAuthority> getAuthorities() {
        final Set<GrantedAuthority> grantedAuthorities = new LinkedHashSet<>();
        for(UserRole role : roles) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return grantedAuthorities;
    }
    @Override
    public boolean isAccountNonExpired() {
        return isValid;
    }
    @Override
    public boolean isAccountNonLocked() {
        return isValid;
    }
    @Override
    public boolean isCredentialsNonExpired() {
        return isValid;
    }
    @Override
    public boolean isEnabled() {
        return isValid;
    }
    @Override
    public boolean equals(Object obj) {
        if(this == obj) return true;
        if(obj == null || !(obj instanceof UserPrincipal)) {
            return false;
        }
        UserPrincipal other = (UserPrincipal) obj;
        if(this.userInfo == null || other.getUserInfo() == null) return false;
        if(!this.userInfo.equals(other.getUserInfo())) return false;
        return true;
    }
}
