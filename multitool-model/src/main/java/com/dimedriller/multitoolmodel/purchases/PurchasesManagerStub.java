package com.dimedriller.multitoolmodel.purchases;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dimedriller.advancedmodel.result.ErrorType;
import com.dimedriller.advancedmodel.result.FailureListener;
import com.dimedriller.advancedmodel.result.ProgressListener;
import com.dimedriller.advancedmodel.result.SuccessListener;

public class PurchasesManagerStub implements PurchasesManager {
    @Override
    public void fetchPurchases(long timeMillis,
            @Nullable ProgressListener progressListener,
            @NonNull SuccessListener<Purchase[]> successListener,
            @NonNull FailureListener failureListener) {
        failureListener.onFailure(ErrorType.SERVICE_NOT_INITIALIZED);
    }
}
