package com.billsplit.app.billsplitApp.repository;

import javax.transaction.Transactional;

import com.billsplit.app.billsplitApp.models.TaskImpl;

@Transactional
public interface TaskRepository extends TaskBaseRepository<TaskImpl> {
	TaskImpl save(TaskImpl task);

}
