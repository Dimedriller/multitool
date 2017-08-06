package com.dimedriller.presenter;

import android.os.Parcelable;

abstract class TransactionStep implements Parcelable {
    abstract void actDirect(PresenterManager manager);
    abstract void actReverse(PresenterManager manager);
    abstract boolean isOpposite(TransactionStep other);
}
