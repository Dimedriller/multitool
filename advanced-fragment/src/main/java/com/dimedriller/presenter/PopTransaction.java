package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class PopTransaction {
    private final @NonNull PresenterManager mManager;
    private final @Nullable String mStackName;

    private int mPopTransactionCount = 1;
    private @Nullable String mPopToTagName;

    PopTransaction(@NonNull PresenterManager manager, @Nullable String stackName) {
        mManager = manager;
        mStackName = stackName;
    }

    public PopTransaction transactionCount(int transactionCount) {
        mPopTransactionCount = transactionCount;
        mPopToTagName = null;
        return this;
    }

    public PopTransaction toTagName(@NonNull String toTagName) {
        mPopToTagName = toTagName;
        return this;
    }

    public PopTransaction all() {
        mPopTransactionCount = Integer.MAX_VALUE;
        mPopToTagName = null;
        return this;
    }

    public void commit() {
        if (mPopToTagName == null)
            mManager.popTransaction(mStackName, mPopTransactionCount);
        else
            mManager.popTransaction(mStackName, mPopToTagName);
    }
}
