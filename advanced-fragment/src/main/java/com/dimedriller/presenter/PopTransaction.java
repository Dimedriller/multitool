package com.dimedriller.presenter;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.dimedriller.log.Log;

import java.util.ArrayList;
import java.util.List;

public class PopTransaction {
    private final PresenterManager mManager;
    private final @Nullable String mStackName;



    PopTransaction(PresenterManager manager, String stackName) {
        mManager = manager;
        mStackName = stackName;
    }


}
