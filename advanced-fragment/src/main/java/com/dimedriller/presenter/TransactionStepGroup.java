package com.dimedriller.presenter;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class TransactionStepGroup implements Parcelable {
    public static final Creator<TransactionStepGroup> CREATOR = new Creator<TransactionStepGroup>() {
        @Override
        public TransactionStepGroup createFromParcel(Parcel source) {
            return new TransactionStepGroup(source);
        }

        @Override
        public TransactionStepGroup[] newArray(int size) {
            return new TransactionStepGroup[size];
        }
    };

    private final TransactionStep[] mSteps;

    TransactionStepGroup(List<TransactionStep> stepList) {
        mSteps = new TransactionStep[stepList.size()];
        stepList.toArray(mSteps);
    }

    TransactionStepGroup(Parcel source) {
        mSteps = (TransactionStep[]) source.readArray(TransactionStep.class.getClassLoader());
    }

    void actDirect(PresenterManager manager) {
        int countStep = mSteps.length;
        //noinspection ForLoopReplaceableByForEach
        for(int indexStep = 0; indexStep < countStep; indexStep++) {
            TransactionStep step = mSteps[indexStep];
            step.actDirect(manager);
        }
    }

    void actReverse(PresenterManager manager) {
        for(int indexStep = mSteps.length - 1; indexStep >=0; indexStep--) {
            TransactionStep step = mSteps[indexStep];
            step.actReverse(manager);
        }
    }

    private static int findOppositeStepIndex(List<TransactionStep> stepList, TransactionStep lookUpStep) {
        int countStep = stepList.size();
        for(int indexStep = 0; indexStep < countStep; indexStep++) {
            TransactionStep step = stepList.get(indexStep);
            if (lookUpStep.isOpposite(step))
                return indexStep;
        }
        return -1;
    }

    private static TransactionStepGroup merge(TransactionStep[] first, TransactionStep[] last) {
        List<TransactionStep> mergedStepList = Arrays.asList(first);
        List<TransactionStep> remainingStepList = new ArrayList<>();

        for(TransactionStep step : last) {
            int indexOppositeStep = findOppositeStepIndex(mergedStepList, step);
            if (indexOppositeStep == -1)
                remainingStepList.add(step);
            else
                mergedStepList.remove(indexOppositeStep);
        }

        mergedStepList.addAll(remainingStepList);

        return new TransactionStepGroup(mergedStepList);
    }

    TransactionStepGroup mergeForward(TransactionStepGroup otherGroup) {
        return merge(mSteps, otherGroup.mSteps);
    }

    /**
     * Merges steps with another {@code TransactionStepGroup} and returns new {@code TransactionStepGroup}.
     *
     * @param otherGroup - other group to merge
     *
     * Note: Order of steps is significantly important so {@code for} with index loop is used
     */
    TransactionStepGroup mergeBack(TransactionStepGroup otherGroup) {
        return merge(otherGroup.mSteps, mSteps);
    }

    boolean containsAttachStep(String tag) {
        for(TransactionStep step : mSteps) {
            if (!(step instanceof TransactionAttachStep))
                continue;

            TransactionAttachStep attachStep = (TransactionAttachStep) step;
            if (attachStep.getTag().equals(tag))
                return true;
        }
        return false;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeParcelableArray(mSteps, flags);
    }
}
