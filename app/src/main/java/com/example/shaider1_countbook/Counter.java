/*
 * Counter
 *
 * Version 1.0
 *
 * September 27, 2017
 *
 * Copyright © 2017 Sajjad - All Rights Reserved.
 */


package com.example.shaider1_countbook;

import java.util.Date;

/**
 * Represents a Counter
 *
 * @author Sajjad
 * @version 1.0
 */
public class Counter {

    // Name of Counter
    private String mCounterName;

    // Description of counter
    private String mCounterDescription;

    // Initial Count
    private int mInitial;

    // Current Count
    private int mCount;

    // Date
    private String mDate;


    public Counter(String vName, String vDescription, int vInitial) {
        mCounterName = vName;
        mCounterDescription = vDescription;
        mInitial = vInitial;
        mCount = vInitial;
        mDate = new Date().toString();
    }

    /*
     * Get Counter Name
     * @return mCounterName
     */
    public String getCounterName() {
        return this.mCounterName;
    }


    /**
     * Get counter description
     * @return mCounterDescription
     */
    public String getCounterDescription() {
        return this.mCounterDescription;
    }


    /**
     * Get Counter initial count
     * @return mCount
     */
    public int getCounterCount() {
        return this.mCount;
    }

    /**
     * Increment current count by 1
     * @return mCount
     */
    public int incCount() {
        this.mCount = mCount + 1;
        return mCount;
    }

    /**
     * Decrement current count by 1
     * @return mCount
     */
    public int decCount() {
        this.mCount = mCount - 1;

        // error check for negative value
        if (this.mCount < 0) {
            this.mCount = 0;
        }
        return mCount;
    }

    /**
     * Reset current count to intial value
     * @return mCount
     */
    public int resetCount() {
        return this.mCount = mInitial;
    }

    /**
     * Set the name of the counter
     * @param name
     */
    public void setCounterName(String name) {
        this.mCounterName = name;
    }

    /**
     * Set the description of the counter
     * @param desc
     */
    public void setCounterDesc(String desc) {
        this.mCounterDescription = desc;
    }

    /**
     * Set the Current count
     * @param count
     */
    public void setCounterCount(int count) {
        this.mCount = count;
    }

    /**
     * Set the Counter Date
     * @return mDate
     */
    public void setCounterDate() {
        this.mDate = new Date().toString();
    }

    /**
     * Get counter date
     * @return mDate
     */
    public String getCounterDate() {
        return mDate;
    }


}
