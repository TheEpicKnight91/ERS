package com.revature.exceptions;

public class ReimbursementDoesntExistException extends RuntimeException
{
    public ReimbursementDoesntExistException() {
        super();
    }

    public ReimbursementDoesntExistException(String message) {super(message);}
}
