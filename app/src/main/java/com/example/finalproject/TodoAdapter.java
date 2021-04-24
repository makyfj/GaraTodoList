package com.example.finalproject;

import android.media.Rating;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.RatingBar;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    private List<ShowerThought> showerThoughtList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // Declare items layout
        public TextView taskTextView;
        public CheckBox taskCheckBox;
        public SeekBar difficultySeekBar;

        public TextView thoughtTextView;
        public TextView usernameTextView;
        public RatingBar rateRatingBar;

        public MyViewHolder(View view){

            super(view);

            // Initialize items layout
            thoughtTextView = view.findViewById(R.id.textViewShowerThougth);
            usernameTextView = view.findViewById(R.id.textViewEmailUsername);
            rateRatingBar = view.findViewById(R.id.ratingBarRating);

        }
    }

    // Adapter Constructor
    public TodoAdapter(List<ShowerThought> showerThoughtList){
        this.showerThoughtList = showerThoughtList;
    }

    // Instances of ViewHolder
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType){
        // Initiate the individual item layout
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.todo_recycleview_item, parent, false);

        return new MyViewHolder((itemView));
    }

    // Copies data from the underlying collection to the individual item
    @Override
    public void onBindViewHolder(MyViewHolder holder, int position){

        ShowerThought showerThought = showerThoughtList.get(position);

        // Get data to copy into the layout
        String thought = showerThought.getThought();
        String username = showerThought.getUsername();
        float rating = showerThought.getRating();

        // Set data inside the view holder
        holder.thoughtTextView.setText(thought);
        holder.usernameTextView.setText(username);
        holder.rateRatingBar.setRating(rating);
    }

    // Return the number of items in the underlying collection
    @Override
    public int getItemCount() {
        return showerThoughtList.size();
    }

    // change the underlying data collection(not an override)
    public void setData(List<ShowerThought> showerThoughtList){

        // set to the new collection
        this.showerThoughtList = showerThoughtList;

        // notify the recycle view that the underlying data set has changed
        notifyDataSetChanged();
    }
}