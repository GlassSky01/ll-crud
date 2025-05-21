package test;

import ciallo.glasssky.jdbc.Main;
import ciallo.glasssky.jdbc.Tools;

import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class Test {
    public static void main(String[] args) {
        JFrame frame = new JFrame();
        Tools.init();
        int w = (int) (Tools.W / 2);
        int h = w;
        int x = (int) ((Tools.W - w) / 2);
        int y = (int) ((Tools.H - h) / 2);
        frame.setBounds(x , y , w , h);



        JMenuBar bar = new JMenuBar();
        JMenu menu = new JMenu("test");
        JMenuItem item = new JMenuItem("hello");
        bar.add(menu);
        menu.add(item);
        item.addActionListener(e->{
            System.out.println("hello");
        });
        frame.setJMenuBar(bar);

        frame.setVisible(true);

        JPopupMenu popupMenu = new JPopupMenu();
        popupMenu.add(new JMenuItem("复制"));
        popupMenu.add(new JMenuItem("粘贴"));
        frame.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseReleased(MouseEvent e) {
                if(e.isPopupTrigger()){
                    popupMenu.show(e.getComponent() , e.getX() , e.getY());
                }
            }
        });
        JOptionPane.showConfirmDialog(frame , "这是一个提示框" , "标题"
        , JOptionPane.PLAIN_MESSAGE);
        System.exit(1);
    }
}
