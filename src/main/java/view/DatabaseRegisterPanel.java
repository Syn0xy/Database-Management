package view;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;

import model.DataSource;

public class DatabaseRegisterPanel extends ComponentPanel {

    private static final Color BACKGROUND_COLOR = Color.LIGHT_GRAY;

    private static final String REGISTER_TEXT_BUTTON = "Register";

    private DatabaseView dbView;

    private TextField driverField;
    private TextField urlField;
    private TextField loginField;
    private TextField passwordField;

    public DatabaseRegisterPanel(DatabaseView dbView){
        this.dbView = dbView;
        init();
    }

    @Override
    protected void init(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setBackground(BACKGROUND_COLOR);

        driverField = new TextField();
        urlField = new TextField();
        loginField = new TextField();
        passwordField = new TextField();

        add(new JLabel("Driver:"));
        add(driverField);
        add(new JLabel("URL:"));
        add(urlField);
        add(new JLabel("Login:"));
        add(loginField);
        add(new JLabel("Password:"));
        add(passwordField);
        add(getRegisterButton());
    }

    private JButton getRegisterButton(){
        JButton button = new JButton(REGISTER_TEXT_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                DataSource ds = new DataSource(driverField.getText(), urlField.getText(), loginField.getText(), passwordField.getText());
                dbView.setDataSource(ds);
            }
        });
        return button;
    }
}
