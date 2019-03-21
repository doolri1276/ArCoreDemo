package kr.co.naulsnow.arcoredemo.models;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.Node;
import com.google.ar.sceneform.math.Vector3;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.rendering.ViewRenderable;
import com.google.ar.sceneform.ux.TransformableNode;
import com.google.ar.sceneform.ux.TransformationSystem;

import kr.co.naulsnow.arcoredemo.R;
import kr.co.naulsnow.arcoredemo.result.FurnitureResult;
import kr.co.naulsnow.arcoredemo.singletons.FurnitureHelper;

public class FurnitureItem {

    private int furnitureCode;
    private int imageRid;
    private String imageUrl;
    private ViewRenderable viewRenderable;
    private ModelRenderable modelRenderable;
    private int price;
    private String name;
    private Context context;
    private boolean isSelected = false;

    OnDetachNode onDetachNode;

    public FurnitureItem(Context context, FurnitureResult.FurnitureItem furnitureItem, OnDetachNode onDetachNode){
        imageUrl=furnitureItem.getImageUrl();

        ModelRenderable.builder()
                .setSource(context, Uri.parse(furnitureItem.getModelUrl()))
                .build().thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable-> {
                            Toast.makeText(context, "Unable to load bear model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ViewRenderable.builder()
                .setView(context, R.layout.item_furniture_info)
                .build()
                .thenAccept( renderable -> viewRenderable = renderable);

        this.furnitureCode = furnitureItem.getFurnitureID();
        this.context = context;
        price = furnitureItem.getPrice();
        name=furnitureItem.getName();
        this.onDetachNode = onDetachNode;
    }

    public FurnitureItem(Context context, int imageRid, int modelRenderableRid, int price, String name) {

        ModelRenderable.builder()
                .setSource(context, modelRenderableRid)
                .build().thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable-> {
                            Toast.makeText(context, "Unable to load bear model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ViewRenderable.builder()
                .setView(context, R.layout.item_furniture_info)
                .build()
                .thenAccept( renderable -> viewRenderable = renderable);

        this.imageRid = imageRid;
        this.price = price;
        this.name = name;
    }

    public FurnitureItem(Context context, int imageRid, String modelRenderableURL, int price, String name) {

        ModelRenderable.builder()
                .setSource(context, Uri.parse(modelRenderableURL))
                .build().thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable-> {
                            Toast.makeText(context, "Unable to load bear model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ViewRenderable.builder()
                .setView(context, R.layout.item_furniture_info)
                .build()
                .thenAccept( renderable -> viewRenderable = renderable);

        this.imageRid = imageRid;
        this.price = price;
        this.name = name;
    }

    public FurnitureItem(Context context, String imageUrl, String modelRenderableURL, int price, String name) {

        ModelRenderable.builder()
                .setSource(context, Uri.parse(modelRenderableURL))
                .build().thenAccept(renderable -> modelRenderable = renderable)
                .exceptionally(
                        throwable-> {
                            Toast.makeText(context, "Unable to load bear model", Toast.LENGTH_SHORT).show();
                            return null;
                        }
                );

        ViewRenderable.builder()
                .setView(context, R.layout.item_furniture_info)
                .build()
                .thenAccept( renderable -> viewRenderable = renderable);

        this.imageUrl = imageUrl;
        this.price = price;
        this.name = name;
    }



    public void createModel(AnchorNode anchorNode, TransformationSystem transformationSystem){

        FurnitureHelper.getInstance().getCartFurnitureList().add(this);

        TransformableNode transformableNode = new TransformableNode(transformationSystem);
//        TransformationSystem transformationSystem1 = new TransformationSystem(context.getResources().getDisplayMetrics(), new FootprintSelectionVisualizer());
//        TransformableNode transformableNode = new TransformableNode(transformationSystem1);
        transformableNode.setRenderable(modelRenderable);
        transformableNode.getScaleController().setMinScale(0.2f);
        transformableNode.getScaleController().setMaxScale(1f);
        transformableNode.setLocalScale(new Vector3(0.5f, 0.5f, 0.5f));
        transformableNode.setParent(anchorNode);
        transformableNode.select();

        Node infoNode = new Node();
        infoNode.setLocalScale(new Vector3(0.7f, 0.7f, 0.7f));
        infoNode.setLocalPosition(new Vector3(0, transformableNode.getLocalPosition().y+0.5f, 0));
        infoNode.setParent(anchorNode);
        infoNode.setRenderable(viewRenderable);

        TextView tvCancel, tvName, tvPrice;
        tvCancel = viewRenderable.getView().findViewById(R.id.tv_cancel);
        tvName = viewRenderable.getView().findViewById(R.id.tv_name);
        tvPrice = viewRenderable.getView().findViewById(R.id.tv_price);

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onDetachNode.onDetach(FurnitureHelper.getInstance().getCartFurnitureList().indexOf(FurnitureItem.this), anchorNode);
//                anchorNode.setParent(null);
            }
        });

        tvName.setText(name);
        tvPrice.setText(price+"원");

    }

    public int getImageRid() {
        return imageRid;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public boolean isSelected() {
        return isSelected;
    }

    @Override
    public boolean equals(Object obj) {

        if(obj instanceof FurnitureItem==false)
            return false;

        return this.furnitureCode==((FurnitureItem)obj).furnitureCode;
    }

    public interface OnDetachNode{
        void onDetach(int index, AnchorNode anchorNode);
    }
}
