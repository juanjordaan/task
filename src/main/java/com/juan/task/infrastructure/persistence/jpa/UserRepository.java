package com.juan.task.infrastructure.persistence.jpa;

import org.springframework.stereotype.Repository;

import com.juan.task.domain.model.User;

@Repository
public class UserRepository extends AbstractJpaRepository<User, Long> {

}
