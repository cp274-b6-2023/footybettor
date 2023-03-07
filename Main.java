import java.io.*;
import java.util.Scanner;

public class Main {
    private static String foldername;
    private static String filename;

    public static boolean createfolder(String path) {
        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }else {
                return false;
            }
        }catch(Exception e) {
        }return false;
    }

    public static void main(String[] args) throws IOException {
        foldername = "FootyBettor";
        if(createfolder(foldername)){
            System.out.println("Folder created!");
        } else {
            System.out.println("Folder exists.");
        }
        while(true){
            System.out.println("\n--------Welcome the Sport Betting Platform---------\n");
            System.out.println("1) Sign up");
            System.out.println("2) Log in");
            System.out.println("3) Quit");
            System.out.println("Please type 1, 2, or 3 to enter your choice:");
            Scanner scanner = new Scanner(System.in);
            int choice = scanner.nextInt();
            if (choice == 1){
                System.out.println("Enter your username:");
                String inputUsername = scanner.next();
                User user = new User();
                String fileName = "FootyBettor/" + inputUsername + ".txt";
                File acc = new File(fileName);
                if(!acc.exists()){
                    System.out.println("Enter your password:");
                    String inputPassword = scanner.next();
                    user.createUser(inputUsername, inputPassword);
                    System.out.println("Account Created Successfully!");
                } else {
                    System.out.println("Account already exists.");
                }
            } else if (choice == 2){
                Scanner sc = new Scanner(System.in);
                System.out.println("Enter your username:");
                String acc_name = scanner.next();
                File acc2 = new File("FootyBettor/"+acc_name+".txt");
                User user2 = new User();
                System.out.println("Enter your password:");
                String acc_pw = scanner.next();
                boolean flag = user2.verifyUser(acc_name,acc_pw);
                if(flag == true) {
                    System.out.println("Successfully Log in!");
                    Boolean quit = true;
                    while (quit) {
                        System.out.println("----------- Menu -----------");
                        System.out.println("1. Check Team Stats");
                        System.out.println("2. Start to bet");
                        System.out.println("3. Check balance/Deposit");
                        System.out.println("4. Back to Log in Page");
                        System.out.println("Type 1, 2, 3, or 4 to enter your choice:");
                        Scanner in = new Scanner(System.in);
                        String userChoice = in.nextLine();
                        if (userChoice.equals("1")) {

                        } else if (userChoice.equals("2")) {

                        } else if (userChoice.equals("3")) {
                            
                            while(true){
                                System.out.println("Current Balance: $" + user2.balance.loadBalance());
                                System.out.println("Do you want to add money?");
                                System.out.println("Enter Y for Yes or N or No");
                                Scanner in2 = new Scanner(System.in);
                                String userChoice2 = in.nextLine();
                                if (userChoice2.equals("Y")){
                                    System.out.println("Enter the amount of money to add:");
                                    int money = in2.nextInt();
                                    user2.addBalance(money);
                                    System.out.println("$" + money + " has been deposited.");
                                    
                                } else if (userChoice2.equals("N")){
                                    break;
                                } else {
                                    System.out.println("Invalid choice.");
                                }
                            }
                        } else if (userChoice.equals("4")) {
                            quit = false;
                            break;
                        } else {
                            System.out.println("Invalid input.");
                        }

                    }
                } else {
                    System.out.println("Invalid password. Failed to log in.");
                }
            } else if (choice == 3) {
                System.out.println("Thank you for using our services.");
                break;
            } else {
                System.out.println("Invalid choice. Please try again.");
            }
        }

    }
}
