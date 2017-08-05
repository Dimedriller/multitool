package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dimedriller.log.Log;

import java.util.ArrayList;
import java.util.List;

public class PushTransaction {
    private final @NonNull PresenterManager mManager;
    private final @NonNull PresenterContainer mPresenterContainer;
    private final @Nullable String mStackName;

    private final List<TransactionStep> mStepList = new ArrayList<>();

    public PushTransaction(@NonNull PresenterManager manager,
            @NonNull PresenterContainer presenterContainer,
            @Nullable String stackName) {
        mManager = manager;
        mPresenterContainer = presenterContainer;
        mStackName = stackName;
    }

    public PushTransaction addPresenter(@NonNull PresenterBuilder presenterBuilder) {
        String tag = presenterBuilder.getTag();
        Presenter presenter = presenterBuilder.build(mPresenterContainer);

        mStepList.add(new TransactionAttachStep(tag, presenter));
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
        transaction.actDirect(mManager);
    }
}
