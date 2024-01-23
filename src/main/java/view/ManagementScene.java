package view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.TextArea;
import java.awt.TextComponent;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;

import model.DatabaseManagement;
import model.Table;

public class ManagementScene extends JPanel {

    private static final String BACK_TEXT_BUTTON = "Back";

    private static final String REQUEST_TEXT_BUTTON = "Execute request";

    private static final String RELOAD_TEXT_BUTTON = "Reload";

    private static final Color ERROR_COLOR = Color.RED;

    private DatabaseView databaseView;

    private DatabaseManagement management;

    private JPanel tablesPanel;

    private TablePanel currentTablePanel;
    
    private JTable requestExit;

    private TextComponent requestField;

    private TextComponent errorText;

    protected ManagementScene(DatabaseView databaseView, DatabaseManagement management){
        this.databaseView = databaseView;
        this.management = management;
        init();
    }

    private void init(){
        setLayout(new BorderLayout());

        tablesPanel = new JPanel();
        currentTablePanel = new TablePanel();
        requestExit = new JTable();
        requestField = new TextArea();
        errorText = new TextArea();

        tablesPanel.setLayout(new BoxLayout(tablesPanel, BoxLayout.Y_AXIS));

        errorText.setEditable(false);
        errorText.setForeground(ERROR_COLOR);

        JPanel requestPanel = new JPanel();
        requestPanel.setLayout(new GridLayout());
        
        requestPanel.add(requestField);
        requestPanel.add(getRequestButton());
        requestPanel.add(getReloadButton());
        requestPanel.add(getBackButton());

        add(new JScrollPane(tablesPanel), BorderLayout.WEST);
        add(currentTablePanel, BorderLayout.CENTER);
        add(requestExit, BorderLayout.EAST);
        add(errorText, BorderLayout.EAST);
        add(requestPanel, BorderLayout.SOUTH);
    }

    protected void reload(){
        management.reload();
        Table[] tables = management.getTables();

        tablesPanel.removeAll();

        for (int i = 0; i < tables.length; i++) {
            tablesPanel.add(getTableButton(tables[i]));
        }

        repaint();
        validate();
    }

    private JButton getTableButton(Table table){
        JButton button = new JButton(table.getName());
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                currentTablePanel.setTable(table);
            }
        });
        return button;
    }

    private void setRequestExit(List<List<Object>> results){
        Object[][] datas = new Object[results.size()][];

        for (int i = 0; i < datas.length; i++) {
            datas[i] = results.get(i).toArray(new Object[results.get(i).size()]);
            System.out.println("i:" + i + " - " + datas[i]);
        }

        remove(requestExit);
        add(new JTable(datas, new Object[]{ "" }));
    }

    private void resetErrorText(){
        errorText.setEnabled(false);
    }

    private void setErrorText(String error){
        errorText.setText(error);
        errorText.setEnabled(true);
        repaint();
    }

    private JButton getRequestButton(){
        JButton button = new JButton(REQUEST_TEXT_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                resetErrorText();
                String request = requestField.getText();
                if(request != null && !request.isEmpty()){
                    try {
                        setRequestExit(management.executeRequest(request));
                        requestField.setText(new String());
                        reload();
                    } catch (Exception e) {
                        setErrorText("Request error: " + e.getMessage());
                    }
                }
            }
        });
        return button;
    }

    private JButton getReloadButton(){
        JButton button = new JButton(RELOAD_TEXT_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                reload();
            }
        });
        return button;
    }

    private JButton getBackButton(){
        JButton button = new JButton(BACK_TEXT_BUTTON);
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent event) {
                databaseView.setLoadingScene();
            }
        });
        return button;
    }
}