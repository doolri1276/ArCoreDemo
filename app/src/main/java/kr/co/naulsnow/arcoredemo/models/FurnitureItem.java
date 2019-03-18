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

public class FurnitureItem {

    private int imageRid;
    private String imageUrl;
    private ViewRenderable viewRenderable;
    private ModelRenderable modelRenderable;
    private int price;
    private String name;

    public FurnitureItem(Context context, FurnitureResult.FurnitureItem furnitureItem){
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

        price = furnitureItem.getPrice();
        name=furnitureItem.getName();
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

        TransformableNode transformableNode = new TransformableNode(transformationSystem);

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
                anchorNode.setParent(null);
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
}
