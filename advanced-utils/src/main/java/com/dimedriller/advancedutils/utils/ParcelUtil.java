package com.dimedriller.advancedutils.utils;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class ParcelUtil {
    public static <V extends Parcelable> void writeStringMap(Parcel dest, Map<String, V> map) {
        dest.writeInt(map.size());
        Set<String> keySet = map.keySet();
        for(String key : keySet) {
            dest.writeString(key);
            V value = map.get(key);
            dest.writeParcelable(value, 0);
        }
    }

    public static <V extends Parcelable> Map<String, V> readStringMap(Parcel source, Class<V> valueClass) {
        int numRecords = source.readInt();
        Map<String, V> map = new HashMap<>();
        ClassLoader valueClassLoader = valueClass.getClassLoader();
        for(int indexRecord = 0; indexRecord < numRecords; indexRecord++) {
            String key = source.readString();
            V value = source.readParcelable(valueClassLoader);
            map.put(key, value);
        }
        return map;
    }
}
