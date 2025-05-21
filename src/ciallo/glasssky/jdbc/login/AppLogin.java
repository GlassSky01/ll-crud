package ciallo.glasssky.jdbc.login;
import ciallo.glasssky.jdbc.database.DbOperates;
import ciallo.glasssky.jdbc.database.Dbs;
import ciallo.glasssky.jdbc.Main;
import ciallo.glasssky.jdbc.Tools;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class AppLogin extends Login{
    private JButton register;
    public AppLogin(){
        super();
        this.setTitle("登录管理系统...");
        login.setText("登录");
        register = new JButton("注册账号");
        Font font = Tools.getFont(size , 11);
        login.setFont(font);
        register.setFont(font);
        this.add(register , 5);

        login.addActionListener(e->{
            if(user.getText().isEmpty() || password.getText().isEmpty()){
                warning.setText("请输入完整信息");
                Tools.appear(1000 , warning);
                return ;
            }
            try {
                if(DbOperates.login(user.getText() , password.getText()))
                {
                    DbOperates.setInfo(user.getText() , password.getText());
                    Main.switchover(this , Main.window);

                }
                else{
                    warning.setText("账号或密码错误");
                    Tools.appear(1000 , warning);
                }
            } catch (SQLException ex) {
            }
        });
        register.addActionListener(e->{
            Main.switchover( this , Main.appRegister);
        });

        JButton quitAndClearDb = new JButton("清空数据库并退出");
        quitAndClearDb.setFont(font);
        setQuitAndClearDb(quitAndClearDb);
        this.add(quitAndClearDb , 6);
    }
    private void setQuitAndClearDb(JButton button){
        button.addActionListener(e->{

            Tools.setWarning(this,  "数据库已清空,正在结束进程......" , "");
            new Timer(1000 , ee->{
                System.exit(1);
            }).start();
            try {
                DbOperates.execute("drop database if exists glassskydb");
            } catch (SQLException ex) {
            }
            System.exit(1);
        });
    }
}
