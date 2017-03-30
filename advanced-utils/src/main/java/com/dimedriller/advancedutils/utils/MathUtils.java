package com.dimedriller.advancedutils.utils;

public class MathUtils {
    /**
     * Linear interpolate between a and b with parameter t.
     */
    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    public static float minMax(float value, float valueMin, float valueMax) {
        return Math.min(valueMax, Math.max(value, valueMin));
    }
}
