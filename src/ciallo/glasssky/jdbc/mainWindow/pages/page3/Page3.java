package ciallo.glasssky.jdbc.mainWindow.pages.page3;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Main;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.Dbs;
import ciallo.glasssky.jdbc.mainWindow.pages.Page;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Page3 extends Page implements Format {
    private Left left;
    private Right right ;
    public Page3(JPanel window ,int h) {
        super(window , h);
//        this.setBorder(BorderFactory.createLineBorder(Color.RED , 10));
//        this.setBackground(Color.black);
        this.setLayout(new BorderLayout());
        Font font = Tools.getFont(this.h , 12);
        left = new Left(font);
        right = new Right(font);
        this.add( left, BorderLayout.WEST);
        this.add(right, BorderLayout.CENTER);

        JButton quit = new JButton("退出登录");
        quit.setFont(font);
        this.add(quit , BorderLayout.SOUTH);

        quit.addActionListener(e->{
            Main.switchover(Main.window , Main.appLogin);
        });

    }

    public Right getRight(){
        return right;
    }
    @Override
    public void init() throws SQLException {
        right.init();
    }

    @Override
    public void end() {
    }
}
