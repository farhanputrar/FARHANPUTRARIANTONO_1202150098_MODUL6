package com.example.android.farhan_1202150098_modul6.fragment;

/**
 * Created by user on 01/04/2018.
 */

public class MyTopPostFragment extends PostListFragment {
    public MyTopPostsFragment() {}



    @Override

    public Query getQuery(DatabaseReference databaseReference) {

        // [START my_top_posts_query]

        // My top posts by number of stars

        String myUserId = getUid();

        Query myTopPostsQuery = databaseReference.child("user-posts").child(myUserId)

                .orderByChild("starCount");

        // [END my_top_posts_query]



        return myTopPostsQuery;

    }
}
