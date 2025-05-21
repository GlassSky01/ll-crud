package ciallo.glasssky.jdbc.mainWindow.pages.page0;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.mainWindow.pages.Page;
import ciallo.glasssky.jdbc.mainWindow.pages.page0.Bottom.Bottom;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;

public class Page0 extends Page implements Format {
    DefaultTableModel billingModel;
    private Bottom bottom;
    private Center center;
    public Page0(JPanel window ,int h) {
        super(window , h);
        bottom = new Bottom(h);
        center = new Center(h);
        this.setLayout(new BorderLayout());
        this.add(center , BorderLayout.CENTER);
        this.add(bottom , BorderLayout.SOUTH);
    }

    @Override
    public void init() throws SQLException {
        bottom.init();
        center.init();
    }

    @Override
    public void end() {
    }
    public Center getCenter(){
        return center;
    }
}
