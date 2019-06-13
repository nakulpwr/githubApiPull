package com.nakul.meesho.viewmodel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.nakul.meesho.datasource.Repository;

public class ViewModelFactory implements ViewModelProvider.Factory {

    private Repository repository;

    public ViewModelFactory(Repository repository) {
        this.repository = repository;
    }

    public void setRepositoryParams(String repo, String owner){
        repository.setParamsValues(repo , owner);
    }


    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(RepoPagingLibViewModel.class)) {
            return (T) new RepoPagingLibViewModel(repository);
        }
        throw new IllegalArgumentException("Unknown class name");
    }
}
