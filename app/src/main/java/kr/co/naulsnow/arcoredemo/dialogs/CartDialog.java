package kr.co.naulsnow.arcoredemo.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import kr.co.naulsnow.arcoredemo.R;
import kr.co.naulsnow.arcoredemo.singletons.FurnitureHelper;

public class CartDialog extends DialogFragment {

    OnResult onResult;

    //툴바
    ImageView ivCancel;
    TextView tvTopSum;

    //장바구니 리스트
    RecyclerView rvFurnitures;

    //구매
    TextView tvPurchaseSelected, tvPurchaseAll;

    //결과
    TextView tvFurnitureSum, tvDiscountSum, tvCouponSum, tvShippingSum, tvSum;



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.dialog_cart, container, false);
        setViews(view);
        setListeners();
        setAppBar();
        setData();
        return view;
    }

    private void setViews(View view) {
        //툴바
        ivCancel = view.findViewById(R.id.iv_cancel);
        tvTopSum = view.findViewById(R.id.tv_top_sum);

        //장바구니 리스트
        rvFurnitures = view.findViewById(R.id.rv_furnitures);

        //구매
        tvPurchaseSelected = view.findViewById(R.id.tv_purchase_selected);
        tvPurchaseAll = view.findViewById(R.id.tv_purchase_all);

        //결과
        tvFurnitureSum = view.findViewById(R.id.tv_furniture_sum);
        tvDiscountSum = view.findViewById(R.id.tv_discount_sum);
        tvCouponSum = view.findViewById(R.id.tv_coupon_sum);
        tvShippingSum = view.findViewById(R.id.tv_shipping_sum);
        tvSum = view.findViewById(R.id.tv_sum);

    }

    private void setListeners() {
        ivCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onResult.finish();
                getDialog().dismiss();
            }
        });

    }

    private void setAppBar() {

    }

    private void setData() {
        //툴바
        tvTopSum.setText(FurnitureHelper.getInstance().getSelectedSum()+"원");

        //결과
        tvFurnitureSum.setText(FurnitureHelper.getInstance().getSelectedSum()+"원");
        tvSum.setText(FurnitureHelper.getInstance().getSelectedSum());

    }

    public interface OnResult{
        void finish();
    }

    public void setOnResult(OnResult onResult) {
        this.onResult = onResult;
    }
}
