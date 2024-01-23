package view;

import java.awt.Dimension;
import java.awt.Point;
import java.awt.Toolkit;

import javax.swing.JFrame;

public abstract class View extends JFrame {

    protected static final Dimension SCREEN_SIZE = Toolkit.getDefaultToolkit().getScreenSize();

    protected static final int SCREEN_WIDTH = (int)SCREEN_SIZE.getWidth();

    protected static final int SCREEN_HEIGHT = (int)SCREEN_SIZE.getHeight();

    public abstract String title();
    public abstract Point position();
    public abstract void view();

    public void init(int width, int height){
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(width, height);
        setTitle(title());
        view();
        setLocation(position());
        setVisible(true);
    }

    protected Point center(){
        return new Point((SCREEN_WIDTH - getWidth()) / 2, (SCREEN_HEIGHT - getHeight()) / 2);
    }

}
