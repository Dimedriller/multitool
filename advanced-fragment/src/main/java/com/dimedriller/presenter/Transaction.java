package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private final List<TransactionStep> mStepList = new ArrayList<>();
    private boolean mIsPushedToStack = false;
    private String mStackName = null;

    public Transaction addPresenter(@NonNull ViewAnchor anchor, @NonNull PresenterBuilder presenter) {

    }

    public Transaction removePresenter(@NonNull Presenter presenter) {

    }

    public Transaction pushTransaction(@Nullable String stackName) {
        mIsPushedToStack = true;
        mStackName = stackName;
        return this;
    }

    public Transaction pushTransaction() {
        return pushTransaction(null);
    }

    public void commit(PresenterManager manager) {
        for(TransactionStep step : mStepList)
            step.actDirect(manager);

        if (mIsPushedToStack)
            manager.pushTransaction(mStackName, this);
    }
}
