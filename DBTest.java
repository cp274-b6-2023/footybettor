import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {

    //private static Connection connect = null;


    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
//        CreateFixture.makeFixToDBConn();
//        //CreateFixture.makeSQLFixtureTable("pySoccer.txt");
//
//        CreateFixture.addSQLToFixtureList();
//        CreateFixture.showFixtureList();


        CreateTeamData.makeTeamStatToDBConn();
        //CreateTeamData.makeSQLTeamStatTable("pastSeason.txt");

        CreateTeamData.addSQLToTeamList();
        //CreateTeamData.showTeamStatFromSQL();
        CreateTeamData.showTeamList();
    }
}
