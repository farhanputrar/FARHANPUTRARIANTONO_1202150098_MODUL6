package com.example.android.farhan_1202150098_modul6.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 01/04/2018.
 */

public class Post {
    public String uid;

        public String author;

        public String title;

        public String body;

        public int starCount = 0;

        public Map<String, Boolean> stars = new HashMap<>();

        public Post() {



        }



        public Post(String uid, String author, String title, String body) {

            this.uid = uid;

            this.author = author;

            this.title = title;

            this.body = body;

        }





        public Map<String, Object> toMap() {

            HashMap<String, Object> result = new HashMap<>();

            result.put("uid", uid);

            result.put("author", author);

            result.put("title", title);

            result.put("body", body);

            result.put("starCount", starCount);

            result.put("stars", stars);



            return result;

        }

        }
