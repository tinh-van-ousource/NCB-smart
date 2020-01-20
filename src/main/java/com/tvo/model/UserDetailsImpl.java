/**
 * 
 */
package com.tvo.model;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;



/**
 * @author Ace
 *
 */
public class UserDetailsImpl implements UserDetails {
	 private static final long serialVersionUID = -7206276226932033386L;
	    private String userName;
	    private List<GrantedAuthority> authorities;
	    private Date creationDate;
	    
	    public String getUserName() {
			return userName;
		}

		public void setUserName(String userName) {
			this.userName = userName;
		}

		public Date getCreationDate() {
			return creationDate;
		}

		public void setCreationDate(Date creationDate) {
			this.creationDate = creationDate;
		}

		public UserDetailsImpl(String userName, Date creationDate, List<GrantedAuthority> authorities) {
	        this.userName = userName;
	        this.creationDate = creationDate;
	        this.authorities = authorities;
	    }

	    public UserDetailsImpl(final String userName, final List<GrantedAuthority> authorities) {
	        this.userName = userName;
	        this.authorities = authorities;
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

		@Override
		public String getPassword() {
			return null;
		}

		@Override
		public String getUsername() {
			return userName;
		}

		@Override
		public boolean isEnabled() {
			return true;
		}

}
