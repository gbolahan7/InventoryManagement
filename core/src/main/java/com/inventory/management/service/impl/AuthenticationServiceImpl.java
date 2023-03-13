package com.inventory.management.service.impl;

import com.inventory.management.auth.UserDetailsImpl;
import com.inventory.management.domain.Constants;
import com.inventory.management.domain.Role;
import com.inventory.management.domain.User;
import com.inventory.management.repository.RoleRepository;
import com.inventory.management.repository.UserRepository;
import com.inventory.management.service.AuthenticationService;
import com.inventory.management.util.JwtHelper;
import com.inventory.management.util.StartupHelper;
import com.inventory.management.validation.AuthenticationValidation;
import com.inventory.management.vo.dto.CredentialDto;
import com.inventory.management.vo.problem.CustomApiException;
import com.inventory.management.vo.problem.ValidationException;
import com.inventory.management.vo.problem.ValidatorError;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final AuthenticationManager authenticationManager;

    private final UserRepository userRepository;

    private final RoleRepository roleRepository;

    private final PasswordEncoder encoder;

    private final JwtHelper jwtHelper;
    private final AuthenticationValidation authenticationValidation;

    private static CredentialDto getLoginCredential(String jwt, UserDetailsImpl userDetails) {
        CredentialDto response = new CredentialDto();
        response.setToken(jwt);
        response.setRole(userDetails.getRoles());
        response.setUsername(userDetails.getUsername());
        response.setEmail(userDetails.getEmail());
        return response;
    }

    @Override
    public CredentialDto loginUser(CredentialDto credentials) {
        List<ValidatorError> errors = authenticationValidation.validateLoginUser(credentials);
        if(!CollectionUtils.isEmpty(errors)) throw new ValidationException(errors);

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(credentials.getUsername(), credentials.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtHelper.generateJwtToken(authentication);

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        return getLoginCredential(jwt, userDetails);
    }

    @Override
    public CredentialDto registerUser(CredentialDto credentials) {
        List<ValidatorError> errors = authenticationValidation.validateRegisterUser(credentials);
        if(!CollectionUtils.isEmpty(errors)) throw new ValidationException(errors);

        User user = new User(credentials.getUsername(), credentials.getEmail(), encoder.encode(credentials.getPassword()));

        Set<Role> roles = new HashSet<>();
        Long allUsersCount = userRepository.countAllUsers();
        Role userRole = (allUsersCount == null || allUsersCount == 0) ? getRoleByName(StartupHelper.SUPER_ADMIN) : getRoleByName(StartupHelper.GUEST);
        roles.add(userRole);

        setUser(user, roles);
        user = userRepository.save(user);

        return getLoginCredential(null, UserDetailsImpl.build(user));
    }

    private void setUser(User user, Set<Role> roles) {
        user.setRoles(roles);
        user.setAccess(Constants.Access.NOT_LOCKED);
        user.setStatus(Constants.Status.ACTIVE);
    }

    private Role getRoleByName(String roleName) {
        return roleRepository.findByName(roleName).orElseThrow(() -> new CustomApiException("custom.error.role.not.exist", new Object[0]));
    }

}
