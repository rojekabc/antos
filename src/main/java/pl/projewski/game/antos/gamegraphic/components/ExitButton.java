/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pl.projewski.game.antos.gamegraphic.components;

import java.awt.PopupMenu;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import pl.projewski.game.antos.AntosConfiguration;
import pl.projewski.game.antos.AntosProperties;

/**
 *
 * @author WRO00541
 */
class ExitButton extends JButton implements ActionListener {

    public ExitButton() {
        super(AntosConfiguration.getInstance().getMessage("menu.button.exit"));
        setSize(AntosProperties.MENUPANEL_WIDTH - AntosProperties.MENUPANEL_MARGIN * 2, 40);
        setLocation(
                AntosProperties.MENUPANEL_MARGIN + AntosProperties.GAMEPANEL_WIDTH,
                AntosProperties.MENUPANEL_HEIGHT - getHeight() - AntosProperties.MENUPANEL_MARGIN);
        addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        System.exit(0);
    }

}
