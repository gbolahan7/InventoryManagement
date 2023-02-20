package com.inventory.management.service;

import com.inventory.management.vo.dto.CredentialDto;

public interface AuthenticationService {

    CredentialDto loginUser(CredentialDto credentials);

    CredentialDto registerUser(CredentialDto credentials);
}