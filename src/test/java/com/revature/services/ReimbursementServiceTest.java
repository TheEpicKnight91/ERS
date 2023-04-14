package com.revature.services;

import java.util.ArrayList;
import java.util.List;

import com.revature.models.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.mockito.Mockito;

import com.revature.repositories.ReimbursementDAOImple;

public class ReimbursementServiceTest
{
    private ReimbursementService reimbursementService;

    private ReimbursementDAOImple reimbursementDAOImple = Mockito.mock(ReimbursementDAOImple.class);

    public ReimbursementServiceTest()
    {
        this.reimbursementService = new ReimbursementService(reimbursementDAOImple);
    }

    @Test
    void CreatingReimbursement()
    {
        Reimbursement reimbursement =  new Reimbursement(5000.0,"", null,4,Status.PENDING, Type.FOOD);

        reimbursementService.createReimbursment(reimbursement);

        Mockito.verify(reimbursementDAOImple,Mockito.times(1)).createReimbursment(reimbursement);
    }

    @Test
    void getReimbursementByValidId()
    {
        Integer reimId = 2;

        Reimbursement expectedoutput = new Reimbursement();

        Mockito.when(reimbursementDAOImple.getById(reimId)).thenReturn(expectedoutput);

        Reimbursement actualouput;

        actualouput = reimbursementService.getReimbursementsById(reimId);

        Assertions.assertEquals(expectedoutput,actualouput);
    }

    @Test
    void getReimbursementByInvalidId()
    {
        Integer reimId = -1;

        Reimbursement expectedoutput = new Reimbursement();

        Mockito.when(reimbursementDAOImple.getById(reimId)).thenReturn(expectedoutput);

        Reimbursement actualouput;

        actualouput = reimbursementService.getReimbursementsById(reimId);

        Assertions.assertEquals(expectedoutput,actualouput);
    }
    @Test
    void getsListReimbursementsGivenEmployeeId()
    {
        Integer userId = 4;
        List<Reimbursement> expectedOutput = new ArrayList<>();

        Mockito.when(reimbursementDAOImple.getAllReimbursementsGivenUser(userId)).thenReturn(expectedOutput);

        List<Reimbursement> actualOutput;

        actualOutput = reimbursementService.getAllReimbursmentsAsEmploee(userId);

        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void doesntGetsListReimbursementsGivenEmployeeId()
    {
        Integer userId = 15;
        List<Reimbursement> expectedOutput = new ArrayList<>();

        Mockito.when(reimbursementDAOImple.getAllReimbursementsGivenUser(userId)).thenReturn(expectedOutput);

        List<Reimbursement> actualOutput;

        actualOutput = reimbursementService.getAllReimbursmentsAsEmploee(userId);

        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void getsListReimbursementsAsManager()
    {
        List<Reimbursement> expectedOutput = new ArrayList<>();

        Mockito.when(reimbursementDAOImple.getAllReimbursements()).thenReturn(expectedOutput);

        List<Reimbursement> actualOutput;

        actualOutput = reimbursementService.getAllReimbursementsAsManager();

        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void getsListReimbursementsGivenStatus()
    {
        Status status = Status.PENDING;

        List<Reimbursement> expectedOutput = new ArrayList<>();

        Mockito.when(reimbursementDAOImple.getByStatus(status)).thenReturn(expectedOutput);

        List<Reimbursement> actualOutput;

        actualOutput = reimbursementService.getReimbursementsByStatus(status);

        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void processReimbursementGivenValidReimId()
    {
        Integer reimId = 3;
        Status status = Status.APPROVED;
        Integer userId = 9;

        Reimbursement expectedOutput = null;

        Mockito.when(reimbursementDAOImple.update(reimbursementDAOImple.getById(reimId),status.ordinal(),userId)).thenReturn(expectedOutput);

        Reimbursement actualOutput = reimbursementService.process(reimId,status,userId);

        Assertions.assertEquals(expectedOutput,actualOutput);
    }

    @Test
    void processReimbursementGivenInvalidReimId()
    {
        Integer reimId = 10;
        Status status = Status.APPROVED;
        Integer userId = 9;

        Reimbursement expectedOutput = null;

        Mockito.when(reimbursementDAOImple.update(reimbursementDAOImple.getById(reimId),status.ordinal(),userId)).thenReturn(expectedOutput);

        Reimbursement actualOutput = reimbursementService.process(reimId,status,userId);

        Assertions.assertEquals(expectedOutput,actualOutput);
    }
}
