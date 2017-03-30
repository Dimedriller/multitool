package com.dimedriller.multitoolmodel;

import android.content.Context;
import android.support.annotation.NonNull;

import com.dimedriller.advancedmodel.Model;
import com.dimedriller.multitoolmodel.purchases.PurchasesManager;
import com.dimedriller.multitoolmodel.purchases.PurchasesServiceLink;

public class MultitoolModel extends Model {
    private final PurchasesServiceLink mPurchaseServiceLink = new PurchasesServiceLink();

    public void startServices(@NonNull Context context) {
        mPurchaseServiceLink.startService(context);
    }

    public void stopServices(@NonNull Context context) {
        mPurchaseServiceLink.stopService(context);
    }

    public @NonNull PurchasesManager getPurchaseManager() {
        return mPurchaseServiceLink.getPurchaseManager();
    }
}
