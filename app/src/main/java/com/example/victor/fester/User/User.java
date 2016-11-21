package com.example.victor.fester.User;

import android.content.Context;


/**
 * Created by Victor on 21/11/2016.
 */
public class User {

    private String name;
    private String email;
    private String phone;
    private byte[] photo;
    private long id;

    public User(long id, String name, String email, String phone, byte[] photo){
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.photo = photo;
        this.id = id;
    }

    public void setName(String name) {this.name = name;}

    public void setEmail(String email) {this.email = email;}

    public void setPhone(String phone) {this.phone = phone;}

    public void setPhoto(byte[] photo) {this.photo = photo;}

    public String getName() {return name;}

    public String getEmail() {return email;}

    public String getPhone() {return phone;}

    public byte[] getPhoto() {return photo;}

    public long getId() {return id;}

    public void toDataBase(Context context){

        UserDBAdapter dbAdapter = new UserDBAdapter(context);
        dbAdapter.open();

        // Delete all
        dbAdapter.deleteUsers();

        // Update
        dbAdapter.updateUser(this.name, this.email, this.phone, this.photo);

        dbAdapter.close();
    }

    public void fromDataBase(Context context){

        UserDBAdapter dbAdapter = new UserDBAdapter(context);

        dbAdapter.open();

        User user = dbAdapter.getUser();

        dbAdapter.close();

    }

}
