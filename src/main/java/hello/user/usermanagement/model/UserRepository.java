package hello.user.usermanagement.model;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

public interface UserRepository extends MongoRepository<UserObject, Long> {
	
	UserObject findById(Long Id);
	
	UserObject findByEmail(String email);
	
	UserObject findByFName(String fName);
	
	@Query("{'id': ?0, 'isActive': true}")
	UserObject findUserExists(Long id);
}
