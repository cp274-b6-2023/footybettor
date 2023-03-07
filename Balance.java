public class Balance {
    private int balanceAmount;
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
