package view;

import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

import model.DataSource;

public class LoadingScene extends JPanel {

    private DatabaseView databaseView;

    protected LoadingScene(DatabaseView databaseView){
        this.databaseView = databaseView;
        init();
    }

    private void init(){
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        add(new JLabel("Veuillez selectionnez la base voulu"));
        add(getDatabasePanel());
    }

    private JPanel getDatabasePanel(){
        JPanel panel = new JPanel();
        DataSource[] dts = DataSource.getDataSources();

        for (int i = 0; i < dts.length; i++) {
            panel.add(new DataSourcePanel(databaseView, dts[i]));
        }

        panel.add(new DatabaseRegisterPanel(databaseView));

        return panel;
    }
}