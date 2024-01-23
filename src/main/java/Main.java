import model.DatabaseManagement;
import view.DatabaseView;

public class Main{

    public static void main(String[] args) {
        DatabaseManagement management = new DatabaseManagement();
        new DatabaseView(management);
    }

}