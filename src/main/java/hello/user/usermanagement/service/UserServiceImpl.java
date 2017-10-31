package hello.user.usermanagement.service;

import hello.user.usermanagement.exception.BusinessException;
import hello.user.usermanagement.model.UserObject;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {
	
	public List<UserObject> userList = new ArrayList<UserObject>();

	@Override
	public UserObject createUser(UserObject user) throws Exception{
		for(UserObject existingUser : userList){
			if(existingUser.getId().equals(user.getId())){
				return null;
			}
			if(existingUser.getEmail().equalsIgnoreCase(user.getEmail())){
				throw new BusinessException();				
			}
		}
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
	public Boolean deleteUser(String userId) throws Exception{
		for(UserObject existingUser : userList){
			if(existingUser.getId().equals(userId)){
				userList.remove(existingUser);
				return Boolean.TRUE;
			}
		}
		return Boolean.FALSE;
	}

	@Override
	public UserObject fetchUser(String userId) throws Exception {
		for(UserObject existingUser : userList){
			if(existingUser.getId().equals(userId)){
				return existingUser;
			}
		}
		return null;
	}

}
