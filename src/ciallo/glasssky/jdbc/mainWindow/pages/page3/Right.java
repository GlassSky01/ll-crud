package ciallo.glasssky.jdbc.mainWindow.pages.page3;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class Right extends JPanel implements Format {
    JLabel user , reSetPassword , rename;
    JTextField name , oldPassword , newPassword;
    public Right(Font font){
        this.setLayout(new GridLayout(6 , 1));
        name = new JTextField();
        user = new JLabel();
        oldPassword = new JTextField();
        newPassword = new JPasswordField();
        rename = new JLabel();
        reSetPassword = new JLabel();
        JComponent[] comps = {name , rename ,user , oldPassword , newPassword , reSetPassword };
        Tools.setFont(font , comps);
        Tools.add(this , comps);
    }

    @Override
    public void init() throws SQLException {
        name.setText(DbOperates.getStringResults(String.format(
                "select name from glassskydb.users where user = '%s';", DbOperates.getUser()
        )).get(0));
        user.setText(DbOperates.getUser());
    }

    @Override
    public void end() {

    }
}
