package com.example.android.farhan_1202150098_modul6.fragment;

/**
 * Created by user on 01/04/2018.
 */

public class RecentPostFragment extends PostListFragment {
    public RecentPostsFragment(){}

    @Override

    public Query getQuery(DatabaseReference databaseReference) {



        Query recentPostsQuery = databaseReference.child("posts")

                .limitToFirst(100);


        return recentPostsQuery;

    }
}
