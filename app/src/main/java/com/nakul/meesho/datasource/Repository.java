package com.nakul.meesho.datasource;

import com.nakul.meesho.network.VolleyHelper;

import org.json.JSONObject;

import io.reactivex.Observable;

public class Repository {

    private String ownerName = "", repoName = "";

    public void setParamsValues(String repo, String owner){
        ownerName = owner;
        repoName = repo;
    }

    Observable<JSONObject> getRepositories(int pageIndex) {
        return Observable.just(VolleyHelper.getRepoIssues(pageIndex, repoName, ownerName));
    }

}
