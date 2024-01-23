package view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.DataSource;

public class DataSourcePanel extends ComponentPanel {

    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    private static final String LOGIN_TEXT_BUTTON = "Login";

    private DatabaseView dbView;

    private DataSource dataSource;

    public DataSourcePanel(DatabaseView dbView, DataSource dataSource){
        this.dbView = dbView;
        this.dataSource = dataSource;
        init();
    }

    @Override
    protected void init(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);
        
        add(new JLabel("Driver: " + dataSource.getDriver()));
        add(new JLabel("URL: " + dataSource.getUrl()));
        add(new JLabel("Login: " + dataSource.getLogin()));
        add(new JLabel("Password: " + dataSource.getPassword()));
        add(getLoginButton());

        
    }

    private JButton getLoginButton(){
        JButton button = new JButton(LOGIN_TEXT_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dbView.setDataSource(dataSource);
            }
        });
        return button;
    }

}
