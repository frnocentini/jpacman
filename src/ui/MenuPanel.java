package ui;

import image.Image;
import image.ImageFactory;
import sound.SoundFactory;
import sound.SoundPlayer;

import javax.swing.*;
import javax.swing.border.LineBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static sound.Sound.*;

public class MenuPanel extends JPanel {

    GameMainFrame gameMainFrame;

    public MenuPanel(GameMainFrame gameMainFrame){
        this.gameMainFrame = gameMainFrame;
        setLayout(null);
        SoundPlayer.playMusic(STARTUP);
    }

    @Override
    protected void paintComponent(Graphics g){
        super.paintComponent(g);
        g.drawImage(ImageFactory.createImage(Image.MENU).getImage(), 0,0,null);
        doDrawing(g);
    }

    private void doDrawing(Graphics g) {
        drawButtons();
        Toolkit.getDefaultToolkit().sync();
    }

    private void drawButtons() {
        JButton startButton = new JButton("Start Game");
        startButton.setBackground(new Color(255, 255, 0));
        startButton.setForeground(Color.RED);
        startButton.setFocusPainted(false);
        startButton.setBorder(new LineBorder(Color.RED));
        startButton.setFont(new Font("Tahoma", Font.BOLD, 18));
        startButton.setBounds(90,170,200,60);
        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.stopMusic(STARTUP);
                SoundPlayer.playEffect(CREDIT);
                gameMainFrame.initializeLayout();
            }
        });
        add(startButton);
        JButton muteMusicButton;
        if(SoundPlayer.isMuteMusic()){
            muteMusicButton  = new JButton("Enable Music");
        } else {
            muteMusicButton  = new JButton("Mute Music");
        }
        muteMusicButton.setBackground(new Color(255, 255, 0));
        muteMusicButton.setForeground(Color.RED);
        muteMusicButton.setFocusPainted(false);
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
        muteEffectsButton.setBackground(new Color(255, 255, 0));
        muteEffectsButton.setForeground(Color.RED);
        muteEffectsButton.setFocusPainted(false);
        muteEffectsButton.setBorder(new LineBorder(Color.RED));
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
