package br.com.example.redisspring.apis;

import br.com.example.redisspring.models.User;
import br.com.example.redisspring.repositories.UserRepository;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserApi {

    @Cacheable(value = "Users")
    @GetMapping("/users")
    public List<User> findAll(){
        return UserRepository.findAll();
    }
}
