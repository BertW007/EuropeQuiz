package com.czarek.europequiz;

/**
 * {@link Country} represents a simplified country model that user can learn about.
 * Contains the name of the country, capital, population, area of the country and flag.
 * In addition, the {@link Country} object has masks representing the area of the country that are used in map-related activities.
 * The ID of each country is included in the class to simplify the checking of game conditions.
 *
 * @author Czarek Pietrzak
 */
class Country {
    /**
     * The name of the country.
     */
    private String mCountryName;

    /**
     * The capital of the country.
     */
    private String mCapital;

    /**
     * The population of the country.
     */
    private String mPopulation;

    /**
     * The area of the country in km2.
     */
    private String mArea;

    /**
     * ID of the graphic source representing the flag of country.
     */
    private int mFlag;

    /**
     * ID of the graphic source representing the country's surface in the form of a colored mask.
     */
    private int mHighlightMask;

    /**
     * ID of the graphic source representing the country's surface in the form of a gray mask.
     */
    private int mGrayMask;

    /**
     * ID of the country.
     */
    private String mCountryID;

    /**
     * Creates a Country object. Used in {@link InteractiveMapActivity}
     *
     * @param countryName   name of the country
     * @param capital       capital of the country
     * @param population    population of the country
     * @param area          area of the country in km2
     * @param highlightMask ID of the graphic source - colored mask
     * @param flag          ID of the graphic source - flag of country
     * @param countryID     ID of the country
     */
    Country(String countryName, String capital, String population, String area, int highlightMask, int flag, String countryID) {
        mCountryName = countryName;
        mCapital = capital;
        mPopulation = population;
        mArea = area;
        mHighlightMask = highlightMask;
        mFlag = flag;
        mCountryID = countryID;
    }

    /**
     * Creates a Country object. Used in {@link MapGameActivity}
     *
     * @param countryName   name of the country
     * @param capital       capital of the country
     * @param population    population of the country
     * @param area          area of the country in km2
     * @param highlightMask ID of the graphic source - colored mask
     * @param grayMask      ID of the graphic source - gray mask
     * @param flag          ID of the graphic source - flag of country
     * @param countryID     ID of the country
     */
    Country(String countryName, String capital, String population, String area, int highlightMask, int grayMask, int flag, String countryID) {
        mCountryName = countryName;
        mCapital = capital;
        mPopulation = population;
        mArea = area;
        mHighlightMask = highlightMask;
        mGrayMask = grayMask;
        mFlag = flag;
        mCountryID = countryID;
    }

    /**
     * Creates a Country object. Used in {@link FlagGameActivity}
     *
     * @param countryName   name of the country
     * @param flag          ID of the graphic source - flag of country
     */
    Country(String countryName, int flag) {
        mCountryName = countryName;
        mFlag = flag;
    }

    /**
     * Gets the name of the country.
     *
     * @return name of the country
     */
    String getCountryName() {
        return mCountryName;
    }//getCountryName()

    /**
     * Gets the capital of the country.
     *
     * @return capital of the country
     */
    String getCapital() {
        return mCapital;
    }//getCapital()

    /**
     * Gets the population of the country.
     *
     * @return population of the country
     */
    String getPopulation() {
        return mPopulation;
    }//getPopulation()

    /**
     * Gets the area of the country in km2.
     *
     * @return area of the country in km2
     */
    String getArea() {
        return mArea;
    }//getArea()

    /**
     * Gets the ID of the graphic source - flag of country.
     *
     * @return ID of the graphic source - flag of country
     */
    int getFlag() {
        return mFlag;
    }//getFlag()

    /**
     * Gets the ID of the graphic source - colored mask.
     *
     * @return ID of the graphic source - colored mask
     */
    int getHighlightMask() {
        return mHighlightMask;
    }//getHighlightMask()

    /**
     * Gets the ID of the graphic source - gray mask.
     *
     * @return ID of the graphic source - gray mask
     */
    int getGrayMask() {
        return mGrayMask;
    }//getGrayMask()

    /**
     * Gets the ID of the country.
     *
     * @return ID of the country
     */
    String getCountryID() {
        return mCountryID;
    }//getCountryID()
}//end of the Country class