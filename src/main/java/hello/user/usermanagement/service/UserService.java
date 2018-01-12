package hello.user.usermanagement.service;

import hello.user.usermanagement.model.UserObject;

public interface UserService {
	public UserObject createUser(UserObject user) throws Exception;
	public UserObject updateUser(UserObject user) throws Exception;
	public Boolean deleteUser(Long userId) throws Exception;
	public UserObject fetchUser(Long userId) throws Exception;
}
