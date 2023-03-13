package com.inventory.management.auth;

import com.inventory.management.domain.Constants;
import com.inventory.management.domain.Role;
import com.inventory.management.domain.User;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.security.Principal;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Data
public class UserDetailsImpl implements UserDetails, Principal {

	private final User user;

	private UserDetailsImpl(User user) {
		this.user = user;
	}

	public static UserDetailsImpl build(User user) {
		return new UserDetailsImpl(user);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream()
				.flatMap(role -> role.getPrivileges().stream())
				.map(SimpleGrantedAuthority::new)
				.collect(Collectors.toSet());
	}

	public List<String> getGroup() {
		return user.getRoles().stream().map(Role::getName).collect(Collectors.toList());
	}

	public String getEmail() {
		return user.getEmail();
	}

	public Set<String> getRoles() {
		return user.getRoles().stream().map(Role::getName).collect(Collectors.toUnmodifiableSet());
	}

	@Override
	public String getPassword() {
		return user.getPassword();
	}

	@Override
	public String getUsername() {
		return user.getUsername();
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return user.getAccess().getValue().equals(Constants.Access.NOT_LOCKED.getValue());
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return user.getStatus().getValue().equals(Constants.Status.ACTIVE.getValue());
	}

	@Override
	public String getName() {
		return user.getUsername();
	}
}
