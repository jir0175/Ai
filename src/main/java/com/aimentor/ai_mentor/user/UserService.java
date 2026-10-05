package com.aimentor.ai_mentor.user;
import com.aimentor.ai_mentor.config.JwtUtils;
import com.aimentor.ai_mentor.user.dto.CreateUserRequest;
import com.aimentor.ai_mentor.user.dto.LoginRequest;
import com.aimentor.ai_mentor.user.dto.LoginResponse;
import com.aimentor.ai_mentor.user.dto.ResponseOnCreateUser;
import com.aimentor.ai_mentor.user.exception.EmailAlreadyExistsException;
import com.aimentor.ai_mentor.user.exception.InvalidCredentialsException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils;
    //Registration
    public ResponseOnCreateUser createUser(CreateUserRequest newUser){
        if(userRepository.existsByEmail(newUser.getEmail())){
            throw new EmailAlreadyExistsException("Email already exists");
        }
        User user = new User();
        user.setEmail(newUser.getEmail());
        user.setPasswordHash(passwordEncoder.encode(newUser.getPassword()));
        user = userRepository.save(user);
        ResponseOnCreateUser response = new ResponseOnCreateUser();
        response.setEmail(user.getEmail());
        
        return response;
    }
    //Login
    public LoginResponse loginUser(LoginRequest user){
        User userInSystem = userRepository.findByEmail(user.getEmail())
                .orElseThrow(() -> new InvalidCredentialsException("login or password not correct"));

        if(!passwordEncoder.matches(user.getPassword(),userInSystem.getPasswordHash())){
            throw new InvalidCredentialsException("login or password not correct");
        }
        String jwtToken = jwtUtils.generateToken(userInSystem.getEmail());
        LoginResponse response = new LoginResponse();
        response.setAccessToken(jwtToken);
        return response;


    }
}
