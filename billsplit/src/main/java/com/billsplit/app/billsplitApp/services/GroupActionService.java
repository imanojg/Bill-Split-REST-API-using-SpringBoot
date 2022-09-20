package com.billsplit.app.billsplitApp.services;

import com.billsplit.app.billsplitApp.models.Group;

public interface GroupActionService {
	Group save(Group group);

	Group findByGroupName(String groupName);

	Group getOne(Long groupId);

	Group findByGroupId(Long groupId);

	// boolean existsByGroupId(Long groupId);
}
