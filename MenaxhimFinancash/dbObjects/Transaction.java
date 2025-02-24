package dbObjects;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Transaction {
    private User user;
    private String tsType;
    private double tsAmount;
    private String tsDate;
    private String tsUserToTransfer;
    
    public Transaction(User user, String tsType, double tsAmount) {
        this.user = user;
        this.tsType = tsType;
        this.tsAmount = tsAmount;
        
        SimpleDateFormat dtFormat = new SimpleDateFormat("dd/MM/yy");
        this.tsDate = dtFormat.format(new Date());
    }
    
    public Transaction(User user, String tsType, double tsAmount, String tsDate) {
        this.user = user;
        this.tsType = tsType;
        this.tsAmount = tsAmount;
        this.tsDate = tsDate;
    }
    
    public void setTsUserToTransfer(String tsUserToTransfer) {
        this.tsUserToTransfer = tsUserToTransfer;
    }

    public User getUser() {
        return user;
    }

    public String getTsType() {
        return tsType;
    }
    
    public double getTsAmount() {
        return tsAmount;
    }
    
    public String getTsDate() {
        return tsDate;
    }
    
    public String getTsUserToTransfer() {
        return tsUserToTransfer;
    }
}
