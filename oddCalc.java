public class oddCalc {

    //TeamChoice choice;



    int findWinPercentage(TeamStat team){

        gameWon = team.getGameWon();

        int winPercentage = (gameWon/38) * 100;

        return winPercentage;

    }

    int findDrawPercentage(TeamStat team){

        gameDraw = team.getGameDraw();

        int drawPercentage = (gameDraw/38) * 100;

        return drawPercentage;
    }

    int findLossPercentage(TeamStat team){

        gameLoss = team.getGameLoss();

        int drawPercentage = (gameLoss/38) * 100;

        return drawPercentage;
    }

    public int[] findOdd(TeamChoice choice){

        //look at junhao code for getting a team
        TeamStat t1 = choice.getFirstTeam();
        TeamStat t2 = choice.getSecondTeam();

        int[] odds = new int[3];

        oddTeamOneWin = (findWinPercentage(t1)+findLossPercentage(t2))/2;
        oddTeamTwoWin = (findWinPercentage(t2) + findLossPercentage(t1))/2;
        oddDraw = (findDrawPercentage(t1)+findDrawPercentage(t2))/2;


       odds[0] = oddTeamOneWin;
       odds[1] = oddTeamTwoWin;
       odds[2] = oddDraw;


       return odds;

    }

    public static void main(String[] args) {
        // what i want this code to be able to do
    }
}
