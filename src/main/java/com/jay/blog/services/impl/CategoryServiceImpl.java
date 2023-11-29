package com.jay.blog.services.impl;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.jay.blog.entities.Category;
import com.jay.blog.exceptions.ResourceNotFoundException;
import com.jay.blog.payloads.CategoryDto;
import com.jay.blog.payloads.CategoryResponse;
import com.jay.blog.repositories.CategoryRepository;
import com.jay.blog.services.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService {

	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private CategoryRepository categoryRepository;
	
	@Override
	public CategoryDto createCategory(CategoryDto categoryDto) {
		// TODO Auto-generated method stub
		Category category = this.modelMapper.map(categoryDto, Category.class);
		Category savedCategory = this.categoryRepository.save(category);
		return this.modelMapper.map(savedCategory, CategoryDto.class);
	}

	@Override
	public CategoryDto updateCategory(CategoryDto categoryDto, Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "ID", categoryId));
		category.setCategoryTitle(categoryDto.getCategoryTitle());
		category.setCategoryDescription(categoryDto.getCategoryDescription());
		Category updatedCategory = this.categoryRepository.save(category);
		return this.modelMapper.map(updatedCategory, CategoryDto.class);
	}

	@Override
	public void deleteCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "ID", categoryId));
		this.categoryRepository.delete(category);

	}

	@Override
	public CategoryDto getCategoryById(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepository.findById(categoryId).orElseThrow(()->new ResourceNotFoundException("Category", "ID", categoryId));
		return this.modelMapper.map(category, CategoryDto.class);
	}

	@Override
	public CategoryResponse getAllCategory(Integer pageNumber, Integer pageSize, String sortBy, String sortDir) {
		Sort sort = sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
		Pageable pageable = PageRequest.of(pageNumber, pageSize, sort);
		Page<Category> pageCategory = this.categoryRepository.findAll(pageable);
		List<Category> allCategory = pageCategory.getContent();
		List<CategoryDto> categoryDtos = allCategory.stream().map((category)->this.modelMapper.map(category, CategoryDto.class)).collect(Collectors.toList());
		
		CategoryResponse categoryResponse = new CategoryResponse();
		categoryResponse.setContent(categoryDtos);
		categoryResponse.setPageNumber(pageCategory.getNumber());
		categoryResponse.setPageSize(pageCategory.getSize());
		categoryResponse.setTotalElements(pageCategory.getTotalElements());
		categoryResponse.setTotalPages(pageCategory.getTotalPages());
		categoryResponse.setLastPage(pageCategory.isLast());
		return categoryResponse;
	}

}
