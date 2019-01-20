package ru.pschsch.pschschapps.githubwork.RetrofitUtils;
import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import ru.pschsch.pschschapps.githubwork.Models.GitHubReposResponse;

public interface GitHubClient {
    @GET("/users/{user}/repos")
    Call<ArrayList<GitHubReposResponse>> mGitHubRepos(@Path("user") String user);

}
