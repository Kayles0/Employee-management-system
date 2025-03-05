package com.kayles.employee_management_system.Service.security;

import com.kayles.employee_management_system.Service.PersonRegisterService;
import com.kayles.employee_management_system.dto.security.AuthenticationRequest;
import com.kayles.employee_management_system.dto.security.AuthenticationResponse;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.enums.RoleEnum;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.exception.LoginDuplicateException;
import com.kayles.employee_management_system.mapper.JwtPersonMapper;
import com.kayles.employee_management_system.repository.PersonRepository;
import com.kayles.employee_management_system.repository.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PersonRepository personRepository;
    private final RoleRepository roleRepository;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final PasswordEncoder passwordEncoder;
    private final JwtPersonMapper jwtPersonMapper;

    public AuthenticationResponse register(JwtPerson request) {
        Person person = Person.builder()
                .login(request.getLogin())
                .password(passwordEncoder.encode(
                        request.getPassword()
                ))
                .role(roleRepository.findByName(RoleEnum.ROLE_USER))
                .isDeleted(false)
                .build();
        try {
            person = personRepository.save(person);
        }
        catch (Exception e) {
            throw new LoginDuplicateException("The username is already taken: {}", person.getLogin());
        }
        String token = jwtService.generateToken(jwtPersonMapper.toJwtPerson(person));
        return AuthenticationResponse
                .builder()
                .login(request.getLogin())
                .token(token)
                .build();
    }

    public AuthenticationResponse login(AuthenticationRequest request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword())
        );
        Person person = personRepository.findByLogin(request.getLogin())
                .orElseThrow(() -> new EntityNotFoundException("person not found with login: {0}", request.getLogin()));
        String token = jwtService.generateToken(jwtPersonMapper.toJwtPerson(person));
        return AuthenticationResponse
                .builder()
                .login(person.getLogin())
                .token(token)
                .build();
    }
}
