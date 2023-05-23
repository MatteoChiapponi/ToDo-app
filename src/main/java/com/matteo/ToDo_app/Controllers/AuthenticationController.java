package com.matteo.ToDo_app.Controllers;

import com.matteo.ToDo_app.Dtos.UserDto.AuthenticationUserReq;
import com.matteo.ToDo_app.Dtos.UserDto.AuthenticationUserResp;
import com.matteo.ToDo_app.Dtos.UserDto.RegisterUserReq;
import com.matteo.ToDo_app.Services.AuthenticationService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/v1/auth")
@CrossOrigin(origins = "http://127.0.0.1:5500")
public class AuthenticationController {
    private final AuthenticationService authenticationService;

    public AuthenticationController(AuthenticationService authenticationService) {
        this.authenticationService = authenticationService;
    }
    @PostMapping("/login")
    public ResponseEntity<AuthenticationUserResp> AuthenticateUser(@RequestBody AuthenticationUserReq authenticationRequest) throws Exception{
        return ResponseEntity.ok(authenticationService.authenticateUser(authenticationRequest));
    }
    @PostMapping("/singup")
    public ResponseEntity<?> registerNewUser(@RequestBody RegisterUserReq registerUserReq) throws Exception{
        return ResponseEntity.ok(authenticationService.registerUser(registerUserReq));
    }

}
