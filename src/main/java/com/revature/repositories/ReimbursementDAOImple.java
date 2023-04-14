package com.revature.repositories;

import com.revature.exceptions.ReimbursementDidntUpdateException;
import com.revature.models.*;
import com.revature.util.ConnectionFactory;

import java.sql.*;
import java.util.*;

public class ReimbursementDAOImple implements ReimbursementDAO
{
    ConnectionFactory connect = ConnectionFactory.getInstance();
    /**
     * Should retrieve a Reimbursement from the DB with the corresponding id or an empty optional if there is no match.
     */
    @Override
    public Reimbursement getById(int id)
    {
        Reimbursement reimbursement = null;
        try(Connection conn = connect.getConnection())
        {
            String sql = "select r.REIMB_ID, r.reimb_amount, to_char(reimb_submitted::timestamp, 'DD Mon YYYY HH:MIPM'), to_char(reimb_resolved ::timestamp, 'DD Mon YYYY HH:MIPM'), r.reimb_description, r.reimb_receipt, r.reimb_author, reimb_resolver, s.reimb_status, rt.reimb_type from ERS_REIMBURSEMENT r\n" +
                    "inner join ERS_REIMBURSEMENT_STATUS s on r.REIMB_STATUS_ID = s.REIMB_STATUS_ID\n" +
                    "inner join ers_reimbursement_type rt on r.reimb_type_id = rt.reimb_type_id where reimb_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, id);

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                reimbursement = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBinaryStream(6),rs.getInt(7),rs.getInt(8), Status.valueOf(rs.getString(9).toUpperCase(Locale.ROOT)), Type.valueOf(rs.getString(10).toUpperCase(Locale.ROOT)));
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return reimbursement;
    }
    @Override
    public List<Reimbursement> getAllReimbursements()
    {
        List<Reimbursement> list = new ArrayList<>();
        Reimbursement reimbursement = null;
        try (Connection conn = connect.getConnection())
        {
            String sql = "select r.REIMB_ID, r.reimb_amount,to_char(reimb_submitted::timestamp, 'DD Mon YYYY HH:MIPM'), to_char(reimb_resolved ::timestamp, 'DD Mon YYYY HH:MIPM'), r.reimb_description, r.reimb_receipt, r.reimb_author, r.reimb_resolver, s.reimb_status, rt.reimb_type from ERS_REIMBURSEMENT r\n" +
                    "inner join ERS_REIMBURSEMENT_STATUS s on r.REIMB_STATUS_ID = s.REIMB_STATUS_ID\n" +
                    "inner join ers_reimbursement_type rt on r.reimb_type_id = rt.reimb_type_id;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                reimbursement = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBinaryStream(6),rs.getInt(7),rs.getInt(8), Status.valueOf(rs.getString(9).toUpperCase(Locale.ROOT)), Type.valueOf(rs.getString(10).toUpperCase(Locale.ROOT)));
                list.add(reimbursement);
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return list;
    }

    /**
     * Should retrieve a List of Reimbursements from the DB with the corresponding Status or an empty List if there are no matches.
     */
    @Override
    public List<Reimbursement> getByStatus(Status status)
    {
        List<Reimbursement> ReimList = new ArrayList<>();

        try(Connection conn = connect.getConnection())
        {
            String sql = "select reimb_id from ers_reimbursement where reimb_status_id = ?";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,status.ordinal()+1);

            ResultSet rs = ps.executeQuery();

            while (rs.next())
            {
                Reimbursement reimbursement = getById(rs.getInt(1));
                ReimList.add(reimbursement);
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return ReimList;
    }

    /**
     * <ul>
     *     <li>Should Update an existing Reimbursement record in the DB with the provided information.</li>
     *     <li>Should throw an exception if the update is unsuccessful.</li>
     *     <li>Should return a Reimbursement object with updated information.</li>
     * </ul>
     */
    @Override
    public Reimbursement update(Reimbursement unprocessedReimbursement, Integer statusId, Integer resolverId)
    {
        Reimbursement updatedReimbursement = null;
        try (Connection conn = connect.getConnection())
        {
            String sql = "update ers_reimbursement set REIMB_ID = ?, reimb_amount = ?, reimb_resolved = now(), reimb_resolver = ?, reimb_status_id = ? where reimb_id = ?;";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1,unprocessedReimbursement.getId());
            ps.setDouble(2,unprocessedReimbursement.getAmount());
            ps.setInt(3,resolverId);
            ps.setInt(4,statusId);
            ps.setInt(5,unprocessedReimbursement.getId());

            ps.executeUpdate();

            updatedReimbursement = getById(unprocessedReimbursement.getId());

            if (updatedReimbursement == unprocessedReimbursement)
            {
                throw new ReimbursementDidntUpdateException("Update didnt work");
            }
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return updatedReimbursement;
    }
    @Override
    public List<Reimbursement> getAllReimbursementsGivenUser(Integer userId)
    {
        List<Reimbursement> list = new ArrayList<>();
        Reimbursement reimbursement = null;
        try (Connection conn = connect.getConnection())
        {
            String sql = "select r.REIMB_ID, r.reimb_amount, to_char(reimb_submitted::timestamp, 'DD Mon YYYY HH:MIPM'), to_char(reimb_resolved::timestamp, 'DD Mon YYYY HH:MIPM'), r.reimb_description, r.reimb_receipt, r.reimb_author, r.reimb_resolver, s.reimb_status, rt.reimb_type from ERS_REIMBURSEMENT r\n" +
                    "inner join ERS_REIMBURSEMENT_STATUS s on r.REIMB_STATUS_ID = s.REIMB_STATUS_ID\n" +
                    "inner join ers_reimbursement_type rt on r.reimb_type_id = rt.reimb_type_id where r.reimb_author = ?;";
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1,userId);

            ResultSet rs = ps.executeQuery();

            while(rs.next())
            {
                reimbursement = new Reimbursement(rs.getInt(1), rs.getDouble(2), rs.getString(3), rs.getString(4), rs.getString(5), rs.getBinaryStream(6),rs.getInt(7),rs.getInt(8), Status.valueOf(rs.getString(9).toUpperCase(Locale.ROOT)), Type.valueOf(rs.getString(10).toUpperCase(Locale.ROOT)));
                list.add(reimbursement);
            }
        }
        catch(SQLException sqle)
        {
            sqle.printStackTrace();
        }
        return list;
    }
    @Override
    public void createReimbursment(Reimbursement reimbursement)
    {
        try(Connection conn = connect.getConnection())
        {
            String sql = "insert into ERS_REIMBURSEMENT (REIMB_AMOUNT, reimb_submitted, REIMB_DESCRIPTION, reimb_author, reimb_status_id, reimb_type_id) values (?, now(), ?, ?, ?, ?);";

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setDouble(1,reimbursement.getAmount());
            ps.setString(2,reimbursement.getDescription());
            ps.setInt(3,reimbursement.getAuthorId());
            ps.setInt(4,reimbursement.getStatus().ordinal()+1);
            ps.setInt(5,reimbursement.getType().ordinal()+1);

            ps.executeUpdate();
        }
        catch (SQLException sqle)
        {
            sqle.printStackTrace();
        }
    }
}
