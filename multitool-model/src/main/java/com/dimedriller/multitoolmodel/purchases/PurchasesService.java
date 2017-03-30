package com.dimedriller.multitoolmodel.purchases;

import android.app.Service;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.util.LongSparseArray;

import com.dimedriller.advancedmodel.result.ErrorType;
import com.dimedriller.advancedmodel.result.FailureListener;
import com.dimedriller.advancedmodel.result.ProgressListener;
import com.dimedriller.advancedmodel.result.SuccessListener;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class PurchasesService extends Service {
    private @Nullable PurchasesManagerImpl mBinder;

    private @Nullable LongSparseArray<Purchase[]> mPurchasesMap;

    @Override
    public IBinder onBind(Intent intent) {
        mBinder = new PurchasesManagerImpl(this);
        return mBinder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        mBinder = null;
        mPurchasesMap = null;
        return false;
    }

    static LongSparseArray<Purchase[]> parsePurchases(BufferedReader reader) throws IOException {
        Map<Long, List<Purchase>> purchaseListMap = new HashMap<>();
        List<Purchase> purchaseList = new ArrayList<>();
        DateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd");

        for(String line = reader.readLine(); line != null; line = reader.readLine()) {
            line = line.trim();
            if (line.isEmpty())
                continue;

            try {
                Date date = dateFormat.parse(line);
                long dateMillis = date.getTime();
                if (purchaseListMap.containsKey(dateMillis))
                    purchaseList = purchaseListMap.get(dateMillis);
                else {
                    purchaseList = new ArrayList<>();
                    purchaseListMap.put(dateMillis, purchaseList);
                }
            } catch (ParseException e) {
                Purchase purchase = Purchase.parsePurchase(line);
                purchaseList.add(purchase);
            }
        }

        Set<Long> datesMillis = purchaseListMap.keySet();
        LongSparseArray<Purchase[]> purchasesMap = new LongSparseArray<>(datesMillis.size());
        for(Long dateMillis : datesMillis) {
            purchaseList = purchaseListMap.get(dateMillis);
            if (purchaseList.isEmpty())
                continue;

            Purchase[] purchases = new Purchase[purchaseList.size()];
            purchaseList.toArray(purchases);

            purchasesMap.put(dateMillis, purchases);
        }
        return purchasesMap;
    }

    @Nullable Purchase[] findPurchasesByTime(long timeMillis) {
        if (mPurchasesMap == null)
            return null;

        int numKeys = mPurchasesMap.size();
        long topDiff = Long.MAX_VALUE;
        long topKey = Long.MAX_VALUE;
        for(int indexKey = 0; indexKey < numKeys; indexKey++) {
            long key = mPurchasesMap.keyAt(indexKey);
            long diff = Math.abs(timeMillis - key);
            if (diff < topDiff) {
                topDiff = diff;
                topKey = key;
            }
        }
        return mPurchasesMap.get(topKey);
    }

    void fetchPurchases(PurchasesManagerImpl manager,
            final long timeMillis,
            @Nullable ProgressListener progressListener,
            final @NonNull SuccessListener<Purchase[]> successListener,
            final @NonNull FailureListener failureListener) {
        if (manager != mBinder) {
            failureListener.onFailure(ErrorType.SERVICE_NOT_INITIALIZED);
            return;
        }

        Purchase[] purchases = findPurchasesByTime(timeMillis);
        if (purchases != null) {
            successListener.onSuccess(purchases);
            return;
        }

        if (progressListener != null)
            progressListener.onProgress();

        new AsyncTask<Void, Void, ErrorType>() {
            @Override
            protected ErrorType doInBackground(Void... params) {
                InputStream is = null;

                try {
                    URL url = new URL("https://dl.dropboxusercontent.com/u/59603736/purchase_list.txt");
                    URLConnection connection = url.openConnection();
                    connection.setReadTimeout(5000);
                    connection.setConnectTimeout(10000);

                    is = connection.getInputStream();
                    BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                    mPurchasesMap = parsePurchases(reader);

                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                    return ErrorType.NETWORK;
                } finally {
                    if (is != null)
                        try {
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                }
                return null;
            }

            @Override
            protected void onPostExecute(ErrorType errorType) {
                if (errorType == null) {
                    Purchase[] purchases = findPurchasesByTime(timeMillis);
                    successListener.onSuccess(purchases);
                } else
                    failureListener.onFailure(errorType);
            }
        }.execute();
    }
}
