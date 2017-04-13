package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

class Transaction {
    private final TransactionStep[] mSteps;

    Transaction(TransactionStep[] steps) {
        mSteps = steps;
    }

    void actDirect(PresenterManager manager) {
        for(TransactionStep step : mSteps)
            step.actDirect(manager);
    }

    void actReverse(PresenterManager manager) {
        for(TransactionStep step : mSteps)
            step.actReverse(manager);
    }
}
