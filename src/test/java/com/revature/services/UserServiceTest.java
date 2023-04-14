package com.revature.services;

import com.revature.repositories.UserDAOImple;
import org.junit.jupiter.api.Test;

import com.revature.models.Role;
import com.revature.models.User;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

public class UserServiceTest
{
    private UserService userService;

    private UserDAOImple userDAOImple = Mockito.mock(UserDAOImple.class);

    public UserServiceTest()
    {
        this.userService = new UserService(userDAOImple);
    }

    @Test
    void validateCredentialsInvalidUsername()
    {
        //arrange
        String expectedUsername = "incorrectusername";
        String expectedPassword = "pass123";
        User expectedOutput = null;
        Mockito.when(userDAOImple.getByUsername(expectedUsername)).thenReturn(expectedOutput);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername,expectedPassword);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void validateCredentialsInvalidPassword()
    {
        //arrange
        String expectedUsername = "bob";
        String expectedPassword = "pass1234";
        User expectedOutput = null;
        Mockito.when(userDAOImple.getByUsername(expectedUsername)).thenReturn(expectedOutput);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername,expectedPassword);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void validateCredentialsValidLogin()
    {
        //arrange
        String expectedUsername = "bob";
        String expectedPassword = "pass123";
        User expectedOutput = null;
        Mockito.when(userDAOImple.getByUsername(expectedUsername)).thenReturn(expectedOutput);

        //act
        User actualOutput = userService.validateCredentials(expectedUsername,expectedPassword);

        //assert
        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void createUserWithUniqueCredentials()
    {
        User expectedUser = new User(5,"username", "password", Role.EMPLOYEE, "user","one", "test@gamil.com");

        Mockito.when(userService.createUser(expectedUser)).thenReturn(expectedUser);
        User actualUser = userService.createUser(expectedUser);

        Assertions.assertEquals(expectedUser,actualUser);
    }

    @Test
    void createUserWithNonUniqueUsername()
    {
        User expectedUser = new User(5,"zach", "pass123", Role.EMPLOYEE, "user","one", "test@gamil.com");

        Mockito.when(userService.createUser(expectedUser)).thenReturn(expectedUser);
        User actualUser = userService.createUser(expectedUser);

        Assertions.assertEquals(expectedUser,actualUser);
    }

    @Test
    void createUserWithNonUniqueEmail()
    {
        User expectedUser = new User(5,"stephenie", "pass123", Role.EMPLOYEE, "user","one", "zstarkey@icloud.com");

        Mockito.when(userService.createUser(expectedUser)).thenReturn(expectedUser);
        User actualUser = userService.createUser(expectedUser);

        Assertions.assertEquals(expectedUser,actualUser);
    }
}
