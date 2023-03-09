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
                User user = new User(userName, password, Float.parseFloat(last));
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
    public void displayStat() throws IOException {
        CreateTeamData.addTeamStatFromFile("src/pastSeason.txt");
        for(int i = 0; i< CreateTeamData.teamList.size(); i ++){
            System.out.println(CreateTeamData.teamList.get(i).toString());
        }
    }
    public void startBet(User user) throws IOException {
        int team1 = 0;
        int team2 = 0;
        int num = 0;
        CreateTeamData.addTeamStatFromFile("src/pastSeason.txt");
        CreateFixture.addFixtureFromFile("src/pySoccer.txt");
        System.out.println("Choose a game you want to bet:");
        for(int i = 0; i < CreateFixture.fixtureList.size(); i ++){
            num = i +1;
            System.out.println( num + ". home team:"+ CreateFixture.fixtureList.get(i).homeTeam + " | away team:" +CreateFixture.fixtureList.get(i).awayTeam);
        }
        System.out.println("Type game number:");
        Scanner teamchoice1 = new Scanner(System.in);
        String choice1 = teamchoice1.nextLine();
        int choice_final = Integer.parseInt(choice1);
        String team1choice = CreateFixture.fixtureList.get(choice_final).homeTeam;
        String team2choice = CreateFixture.fixtureList.get(choice_final).awayTeam;
        for(int j = 0; j < CreateTeamData.teamList.size(); j ++){
            if(team1choice.equals(CreateTeamData.teamList.get(j).teamName)){
                team1 = j;
            }
        }
        for(int j = 0; j < CreateTeamData.teamList.size(); j ++){
            if(team2choice.equals(CreateTeamData.teamList.get(j).teamName)){
                team2 = j;
            }
        }
        TeamChoice userchoice = new TeamChoice(team1,team2);
        oddCalc odd = new oddCalc();
        odd.findOdd(userchoice);

        System.out.println("Choose a result:");
        System.out.println("1. "+ CreateTeamData.teamList.get(team1).teamName + " win. Odds: " + odd.findOdd(userchoice)[0]);
        System.out.println("2. "+ CreateTeamData.teamList.get(team2).teamName + " win. Odds: " + odd.findOdd(userchoice)[1]);
        System.out.println("3. This is a draw. Odds:" + odd.findOdd(userchoice)[2]);
        System.out.println("Type result number:");
        Scanner resultchoice = new Scanner(System.in);
        String result = resultchoice.nextLine();
        int userresult = Integer.parseInt(result);
        int actual_result = 0;
        String actualresult = CreateFixture.fixtureList.get(choice_final).winner;
        if(actualresult.equals("H")){
            actual_result = 1;
        }else if(actualresult.equals("A")){
            actual_result = 2;
        }else if(actualresult.equals("D")){
            actual_result = 3;
        }else{

        }
        while(true) {
            System.out.println("Wager:");
            Scanner wagerchoice = new Scanner(System.in);
            String wager = resultchoice.nextLine();
            float userwager = Float.parseFloat(wager);
            if (user.balance.loadBalance() > userwager) {
                BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));

                System.out.println("Result:");
                System.out.println("You won:" + betcalc.calcReturn());
                user.addBalance(betcalc.calcReturn());
                break;
            } else {
                System.out.println("No enough money");
                System.out.println("Do you want to recharge or return to the previous window?");
                System.out.println("1.recharge");
                System.out.println("2.return");
                System.out.println("Type a option number:");
                Scanner optionchoice = new Scanner(System.in);
                String option = optionchoice.nextLine();
                int useroption = Integer.parseInt(option);
                if (useroption == 1) {
                    System.out.println("Enter the amount of money to add:");
                    Scanner in2 = new Scanner(System.in);
                    float money = in2.nextInt();
                    user.addBalance(money);
                } else if (useroption == 2){
                }
            }
        }
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
                float money = in2.nextInt();
                user2.addBalance(money);
            } else if (userChoice2.equals("N")){
                break;
            } else {
                System.out.println("Invalid choice.");
            }
        }
    }

}
