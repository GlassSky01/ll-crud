package ciallo.glasssky.jdbc.mainWindow.pages.page3;

import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;
import ciallo.glasssky.jdbc.database.DbOperates;

import javax.swing.*;
import javax.tools.Tool;
import java.awt.*;
import java.sql.SQLException;

public class Left extends JPanel {
    public Left(Font font){
        this.setLayout(new GridLayout(6 , 1));
        JLabel name, user, oldPassword , newPassword;
        JButton rename , reSetPassword;
        name = new JLabel("昵称: " , SwingConstants.RIGHT);
        user = new JLabel("账户: ", SwingConstants.RIGHT);
        oldPassword = new JLabel("旧密码: ", SwingConstants.RIGHT);
        newPassword = new JLabel("新密码: ", SwingConstants.RIGHT);
        rename = new JButton("改名 ");
        reSetPassword = new JButton("重置密码 ");
        rename.setHorizontalAlignment(SwingConstants.RIGHT);
        reSetPassword.setHorizontalAlignment(SwingConstants.RIGHT);
        JComponent[] comps= {name , rename ,user , oldPassword , newPassword , reSetPassword };
        Tools.setFont(font , comps);
        Tools.add(this , comps);
        addSetName(rename);
        addSetPassword(reSetPassword);
    }
    private void addSetName(JButton button){
        button.addActionListener(e->{
            Right right = ((Page3) this.getParent()).getRight();

            try {
                if(right.name.getText().isEmpty())
                    throw new SQLException();
                DbOperates.execute(String.format(
                        "update glassskydb.users set name = '%s' where user = '%s';"
                        , right.name.getText() , DbOperates.getUser()
                ));
                right.rename.setText("修改成功");
                Tools.appear(1000 , right.rename);
            } catch (SQLException ex) {
                right.rename.setText("名字不能为空");
                Tools.appear(1000 , right.rename);
            }
        });
    }
    private void addSetPassword(JButton button){
        button.addActionListener(e->{
            Right right = ((Page3)this.getParent()).getRight();
            JTextField oldPassword = right.oldPassword;
            JTextField newPassword = right.newPassword;
            try {
                if(newPassword.getText().isEmpty())
                    throw new SQLException();
                DbOperates.execute(String.format(
                        "update glassskydb.users set password = '%s' where user = '%s' and password = '%s';"
                        , newPassword.getText() , DbOperates.getUser() , oldPassword.getText()
                ));
                oldPassword.setText("");
                newPassword.setText("");
                right.reSetPassword.setText("修改成功");
                Tools.appear(1000 , right.reSetPassword);
            } catch (SQLException ex) {
                right.reSetPassword.setText("密码不能为空");
                Tools.appear(1000 , right.reSetPassword);
            }
        });
    }
}
