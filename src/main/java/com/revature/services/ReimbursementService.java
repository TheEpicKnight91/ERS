package com.revature.services;

import com.revature.models.Reimbursement;
import com.revature.models.Status;
import com.revature.repositories.ReimbursementDAOImple;
import com.revature.repositories.UserDAOImple;

import java.util.List;

/**
 * The ReimbursementService should handle the submission, processing,
 * and retrieval of Reimbursements for the ERS application.
 *
 * {@code process} and {@code getReimbursementsByStatus} are the minimum methods required;
 * however, additional methods can be added.
 *
 * Examples:
 * <ul>
 *     <li>Create Reimbursement</li>
 *     <li>Update Reimbursement</li>
 *     <li>Get Reimbursements by ID</li>
 *     <li>Get Reimbursements by Author</li>
 *     <li>Get Reimbursements by Resolver</li>
 *     <li>Get All Reimbursements</li>
 * </ul>
 */
public class ReimbursementService {

    private ReimbursementDAOImple reimbursementDAOImple;
    private UserDAOImple userDAOImple;
    /**
     * <ul>
     *     <li>Should ensure that the user is logged in as a Finance Manager</li>
     *     <li>Must throw exception if user is not logged in as a Finance Manager</li>
     *     <li>Should ensure that the reimbursement request exists</li>
     *     <li>Must throw exception if the reimbursement request is not found</li>
     *     <li>Should persist the updated reimbursement status with resolver information</li>
     *     <li>Must throw exception if persistence is unsuccessful</li>
     * </ul>
     *
     * Note: unprocessedReimbursement will have a status of PENDING, a non-zero ID and amount, and a non-null Author.
     * The Resolver should be null. Additional fields may be null.
     * After processing, the reimbursement will have its status changed to either APPROVED or DENIED.
     */
    public ReimbursementService()
    {
        this.reimbursementDAOImple = new ReimbursementDAOImple();
        this.userDAOImple = new UserDAOImple();
    }

    public ReimbursementService(ReimbursementDAOImple reimbursementDAOImple)
    {
        this.reimbursementDAOImple = reimbursementDAOImple;
        this.userDAOImple = new UserDAOImple();
    }

    public Reimbursement process(Integer reimId, Status finalStatus, Integer userId)
    {
        Reimbursement processedreim = reimbursementDAOImple.getById(reimId);
        return reimbursementDAOImple.update(processedreim, finalStatus.ordinal()+1, userId);
    }

    public Reimbursement getReimbursementsById(Integer Id)
    {
        Reimbursement returnreim = reimbursementDAOImple.getById(Id);
        if (returnreim != null)
        {
            returnreim.setAuthor(userDAOImple.getByUserId(returnreim.getAuthorId()));
            returnreim.setResolver(userDAOImple.getByUserId(returnreim.getResolverId()));
        }
        return returnreim;
    }
    /**
     * Should retrieve all reimbursements with the correct status.
     */
    public List<Reimbursement> getReimbursementsByStatus(Status status)
    {
        return reimbursementDAOImple.getByStatus(status);
    }

    public List<Reimbursement> getAllReimbursementsAsManager()
    {
        return reimbursementDAOImple.getAllReimbursements();
    }

    public List<Reimbursement> getAllReimbursmentsAsEmploee(Integer employeeId)
    {
        return reimbursementDAOImple.getAllReimbursementsGivenUser(employeeId);
    }

    public void createReimbursment(Reimbursement reimbursement)
    {
        this.reimbursementDAOImple.createReimbursment(reimbursement);
    }
}
