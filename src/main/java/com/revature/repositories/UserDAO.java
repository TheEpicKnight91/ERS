package com.revature.repositories;

import com.revature.models.User;

public interface UserDAO
{
    User getByUsername(String username);
    User getByUserId(Integer userid);
    User getByUserEmail(String email);
    User create(User userToBeRegistered);
    Boolean emailIsUnique(String email);
}
