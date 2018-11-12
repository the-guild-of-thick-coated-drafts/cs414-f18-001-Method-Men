package edu.colostate.cs.cs414.method_men.jungle.client.gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameEndPage extends Page implements ActionListener {
    public GameEndPage(GUI frame, boolean topPlayerWin) {
        super(frame);

        JLabel winLabel;

        Box box = Box.createVerticalBox();

        if (topPlayerWin) {
            winLabel = new JLabel("Top Player Wins!");
        } else {
            winLabel = new JLabel("Bottom Player Wins!");
        }
        winLabel.setFont(new Font(winLabel.getFont().getName(), Font.PLAIN, 50));

        JButton againButton = new JButton("Play again");
        againButton.addActionListener(this);

        JButton exitButton = new JButton("Exit game");

        box.add(box.createVerticalStrut(100));
        box.add(winLabel);
        box.add(Box.createVerticalStrut(100));
        box.add(againButton);
        box.add(box.createVerticalStrut(100));
        box.add(exitButton);

        add(box);
    }


    @Override
    public void actionPerformed(ActionEvent actionEvent) {
        frame.changePageTo(new GamePage(frame));
    }
}
