package ciallo.glasssky.jdbc.database;

import java.sql.*;
import java.util.ArrayList;

public class DbOperates {
    private static Statement state;
    private static ResultSet result;
    private static String password;
    private static String user;

    public static void setState(Statement state)  {
        DbOperates.state = state;
    }
    public static void setInfo(String user, String password) {
        DbOperates.user = user;
        DbOperates.password = password;
    }
    public static boolean login(String user, String password) throws SQLException {
        String sql = String.format("select count(user) from glassskydb.users where user = '%s' and password = '%s'", user, password);
        result = state.executeQuery(sql);
        result.next();
        return result.getInt(1) == 1;
    }
    public static boolean register(String name, String user, String password) throws SQLException {
        String sql = String.format("select count(user) from glassskydb.users where user = '%s';", user);
        result = state.executeQuery(sql);
        result.next();
        if (result.getInt(1) == 0) {
            sql = String.format("insert into glassskydb.users value('%s' , '%s' , '%s');", name, user, password);
            state.execute(sql);
            return true;
        }
        return false;
    }
    public static ArrayList<Double> getDoubleResults(String sql) throws SQLException {
        result = state.executeQuery(sql);
        ArrayList<Double> ans = new ArrayList<>();
        while (result.next()) {
            ans.add(result.getDouble(1));
        }
        return ans;
    }
    public static ArrayList<String> getStringResults(String sql) throws SQLException {
        result = state.executeQuery(sql);
        ArrayList<String> ans = new ArrayList<>();
        while (result.next()) {
            ans.add(result.getString(1));
        }
        return ans;
    }
    public static String getUser() {
        return user;
    }
    public static void execute(String sql) throws SQLException {
        state.execute(sql);
    }
    public static ArrayList<Object[]> query(String sql , String[] args) throws SQLException {
        ArrayList<Object[]> arr = new ArrayList<>();
        result = state.executeQuery(sql);
        while(result.next())
        {
            Object[] obj = new Object[args.length];
            for(int i = 0 ; i < obj.length ; i ++)
            {
                String op = args[i];
                if("int".equalsIgnoreCase(op))
                {
                    obj[i] = result.getInt(i + 1);
                }
                else if("string".equalsIgnoreCase(op)){
                    obj[i] = result.getString(i + 1);
                }
                else{
                    obj[i] = result.getDouble(i + 1);
                }
            }
            arr.add(obj);
        }
        return arr;
    }
    public static ArrayList<Object[]> queryAllBilling() throws SQLException {
        ArrayList<Object[]> arr = new ArrayList<>();
        result = state.executeQuery(String.format(
                "select * from glassskydb.billing where user = '%s';"
                , user
        ));
        while(result.next())
        {
            Object[] sin = new Object[6];
            sin[0] = result.getDouble(1);
            for(int i = 1 ; i < sin.length ; i ++){
                sin[i] = result.getString(i + 1);
            }
            arr.add(sin);
        }
        return arr;
    }
    public static ArrayList<Object[]> queryAllAccount() throws SQLException {
        ArrayList<Object[]> arr = new ArrayList<>();
        result = state.executeQuery(String.format(
                "select account , money from glassskydb.account where user = '%s' and enable = 1;"
                , user
        ));
        while(result.next())
        {
            Object[] sin = new Object[2];
            sin[0] = result.getString(1);
            sin[1] = result.getDouble(2);
            arr.add(sin);
        }
        return arr;
    }
}
