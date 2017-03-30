package com.dimedriller.multitoolmodel.purchases;

import android.support.v4.util.LongSparseArray;

import junit.framework.Assert;
import junit.framework.TestCase;

import java.io.BufferedReader;
import java.io.StringReader;

public class PurchasesServiceTest extends TestCase {
    public void testParsePurchases() throws Exception {
        BufferedReader reader = new BufferedReader(new StringReader("2017.02.25\n" +
                "Cucumbers 2\n" +
                "Apples\n" +
                "Tenderloin 2\n" +
                "Potato\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Air Filter\n" +
                "Tomatoes\n" +
                "Raspberry 4\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Pork\n" +
                "\n" +
                "\n" +
                "2017.02.18\n" +
                "Tangerines\n" +
                "Ribeye 2\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Tomatoes\n" +
                "Raspberry 2\n" +
                "Strawberry 2\n" +
                "Sour cream\n" +
                "Ground pork\n" +
                "\n" +
                "2017.02.11\n" +
                "Cucumbers\n" +
                "Apples\n" +
                "T-bone 3\n" +
                "Potato\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Chicken thighs\n" +
                "\n" +
                "\n" +
                "2017.02.4\n" +
                "Cucumbers 2\n" +
                "Apples\n" +
                "Potato\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Tomatoes\n" +
                "Sour cream\n" +
                "Chicken whole\n" +
                "\n" +
                "\n" +
                "2017.01.29\n" +
                "Cucumbers\n" +
                "Tenderloin 4\n" +
                "Tomatoes\n" +
                "Raspberry 4\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Pork\n" +
                "\n" +
                "2017.01.21\n" +
                "Cucumbers 2\n" +
                "Apples\n" +
                "Ryb eye\n" +
                "Potato\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Air Filter\n" +
                "Tomatoes\n" +
                "Sour cream\n" +
                "Pork\n" +
                "\n" +
                "2017.01.15\n" +
                "Cucumbers 2\n" +
                "Apples\n" +
                "Tenderloin 2\n" +
                "Potato\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Pork\n" +
                "\n" +
                "2017.01.8\n" +
                "Cucumbers 2\n" +
                "Apples\n" +
                "Tenderloin 2\n" +
                "Potato\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Bowl\n" +
                "Soccer ball\n" +
                "Tomatoes\n" +
                "Raspberry 4\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Pork\n" +
                "\n" +
                "2016.12.29\n" +
                "Potato\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Tomatoes\n" +
                "Raspberry 4\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Sugar\n" +
                "\n" +
                "2016.12.24\n" +
                "Cucumbers 2\n" +
                "Apples\n" +
                "Cottage cheese\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Honey\n" +
                "Corn oil\n" +
                "\n" +
                "2016.12.29\n" +
                "Cucumbers 2\n" +
                "Apples\n" +
                "T-bone 4\n" +
                "Tomatoes\n" +
                "Raspberry 4\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Pork\n" +
                "\n" +
                "2016.12.18\n" +
                "Cucumbers 2\n" +
                "Apples\n" +
                "Potato\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Air Filter\n" +
                "Tomatoes\n" +
                "Raspberry 2\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Chicken whole\n" +
                "\n" +
                "2016.12.10\n" +
                "Potato\n" +
                "Milk\n" +
                "Cottage cheese\n" +
                "Tomatoes\n" +
                "Raspberry 4\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Salmon\n" +
                "\n" +
                "2016.12.4\n" +
                "Cucumbers 2\n" +
                "Tenderloin 2\n" +
                "Potato\n" +
                "Cottage cheese\n" +
                "Air Filter\n" +
                "Tomatoes\n" +
                "Raspberry 4\n" +
                "Strawberry 2\n" +
                "Blackberry 1\n" +
                "Blueberry\n" +
                "Sour cream\n" +
                "Sausages"));
        LongSparseArray<Purchase[]> purchasesMap = PurchasesService.parsePurchases(reader);
        Assert.assertTrue(purchasesMap.size() == 13);
    }

}