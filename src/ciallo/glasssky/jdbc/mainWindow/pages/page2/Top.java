package ciallo.glasssky.jdbc.mainWindow.pages.page2;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Top extends JComboBox<String> implements Format {
    public Top(Font font ){
        this.addItem("收入");
        this.addItem("支出");
        this.setRenderer(Tools.getCenter());
        this.setFont(font);
        this.addActionListener(e->{
            if(e.getSource() == this)
            {
                try {
                    ((Page2)this.getParent()).getCenter().query((String) this.getSelectedItem());
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });
    }

    @Override
    public void init() {
        this.setSelectedIndex(0);
    }

    @Override
    public void end() {

    }
}
