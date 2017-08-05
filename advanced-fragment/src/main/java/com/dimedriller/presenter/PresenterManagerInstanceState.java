package com.dimedriller.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import com.dimedriller.advancedutils.utils.ParcelUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

final class PresenterManagerInstanceState implements Parcelable {
    public static final Creator<PresenterManagerInstanceState> CREATOR = new Creator<PresenterManagerInstanceState>() {
        @Override
        public PresenterManagerInstanceState createFromParcel(Parcel source) {
            return new PresenterManagerInstanceState(source);
        }

        @Override
        public PresenterManagerInstanceState[] newArray(int size) {
            return new PresenterManagerInstanceState[size];
        }
    };

    private final Map<String, PresenterParcelableInstance> mPresenterMap;
    private final Map<String, List<TransactionStepGroup>> mTransactionStackMap;

    public PresenterManagerInstanceState(Map<String, PresenterParcelableInstance> presenterMap,
            Map<String, List<TransactionStepGroup>> transactionStackMap) {
        mPresenterMap = presenterMap;
        mTransactionStackMap = transactionStackMap;
    }

    PresenterManagerInstanceState(Parcel source) {
        mPresenterMap = ParcelUtil.readStringMap(source, PresenterParcelableInstance.class);

        int numEntries = source.readInt();
        mTransactionStackMap = new HashMap<>(numEntries);
        for(int indexEntry = 0; indexEntry < numEntries; indexEntry++) {
            String key = source.readString();
            List<TransactionStepGroup> transactionStack = source.createTypedArrayList(TransactionStepGroup.CREATOR);
            mTransactionStackMap.put(key, transactionStack);
        }
    }

    public Map<String, PresenterParcelableInstance> getPresenterMap() {
        return mPresenterMap;
    }

    public Map<String, List<TransactionStepGroup>> getTransactionStackMap() {
        return mTransactionStackMap;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        ParcelUtil.writeStringMap(dest, mPresenterMap);

        dest.writeInt(mTransactionStackMap.size());
        Set<Map.Entry<String, List<TransactionStepGroup>>> transactionStackEntrySet = mTransactionStackMap.entrySet();
        for(Map.Entry<String, List<TransactionStepGroup>> entry : transactionStackEntrySet) {
            String key = entry.getKey();
            dest.writeString(key);
            List<TransactionStepGroup> transactionStack = entry.getValue();
            dest.writeTypedList(transactionStack);
        }
    }
}
