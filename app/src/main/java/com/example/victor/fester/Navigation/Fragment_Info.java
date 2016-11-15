package com.example.victor.fester.Navigation;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.victor.fester.R;

public class Fragment_Info extends Fragment {

    public Fragment_Info() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment_info, container, false);

        // Tratando a foto das informações
        ImageView info_img = (ImageView)view.findViewById(R.id.info_foto);

        Bitmap srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.foto);
        Bitmap dstBmp;

        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth()/2 - srcBmp.getHeight()/2, 0, srcBmp.getHeight(), srcBmp.getHeight());
        }else{
            dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight()/2 - srcBmp.getWidth()/2, srcBmp.getWidth(), srcBmp.getWidth());
        }

        Bitmap src = Bitmap.createScaledBitmap(dstBmp, 400, 400, true);

        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(getResources(), src);
        dr.setCircular(true);
        info_img.setImageDrawable(dr);

        return view;
    }

}
