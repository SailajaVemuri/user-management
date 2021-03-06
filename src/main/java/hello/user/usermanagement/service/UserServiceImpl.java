package hello.user.usermanagement.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import hello.user.usermanagement.exception.BusinessException;
import hello.user.usermanagement.exception.ERR_CODES;
import hello.user.usermanagement.model.UserObject;

@Service
@Qualifier("list")
public class UserServiceImpl implements UserService {
	
	public List<UserObject> userList = new ArrayList<UserObject>();

	@Override
	public UserObject createUser(UserObject user) throws Exception{
		for(UserObject existingUser : userList){
			if(existingUser.getId().equals(user.getId()) && existingUser.getIsActive()){
				throw new BusinessException(ERR_CODES.USER_EXISTS, "Id", "User already exists");	
			}
			if(existingUser.getEmail().equalsIgnoreCase(user.getEmail())){
				throw new BusinessException(ERR_CODES.USER_EMAIL_EXISTS, "email", "Email already exists");				
			}
		}
		user.setIsActive(Boolean.TRUE);
		userList.add(user);
		return user;

	}

	@Override
	public UserObject updateUser(UserObject user) throws Exception{
		for(UserObject existingUser : userList){
			if(existingUser.getId().equals(user.getId())){
				existingUser.setDob(user.getDob());
				existingUser.setPincode(user.getPincode());
				return existingUser;
			}
		}
		return null;
	}

	@Override
	public Boolean deleteUser(Long userId) throws Exception{
		for(UserObject existingUser : userList){
			if(existingUser.getId() == userId){
				//userList.remove(existingUser);
				existingUser.setIsActive(Boolean.FALSE);
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	@Override
	public UserObject fetchUser(Long userId) throws Exception {
		for(UserObject existingUser : userList){
			if(existingUser.getId() == userId){
				return existingUser;
			}
		}
		return null;
	}

}
