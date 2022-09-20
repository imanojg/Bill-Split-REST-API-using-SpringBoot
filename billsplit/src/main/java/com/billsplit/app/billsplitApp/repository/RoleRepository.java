package com.billsplit.app.billsplitApp.repository;

import java.util.Optional;

import com.billsplit.app.billsplitApp.models.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.billsplit.app.billsplitApp.models.Role;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	Optional<Role> findByName(ERole name);
}
