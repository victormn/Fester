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
import android.widget.TextView;

import com.example.victor.fester.R;
import com.example.victor.fester.Toolbox.BitmapManager;
import com.example.victor.fester.User.User;
import com.example.victor.fester.User.UserDBAdapter;

public class Fragment_Info extends Fragment {

    public Fragment_Info() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment_info, container, false);

        // -- Tratando informacoes

        // Recebendo usuario do BD
        UserDBAdapter dbAdapter = new UserDBAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        User user = dbAdapter.getUser();
        dbAdapter.close();

        // Tratando a foto
        ImageView info_img = (ImageView)view.findViewById(R.id.info_foto);
        Bitmap srcBmp = BitmapManager.byteArrayToBitmap(user.getPhoto());
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

        // Tratando o nome
        TextView nav_name = (TextView) view.findViewById(R.id.info_name);
        nav_name.setText(user.getName());

        // Tratando o email
        TextView nav_email = (TextView) view.findViewById(R.id.info_email);
        nav_email.setText(user.getEmail());

        // Tratando o telefone
        TextView nav_phone = (TextView) view.findViewById(R.id.info_phone);
        nav_phone.setText(user.getPhone());

        return view;
    }

}
