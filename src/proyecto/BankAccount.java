package proyecto;


public class BankAccount {
    
    private String accountNumber;
    private double balance;
    private String accountType;
    private String documentClient;

    public BankAccount(String accountNumber, double balance, String accountType, String documentClient) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.accountType = accountType;
        this.documentClient = documentClient;
    }

    /**
     * @return the accountNumber
     */
    public String getAccountNumber() {
        return accountNumber;
    }

    /**
     * @param accountNumber the accountNumber to set
     */
    public void setAccountNumber(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    /**
     * @return the balance
     */
    public double getBalance() {
        return balance;
    }

    /**
     * @param balance the balance to set
     */
    public void setBalance(double balance) {
        this.balance = balance;
    }

    /**
     * @return the accountType
     */
    public String getAccountType() {
        return accountType;
    }

    /**
     * @param accountType the accountType to set
     */
    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }
    
    public void deposit(double amount) {
        balance += amount;
    }

    /**
     * @return the documentClient
     */
    public String getDocumentClient() {
        return documentClient;
    }

    /**
     * @param documentClient the documentClient to set
     */
    public void setDocumentClient(String documentClient) {
        this.documentClient = documentClient;
    }
    
    public boolean validateWithdrawMoney(double amount){
        if(amount == 0){
            System.out.println("EL RETIRO NO PUEDE SER 0");
            return false;
        }
        if(accountType.equals("AHORRO") && ((balance - amount) < 0)){
            System.out.println("EL RETIRO NO PUEDE SUPERAR AL SALDO - CUENTA AHORRO.");
            return false;
        }        
        if(accountType.equals("CORRIENTE") && ((balance - amount) < -500.00 )){
            System.out.println("EL RETIRO ESTA GENERANDO UN SOBRESALDO  - CUENTA CORRIENTE.");
            return false;
        }        
        return true;
    }
}
