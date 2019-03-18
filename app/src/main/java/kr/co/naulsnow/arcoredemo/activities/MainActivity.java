package kr.co.naulsnow.arcoredemo.activities;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.google.ar.core.Anchor;
import com.google.ar.core.Config;
import com.google.ar.core.HitResult;
import com.google.ar.core.Plane;
import com.google.ar.core.Session;
import com.google.ar.core.exceptions.UnavailableException;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.BaseArFragment;

import kr.co.naulsnow.arcoredemo.R;
import kr.co.naulsnow.arcoredemo.adapters.FurnitureListAdapter;
import kr.co.naulsnow.arcoredemo.models.FurnitureItem;
import kr.co.naulsnow.arcoredemo.singletons.FurnitureHelper;
import kr.co.naulsnow.arcoredemo.tools.BaseTool;
import kr.co.naulsnow.arcoredemo.tools.CameraPermissionHelper;
import kr.co.naulsnow.arcoredemo.tools.Data;

public class MainActivity extends AppCompatActivity {

    private final String TAG="main_ac";

    //// AR CORE //////////////
    ArFragment arFragment;


    //데이터들
    private boolean installRequested;
//    List<FurnitureItem> furnitureModelList = new ArrayList<>();
    int selected;

    //// 기본 ///////////////
    ImageView ivCart, ivPreview;

    //// 메뉴 ////
    LinearLayout llMenu;
    ImageView ivBack;
    RecyclerView rvChairs;
    FurnitureListAdapter furnitureListAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //초기셋팅
        setUpModels();
        setArFragment();
        setViews();
        setAdapters();
        setListeners();
    }

    private void setUpModels(){

        //여기서 리스트 만들어야함
        for(int i=0;i<5;i++){
//            if(i==0)
                FurnitureHelper.getInstance().getFurnitureList().add(new FurnitureItem(MainActivity.this, R.drawable.fur01+i, "http://snownaul2.dothome.co.kr/ar/fur0"+(i+1)+".sfb", 1000*(i+1), "의자"+i+"번"));
//            else
//                FurnitureHelper.getInstance().getFurnitureList().add(new FurnitureItem(MainActivity.this, R.drawable.fur01+i, R.raw.fur01+i, 1000*(i+1), "의자"+i+"번"));
        }
    }


    private void setArFragment(){
        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.sceneform_ux_fragment);

        arFragment.setOnTapArPlaneListener(new BaseArFragment.OnTapArPlaneListener() {
            @Override
            public void onTapPlane(HitResult hitResult, Plane plane, MotionEvent motionEvent) {
                //When user tap on plane, we will add model
                Anchor anchor = hitResult.createAnchor();
                AnchorNode anchorNode = new AnchorNode(anchor);
                anchorNode.setParent(arFragment.getArSceneView().getScene());

                FurnitureHelper.getInstance().getFurnitureList().get(selected).createModel(anchorNode, arFragment.getTransformationSystem());

//                createModel(anchorNode, selected);
            }
        });




//        arFragment.getArSceneView().getSession().setCameraConfig(new CameraConfig(arFragment.getArSceneView().getSession(), ));


    }

    private void setViews(){
        ivCart = findViewById(R.id.iv_cart);
        ivPreview = findViewById(R.id.iv_preview);

        llMenu = findViewById(R.id.ll_menu);
        ivBack = findViewById(R.id.iv_back);
        rvChairs = findViewById(R.id.rv_chairs);
    }

    private void setListeners() {
        ivCart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                MovingTool.goToFurnitureSelectActivityForResult(MainActivity.this, Data.RESULT_SELECT_FURNITURE);
                llMenu.setVisibility(View.VISIBLE);
            }
        });

        ivBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                llMenu.setVisibility(View.GONE);
            }
        });
    }

    private void setAdapters(){

        if(furnitureListAdapter == null){
            furnitureListAdapter = new FurnitureListAdapter(MainActivity.this, new FurnitureListAdapter.OnClickFurnitureInterface() {
                @Override
                public void onClick(int index) {
                    selectChair(index);
                }
            });
            rvChairs.setAdapter(furnitureListAdapter);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        Session session = arFragment.getArSceneView().getSession();

        Log.d(TAG, "session 널이다 : "+(session == null));

        if(session == null){

            try{
                session = BaseTool.createArSession(this, installRequested);

                if(session == null ){
                    installRequested = CameraPermissionHelper.hasCameraPermission(this);
                    return;
                }else{
                    arFragment.getArSceneView().setupSession(session);
                }

            } catch (UnavailableException e) {
                BaseTool.handleSessionException(this, e);
            }

        }

        if(session!=null){
            BaseTool.log(TAG, "session 널아님!! 셋팅함!");
            Config config = session.getConfig();
            config.setFocusMode(Config.FocusMode.AUTO);
            config.setLightEstimationMode(Config.LightEstimationMode.AMBIENT_INTENSITY);
            config.setCloudAnchorMode(Config.CloudAnchorMode.ENABLED);
            arFragment.getArSceneView().getSession().configure(config);

        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if(resultCode == Activity.RESULT_OK && data!=null){
            if(requestCode == Data.RESULT_SELECT_FURNITURE){
                selectedChair(data);
            }
        }



        super.onActivityResult(requestCode, resultCode, data);
    }

    private void selectedChair(Intent data){
        if(data.hasExtra(Data.BUNDLE_INDEX)){
            int index = data.getIntExtra(Data.BUNDLE_INDEX, 0);

            selectChair(index);

            if(selected == index)
                return;

            selected=index;

            FurnitureItem item = FurnitureHelper.getInstance().getFurnitureList().get(selected);

            Glide.with(this).load(item.getImageUrl()==null?item.getImageRid():item.getImageUrl()).into(ivPreview);

        }
    }

    private void selectChair(int index){
        if(selected == index)
            return;

        selected=index;
        llMenu.setVisibility(View.GONE);

        FurnitureItem item = FurnitureHelper.getInstance().getFurnitureList().get(selected);

        Glide.with(this).load(item.getImageUrl()==null?item.getImageRid():item.getImageUrl()).into(ivPreview);
    }

}
