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
    public void displayStat() throws IOException {
        CreateTeamData.addTeamStatFromFile("src/pastSeason.txt");
        for(int i = 0; i< CreateTeamData.teamList.size(); i ++){
            System.out.println(CreateTeamData.teamList.get(i).toString());
        }
    }
    public void startBet() throws IOException {
        int team1 = 0;
        int team2 = 0;
        CreateTeamData.addTeamStatFromFile("src/pastSeason.txt");
        CreateFixture.addFixtureFromFile("src/pySoccer.txt");
        System.out.println("Choose a game you want to bet:");
        for(int i = 0; i < CreateFixture.fixtureList.size(); i ++){
            int num = i +1;
            System.out.println( num + ". home team:"+ CreateFixture.fixtureList.get(i).homeTeam + " away team:" +CreateFixture.fixtureList.get(i).awayTeam);
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
        for(int i = 0; i < odd.findOdd(userchoice).length; i ++){
            System.out.println(odd.findOdd(userchoice)[i]);
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
