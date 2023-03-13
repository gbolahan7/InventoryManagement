package com.inventory.management.validation;

import com.inventory.management.annotation.Validate;
import com.inventory.management.repository.UserRepository;
import com.inventory.management.vo.dto.CredentialDto;
import com.inventory.management.vo.problem.ValidationBuilder;
import com.inventory.management.vo.problem.ValidatorError;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.EmailValidator;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

@Validate
@RequiredArgsConstructor
public class AuthenticationValidation {

    private final UserRepository userRepository;

    public List<ValidatorError> validateLoginUser(CredentialDto credentials) {
        ValidationBuilder builder  = new ValidationBuilder();
        if(Objects.isNull(credentials))
            builder.addError("validation.error.credential.empty");
        else{
            if(!StringUtils.hasText(credentials.getUsername()))
                builder.addError("validation.error.credential.username.empty");
            if(!StringUtils.hasText(credentials.getPassword()))
                builder.addError("validation.error.credential.password.empty");
        }
        return builder.build();
    }

    public List<ValidatorError> validateRegisterUser(CredentialDto credentials) {
        ValidationBuilder builder  = new ValidationBuilder();
        if(Objects.isNull(credentials))
            builder.addError("validation.error.credential.empty");
        else{
            if(!StringUtils.hasText(credentials.getUsername()))
                builder.addError("validation.error.credential.username.empty");
            if(!StringUtils.hasText(credentials.getPassword()))
                builder.addError("validation.error.credential.password.empty");
            if(!StringUtils.hasText(credentials.getEmail()) || !EmailValidator.getInstance().isValid(credentials.getEmail()))
                builder.addError("validation.error.credential.email.invalid");
        }
        if(builder.isClean()) {
            if(userRepository.existsByUsernameOrEmail(credentials.getUsername(), credentials.getEmail()))
                builder.addError("validation.error.credential.email.or.username");
        }
        return builder.build();
    }
}
