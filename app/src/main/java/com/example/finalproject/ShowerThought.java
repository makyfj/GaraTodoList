package com.example.finalproject;

public class ShowerThought {

    private String thought;
    private String username;
    private float rating;

    public ShowerThought(String thought, String username, float rating){
        this.thought = thought;
        this.username = username;
        this.rating = rating;
    }

    public String getThought() {
        return thought;
    }

    public void setThought(String thought) {
        this.thought = thought;
    }

    public float getRating() {
        return rating;
    }

    public void setRating(float rating) {
        this.rating = rating;
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }
}
