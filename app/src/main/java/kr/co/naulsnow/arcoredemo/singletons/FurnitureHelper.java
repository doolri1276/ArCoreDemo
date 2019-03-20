package kr.co.naulsnow.arcoredemo.singletons;

import java.util.ArrayList;
import java.util.List;

import kr.co.naulsnow.arcoredemo.models.FurnitureItem;

public class FurnitureHelper {

    private static FurnitureHelper instance = new FurnitureHelper();
    public static FurnitureHelper getInstance() {
        return instance;
    }



    private List<FurnitureItem> furnitureList;
    private List<FurnitureItem> selectedFurnitureList;




    public FurnitureHelper() {
        furnitureList = new ArrayList<>();
        selectedFurnitureList = new ArrayList<>();
    }


    public List<FurnitureItem> getFurnitureList() {
        return furnitureList;
    }

    public void setFurnitureList(List<FurnitureItem> furnitureList) {
        this.furnitureList = furnitureList;
    }

    public List<FurnitureItem> getSelectedFurnitureList() {
        return selectedFurnitureList;
    }
}
