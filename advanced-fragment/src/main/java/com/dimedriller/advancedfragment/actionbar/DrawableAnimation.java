package com.dimedriller.advancedfragment.actionbar;

import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.os.Looper;

import com.dimedriller.advancedutils.utils.MathUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class DrawableAnimation {
    private final Drawable mDrawable;

    private final int mNumValues;
    private final float[] mCurrentValues;
    private final float[] mStartValues;
    private final float[] mEndValues;

    private long mStartTimeMillis;
    private long mDurationMillis;

    private final Handler mHandler = new Handler(Looper.getMainLooper());
    private final Runnable mUpdateRunnable = new Runnable() {
        @Override
        public void run() {
            onUpdate();
        }
    };

    DrawableAnimation(Drawable drawable, float... currentValues) {
        mDrawable = drawable;

        mNumValues = currentValues.length;

        mCurrentValues = new float[mNumValues];
        System.arraycopy(currentValues, 0, mCurrentValues, 0, mNumValues);

        mStartValues = new float[mNumValues];
        System.arraycopy(currentValues, 0, mStartValues, 0, mNumValues);

        mEndValues = new float[mNumValues];
        System.arraycopy(currentValues, 0, mStartValues, 0, mNumValues);
    }

    void onUpdate() {
        long deltaMillis = System.currentTimeMillis() - mStartTimeMillis;
        if (deltaMillis < mDurationMillis)
            mHandler.post(mUpdateRunnable);

        float progress = 1f * deltaMillis / mDurationMillis;
        progress = MathUtils.minMax(progress, 0f, 1f);

        if (progress == 1f)
            System.arraycopy(mEndValues, 0, mCurrentValues, 0, mNumValues);
        else
            for(int indexValue = 0; indexValue < mNumValues; indexValue++)
                mCurrentValues[indexValue] = MathUtils.lerp(mStartValues[indexValue], mEndValues[indexValue], progress);

        mDrawable.invalidateSelf();
    }

    private int[] countActiveValues(float[] values) {
        List<Integer> activeValueList = new ArrayList<>();
        for(int indexValue = 0; indexValue < mNumValues; indexValue++)
            if (mCurrentValues[indexValue] != values[indexValue])
                activeValueList.add(indexValue);

        int numActiveValues = activeValueList.size();
        int[] activeValues = new int[numActiveValues];
        for(int indexValue = 0; indexValue < numActiveValues; indexValue++)
            activeValues[indexValue] = activeValueList.get(indexValue);

        return activeValues;
    }

    void start(long durationMillis, float... values) {
        if (Arrays.equals(mEndValues, values))
            return;

        mHandler.removeCallbacks(mUpdateRunnable);

        long currentTimeMillis = System.currentTimeMillis();
        if (mStartTimeMillis + mDurationMillis > currentTimeMillis) {
            int[] currentActiveStates = countActiveValues(mEndValues);
            int[] newActiveStates = countActiveValues(values);

            if (Arrays.equals(currentActiveStates, newActiveStates))
                mDurationMillis = currentTimeMillis - mStartTimeMillis;
            else
                mDurationMillis = durationMillis;
        } else
            mDurationMillis = durationMillis;

        mStartTimeMillis = currentTimeMillis;
        System.arraycopy(mCurrentValues, 0, mStartValues, 0, mNumValues);
        System.arraycopy(values, 0, mEndValues, 0, mNumValues);

        mUpdateRunnable.run();
    }

    float[] getCurrentValues() {
        return mCurrentValues;
    }
}
