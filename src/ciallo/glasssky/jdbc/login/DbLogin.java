package ciallo.glasssky.jdbc.login;

import ciallo.glasssky.jdbc.database.Dbs;
import ciallo.glasssky.jdbc.Main;
import ciallo.glasssky.jdbc.Tools;

import java.io.*;
import java.util.Properties;

public class DbLogin extends Login {
    public DbLogin() {
        super();
        this.setTitle("登录MYSQL...");
        login.setText("登录MYSQL");
        login.addActionListener(e -> {
            try {
                Dbs.setInfo(user.getText(), password.getText());
                Dbs.getConnection();
                Dbs.initDb();
                saveInfo(user.getText(), password.getText());

                Main.switchover(Main.dbLogin,Main.appLogin );
                this.setVisible(false);
            } catch (Exception ee) {
                Tools.appear(1000 , warning);
            }
        });
    }
    private void saveInfo(String user, String password) throws IOException {
        OutputStream os = new FileOutputStream("./config/DbData.properties");
        Properties prop = new Properties();
        prop.setProperty("user" , user);
        prop.setProperty("password" , password);
        prop.store(os , "数据库配置");
    }
}
