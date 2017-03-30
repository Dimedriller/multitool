package com.dimedriller.multitoolmodel.purchases;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.annotation.NonNull;

public class PurchasesServiceLink {
    private @NonNull PurchasesManager mPurchasesManager = new PurchasesManagerStub();
    private final ServiceConnection mServiceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mPurchasesManager = (PurchasesManager) service;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mPurchasesManager = new PurchasesManagerStub();
        }
    };


    public void startService(@NonNull Context context) {
        Intent intent = new Intent(context, PurchasesService.class);
        context.bindService(intent, mServiceConnection, Context.BIND_AUTO_CREATE);
    }

    public void stopService(@NonNull Context context) {
        context.unbindService(mServiceConnection);
    }

    public PurchasesManager getPurchaseManager() {
        return mPurchasesManager;
    }
}
