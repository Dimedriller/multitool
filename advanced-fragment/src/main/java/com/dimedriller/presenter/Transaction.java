package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private final List<TransactionStep> mStepList = new ArrayList<>();
    private @Nullable JournalStep mJournalStep;

    public Transaction addPresenter(@NonNull ViewAnchor anchor, @NonNull PresenterBuilder presenterBuilder) {
        String tag = presenterBuilder.getTag();
        TransactionAddStep step = new TransactionAddStep(anchor, presenterBuilder, tag);
        mStepList.add(step);
        return this;
    }

    public Transaction removePresenter(@NonNull String tag) {
        TransactionRemoveStep step = new TransactionRemoveStep(tag);
        mStepList.add(step);
        return this;
    }

    public Transaction pushTransaction(@Nullable String stackName) {
        mJournalStep = new JournalPushStep(stackName, this);
        return this;
    }

    public Transaction pushTransaction() {
        return pushTransaction(null);
    }

    public Transaction popTransaction(@Nullable String stackName) {
        mJournalStep = new JournalPopStep(stackName);
        return this;
    }

    public Transaction popTransaction() {
        return popTransaction(null);
    }

    public void commit(PresenterManager manager) {
        for(TransactionStep step : mStepList)
            step.actDirect(manager);

        if (mJournalStep != null)
            mJournalStep.act(manager);
    }
}
