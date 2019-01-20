package ru.pschsch.pschschapps.githubwork.Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import java.util.ArrayList;

import ru.pschsch.pschschapps.githubwork.R;
import ru.pschsch.pschschapps.githubwork.Models.GitHubReposResponse;

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder>{
    private LayoutInflater mInflater;
    private ArrayList<GitHubReposResponse> mReposList;

    public ReposAdapter(Context c, ArrayList<GitHubReposResponse> a){
        this.mReposList = a;
        this.mInflater = LayoutInflater.from(c);
    }

    @NonNull
    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = mInflater.inflate(R.layout.baserecyclerviewitem, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ReposAdapter.ViewHolder holder, int position) {
        GitHubReposResponse mRepos = mReposList.get(position);
        holder.mName.setText(mRepos.getName());
        holder.mId.setText(mRepos.getId());
    }

    @Override
    public int getItemCount() {
        return mReposList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{
        TextView mName;
        TextView mId;
        ViewHolder(View v){
            super(v);
            mName = v.findViewById(R.id.name);
            mId = v.findViewById(R.id.id);
        }
    }
}


