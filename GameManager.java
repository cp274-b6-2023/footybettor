import java.util.Scanner;

public class GameManager {

    void login(User user){

    }

    void signUp(User user){

    }
    void displayStat(){

    }
    void startBet(){
        int team1;
        int team2;
        System.out.println("Choose the two team you want to bet:");
        System.out.println("Choose team 1 :");
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

    void showBalance(){

    }

}
