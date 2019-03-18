package kr.co.naulsnow.arcoredemo.result;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class FurnitureResult {

    @SerializedName("data")             private List<FurnitureItem> furnitureItemList;

    public class FurnitureItem{
        @SerializedName("arFurnitureID")    private int furnitureID;
        @SerializedName("name")             private String name;
        @SerializedName("price")            private int price;
        @SerializedName("imageUrl")         private String imageUrl;
        @SerializedName("modelUrl")         private String modelUrl;

        public int getFurnitureID() {
            return furnitureID;
        }

        public String getName() {
            return name;
        }

        public int getPrice() {
            return price;
        }

        public String getImageUrl() {
            return imageUrl;
        }

        public String getModelUrl() {
            return modelUrl;
        }
    }

    public List<FurnitureItem> getFurnitureItemList() {
        return furnitureItemList;
    }
}
