package com.dimedriller.advancedfragment.actionbar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;
import android.support.v4.graphics.drawable.DrawableCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.DrawerIndicatorDrawable;
import android.support.v7.app.ActionBarIndicatorState;

import com.dimedriller.advancedutils.utils.MathUtils;

import java.util.EnumMap;
import java.util.Map;

public class ActionBarIndicatorDrawable extends DrawerIndicatorDrawable {
    private static final long DEFAULT_ANIMATION_DURATION_MILLIS = 500;
    private static final float ARROW_HEAD_ANGLE = (float) Math.toRadians(45);

    private float mArrowHeadBarLength;
    // the amount that overlaps w/ bar size when rotation is max
    private float mMaxCutForBarSize;
    private boolean mFlipToPointRight;

    // Whether we should mirror animation when animation is reversed.
    private boolean mIsVerticalMirror;

    private @NonNull ActionBarIndicatorState mState = ActionBarIndicatorState.HOME;

    private final DrawableAnimation mAnimation;
    private final DrawParams mTempDrawParams0 = new DrawParams();
    private final DrawParams mTempDrawParams1 = new DrawParams();
    private final DrawParams mTempDrawParams = new DrawParams();
    private final RectF mTempRect = new RectF();

    // Use Path instead of canvas operations so that if color has transparency, overlapping sections
    // wont look different
    private final Path mDrawPath = new Path();

    public ActionBarIndicatorDrawable(Context context) {
        super(context);

        DrawState drawState = DrawState.findState(mState);
        mAnimation = new DrawableAnimation(this, drawState.getParam0(), drawState.getParam1());

        setArrowHeadLength(getArrowHeadLength());
        setBarThickness(getBarThickness());
        setDirection(getDirection());
        setVerticalMirror(false);
    }

    @Override
    public void setArrowHeadLength(float length) {
        super.setArrowHeadLength(length);

        mArrowHeadBarLength = (float) (length * Math.sqrt(2));
    }

    @Override
    public void setBarThickness(float width) {
        super.setBarThickness(width);

        mMaxCutForBarSize = (float) (width / 2 * Math.cos(ARROW_HEAD_ANGLE));
    }

    @Override
    public void setDirection(int direction) {
        super.setDirection(direction);

        switch (direction) {
            case ARROW_DIRECTION_LEFT:
                mFlipToPointRight = false;
                break;
            case ARROW_DIRECTION_RIGHT:
                mFlipToPointRight = true;
                break;
            case ARROW_DIRECTION_END:
                mFlipToPointRight = DrawableCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_LTR;
                break;
            case ARROW_DIRECTION_START:
            default:
                mFlipToPointRight = DrawableCompat.getLayoutDirection(this) == ViewCompat.LAYOUT_DIRECTION_RTL;
                break;
        }
    }

    @Override
    public void setVerticalMirror(boolean verticalMirror) {
        super.setVerticalMirror(verticalMirror);

        mIsVerticalMirror = verticalMirror;
    }

    @Override
    public void showState(ActionBarIndicatorState state, boolean isForced) {
        if (state == mState && !isForced)
            return;

        mState = state;

        DrawState drawState = DrawState.findState(state);
        mAnimation.start(DEFAULT_ANIMATION_DURATION_MILLIS, drawState.getParam0(), drawState.getParam1());
    }

