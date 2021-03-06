package com.example.fitfreak;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;


import com.google.firebase.auth.FirebaseAuth;
//<<<<<<< HEAD
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
//=======
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
//>>>>>>> 70d271e8a8aca8bcd215421dfd626ef65cf294ee
import com.kwabenaberko.newsapilib.NewsApiClient;
import com.kwabenaberko.newsapilib.models.request.EverythingRequest;
import com.kwabenaberko.newsapilib.models.response.ArticleResponse;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class api extends AppCompatActivity {
    DatabaseReference databaseReference;
    String userId;
    private RecyclerView topFeedRecyclerView;
    private TopFeedAdaptor topFeedAdaptor;
    private List<TopFeedModel> topFeedModelList=new ArrayList<>();
    private int pageNumber=1,per_page=20;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_api);
        topFeedRecyclerView=findViewById(R.id.top_feed_recycler_view);

        userId= FirebaseAuth.getInstance().getCurrentUser().getUid();
        databaseReference= FirebaseDatabase.getInstance().getReference("Users").child(userId);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                pageNumber=snapshot.getValue(User.class).getDays();
                pageNumber=(pageNumber%5)+1;
                getData();


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });


        //getData();
        topFeedAdaptor=new TopFeedAdaptor(topFeedModelList,this);
        LinearLayoutManager layoutManager=new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        topFeedRecyclerView.setLayoutManager(layoutManager);
        topFeedRecyclerView.setAdapter(topFeedAdaptor);
        topFeedAdaptor.notifyDataSetChanged();
    }
    private void getData()
    {
        NewsApiClient newsApiClient = new NewsApiClient("8daa0a21bd7e4017aa08c678b50d0709");
        newsApiClient.getEverything(
                new EverythingRequest.Builder()
                        .q("fitness").pageSize(per_page).page(pageNumber).build(),
                new NewsApiClient.ArticlesResponseCallback() {
                    @Override
                    public void onSuccess(ArticleResponse response) {
                        for(int i=0;i<response.getArticles().size();i++) {
                            String title = response.getArticles().get(i).getTitle();
                            // Toast.makeText(MainActivity.this,title,Toast.LENGTH_SHORT).show();
                            String desc = response.getArticles().get(i).getDescription();
                            String url=response.getArticles().get(i).getUrl();
                            String author = response.getArticles().get(i).getAuthor();
                            topFeedModelList.add(new TopFeedModel(title, desc, url, author));
                        }
                        topFeedAdaptor.notifyDataSetChanged();
                        pageNumber++;
                    }

                    @Override
                    public void onFailure(Throwable throwable) {
                        //System.out.println(throwable.getMessage());
                        Toast.makeText(getApplicationContext(),throwable.getMessage(),Toast.LENGTH_SHORT).show();
                    }
                }
        );
    }
}