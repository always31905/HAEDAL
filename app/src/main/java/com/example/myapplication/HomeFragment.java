package com.example.myapplication;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
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

public class HomeFragment extends Fragment {

    private RecyclerView recyclerView;
    private DetailAdapter adapter;
    private List<ImageDetail> DetailList;
    RecyclerView homeFragmentRecyclerview;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_home, container, false);
        recyclerView = rootView.findViewById(R.id.homeFragment_Recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        DetailList = new ArrayList<>();
        adapter = new DetailAdapter(DetailList);
        recyclerView.setAdapter(adapter);
        loadImages();
        return rootView;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle SavedInstanceState){
        homeFragmentRecyclerview=view.findViewById(R.id.homeFragment_Recyclerview);
    }



    private void loadImages() {

        DatabaseReference userRef = FirebaseDatabase.getInstance().getReference("users");

        userRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {  // 여기에 user총데이터

                DetailList.clear();

                for (DataSnapshot user : snapshot.getChildren()) {

                    for(DataSnapshot child : user.getChildren()){
                        long timestamp = Long.parseLong(child.getKey());

                        String imageUrl = child.getValue(String.class);

                        ImageItem imageItem = new ImageItem(imageUrl, timestamp);
                        DetailList.add(new ImageDetail(user.getKey(), imageItem));
                    }
                }

                // timestamp 기준으로 내림차순 정렬
                Collections.sort(DetailList, new Comparator<ImageDetail>() {
                    @Override
                    public int compare(ImageDetail o1, ImageDetail o2) {
                        return Long.compare(o2.getImageItem().getTimestamp(), o1.getImageItem().getTimestamp());
                    }
                });
                adapter.notifyDataSetChanged();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}