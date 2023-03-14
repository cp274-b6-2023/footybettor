import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBTest {

    static Connection connect = null;

    public static void main(String[] args) throws SQLException, ClassNotFoundException, IOException {
        CreateFixture.makeFixToDBConn();
        //CreateFixture.makeSQLFixtureTable("pySoccer.txt");

        CreateFixture.addSQLToList();
        CreateFixture.showFixtureList();
    }
}
