package ciallo.glasssky.jdbc;

import ciallo.glasssky.jdbc.mainWindow.pages.page0.Bottom.Bottom;

import javax.swing.*;
import java.awt.*;

public class Tools {
    public static double W , H ;
    private static final DefaultListCellRenderer center = new DefaultListCellRenderer() {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value,
                                                      int index, boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            setHorizontalAlignment(SwingConstants.CENTER);
            return this;
        }
    };
    public static void init(){
        Dimension screen = Toolkit.getDefaultToolkit().getScreenSize();
        W = screen.getWidth();
        H = screen.getHeight();
    }
    public static Font getFont(double size , double ratio){
        return new Font("宋体" , Font.PLAIN , (int)(size / ratio));
    }

    public static void appear(long time , Component a){
        a.setVisible(true);
        new Timer(1000, e -> {
            a.setVisible(false);
            ((Timer) e.getSource()).stop();
        }).start();
    }
    public static void add(JComponent a , JComponent[] b){
        for(JComponent i : b)
            a.add(i);
    }
    public static void setFont(Font font , JComponent[] b)
    {
        for(JComponent i : b)
            i.setFont(font);
    }
    public static DefaultListCellRenderer getCenter(){
        return center;
    }
    public static void setWarning(Component fa , String warning , String title){
        JOptionPane.showConfirmDialog(fa , warning , title , JOptionPane.DEFAULT_OPTION);
    }
    public static String toHtml(String s){
        return "<html>" + s + "</html>";
    }

}
