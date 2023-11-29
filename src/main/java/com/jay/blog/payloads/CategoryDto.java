package com.jay.blog.payloads;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class CategoryDto {

	private int categoryId;
	
	@NotBlank
	@Size(min = 4, message="Size must be greater than equal to 4 characters")
	private String categoryTitle;
	
	@NotBlank
	@Size(min=10, message="Size must be greater than equal to 10 characters")
	private String categoryDescription;
}
