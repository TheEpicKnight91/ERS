package com.revature.repositories;

import com.revature.models.Reimbursement;
import com.revature.models.Status;

import java.util.List;

public interface ReimbursementDAO
{
    Reimbursement getById(int id);
    List<Reimbursement> getAllReimbursements();
    List<Reimbursement> getByStatus(Status status);
    List<Reimbursement> getAllReimbursementsGivenUser(Integer userId);
    Reimbursement update(Reimbursement unprocessedReimbursement, Integer statusId, Integer resolverId);
    void createReimbursment(Reimbursement reimbursement);
}
