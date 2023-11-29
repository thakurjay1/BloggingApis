package com.jay.blog.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jay.blog.payloads.ApiResponse;
import com.jay.blog.payloads.CategoryDto;
import com.jay.blog.payloads.CategoryResponse;
import com.jay.blog.services.CategoryService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/categories")
public class CategoryController {
	
	@Autowired
	private CategoryService categoryService;
	
	//create
	@PostMapping("/")
	public ResponseEntity<CategoryDto> createUser(@Valid @RequestBody CategoryDto categoryDto){
		CategoryDto createdCategory = this.categoryService.createCategory(categoryDto);
		return new ResponseEntity<CategoryDto>(createdCategory, HttpStatus.CREATED);
	}
	
	//update
	@PutMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> updateUser(@Valid @RequestBody CategoryDto categoryDto, @PathVariable() Integer categoryId){
		CategoryDto updatedCategory = this.categoryService.updateCategory(categoryDto, categoryId);
		return new ResponseEntity<CategoryDto>(updatedCategory, HttpStatus.OK);
	}
	
	//delete
	@DeleteMapping("/{categoryId}")
	public ResponseEntity<ApiResponse> deleteCategory(@PathVariable Integer categoryId){
		this.categoryService.deleteCategory(categoryId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Category deleted successfully", true), HttpStatus.OK);
	}
	
	//get
	@GetMapping("/{categoryId}")
	public ResponseEntity<CategoryDto> getCategoryById(@PathVariable Integer categoryId){
		CategoryDto category = this.categoryService.getCategoryById(categoryId);
		return new ResponseEntity<CategoryDto>(category, HttpStatus.OK);
	}
	
	//getAll
	@GetMapping("/")
	public ResponseEntity<CategoryResponse> getAllCategory(
			@RequestParam(value="pageNumber", defaultValue = "0", required = false) Integer pageNumber,
			@RequestParam(value="pageSize", defaultValue = "5", required = false) Integer pageSize,
			@RequestParam(value="soryBy", defaultValue = "categoryId", required = false) String sortBy,
			@RequestParam(value="sortDir", defaultValue = "asc", required=false) String sortDir
			){
		CategoryResponse allCategory = this.categoryService.getAllCategory(pageNumber, pageSize, sortBy, sortDir);
		return new ResponseEntity<CategoryResponse>(allCategory, HttpStatus.OK);
	}
}
