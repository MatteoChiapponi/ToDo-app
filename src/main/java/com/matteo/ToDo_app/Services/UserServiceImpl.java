package com.matteo.ToDo_app.Services;

import com.matteo.ToDo_app.Dtos.UserDto.GetUserDto;
import com.matteo.ToDo_app.Dtos.UserDto.RegisterUserDto;
import com.matteo.ToDo_app.Entitys.User.User;
import com.matteo.ToDo_app.Entitys.User.UserRole;
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
    public User saveNewUser(RegisterUserDto registerUserDto) throws Exception {
        if (registerUserDto.password().equals(registerUserDto.repitedPassword())){
            User user = new User(registerUserDto.firstName(),registerUserDto.lastName(),registerUserDto.email(),passwordEncoder.encode(registerUserDto.password()),UserRole.ROLE_USER);
            System.out.println(user.getId());
            return userRepository.save(user);
        }
        else
            throw new Exception("both password must be equals");
    }

    @Override
    public GetUserDto findUserById(Long id) throws Exception {
        Optional<User> userOptional = userRepository.findById(id);
        if (userOptional.isPresent()){
            var user = userOptional.get();
            return new GetUserDto(user.getFirstName(), user.getLastName());
        }
        throw new Exception("user not found");
    }

    @Override
    public User findUserByEmail(String email) throws Exception {
        Optional<User> user = userRepository.findUserByEmail(email);
        if (user.isPresent())
            return user.get();
        else
            throw new Exception("user not found");
    }

    @Override
    public List<GetUserDto> findALlUser() throws Exception {
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
