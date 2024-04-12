package com.alvaro.dockerizedApiNBA.user;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins= {"http://localhost:4200"})
@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody User loginRequest) {
        String username = loginRequest.getUsername();
        String password = loginRequest.getPassword();

        // Buscar al usuario por nombre de usuario
        User user = userRepository.findByUsername(username);

        // Verificar las credenciales de la contraseña de ese usuario
        if (user != null && user.getPassword().equals(password)) {
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Credenciales incorrectas");
        }
    }

    @GetMapping
    public List<User> getAllUsers() {
        return (List<User>) userRepository.findAll();
    }

    @GetMapping({"/{id}"})
    public User getUserById(@PathVariable Long id) {
        return userRepository.findById(id).orElse(null);
    }

    @PostMapping("/registrarse")
    public User createUser(@RequestBody User user) {
        return userRepository.save(user);
    }
}
