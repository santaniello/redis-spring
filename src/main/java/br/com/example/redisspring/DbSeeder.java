package br.com.example.redisspring;

import br.com.example.redisspring.models.User;
import br.com.example.redisspring.repositories.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

// This interface provides access to application arguments as string array.
// Let's see the example code for more clarity.
@Component
public class DbSeeder implements CommandLineRunner {

    @Override
    public void run(String... args) throws Exception {
        UserRepository.save(new User("Felipe Santaniello"));
        UserRepository.save(new User("Naiara Santaniello"));
    }
}