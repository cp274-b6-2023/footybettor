import javax.swing.*;
import javax.swing.plaf.FontUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.io.*;
import java.util.Scanner;

public class Main2 {
    private static String foldername;
    private static String filename;

    public static boolean createfolder(String path) {
        File file = null;
        try {
            file = new File(path);
            if (!file.exists()) {
                return file.mkdirs();
            }else {
                return false;
            }
        }catch(Exception e) {
        }return false;
    }

    public static void main(String[] args) throws IOException {
        foldername = "FootyBettor";
        GameManager game = new GameManager();
        if(createfolder(foldername)){
            System.out.println("Folder created!");
        } else {
            System.out.println("Folder exists.");
        }
        JFrame frame = new JFrame();
        frame.setTitle("FootyBettor");
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.getContentPane().setBackground(Color.WHITE);
        frame.setLayout(new FlowLayout());
        JLabel label = new JLabel("Welcome to the Sports Betting Platform", JLabel.CENTER);
        label.setFont(new Font("Verdana", Font.BOLD, 20));
        JButton logIn = new JButton("Log in");
        logIn.setBounds(800,800, 800, 800);
        logIn.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame testFrame = new JFrame("LOG IN");
                testFrame.setSize(400, 150);
                testFrame.setResizable(false);
                testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                testFrame.setLocationRelativeTo(null);
                testFrame.setLayout(new FlowLayout());

                JButton enter = new JButton("Enter");
                JLabel text = new JLabel("Enter username:");
                JTextField textBook = new JTextField(20);
                testFrame.add(text);
                testFrame.add(textBook);
                JLabel text2 = new JLabel("Enter password:");
                JTextField textBook2 = new JTextField(20);
                testFrame.add(text2);
                testFrame.add(textBook2);
                testFrame.add(enter);
                enter.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String command = e.getActionCommand();
                        if ("Enter".equals(command)){
                            User user = game.login(textBook, textBook2);
                            if(user.balance.loadBalance() != -10 && user.balance.loadBalance() != -20){
                                System.out.println("Access Granted!");
                                testFrame.dispose();
                                frame.dispose();

                                //////start a new frame to display dashboard
                                JFrame frame2 = new JFrame();
                                frame2.setTitle("Sport Betting Platform");
                                frame2.setSize(500, 300);
                                frame2.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                                frame2.setResizable(false);
                                frame2.getContentPane().setBackground(Color.WHITE);
                                // the box layout will be from top to bottom
                                frame2.setLayout(new FlowLayout());
                                JLabel label = new JLabel("Welcome to Sport Betting Platform", JLabel.CENTER);
                                // set font style and size
                                label.setFont(new Font("Verdana", Font.BOLD, 20));

                                JButton check_team_stats = new JButton("Check Team Stats");
                                check_team_stats.setBounds(800,800, 800, 800);
                                check_team_stats.addActionListener(new AbstractAction() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                    }
                                });

                                JButton bet = new JButton("Start to bet");
                                bet.setBounds(800,800, 800, 800);
                                bet.addActionListener(new AbstractAction() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                    }
                                });

                                JButton check_balance = new JButton("Check Balance/ Deposit");
                                check_balance.setBounds(800,800, 800, 800);
                                check_balance.addActionListener(new AbstractAction() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {

                                    }
                                });

                                JButton exit = new JButton("Exit Program");
                                exit.addActionListener(new AbstractAction() {
                                    @Override
                                    public void actionPerformed(ActionEvent e) {
                                        //confirm before leave or exit the program
                                        String command = e.getActionCommand();
                                        UIManager.put("OptionPane.buttonFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                        UIManager.put("OptionPane.messageFont", new FontUIResource(new Font("Verdana", Font.PLAIN, 13)));
                                        if ("Exit Program".equals(command)) {
                                            Object[] options = { "Confirm", "Cancel" };
                                            int response = JOptionPane.showOptionDialog(frame, "Do you want to exit?", "", JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE, null, options, options[0]);
                                            if (response == 0) {
                                                System.exit(0);
                                            }
                                        }
                                    }
                                });
                                frame2.add(label);
                                frame2.add(check_balance);
                                frame2.add(check_team_stats);
                                frame2.add(bet);
                                frame2.add(exit);
                                frame2.setVisible(true);

                            } else if (user.balance.loadBalance() == -10){
                                //account does exist, but wrong password
                                testFrame.dispose();
                                JFrame newFrame = new JFrame();
                                newFrame.setSize(400, 150);
                                newFrame.setTitle("Result");
                                newFrame.setResizable(false);
                                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                newFrame.setLayout(new FlowLayout());
                                newFrame.setLocationRelativeTo(null);
                                JLabel label = new JLabel("Invalid password. Failed to log in.");
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
                                //account does not exist
                                testFrame.dispose();
                                JFrame newFrame = new JFrame();
                                newFrame.setSize(400, 150);
                                newFrame.setTitle("Result");
                                newFrame.setResizable(false);
                                newFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                                newFrame.setLayout(new FlowLayout());
                                newFrame.setLocationRelativeTo(null);
                                JLabel label = new JLabel("Account does not exist.");
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

                    }

                });
                testFrame.setVisible(true);
            }
        });
        JButton signUp = new JButton("Sign Up");
        frame.add(signUp);
        signUp.setBounds(800,800, 800, 800);
        signUp.addActionListener(new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFrame testFrame = new JFrame("SIGN UP");
                testFrame.setSize(400, 150);
                testFrame.setResizable(false);
                testFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                testFrame.setLocationRelativeTo(null);
                testFrame.setLayout(new FlowLayout());

                JButton enter = new JButton("Enter");
                JLabel text = new JLabel("Enter username:");
                JTextField textBook = new JTextField(20);
                testFrame.add(text);
                testFrame.add(textBook);
                JLabel text2 = new JLabel("Enter password:");
                JTextField textBook2 = new JTextField(20);
                testFrame.add(text2);
                testFrame.add(textBook2);
                testFrame.add(enter);
                enter.addActionListener(new AbstractAction() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String command = e.getActionCommand();
                        if ("Enter".equals(command)){
                            game.signUp(textBook, textBook2, testFrame);

                        }
                    }
                });
                testFrame.setVisible(true);
            }
        });
        frame.add(logIn);
        frame.add(signUp);
        frame.setVisible(true);
    }
}
