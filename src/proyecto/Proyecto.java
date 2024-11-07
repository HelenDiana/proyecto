package proyecto;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;


public class Proyecto {

    /**
     * @param args the command line arguments
     */

    private static List<Client> clients = new ArrayList<>();
    private static List<BankAccount> bankAccounts = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    
    public static void main(String[] args) {
        // TODO code application logic here
        int choice;
        do {
            System.out.println("\n--- SISTEMA MENÚ ---");
            System.out.println("1. REGISTRAR CLIENTE");
            System.out.println("2. ABRIR CUENTA BANCARIA");
            System.out.println("3. DEPOSITAR DINERO");
            System.out.println("4. RETIRAR DINERO");
            System.out.println("5. CONSULTAR SALDO");
            System.out.println("0. SALIR");
            System.out.print("SELECCIONE UNA OPCIÓN: ");
            choice = scanner.nextInt();
            scanner.nextLine();

             switch (choice) {
                case 1:
                    registerClient();
                    break;
                case 2:
                    openBankAccount();
                    break;
                case 3:
                    depositMoney();
                    break;
                case 4:
                    withdrawMoney();
                    break;
                case 5:
                    checkBalance();
                    break;
                case 6:
                    System.out.println("TERMINAR PROGRAMA.");
                    return;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        } while (choice != 0);
    }
    
    private static void registerClient() {
        System.out.print("INGRESE NOMBRE: ");
        String firstName = scanner.nextLine();
        System.out.print("INGRESE APELLIDO: ");
        String lastName = scanner.nextLine();
        System.out.print("INGRESE DOCUMENTO: ");
        String document = scanner.nextLine();
        System.out.print("INGRESE EMAIL: ");
        String email = scanner.nextLine();
        
        if(!validateDataClient(firstName, lastName, document, email)){
            return;
        }
        
        Boolean clientExist = validateDocument(document);
        
        if (clientExist) {
            System.out.println("CLIENTE YA SE ENCUENTRA REGISTRADO.");
            return;
        }
        
        Client client = new Client(firstName, lastName, document, email);
        clients.add(client);
        System.out.println("CLIENTE REGISTRADO CORRECTAMENTE");
    }
    
    private static void openBankAccount() {
        System.out.print("INGRESE DOCUMENTO: ");
        String document = scanner.nextLine();
        Boolean clientExist = validateDocument(document);
        
        if (!clientExist) {
            System.out.println("CLIENTE NO ENCONTRADO.");
            return;
        }

        System.out.print("INGRESE EL TIPO DE CUENTA (AHORRO Ó CORRIENTE): ");
        String accountTypeInput = scanner.nextLine().toUpperCase();
        
        System.out.println(accountTypeInput);
        if(!accountTypeInput.equals("AHORRO") && !accountTypeInput.equals("CORRIENTE")){
            System.out.println("TIPO DE CUENTA NO VALIDO.");
            return;
        }
        
        String numberAccount = "ACC" + (int)(Math.random() * 10000);
        BankAccount bankAccount = new BankAccount(numberAccount, 0.00, accountTypeInput, document);
        bankAccounts.add(bankAccount);
        System.out.println("CUENTA DE BANCO ABIERTA CORRECTAMENTE - SU NUMERO DE CUENTA ES : " + numberAccount);
    }
    
    private static void depositMoney() {
        System.out.print("INGRESE DOCUMENTO: ");
        String document = scanner.nextLine();
        Boolean clientExist = validateDocument(document);
        
        if (!clientExist) {
            System.out.println("CLIENTE NO ENCONTRADO.");
            return;
        }

        System.out.print("INGRESE NÚMERO DE CUENTA: ");
        String accountNumber = scanner.nextLine();        
        Boolean accountExist = validateAccountAndDocument(accountNumber,document);
        
        if (!accountExist) {
            System.out.println("CUENTA NO ENCONTRADA.");
            return;
        }

        System.out.print("INGRESE EL MONTO A DEPOSITAR: ");
        double amount = scanner.nextDouble();
        bankAccounts.stream()
            .forEach((account)-> {
                if(account.getAccountNumber().equals(accountNumber)){
                    account.setBalance(account.getBalance() + amount);
                    System.out.println("SU SALDO ACTUAL ES : " + account.getBalance());
                }
            });
        System.out.println("DINERO DEPOSITADO CORRECTAMENTE.");
    }
    
    private static void withdrawMoney() {
        System.out.print("INGRESE DOCUMENTO: ");
        String document = scanner.nextLine();
        Boolean clientExist = validateDocument(document);
        
        if (!clientExist) {
            System.out.println("CLIENTE NO ENCONTRADO.");
            return;
        }

        System.out.print("INGRESE NÚMERO DE CUENTA: ");
        String accountNumber = scanner.nextLine();        
        Boolean accountExist = validateAccountAndDocument(accountNumber,document);
        
        if (!accountExist) {
            System.out.println("CUENTA NO ENCONTRADA.");
            return;
        }

        System.out.print("INGRESE EL MONTO A RETIRAR : ");
        double amount = scanner.nextDouble();
        bankAccounts.stream()
            .forEach((account)-> {
                if(account.getAccountNumber().equals(accountNumber)){
                    if(account.validateWithdrawMoney(amount)){
                        account.setBalance(account.getBalance() - amount);
                        System.out.println("SU SALDO ACTUAL ES : " + account.getBalance());
                        System.out.println("DINERO RETIRADO CORRECTAMENTE.");
                    }
                }
            });
        
    }
    
     private static void checkBalance() {
        System.out.print("INGRESE DOCUMENTO: ");
        String document = scanner.nextLine();
        Boolean clientExist = validateDocument(document);
        
        if (!clientExist) {
            System.out.println("CLIENTE NO ENCONTRADO.");
            return;
        }

        System.out.print("INGRESE NÚMERO DE CUENTA: ");
        String accountNumber = scanner.nextLine();        
        Boolean accountExist = validateAccountAndDocument(accountNumber,document);
        
        if (!accountExist) {
            System.out.println("CUENTA NO ENCONTRADA.");
            return;
        }

        bankAccounts.stream()
        .forEach((account)-> {
            if(account.getAccountNumber().equals(accountNumber)){
                System.out.println("SALDO : " + account.getBalance());
            }
        });
    }
     
    private static boolean validateDocument(String document){
        return clients.stream().anyMatch(client -> client.getDocument().equals(document));
    }
    
    private static boolean validateAccountAndDocument(String accountNumber, String document){
        return bankAccounts.stream().anyMatch(
            bankAccount -> bankAccount.getAccountNumber().equals(accountNumber) && 
            bankAccount.getDocumentClient().equals(document)
        );
    }
    
    public static boolean validateEmail(String email) {
        String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
        Pattern pattern = Pattern.compile(EMAIL_REGEX);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }
    
     public static boolean validateDataClient(String firstName, String lastName, String document, String email) {
        if (firstName.isEmpty()) {
            System.out.println("El nombre es requerido.");
            return false;
        }
        if (lastName.isEmpty()) {
            System.out.println("El apellido es requerido.");
            return false;
        }
        if (document.isEmpty()) {
            System.out.println("El documento es requerido.");
            return false;
        }
        if (email.isEmpty()) {
            System.out.println("El correo electrónico es requerido.");
            return false;
        }
        if(!validateEmail(email)){
            System.out.println("El correo electrónico no es valido.");
            return false;
        }
        return true;
    }
}
