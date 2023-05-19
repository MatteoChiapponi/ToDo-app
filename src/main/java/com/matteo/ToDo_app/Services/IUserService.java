package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.UserDto.GetUserDto;
import com.matteo.ToDo_app.Dtos.UserDto.RegisterUserDto;
import com.matteo.ToDo_app.Entitys.User.User;

import java.util.List;

public interface IUserService {
    User saveNewUser(RegisterUserDto registerUserDto) throws Exception;
    GetUserDto findUserById(Long id) throws Exception;
    User findUserByEmail(String email) throws Exception;
    List<GetUserDto> findALlUser() throws Exception;
    void updateUser(Long id, Boolean status) throws Exception;
}
