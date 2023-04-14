package com.revature.repositories;

import com.revature.models.Role;
import com.revature.models.User;
import com.revature.util.ConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Locale;

public class UserDAOImple implements UserDAO
{

    ConnectionFactory connect = ConnectionFactory.getInstance();
    /**
     * Should retrieve a User from the DB with the corresponding username or an empty optional if there is no match.
     */
    @Override
    public User getByUsername(String username)
    {
        User user = null;
        try(Connection conn = connect.getConnection())
        {
            String sqlStatement = "select u.ERS_USERS_ID, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email, r.user_role from ERS_USERS u\n" +
                    "inner join ERS_USER_ROLES r on u.user_role_id = r.ers_user_role_id where u.ers_username = ?;";

            PreparedStatement ps = conn.prepareStatement(sqlStatement);
            ps.setString(1, username);

            ResultSet rsUser = ps.executeQuery();

            while(rsUser.next())
            {
                user = new User(rsUser.getInt(1),rsUser.getString(2), rsUser.getString(3), Role.valueOf(rsUser.getString(7).toUpperCase(Locale.ROOT)),rsUser.getString(4),rsUser.getString(5), rsUser.getString(6));
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return user;
    }
    @Override
    public User getByUserId(Integer userid)
    {
        User user = null;
        try(Connection conn = connect.getConnection())
        {
            String sqlStatement = "select u.ERS_USERS_ID, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email, r.user_role from ERS_USERS u\n" +
                    "inner join ERS_USER_ROLES r on u.user_role_id = r.ers_user_role_id where u.ers_users_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sqlStatement);
            ps.setInt(1, userid);

            ResultSet rsUser = ps.executeQuery();

            while(rsUser.next())
            {
                user = new User(rsUser.getInt(1),rsUser.getString(2), rsUser.getString(3), Role.valueOf(rsUser.getString(7).toUpperCase(Locale.ROOT)),rsUser.getString(4),rsUser.getString(5), rsUser.getString(6));
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return user;
    }
    @Override
    public User getByUserEmail(String email)
    {
        User user = null;
        try(Connection conn = connect.getConnection())
        {
            String sqlStatement = "select u.ERS_USERS_ID, u.ers_username, u.ers_password, u.user_first_name, u.user_last_name, u.user_email, r.user_role from ERS_USERS u\n" +
                    "inner join ERS_USER_ROLES r on u.user_role_id = r.ers_user_role_id where u.user_email = ?;";

            PreparedStatement ps = conn.prepareStatement(sqlStatement);
            ps.setString(1,email);

            ResultSet rsUser = ps.executeQuery();

            while(rsUser.next())
            {
                user = new User(rsUser.getInt(1),rsUser.getString(2), rsUser.getString(3), Role.valueOf(rsUser.getString(7).toUpperCase(Locale.ROOT)),rsUser.getString(4),rsUser.getString(5), rsUser.getString(6));
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return user;
    }
    /**
     * <ul>
     *     <li>Should Insert a new User record into the DB with the provided information.</li>
     *     <li>Should throw an exception if the creation is unsuccessful.</li>
     *     <li>Should return a User object with an updated ID.</li>
     * </ul>
     *
     * Note: The userToBeRegistered will have an id=0, and username and password will not be null.
     * Additional fields may be null.
     */
    @Override
    public User create(User userToBeRegistered)
    {
        try(Connection conn = connect.getConnection())
        {
            String sql = "insert into ers_users (ers_username,ers_password,user_first_name,user_last_name,user_email,user_role_id) values (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1,userToBeRegistered.getUsername());
            ps.setString(2,userToBeRegistered.getPassword());
            ps.setString(3,userToBeRegistered.getFirst_name());
            ps.setString(4,userToBeRegistered.getLast_name());
            ps.setString(5,userToBeRegistered.getEmail());
            ps.setInt(6,userToBeRegistered.getRole().ordinal()+1);

            ps.executeUpdate();
            userToBeRegistered.setId(getByUsername(userToBeRegistered.getUsername()).getId());
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return userToBeRegistered;
    }
    @Override
    public Boolean emailIsUnique(String email)
    {

        Boolean isUnique = false;
        try(Connection conn = connect.getConnection())
        {
            String sql = "select * from ers_users where user_email = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1,email);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                if (rs.getInt(1) == 0)
                {
                    isUnique = true;
                }
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return isUnique;
    }
}
