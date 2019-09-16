package com.stackroute.service;

import com.stackroute.domain.User;
import com.stackroute.exception.UserAlreadyExistException;
import com.stackroute.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;

@Service
public class UserServiceImpl extends BaseService implements UserService  {
    String esb="hqul/1234";
    private UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        super();
        this.userRepository = userRepository;

    }

    @Override
    public User saveUser(User user) throws UserAlreadyExistException {
        User savedUser = null;
        if(userRepository.existsById(user.getId())){
            throw new UserAlreadyExistException("User already exist");
        }

        else{
            savedUser = userRepository.save(user);
            if(savedUser == null){
                throw new UserAlreadyExistException("User already exist");
            }
        }

         return savedUser;



    }

    @Override
    public List<User> getAllUser() {
        return userRepository.findAll();


    }
    @Override
    public String getUserDetails(User user){ //the user we are getting here is some requets object which will have id and coid and insured number.
        HttpHeaders headers1 = getHttpHeaders();
        System.out.println(user.getId()); //check the id
        ResponseEntity<User > res=null;
        try{
            //set some insured number suppose user.setId(service.getID());
            HttpEntity<User> entity=new HttpEntity<>(user,getHttpHeaders());
            res=getRestTemplate().exchange(esb,HttpMethod.POST,entity,User.class);

        }
        catch (Exception e){
            System.out.println("Exception");
        }
        return res.getBody().getFirstName();
    }
}
