package kr.co.naulsnow.arcoredemo.network;

import kr.co.naulsnow.arcoredemo.tools.Data;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NetworkManager {

    private static NetworkManager instance = new NetworkManager();

    public static NetworkManager getInstance() {
        return instance;
    }

    Retrofit retrofit;

    public NetworkManager() {
        retrofit = new Retrofit.Builder()
                .baseUrl(Data.URL_SERVER)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public Retrofit getRetrofit() {
        return retrofit;
    }
}
