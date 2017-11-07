package com.cts.user.application.dao;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.transaction.annotation.Transactional;

import com.cts.user.application.document.User;

@Transactional
public interface UserRepository extends MongoRepository<User, String> {
}
