public class oddCalc {

    //TeamChoice choice;


    int findWinPercentage(TeamStat team){

        int gameWon = team.getGameWon();

        int winPercentage = (gameWon/38) * 100;

        return winPercentage;

    }

    int findDrawPercentage(TeamStat team){

        int gameDraw = team.getGameTied();

        int drawPercentage = (gameDraw/38) * 100;

        return drawPercentage;
    }

    int findLossPercentage(TeamStat team){

        int gameLoss = team.getGameLost();

        int drawPercentage = (gameLoss/38) * 100;

        return drawPercentage;
    }

    public int[] findOdd(TeamChoice choice){

        //look at junhao code for getting a team
        TeamStat t1 = choice.getT1();
        TeamStat t2 = choice.getT2();

        int[] odds = new int[3];

        int oddTeamOneWin = (findWinPercentage(t1)+findLossPercentage(t2))/2;
        int oddTeamTwoWin = (findWinPercentage(t2) + findLossPercentage(t1))/2;
        int oddDraw = (findDrawPercentage(t1)+findDrawPercentage(t2))/2;


       odds[0] = oddTeamOneWin;
       odds[1] = oddTeamTwoWin;
       odds[2] = oddDraw;


       return odds;

    }


}
