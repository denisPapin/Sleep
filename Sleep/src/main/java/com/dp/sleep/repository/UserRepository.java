package com.dp.sleep.repository;

import com.dp.sleep.model.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends GenericRepository<User>{
    User findUserByLogin(String login);

    User findUserByEmail(String email);

    User findUserByChangePasswordToken(String uuid);
}
