import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class CreateTeamData {

    //variables
    public static ArrayList<TeamStat> teamList = new ArrayList<>();
    static String file;

    public static void addTeamStatFromFile(String filename) throws IOException {

        file = filename;

        BufferedReader br = new BufferedReader(new FileReader(filename));
        String line = br.readLine();

        while (line != null){
            String[] tLine = line.split(",");
            String tName = tLine[0];
            int gWon = Integer.parseInt(tLine[1]);
            int gLost = Integer.parseInt(tLine[2]);
            int gTied = Integer.parseInt(tLine[3]);;
            TeamStat tStat = new TeamStat(tName, gWon, gLost, gTied);
            addTeamStatToList(tStat);
            line = br.readLine();
        }
        br.close();
    }

    public static void addTeamStatToList(TeamStat teamStat){
        teamList.add(teamStat);
    }

    public static void displayTeamList(){
        for(TeamStat t : teamList){
            System.out.println(t);
        }
    }
}
