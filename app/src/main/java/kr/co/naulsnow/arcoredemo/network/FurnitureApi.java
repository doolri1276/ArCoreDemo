package kr.co.naulsnow.arcoredemo.network;

import kr.co.naulsnow.arcoredemo.result.FurnitureResult;
import retrofit2.Call;
import retrofit2.http.GET;

public interface FurnitureApi {

    @GET("loadAllFurniture.php")
    Call<FurnitureResult> getAllFurnitures();
}
