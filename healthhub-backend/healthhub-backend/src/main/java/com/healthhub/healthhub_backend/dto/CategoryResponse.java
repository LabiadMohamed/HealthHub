package com.healthhub.healthhub_backend.dto;


public class CategoryResponse {

    private Integer id;
    private  String name;
    private  String slug ;

    public CategoryResponse( Integer id,  String name, String slug){
        this.id = id;
        this.slug = slug;
        this.name = name;
    }

    // Getters
    public Integer getId() { return id; }
    public String getName() { return name; }
    public String getSlug() { return slug; }
}
