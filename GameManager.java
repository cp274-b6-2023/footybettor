import java.io.*;
import java.util.Scanner;

public class GameManager {

    public User login(){
        Scanner scanner = new Scanner(System.in);
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter your username:");
        String userName = scanner.next();
        File acc2 = new File("FootyBettor/"+userName+".txt");
        System.out.println("Enter your password:");
        String password = scanner.next();
        if(acc2.exists() == true) {
            try {
                BufferedReader readTxt = new BufferedReader(new FileReader("FootyBettor/" + userName + ".txt"));
                String str = "";
                String text = "";
                while ((text = readTxt.readLine()) != null) {
                    str += text;
                }
                String[] array = str.split(",");
                String last = array[array.length - 1];
                User user = new User(userName, password, Integer.parseInt(last));
                String pwinput = "Password: " + password;
                if (array[1].equals(pwinput)) {
                    return user;
                } else {
                    return new User("", "", -10);
                }
            } catch (IOException ext) {
                if (ext instanceof FileNotFoundException) {
                    System.out.println("Account does not exist.");
                    ext.printStackTrace();
                } else {
                    System.err.println("Exception " + ext);
                }
            }
        }else {
            return new User("", "", -10);
        }
        return new User("", "", -10);
    }

    public void signUp() throws IOException {
        System.out.println("Enter your username:");
        Scanner scanner = new Scanner(System.in);
        String inputUsername = scanner.next();
        String fileName = "FootyBettor/" + inputUsername + ".txt";
        File acc = new File(fileName);
        if(!acc.exists()){
            System.out.println("Enter your password:");
            String inputPassword = scanner.next();
            User user = new User(inputUsername,inputPassword,0);
            try {
                FileWriter writer = new FileWriter("FootyBettor/"+ user.userName+".txt");
                writer.write("Username:" + user.userName + ",");
                writer.write("\nPassword: "+ user.password + ",");
                writer.write("\nBalance" + "," + user.balance.loadBalance() + ",");
                writer.flush();
                writer.close();
            }catch(IOException ex) {
                System.out.println("Error.signUp");
            }
            System.out.println("Account Created Successfully!");
        } else {
            System.out.println("Account already exists.");
        }
    }
    void displayStat(){

    }
    public void startBet(){
        int team1;
        int team2;
        System.out.println("Choose a game you want to bet:");
        for(int i = 0; i < CreateTeamData.teamList.size(); i ++){
            int num = i +1;
            System.out.println( num + ". "+ CreateTeamData.teamList.get(i).getTeamName());
        }
        System.out.println("Type team number:");
        Scanner teamchoice1 = new Scanner(System.in);
        String choice1 = teamchoice1.nextLine();
        team1 = Integer.parseInt(choice1);
        System.out.println("Choose team 2 :");
        for(int i = 0; i < CreateTeamData.teamList.size(); i ++){
            System.out.println(i + ". "+ CreateTeamData.teamList.get(i).getTeamName());
        }
        Scanner teamchoice2 = new Scanner(System.in);
        String choice2 = teamchoice2.nextLine();
        team2 = Integer.parseInt(choice2);
        TeamChoice userchoice = new TeamChoice(team1,team2);
    }

    public void showBalance(User user2){
        while(true){
            System.out.println("Current Balance: $" + user2.balance.loadBalance());
            System.out.println("Do you want to add money?");
            System.out.println("Enter Y for Yes or N or No");
            Scanner in = new Scanner(System.in);
            Scanner in2 = new Scanner(System.in);
            String userChoice2 = in.nextLine();
            if (userChoice2.equals("Y")){
                System.out.println("Enter the amount of money to add:");
                int money = in2.nextInt();
                user2.addBalance(money);
            } else if (userChoice2.equals("N")){
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

}
