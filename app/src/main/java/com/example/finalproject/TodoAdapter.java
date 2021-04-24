package com.example.finalproject;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class TodoAdapter extends RecyclerView.Adapter<TodoAdapter.MyViewHolder> {

    private List<Todo> todoList = new ArrayList<>();

    public class MyViewHolder extends RecyclerView.ViewHolder {

        // Declare items layout
        public TextView taskTextView;
        public CheckBox taskCheckBox;
        public SeekBar difficultySeekBar;

        public MyViewHolder(View view){

            super(view);

            // Initialize items layout
            taskTextView = view.findViewById(R.id.textViewTask);
            taskCheckBox = view.findViewById(R.id.checkBoxTask);
            difficultySeekBar = view.findViewById(R.id.seekBarDifficulty);
        }
    }

    // Adapter Constructor
    public TodoAdapter(List<Todo> todoList){
        this.todoList = todoList;
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

        Todo todo = todoList.get(position);

        // Get data to copy into the layout
        String task = todo.getTask();
        boolean checked = todo.getChecked();
        int difficulty = todo.getDifficulty();

        // Set data inside the view holder
        holder.taskTextView.setText(task);
        holder.taskCheckBox.setChecked(checked);
        holder.difficultySeekBar.setProgress(difficulty);
    }

    // Return the number of items in the underlying collection

    @Override
    public int getItemCount() {
        return todoList.size();
    }

    // change the underlying data collection(not an override)
    public void setData(List<Todo> todoList){

        // set to the new collection
        this.todoList = todoList;

        // notify the recycle view that the underlying data set has changed
        notifyDataSetChanged();
    }
}
