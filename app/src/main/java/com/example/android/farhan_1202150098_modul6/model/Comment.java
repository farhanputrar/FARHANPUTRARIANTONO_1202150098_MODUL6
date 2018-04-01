package com.example.android.farhan_1202150098_modul6.model;

/**
 * Created by user on 01/04/2018.
 */

public class Comment {
    public String uid;

        public String author;

        public String text;



        public Comment() {

            // Default constructor required for calls to DataSnapshot.getValue(Comment.class)

        }



        public Comment(String uid, String author, String text) {

            this.uid = uid;

            this.author = author;

            this.text = text;

        }
}

