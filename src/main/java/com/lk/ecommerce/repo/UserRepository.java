package com.lk.ecommerce.repo;

import com.lk.ecommerce.entity.User;
import com.lk.ecommerce.eums.UserRoles;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    User findByEmail(String userName);

    User findByRole(UserRoles role);

    boolean existsByEmail(String userName);
}
