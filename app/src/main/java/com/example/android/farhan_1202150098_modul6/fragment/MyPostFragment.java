package com.example.android.farhan_1202150098_modul6.fragment;

/**
 * Created by user on 01/04/2018.
 */

public class MyPostFragment extends PostListFragment {

    public MyPostsFragment() {}



    @Override

    public Query getQuery(DatabaseReference databaseReference) {

        return databaseReference.child("user-posts")

                .child(getUid());

    }
}
