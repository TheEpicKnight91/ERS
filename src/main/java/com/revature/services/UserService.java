package com.revature.services;

import com.revature.models.User;
import com.revature.repositories.UserDAOImple;

/**
 * The UserService should handle the processing and retrieval of Users for the ERS application.
 *
 * {@code getByUsername} is the only method required;
 * however, additional methods can be added.
 *
 * Examples:
 * <ul>
 *     <li>Create User</li>
 *     <li>Update User Information</li>
 *     <li>Get Users by ID</li>
 *     <li>Get Users by Email</li>
 *     <li>Get All Users</li>
 * </ul>
 */
public class UserService {

	/**
	 *     Should retrieve a User with the corresponding username or an empty optional if there is no match.
     */
	private UserDAOImple userDAOImple;

	public UserService()
	{
		this.userDAOImple = new UserDAOImple();
	}
	public UserService(UserDAOImple userDAOImple)
	{
		this.userDAOImple = userDAOImple;
	}
	public User validateCredentials(String username,String password)
	{
		User user = this.userDAOImple.getByUsername(username);
		if (user == null || !password.equals(user.getPassword()))
		{
			return null;
		}
		return user;
	}

	public User createUser(User user)
	{
		User userDb = userDAOImple.getByUsername(user.getUsername());
		User userDb2 = userDAOImple.getByUserEmail(user.getEmail());
		if (userDb == null && userDb2 == null)
		{
			return this.userDAOImple.create(user);
		}
		return null;
	}
}
