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
    private List<FurnitureItem> cartFurnitureList;




    public FurnitureHelper() {
        furnitureList = new ArrayList<>();
        cartFurnitureList = new ArrayList<>();
    }


    public List<FurnitureItem> getFurnitureList() {
        return furnitureList;
    }

    public void setFurnitureList(List<FurnitureItem> furnitureList) {
        this.furnitureList = furnitureList;
    }

    public List<FurnitureItem> getCartFurnitureList() {
        return cartFurnitureList;
    }

    public int getCartSum(){
        int sum=0;

        for(int i = 0; i< cartFurnitureList.size(); i++){
            sum+= cartFurnitureList.get(i).getPrice();
        }
        return sum;
    }

    public int getSelectedSum(){
        int sum=0;

        for(int i=0;i<cartFurnitureList.size();i++){
            if(cartFurnitureList.get(i).isSelected()){
                sum+=cartFurnitureList.get(i).getPrice();
            }
        }
        return sum;
    }
}
