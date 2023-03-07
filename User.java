import java.io.*;


public class User {
    public String userName;
    public String password;
    public Balance balance;

    public User(){
        balance = new Balance();
    }
/////need to discuss private or not
    protected void createUser(String _username, String _password) throws IOException {
        this.userName = _username;
        this.password = _password;
        this.balance.changeBalance(0);
        ///need to write data
        try {
            FileWriter writer = new FileWriter("FootyBettor/"+ userName+".txt");
            writer.write("Username:" + userName + ",");
            writer.write("\nPassword: "+ password + ",");
            writer.write("\nBalance" + "," + balance.loadBalance() + ",");
            writer.flush();
            writer.close();
        }catch(IOException ex) {
            System.out.println("Error.");
        }
    }
    /////need to discuss private or not
    protected boolean verifyUser(String _username, String _password){
        this.userName = _username;
        this.password = _password;
        try {
            BufferedReader readTxt = new BufferedReader(new FileReader("FootyBettor/"+userName+".txt"));
            String str = "";
            String text = "";
            while ((text = readTxt.readLine()) != null){
                str += text;
            }
            String[] array = str.split(",");
            String last = array[array.length-1];
            this.balance.changeBalance(Integer.parseInt(last));
            String pwinput = "Password: " + password;
            if(array[1].equals(pwinput)){
                return true;
            } else {
                return false;
            }
        } catch (IOException ext){
            if (ext instanceof FileNotFoundException){
                System.out.println("Account does not exist.");
                ext.printStackTrace();
            } else {
                System.err.println("Exception " + ext);
            }
        }
        return false;
    }
    protected int addBalance(int money){
        balance.changeBalance(money);
        try {
            FileWriter writer = new FileWriter("FootyBettor/"+userName+".txt");
            writer.write("Username:" + userName + ",");
            writer.write("\nPassword: "+ password + ",");
            writer.write("\nBalance" + "," + balance.loadBalance() + ",");
            writer.flush();
            writer.close();

        }catch(IOException ex) {
            System.out.println("Error.");
            ex.printStackTrace();
        }
        return balance.loadBalance();
    }

}
