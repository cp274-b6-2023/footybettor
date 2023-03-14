 import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

public class userDBTest {



    public static void signUptoDB (String username, String password){
        try{

            String insertQuery = "INSERT into COMPANY VALUES (?,?,?)";
            statement = conn.prepareStatement(insertQuery);
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setInt(3, 0);
            statement.executeUpdate();

        }

        catch(SQLException e){

            e.printStackTrace();
        }
    }


// signUp method in GameManger

    public void signUp() throws IOException {
        System.out.println("Enter your username:");
        Scanner scanner = new Scanner(System.in);
        String inputUsername = scanner.next();
        //String fileName = "FootyBettor/" + inputUsername + ".txt";
        //File acc = new File(fileName);
        if(!acc.exists()){
            System.out.println("Enter your password:");
            String inputPassword = scanner.next();
            User user = new User(inputUsername,inputPassword,0);
            try {
//
                signUptoDB(user.userName, user.password);

            }catch(IOException ex) {
                System.out.println("Error.signUp");
            }
            System.out.println("Account Created Successfully!");
        } else {
            System.out.println("Account already exists.");
        }
    }


    public static void adjustBalanceDB (float balance){
        try{

            String insertQuery = "INSERT into COMPANY VALUES (?,?,?)";
            statement = conn.prepareStatement(insertQuery);
            statement.setInt(3, balance);
            statement.executeUpdate();

        }

        catch(SQLException e){

            e.printStackTrace();
        }

    }


    // user class
    public class User {
        public String userName;
        public String password;
        public Balance balance;

        public User(String userName, String password, float balance) {
            this.userName = userName;
            this.password = password;
            this.balance = new Balance(balance);
        }

        protected float addBalance(float money) {
            balance.changeBalance(money);
            try {

                adjustBalanceDB(balance.loadBalance());
//

            } catch (IOException ex) {
                System.out.println("Error.addBalance");
                ex.printStackTrace();
            }
            return balance.loadBalance();
        }

    }
    }

