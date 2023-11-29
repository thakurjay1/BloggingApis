package com.jay.blog.services;

import com.jay.blog.payloads.CategoryDto;
import com.jay.blog.payloads.CategoryResponse;

public interface CategoryService {
	//create
	 CategoryDto createCategory(CategoryDto categoryDto);
	
	//update
	 CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId);
	
	//delete
	void deleteCategory(Integer categoryId);
	 
	//get
	 CategoryDto getCategoryById(Integer categoryId);
	
	//getAll
	 CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir);
}
