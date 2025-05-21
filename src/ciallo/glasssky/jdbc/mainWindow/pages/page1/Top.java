package ciallo.glasssky.jdbc.mainWindow.pages.page1;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.mainWindow.pages.page2.Page2;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Top extends JLabel {
    public Top(Font font ){
        this.setText("账户");
        this.setHorizontalAlignment(SwingConstants.CENTER);
        this.setFont(font);
    }

}
