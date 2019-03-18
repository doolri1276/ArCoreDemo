package kr.co.naulsnow.arcoredemo.result;

import com.google.gson.annotations.SerializedName;

public class FurnitureResult {
    @SerializedName("arFurnitureID")    private int furnitureID;
    @SerializedName("name")             private String name;
    @SerializedName("price")            private int price;
    @SerializedName("imageUrl")         private String imageUrl;
    @SerializedName("modelUrl")         private String modelUrl;
}
