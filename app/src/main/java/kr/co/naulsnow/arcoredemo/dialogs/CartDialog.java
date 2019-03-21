package kr.co.naulsnow.arcoredemo.dialogs;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import kr.co.naulsnow.arcoredemo.R;

public class CartDialog extends DialogFragment {

    OnResult onResult;

    //X버튼
    ImageView ivCancel;




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
        ivCancel = view.findViewById(R.id.iv_cancel);

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

    }

    public interface OnResult{
        void finish();
    }

    public void setOnResult(OnResult onResult) {
        this.onResult = onResult;
    }
}
