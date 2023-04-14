package com.revature.models;

import javax.swing.text.Document;
import java.io.File;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Date;

/**
 * This concrete Reimbursement class can include additional fields that can be used for
 * extended functionality of the ERS application.
 *
 * Example fields:
 * <ul>
 *     <li>Description</li>
 *     <li>Creation Date</li>
 *     <li>Resolution Date</li>
 *     <li>Receipt Image</li>
 * </ul>
 *
 */
public class Reimbursement extends AbstractReimbursement {

    private String creation_Date;
    private String resolution_Date;
    private String description;
    private InputStream receipt_Image;
    private Type type;
    public Reimbursement() {
        super();
    }

    /**
     * This includes the minimum parameters needed for the {@link com.revature.models.AbstractReimbursement} class.
     * If other fields are needed, please create additional constructors.
     */
    public Reimbursement(int id, Status status, Integer authorId, Integer resolverId, double amount, User author, User resolver)
    {
        super(id, status, authorId, resolverId, amount, author, resolver);
    }

    public Reimbursement(int id, double amount, String created, String reolved, String desc, InputStream receipt, Integer authorId, Integer resolverId, Status status, Type type, User author,User resolver)
    {
        super(id,status,authorId,resolverId,amount, author, resolver);
        creation_Date = created;
        resolution_Date = reolved;
        description = desc;
        receipt_Image = receipt;
        this.type = type;
    }

    public Reimbursement(double amount, String desc, InputStream receipt, Integer authorId, Status status, Type type)
    {
        super(amount,authorId,status);
        this.description = desc;
        this.receipt_Image = receipt;
        this.type = type;
    }

    public Reimbursement(int id, double amount, String created, String reolved, String desc, InputStream receipt,Integer authorid,Integer reolverid, Status status, Type type)
    {
        super(id,status,authorid,reolverid,amount);
        creation_Date = created;
        resolution_Date = reolved;
        description = desc;
        receipt_Image = receipt;
        this.type = type;
    }

    public String getCreation_Date()
    {
        return creation_Date;
    }

    public void setCreation_Date(String creation_Date)
    {
        this.creation_Date = creation_Date;
    }

    public String getResolution_Date()
    {
        return resolution_Date;
    }

    public void setResolution_Date(String resolution_Date)
    {
        this.resolution_Date = resolution_Date;
    }

    public String getDescription()
    {
        return description;
    }

    public void setDescription(String description)
    {
        this.description = description;
    }

    public InputStream getReceipt_Image()
    {
        return receipt_Image;
    }

    public void setReceipt_Image(InputStream receipt_Image)
    {
        this.receipt_Image = receipt_Image;
    }

    public Type getType()
    {
        return type;
    }

    public void setType(Type type)
    {
        this.type = type;
    }

    @Override
    public String toString()
    {
        return "Reimbursement{" +
                "creation_Date=" + creation_Date +
                ", resolution_Date=" + resolution_Date +
                ", description='" + description + '\'' +
                ", receipt_Image=" + receipt_Image.toString() +
                '}';
    }
}
