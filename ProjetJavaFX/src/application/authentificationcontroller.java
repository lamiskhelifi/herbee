package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;


import javafx.scene.Parent;


import javafx.scene.Scene;



import javafx.scene.control.Alert;


import javafx.scene.control.Button;



import javafx.scene.control.PasswordField;



import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class authentificationcontroller implements Initializable {

    private Connection connection;
    private PreparedStatement statement;

    @FXML
    private TextField tfGmail;

    @FXML
    private PasswordField tfPassword;

    @FXML
    private Button connectBtn;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        // Initialisation du contrôleur
        connection =application.connection.getCn(); // Remplacez MyDatabaseConnection.getConnection() par votre méthode de connexion à la base de données
        
        if (connection != null) {
            System.out.println("Connexion à la base de données réussie.");
        } else {
            System.out.println("Échec de la connexion à la base de données.");
        }
    }


    @FXML
    private void handleConnectBtn() {
        String gmail = tfGmail.getText();
        String password = tfPassword.getText();

        if (isValidCredentials(gmail, password)) {
            openRecherchePage();
        } else {
            showAlert("Erreur d'authentification", "Identifiants incorrects.");
        }
    }

    private boolean isValidCredentials(String gmail, String password) {
        try {
            // Requête pour vérifier les identifiants dans la base de données
            String query = "SELECT * FROM user WHERE gmail = ? AND password = ?";
            statement = connection.prepareStatement(query);
            statement.setString(1, gmail);
            statement.setString(2, password);
            ResultSet resultSet = statement.executeQuery();

            // Vérification des résultats de la requête
            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    private void openRecherchePage() {
        try {
            // Chargement de la vue "recherche.fxml"
            Parent rechercheView = FXMLLoader.load(getClass().getResource("recherche.fxml"));
            Scene rechercheScene = new Scene(rechercheView);

            // Récupération de la scène actuelle et du stage
            Stage currentStage = (Stage) connectBtn.getScene().getWindow();
            currentStage.setScene(rechercheScene);
            currentStage.show();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
