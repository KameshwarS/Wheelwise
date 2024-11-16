package com.infosys.Wheelwise.repo;

import java.util.Optional;

import com.infosys.Wheelwise.Model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface UserRepository extends JpaRepository<User, Integer> {
	Optional<User> findByEmail(String email);  
}