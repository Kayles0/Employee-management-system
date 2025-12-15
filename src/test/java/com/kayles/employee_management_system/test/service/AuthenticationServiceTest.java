package com.kayles.employee_management_system.test.service;

import com.kayles.employee_management_system.service.security.AuthenticationService;
import com.kayles.employee_management_system.service.security.JwtService;
import com.kayles.employee_management_system.dto.security.AuthenticationRequest;
import com.kayles.employee_management_system.dto.security.AuthenticationResponse;
import com.kayles.employee_management_system.dto.security.JwtPerson;
import com.kayles.employee_management_system.entity.Person;
import com.kayles.employee_management_system.entity.Role;
import com.kayles.employee_management_system.enums.RoleEnum;
import com.kayles.employee_management_system.exception.EntityNotFoundException;
import com.kayles.employee_management_system.exception.LoginDuplicateException;
import com.kayles.employee_management_system.mapper.JwtPersonMapper;
import com.kayles.employee_management_system.repository.PersonRepository;
import com.kayles.employee_management_system.repository.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthenticationServiceTest {

    @Mock
    private PersonRepository personRepository;

    @Mock
    private RoleRepository roleRepository;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtPersonMapper jwtPersonMapper;

    @InjectMocks
    private AuthenticationService authenticationService;


    @Test
    void register_WithValidRequest_ReturnsAuthenticationResponse() {
        // Arrange
        JwtPerson request = createMockJwtPerson("newuser", "password123");

        Person savedPerson = createMockPerson("newuser", "encodePassword");

        JwtPerson jwtPersonDto = JwtPerson.builder()
                .login("newuser")
                .build();

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName(RoleEnum.ROLE_USER)).thenReturn(Optional.of(mock(Role.class)));
        when(personRepository.save(any(Person.class))).thenReturn(savedPerson);
        when(jwtPersonMapper.toJwtPerson(savedPerson)).thenReturn(jwtPersonDto);
        when(jwtService.generateToken(jwtPersonDto)).thenReturn("jwt.token.here");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getLogin()).isEqualTo("newuser");
        assertThat(response.getToken()).isEqualTo("jwt.token.here");

        verify(passwordEncoder).encode("password123");
        verify(personRepository).save(any(Person.class));
        verify(jwtService).generateToken(jwtPersonDto);
    }

    @Test
    void register_WithDuplicateLogin_ThrowsLoginDuplicateException() {
        // Arrange
        JwtPerson request = createMockJwtPerson("newUser", "password123");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName(RoleEnum.ROLE_USER)).thenReturn(Optional.of(mock(Role.class)));
        when(personRepository.save(any(Person.class)))
                .thenThrow(new RuntimeException("Constraint violation"));

        // Act & Assert
        assertThatThrownBy(() -> authenticationService.register(request))
                .isInstanceOf(LoginDuplicateException.class)
                .hasMessageContaining("The username is already taken");

        verify(personRepository).save(any(Person.class));
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void register_WhenRoleNotFound_ThrowsException() {
        // Arrange
        JwtPerson request = createMockJwtPerson("newUser", "password123");

        when(passwordEncoder.encode(anyString())).thenReturn("encodedPassword");
        when(roleRepository.findByName(RoleEnum.ROLE_USER)).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authenticationService.register(request))
                .isInstanceOf(Exception.class);

        verify(personRepository, never()).save(any(Person.class));
    }

    // ========== LOGIN TESTS ==========

    @Test
    void login_WithValidCredentials_ReturnsAuthenticationResponse() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("validuser", "correctpassword");

        Person person = createMockPerson("validuser", "correctpassword");

        JwtPerson jwtPersonDto = JwtPerson.builder()
                .login("validuser")
                .build();

        // Мокаем успешную аутентификацию
        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(personRepository.findByLogin("validuser")).thenReturn(Optional.of(person));
        when(jwtPersonMapper.toJwtPerson(person)).thenReturn(jwtPersonDto);
        when(jwtService.generateToken(jwtPersonDto)).thenReturn("jwt.token.here");

        // Act
        AuthenticationResponse response = authenticationService.login(request);

        // Assert
        assertThat(response).isNotNull();
        assertThat(response.getLogin()).isEqualTo("validuser");
        assertThat(response.getToken()).isEqualTo("jwt.token.here");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(personRepository).findByLogin("validuser");
        verify(jwtService).generateToken(jwtPersonDto);
    }

    @Test
    void login_WithInvalidCredentials_ThrowsBadCredentialsException() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("invaliduser", "wrongpassword");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenThrow(new BadCredentialsException("Bad credentials"));

        // Act & Assert
        assertThatThrownBy(() -> authenticationService.login(request))
                .isInstanceOf(BadCredentialsException.class)
                .hasMessageContaining("Bad credentials");

        verify(personRepository, never()).findByLogin(anyString());
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void login_WithValidAuthButUserNotFound_ThrowsEntityNotFoundException() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("deleteduser", "password");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(personRepository.findByLogin("deleteduser")).thenReturn(Optional.empty());

        // Act & Assert
        assertThatThrownBy(() -> authenticationService.login(request))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("person not found with login");

        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
        verify(personRepository).findByLogin("deleteduser");
        verify(jwtService, never()).generateToken(any());
    }

    @Test
    void login_WithUserMarkedAsDeleted_ThrowsEntityNotFoundException() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("deleteduser", "password");

        Person deletedPerson = createMockPerson("deleteduser", "encodePassword");
            deletedPerson.setIsDeleted(true); //Пользователь удалён

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenReturn(mock(Authentication.class));
        when(personRepository.findByLogin("deleteduser")).thenReturn(Optional.of(deletedPerson));

        // Act & Assert
         when(personRepository.findByLogin("deleteduser")).thenReturn(Optional.empty());
         assertThatThrownBy(() -> authenticationService.login(request))
                 .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void login_WithNullRequest_ThrowsNullPointerException() {
        // Act & Assert
        assertThatThrownBy(() -> authenticationService.login(null))
                .isInstanceOf(NullPointerException.class);
    }

    @Test
    void register_WithNullRequest_ThrowsNullPointerException() {
        // Act & Assert
        assertThatThrownBy(() -> authenticationService.register(null))
                .isInstanceOf(NullPointerException.class);
    }

    // ========== EDGE CASES ==========

    @Test
    void register_WithEmptyPassword_EncodesEmptyString() {
        // Arrange
        JwtPerson request = createMockJwtPerson("testuser", ""); //Пустой пароль

        when(passwordEncoder.encode("")).thenReturn("encodedEmpty");
        when(roleRepository.findByName(RoleEnum.ROLE_USER)).thenReturn(Optional.of(mock(Role.class)));
        when(personRepository.save(any(Person.class))).thenReturn(mock(Person.class));
        when(jwtPersonMapper.toJwtPerson(any())).thenReturn(JwtPerson.builder().build());
        when(jwtService.generateToken(any())).thenReturn("token");

        // Act
        AuthenticationResponse response = authenticationService.register(request);

        // Assert
        assertThat(response).isNotNull();
        verify(passwordEncoder).encode("");
    }

    @Test
    void login_VerifiesAuthenticationTokenParameters() {
        // Arrange
        AuthenticationRequest request = new AuthenticationRequest("testuser", "testpass");

        when(authenticationManager.authenticate(any(UsernamePasswordAuthenticationToken.class)))
                .thenAnswer(invocation -> {
                    UsernamePasswordAuthenticationToken token = invocation.getArgument(0);
                    assertThat(token.getPrincipal()).isEqualTo("testuser");
                    assertThat(token.getCredentials()).isEqualTo("testpass");
                    return mock(Authentication.class);
                });
        when(personRepository.findByLogin("testuser")).thenReturn(Optional.of(mock(Person.class)));
        when(jwtPersonMapper.toJwtPerson(any())).thenReturn(JwtPerson.builder().build());
        when(jwtService.generateToken(any())).thenReturn("token");

        // Act
        authenticationService.login(request);

        // Assert уже в thenAnswer
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }

    private Person createMockPerson(String login, String password) {
        return Person.builder()
                .login(login)
                .password(password)
                .build();
    }

    private JwtPerson createMockJwtPerson(String login, String password) {
        return JwtPerson.builder()
                .login(login)
                .password(password)
                .build();
    }


}
