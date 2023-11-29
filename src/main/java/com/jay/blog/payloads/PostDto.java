package com.jay.blog.payloads;

import java.util.*;
import java.util.ArrayList;
import java.util.Date;
import com.jay.blog.entities.*;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PostDto {
	private Integer postId;
	private String postTitle;
	private String postContent;
	private String postImageName;
	private Date postAddedDate;
	
	private CategoryDto category;
	private UserDto user;
	
	private List<CommentDto> comments =  new ArrayList<>();
}
