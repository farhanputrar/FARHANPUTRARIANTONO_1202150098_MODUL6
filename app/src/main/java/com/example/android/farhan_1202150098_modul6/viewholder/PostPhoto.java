package com.example.android.farhan_1202150098_modul6.viewholder;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 01/04/2018.
 */

public class PostPhoto extends AppCompatActivity {

    private static final String TAG = "NewPostActivity";

    private static final String REQUIRED = "Required";

    String imgDecodableString;

    private DatabaseReference mDatabase;

    private EditText mTitleField;

    private EditText mBodyField;

    private Button btnChoose;

    ImageView imageView;

    private FloatingActionButton mSubmitButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post_foto);

        mDatabase = FirebaseDatabase.getInstance().getReference();

        mTitleField = findViewById(R.id.field_title);

        mBodyField = findViewById(R.id.field_body);

        mSubmitButton = findViewById(R.id.fab_submit_post);

        btnChoose = findViewById(R.id.choose);

        imageView = findViewById(R.id.gambar);

        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK,
                        MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                intent.setType("image/*");
                Intent galleryIntent = new Intent(Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(galleryIntent, 1);
            }
        });



        mSubmitButton.setOnClickListener(new View.OnClickListener() {

            @Override

            public void onClick(View v) {

                submitPost();

            }

        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {

            if (requestCode == 1 && resultCode == RESULT_OK
                    && null != data) {


                Uri selectedImage = data.getData();
                String[] filePathColumn = { MediaStore.Images.Media.DATA };


                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);

                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                imgDecodableString = cursor.getString(columnIndex);
                cursor.close();

                imageView.setImageBitmap(BitmapFactory
                        .decodeFile(imgDecodableString));

            } else {
                Toast.makeText(this, "You haven't picked Image",
                        Toast.LENGTH_LONG).show();
            }
        } catch (Exception e) {
            Toast.makeText(this, "Something went wrong", Toast.LENGTH_LONG)
                    .show();
        }
    }

    private void submitPost() {
        final String title = mTitleField.getText().toString();

        final String body = mBodyField.getText().toString();

        if (TextUtils.isEmpty(title)) {

            mTitleField.setError(REQUIRED);

            return;

        }

        if (TextUtils.isEmpty(body)) {

            mBodyField.setError(REQUIRED);

            return;

        }

        setEditingEnabled(false);

        Toast.makeText(this, "Posting...", Toast.LENGTH_SHORT).show();

        FirebaseUser current_user = FirebaseAuth.getInstance().getCurrentUser();

        final String userId = current_user.getUid();

        mDatabase.child("users").child(userId).addListenerForSingleValueEvent(

                new ValueEventListener() {

                    @Override

                    public void onDataChange(DataSnapshot dataSnapshot) {

                        User user = dataSnapshot.getValue(User.class);

                        if (user == null) {

                            Log.e(TAG, "User " + userId + " is unexpectedly null");

                            Toast.makeText(PostFoto.this,

                                    "Error: could not fetch user.",

                                    Toast.LENGTH_SHORT).show();

                        } else {

                            writeNewPost(userId, user.username, title, body);

                        }
                        setEditingEnabled(true);
                        finish();
                    }

                    @Override

                    public void onCancelled(DatabaseError databaseError) {

                        Log.w(TAG, "getUser:onCancelled", databaseError.toException());

                        setEditingEnabled(true);
                    }
                });


    }

    private void setEditingEnabled(boolean b) {
        mTitleField.setEnabled(b);

        mBodyField.setEnabled(b);

        if (b) {

            mSubmitButton.setVisibility(View.VISIBLE);

        } else {

            mSubmitButton.setVisibility(View.GONE);

        }
    }

    private void writeNewPost(String userId, String username, String title, String body) {

        String key = mDatabase.child("posts").push().getKey();

        Post post = new Post(userId, username, title, body);

        Map<String, Object> postValues = post.toMap();

        Map<String, Object> childUpdates = new HashMap<>();

        childUpdates.put("/posts/" + key, postValues);

        childUpdates.put("/user-posts/" + userId + "/" + key, postValues);

        mDatabase.updateChildren(childUpdates);

    }
}