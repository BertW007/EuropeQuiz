package com.czarek.europequiz;

import android.graphics.Color;
import android.view.View;
import android.widget.ImageView;

import java.util.ArrayList;

public class Country {
    private String mCountryName;
    private String mCapital;
    private String mPopulation;
    private String mArea;
    private int mFlag;
    private int mHighlightMask;
    private String mCountryID;

    public Country(String countryName, String capital, String population, String area, int highlightMask, String countryID) {
        mCountryName = countryName;
        mCapital = capital;
        mPopulation = population;
        mArea = area;
        mHighlightMask = highlightMask;
        mCountryID = countryID;
    }

    public Country(String countryName, String capital, int highlightMask, String countryID){
        mCountryName = countryName;
        mCapital = capital;
        mHighlightMask = highlightMask;
        mCountryID = countryID;
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
    public String getCountryID() {return mCountryID;}
}