    private DrawParams calculateParams0(float progress0) {
        final float barLength = getBarLength();
        final float arrowHeadBarLength = MathUtils.lerp(barLength, mArrowHeadBarLength, progress0);
        final float arrowShaftLength = MathUtils.lerp(barLength,  getArrowShaftLength(), progress0);
        // Interpolated size of middle bar
        final float arrowShaftCut = Math.round(MathUtils.lerp(0, mMaxCutForBarSize, progress0));
        // The rotation of the top and bottom bars (that make the arrow head)
        final float rotation = MathUtils.lerp(0, ARROW_HEAD_ANGLE, progress0);
        final float arrowWidth = Math.round(arrowHeadBarLength * Math.cos(rotation));
        final float arrowHeight = Math.round(arrowHeadBarLength * Math.sin(rotation));
        final float strokeWidth = getPaint().getStrokeWidth();
        final float topBottomBarOffset = MathUtils.lerp(getGapSize() + strokeWidth, -mMaxCutForBarSize, progress0);
        final float arrowEdge = -arrowShaftLength / 2f;

        DrawParams drawParams = mTempDrawParams0;

        drawParams.mTopLineX = arrowEdge;
        drawParams.mTopLineY = topBottomBarOffset;
        //noinspection SuspiciousNameCombination
        drawParams.mTopLineDX = arrowWidth;
        drawParams.mTopLineDY = arrowHeight;

        drawParams.mCenterLineX = arrowEdge + arrowShaftCut;
        drawParams.mCenterLineY = 0f;
        drawParams.mCenterLineDX = arrowShaftLength - arrowShaftCut * 2f;
        drawParams.mCenterLineDY = 0f;

        drawParams.mBottomLineX = arrowEdge;
        drawParams.mBottomLineY = -topBottomBarOffset;
        //noinspection SuspiciousNameCombination
        drawParams.mBottomLineDX = arrowWidth;
        drawParams.mBottomLineDY = -arrowHeight;

        drawParams.mArcAngle = 0f;

        // The whole canvas rotates as the transition happens
        drawParams.mCanvasRotation = MathUtils.lerp(mFlipToPointRight ? 0f : -180f,
                mFlipToPointRight ? 180f : 0f,
                progress0);

        return drawParams;
    }

    private DrawParams calculateParams1(float progress0, float progress1) {
        final float arrowShaftLength = getArrowShaftLength();
        final float arrowEdge = -arrowShaftLength / 2f;
        final float centerLineLength = arrowShaftLength + 2f * mMaxCutForBarSize;
        final float arrowHeadLineLength = MathUtils.lerp(centerLineLength, 0, progress0) / 2f;

        DrawParams drawParams = mTempDrawParams1;

        drawParams.mTopLineX = 0f;
        drawParams.mTopLineY = 0f;
        drawParams.mTopLineDX = 0f;
        drawParams.mTopLineDY = arrowHeadLineLength;

        drawParams.mCenterLineX = arrowEdge - mMaxCutForBarSize;
        drawParams.mCenterLineY = 0f;
        drawParams.mCenterLineDX = centerLineLength;
        drawParams.mCenterLineDY = 0f;

        drawParams.mBottomLineX = 0f;
        drawParams.mBottomLineY = 0f;
        drawParams.mBottomLineDX = 0f;
        drawParams.mBottomLineDY = -arrowHeadLineLength;

        if (progress0 > 0.5f && progress1 > 0.5f)
            drawParams.mArcAngle = MathUtils.lerp(0, 360, (progress0 - 0.5f) * 2f);
        else
            drawParams.mArcAngle = 0f;

        drawParams.mCanvasRotation = MathUtils.lerp(45, 135, progress0);

        return drawParams;
    }

    @Override
    public void draw(Canvas canvas) {
        float[] progress = mAnimation.getCurrentValues();
        float progress0 = progress[0];
        float progress1 = progress[1];
        DrawParams drawParams0 = calculateParams0(progress0);
        DrawParams drawParams1 = calculateParams1(progress0, progress1);

        DrawParams drawParams = mTempDrawParams;
        drawParams.merge(drawParams0, drawParams1, progress1);

        Path drawPath = mDrawPath;
        drawPath.rewind();

        drawPath.moveTo(drawParams.mTopLineX, drawParams.mTopLineY);
        drawPath.rLineTo(drawParams.mTopLineDX, drawParams.mTopLineDY);
        drawPath.moveTo(drawParams.mCenterLineX, drawParams.mCenterLineY);
        drawPath.rLineTo(drawParams.mCenterLineDX, drawParams.mCenterLineDY);
        drawPath.moveTo(drawParams.mBottomLineX, drawParams.mBottomLineY);
        drawPath.rLineTo(drawParams.mBottomLineDX, drawParams.mBottomLineDY);

        Paint paint = getPaint();
        float barThickness = paint.getStrokeWidth();

        RectF arcRect = mTempRect;
        arcRect.left = drawParams.mCenterLineX - barThickness / 2f;
        arcRect.right = drawParams.mCenterLineX + drawParams.mCenterLineDX + barThickness / 2f;
        //noinspection SuspiciousNameCombination
        arcRect.top = arcRect.left;
        //noinspection SuspiciousNameCombination
        arcRect.bottom = arcRect.right;
        drawPath.addArc(arcRect, 0, drawParams.mArcAngle);

        canvas.save();

        Rect bounds = getBounds();
        float barGap = getGapSize();
        // Rotate the whole canvas if spinning, if not, rotate it 180 to get
        // the arrow pointing the other way for RTL.
        final int remainingSpace = (int) (bounds.height() - barThickness * 3 - barGap * 2);
        float yOffset = (remainingSpace / 4) * 2; // making sure it is a multiple of 2.
        yOffset += barThickness * 1.5f + barGap;

        canvas.translate(bounds.centerX(), yOffset);
        if (isSpinEnabled())
            canvas.rotate(drawParams.mCanvasRotation * ((mIsVerticalMirror ^ mFlipToPointRight) ? -1 : 1));
        else if (mFlipToPointRight)
            canvas.rotate(180);

        canvas.drawPath(drawPath, paint);

        canvas.restore();
    }

