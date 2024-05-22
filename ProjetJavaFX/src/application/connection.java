package application;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class connection {
    private static String login = "root";
    private static String password = "";
    private static String url = "jdbc:mysql://localhost/projetjavafx";
    public static Connection cn;

    static {
        try {
            // Étape 1 : Charger le driver de la base de données cible
        	Class.forName("com.mysql.cj.jdbc.Driver");
            // Étape 2 : Authentification auprès de la base de données
            cn = DriverManager.getConnection(url, login, password);
        } catch (ClassNotFoundException ex) {
            System.out.println("Impossible de charger le driver");
            ex.printStackTrace();
        } catch (SQLException ex) {
            System.out.println("Erreur de connexion");
            ex.printStackTrace();
        }
    }

    public static Connection getCn() {
        return cn;
    }
}


