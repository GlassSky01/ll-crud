package ciallo.glasssky.jdbc.mainWindow.pages;

import ciallo.glasssky.jdbc.Format;

import javax.swing.*;
import java.awt.*;

public abstract class Page extends JPanel implements Format {
    protected int h;
    protected JPanel window;
    public Page(JPanel window ,int h) {
        this.h = h;
        this.window = window;
    }
}
