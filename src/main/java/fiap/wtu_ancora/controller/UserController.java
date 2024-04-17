package fiap.wtu_ancora.controller;

import fiap.wtu_ancora.model.User;
import fiap.wtu_ancora.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @GetMapping("/get-all")
    public List<User> getAllUsers(){
        return userService.findAllUsers();
    }
}
