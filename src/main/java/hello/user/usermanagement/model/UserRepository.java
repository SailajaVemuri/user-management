package hello.user.usermanagement.model;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<UserObject, Long> {
	
	UserObject findById(Long Id);
	
	UserObject findByEmail(String email);
	
	UserObject findByFName(String fName);
}
