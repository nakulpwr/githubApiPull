package com.nakul.meesho.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.arch.paging.LivePagedListBuilder;
import android.arch.paging.PagedList;

import com.nakul.meesho.datasource.RepoDataSourceClass;
import com.nakul.meesho.datasource.RepoDataSourceFactory;
import com.nakul.meesho.datasource.Repository;
import com.nakul.meesho.models.RepoPullModelClass;

import io.reactivex.disposables.CompositeDisposable;

public class RepoPagingLibViewModel extends ViewModel {

    private RepoDataSourceFactory repoDataSourceFactory;
    private LiveData<PagedList<RepoPullModelClass>> listLiveData;

    private LiveData<String> progressLoadStatus = new MutableLiveData<>();
    private CompositeDisposable compositeDisposable = new CompositeDisposable();

    public RepoPagingLibViewModel(Repository repository) {
        repoDataSourceFactory = new RepoDataSourceFactory(repository, compositeDisposable);
        initializePaging();
    }


    private void initializePaging() {

        PagedList.Config pagedListConfig =
                new PagedList.Config.Builder()
                        .setEnablePlaceholders(true)
                        .setInitialLoadSizeHint(9)
                        .setPageSize(9).build();

        listLiveData = new LivePagedListBuilder<>(repoDataSourceFactory, pagedListConfig)
                .build();

        progressLoadStatus = Transformations.switchMap(repoDataSourceFactory.getMutableLiveData(), RepoDataSourceClass::getProgressLiveStatus);

    }

    public void reLoadList(){
        repoDataSourceFactory.getMutableLiveData().getValue().invalidate();
    }

    public LiveData<String> getProgressLoadStatus() {
        return progressLoadStatus;
    }

    public LiveData<PagedList<RepoPullModelClass>> getListLiveData() {
        return listLiveData;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        compositeDisposable.clear();
    }
}
