package com.nakul.meesho.datasource;

import android.annotation.SuppressLint;
import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.PageKeyedDataSource;
import android.support.annotation.NonNull;

import com.nakul.meesho.models.RepoPullModelClass;
import com.nakul.meesho.utils.Constant;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.disposables.CompositeDisposable;

public class RepoDataSourceClass extends PageKeyedDataSource<Integer, RepoPullModelClass> {

    private Repository repository;
    private int sourceIndex;
    private MutableLiveData<String> progressLiveStatus;
    private CompositeDisposable compositeDisposable;

    RepoDataSourceClass(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        progressLiveStatus = new MutableLiveData<>();
    }


    public MutableLiveData<String> getProgressLiveStatus() {
        return progressLiveStatus;
    }

    @SuppressLint("CheckResult")
    @Override
    public void loadInitial(@NonNull LoadInitialParams<Integer> params, @NonNull LoadInitialCallback<Integer, RepoPullModelClass> callback) {

        repository.getRepositories(sourceIndex)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JSONObject result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);


                            JSONArray array = result.optJSONArray("result");

                            ArrayList<RepoPullModelClass> arrayList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject repoObject = array.optJSONObject(i);
                                RepoPullModelClass repoModel = new RepoPullModelClass();
                                repoModel.setAvatarUrl(repoObject.optJSONObject("user").optString("avatar_url"));
                                repoModel.setId(repoObject.optString("id"));
                                repoModel.setIssueUrl(repoObject.optString("html_url"));
                                repoModel.setTitle(repoObject.optString("title"));
                                repoModel.setUserName(repoObject.optJSONObject("user").optString("login"));
                                arrayList.add(repoModel);
                            }

                            sourceIndex++;
                            callback.onResult(arrayList, null, sourceIndex);
                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)

                );

    }

    @Override
    public void loadBefore(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, RepoPullModelClass> callback) {

    }

    @SuppressLint("CheckResult")
    @Override
    public void loadAfter(@NonNull LoadParams<Integer> params, @NonNull LoadCallback<Integer, RepoPullModelClass> callback) {

        repository.getRepositories(params.key)
                .doOnSubscribe(disposable ->
                {
                    compositeDisposable.add(disposable);
                    progressLiveStatus.postValue(Constant.LOADING);
                })
                .subscribe(
                        (JSONObject result) ->
                        {
                            progressLiveStatus.postValue(Constant.LOADED);

                            JSONArray array = result.optJSONArray("result");

                            ArrayList<RepoPullModelClass> arrayList = new ArrayList<>();

                            for (int i = 0; i < array.length(); i++) {
                                JSONObject repoObject = array.optJSONObject(i);
                                RepoPullModelClass repoModel = new RepoPullModelClass();
                                repoModel.setAvatarUrl(repoObject.optJSONObject("user").optString("avatar_url"));
                                repoModel.setId(repoObject.optString("id"));
                                repoModel.setIssueUrl(repoObject.optString("html_url"));
                                repoModel.setTitle(repoObject.optString("title"));
                                repoModel.setUserName(repoObject.optJSONObject("user").optString("login"));
                                arrayList.add(repoModel);                            }

                            sourceIndex++;
                            callback.onResult(arrayList, sourceIndex);
                        },
                        throwable ->
                                progressLiveStatus.postValue(Constant.LOADED)
                );
    }
}
