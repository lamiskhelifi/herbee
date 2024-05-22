package application;

import javafx.beans.property.*;

public class herbe {

    private IntegerProperty id;
    private StringProperty emplacement;
    private StringProperty commentBoire;
    private IntegerProperty nombreFois;
    private StringProperty maladie;
    private StringProperty nom;

    public herbe(int id, String emplacement, String commentBoire, int nombreFois, String maladie, String nom) {
        this.id = new SimpleIntegerProperty(id);
        this.emplacement = new SimpleStringProperty(emplacement);
        this.commentBoire = new SimpleStringProperty(commentBoire);
        this.nombreFois = new SimpleIntegerProperty(nombreFois);
        this.maladie = new SimpleStringProperty(maladie);
        this.nom = new SimpleStringProperty(nom);
    }

    // Ajoutez les méthodes d'accès aux propriétés ici

    public IntegerProperty idProperty() {
        return id;
    }

    public StringProperty emplacementProperty() {
        return emplacement;
    }

    public StringProperty commentBoireProperty() {
        return commentBoire;
    }

    public IntegerProperty nombreFoisProperty() {
        return nombreFois;
    }

    public StringProperty maladieProperty() {
        return maladie;
    }

    public StringProperty nomProperty() {
        return nom;
    }
}
