package com.mygame;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Date;
import javax.swing.DebugGraphics;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class MyGame extends JFrame implements ActionListener{
    
    //VARIABLE TO  BE USED
    JLabel heading, clockLabel, winCountLabel;
    Font font = new Font("",Font.BOLD,40);
    JPanel mainPanel;
    JButton []btns = new JButton[9];
    
    //GAME INSTANCE VARIABLE
    int gameChances[] ={2,2,2,2,2,2,2,2,2};
    int activePlayer = 0;

    //FIND THE WINNNER
    int winP[][]={
        {0,1,2},
        {3,4,5},
        {6,7,8},
        {0,3,6},
        {1,4,7},
        {2,5,8},
        {0,4,8},
        {2,4,6}
       
    };

    static int playerXwins = 0;
    static int playerOwins = 0;
    
    int winner = 2;
    boolean gameOver = false;
    //=====================================================

    //MAIN-WINDOW
    MyGame(){
        System.out.println("GUI Of Game");
        setTitle("My Tic Tac Toe Game");
        setSize(700,700);
        ImageIcon icon = new ImageIcon("src/img/tictactoelogo.jpg");
        setIconImage(icon.getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        createGUI();
        setVisible(true);
    }
    
    private void createGUI(){
        this.getContentPane().setBackground(Color.decode("#000000")); // set the color of background
        this.setLayout(new BorderLayout());
        

        JPanel headingpanel = new JPanel();
        headingpanel.setLayout(new GridLayout(2,1));
        headingpanel.setBackground(Color.decode("#0000000"));
        headingpanel.setPreferredSize(new Dimension(100,130));
        //NORTH-HEADING
        heading = new JLabel("Tic Tac Toe");

        ImageIcon logo = new ImageIcon("src/img/tictactoelogo.jpg");
        Image logoimg = logo.getImage();
        Image scaledlogo = logoimg.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
        heading.setIcon(new ImageIcon(scaledlogo));

        heading.setHorizontalTextPosition(SwingConstants.CENTER);
        heading.setVerticalTextPosition(SwingConstants.BOTTOM);
        heading.setFont(new Font(" ",Font.BOLD,20));
        heading.setHorizontalAlignment(SwingConstants.CENTER);
        heading.setForeground(Color.decode("#ffffff"));
        headingpanel.add(heading);


        //CREATING OBJ OF JLabel FOR CLOCK
        clockLabel = new JLabel("Clock");
        clockLabel.setFont(font);
        clockLabel.setHorizontalAlignment(SwingConstants.CENTER);
        clockLabel.setForeground(Color.decode("#ffffff"));
        this.add(clockLabel,BorderLayout.SOUTH);

        winCountLabel = new JLabel("Player X: " + playerXwins+ "  Player O: "+ playerOwins);
        winCountLabel.setFont(new Font("", Font.BOLD, 16));
        winCountLabel.setHorizontalAlignment(SwingConstants.CENTER);
        winCountLabel.setForeground(Color.WHITE);
        headingpanel.add(winCountLabel);

        this.add(headingpanel,BorderLayout.NORTH);
        
        Thread thread = new Thread(){
            public void run(){

                try{
                    while (true) {
                        String datetime = new Date().toLocaleString();

                        clockLabel.setText(datetime);

                        Thread.sleep(1000);
                    }
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            }
        };
        thread.start();


        //PANEL SECTION
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(3, 3));

        for (int i=1; i<=9; i++) {
            JButton btn = new JButton();
            btn.setBackground(Color.decode("#ffffff"));
            btn.setFont(font);
            mainPanel.add(btn);
            btns[i-1] = btn;
            btn.addActionListener(this);
            btn.setName(String.valueOf(i-1));

        }
        this.add(mainPanel,BorderLayout.CENTER);
    }


    //ACTION TO BE PERFORMED

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton currentBtn = (JButton) e.getSource();
        String nameStr = currentBtn.getName();
        int name = Integer.parseInt(nameStr.trim());

        if (gameOver == true) 
        {
            JOptionPane.showMessageDialog(this,"Game Over 🕐");
            return;
        }

        if (gameChances[name] == 2) {

            if (activePlayer == 1) {
                ImageIcon icon = new ImageIcon("src/img/X.png");
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                currentBtn.setIcon(new ImageIcon(scaledImg));

                gameChances[name] = activePlayer;
                activePlayer = 0;
            }
            else{
                ImageIcon icon = new ImageIcon("src/img/O.png");
                Image img = icon.getImage();
                Image scaledImg = img.getScaledInstance(150, 150, Image.SCALE_SMOOTH);
                currentBtn.setIcon(new ImageIcon(scaledImg));

                gameChances[name] = activePlayer;
                activePlayer = 1;
            }

            //FIND THE WINNER
            for (int []temp : winP) 
            {
                if ((gameChances[temp[0]]==gameChances[temp[1]]) && (gameChances[temp[1]]==gameChances[temp[2]]) && (gameChances[temp[2]]!=2)) 
                {
                    winner = gameChances[temp[0]];
                    gameOver = true;

                    if (winner ==1) {
                        playerXwins++;
                    }else if (winner==0) {
                        playerOwins++;
                    }
                    winCountLabel.setText("Player X: "+playerXwins + " Player O: "+ playerOwins);

                    JOptionPane.showMessageDialog(null, "Player " + winner + " has won the game 🏆");
                    int i = JOptionPane.showConfirmDialog(this,"Let's play again?? ");
                    System.out.println(i);
                    if (i==0) {
                        this.setVisible(false);
                        new MyGame();
                    }
                    else if (i==1)
                    {
                        System.exit(786);;
                    }else{

                    }
                    System.out.println(i);
                    break;
                }
            
            }

            //DRAW LOGIC 
            int count = 0;
            for (int x : gameChances) 
            {
                if (x==2) {
                    count++;
                    break;
                }
            }
            if (count==0 && gameOver==false) {
                JOptionPane.showMessageDialog(null, "Game Draw");

                playerXwins = 0;
                playerOwins = 0;
                winCountLabel.setText("Player X: "+playerXwins + " Player O: "+ playerOwins);
                
                int i = JOptionPane.showConfirmDialog(null, "Let's play again??");
                if (i==0) {
                    this.setVisible(false);
                    new MyGame();
                }
                else if (i==1)
                {
                    System.exit(786);;
                }else{

                }
                gameOver=true;
            }
            
        }
        else{
            JOptionPane.showMessageDialog(this,"Position already occupied");
        }
        
    }
}
