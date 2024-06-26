package fiap.wtu_ancora.controller;

import fiap.wtu_ancora.model.Unit;
import fiap.wtu_ancora.model.User;
import fiap.wtu_ancora.model.UserRole;
import fiap.wtu_ancora.dto.AuthenticationDTO;
import fiap.wtu_ancora.dto.LoginResponseDTO;
import fiap.wtu_ancora.dto.RegisterDTO;
import fiap.wtu_ancora.repository.UnitRepository;
import fiap.wtu_ancora.repository.UserRepository;
import fiap.wtu_ancora.security.TokenService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UnitRepository unitRepository;

    @PostMapping("/login")
    public ResponseEntity login(@RequestBody @Valid AuthenticationDTO data){
        var usernamePassword = new UsernamePasswordAuthenticationToken(data.email(), data.password());
        var auth = this.authenticationManager.authenticate(usernamePassword);

        var token = tokenService.generateToken((User)auth.getPrincipal());

        return ResponseEntity.ok(new LoginResponseDTO(token));
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDTO data){
        if(this.userRepository.findByEmail(data.email()) != null) return ResponseEntity.badRequest().build();

        String encryptedPassword = new BCryptPasswordEncoder().encode(data.password());
        Unit unit = this.unitRepository.findById(data.unitId()).orElseThrow(() -> new IllegalArgumentException("Unidade não encontrada"));
        User newUser = new User(data.name(), data.email(), encryptedPassword, UserRole.USER);
        newUser.setUnit(unit);

        System.out.println("Segue: " + data.name() + " " + data.email() + " " + encryptedPassword + " " + unit.getName());

        this.userRepository.save(newUser);

        return ResponseEntity.ok().build();
    }
}
