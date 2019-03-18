package kr.co.naulsnow.arcoredemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;

import kr.co.naulsnow.arcoredemo.R;
import kr.co.naulsnow.arcoredemo.adapters.FurnitureListAdapter;
import kr.co.naulsnow.arcoredemo.tools.Data;

public class FurnitureSelectActivity extends AppCompatActivity {

    ImageView ivBack;

    RecyclerView rvChairs;
    FurnitureListAdapter furnitureListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_furniture_select);

        setViews();
        setListeners();
        setAdapters();

    }

    private void setViews(){

        ivBack = findViewById(R.id.iv_back);
        rvChairs = findViewById(R.id.rv_chairs);

    }

    private void setListeners(){
        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void setAdapters(){

        if(furnitureListAdapter == null){
            furnitureListAdapter = new FurnitureListAdapter(FurnitureSelectActivity.this, new FurnitureListAdapter.OnClickFurnitureInterface() {
                @Override
                public void onClick(int index) {
                    returnSelectedFurniture(index);
                }
            });
            rvChairs.setAdapter(furnitureListAdapter);
        }
    }

    private void returnSelectedFurniture(int index){
        Intent data = new Intent();
        data.putExtra(Data.BUNDLE_INDEX, index);
        setResult(Activity.RESULT_OK, data);
        finish();
    }
}
