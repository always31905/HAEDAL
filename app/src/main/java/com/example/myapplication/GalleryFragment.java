package com.example.myapplication;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.util.Date;


public class GalleryFragment extends Fragment {

    ImageView add_image;
    Uri photoUri;
    private final ActivityResultLauncher<Intent> resultLauncher=
            registerForActivityResult(new ActivityResultContracts.StartActivityForResult(),
                    result->{ //람다함수: result라는 변수가 들어가는 함수를 1회용으로 만들겠다
                        if(result.getResultCode()== Activity.RESULT_OK&&result.getData()!=null){
                            photoUri=result.getData().getData();
                            if(photoUri!=null){
                                add_image.setImageURI(photoUri);

                            }
                        }
                    });
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_gallery, container, false);
    }
    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState){
        add_image=view.findViewById(R.id.add_image);

        Intent photoPickerIntent=new Intent(Intent.ACTION_PICK);
        photoPickerIntent.setType("image/*");
        resultLauncher.launch(photoPickerIntent);
        Button upload_button = view.findViewById(R.id.btn_upload);
        upload_button.setOnClickListener(v->{
            uploadImageTOFirebase(photoUri);
        });
    }

    public void uploadImageTOFirebase(Uri imageUri){
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String email = user.getEmail();
        String safeEmail = email.replace(".","_").replace("@","_");
        long timestamp = new Date().getTime()/1000;
        StorageReference storageRef = FirebaseStorage.getInstance().getReference()
                .child("image")
                .child(safeEmail)
                .child(timestamp+".jpg");

        UploadTask uploadTask = storageRef.putFile(imageUri);
        uploadTask.addOnSuccessListener(taskSnapshot->{
            storageRef.getDownloadUrl().addOnSuccessListener(uri->{
                String downloadUrl = uri.toString();
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference();
                databaseReference.child("users")
                        .child(safeEmail)
                        .child(String.valueOf(timestamp))
                        .setValue(downloadUrl);
            });
        });
    }
}