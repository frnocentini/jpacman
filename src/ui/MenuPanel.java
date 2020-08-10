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
        JButton b = new JButton("Start Game");
        b.setBackground(new Color(255, 255, 0));
        b.setForeground(Color.RED);
        b.setFocusPainted(false);
        b.setBorder(new LineBorder(Color.RED));
        b.setFont(new Font("Tahoma", Font.BOLD, 18));
        b.setBounds(90,200,200,60);
        b.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                SoundPlayer.stopMusic(STARTUP);
                SoundPlayer.playEffect(CREDIT);
                gameMainFrame.initializeLayout();
            }
        });
        add(b);
    }
}
