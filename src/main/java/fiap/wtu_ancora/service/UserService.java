package fiap.wtu_ancora.service;

import fiap.wtu_ancora.model.User;
import fiap.wtu_ancora.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public List<User> findAllUsers(){
        return userRepository.findAll();
    }

    public Optional<User> findUser(Long id){
        return userRepository.findById(id);
    }

    public User saveUser(User user){
        return userRepository.save(user);
    }
}
