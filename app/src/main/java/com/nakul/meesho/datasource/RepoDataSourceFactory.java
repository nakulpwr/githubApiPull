package com.nakul.meesho.datasource;

import android.arch.lifecycle.MutableLiveData;
import android.arch.paging.DataSource;

import com.nakul.meesho.models.RepoPullModelClass;

import io.reactivex.disposables.CompositeDisposable;

public class RepoDataSourceFactory extends DataSource.Factory<Integer, RepoPullModelClass> {

    private MutableLiveData<RepoDataSourceClass> liveData;
    private Repository repository;
    private CompositeDisposable compositeDisposable;

    public RepoDataSourceFactory(Repository repository, CompositeDisposable compositeDisposable) {
        this.repository = repository;
        this.compositeDisposable = compositeDisposable;
        liveData = new MutableLiveData<>();
    }

    public MutableLiveData<RepoDataSourceClass> getMutableLiveData() {
        return liveData;
    }

    @Override
    public DataSource<Integer, RepoPullModelClass> create() {
        RepoDataSourceClass dataSourceClass = new RepoDataSourceClass(repository, compositeDisposable);
        liveData.postValue(dataSourceClass);
        return dataSourceClass;
    }
}