    enum DrawState {
        HOME(ActionBarIndicatorState.HOME, 0, 0),
        UP(ActionBarIndicatorState.BACK, 1, 0),
        CLOSE(ActionBarIndicatorState.CLOSE, 0, 1),
        NO_ACTION(ActionBarIndicatorState.NO_ACTION, 1, 1);

        private final ActionBarIndicatorState mIndicatorState;
        private final float mParam0;
        private final float mParam1;

        private static final Map<ActionBarIndicatorState, DrawState> STATE_MAP = new EnumMap<>(ActionBarIndicatorState.class);
        static {
            DrawState[] values = values();
            for(DrawState value : values)
                STATE_MAP.put(value.mIndicatorState, value);
        }

        DrawState(ActionBarIndicatorState indicatorState, float param0, float param1) {
            mIndicatorState = indicatorState;
            mParam0 = param0;
            mParam1 = param1;
        }

        float getParam0() {
            return mParam0;
        }

        float getParam1() {
            return mParam1;
        }

        static @NonNull DrawState findState(ActionBarIndicatorState indicatorState) {
            DrawState drawState = STATE_MAP.get(indicatorState);
            if (drawState == null)
                return HOME;
            else
                return drawState;
        }
    }

    static class DrawParams {
        float mTopLineX;
        float mTopLineY;
        float mTopLineDX;
        float mTopLineDY;

        float mCenterLineX;
        float mCenterLineY;
        float mCenterLineDX;
        float mCenterLineDY;

        float mBottomLineX;
        float mBottomLineY;
        float mBottomLineDX;
        float mBottomLineDY;

        float mArcAngle;

        float mCanvasRotation;

        void merge(DrawParams params1, DrawParams params2, float progress) {
            mTopLineX = MathUtils.lerp(params1.mTopLineX, params2.mTopLineX, progress);
            mTopLineY = MathUtils.lerp(params1.mTopLineY, params2.mTopLineY, progress);
            mTopLineDX = MathUtils.lerp(params1.mTopLineDX, params2.mTopLineDX, progress);
            mTopLineDY = MathUtils.lerp(params1.mTopLineDY, params2.mTopLineDY, progress);

            mCenterLineX = MathUtils.lerp(params1.mCenterLineX, params2.mCenterLineX, progress);
            mCenterLineY = MathUtils.lerp(params1.mCenterLineY, params2.mCenterLineY, progress);
            mCenterLineDX = MathUtils.lerp(params1.mCenterLineDX, params2.mCenterLineDX, progress);
            mCenterLineDY = MathUtils.lerp(params1.mCenterLineDY, params2.mCenterLineDY, progress);

            mBottomLineX = MathUtils.lerp(params1.mBottomLineX, params2.mBottomLineX, progress);
            mBottomLineY = MathUtils.lerp(params1.mBottomLineY, params2.mBottomLineY, progress);
            mBottomLineDX = MathUtils.lerp(params1.mBottomLineDX, params2.mBottomLineDX, progress);
            mBottomLineDY = MathUtils.lerp(params1.mBottomLineDY, params2.mBottomLineDY, progress);

            mArcAngle = MathUtils.lerp(params1.mArcAngle, params2.mArcAngle, progress);

            mCanvasRotation = MathUtils.lerp(params1.mCanvasRotation, params2.mCanvasRotation, progress);
        }
    }
}
