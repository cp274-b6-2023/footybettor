public class BetCalc {

    // takes in the users prediction, their wager, the odds and the
    // actual result and the odds to calculate their return.

    int prediction ;
    int wager;
    int actualResult;
    int[] odds;

    int calcReturn(prediction, wager, actualResult, odds){

        if (prediction == actualResult){

            int userReturn = wager * (100/odds[prediction-1]);

            return userReturn;
        }

        else {
            int userReturn = wager;

            return userReturn;
        }
    }

}
