package com.healthhub.healthhub_backend.service;

import com.healthhub.healthhub_backend.dto.CategoryRequest;
import com.healthhub.healthhub_backend.dto.CategoryResponse;
import com.healthhub.healthhub_backend.entity.Category;
import com.healthhub.healthhub_backend.repository.CategoryRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    public  CategoryService(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    // Utility: convert name to slug
    public String toSlug(String name){
        return name.trim().toLowerCase().replaceAll("[^a-z0-9]+", "-");
    }

    // Utility: entity → response DTO
    public CategoryResponse toResponse(Category category){
        return new CategoryResponse(category.getId(),category.getName(),category.getSlug());
    }

    public List<CategoryResponse> getAllCategories(){
        return categoryRepository.findAll()
                .stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }

    public CategoryResponse createCategories(CategoryRequest request){

        if (categoryRepository.existsByName(request.getName())){
            throw  new RuntimeException("Category already exists: " + request.getName());
        }
        Category category = new Category();
        category.setName(request.getName());
        category.setSlug(toSlug(request.getName()));
        return toResponse(categoryRepository.save(category));
    }

    public CategoryResponse updateCategory(Integer id, CategoryRequest request) {
        Category category = categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Category not found"));
        category.setName(request.getName());
        category.setSlug(toSlug(request.getName()));
        return toResponse(categoryRepository.save(category));
    }

    public void deleteCategory(Integer id) {
        if (!categoryRepository.existsById(id)) {
            throw new RuntimeException("Category not found");
        }
        categoryRepository.deleteById(id);
    }



}
