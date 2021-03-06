package com.example.victor.fester.Navigation;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
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
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

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


        // -- Tratando botao
        final FloatingActionButton fab = (FloatingActionButton) view.findViewById(R.id.fab_info);
        fab.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(intent, 1);

            }
        });

        return view;
    }

    @Override
    public void onResume() {
        super.onResume();
        getActivity().setTitle(getResources().getString(R.string.info));
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode){
            case 1:
                if(resultCode == Activity.RESULT_OK){
                    Uri uri = data.getData();
                    String[] projection = {MediaStore.Images.Media.DATA};

                    Cursor cursor = getActivity().getContentResolver().query(uri, projection, null, null, null);
                    cursor.moveToFirst();

                    String filePath = cursor.getString(cursor.getColumnIndex(projection[0]));
                    cursor.close();

                    Bitmap bitmap = BitmapFactory.decodeFile(filePath);
                    byte[] photo = BitmapManager.bitmapToByteArray(bitmap);

                    // Recebendo usuario do BD
                    UserDBAdapter dbAdapter = new UserDBAdapter(getActivity().getBaseContext());
                    dbAdapter.open();
                    User user = dbAdapter.getUser();
                    dbAdapter.close();

                    user.setPhoto(photo);
                    user.toDataBase(getActivity().getBaseContext());
                }
        }
    }
}
