package com.dimedriller.advancedutils.utils;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.SparseArray;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParcelUtil {
    public static <T extends Parcelable> void writeStringMap(Parcel dest, Map<String, T> map) {
        dest.writeInt(map.size());
        Set<Map.Entry<String, T>> entrySet = map.entrySet();
        for(Map.Entry<String, T> entry : entrySet) {
            dest.writeString(entry.getKey());
            dest.writeParcelable(entry.getValue(), 0);
        }
    }

    public static <T extends Parcelable> Map<String, T> readStringMap(Parcel source, Class<T> valueClass) {
        int numRecords = source.readInt();
        Map<String, T> map = new HashMap<>();
        ClassLoader valueClassLoader = valueClass.getClassLoader();
        for(int indexRecord = 0; indexRecord < numRecords; indexRecord++) {
            String key = source.readString();
            T value = source.readParcelable(valueClassLoader);
            map.put(key, value);
        }
        return map;
    }

    public static <T extends Parcelable> void writeSparseArray(Parcel dest, SparseArray<T> array, int flags) {
        int size = array.size();
        dest.writeInt(size);
        for(int index = 0; index < size; index++) {
            dest.writeInt(array.keyAt(index));
            dest.writeParcelable(array.valueAt(index), flags);
        }
    }

    public static <T extends Parcelable> SparseArray<T> readSparseArray(Parcel source, Class<T> itemClass) {
        int size = source.readInt();
        ClassLoader classLoader = itemClass.getClassLoader();
        SparseArray<T> array = new SparseArray<>(size);
        for(int index = 0; index < size; index++) {
            int key = source.readInt();
            T value = source.readParcelable(classLoader);
            array.put(key, value);
        }
        return array;
    }
}
