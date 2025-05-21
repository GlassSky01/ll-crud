package ciallo.glasssky.jdbc.login;
import ciallo.glasssky.jdbc.Format;
import ciallo.glasssky.jdbc.Tools;

import javax.swing.*;
import java.awt.*;

public class Login extends JFrame implements Format {
    protected JButton login;
    protected JLabel warning;
    protected JTextField user , password ;
    protected double size;
    protected Font font , font2;
    protected int columns = 10;
    public Login(){
        size = Tools.H / 3;
        initWindow();

        font = Tools.getFont(size , 10);
        JLabel label1 = new JLabel("账号:");
        user = new JTextField(columns);
        JLabel label2 = new JLabel("密码:");
        password = new JPasswordField(columns);
        label1.setFont(font);
        user.setFont(font);
        label2.setFont(font);
        password.setFont(font);
        this.add(label1);
        this.add(user);
        this.add(label2);
        this.add(password);


        font2  = Tools.getFont(size , 7);
        login = new JButton();
        login.setFont(font2);
        this.add(login);

        warning = new JLabel(" 账号或密码错误 ");
        warning.setFont(font);
        warning.setVisible(false);
        this.add(warning);
    }
    protected void initWindow(){
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        int x = (int) ((Tools.W - size) / 2);
        int y = (int) ((Tools.H - size) / 2);
        int w = (int) size;
        int h = (int) size;
        this.setBounds(x , y , w , h);
        this.setLayout(new FlowLayout());
    }
    @Override
    public void init() {
        this.setVisible(true);
        user.setText("");
        password.setText("");
    }

    @Override
    public void end() {
        this.setVisible(false);
    }
}
