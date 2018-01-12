package hello.user.usermanagement.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import hello.user.usermanagement.exception.BusinessException;
import hello.user.usermanagement.model.UserObject;
import hello.user.usermanagement.service.UserService;

@RestController
public class UserController {
	
	@Autowired
	@Qualifier("mongodb")
	private  UserService userService;
		
	/*public UserController(UserService userService){
		this.userService = userService;
	}
	*/
	
	
	@GetMapping("/user/{userId}")
	public ResponseEntity<?> fetchUser(@PathVariable Long userId){
		
		UserObject user = null;
		try {
			user = userService.fetchUser(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(user != null)		
			return new ResponseEntity<UserObject>(user, HttpStatus.OK);
		else {
			ResponseObject resObj = new ResponseObject();
			resObj.setResMsg("Does not exist");
			resObj.setUserId(userId);
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.OK);
		}
			
	}
	
	@PostMapping("/user/createUser")
	public ResponseEntity<ResponseObject> createUser(@Valid @RequestBody UserObject user){
		ResponseObject resObj = new ResponseObject();
		UserObject userCreated  = null;
		try{
			userCreated = userService.createUser(user);
		}
		catch(BusinessException e){
			resObj.setResMsg("User creation Failed");
			resObj.setUserId(user.getId());
			resObj.addValError(new ValError(e.getErrCode(), e.getErrField(), e.getErrMsg()));
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		
		if(userCreated != null){			
			resObj.setResMsg("User created successfully");
			resObj.setUserId(userCreated.getId());
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.CREATED);
		}else{			
			resObj.setResMsg("User creation failed");
			resObj.setUserId(user.getId());
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
		}
				
	}
	
	@PostMapping("/user/updateUser")
	public ResponseEntity<ResponseObject> updateUser(@Valid @RequestBody UserObject user, RedirectAttributes redirectAttributes){
		
		UserObject userUpdated = null;
		try {
			userUpdated = userService.updateUser(user);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		ResponseObject resObj;
		if(userUpdated != null){
			resObj = new ResponseObject();
			resObj.setResMsg("User updated successfully");
			resObj.setUserId(userUpdated.getId());
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.CREATED);
		}else
		{
			resObj = new ResponseObject();
			resObj.setResMsg("User does not exist");
			resObj.setUserId(user.getId());
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
		}
		
		
	}
	
	@DeleteMapping("/user/deleteUser/{userId}")
	public ResponseEntity<ResponseObject> deleteUser(@PathVariable Long userId, RedirectAttributes redirectAttributes){
		Boolean res = null;
		try {
			res = userService.deleteUser(userId);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(res == Boolean.TRUE){
			ResponseObject resObj = new ResponseObject();
			resObj.setResMsg("User deleted successfully");
			resObj.setUserId(userId);
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.OK);
		}else
		{
			ResponseObject resObj = new ResponseObject();
			resObj.setResMsg("User does not exist");
			resObj.setUserId(userId);
			return new ResponseEntity<ResponseObject>(resObj, HttpStatus.BAD_REQUEST);
		}
		
		
			
		
	}

}