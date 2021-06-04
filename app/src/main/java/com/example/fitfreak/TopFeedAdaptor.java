package com.example.fitfreak;



import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;

//import com.example.myapiapp.R;

import java.util.List;

public class TopFeedAdaptor extends RecyclerView.Adapter<TopFeedAdaptor.ViewHolder> {

    List<TopFeedModel> topFeedModelList;
    Context context;

    public TopFeedAdaptor(List<TopFeedModel> topFeedModelList, Context context) {
        this.topFeedModelList = topFeedModelList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view= LayoutInflater.from(context).inflate(R.layout.top_feed_layout,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(topFeedModelList.get(position).getTitle());
        holder.desc.setText(topFeedModelList.get(position).getDesc());
        holder.link.setText(topFeedModelList.get(position).getLink());
        holder.author.setText(topFeedModelList.get(position).getAuthor());

    }

    @Override
    public int getItemCount() {
        return topFeedModelList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title,desc,link,author;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title_content);
            desc=itemView.findViewById(R.id.desc_content);
            link=itemView.findViewById(R.id.url_content);
            author=itemView.findViewById(R.id.author_content);
        }
    }
}

