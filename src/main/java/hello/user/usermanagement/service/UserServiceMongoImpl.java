package hello.user.usermanagement.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hello.user.usermanagement.exception.BusinessException;
import hello.user.usermanagement.exception.ERR_CODES;
import hello.user.usermanagement.model.UserObject;
import hello.user.usermanagement.model.UserRepository;

@Service
@Qualifier("mongodb")
public class UserServiceMongoImpl implements UserService {

	@Autowired
	private UserRepository userRepository;
		
	@Override
	public UserObject createUser(UserObject user) throws Exception {
		
		UserObject existingUser = userRepository.findById(user.getId());
		if(existingUser != null && existingUser.getIsActive())
			throw new BusinessException(ERR_CODES.USER_EXISTS, "Id", "User already exists");
		//validate for unique email for all users
		existingUser = userRepository.findByEmail(user.getEmail());
		if(existingUser != null)
			throw new BusinessException(ERR_CODES.USER_EMAIL_EXISTS, "email", "Email already exists");
		
		user.setIsActive(Boolean.TRUE);
		return userRepository.insert(user);
		
		//return null;
	}

	@Override
	public UserObject updateUser(UserObject user) throws Exception {
		
		UserObject existingUser = userRepository.findById(user.getId());
		if(existingUser != null){
			existingUser.setDob(user.getDob());
			existingUser.setPincode(user.getPincode());
			
			return userRepository.save(existingUser);
		}
		
		return null;
	}

	@Override
	public Boolean deleteUser(Long userId) throws Exception {
		UserObject existingUser = userRepository.findById(userId);
		if(existingUser != null) {
			userRepository.delete(existingUser);
			return Boolean.TRUE;
		}
		return Boolean.FALSE;
	}

	@Override
	public UserObject fetchUser(Long userId) throws Exception {
		return userRepository.findById(userId);
	}

}
