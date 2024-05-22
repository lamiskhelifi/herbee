package application;
	import java.sql.Connection;
	import java.sql.PreparedStatement;
	import java.sql.ResultSet;
	import java.sql.SQLException;

	import javafx.fxml.FXML;
	import javafx.scene.control.Button;
	import javafx.scene.control.Label;
	import javafx.scene.control.TextField;

	public class comparaisoncontroller {
	    
	    @FXML
	    private TextField medicamentField;
	    
	    @FXML
	    private Button comparerButton;
	    
	    @FXML
	    private Label equivalentLabel;
	    
	    private Connection connection;
	    
	    @FXML
	    private void initialize() {
	        connection = application.connection.getCn();
	    }
	    
	    @FXML
	    private void comparerAction() {
	        String medicament = medicamentField.getText();
	        String equivalent = getEquivalent(medicament);
	        
	        equivalentLabel.setText("Équivalent : " + equivalent);
	    }
	    
	    private String getEquivalent(String medicament) {
	        String equivalent = "";
	        String query = "SELECT equivalent FROM maladie WHERE medicament = ?";
	        try (PreparedStatement statement = connection.prepareStatement(query)) {
	            statement.setString(1, medicament);
	            
	            ResultSet resultSet = statement.executeQuery();
	            if (resultSet.next()) {
	                equivalent = resultSet.getString("equivalent");
	            } else {
	                equivalent = "Aucun équivalent trouvé.";
	            }
	        } catch (SQLException e) {
	            e.printStackTrace();
	            // Gérer l'erreur lors de l'exécution de la requête SQL
	        }
	        
	        return equivalent;
	    }
	}


