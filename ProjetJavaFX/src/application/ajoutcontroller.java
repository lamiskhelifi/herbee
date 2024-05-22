package application;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class ajoutcontroller {

    @FXML
    private TextField maladieField;

    @FXML
    private TextField emplacementField;

    @FXML
    private TextField commentBoireField;

    @FXML
    private TextField nombreFoisField;

    @FXML
    private TextField nomField;

    @FXML
    private Button ajouterButton;

    private Connection connection;

    @FXML
    private void initialize() {
        connection = application.connection.getCn();
    }

    @FXML
    private void ajouterAction() {
        String maladie = maladieField.getText();
        String emplacement = emplacementField.getText();
        String commentBoire = commentBoireField.getText();
        int nombreFois = Integer.parseInt(nombreFoisField.getText());
        String nom = nomField.getText();

        if (maladie.isEmpty() || emplacement.isEmpty() || commentBoire.isEmpty() || nom.isEmpty()) {
            afficherAlerteErreur("Veuillez remplir tous les champs.");
            return;
        }

        String query = "INSERT INTO herbe (maladie, emplacement, commentboire, nombrefois, nom) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, maladie);
            statement.setString(2, emplacement);
            statement.setString(3, commentBoire);
            statement.setInt(4, nombreFois);
            statement.setString(5, nom);

            int rowsAffected = statement.executeUpdate();
            if (rowsAffected > 0) {
                afficherAlerteInfo("La maladie a été ajoutée avec succès.");
                clearFields();
            } else {
                afficherAlerteErreur("Erreur lors de l'ajout de la maladie.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            afficherAlerteErreur("Erreur lors de l'exécution de la requête SQL.");
        }
    }

    private void afficherAlerteErreur(String message) {
        Alert alert = new Alert(AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void afficherAlerteInfo(String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void clearFields() {
        maladieField.clear();
        emplacementField.clear();
        commentBoireField.clear();
        nombreFoisField.clear();
        nomField.clear();
    }
}
