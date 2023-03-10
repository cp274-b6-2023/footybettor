import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;


public class GameManager {
    int actual_result = 0;
    void displayStat() throws IOException {
        JFrame Team = new JFrame();
        Team.setTitle("Team Stats");
        Team.setSize(1000, 500);


        Team.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Team.setLayout(new FlowLayout());
        Team.getContentPane().setBackground(Color.LIGHT_GRAY);
        Team.setLocationRelativeTo(null);
        CreateTeamData.addTeamStatFromFile("src/pastSeason.txt");
        for(int i = 0; i< CreateTeamData.teamList.size(); i++){
            JLabel label = new JLabel(CreateTeamData.teamList.get(i).toString());
            Team.add(label);
            //jta.append(CreateTeamData.teamList.get(i).toString() + System.lineSeparator());

        }
        Team.setVisible(true);
        JButton back = new JButton("Return");
        Team.add(back);
        //Team.add(panel,BorderLayout.SOUTH);
        back.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command2 = e.getActionCommand();
                if ("Return".equals(command2)){
                    Team.dispose();
                }
            }
        });
    }
    public void startBet(User user) throws IOException {
        final int[] team1 = {0};
        final int[] team2 = {0};
        CreateTeamData.addTeamStatFromFile("src/pastSeason.txt");
        CreateFixture.addFixtureFromFile("src/pySoccer.txt");

        JFrame testFrame = new JFrame("Bet");
        //testFrame.setSize(450, 150);
        JTextArea jta = new JTextArea();
        JScrollPane jsp = new JScrollPane(jta);
        JPanel panel = new JPanel();

        testFrame.add(jsp, BorderLayout.CENTER);
        jta.setEditable(false);
        testFrame.setBounds(200,100,500,500);
        testFrame.setResizable(false);
        testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        testFrame.setLocationRelativeTo(null);
        testFrame.setVisible(true);
        //testFrame.setLayout(new FlowLayout());

        /*
        JButton enter = new JButton("Enter");
        JLabel text = new JLabel("Enter Home Team name:");
        JTextField textBook = new JTextField(20);
        testFrame.add(text);
        testFrame.add(textBook);
        JLabel text2 = new JLabel("Enter Away Team name:");
        JTextField textBook2 = new JTextField(20);
        testFrame.add(text2);
        testFrame.add(textBook2);
        testFrame.add(enter);
        enter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                if ("Enter".equals(command)){
                    String homeTeam = text.getText();
                    String arrayTeam = text2.getText();
                    /////

                }
            }
        });
         */
        //list all games in scroll panel
        for(int i = 0; i < CreateFixture.fixtureList.size(); i ++){
            int num = i +1;
            jta.append( num + ". Home team: "+ CreateFixture.fixtureList.get(i).getHomeTeam() + ". Away team: "
                    +CreateFixture.fixtureList.get(i).getAwayTeam() + "." + System.lineSeparator());
        }


        JButton enter = new JButton("Enter");
        JLabel text = new JLabel("Enter game number:");
        JTextField textBook = new JTextField(20);
        panel.add(text);
        panel.add(textBook);
        //testFrame.add(enter);
        panel.add(enter);
        testFrame.add(panel,BorderLayout.SOUTH);
        enter.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command = e.getActionCommand();
                testFrame.dispose();
                if ("Enter".equals(command)){
                    String userChoice = textBook.getText();
                    int choice_final = Integer.parseInt(userChoice);
                    String team1choice = CreateFixture.fixtureList.get(choice_final).getHomeTeam();
                    String team2choice = CreateFixture.fixtureList.get(choice_final).getAwayTeam();
                    for(int j = 0; j < CreateTeamData.teamList.size(); j ++){
                        if(team1choice.equals(CreateTeamData.teamList.get(j).getTeamName())){
                            team1[0] = j;
                        }
                    }
                    for(int j = 0; j < CreateTeamData.teamList.size(); j ++){
                        if(team2choice.equals(CreateTeamData.teamList.get(j).getTeamName())){
                            team2[0] = j;
                        }
                    }
                    TeamChoice userchoice = new TeamChoice(team1[0], team2[0]);

                    //show a new window with odds and allow the user to click which team wins or draw
                    JFrame newFrame = new JFrame();
                    newFrame.setSize(400, 150);
                    newFrame.setTitle("Pick one to bet!");
                    newFrame.setResizable(false);
                    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    newFrame.setLayout(new FlowLayout());
                    newFrame.setLocationRelativeTo(null);
                    newFrame.setVisible(true);

                    ////show odds
                    oddCalc odd = new oddCalc();
                    odd.findOdd(userchoice);

                    JLabel label = new JLabel("1. Home Team "+ team1choice + " wins. Odds: "+odd.findOdd(userchoice)[0]);
                    newFrame.add(label);
                    JLabel label2 = new JLabel("2. Away Team "+ team2choice + " wins. Odds: "+odd.findOdd(userchoice)[1]);
                    newFrame.add(label2);
                    JLabel label3 = new JLabel("3. This is a draw. Odds: " + odd.findOdd(userchoice)[2]);
                    newFrame.add(label3);
                    //JLabel label4 = new JLabel("Pick one to bet!" + System.lineSeparator());
                    //newFrame.add(label4);


                    String actualresult = CreateFixture.fixtureList.get(choice_final).getWinner();
                    if(actualresult.equals("H")){
                        actual_result = 1;
                    }else if(actualresult.equals("A")){
                        actual_result = 2;
                    }else if(actualresult.equals("D")){
                        actual_result = 3;
                    }else{

                    }

                    JButton t1win = new JButton("1");
                    newFrame.add(t1win);
                    int finalActual_result = actual_result;
                    t1win.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String command2 = e.getActionCommand();
                            if ("1".equals(command2)){
                                newFrame.dispose();
                                int userresult = 1;
                                while(true){
                                    String wager_s = JOptionPane.showInputDialog("Enter wager:");
                                    float userwager = Float.parseFloat(wager_s);
                                    if (user.balance.loadBalance() > userwager) {
                                        BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                        user.addBalance(betcalc.calcReturn());
                                        JOptionPane.showMessageDialog(null, "You won: " + betcalc.calcReturn() +"\nCurrent Balance: $" + user.balance.loadBalance(),
                                                "Result", JOptionPane.PLAIN_MESSAGE );
                                        break;

                                    } else {
                                        //no enough money, show a new window to ask whether the user want to add money or not
                                        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                        Object[] options = { "Yes", "No" };
                                        int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                        if (response == 0) {
                                            //yes
                                            String add_s = JOptionPane.showInputDialog("Enter amount of money:");
                                            float add = Float.parseFloat(add_s);
                                            user.addBalance(add);
                                            JOptionPane.showMessageDialog(null, "$" + add_s +" has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                    "Result", JOptionPane.PLAIN_MESSAGE );

                                        }
                                    }
                                }


                            }
                        }
                    });

                    JButton t2win = new JButton("2");
                    newFrame.add(t2win);
                    t2win.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String command2 = e.getActionCommand();
                            if ("2".equals(command2)){
                                newFrame.dispose();
                                int userresult = 2;
                                while(true){
                                    String wager_s = JOptionPane.showInputDialog("Enter wager:");
                                    float userwager = Float.parseFloat(wager_s);
                                    if (user.balance.loadBalance() > userwager) {
                                        BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                        user.addBalance(betcalc.calcReturn());
                                        JOptionPane.showMessageDialog(null, "You won: " + betcalc.calcReturn() +"\nCurrent Balance: $" + user.balance.loadBalance(),
                                                "Result", JOptionPane.PLAIN_MESSAGE );
                                        break;

                                    } else {
                                        //no enough money, show a new window to ask whether the user want to add money or not
                                        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                        Object[] options = { "Yes", "No" };
                                        int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                        if (response == 0) {
                                            //yes
                                            String add_s = JOptionPane.showInputDialog("Enter amount of money:");
                                            float add = Float.parseFloat(add_s);
                                            user.addBalance(add);
                                            JOptionPane.showMessageDialog(null, "$" + add_s +" has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                    "Result", JOptionPane.PLAIN_MESSAGE );

                                        }
                                    }
                                }
                            }
                        }
                    });

                    JButton draw = new JButton("3");
                    newFrame.add(draw);
                    draw.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String command2 = e.getActionCommand();
                            if ("3".equals(command2)){
                                newFrame.dispose();
                                int userresult = 3;
                                while(true){
                                    String wager_s = JOptionPane.showInputDialog("Enter wager:");
                                    float userwager = Float.parseFloat(wager_s);
                                    if (user.balance.loadBalance() > userwager) {
                                        BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                        user.addBalance(betcalc.calcReturn());
                                        JOptionPane.showMessageDialog(null, "You won: " + betcalc.calcReturn() +"\nCurrent Balance: $" + user.balance.loadBalance(),
                                                "Result", JOptionPane.PLAIN_MESSAGE );
                                        break;

                                    } else {
                                        //no enough money, show a new window to ask whether the user want to add money or not
                                        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                        Object[] options = { "Yes", "No" };
                                        int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                        if (response == 0) {
                                            //yes
                                            String add_s = JOptionPane.showInputDialog("Enter amount of money:");
                                            float add = Float.parseFloat(add_s);
                                            user.addBalance(add);
                                            JOptionPane.showMessageDialog(null, "$" + add_s +" has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                    "Result", JOptionPane.PLAIN_MESSAGE );

                                        }
                                    }
                                }

                            }
                        }
                    });

                }
            }
        });
        //testFrame.add(enter);
        testFrame.setVisible(true);

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


    public User login(JTextField textBook, JTextField textBook2){
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
                User user = new User(inputUsername, inputPassword, Float.parseFloat(last));
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