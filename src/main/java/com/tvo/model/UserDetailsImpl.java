/**
 * 
 */
package com.tvo.model;

import com.tvo.enums.StatusActivate;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;



/**
 * @author Ace
 *
 */
public class UserDetailsImpl implements UserDetails {
	 private static final long serialVersionUID = -7206276226932033386L;
	    private User user;
	    private List<GrantedAuthority> authorities;

	    public UserDetailsImpl(final User user, final List<GrantedAuthority> authorities) {
	        this.user = user;
	        this.authorities = authorities;
	    }

	    public User getUser() {
	        return user;
	    }

	    public void setUser(final User user) {
	        this.user = user;
	    }

	    public void setAuthorities(final List<GrantedAuthority> authorities) {
	        this.authorities = authorities;
	    }

	    @Override
	    public Collection<? extends GrantedAuthority> getAuthorities() {
	        return authorities;
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

	    // check user status (Activate/Deactivate)
	    @Override
	    public boolean isEnabled() {
	    	String userStatus = user.getStatus();
			return userStatus.contentEquals(StatusActivate.STATUS_ACTIVATED.getStatus());
		}

	    @Override
	    public String getPassword() {
	        return user.getPassword();
	    }

	    @Override
	    public String getUsername() {
	        return user.getUserName();
	    }
}
