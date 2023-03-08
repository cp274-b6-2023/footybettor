import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;


public class GameManager {

    void displayStat(){

    }
    public void startBet(){

    }


    public void showBalance(User user){
        JFrame testFrame = new JFrame("Balance");
        testFrame.setSize(400, 100);
        testFrame.setResizable(false);
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        testFrame.setLocationRelativeTo(null);
        testFrame.setLayout(new FlowLayout());
        JLabel show_balance = new JLabel("Current Balance: $"+user.balance.loadBalance());
        testFrame.add(show_balance);

        JButton enter = new JButton("Enter");
        JLabel text = new JLabel("Enter amount of money to add: ");
        JTextField textBook = new JTextField(20);
        testFrame.add(text);
        testFrame.add(textBook);
        enter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if ("Enter".equals(command)) {
                    String input = textBook.getText();
                    int money = Integer.parseInt(input);
                    user.addBalance(money);
                    ///new window and show money successfully added
                    testFrame.dispose();
                    JFrame newFrame = new JFrame();
                    newFrame.setSize(400, 150);
                    newFrame.setTitle("Result");
                    newFrame.setResizable(false);
                    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    newFrame.setLayout(new FlowLayout());
                    newFrame.setLocationRelativeTo(null);
                    JLabel label = new JLabel("Money Added Successfully!");
                    newFrame.add(label);
                    JLabel label2 = new JLabel("Current Balance: $"+ user.balance.loadBalance());
                    newFrame.add(label2);
                    newFrame.setVisible(true);
                    JButton back = new JButton("Return");
                    newFrame.add(back);
                    back.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String command2 = e.getActionCommand();
                            if ("Return".equals(command2)){
                                newFrame.dispose();
                            }
                        }
                    });
                }
            }

        });
        testFrame.add(enter);
        testFrame.setVisible(true);

    }

    public void signUp(JTextField textBook, JTextField textBook2, JFrame testFrame) {
        String inputUsername = textBook.getText();
        String inputPassword = textBook2.getText();
        String fileName = "FootyBettor/" + inputUsername + ".txt";
        File acc = new File(fileName);
        if(!acc.exists()){
            User user = new User(inputUsername,inputPassword,0);
            try {
                FileWriter writer = new FileWriter("FootyBettor/"+ user.userName+".txt");
                writer.write("Username:" + user.userName + ",");
                writer.write("\nPassword: "+ user.password + ",");
                writer.write("\nBalance" + "," + user.balance.loadBalance() + ",");
                writer.flush();
                writer.close();
            }catch(IOException ex) {
                System.out.println("Error.signUp");
            }

            testFrame.dispose();
            JFrame newFrame = new JFrame();
            newFrame.setSize(400, 150);
            newFrame.setTitle("Result");
            newFrame.setResizable(false);
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setLayout(new FlowLayout());
            newFrame.setLocationRelativeTo(null);
            JLabel label = new JLabel("Account Created Successfully!");
            newFrame.add(label);
            newFrame.setVisible(true);
            JButton back = new JButton("Return");
            newFrame.add(back);
            back.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command2 = e.getActionCommand();
                    if ("Return".equals(command2)){
                        newFrame.dispose();
                    }
                }
            });
        } else {
            testFrame.dispose();
            JFrame newFrame = new JFrame();
            newFrame.setSize(400, 150);
            newFrame.setTitle("Result");
            newFrame.setResizable(false);
            newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            newFrame.setLayout(new FlowLayout());
            newFrame.setLocationRelativeTo(null);
            JLabel label = new JLabel("Account already exists.");
            newFrame.add(label);
            newFrame.setVisible(true);
            JButton back = new JButton("Return");
            newFrame.add(back);
            back.addActionListener(new AbstractAction() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    String command2 = e.getActionCommand();
                    if ("Return".equals(command2)){
                        newFrame.dispose();
                    }
                }
            });
        }
    }


    public User login(JTextField textBook, JTextField textBook2, JFrame testFrame){
        String inputUsername = textBook.getText();
        String inputPassword = textBook2.getText();
        String fileName = "FootyBettor/" + inputUsername + ".txt";
        File acc = new File(fileName);
        if(acc.exists() == true) {
            try {
                BufferedReader readTxt = new BufferedReader(new FileReader("FootyBettor/" + inputUsername + ".txt"));
                String str = "";
                String text = "";
                while ((text = readTxt.readLine()) != null) {
                    str += text;
                }
                String[] array = str.split(",");
                String last = array[array.length - 1];
                User user = new User(inputUsername, inputPassword, Integer.parseInt(last));
                String pwinput = "Password: " + inputPassword;
                if (array[1].equals(pwinput)) {
                    return user;
                } else {
                    //wrong password
                    return new User("","",-10);
                }
            } catch (IOException ext) {
                if (ext instanceof FileNotFoundException) {
                    System.out.println("Account does not exist.");
                    ext.printStackTrace();
                } else {
                    System.err.println("Exception " + ext);
                }
            }


        }else {
            return new User("","",-20);
        }
        return new User("","",-20);
    }
}
