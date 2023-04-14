package com.revature.exceptions;

public class ReimbursementDidntUpdateException extends RuntimeException
{
    public ReimbursementDidntUpdateException() {super();}

    public ReimbursementDidntUpdateException(String message) {super(message);};
}
