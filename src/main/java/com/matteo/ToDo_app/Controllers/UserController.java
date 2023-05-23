package com.matteo.ToDo_app.Controllers;

import com.matteo.ToDo_app.Dtos.UserDto.GetUserResp;
import com.matteo.ToDo_app.Services.UserServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/v1/users")
public class UserController {
    UserServiceImpl userService;

    public UserController(UserServiceImpl userService) {
        this.userService = userService;
    }

    @GetMapping("/getMe")
    public ResponseEntity<GetUserResp> getMe() throws Exception {
        var userId = Long.parseLong((String) SecurityContextHolder.getContext().getAuthentication().getCredentials());
        return ResponseEntity.ok(userService.findUserById(userId));
    }
}
