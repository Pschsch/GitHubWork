package ru.pschsch.pschschapps.githubwork.Models;

public class User {

    private String name;
    private String job;

    private Integer id;

    public User(String name, String job) {
        this.name = name;
        this.job = job;
    }

    public Integer getId() {
        return id;
    }
}
