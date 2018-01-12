package hello.user.usermanagement;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import hello.user.usermanagement.controller.UserController;
import hello.user.usermanagement.exception.BusinessException;
import hello.user.usermanagement.exception.ERR_CODES;
import hello.user.usermanagement.model.UserObject;
import hello.user.usermanagement.service.UserService;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.ResultMatcher;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@RunWith(SpringRunner.class)
@WebMvcTest(value = UserController.class, secure =false)
@AutoConfigureMockMvc
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;
    
       
    UserObject mockUser = new UserObject();
	
    String exampleUserJson = "{\"id\":\"123423\",\"fName\":\"John\",\"lName\":\"Matt\",\"email\":\"John.Smith@gmail.com\",\"pinCode\":\"123456\"}";
    
    String resultJson = "{\"resMsg\":\"User created successfully\",\"userId\":\"1234\",\"valErrors\":null}";

    @Test
    public void shouldCreateUser() throws Exception {
    	mockUser.setId(4444L);
    	mockUser.setfName("John");
    	mockUser.setlName("Matt");
    	mockUser.setEmail("test@test.com");

    	
    	Mockito.when(userService.createUser(Mockito.any(UserObject.class))).thenReturn(mockUser);
    			
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/createUser")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);

			
    	ResultMatcher expectedMsg = MockMvcResultMatchers.jsonPath("resMsg").value("User created successfully");
    	ResultMatcher expectedId =  MockMvcResultMatchers.jsonPath("userId").value("4444");
    	
		mockMvc.perform(requestBuilder).andExpect(status().isCreated())
										.andExpect(expectedMsg)
										.andExpect(expectedId);
		
	    }
    
    
    //Test for user creation failure on using same userid
    @Test
    public void shouldFailCreateUser() throws Exception {
    	
    	BusinessException bre = new BusinessException(ERR_CODES.USER_EXISTS, "", "");

    	
    	Mockito.when(userService.createUser(Mockito.any(UserObject.class))).thenThrow(bre);
    			
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/createUser")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);
   	
    	ResultMatcher expectedMsg = MockMvcResultMatchers.jsonPath("resMsg").value("User creation Failed");
    	ResultMatcher expectedId =  MockMvcResultMatchers.jsonPath("userId").value("123423");
    	
		mockMvc.perform(requestBuilder).andExpect(status().isBadRequest())
										.andExpect(expectedMsg)
										.andExpect(expectedId);
				
    }
    
    @Test
    public void shoutFetchUser() throws Exception{
    	mockUser.setId(4444L);
    	mockUser.setfName("John");
    	mockUser.setlName("Matt");
    	mockUser.setEmail("test@test.com");

    	Mockito.when(userService.fetchUser(Mockito.anyLong())).thenReturn(mockUser);
    	
    	RequestBuilder requestBuilder = MockMvcRequestBuilders.get("/users/4444").accept(MediaType.APPLICATION_JSON);
    	    	
    	
    	ResultMatcher expectedId =  MockMvcResultMatchers.jsonPath("id").value("4444");
    	ResultMatcher expectedfName = MockMvcResultMatchers.jsonPath("fName").value("John");
    	ResultMatcher expectedEmail = MockMvcResultMatchers.jsonPath("email").value("test@test.com");
    	
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
										.andExpect(expectedId)
										.andExpect(expectedfName)
										.andExpect(expectedEmail);
    	    	
    }
    
    @Test
    public void shouldUpdateUser() throws Exception {
    	mockUser.setId(4444L);
    	mockUser.setfName("John");
    	mockUser.setlName("Matt");
    	mockUser.setEmail("test@test.com");

    	
    	Mockito.when(userService.updateUser(Mockito.any(UserObject.class))).thenReturn(mockUser);
    			
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.post("/updateUser")
				.accept(MediaType.APPLICATION_JSON)
				.content(exampleUserJson)
				.contentType(MediaType.APPLICATION_JSON);

			
    	ResultMatcher expectedMsg = MockMvcResultMatchers.jsonPath("resMsg").value("User updated successfully");
    	ResultMatcher expectedId =  MockMvcResultMatchers.jsonPath("userId").value("4444");
    	
		mockMvc.perform(requestBuilder).andExpect(status().isCreated())
										.andExpect(expectedMsg)
										.andExpect(expectedId);
		
    }
    
    @Test
    public void shouldDeleteUser() throws Exception {
    	mockUser.setId(4444L);
    	mockUser.setfName("John");
    	mockUser.setlName("Matt");
    	mockUser.setEmail("test@test.com");

    	
    	Mockito.when(userService.deleteUser(Mockito.anyLong())).thenReturn(Boolean.TRUE);
    			
    	RequestBuilder requestBuilder = MockMvcRequestBuilders
				.delete("/deleteUser/4444");
				
    	ResultMatcher expectedMsg = MockMvcResultMatchers.jsonPath("resMsg").value("User deleted successfully");
    	ResultMatcher expectedId =  MockMvcResultMatchers.jsonPath("userId").value("4444");
    	
		mockMvc.perform(requestBuilder).andExpect(status().isOk())
										.andExpect(expectedMsg)
										.andExpect(expectedId);
		
    }

   
}
