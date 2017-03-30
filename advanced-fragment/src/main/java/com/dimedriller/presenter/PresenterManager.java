package com.dimedriller.presenter;

import android.support.annotation.Nullable;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PresenterManager {
    final PresenterContainer mPresenterContainer;

    private final Map<String, PresenterRecord> mPresenterMap = new HashMap<>();
    private final Map<String, List<Transaction>> mTransactionStackMap = new HashMap<>();

    public PresenterManager(PresenterContainer presenterContainer) {
        mPresenterContainer = presenterContainer;
    }

    void pushTransaction(String stackName, Transaction transaction) {
        List<Transaction> transactionStack = mTransactionStackMap.get(stackName);
        if (transactionStack == null) {
            transactionStack = new ArrayList<>();
            mTransactionStackMap.put(stackName, transactionStack);
        }

        transactionStack.add(0, transaction);
    }

    public void popTransaction(@Nullable String stackName) {

    }

    public void popTransaction() {
        popTransaction(null);
    }
}
