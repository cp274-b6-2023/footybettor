public class Balance {
    private int balanceAmount;
    public Balance(int balance){
        this.balanceAmount = balance;
    }
    public int loadBalance(){
        return balanceAmount;
    }
    public void changeBalance(int add){
        balanceAmount += add;

    }
}