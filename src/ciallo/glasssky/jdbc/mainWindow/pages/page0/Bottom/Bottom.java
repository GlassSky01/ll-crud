package ciallo.glasssky.jdbc.mainWindow.pages.page0.Bottom;

import ciallo.glasssky.jdbc.Format;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Bottom extends JPanel implements Format {
    private Buttons buttons;
    public Input input;
    public Bottom(int h){
        input = new Input(h);
        buttons = new Buttons(h);
        this.setLayout(new GridLayout(2 , 1));
        this.add(input);
        this.add(buttons);
    }
    @Override
    public void init() throws SQLException {
        input.init();
    }

    @Override
    public void end() {

    }
}
