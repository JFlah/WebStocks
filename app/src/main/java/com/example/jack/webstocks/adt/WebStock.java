package com.example.jack.webstocks.adt;

import java.io.Serializable;

public class WebStock implements Serializable {
    String symbol;
    String percentChange;
    double askPrice = 0.0d;
    double bidPrice = 0.0d;
    double dayLow;
    double dayHigh;
    int avgDailyVolume;
    double open;
    double prevClose;
    double bookVal;
    double divShare;
    double epsEstimateCurrentYr;
    double epsEstimateNextYr;
    double yearLow;
    double yearHigh;

    long id;

    public WebStock(String symbol, String percentChange, double askPrice, double bidPrice, double dayLow, double dayHigh, int avgDailyVolume,
                    double open, double prevClose, double bookVal, double divShare, double epsEstimateCurrentYr, double epsEstimateNextYr,
                    double yearLow, double yearHigh) {
        this.symbol = symbol;
        this.percentChange = percentChange;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.dayLow = dayLow;
        this.dayHigh = dayHigh;
        this.avgDailyVolume = avgDailyVolume;
        this.open = open;
        this.prevClose = prevClose;
        this.bookVal = bookVal;
        this.divShare = divShare;
        this.epsEstimateCurrentYr = epsEstimateCurrentYr;
        this.epsEstimateNextYr = epsEstimateNextYr;
        this.yearLow = yearLow;
        this.yearHigh = yearHigh;
    }

    public WebStock(long id, String symbol, String percentChange, double askPrice, double bidPrice, double dayLow, double dayHigh, int avgDailyVolume,
                    double open, double prevClose, double bookVal, double divShare, double epsEstimateCurrentYr, double epsEstimateNextYr,
                    double yearLow, double yearHigh) {
        this.id = id;
        this.symbol = symbol;
        this.percentChange = percentChange;
        this.askPrice = askPrice;
        this.bidPrice = bidPrice;
        this.dayLow = dayLow;
        this.dayHigh = dayHigh;
        this.avgDailyVolume = avgDailyVolume;
        this.open = open;
        this.prevClose = prevClose;
        this.bookVal = bookVal;
        this.divShare = divShare;
        this.epsEstimateCurrentYr = epsEstimateCurrentYr;
        this.epsEstimateNextYr = epsEstimateNextYr;
        this.yearLow = yearLow;
        this.yearHigh = yearHigh;
    }

    public String getSymbol() {
        return symbol;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getId() {
        return id;
    }

    public String getPercentChange() {
        return percentChange;
    }

    public double getDayHigh() {
        return dayHigh;
    }

    public double getDayLow() {
        return dayLow;
    }

    public double getBidPrice() {
        return bidPrice;
    }

    public double getAskPrice() {
        return askPrice;
    }

    public int getAvgDailyVolume() {
        return avgDailyVolume;
    }

    public double getOpen() {
        return open;
    }

    public double getPrevClose() {
        return prevClose;
    }

    public double getBookVal() {
        return bookVal;
    }

    public double getDivShare() {
        return divShare;
    }

    public double getEpsEstimateCurrentYr() {
        return epsEstimateCurrentYr;
    }

    public double getEpsEstimateNextYr() {
        return epsEstimateNextYr;
    }

    public double getYearLow() {
        return yearLow;
    }

    public double getYearHigh() {
        return yearHigh;
    }

    public String toString() {
        return "Symbol: " + this.symbol + " " + "Change: " + this.percentChange;
    }
}