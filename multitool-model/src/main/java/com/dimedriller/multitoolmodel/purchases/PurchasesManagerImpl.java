package com.dimedriller.multitoolmodel.purchases;

import android.os.Binder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dimedriller.advancedmodel.result.ErrorType;
import com.dimedriller.advancedmodel.result.FailureListener;
import com.dimedriller.advancedmodel.result.ProgressListener;
import com.dimedriller.advancedmodel.result.SuccessListener;

import java.lang.ref.WeakReference;

public class PurchasesManagerImpl extends Binder implements PurchasesManager {
    private final WeakReference<PurchasesService> mServiceRef;

    public PurchasesManagerImpl(PurchasesService service) {
        mServiceRef = new WeakReference<>(service);
    }

    @Override
    public void fetchPurchases(long timeMillis,
            @Nullable ProgressListener progressListener,
            @NonNull SuccessListener<Purchase[]> successListener,
            @NonNull FailureListener failureListener) {
        PurchasesService service = mServiceRef.get();
        if (service == null)
            failureListener.onFailure(ErrorType.SERVICE_NOT_INITIALIZED);
        else
            service.fetchPurchases(this, timeMillis, progressListener, successListener, failureListener);
    }
}
