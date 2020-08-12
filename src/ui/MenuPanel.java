package ui;

import constants.Constants;
import image.Image;
import image.ImageFactory;
import sound.SoundPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import static sound.Sound.*;

public class MenuPanel extends JPanel {

    private GameMainFrame gameMainFrame;
    private int mazesNumber;

    public MenuPanel(GameMainFrame gameMainFrame){
        this.gameMainFrame = gameMainFrame;
        setLayout(null);
        SoundPlayer.playMusic(STARTUP);
        this.mazesNumber = loadMazes();
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(ImageFactory.createImage(Image.MENU).getImage(), 0,0,null);
        doDrawing();
    }

    private void doDrawing() {
        drawLayout();
        Toolkit.getDefaultToolkit().sync();
    }

    private int loadMazes() {
        return new File(Constants.MAZES_DIR).list().length;
    }

    private void drawLayout() {
        String[] mazeStrings = new String[this.mazesNumber];
        for(int i=0;i<this.mazesNumber;i++){
            mazeStrings[i] = new String(""+(i+1));
        }
        JComboBox mazeList = new JComboBox(mazeStrings);
        mazeList.setSelectedIndex(0);
        mazeList.setBorder(new LineBorder(Color.RED));
        mazeList.setBackground(Color.YELLOW);
        mazeList.setBounds(240,190,50,40);
        add(mazeList);
        JLabel mazesLabel = new JLabel("Maze");
        mazesLabel.setBounds(240,155,50,40);
        mazesLabel.setFont(new Font("PF Arma Five", Font.PLAIN, 11));
        mazesLabel.setForeground(Color.RED);
        add(mazesLabel);
        JButton startButton = new JButton("Start Game");
        startButton.setBackground(Color.YELLOW);
        startButton.setForeground(Color.RED);
        startButton.setFocusPainted(false);
        startButton.setBorder(new LineBorder(Color.RED));
        startButton.setFont(new Font("PF Arma Five", Font.BOLD, 18));
        startButton.setBounds(90,170,140,60);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.stopMusic(STARTUP);
                SoundPlayer.playEffect(CREDIT);
                gameMainFrame.initializeLayout(mazeList.getSelectedIndex()+1);
            }
        });
        add(startButton);
        JButton muteMusicButton;
        if(SoundPlayer.isMuteMusic()){
            muteMusicButton  = new JButton("Enable Music");
        } else {
            muteMusicButton  = new JButton("Mute Music");
        }
        muteMusicButton.setBackground(Color.YELLOW);
        muteMusicButton.setForeground(Color.RED);
        muteMusicButton.setFocusPainted(false);
        muteMusicButton.setFont(new Font("PF Arma Five", Font.BOLD, 11));
        muteMusicButton.setBorder(new LineBorder(Color.RED));
        muteMusicButton.setBounds(90,245,90,60);
        muteMusicButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.playEffect(CREDIT);
                if(!SoundPlayer.isMuteMusic()){
                    SoundPlayer.stopMusic(STARTUP);
                    SoundPlayer.removeMusic(STARTUP);
                    SoundPlayer.setMuteMusic(true);
                    muteMusicButton.setText("Enable Music");
                } else {
                    SoundPlayer.setMuteMusic(false);
                    SoundPlayer.playMusic(STARTUP);
                    muteMusicButton.setText("Mute Music");
                }
            }
        });
        add(muteMusicButton);
        JButton muteEffectsButton;
        if(SoundPlayer.isMuteEffects()){
            muteEffectsButton  = new JButton("Enable Effects");
        } else {
            muteEffectsButton  = new JButton("Mute Effects");
        }
        muteEffectsButton.setBackground(Color.YELLOW);
        muteEffectsButton.setForeground(Color.RED);
        muteEffectsButton.setFocusPainted(false);
        muteEffectsButton.setBorder(new LineBorder(Color.RED));
        muteEffectsButton.setFont(new Font("PF Arma Five", Font.BOLD, 11));
        muteEffectsButton.setBounds(200,245,90,60);
        muteEffectsButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!SoundPlayer.isMuteEffects()){
                    SoundPlayer.stopAll();
                    SoundPlayer.setMuteEffects(true);
                    muteEffectsButton.setText("Enable Effects");
                } else {
                    SoundPlayer.setMuteEffects(false);
                    SoundPlayer.playEffect(CREDIT);
                    muteEffectsButton.setText("Mute Effects");
                }
            }
        });
        add(muteEffectsButton);
    }
}
