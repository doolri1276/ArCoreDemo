package kr.co.naulsnow.arcoredemo.singletons;

import java.util.ArrayList;
import java.util.List;

import kr.co.naulsnow.arcoredemo.models.FurnitureItem;

public class FurnitureHelper {

    private static FurnitureHelper instance;
    public static FurnitureHelper getInstance() {
        if(instance == null)
            instance = new FurnitureHelper();
        return instance;
    }



    private List<FurnitureItem> furnitureList;




    public FurnitureHelper() {
        furnitureList = new ArrayList<>();
    }


    public List<FurnitureItem> getFurnitureList() {
        return furnitureList;
    }

    public void setFurnitureList(List<FurnitureItem> furnitureList) {
        this.furnitureList = furnitureList;
    }
}
