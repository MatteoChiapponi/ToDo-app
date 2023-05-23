package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.UserDto.AuthenticationUserReq;
import com.matteo.ToDo_app.Dtos.UserDto.AuthenticationUserResp;
import com.matteo.ToDo_app.Dtos.UserDto.RegisterUserReq;
import com.matteo.ToDo_app.Security.JwtUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;
import java.util.HashMap;
import java.util.Map;

@Service
public class AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final JwtUtils jwtUtils;

    public AuthenticationService(AuthenticationManager authenticationManager, UserServiceImpl userService, JwtUtils jwtUtils) {
        this.authenticationManager = authenticationManager;
        this.userService = userService;
        this.jwtUtils = jwtUtils;
    }

    public AuthenticationUserResp authenticateUser(AuthenticationUserReq authenticationUserReq) throws Exception{
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                authenticationUserReq.username(),authenticationUserReq.password()
                )
        );
        var user = userService.findUserByEmail(authenticationUserReq.username());
        Map extraClaims = new HashMap<String,Object>();
        extraClaims.put("id",user.getId());
        String jwt = jwtUtils.generateToken(user,extraClaims);
        return new AuthenticationUserResp(jwt);
    }
    public AuthenticationUserResp registerUser(RegisterUserReq registerUserReq) throws Exception {
        var user = userService.saveNewUser(registerUserReq);
        Map extraClaims = new HashMap<String,Object>();
        extraClaims.put("id",user.getId());
        String jwt = jwtUtils.generateToken(user,extraClaims);
        return new AuthenticationUserResp(jwt);
    }
}
