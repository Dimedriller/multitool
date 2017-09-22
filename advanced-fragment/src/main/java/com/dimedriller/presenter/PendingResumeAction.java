package com.dimedriller.presenter;

import java.util.List;

class PendingResumeAction implements PendingAction {
    private final String[] mTags;

    PendingResumeAction(List<String> tagList) {
        mTags = new String[tagList.size()];
        tagList.toArray(mTags);
    }

    @Override
    public void act(PresenterManager manager) {
        for(String tag : mTags)
            manager.resumePresenter(tag);
    }
}
