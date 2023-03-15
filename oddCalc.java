public class oddCalc {


    float findWinPercentage(TeamStat team){

        float gameWon = team.getGameWon();

        float winPercentage = (gameWon/38) * 100;

        return winPercentage;

    }

    float findDrawPercentage(TeamStat team){

        float gameDraw = team.getGameTied();

        float drawPercentage = (gameDraw/38) * 100;

        return drawPercentage;
    }

    float findLossPercentage(TeamStat team){

        float gameLoss = team.getGameLost();

        float drawPercentage = (gameLoss/38) * 100;

        return drawPercentage;
    }

    public float[] findOdd(TeamChoice choice){

        TeamStat t1 = choice.t1;
        TeamStat t2 = choice.t2;

        float[] odds = new float[3];

        float oddTeamOneWin = (2*findWinPercentage(t1)+findLossPercentage(t2))/3;
        float oddTeamTwoWin = (1/2*findWinPercentage(t2) + findLossPercentage(t1))/(3/2);
        float oddDraw = 100 - oddTeamOneWin - oddTeamTwoWin;


        odds[0] = oddTeamOneWin;
        odds[1] = oddTeamTwoWin;
        odds[2] = oddDraw;


        return odds;

    }


}
