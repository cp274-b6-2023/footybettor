public class Balance {
    public int balanceAmount;
    public Balance(){
        balanceAmount = 0;
    }
    public int loadBalance(){
        return balanceAmount;
    }
    public void changeBalance(int add){
        balanceAmount += add;

    }
}
