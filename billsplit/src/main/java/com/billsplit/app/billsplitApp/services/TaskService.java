package com.billsplit.app.billsplitApp.services;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import com.billsplit.app.billsplitApp.models.TaskImpl;
import com.billsplit.app.billsplitApp.repository.PaymentsRepository;
import com.billsplit.app.billsplitApp.repository.ShoppingItemRepository;
import com.billsplit.app.billsplitApp.repository.UserRepository;
import com.billsplit.app.billsplitApp.DTO.request.ItemsRequest;
import com.billsplit.app.billsplitApp.DTO.request.ToDoTaskRequest;
import com.billsplit.app.billsplitApp.DTO.response.ItemsResponse;
import com.billsplit.app.billsplitApp.DTO.response.ToDoTaskResponse;
import com.billsplit.app.billsplitApp.models.Item;
import com.billsplit.app.billsplitApp.models.Payments;
import com.billsplit.app.billsplitApp.models.TaskStatus;
import com.billsplit.app.billsplitApp.models.TaskType;
import com.billsplit.app.billsplitApp.models.User;
import com.billsplit.app.billsplitApp.repository.TaskRepository;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class TaskService {

	@Autowired
	UserDetailsServiceImpl userServiceImpl;

	@Autowired
	GroupActionServiceImpl groupActionServiceImpl;

	@Autowired
	TaskRepository taskRepository;

	@Autowired
    UserRepository userRepository;

	@Autowired
    ShoppingItemRepository shoppingItemRepository;

	@Autowired
    PaymentsRepository paymentsRepository;

	@Autowired
	ModelMapper modelMapper;

	public ResponseEntity<?> createTask(ToDoTaskRequest taskForm) {
		User user = null;
		TaskImpl task = new TaskImpl();
		task.setTaskName(taskForm.getTaskName());
		task.setTaskDescription(taskForm.getTaskDescription());
		task.setStatus(TaskStatus.NEW);
		task.setCreationDate(new Date());
		task.setCompletionDate(stringTODate(taskForm.getCompletionDate()));
		User u = userRepository.findByUsername(taskForm.getUserInCharge());
		task.setUserInCharge(u);
		user = userServiceImpl.getCurrentUserFromSession();
		task.setAddedByUser(user);
		task.setTaskType(TaskType.TO_DO_TASK);
		task.setIsTaskComplete(false);
		task.setIsTaskUpForSwap(taskForm.getIsTaskUpForSwap() == null ? false : taskForm.getIsTaskUpForSwap());
		task.setGroupInfo(groupActionServiceImpl.findByGroupName(user.getGroupInfo().getGroupName()));
		try {
			task = save(task);
			user.getTasks().add(task);

		} catch (Exception e) {
			log.error(e.getMessage());
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

		return ResponseEntity.ok(modelMapper.map(task, ToDoTaskResponse.class));
	}

	public ResponseEntity<?> createItem(ItemsRequest itemForm) {
		User user = null;
		Item task = new Item();
		task.setTaskName(itemForm.getTaskName());
		task.setTaskDescription(itemForm.getTaskDescription());
		task.setStatus(TaskStatus.NEW);
		task.setCreationDate(new Date());
		task.setCompletionDate(stringTODate(itemForm.getCompletionDate()));
		task.setItemName(itemForm.getItemName());
		// User u = userRepository.findByUsername(itemForm.getUserInCharge());
		// task.setUserInCharge(u);
		user = userServiceImpl.getCurrentUserFromSession();
		task.setAddedByUser(user);
		task.setIsTaskComplete(false);
		task.setIsTaskUpForSwap(false);
		task.setTaskType(TaskType.SHOPPING_LIST_ITEM);
		task.setGroupInfo(groupActionServiceImpl.findByGroupName(user.getGroupInfo().getGroupName()));
		try {
			task = save(task);
			user.getTasks().add(task);

		} catch (Exception e) {
			log.error(e.getMessage());

			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(modelMapper.map(task, ItemsResponse.class));
	}

	public List<Item> viewShoppingAllItems() {
		List<Item> taskList = new ArrayList<>();
		taskList = shoppingItemRepository.findAllTaskByGroupAndType(
				userServiceImpl.getCurrentUserFromSession().getGroupInfo(), TaskType.SHOPPING_LIST_ITEM);
		return taskList;
	}

	public List<Item> viewAllUserBoughtItems(HttpServletRequest request) {
		User user = userServiceImpl.getCurrentUserFromSession();
		return shoppingItemRepository.findAllShoppingItemsByUser(user.getId());
	}

	public TaskImpl save(TaskImpl task) {
		return taskRepository.save(task);
	}

	public Item save(Item item) {
		return shoppingItemRepository.save(item);
	}

	public Date stringTODate(String s) {
		Date date;
		try {
			return date = new SimpleDateFormat("yyyy-MM-dd").parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return null;
	}

	public String DateToString() {
		Date date = new Date();
		DateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
		return dateFormat.format(date);
	}

	public void deleteScheduleTask(Long id) {
		taskRepository.deleteByTaskId(id);
	}

	public ResponseEntity<?> updateScheduleTask(TaskImpl taskInfo, ToDoTaskRequest task, User userInCharge) {

		taskInfo.setTaskName(task.getTaskName());
		taskInfo.setTaskDescription(task.getTaskDescription());
		taskInfo.setUserInCharge(userInCharge);
		taskInfo.setCompletionDate(stringTODate(task.getCompletionDate()));
		if (null != task.getIsTaskComplete() && task.getIsTaskComplete()) {
			taskInfo.setStatus(TaskStatus.COMPLETED);
			taskInfo.setIsTaskComplete(true);
		} else if (null != task.getTaskStatus() && null != TaskStatus.valueOf(task.getTaskStatus())) {
			taskInfo.setStatus(TaskStatus.valueOf(task.getTaskStatus()));
			taskInfo.setIsTaskComplete(false);
		}
		if (null != task.getIsTaskUpForSwap()) {
			taskInfo.setIsTaskUpForSwap(task.getIsTaskUpForSwap());
		}
		try {
			taskInfo = save(taskInfo);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(modelMapper.map(taskInfo, ToDoTaskResponse.class));
	}

	public void deleteShoppingItems(long id) {
		shoppingItemRepository.deleteByTaskId(id);
	}

	public ResponseEntity<?> updateShoppingItems(Item item, ItemsRequest itemForm, User boughtBy, Set<User> sharedBy) {

		item.setTaskName(itemForm.getTaskName());
		item.setTaskDescription(itemForm.getTaskDescription());
		item.setCompletionDate(stringTODate(itemForm.getCompletionDate()));
		item.setItemName(itemForm.getItemName());
		if (itemForm.getIsTaskComplete()) {
			item.setStatus(TaskStatus.COMPLETED);
			item.setIsTaskComplete(true);
			item.setPurchasedOnDate(stringTODate(itemForm.getPurchasedOnDate()));
			item.setItemPrice(itemForm.getItemPrice());
			item.setBoughtBy(boughtBy);
			item.setSharedUsers(sharedBy);

			// //Payments
			// Payments payment = new Payments();
			// Set<User> sharedUsers = item.getSharedUsers();
			// Double sharePerUser = item.getItemPrice() / sharedUsers.size();
			// payment.setAmountToBePaidPerUser(sharePerUser);
			// final User boughtByU = item.getBoughtBy();
			// if (sharedUsers.contains(item.getBoughtBy())) {
			// payment.setAmountToBePaid(item.getItemPrice() - sharePerUser);
			// payment.setUsersPaymentPending(item.getSharedUsers().stream().filter(i ->
			// !i.equals(boughtByU))
			// .collect(Collectors.toSet()));
			// } else {
			// payment.setAmountToBePaid(item.getItemPrice());
			// payment.setUsersPaymentPending(item.getSharedUsers());
			// }

			// payment.setAmountPaid(0.0);
			// payment.setItem(item);
			// paymentsRepository.save(payment);

		}
		try {
			item = save(item);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}
		return ResponseEntity.ok(modelMapper.map(item, ItemsResponse.class));

	}

	public List<Payments> viewAllUserPendingPayments(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return paymentsRepository.getAllPendingPaymentsByUser(userServiceImpl.getCurrentUserFromSession().getId());

	}

	public List<Payments> getAllPaidPaymentsByUser(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return paymentsRepository.getAllPaidPaymentsByUser(userServiceImpl.getCurrentUserFromSession().getId());

	}

	public List<Payments> getAllUserBoughtItems(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return paymentsRepository.getAllUserBoughtItems(userServiceImpl.getCurrentUserFromSession().getId());

	}

	public void updateUserPendingPayments(Long paymentId, Long taskId, HttpServletRequest request) {
		// TODO Auto-generated method stub
		Payments payment = paymentsRepository.findById(paymentId).orElse(null);
		if (null != payment) {
			payment.setAmountPaid(payment.getAmountPaid() + payment.getAmountToBePaidPerUser());
			payment.setAmountToBePaid(payment.getAmountToBePaid() - payment.getAmountToBePaidPerUser());
			payment.getUsersPaymentPending()
					.removeIf(user -> user.getId() == userServiceImpl.getCurrentUserFromSession().getId());
			payment.getUsersPaymentReceived().add(userServiceImpl.getCurrentUserFromSession());

			paymentsRepository.save(payment);
		}
		// return
		// paymentsRepository.getAllPaymentsPendingToUser(userServiceImpl.getCurrentUserFromSession(request).getId());

	}

	public List<TaskImpl> getAllTaskForCurrentGroup() {
		List<TaskImpl> taskList = new ArrayList<>();
		taskList = taskRepository.findAllTaskByGroupAndType(userServiceImpl.getCurrentUserFromSession().getGroupInfo(),
				TaskType.TO_DO_TASK);
		return taskList;
	}
}
