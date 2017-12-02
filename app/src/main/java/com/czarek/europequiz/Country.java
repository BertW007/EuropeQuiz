package com.czarek.europequiz;

import android.graphics.Color;

public class Country {
    private String mCountryName;
    private String mCapital;
    private String mPopulation;
    private String mArea;
    private int mFlag;
    private int mHighlightMask;
    private int mMaskColor;

    public Country(String countryName, String capital, String population, String area, int highlightMask, int maskColor) {
        mCountryName = countryName;
        mCapital = capital;
        mPopulation = population;
        mArea = area;
        mHighlightMask = highlightMask;
        mMaskColor = maskColor;
    }

    public Country(String countryName, String capital, int highlightMask, int maskColor){
        mCountryName = countryName;
        mCapital = capital;
        mHighlightMask = highlightMask;
        mMaskColor = maskColor;
    }

    public Country(String countryName, int flag){
        mCountryName = countryName;
        mFlag = flag;
    }

    public String getCountryName() {return mCountryName;}
    public String getCapital() {return mCapital;}
    public String getPopulation() {return mPopulation;}
    public String getArea() {return mArea;}
    public int getFlag() {return mFlag;}
    public int getHighlightMask() {return mHighlightMask;}
    public int getMaskColor() {return mMaskColor;}
}