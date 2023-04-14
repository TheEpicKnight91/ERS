package com.revature.controllers;

import com.revature.models.*;
import com.revature.services.ReimbursementService;
import io.javalin.http.Context;

import java.util.List;
import java.util.Locale;


public class ReimbursementController
{
    private ReimbursementService reimbursementService;

    public ReimbursementController()
    {
        reimbursementService = new ReimbursementService();
    }

    public ReimbursementController(ReimbursementService reimbursementService)
    {
        this.reimbursementService = reimbursementService;
    }

    public void getReimbursmentById(Context context)
    {
        Integer reimId = Integer.parseInt(context.pathParam("reimId"));
        Reimbursement reimbursement;

        reimbursement = reimbursementService.getReimbursementsById(reimId);

        context.json(new JsonResponse(true,"Getting reimbursement by Id: " + reimId,reimbursement));
    }

    public void getAllReimbursements(Context context)
    {
        Role role = Role.valueOf(context.queryParam("role").toUpperCase(Locale.ROOT));
        List<Reimbursement> reimbursementList;

        if (role == Role.EMPLOYEE)
        {
            Integer userId = Integer.parseInt(context.queryParam("userId"));

            reimbursementList = reimbursementService.getAllReimbursmentsAsEmploee(userId);

            context.json(new JsonResponse(true,"Showing all Reimbursement request for user Id: " + userId, reimbursementList));
        }
        else
        {
            String status = context.queryParam("status").toUpperCase(Locale.ROOT);
            if (status.equals("STATUS"))
            {
                reimbursementList = reimbursementService.getAllReimbursementsAsManager();
            }
            else
            {
                reimbursementList = reimbursementService.getReimbursementsByStatus(Status.valueOf(status));
            }

            context.json(new JsonResponse(true,"Showing all Reimbursements: ", reimbursementList));
        }
    }

    public void createReimbursement(Context context)
    {
        Reimbursement reimbursement = context.bodyAsClass(Reimbursement.class);

        reimbursementService.createReimbursment(reimbursement);

        context.json(new JsonResponse(true, "Reimbursement Created", null));
    }

    public void updateReimbursement(Context context)
    {
        Integer reimId = Integer.parseInt(context.pathParam("reimId"));
        Status status = Status.valueOf(context.pathParam("status").toUpperCase(Locale.ROOT));
        Integer userId = Integer.parseInt(context.pathParam("userId"));

        reimbursementService.process(reimId,status,userId);

        context.json(new JsonResponse(true, "Updated Reimbursement: " + reimId.toString(), null));
    }
}
