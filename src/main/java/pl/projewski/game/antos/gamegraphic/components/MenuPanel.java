package pl.projewski.game.antos.gamegraphic.components;

import java.awt.Color;
import javax.swing.JPanel;
import pl.projewski.game.antos.AntosProperties;

/**
 *
 * @author WRO00541
 */
class MenuPanel extends JPanel {

    public MenuPanel() {
        setSize(AntosProperties.MENUPANEL_WIDTH,
                AntosProperties.MENUPANEL_HEIGHT);
        setLocation(AntosProperties.GAMEPANEL_WIDTH, 0);
        setLayout(null);
        // setBackground(Color.yellow);
        add(new ExitButton());
    }

}
