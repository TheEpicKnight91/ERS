package com.revature.controllers;

import com.revature.models.JsonResponse;
import com.revature.models.User;
import com.revature.services.UserService;
import io.javalin.http.Context;

public class UserController
{
    private UserService userService;

    public UserController()
    {
        userService = new UserService();
    }

    public UserController(UserService userService)
    {
        this.userService = userService;
    }

    public void registerUser(Context context)
    {
        JsonResponse jsonResponse;
        User user = context.bodyAsClass(User.class);

        User registered = this.userService.createUser(user);

        if (registered == null)
        {
            jsonResponse = new JsonResponse(false,"Username or email already taken", null);
        }
        else
        {
            jsonResponse = new JsonResponse(true,"Registration Successful",registered);
        }
        context.json(jsonResponse);
    }
}
