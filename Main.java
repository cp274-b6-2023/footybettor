import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {

        CreateTeamData.addTeamStatFromFile("/Users/sham/Desktop/CP274/code/implementation v1/out/production/implementation v1/pastSeason.txt");
        CreateTeamData.displayTeamList();

    }
}