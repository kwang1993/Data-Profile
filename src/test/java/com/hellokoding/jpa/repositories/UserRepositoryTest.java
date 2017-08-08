package com.hellokoding.jpa.repositories;


import com.hellokoding.jpa.configuration.RepositoryConfiguration;
import com.hellokoding.jpa.models.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = {RepositoryConfiguration.class})
public class UserRepositoryTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testSaveUser(){
        //setup user
        User user = new User("testUser", "testPassword");
        

        //save user, verify has ID value after save
        assertNull(user.getId()); //null before save
        userRepository.save(user);
        assertNotNull(user.getId()); //not null after save
        //fetch from DB
        User fetchedUser = userRepository.findOne(user.getId());

        //should not be null
        assertNotNull(fetchedUser);

        //should equal
        assertEquals(user.getId(), fetchedUser.getId());


        //update description and save
        fetchedUser.setUserName("NewUserName");
        userRepository.save(fetchedUser);

        //get from DB, should be updated
        User fetchedUpdatedUser = userRepository.findOne(fetchedUser.getId());
        assertEquals(fetchedUser.getUserName(), fetchedUpdatedUser.getUserName());

//        //verify count of users in DB
//        long userCount = userRepository.count();
//        assertEquals(userCount, 1);
//
//        //get all users, list should only have one
//        Iterable<User> users = userRepository.findAll();
//
//        int count = 0;
//
//        for(User u : users){
//            count++;
//        }
//
//        assertEquals(count, 1);
    }
}
