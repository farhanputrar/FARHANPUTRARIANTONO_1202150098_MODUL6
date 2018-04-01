package com.example.android.farhan_1202150098_modul6.model;

/**
 * Created by user on 01/04/2018.
 */

public class User {
    public String username;

        public String email;



        public User() {

            // Default constructor required for calls to DataSnapshot.getValue(User.class)

        }



        public User(String username, String email) {

            this.username = username;

            this.email = email;

        }
}
