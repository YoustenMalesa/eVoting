package com.mrkenobii.ecommerceapp.service;

import com.mrkenobii.ecommerceapp.dto.user.SignInDto;
import com.mrkenobii.ecommerceapp.dto.user.SignInResponseDto;
import com.mrkenobii.ecommerceapp.dto.user.SignupResponseDto;
import com.mrkenobii.ecommerceapp.dto.user.SignupDto;
import com.mrkenobii.ecommerceapp.exception.AuthenticationFailedException;
import com.mrkenobii.ecommerceapp.exception.CustomException;
import com.mrkenobii.ecommerceapp.model.AuthenticationToken;
import com.mrkenobii.ecommerceapp.model.Organization;
import com.mrkenobii.ecommerceapp.model.User;
import com.mrkenobii.ecommerceapp.repository.OrganizationRepository;
import com.mrkenobii.ecommerceapp.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final OrganizationRepository organizationRepository;
    private final AuthenticationService authenticationService;
    @Transactional
    public SignupResponseDto signup(SignupDto signupDto) {

        if (Objects.nonNull(userRepository.findByEmail(signupDto.getEmail())))
            throw new CustomException("User already present");
        String encryptedPassword = signupDto.getPassword();
        try {
            encryptedPassword = hashPassword(encryptedPassword);
        } catch (NoSuchAlgorithmException e){
            e.printStackTrace();
        }
        User user = new User();
        user.setEmail(signupDto.getEmail());
        user.setFirstName(signupDto.getFirstName());
        user.setPassword(encryptedPassword);
        String org = getOrganization(user.getEmail());
        user.setOrganization(org.toUpperCase());

        Organization organization = organizationRepository.findByDomain(user.getOrganization());
        if(organization == null) {
            throw new CustomException("Organization with domain " + org + " does not exist.");
        }

        userRepository.save(user);

        final AuthenticationToken authenticationToken = new AuthenticationToken(user);
        authenticationService.saveConfirmationToken(authenticationToken);
        SignupResponseDto signupResponseDto = new SignupResponseDto("success", "user created successfully");
        return signupResponseDto;
    }

    private String getOrganization(String email) {
        String parts[] = email.split("@");
        String [] domain = parts[1].split("\\.");

        return domain[0];
    }

    private String hashPassword(String encryptedPassword) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        messageDigest.update(encryptedPassword.getBytes());
        byte[] digest = messageDigest.digest();
        String hash = DatatypeConverter
                .printHexBinary(digest).toUpperCase();
        return hash;
    }

    public SignInResponseDto signIn(SignInDto signInDto) {
        User user = userRepository.findByEmail(signInDto.getEmail());
        if(Objects.isNull(user))
            return new SignInResponseDto("failure", "Login failed. Bad credentials.", false);
            //throw new AuthenticationFailedException("");
        try {
            if(!user.getPassword().equals(hashPassword(signInDto.getPassword())))
                return new SignInResponseDto("failure", "Login failed. Bad credentials.", false);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }

        if(!user.isActive()) {
            return new SignInResponseDto("failure", "User account not active. Contact admin.", false);
        }

        AuthenticationToken authenticationToken=authenticationService.getToken(user);
        return new SignInResponseDto("success", authenticationToken.getToken(), user.isActive());
    }

    public User findByEmail(String email) {
        System.out.println("Email:: " + email);
        User user = userRepository.findByEmail(email);
        if(Objects.isNull(user))
            throw new AuthenticationFailedException("User not found");

        return user;
    }

    public void activateUser(String email) {
        User user = findByEmail(email);
        if(user != null) {
            user.setActive(true);
        }

        userRepository.saveAndFlush(user);
    }

    public User findById(String id) {
        System.out.println("Email:: " + id);
        User user = userRepository.findById(Integer.valueOf(id)).get();
        if(Objects.isNull(user))
            throw new AuthenticationFailedException("User not found");

        return user;
    }

}
