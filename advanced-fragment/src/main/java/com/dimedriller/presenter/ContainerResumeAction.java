package com.dimedriller.presenter;

import java.util.List;

class ContainerResumeAction implements PendingAction {
    private final String[] mTags;

    public ContainerResumeAction(List<String> tagList) {
        mTags = new String[tagList.size()];
        tagList.toArray(mTags);
    }

    @Override
    public boolean isPersistable() {
        return false;
    }

    @Override
    public void act(PresenterManager manager) {
        for(String tag : mTags)
            manager.resumePresenter(tag);
    }
}
