package ru.pschsch.pschschapps.githubwork.Models;

import com.google.gson.annotations.SerializedName;

public class GitHubReposResponse {
    @SerializedName("name")
    private String name;

    @SerializedName("id")
    private String id;

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }
}
