package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.UserDto.GetUserResp;
import com.matteo.ToDo_app.Dtos.UserDto.RegisterUserReq;
import com.matteo.ToDo_app.Entitys.User.User;
import com.matteo.ToDo_app.Entitys.User.UserRole;
import com.matteo.ToDo_app.Exceptions.BadRequestException;
import com.matteo.ToDo_app.Exceptions.EntityNotFoudException;
import com.matteo.ToDo_app.Exceptions.SqlViolationException;
import com.matteo.ToDo_app.Repositorys.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserDetailsService, IUserService{
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserServiceImpl(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public User saveNewUser(RegisterUserReq registerUserReq) throws BadRequestException, SqlViolationException {
        if (registerUserReq.password().equals(registerUserReq.repitedPassword())){
            if (userRepository.findUserByEmail(registerUserReq.email()).isEmpty()){
                User user = new User(registerUserReq.firstName(), registerUserReq.lastName(), registerUserReq.email(),passwordEncoder.encode(registerUserReq.password()),UserRole.ROLE_USER);
                return userRepository.save(user);
            }else
                throw new SqlViolationException("user email already exist");
        }
        else
            throw new BadRequestException("both password must be equals");
    }

    @Override
    public GetUserResp findUserById(Long id) throws EntityNotFoudException {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            var user = userOptional.get();
            return new GetUserResp(user.getFirstName(), user.getLastName());
        }
        throw new EntityNotFoudException("user not found");
    }

    @Override
    public User findUserByEmail(String email) throws EntityNotFoudException {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent())
            return user.get();
        else
            throw new EntityNotFoudException("user not found");
    }

    @Override
    public List<GetUserResp> findALlUser() throws Exception {
        return null;
    }

    @Override
    public void updateUser(Long id, Boolean status) throws Exception {

    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> userOptional = userRepository.findUserByEmail(username);
        if (userOptional.isPresent())
            return userOptional.get();
        throw new UsernameNotFoundException("user not found");
    }

}
