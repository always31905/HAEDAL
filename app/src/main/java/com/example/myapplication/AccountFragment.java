package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;


public class AccountFragment extends Fragment {
    private RecyclerView recyclerView; //사진 올릴 recyclerview
    private imageAdapter adapter; // 리사이클러뷰에 넣을 어댑터
    private List<ImageItem> imageList; // 이미지아이템 리스트


    public AccountFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        //fragment acoount라는 xml화면을 inflate해서 rootview에 올린다.
        View rootView = inflater.inflate(R.layout.fragment_account,container,false);
//        //rootview에 xml화면이 담겨있기 때문에 rootview.findviewid사용가능
//        recyclerView = rootView.findViewById(R.id.account_recyclerView);
//        recyclerView.setLayoutManager(new GridLayoutManager(getContext(),3));
//        imageList = new ArrayList<>();
//        adapter = new imageAdapter(getContext(),imageList);
//        // Inflate the layout for this fragment
//        recyclerView.setAdapter(adapter);
//        loadImages();
        return rootView;
    }
    private void loadImages() {
        // 현제 로그인한 유저 불러오기
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        // aaa@aaa.com을 aaa_aaa_com으로 바꿔주는 코드
        String safeEmail = user.getEmail().replace(".", "_").replace("@", "_");

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users").child(safeEmail);

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                imageList.clear();
                for (DataSnapshot child : snapshot.getChildren()) {
                    long timestamp = Long.parseLong(child.getKey());
                    String imageUrl = child.getValue(String.class);
                    imageList.add(new ImageItem(imageUrl, timestamp));
                }
                // 시간순 정렬
                Collections.sort(imageList, Comparator.comparingLong(ImageItem::getTimestamp).reversed());
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

}