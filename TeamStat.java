public class TeamStat {

    //variables
    private String teamName;
    private int gameWon;
    private int gameLost;
    private int gameTied;

    //constructor
    public TeamStat(String name, int won, int lost, int tied){
        this.teamName = name;
        this.gameWon = won;
        this.gameLost = lost;
        this.gameTied = tied;
    }

    //methods
    public String getTeamName(){
        return this.teamName;
    }

    public int getGameWon(){
        return this.gameWon;
    }

    public int getGameLost(){
        return this.gameLost;
    }

    public int getGameTied(){
        return this.gameTied;
    }

    @Override
    public String toString(){
        return(this.getTeamName() + ", game won: " + this.getGameWon() + ", game lost: " + this.getGameLost() + ", game tied: " + this.getGameTied());
    }
}
