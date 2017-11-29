package com.czarek.europequiz;

import android.graphics.Color;

public class Country {
    private String mCountryName;
    private String mCapital;
    private String mPopulation;
    private String mArea;
    private int mFlag;
    private int mHighlightMask;
    private Color mMaskColor;

    public Country(String countryName, String capital, String population, String area, int highlightMask, Color maskColor) {
        mCountryName = countryName;
        mCapital = capital;
        mPopulation = population;
        mArea = area;
        mHighlightMask = highlightMask;
        mMaskColor = maskColor;
    }

    public Country(String countryName, String capital, int flag, String population, String area, Color maskColor) {
        mCountryName = countryName;
        mCapital = capital;
        mPopulation = population;
        mArea = area;
        mMaskColor = maskColor;
        mFlag = flag;
    }

    public String getCountryName() {return mCountryName;}
    public String getCapital() {return mCapital;}
    public String getPopulation() {return mPopulation;}
    public String getArea() {return mArea;}
    public int getFlag() {return mFlag;}
    public int getHighlightMask() {return mHighlightMask;}
    public Color getMaskColor() {return mMaskColor;}
}