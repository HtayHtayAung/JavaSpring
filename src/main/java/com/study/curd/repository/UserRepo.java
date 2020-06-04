
package com.study.curd.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.study.curd.model.User;

/**
 * @author isgm249
 *
 */
@Repository
public interface UserRepo extends JpaRepository<User,Long>{
	
	@Query(value="select * from user where email=?1",nativeQuery=true)
	User checkEmail(String email);
	@Query(value="select * from user where email=?1 and password=?2",nativeQuery=true)
	User checkLogin(String email, String password);

}
