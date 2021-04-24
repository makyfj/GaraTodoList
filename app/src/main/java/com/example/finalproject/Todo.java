package com.example.finalproject;

public class Todo {

    private String task;
    private boolean checked;
    private int difficulty;

    public Todo(String task, boolean checked, int difficulty){
        this.task = task;
        this.checked = checked;
        this.difficulty = difficulty;
    }

    public String getTask() {
        return task;
    }

    public void setTask(String task) {
        this.task = task;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public void setDifficulty(int difficulty) {
        this.difficulty = difficulty;
    }

    public boolean getChecked(){
        return checked;
    }

    public void setChecked(boolean checked){
        this.checked = checked;
    }
}
