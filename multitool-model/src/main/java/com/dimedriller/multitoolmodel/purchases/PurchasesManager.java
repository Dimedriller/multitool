package com.dimedriller.multitoolmodel.purchases;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dimedriller.advancedmodel.result.FailureListener;
import com.dimedriller.advancedmodel.result.ProgressListener;
import com.dimedriller.advancedmodel.result.SuccessListener;

public interface PurchasesManager {
    void fetchPurchases(long timeMillis,
            @Nullable ProgressListener progressListener,
            @NonNull SuccessListener<Purchase[]> successListener,
            @NonNull FailureListener failureListener);
}
