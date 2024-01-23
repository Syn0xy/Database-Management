package view;

import java.awt.Point;

import javax.swing.JPanel;

import model.DataSource;
import model.DatabaseManagement;
import view.util.Observer;
import view.util.Subject;

public class DatabaseView extends View implements Observer {

    private static final int WIDTH = (int)(SCREEN_WIDTH * (2.0 / 3.0));

    private static final int HEIGHT = (int)(SCREEN_HEIGHT * (2.0 / 3.0));

    private static final String TITLE = "Database Management";

    private DatabaseManagement management;

    private LoadingScene loadingScene;
    
    private ManagementScene managementScene;

    public DatabaseView(DatabaseManagement management){
        this.management = management;
        management.attach(this);
        init(WIDTH, HEIGHT);
    }

    @Override
    public String title() {
        return TITLE;
    }

    @Override
    public Point position() {
        return center();
    }

    @Override
    public void view() {        
        loadingScene = new LoadingScene(this);
        managementScene = new ManagementScene(this, management);

        add(loadingScene);
    }

    protected void setLoadingScene(){
        setScene(loadingScene);
    }

    protected void setManagementScene(){
        setScene(managementScene);
        managementScene.reload();
    }

    private void setScene(JPanel panel){
        remove(loadingScene);
        remove(managementScene);
        add(panel);
        repaint();
    }

    protected void setDataSource(DataSource dataSource){
        management.setDataSource(dataSource);
        setManagementScene();
    }

    @Override
    public void repaint(){
        super.repaint();
        validate();
    }
    
    @Override
    public void update(Subject subj) {
        repaint();
    }

    @Override
    public void update(Subject subj, Object data) {}
    
}
