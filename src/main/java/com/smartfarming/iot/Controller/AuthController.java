package com.smartfarming.iot.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import com.smartfarming.iot.Data.Dto.Response;
import com.smartfarming.iot.Data.Dto.Token;
import com.smartfarming.iot.Data.Model.User;
import com.smartfarming.iot.Repository.UserRepository;
import com.smartfarming.iot.Security.ApiKeyGenerator;
import com.smartfarming.iot.Security.JwtUtil;

@RestController
@CrossOrigin(origins = "*") // cros origins
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    JwtUtil jwtUtils;
   
    @PostMapping("/signin")
public Response<Object> authenticateUser(@RequestBody User user) {
    Response<Object> res = new Response<>();

    try {
        Authentication authentication = authenticationManager.authenticate(
            new UsernamePasswordAuthenticationToken(
                user.getUsername(),
                user.getPassword()
            )
        );

        UserDetails userDetails = (UserDetails) authentication.getPrincipal();
        String role = userDetails.getAuthorities().iterator().next().getAuthority();

        // Ambil kembali User dari database untuk mendapatkan apiKey
        User dbUser = userRepository.findByUsername(userDetails.getUsername());

        String token = jwtUtils.generateToken(userDetails.getUsername());

        // Buat Token DTO lengkap dengan apiKey
        Token responseToken = new Token(
            userDetails.getUsername(),
            token,
            role,
            dbUser.getApiKey()  // ambil apiKey langsung dari database
        );

        res.setStatus(HttpStatus.OK.toString());
        res.setMessage("Congratulations, you are Authorized.");
        res.setPayload(responseToken);

    } catch (Exception e) {
        res.setStatus(HttpStatus.UNAUTHORIZED.toString());
        res.setMessage("Authentication failed: " + e.getMessage());
        res.setPayload(null);
        e.printStackTrace();
    }

    return res;
}

    
    @PostMapping("/signup")
    public Response<Object> registerUser(@RequestBody User user) {
        Response<Object> res = new Response<>(); // ⬅️ perbaikan di sini juga
    
        if (userRepository.existsByUsername(user.getUsername())) {
            res.setStatus(HttpStatus.BAD_REQUEST.toString());
            res.setMessage("Username is already taken!");
            res.setPayload(null);
            return res;
        }
        
        String apikey =ApiKeyGenerator.generateApiKey();

        User newUser = new User(
                null,
                user.getUsername(),
                encoder.encode(user.getPassword()), user.getRoles(),apikey
        );
        userRepository.save(newUser);
    
        res.setStatus(HttpStatus.OK.toString());
        res.setMessage("User registered successfully!");
        res.setPayload(newUser.getUsername());
    
        return res;
    }
    
}
