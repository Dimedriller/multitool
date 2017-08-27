package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dimedriller.log.Log;

import java.util.ArrayList;
import java.util.List;

public class PushTransaction {
    private final @NonNull PresenterManager mManager;
    private final @Nullable String mStackName;

    private final List<TransactionStep> mStepList = new ArrayList<>();

    PushTransaction(@NonNull PresenterManager manager, @Nullable String stackName) {
        mManager = manager;
        mStackName = stackName;
    }

    public PushTransaction addPresenter(@NonNull PresenterBuilder presenterBuilder) {
        String tag = presenterBuilder.getTag();
        mStepList.add(new TransactionAttachStep(tag, presenterBuilder));
        mStepList.add(new TransactionStepSetViewPlacer(tag, new SimpleViewPlacer()));
        mStepList.add(new TransactionShowStep(tag));
        mStepList.add(new TransactionResumeStep(tag));
        return this;
    }

    public PushTransaction showPresenter(@NonNull String tag) {
        mStepList.add(new TransactionShowStep(tag));
        mStepList.add(new TransactionResumeStep(tag));
        return this;
    }

    public PushTransaction hidePresenter(@NonNull String tag) {
        mStepList.add(new TransactionPauseStep(tag));
        mStepList.add(new TransactionHideStep(tag));
        return this;
    }

    public void commit() {
        int stepCount = mStepList.size();
        if (stepCount == 0) {
            Log.w("Empty transaction");
            return;
        }

        TransactionStepGroup transaction = new TransactionStepGroup(mStepList);
        mManager.pushTransaction(mStackName, transaction);
    }
}
