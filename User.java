import java.io.*;

public class User {
    public String userName;
    public String password;
    public Balance balance;

    public User(String userName, String password, int balance){
        this.userName = userName;
        this.password = password;
        this.balance = new Balance(balance);
    }

    protected int addBalance(int money){
        balance.changeBalance(money);
        try {
            FileWriter writer = new FileWriter("FootyBettor/"+userName+".txt");
            writer.write("Username:" + userName + ",");
            writer.write("\nPassword: "+ password + ",");
            writer.write("\nBalance" + "," + balance.loadBalance() + ",");
            writer.flush();
            writer.close();

        }catch(IOException ex) {
            System.out.println("Error.");
            ex.printStackTrace();
        }
        return balance.loadBalance();
    }


}