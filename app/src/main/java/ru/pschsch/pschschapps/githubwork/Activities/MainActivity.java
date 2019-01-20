package ru.pschsch.pschschapps.githubwork.Activities;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MenuItem;

import java.io.File;
import java.util.ArrayList;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import ru.pschsch.pschschapps.githubwork.Adapters.ReposAdapter;
import ru.pschsch.pschschapps.githubwork.BuildConfig;
import ru.pschsch.pschschapps.githubwork.Models.User;
import ru.pschsch.pschschapps.githubwork.R;
import ru.pschsch.pschschapps.githubwork.RetrofitUtils.GitHubClient;
import ru.pschsch.pschschapps.githubwork.Models.GitHubReposResponse;
import ru.pschsch.pschschapps.githubwork.RetrofitUtils.NewUser;
import ru.pschsch.pschschapps.githubwork.RetrofitUtils.RetrofitInfo;

public class MainActivity extends AppCompatActivity {
    private BottomNavigationView mNavigation;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
            = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_list:
                    OkHttpClient.Builder mOkHttpBuilder = new OkHttpClient.Builder();
                    HttpLoggingInterceptor mLoggingInterceptor = new HttpLoggingInterceptor();
                    mLoggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);

                    if(BuildConfig.DEBUG) {
                        mOkHttpBuilder.addInterceptor(mLoggingInterceptor);
                    }
                    Retrofit.Builder mRetrofitBuilder = new Retrofit.Builder()
                            .baseUrl("https://api.github.com/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .client(mOkHttpBuilder.build());
                    Retrofit mBaseRetrofit = mRetrofitBuilder.build();

                    GitHubClient mGitHubClient = mBaseRetrofit.create(GitHubClient.class);

                    Call<ArrayList<GitHubReposResponse>> mReposResponse
                            = mGitHubClient.mGitHubRepos("Pschsch");
                    mReposResponse.enqueue(new Callback<ArrayList<GitHubReposResponse>>() {
                        @Override
                        public void onResponse(Call<ArrayList<GitHubReposResponse>> call, Response<ArrayList<GitHubReposResponse>> response) {
                            ArrayList<GitHubReposResponse> reposList = response.body();
                            RecyclerView mBasicRecyclerView = findViewById(R.id.baserv);
                            mBasicRecyclerView.setLayoutManager(new GridLayoutManager(getBaseContext(), 2));
                            ReposAdapter reposAdapter = new ReposAdapter(MainActivity.this, reposList);
                            mBasicRecyclerView.setAdapter(reposAdapter);
                        }

                        @Override
                        public void onFailure(Call<ArrayList<GitHubReposResponse>> call, Throwable t) {
                            Snackbar.make(mNavigation, R.string.failure, Snackbar.LENGTH_SHORT)
                                    .show();
                        }
                    });
                    return true;
                case R.id.navigation_new_user:
                    Retrofit mRetrofit = new Retrofit.Builder()
                            .baseUrl("https://reqres.in/")
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    NewUser mNewUser = mRetrofit.create(NewUser.class);
                    Call<User> mCall = mNewUser.createAccount(new User("Max", "Android Developer"));
                    mCall.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            Snackbar.make(mNavigation, "ID of new User is:" +response.body().getId(),
                                    Snackbar.LENGTH_LONG).show();
                        }
                        @Override
                        public void onFailure(Call<User> call, Throwable t) {
                            Snackbar.make(mNavigation,R.string.failure,Snackbar.LENGTH_LONG).show();
                        }
                    });
                    return true;
                case R.id.navigation_image:
                    if(ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
                        ActivityCompat.requestPermissions(MainActivity.this,
                                new String[] {
                                        Manifest.permission.READ_EXTERNAL_STORAGE},
                                1);
                    }
                    else {
                        String description = "photo description";
                        // 1-я часть запроса на загрузку файла, содержащяя описание
                        RequestBody descriptionPart = RequestBody.create(MultipartBody.FORM, description);
                        // 2-я часть запроса будет содержать сам файл изображения
                        RequestBody imagePart = RequestBody.create(MediaType.parse("image/*"),
                                new File("some directory"));
                        MultipartBody.Part file = MultipartBody.Part.createFormData("some name,",
                                "some path to file", imagePart);
                        Retrofit retrofit = new Retrofit.Builder()
                                .baseUrl("someimageservice.net")
                                .build();
                        RetrofitInfo uploadingExample = retrofit.create(RetrofitInfo.class);
                        Call<ResponseBody> call = uploadingExample.uploadPhoto("123", file);
                        call.enqueue(new Callback<ResponseBody>() {
                            @Override
                            public void onResponse(Call<ResponseBody> call, Response<ResponseBody> response) {
                                Snackbar.make(mNavigation,"Upload was successful",Snackbar.LENGTH_LONG).show();
                            }
                            @Override
                            public void onFailure(Call<ResponseBody> call, Throwable t) {
                                Snackbar.make(mNavigation,R.string.failure,Snackbar.LENGTH_LONG).show();
                            }
                        });
                    }
                    return true;
                case R.id.navigation_oauthgithub:

            }
            return false;
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mNavigation = findViewById(R.id.navigation);
        mNavigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d("onResume", "onResume");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d("onDestroy", "onDestroy");
    }
}
