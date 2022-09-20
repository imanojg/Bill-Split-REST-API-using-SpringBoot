package com.billsplit.app.billsplitApp.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;

import com.billsplit.app.billsplitApp.models.Item;

public interface ShoppingItemRepository extends TaskBaseRepository<Item> {

	@Query(value = "select t from #{#entityName} t where t.boughtBy = ?1")
	List<Item> findAllShoppingItemsByUser(Long boughtBy);
}