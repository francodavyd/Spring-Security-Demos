package com.francodavyd.repository;

import com.francodavyd.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IRoleRepository extends JpaRepository<Role, Long> {

    List<Role> findByRoleIn (List<String> role);
}
