package com.nakul.meesho.view;

import android.arch.lifecycle.ViewModelProviders;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import com.nakul.meesho.R;
import com.nakul.meesho.datasource.Repository;
import com.nakul.meesho.utils.Constant;
import com.nakul.meesho.viewmodel.RepoPagingLibViewModel;
import com.nakul.meesho.viewmodel.ViewModelFactory;
import com.nakul.meesho.databinding.ActivityMainBinding;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {

   private ViewModelFactory viewModelFactory;

    private RepoPagingLibViewModel viewModel;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Repository repository = new Repository();
        viewModelFactory = new ViewModelFactory(repository);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(RepoPagingLibViewModel.class);
        init();
    }


    private void init() {

        binding.repoListRecyclerView.setLayoutManager(new LinearLayoutManager(this));
        PullRequestPageListAdapter adapter = new PullRequestPageListAdapter();
        binding.repoListRecyclerView.setAdapter(adapter);
        binding.submitBtnImgView.setOnClickListener(view -> {
            if (binding.repoNameEditTxt.getEditText().getText() == null || binding.repoNameEditTxt.getEditText().getText().toString().isEmpty()){
                binding.repoNameEditTxt.setError("Please Enter Repository Name");
                return;
            }
            if (binding.ownerEditTxt.getEditText().getText() == null || binding.ownerEditTxt.getEditText().getText().toString().isEmpty()){
                binding.ownerEditTxt.setError("Please Enter Owner Name");
                return;
            }
            viewModelFactory.setRepositoryParams(binding.repoNameEditTxt.getEditText().getText().toString(), binding.ownerEditTxt.getEditText().getText().toString());
            viewModel.reLoadList();
        });
        final GestureDetector mGestureDetector = new GestureDetector(this, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        binding.repoListRecyclerView.addOnItemTouchListener(new RecyclerView.OnItemTouchListener() {
            @Override
            public boolean onInterceptTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {
                View childView = recyclerView.findChildViewUnder(motionEvent.getX(), motionEvent.getY());

                if (childView != null && mGestureDetector.onTouchEvent(motionEvent)) {
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(viewModel.getListLiveData().getValue().get(recyclerView.getChildAdapterPosition(childView)).getIssueUrl()));
                    startActivity(i);
                }
                return false;
            }

            @Override
            public void onTouchEvent(@NonNull RecyclerView recyclerView, @NonNull MotionEvent motionEvent) {

            }

            @Override
            public void onRequestDisallowInterceptTouchEvent(boolean b) {

            }
        });
        if (!Constant.checkInternetConnection(this)) {
            Snackbar.make(findViewById(android.R.id.content), Constant.CHECK_NETWORK_ERROR, Snackbar.LENGTH_SHORT)
                    .show();
        }

        viewModel.getListLiveData().observe(this, adapter::submitList);


        viewModel.getProgressLoadStatus().observe(this, status -> {
            if (Objects.requireNonNull(status).equalsIgnoreCase(Constant.LOADING)) {
                binding.progress.setVisibility(View.VISIBLE);
            } else if (status.equalsIgnoreCase(Constant.LOADED)) {
                binding.progress.setVisibility(View.GONE);
            }
        });

    }
}
