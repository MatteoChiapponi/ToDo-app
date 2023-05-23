package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.UserDto.GetUserResp;
import com.matteo.ToDo_app.Dtos.UserDto.RegisterUserReq;
import com.matteo.ToDo_app.Entitys.User.User;
import com.matteo.ToDo_app.Exceptions.BadRequestException;
import com.matteo.ToDo_app.Exceptions.EntityNotFoudException;
import com.matteo.ToDo_app.Exceptions.SqlViolationException;

import java.util.List;

public interface IUserService {
    User saveNewUser(RegisterUserReq registerUserReq) throws BadRequestException, SqlViolationException;
    GetUserResp findUserById(Long id) throws EntityNotFoudException;
    User findUserByEmail(String email) throws EntityNotFoudException;
    List<GetUserResp> findALlUser() throws Exception;
    void updateUser(Long id, Boolean status) throws Exception;
}
