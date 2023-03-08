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
            int userReturn = -wager;

            //or 0 depending on how we want to calculate the change in balance
            // ie if the wager is automatically taken out of account when the user bets vs not

            return userReturn;
        }
    }

}
