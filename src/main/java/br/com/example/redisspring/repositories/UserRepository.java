package br.com.example.redisspring.repositories;

import br.com.example.redisspring.models.User;

import java.util.*;


public class UserRepository {
    private static Map<String, User> repository = new HashMap<String, User>();

    public static User save(User user){
        String id = generateId();
        user.setId(id);
        repository.put(id,user);
        return user;
    }

    public static  User findById(String id){
        return repository.get(id);
    }

    public static  void deleteAll(){
        repository.clear();
    }

    public static List<User> findAll(){
        System.out.println("********BATEUUUUU NA REPOSITORY!!!!!!!!!!********");
        Set<String> keys = repository.keySet();
        List<User> users = new ArrayList<User>();
        for (String key: keys) {
            users.add(repository.get(key));
        }
        return users;
    }

    private static  String generateId(){
        String id = UUID.randomUUID().toString();
        if(repository.containsKey(id)) {
            generateId();
        }
        return id;
    }
}
