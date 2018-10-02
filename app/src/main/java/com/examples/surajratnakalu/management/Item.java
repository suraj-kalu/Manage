package com.examples.surajratnakalu.management;

public class Item {

    private String mClassItemName;
    private double mClassQuantity;
    private double mClassRate;
    private String mClassDate;
    private  double mClassTotalCost;

    public Item(String classItemName, double classQuantity, double classRate, String classDate, double classTotalCost) {
        mClassItemName = classItemName;
        mClassQuantity = classQuantity;
        mClassRate = classRate;
        mClassDate = classDate;
        mClassTotalCost = classTotalCost;
    }

    public String getClassItemName() {
        return mClassItemName;
    }

    public double getClassQuantity() {
        return mClassQuantity;
    }

    public double getClassRate() {
        return mClassRate;
    }

    public String getClassDate() {
        return mClassDate;
    }

    public double getClassTotalCost() {
        return mClassTotalCost;
    }
}
