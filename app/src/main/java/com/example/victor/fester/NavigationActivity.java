package com.example.victor.fester;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.victor.fester.Admin.AdminScreen;
import com.example.victor.fester.Admin.QRCode.Reader;
import com.example.victor.fester.DJ.TabbedActivity;
import com.example.victor.fester.Login.Login;
import com.example.victor.fester.Navigation.Fragment_AboutUs;
import com.example.victor.fester.Navigation.Fragment_Info;
import com.example.victor.fester.Navigation.Fragment_Passport;
import com.example.victor.fester.Party.PartyInfo;
import com.example.victor.fester.Toolbox.BitmapManager;
import com.example.victor.fester.User.User;
import com.example.victor.fester.User.UserDBAdapter;

public class NavigationActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private Fragment fragment;

    public final static String EXTRA_STATUS = "com.example.victor.fester.STATUS";
    public final static String EXTRA_ID = "com.example.victor.fester.ID";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.navigation_activity);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        final Activity activity = this;

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close){
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                infoUpdate(getBaseContext(), activity);
            }
        };

        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_party);
        setTitle(getResources().getString(R.string.party));

        fragment = null;

        openParty();

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        if(getFragmentManager().getBackStackEntryCount() == 1)
            setTitle(getResources().getString(R.string.party));

        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        infoUpdate(getBaseContext(), this);

        return true;
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (id == R.id.nav_party) {
            if(fragment != null) {
                while(getFragmentManager().popBackStackImmediate());
            }
            setTitle(getResources().getString(R.string.party));
        }
        else if (id == R.id.nav_info) {

            fragment = new Fragment_Info();

            ft.replace(R.id.nav_content, fragment);
            ft.addToBackStack(null);
            ft.commit();

            setTitle(getResources().getString(R.string.info));

        } else if (id == R.id.nav_passaporte) {

            fragment = new Fragment_Passport();

            ft.replace(R.id.nav_content, fragment);
            ft.addToBackStack(null);
            ft.commit();

            setTitle(getResources().getString(R.string.passport));

        } else if (id == R.id.nav_aboutus) {

            fragment = new Fragment_AboutUs();

            ft.replace(R.id.nav_content, fragment);
            ft.addToBackStack(null);
            ft.commit();

            setTitle(getResources().getString(R.string.aboutus));

        } else if (id == R.id.nav_sair) {
            logout();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public void logout(){
        
        Intent intent = new Intent(this, Login.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
        finish();
    }

    public static void infoUpdate(Context context, Activity activity) {
        // -- Tratamento do NAVEGADOR aqui

        // Recebendo usuario do BD
        UserDBAdapter dbAdapter = new UserDBAdapter(context);
        dbAdapter.open();
        User user = dbAdapter.getUser();
        dbAdapter.close();

        // Tratando a foto
        ImageView nav_img = (ImageView)activity.findViewById(R.id.nav_header_image);
        //Bitmap srcBmp = BitmapFactory.decodeResource(getResources(), R.drawable.foto);
        Bitmap srcBmp = BitmapManager.byteArrayToBitmap(user.getPhoto());
        Bitmap dstBmp;

        if (srcBmp.getWidth() >= srcBmp.getHeight()){
            dstBmp = Bitmap.createBitmap(srcBmp, srcBmp.getWidth()/2 - srcBmp.getHeight()/2, 0, srcBmp.getHeight(), srcBmp.getHeight());
        }else{
            dstBmp = Bitmap.createBitmap(srcBmp, 0, srcBmp.getHeight()/2 - srcBmp.getWidth()/2, srcBmp.getWidth(), srcBmp.getWidth());
        }

        Bitmap src = Bitmap.createScaledBitmap(dstBmp, 110, 110, true);

        RoundedBitmapDrawable dr = RoundedBitmapDrawableFactory.create(activity.getResources(), src);
        dr.setCircular(true);
        nav_img.setImageDrawable(dr);

        // Tratando o nome
        TextView nav_name = (TextView) activity.findViewById(R.id.nav_header_name);
        nav_name.setText(user.getName());

        // Tratando o email
        TextView nav_email = (TextView) activity.findViewById(R.id.nav_header_email);
        nav_email.setText(user.getEmail());
    }

    public void openParty(){

        String partyId = "0"; //getId()

        String partyStatus = "finished"; //getStatus();
        // valores possiveis: incoming, started, finished

        String partyRole = "DJ"; //getPartyRole();
        // valores possiveis: DJ, admin, vendedor, segurança, festeiro

        Intent intent;

        switch (partyStatus) {
            case "incoming":
                intent = new Intent(this, PartyInfo.class);
                intent.putExtra(EXTRA_STATUS, partyStatus);
                intent.putExtra(EXTRA_ID, partyId);
                startActivity(intent);
                break;
            case "started":
                switch (partyRole) {
                    case "DJ":
                        intent = new Intent(this, TabbedActivity.class);
                        intent.putExtra(EXTRA_ID, partyId);
                        startActivity(intent);
                        break;
                    case "admin":
                        intent = new Intent(this, AdminScreen.class);
                        intent.putExtra(EXTRA_ID, partyId);
                        startActivity(intent);
                        break;
                    case "vendedor":
                        intent = new Intent(this, AdminScreen.class);
                        intent.putExtra(EXTRA_ID, partyId);
                        startActivity(intent);
                        break;
                    case "segurança":
                        intent = new Intent(this, Reader.class);
                        intent.putExtra(EXTRA_ID, partyId);
                        startActivity(intent);
                        break;
                    case "festeiro":
                        intent = new Intent(this, TabbedActivity.class); //!!!!!!!!!!!
                        intent.putExtra(EXTRA_ID, partyId);
                        startActivity(intent);
                        break;
                    default:
                        System.out.println(getResources().getString(R.string.error_role_party));
                        break;
                }
                break;
            case "finished":
                intent = new Intent(this, PartyInfo.class);
                intent.putExtra(EXTRA_STATUS, partyStatus);
                intent.putExtra(EXTRA_ID, partyId);
                startActivity(intent);

                break;
            default:
                System.out.println(getResources().getString(R.string.error_status_party));
                break;
        }
    }
}
