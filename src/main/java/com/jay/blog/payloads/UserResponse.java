package com.jay.blog.payloads;

import java.util.List;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {
	private List<UserDto> content;
	private int pageNumber;
	private int pageSize;
	private long totalElements;
	private long totalPages;
	private boolean lastPage;
}
