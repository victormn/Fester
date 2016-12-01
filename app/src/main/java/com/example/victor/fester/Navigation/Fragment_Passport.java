package com.example.victor.fester.Navigation;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.victor.fester.R;
import com.example.victor.fester.Toolbox.BitmapManager;
import com.example.victor.fester.User.User;
import com.example.victor.fester.User.UserDBAdapter;

public class Fragment_Passport extends Fragment {

    public Fragment_Passport() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment_passport, container, false);

        // -- Tratando informacoes

        // Recebendo usuario do BD
        UserDBAdapter dbAdapter = new UserDBAdapter(getActivity().getBaseContext());
        dbAdapter.open();
        User user = dbAdapter.getUser();
        dbAdapter.close();

        // Tratando a foto
        ImageView info_img = (ImageView)view.findViewById(R.id.img_passport);
        Bitmap src = BitmapManager.byteArrayToBitmap(user.getQr());
        info_img.setImageBitmap(src);

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.passport));
    }
}
