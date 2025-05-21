package ciallo.glasssky.jdbc.mainWindow.pages.page2;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;
import ciallo.glasssky.jdbc.mainWindow.pages.Page;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Page2 extends Page implements Format {
    private Top top;
    private Center center;
    private Bottom bottom;
    public Page2(JPanel window ,int h) {
        super(window , h);


        this.setLayout(new BorderLayout());
        Font font = Tools.getFont(h , 10);
        top = new Top(font);
        center = new Center(font);
        bottom = new Bottom(font);
        this.add(top , BorderLayout.NORTH);
        this.add(center , BorderLayout.CENTER);
        this.add(bottom , BorderLayout.SOUTH);
    }


    @Override
    public void init() throws SQLException {
        center.init();
        top.init();
    }

    @Override
    public void end() {
    }
    public Center getCenter(){
        return center;
    }
}
