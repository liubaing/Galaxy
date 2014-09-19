package com.liubaing.galaxy.repository.jpa;

import com.liubaing.galaxy.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 类说明:用户DAO接口
 *
 * @author heshuai
 * @version Feb 24, 2012
 */
public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsernameAndPassword(String username, String password);

    User findByUsername(String username);

    User findByToken(String token);

}
