package com.example.doodleblue;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class PriceJsonResponse implements Serializable {


    @SerializedName("data")
    private ArrayList<datalist> datalist;

    public ArrayList<datalist> getDatalist() {
        return datalist;
    }

    public void setDatalist(ArrayList<datalist> datalist) {
        this.datalist = datalist;
    }

    public   class datalist {


        @SerializedName("name")
        public String name;
        @SerializedName("priceUsd")
        public String price;
        @SerializedName("changePercent24Hr")
        public String changepercent;
        @SerializedName("symbol")
        public String symbol;

        public String getPrice() {
            return price;
        }

        public void setPrice(String price) {
            this.price = price;
        }

        public String getSymbol() {
            return symbol;
        }

        public void setSymbol(String symbol) {
            this.symbol = symbol;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getChangepercent() {
            return changepercent;
        }

        public void setChangepercent(String changepercent) {
            this.changepercent = changepercent;
        }


    }




    }

