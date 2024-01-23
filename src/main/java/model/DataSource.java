package model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class DataSource {

    private static final String SEPARATOR = File.separator;

    private static final String PATH = "res" + SEPARATOR + "config" + SEPARATOR;
    
    private static final String DRIVER_PROPERTY = "driver";
    
    private static final String URL_PROPERTY = "url";
    
    private static final String LOGIN_PROPERTY = "login";
    
    private static final String PASSWORD_PROPERTY = "password";

    private String driver;
    private String url;
    private String login;
    private String password;

    public DataSource(String driver, String url, String login, String password){
        this.driver = driver;
        this.url = url;
        this.login = login;
        this.password = password;
    }

    private DataSource(String configPath) throws IOException {
        loadProperties(configPath);
    }

    public String getDriver() {
        return driver;
    }
    
    public String getUrl() {
        return url;
    }

    public String getLogin() {
        return login;
    }

    public String getPassword() {
        return password;
    }

    protected Connection getConnection() throws ClassNotFoundException, SQLException {
        loadDriver();
        return DriverManager.getConnection(url, login, password);
    }

    private void loadProperties(String configPath) throws IOException {
        Properties p = new Properties();
        p.load(new FileInputStream(configPath));

        driver = p.getProperty(DRIVER_PROPERTY);
        if(driver == null)
            throw new IOException("Le driver n'a pas pu être charger");
            
        url = p.getProperty(URL_PROPERTY);
        if(url == null)
            throw new IOException("L'url n'a pas pu être charger");

        login = p.getProperty(LOGIN_PROPERTY);
        if(login == null)
            throw new IOException("Le nom d'utilisateur n'a pas pu être charger");

        password = p.getProperty(PASSWORD_PROPERTY);
        if(password == null)
            throw new IOException("Le mot de passe n'a pas pu être charger");
    }
    
    private void loadDriver() throws ClassNotFoundException{
        Class.forName(driver);
    }

    public static DataSource[] getDataSources(){
        List<DataSource> dts = new ArrayList<>();
        File directory = new File(PATH);

        if(!directory.exists()){
            directory.mkdir();
        }
        
        File[] files = directory.listFiles();
        
        for (int i = 0; i < files.length; i++) {
            String path = files[i].getPath();

            try {
                dts.add(new DataSource(path));
            } catch (Exception e) {
                System.err.println("Impossible de charger la configuration suivante: " + e.getMessage() + " - " + path);
            }
        }
        
        return dts.toArray(new DataSource[dts.size()]);
    }

}
