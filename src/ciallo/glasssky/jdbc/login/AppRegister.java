package ciallo.glasssky.jdbc.login;

import ciallo.glasssky.jdbc.database.DbOperates;
import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Main;
import ciallo.glasssky.jdbc.Tools;

import javax.swing.*;

public class AppRegister extends Login implements Format {
    private JTextField name;
    public AppRegister(){
        super();
        JLabel label0 = new JLabel("昵称:");
        name = new JTextField(columns);
        label0.setFont(font);
        name.setFont(font);

        JButton back = new JButton("返回");
        back.setFont(font2);


        this.add(label0 , 0);
        this.add(name , 1);
        this.add(back , 6);
        login.setText("注册");


        back.addActionListener(e->{
            name.setText("");
            Main.switchover(this , Main.appLogin);
        });
        login.addActionListener(e->{
            if(name.getText().isEmpty() || user.getText().isEmpty() || password.getText().isEmpty()){
                warning.setText("请输入完整信息");
                Tools.appear(1000 , warning);
                return;
            }
            try{
                if(DbOperates.register(name.getText() , user.getText() , password.getText())){
                    warning.setText("注册成功!");
                    Tools.appear(1000 , warning);
                }
                else{
                    warning.setText("该账号已被注册");
                    Tools.appear(1000 , warning);
                }
            }
            catch(Exception ee)
            {
                ee.printStackTrace();
            }
        });
    }

}
