import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class GameManager {
    int actual_result = 0;

    private boolean checknumber(String string) {

        Pattern pattern = Pattern.compile("[0-9]*\\.?[0-9]+");
        Matcher matcher = pattern.matcher(string);
        if (!matcher.matches()) {
            return false;
        }
        return true;
    }

    public void displayStat() throws IOException {
        JFrame Team = new JFrame();
        Team.setTitle("Team Stats");
        Team.setSize(1000, 500);
        Team.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        Team.setLayout(new FlowLayout());
        Team.getContentPane().setBackground(Color.LIGHT_GRAY);
        Team.setLocationRelativeTo(null);

        CreateTeamData.addTeamStatFromFile("src/pastSeason.txt");
        for (int i = 0; i < CreateTeamData.teamList.size(); i++) {
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
                if ("Return".equals(command2)) {
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

        JFrame frame = new JFrame();
        frame.setSize(400, 150);
        frame.setTitle("Start to bet!");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setLayout(new FlowLayout());
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        JLabel label = new JLabel("Decide which way to pick a game to bet.");
        frame.add(label);
        JLabel label2 = new JLabel("\nClick one of the following buttons.");
        frame.add(label2);

        JButton option1 = new JButton("Enter game number");
        frame.add(option1);
        option1.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command2 = e.getActionCommand();
                if ("Enter game number".equals(command2)) {
                    frame.dispose();

                    //enter game number
                    JFrame testFrame = new JFrame("Bet");
                    JTextArea jta = new JTextArea();
                    JScrollPane jsp = new JScrollPane(jta);
                    JPanel panel = new JPanel();

                    testFrame.add(jsp, BorderLayout.CENTER);
                    jta.setEditable(false);
                    testFrame.setBounds(200, 100, 500, 500);
                    testFrame.setResizable(false);
                    testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    testFrame.setLocationRelativeTo(null);
                    testFrame.setVisible(true);


                    //list all games in scroll panel
                    for (int i = 0; i < CreateFixture.fixtureList.size(); i++) {
                        int num = i + 1;
                        jta.append(num + ". Home team: " + CreateFixture.fixtureList.get(i).getHomeTeam() + ". Away team: "
                                + CreateFixture.fixtureList.get(i).getAwayTeam() + "." + System.lineSeparator());
                    }


                    JButton enter = new JButton("Enter");
                    JLabel text = new JLabel("Enter game number:");
                    JTextField textBook3 = new JTextField(20);
                    panel.add(text);
                    panel.add(textBook3);
                    panel.add(enter);

                    testFrame.add(panel, BorderLayout.SOUTH);
                    enter.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e2) {
                            String command = e2.getActionCommand();
                            if ("Enter".equals(command)) {
                                String userChoice = textBook3.getText();
                                testFrame.dispose();
                                if (checknumber(userChoice)) {
                                    int choice_final = Integer.parseInt(userChoice);
                                    if (choice_final > 0 && choice_final < CreateFixture.fixtureList.size()) {
                                        String team1choice = CreateFixture.fixtureList.get(choice_final).getHomeTeam();
                                        String team2choice = CreateFixture.fixtureList.get(choice_final).getAwayTeam();
                                        for (int j = 0; j < CreateTeamData.teamList.size(); j++) {
                                            if (team1choice.equals(CreateTeamData.teamList.get(j).getTeamName())) {
                                                team1[0] = j;
                                            }
                                        }
                                        for (int j = 0; j < CreateTeamData.teamList.size(); j++) {
                                            if (team2choice.equals(CreateTeamData.teamList.get(j).getTeamName())) {
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

                                        JLabel label = new JLabel("1. Home Team " + team1choice + " wins. Odds: " + odd.findOdd(userchoice)[0]);
                                        newFrame.add(label);
                                        JLabel label2 = new JLabel("2. Away Team " + team2choice + " wins. Odds: " + odd.findOdd(userchoice)[1]);
                                        newFrame.add(label2);
                                        JLabel label3 = new JLabel("3. This is a draw. Odds: " + odd.findOdd(userchoice)[2]);
                                        newFrame.add(label3);
                                        //JLabel label4 = new JLabel("Pick one to bet!" + System.lineSeparator());
                                        //newFrame.add(label4);


                                        String actualresult = CreateFixture.fixtureList.get(choice_final).getWinner();
                                        if (actualresult.equals("H")) {
                                            actual_result = 1;
                                        } else if (actualresult.equals("A")) {
                                            actual_result = 2;
                                        } else if (actualresult.equals("D")) {
                                            actual_result = 3;
                                        } else {

                                        }

                                        JButton t1win = new JButton("1");
                                        newFrame.add(t1win);
                                        int finalActual_result = actual_result;
                                        t1win.addActionListener(new AbstractAction() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                String command2 = e.getActionCommand();
                                                if ("1".equals(command2)) {
                                                    newFrame.dispose();

                                                    int userresult = 1;
                                                    while (true) {
                                                        String wager = JOptionPane.showInputDialog("Enter wager (the amount of money to bet):");
                                                        if (checknumber(wager)) {
                                                            float userwager = Float.parseFloat(wager);
                                                            if (user.balance.loadBalance() >= userwager) {
                                                                BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                                                if (betcalc.calcReturn() > 0) {
                                                                    user.addBalance(userwager * (-1));
                                                                    user.addBalance(betcalc.calcReturn());
                                                                    JOptionPane.showMessageDialog(null, "You won: $" + betcalc.calcReturn() + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                            "Result", JOptionPane.PLAIN_MESSAGE);
                                                                    break;
                                                                } else {
                                                                    user.addBalance(betcalc.calcReturn());
                                                                    JOptionPane.showMessageDialog(null, "You lost: $" + betcalc.calcReturn() * (-1) + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                            "Result", JOptionPane.PLAIN_MESSAGE);
                                                                    break;
                                                                }

                                                            } else {
                                                                //no enough money, show a new window to ask whether the user want to add money or not
                                                                UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                                UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                                Object[] options = {"Yes", "No"};
                                                                int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                                                if (response == 0) {
                                                                    //yes
                                                                    String add_s = JOptionPane.showInputDialog("Enter amount of money to add:");
                                                                    float add = Float.parseFloat(add_s);
                                                                    user.addBalance(add);
                                                                    JOptionPane.showMessageDialog(null, "$" + add_s + " has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                                            "Result", JOptionPane.PLAIN_MESSAGE);

                                                                }
                                                            }

                                                        } else {
                                                            JOptionPane.showMessageDialog(null, "Invalid wager input.",
                                                                    "Result", JOptionPane.PLAIN_MESSAGE);
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
                                                if ("2".equals(command2)) {
                                                    newFrame.dispose();

                                                    int userresult = 2;
                                                    while (true) {
                                                        String wager_s = JOptionPane.showInputDialog("Enter wager (the amount of money to bet):");
                                                        float userwager = Float.parseFloat(wager_s);
                                                        if (user.balance.loadBalance() > userwager) {
                                                            BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                                            if (betcalc.calcReturn() > 0) {
                                                                user.addBalance(userwager * (-1));
                                                                user.addBalance(betcalc.calcReturn());
                                                                JOptionPane.showMessageDialog(null, "You won: $" + betcalc.calcReturn() + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);
                                                                break;
                                                            } else {
                                                                user.addBalance(betcalc.calcReturn());
                                                                JOptionPane.showMessageDialog(null, "You lost: $" + betcalc.calcReturn() * (-1) + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);
                                                                break;
                                                            }

                                                        } else {
                                                            //no enough money, show a new window to ask whether the user want to add money or not
                                                            UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                            Object[] options = {"Yes", "No"};
                                                            int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                                            if (response == 0) {
                                                                //yes
                                                                String add_s = JOptionPane.showInputDialog("Enter amount of money to add:");
                                                                float add = Float.parseFloat(add_s);
                                                                user.addBalance(add);
                                                                JOptionPane.showMessageDialog(null, "$" + add_s + " has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);

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
                                                if ("3".equals(command2)) {
                                                    newFrame.dispose();

                                                    int userresult = 3;
                                                    while (true) {
                                                        String wager_s = JOptionPane.showInputDialog("Enter wager (the amount of money to bet):");
                                                        float userwager = Float.parseFloat(wager_s);
                                                        if (user.balance.loadBalance() > userwager) {
                                                            BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                                            if (betcalc.calcReturn() > 0) {
                                                                user.addBalance(userwager * (-1));
                                                                user.addBalance(betcalc.calcReturn());
                                                                JOptionPane.showMessageDialog(null, "You won: $" + betcalc.calcReturn() + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);
                                                                break;
                                                            } else {
                                                                user.addBalance(betcalc.calcReturn());
                                                                JOptionPane.showMessageDialog(null, "You lost: $" + betcalc.calcReturn() * (-1) + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);
                                                                break;
                                                            }

                                                        } else {
                                                            //no enough money, show a new window to ask whether the user want to add money or not
                                                            UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                            Object[] options = {"Yes", "No"};
                                                            int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                                            if (response == 0) {
                                                                //yes
                                                                String add_s = JOptionPane.showInputDialog("Enter amount of money to add:");
                                                                float add = Float.parseFloat(add_s);
                                                                user.addBalance(add);
                                                                JOptionPane.showMessageDialog(null, "$" + add_s + " has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);

                                                            }
                                                        }
                                                    }

                                                }
                                            }
                                        });

                                    } else {
                                        JFrame newFrame = new JFrame();
                                        newFrame.setSize(400, 150);
                                        newFrame.setTitle("Result");
                                        newFrame.setResizable(false);
                                        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                        newFrame.setLayout(new FlowLayout());
                                        newFrame.setLocationRelativeTo(null);
                                        JLabel label = new JLabel("Invalid game number entered.");
                                        newFrame.add(label);
                                        newFrame.setVisible(true);
                                        JButton back = new JButton("Return");
                                        newFrame.add(back);
                                        back.addActionListener(new AbstractAction() {
                                            @Override
                                            public void actionPerformed(ActionEvent e) {
                                                String command2 = e.getActionCommand();
                                                if ("Return".equals(command2)) {
                                                    newFrame.dispose();
                                                }
                                            }
                                        });
                                    }

                                } else {
                                    JFrame newFrame = new JFrame();
                                    newFrame.setSize(400, 150);
                                    newFrame.setTitle("Result");
                                    newFrame.setResizable(false);
                                    newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                    newFrame.setLayout(new FlowLayout());
                                    newFrame.setLocationRelativeTo(null);
                                    JLabel label = new JLabel("Invalid game number entered.");
                                    newFrame.add(label);
                                    newFrame.setVisible(true);
                                    JButton back = new JButton("Return");
                                    newFrame.add(back);
                                    back.addActionListener(new AbstractAction() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String command2 = e.getActionCommand();
                                            if ("Return".equals(command2)) {
                                                newFrame.dispose();
                                            }
                                        }
                                    });
                                }


                            }
                        }
                    });
                    //testFrame.add(enter);
                    testFrame.setVisible(true);


                }
            }
        });

        JButton option2 = new JButton("Enter team names manually");
        frame.add(option2);
        option2.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String command2 = e.getActionCommand();
                if ("Enter team names manually".equals(command2)) {
                    frame.dispose();

                    //enter team names
                    JFrame testFrame = new JFrame("Bet");
                    //testFrame.setSize(450, 150);
                    JTextArea jta = new JTextArea();
                    JScrollPane jsp = new JScrollPane(jta);
                    JPanel panel = new JPanel();
                    //panel.setSize(500,150);
                    //jta.setBounds(200, 100,500,300);
                    //jsp.setBounds(200, 100, 480, 350);
                    testFrame.add(jsp, BorderLayout.NORTH);
                    jta.setEditable(false);
                    testFrame.setBounds(200, 100, 480, 480);
                    testFrame.setResizable(false);
                    testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                    testFrame.setLocationRelativeTo(null);
                    testFrame.setVisible(true);


                    //list all teams in scroll panel
                    for (int i = 0; i < CreateTeamData.teamList.size(); i++) {
                        int num = i + 1;
                        jta.append(num + ". " + CreateTeamData.teamList.get(i).toString() + System.lineSeparator());
                    }

                    JButton enter = new JButton("Enter");
                    JLabel text = new JLabel("Enter Home Team name:");
                    JTextField textBook = new JTextField(20);
                    panel.add(text);
                    panel.add(textBook);

                    JLabel text2 = new JLabel("Enter Away Team name:");
                    JTextField textBook2 = new JTextField(20);
                    panel.add(text2);
                    panel.add(textBook2);

                    panel.add(enter);
                    testFrame.add(panel);
                    enter.addActionListener(new AbstractAction() {
                        @Override
                        public void actionPerformed(ActionEvent e) {
                            String command = e.getActionCommand();
                            testFrame.dispose();
                            if ("Enter".equals(command)) {
                                String homeTeam = textBook.getText();
                                String awayTeam = textBook2.getText();
                                boolean flag = false;
                                int index = 0;
                                ///original i = 1
                                for (int i = 0; i < CreateFixture.fixtureList.size(); i++) {
                                    if (homeTeam.equals(CreateFixture.fixtureList.get(i).getHomeTeam()) && awayTeam.equals(CreateFixture.fixtureList.get(i).getAwayTeam())) {
                                        flag = true;
                                        index = i;
                                    }
                                }
                                if (flag) {
                                    //flag = true
                                    //show confirm window first
                                    JOptionPane.showMessageDialog(null, "The game you picked is:" + "\nHome Team: " + homeTeam + "\nAway Team: " + awayTeam,
                                            "Result", JOptionPane.PLAIN_MESSAGE);
                                    //after confirm
                                    for (int j = 0; j < CreateTeamData.teamList.size(); j++) {
                                        if (homeTeam.equals(CreateTeamData.teamList.get(j).getTeamName())) {
                                            team1[0] = j;
                                        }
                                    }
                                    for (int j = 0; j < CreateTeamData.teamList.size(); j++) {
                                        if (awayTeam.equals(CreateTeamData.teamList.get(j).getTeamName())) {
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

                                    JLabel label = new JLabel("1. Home Team " + homeTeam + " wins. Odds: " + odd.findOdd(userchoice)[0]);
                                    newFrame.add(label);
                                    JLabel label2 = new JLabel("2. Away Team " + awayTeam + " wins. Odds: " + odd.findOdd(userchoice)[1]);
                                    newFrame.add(label2);
                                    JLabel label3 = new JLabel("3. This is a draw. Odds: " + odd.findOdd(userchoice)[2]);
                                    newFrame.add(label3);

                                    String actualresult = CreateFixture.fixtureList.get(index).getWinner();
                                    if (actualresult.equals("H")) {
                                        actual_result = 1;
                                    } else if (actualresult.equals("A")) {
                                        actual_result = 2;
                                    } else if (actualresult.equals("D")) {
                                        actual_result = 3;
                                    } else {

                                    }

                                    JButton t1win = new JButton("1");
                                    newFrame.add(t1win);
                                    int finalActual_result = actual_result;
                                    t1win.addActionListener(new AbstractAction() {
                                        @Override
                                        public void actionPerformed(ActionEvent e) {
                                            String command2 = e.getActionCommand();
                                            if ("1".equals(command2)) {
                                                newFrame.dispose();

                                                int userresult = 1;
                                                while (true) {
                                                    String wager_s = JOptionPane.showInputDialog("Enter wager (the amount of money to bet):");
                                                    if (checknumber(wager_s)) {
                                                        float userwager = Float.parseFloat(wager_s);
                                                        if (user.balance.loadBalance() >= userwager) {
                                                            BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                                            if (betcalc.calcReturn() > 0) {
                                                                user.addBalance(userwager * (-1));
                                                                user.addBalance(betcalc.calcReturn());
                                                                JOptionPane.showMessageDialog(null, "You won: $" + betcalc.calcReturn() + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);
                                                                break;
                                                            } else {
                                                                user.addBalance(betcalc.calcReturn());
                                                                JOptionPane.showMessageDialog(null, "You lost: $" + betcalc.calcReturn() * (-1) + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);
                                                                break;
                                                            }

                                                        } else {
                                                            //no enough money, show a new window to ask whether the user want to add money or not
                                                            UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                            UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                            Object[] options = {"Yes", "No"};
                                                            int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                                            if (response == 0) {
                                                                //yes
                                                                String add_s = JOptionPane.showInputDialog("Enter amount of money to add:");
                                                                float add = Float.parseFloat(add_s);
                                                                user.addBalance(add);
                                                                JOptionPane.showMessageDialog(null, "$" + add_s + " has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                                        "Result", JOptionPane.PLAIN_MESSAGE);

                                                            }
                                                        }

                                                    } else {
                                                        JOptionPane.showMessageDialog(null, "Invalid wager input.",
                                                                "Result", JOptionPane.PLAIN_MESSAGE);
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
                                            if ("2".equals(command2)) {
                                                newFrame.dispose();

                                                int userresult = 2;
                                                while (true) {
                                                    String wager_s = JOptionPane.showInputDialog("Enter wager (the amount of money to bet):");
                                                    float userwager = Float.parseFloat(wager_s);
                                                    if (user.balance.loadBalance() > userwager) {
                                                        BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                                        if (betcalc.calcReturn() > 0) {
                                                            user.addBalance(userwager * (-1));
                                                            user.addBalance(betcalc.calcReturn());
                                                            JOptionPane.showMessageDialog(null, "You won: $" + betcalc.calcReturn() + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                    "Result", JOptionPane.PLAIN_MESSAGE);
                                                            break;
                                                        } else {
                                                            user.addBalance(betcalc.calcReturn());
                                                            JOptionPane.showMessageDialog(null, "You lost: $" + betcalc.calcReturn() * (-1) + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                    "Result", JOptionPane.PLAIN_MESSAGE);
                                                            break;
                                                        }

                                                    } else {
                                                        //no enough money, show a new window to ask whether the user want to add money or not
                                                        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                        Object[] options = {"Yes", "No"};
                                                        int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                                        if (response == 0) {
                                                            //yes
                                                            String add_s = JOptionPane.showInputDialog("Enter amount of money to add:");
                                                            float add = Float.parseFloat(add_s);
                                                            user.addBalance(add);
                                                            JOptionPane.showMessageDialog(null, "$" + add_s + " has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                                    "Result", JOptionPane.PLAIN_MESSAGE);

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
                                            if ("3".equals(command2)) {
                                                newFrame.dispose();

                                                int userresult = 3;
                                                while (true) {
                                                    String wager_s = JOptionPane.showInputDialog("Enter wager (the amount of money to bet):");
                                                    float userwager = Float.parseFloat(wager_s);
                                                    if (user.balance.loadBalance() > userwager) {
                                                        BetCalc betcalc = new BetCalc(userresult, userwager, actual_result, odd.findOdd(userchoice));
                                                        if (betcalc.calcReturn() > 0) {
                                                            user.addBalance(userwager * (-1));
                                                            user.addBalance(betcalc.calcReturn());
                                                            JOptionPane.showMessageDialog(null, "You won: $" + betcalc.calcReturn() + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                    "Result", JOptionPane.PLAIN_MESSAGE);
                                                            break;
                                                        } else {
                                                            user.addBalance(betcalc.calcReturn());
                                                            JOptionPane.showMessageDialog(null, "You lost: $" + betcalc.calcReturn() * (-1) + "\nCurrent Balance: $" + user.balance.loadBalance(),
                                                                    "Result", JOptionPane.PLAIN_MESSAGE);
                                                            break;
                                                        }

                                                    } else {
                                                        //no enough money, show a new window to ask whether the user want to add money or not
                                                        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                                        Object[] options = {"Yes", "No"};
                                                        int response = JOptionPane.showOptionDialog(null, "You don't have enough money in current balance. \nDo you want to recharge?", "Balance", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                                        if (response == 0) {
                                                            //yes
                                                            String add_s = JOptionPane.showInputDialog("Enter amount of money to add:");
                                                            float add = Float.parseFloat(add_s);
                                                            user.addBalance(add);
                                                            JOptionPane.showMessageDialog(null, "$" + add_s + " has been added." + "\nYour current balance: $" + user.balance.loadBalance(),
                                                                    "Result", JOptionPane.PLAIN_MESSAGE);

                                                        }
                                                    }
                                                }

                                            }
                                        }
                                    });


                                } else {
                                    //flag = false
                                    //check if the input is valid
                                    boolean flag2 = false;
                                    for (int j = 0; j < CreateTeamData.teamList.size(); j++) {
                                        if (homeTeam.equals(CreateTeamData.teamList.get(j).getTeamName())) {
                                            team1[0] = j;
                                            flag2 = true;
                                        }
                                    }
                                    boolean flag3 = false;
                                    for (int j = 0; j < CreateTeamData.teamList.size(); j++) {
                                        if (awayTeam.equals(CreateTeamData.teamList.get(j).getTeamName())) {
                                            team1[0] = j;
                                            flag3 = true;
                                        }
                                    }
                                    if (flag2 == false && flag3 == false) {
                                        JOptionPane.showMessageDialog(null, "Invalid Home Team name and Away Team name.",
                                                "Error", JOptionPane.PLAIN_MESSAGE);
                                    } else if (flag2 == false && flag3 == true) {
                                        JOptionPane.showMessageDialog(null, "Invalid Home Team name.",
                                                "Error", JOptionPane.PLAIN_MESSAGE);

                                    } else if (flag2 == true && flag3 == false) {
                                        JOptionPane.showMessageDialog(null, "Invalid Away Team name.",
                                                "Error", JOptionPane.PLAIN_MESSAGE);

                                    } else {
                                        //flag2 == true & flag3 == true
                                        JOptionPane.showMessageDialog(null, "The game between the two input teams does not exist.",
                                                "Error", JOptionPane.PLAIN_MESSAGE);
                                    }

                                }

                            }
                        }
                    });

                }
            }
        });
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
                    if (checknumber(input)){
                        float money = Float.parseFloat(input);
                        user.addBalance(money);
                        ///new window and show money successfully added
                        //testFrame.dispose();
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
                                    textBook.setText("");
                                }
                            }
                        });
                    } else {
                        //testFrame.dispose();
                        JFrame newFrame = new JFrame();
                        newFrame.setSize(400, 150);
                        newFrame.setTitle("Result");
                        newFrame.setResizable(false);
                        newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        newFrame.setLayout(new FlowLayout());
                        newFrame.setLocationRelativeTo(null);
                        JLabel label = new JLabel("Invalid amount entered.");
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
                                    textBook.setText("");
                                }
                            }
                        });
                    }

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
