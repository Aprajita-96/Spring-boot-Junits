package com.stackroute.service;

import com.stackroute.domain.User;
import com.stackroute.exception.UserAlreadyExistException;
import com.stackroute.repository.UserRepository;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
@RunWith(SpringRunner.class)
@ContextConfiguration(classes ={UserServiceImpl.class,BaseService.class})
//@TestPropertySource(locations = "classpath:unit-test.properties")
public class UserServiceTest {

    private User user;

    //Create a mock for UserRepository
    @Mock
    private UserRepository userRepository;
    private RestTemplate restTemplate=new RestTemplate();

    //Inject the mocks as dependencies into UserServiceImpl
    @Spy
    @Autowired
    @InjectMocks
    private UserServiceImpl userService;
    List<User> list= null;


    @Before
    public void setUp(){
        //Initialising the mock object
        MockitoAnnotations.initMocks(this);
        user = new User();
        user.setLastName("John");
        user.setId(101);
        user.setFirstName("Jenny");
        user.setAge(10);
        list = new ArrayList<>();
        list.add(user);


    }

    @Test
    public void saveUserTestSuccess() throws UserAlreadyExistException {

        when(userRepository.save((User)any())).thenReturn(user);
        User savedUser = userService.saveUser(user);
        Assert.assertEquals(user,savedUser);

        //verify here verifies that userRepository save method is only called once
        verify(userRepository,times(1)).save(user);
      
    }

    @Test(expected = UserAlreadyExistException.class)
    public void saveUserTestFailure() throws UserAlreadyExistException {
        when(userRepository.save((User)any())).thenReturn(null);
        User savedUser = userService.saveUser(user);
        System.out.println("savedUser" + savedUser);
        Assert.assertEquals(user,savedUser);

       /*doThrow(new UserAlreadyExistException()).when(userRepository).findById(eq(101));
       userService.saveUser(user);*/


    }

    @Test
    public void getAllUser(){

        userRepository.save(user);
        //stubbing the mock to return specific data
        when(userRepository.findAll()).thenReturn(list);
        List<User> userlist = userService.getAllUser();
        Assert.assertEquals(list,userlist);
    }

@Test
    public void testGetDetails(){
        User usertest=new User();
        usertest.setId(1);
        usertest.setFirstName("Hey");
        User resultant=new User(); //this is the dto of the result object
        resultant.setId(usertest.getId());
        resultant.setFirstName(usertest.getFirstName());
    ResponseEntity<User> entity=new ResponseEntity<>(resultant, HttpStatus.ACCEPTED);

    Mockito.when(restTemplate.exchange(
            ArgumentMatchers.anyString(),
            any(HttpMethod.class),
            ArgumentMatchers.<HttpEntity<?>> any(),
            ArgumentMatchers.<Class<User>> any())).thenReturn(entity);

    Assert.assertNotNull(userService.getUserDetails(user));



}





}
