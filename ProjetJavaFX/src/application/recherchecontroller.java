package application;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class recherchecontroller {

    @FXML
    private TableView<herbe> tableHerbes;

    @FXML
    private TableColumn<herbe, Integer> idCol;

    @FXML
    private TableColumn<herbe, String> emplacementCol;

    @FXML
    private TableColumn<herbe, String> commentBoireCol;

    @FXML
    private TableColumn<herbe, Integer> nombreFoisCol;

    @FXML
    private TableColumn<herbe, String> maladieCol;

    @FXML
    private TableColumn<herbe, String> nomCol;

    @FXML
    private TextField searchField;

    @FXML
    private Button rechercherButton;
    @FXML
    private Button ajoutmaladie;
    @FXML
    private Button comparaisonButton;

    private Connection connection;

    @FXML
    private void initialize() {
        connection = application.connection.getCn();
        configureTableColumns();
    }

    @FXML
    private void rechercherAction() {
        String maladie = searchField.getText();
        List<herbe> herbes = getHerbesByMaladie(maladie);

        if (herbes.isEmpty()) {
            afficherAucunResultat();
        } else {
            afficherListeHerbes(herbes);
        }
    }

    private void configureTableColumns() {
        idCol.setCellValueFactory(cellData -> cellData.getValue().idProperty().asObject());
        emplacementCol.setCellValueFactory(cellData -> cellData.getValue().emplacementProperty());
        commentBoireCol.setCellValueFactory(cellData -> cellData.getValue().commentBoireProperty());
        nombreFoisCol.setCellValueFactory(cellData -> cellData.getValue().nombreFoisProperty().asObject());
        maladieCol.setCellValueFactory(cellData -> cellData.getValue().maladieProperty());
        nomCol.setCellValueFactory(cellData -> cellData.getValue().nomProperty());
    }

    private List<herbe> getHerbesByMaladie(String maladie) {
        List<herbe> herbes = new ArrayList<>();
        String query = "SELECT * FROM herbe WHERE maladie = ?";
        try (PreparedStatement statement = connection.prepareStatement(query)) {
            statement.setString(1, maladie);

            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                int id = resultSet.getInt("id");
                String emplacement = resultSet.getString("emplacement");
                String commentBoire = resultSet.getString("commentboire");
                int nombreFois = resultSet.getInt("nombrefois");
                String nom = resultSet.getString("nom");

                herbe herbe = new herbe(id, emplacement, commentBoire, nombreFois, maladie, nom);
                herbes.add(herbe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Gérer l'erreur lors de l'exécution de la requête SQL
        }

        return herbes;
    }
    @FXML
    private void ajoutmaladieAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ajout.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    @FXML
    private void comparaisonAction() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("comparaison.fxml"));
        Parent root = loader.load();
        Stage stage = new Stage();
        stage.setScene(new Scene(root));
        stage.show();
    }
    private void afficherListeHerbes(List<herbe> herbes) {
        tableHerbes.getItems().setAll(herbes);
    }

    private void afficherAucunResultat() {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle("Aucun résultat");
        alert.setHeaderText(null);
        alert.setContentText("Aucune herbe trouvée pour la maladie donnée.");
        alert.showAndWait();
    }
}
