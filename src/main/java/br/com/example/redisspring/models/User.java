package br.com.example.redisspring.models;

import lombok.Data;

import java.io.Serializable;

@Data
public class User implements Serializable {
    private String id;
    private String name;

    public User(String name) {
        this.name = name;
    }
}
