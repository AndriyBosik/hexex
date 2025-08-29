package org.example.hexex.adapter.out.persistence.repository;

import org.example.hexex.adapter.out.persistence.repository.model.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {
}
