package com.nakul.meesho.models;

import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;

public class RepoPullModelClass {

    private String avatarUrl, userName, title, id, issueUrl;

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public String getUserName() {
        return userName;
    }

    public String getTitle() {
        return title;
    }

    public RepoPullModelClass() {
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getIssueUrl() {
        return issueUrl;
    }

    public void setIssueUrl(String issueUrl) {
        this.issueUrl = issueUrl;
    }

    public RepoPullModelClass(String userName, String avatarUrl, String id, String title) {
        this.avatarUrl = avatarUrl;
        this.userName = userName;
        this.id = id;
        this.title = title;
    }

    public static DiffUtil.ItemCallback<RepoPullModelClass> DIFF_CALLBACK = new DiffUtil.ItemCallback<RepoPullModelClass>() {
        @Override
        public boolean areItemsTheSame(@NonNull RepoPullModelClass oldItem, @NonNull RepoPullModelClass newItem) {
            return oldItem.id.equals(newItem.id);
        }

        @Override
        public boolean areContentsTheSame(@NonNull RepoPullModelClass oldItem, @NonNull RepoPullModelClass newItem) {
            return oldItem.equals(newItem);
        }
    };

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        RepoPullModelClass article = (RepoPullModelClass) obj;
        return article.id.equals(this.id);
    }
}
