package com.example.victor.fester.Navigation;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;

import com.example.victor.fester.R;
import com.example.victor.fester.Toolbox.BitmapManager;
import com.example.victor.fester.User.User;
import com.example.victor.fester.User.UserDBAdapter;

public class Fragment_Info_Update extends Fragment {

    public Fragment_Info_Update() {}

    @Override
    public void onCreate(Bundle savedInstanceState) {super.onCreate(savedInstanceState);}

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.navigation_fragment_info_update, container, false);

        final EditText name = (EditText)view.findViewById(R.id.info_name_update);

        // -- Tratando botao DONE
        final FloatingActionButton fab1 = (FloatingActionButton) view.findViewById(R.id.fab_info_update);
        fab1.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                // Recebendo usuario do BD
                UserDBAdapter dbAdapter = new UserDBAdapter(getActivity().getBaseContext());
                dbAdapter.open();
                User user = dbAdapter.getUser();
                dbAdapter.close();

                if(name.getText().length() > 0){
                    user.setName(name.getText().toString());
                }

                user.toDataBase(view.getContext());

                InputMethodManager inputManager = (InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
                inputManager.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

                getFragmentManager().popBackStackImmediate();

            }
        });

        // -- Tratando botao FOTO
        final FloatingActionButton fab2 = (FloatingActionButton) view.findViewById(R.id.fab_photo_update);
        fab2.setOnClickListener(new View.OnClickListener() {
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
        getActivity().setTitle(getResources().getString(R.string.info_update));
    }
}
