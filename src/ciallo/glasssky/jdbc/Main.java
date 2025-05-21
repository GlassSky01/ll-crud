package ciallo.glasssky.jdbc;

import ciallo.glasssky.jdbc.database.Dbs;
import ciallo.glasssky.jdbc.login.AppLogin;
import ciallo.glasssky.jdbc.login.AppRegister;
import ciallo.glasssky.jdbc.login.DbLogin;
import ciallo.glasssky.jdbc.mainWindow.Window;

import javax.swing.*;
import java.io.*;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static Format dbLogin , window , appLogin , appRegister ;
    public static JMenuBar bar;
    public static void main(String[] args) {
        Tools.init();
        dbLogin = new DbLogin();
        window = new Window();
        appLogin = new AppLogin();
        appRegister = new AppRegister();
        File file = new File("./config/DbData.properties");
        if (file.exists()) {
            try (InputStream is = new FileInputStream(file)){
                Properties prop = new Properties();
                prop.load(is);
                String user = prop.getProperty("user");
                String password = prop.getProperty("password");
                Dbs.setInfo(user, password);
                try {
                    Dbs.getConnection();
                    Dbs.initDb();
                    switchover(appLogin , appLogin);
                } catch (Exception e) {
                    switchover(dbLogin , dbLogin);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                new File("./config").mkdir();
                file.createNewFile();
                Dbs.initDb();

            } catch (Exception e) {
                e.printStackTrace();
            }
            switchover(dbLogin , dbLogin);
        }

    }
    public static void switchover(Format a , Format b){
        if(a != null)
            a.end();

        try {
            b.init();
        } catch (SQLException e) {
        }
    }

}
