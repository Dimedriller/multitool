package com.dimedriller.multitoolmodel.purchases;

public class Purchase {
    private final String mName;
    private final int mCount;

    public Purchase(String name, int count) {
        mName = name;
        mCount = count;
    }

    public String getName() {
        return mName;
    }

    public int getCount() {
        return mCount;
    }

    static Purchase parsePurchase(String rawString) {
        int indexLastSpace = rawString.lastIndexOf(' ');
        if (indexLastSpace == -1)
            return new Purchase(rawString, 1);

        String numberString = rawString.substring(indexLastSpace + 1);
        try {
            int number = Integer.valueOf(numberString);
            String name = rawString.substring(0, indexLastSpace);
            return new Purchase(name, number);
        } catch (NumberFormatException e) {
            return new Purchase(rawString, 1);
        }
    }
}
