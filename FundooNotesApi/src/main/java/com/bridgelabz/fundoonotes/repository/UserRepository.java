/**
 * @author Nayan Kumar G
 * purpose : Repository provides Crud operations for User
 * 
 */
package com.bridgelabz.fundoonotes.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonotes.entity.User;

@Repository
public interface UserRepository extends CrudRepository<User, Long> {

}
