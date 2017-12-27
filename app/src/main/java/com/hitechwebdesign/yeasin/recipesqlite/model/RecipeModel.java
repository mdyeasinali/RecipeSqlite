package com.hitechwebdesign.yeasin.recipesqlite.model;

/**
 * Created by Yeasin on 12/26/2017.
 */

public class RecipeModel {
    private int id;
    private String name;
    private String ingredients;
    private String preparation;
    private String time;
    private int cost;
    private String catgory;
    private String image;
    private String author;
    private boolean aimer;

    public RecipeModel(int id, String name, String ingredients, String preparation, String time, String catgory, String image) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.time = time;
        this.image = image;
        this.author = author;
        this.catgory = catgory;
        this.id = id;
    }

    public RecipeModel(String name, String ingredients, String preparation, String time, String catgory, String image) {
        this.name = name;
        this.ingredients = ingredients;
        this.preparation = preparation;
        this.time = time;
        this.catgory = catgory;
        this.image = image;
        this.author = author;
    }

    public RecipeModel(RecipeModel fullRecipe){
        this.name = fullRecipe.getName();
        this.ingredients = fullRecipe.getIngredients();
        this.preparation = fullRecipe.getPreparation();
        this.time = fullRecipe.getTime();
        this.catgory = fullRecipe.getCatgory();
        this.image = fullRecipe.getImage();
        this.id = fullRecipe.getId();
        this.aimer = fullRecipe.getAimer();
    }

    public boolean getAimer() {
        return aimer;
    }

    public int getId() {
        return id;
    }

    public void setAimer(boolean aimer) {
        this.aimer = aimer;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIngredients() {
        return ingredients;
    }

    public void setIngredients(String ingredients) {
        this.ingredients = ingredients;
    }

    public String getPreparation() {
        return preparation;
    }

    public void setPreparation(String preparation) {
        this.preparation = preparation;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }



    public String getCatgory() {
        return catgory;
    }

    public void SetCatgory(String difficulty) {
        this.catgory = difficulty;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }



}
