package com.billsplit.app.billsplitApp.DTO.response;

import java.util.Set;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserResponse {

	private Long id;
	private String username;
	private String email;
	private String firstName;
	private String lastName;
	private Long phoneNo;
	private Set<RoleResponse> roles;
}