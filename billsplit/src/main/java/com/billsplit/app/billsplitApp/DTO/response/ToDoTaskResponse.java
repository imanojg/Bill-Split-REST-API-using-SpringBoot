package com.billsplit.app.billsplitApp.DTO.response;

import com.billsplit.app.billsplitApp.models.TaskStatus;
import com.billsplit.app.billsplitApp.models.TaskType;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ToDoTaskResponse {
	private String taskId;
	private String taskName;
	private TaskType taskType;
	private TaskStatus taskStatus;
	private String taskDescription;
	private String creationDate;
	private String completionDate;
	private UserResponse addedByUser;
	private Boolean isTaskComplete;
	private UserResponse userInCharge;
	private Boolean isTaskUpForSwap;
}