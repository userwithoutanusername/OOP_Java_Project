package gui;

import data.CD.CD;
import data.CD.CDPouziti;
import data.CD.CDVyrobci;
import data.HudebniNosice;
import data.Kazeta.Kazeta;
import data.Kazeta.KazetaTypCivky;
import data.VinylovaDeska.VinylovaDeska;
import data.VinylovaDeska.VinylovaDeskaRychlost;
import data.VinylovaDeska.VinylovaDeskaVelikost;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;

public class GUIDialog {

    private final Stage stage;
    private final Scene scene;
    private final GridPane gridPane;
    private TextField kapacitaF;
    private TextField rokVyrobyF;
    private TextField vzacnostF;
    private ComboBox<CDVyrobci> vyrobciCB;
    private ComboBox<CDPouziti> pouzitiCB;
    private ComboBox<KazetaTypCivky> typCivkyCB;
    private ComboBox<VinylovaDeskaRychlost> rychlostCB;
    private ComboBox<VinylovaDeskaVelikost> velikostCB;

    HudebniNosice nosic = null;

    public GUIDialog(String typ, int id) {
        stage = new Stage();
        stage.setTitle("Novy nosic");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        zakladniNastaveni(id);
        switch (typ) {
            case "cd" -> cdNastaveni();
            case "kazeta" -> kazetaNastaveni();
            case "vinylova deska" -> vinylNastaveni();
        }
        HBox buttonBox = new HBox();
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> stage.close());
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {
                int kapacita = Integer.parseInt(kapacitaF.getText());
                int rokVyroby = Integer.parseInt(rokVyrobyF.getText());

                switch (typ) {
                    case "cd" -> {

                            CDVyrobci vyrobci = vyrobciCB.getValue();
                            CDPouziti pouziti = pouzitiCB.getValue();
                            nosic = new CD(id, kapacita, rokVyroby, vyrobci, pouziti);

                    }
                    case "kazeta" -> {

                            int vzacnost = Integer.parseInt(vzacnostF.getText());
                            KazetaTypCivky typCivky = typCivkyCB.getValue();
                            nosic = new Kazeta(id, kapacita, rokVyroby, vzacnost, typCivky);

                    }
                    case "vinylova deska" -> {

                            VinylovaDeskaRychlost rychlost = rychlostCB.getValue();
                            VinylovaDeskaVelikost velikost = velikostCB.getValue();
                            nosic = new VinylovaDeska(id, kapacita, rokVyroby, velikost, rychlost);

                    }
                }
                stage.close();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
            }
        });
        buttonBox.getChildren().addAll(cancelButton, okButton);
        buttonBox.setSpacing(10);
        gridPane.add(buttonBox, 1, 5);

        scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.showAndWait();
    }

    public GUIDialog(HudebniNosice nos) {
        stage = new Stage();
        stage.setTitle("Edit nosicu");
        gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);
        gridPane.setPadding(new javafx.geometry.Insets(10, 10, 10, 10));
        zakladniNastaveni(nos);
        switch (nos.getTyp()) {
            case CD -> cdNastaveni((CD) nos);
            case KAZETA -> kazetaNastaveni((Kazeta) nos);
            case VINYLOVA_DESKA -> vinylNastaveni((VinylovaDeska) nos);
        }
        HBox buttonBox = new HBox();
        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(event -> stage.close());
        Button okButton = new Button("OK");
        okButton.setOnAction(event -> {
            try {

                int kapacita = Integer.parseInt(kapacitaF.getText());
                int rokVyroby = Integer.parseInt(rokVyrobyF.getText());

                switch (nos.getTyp()) {
                    case CD -> {

                            CDVyrobci vyrobci = vyrobciCB.getValue();
                            CDPouziti pouziti = pouzitiCB.getValue();
                            nosic = new CD(nos.getId(), kapacita, rokVyroby, vyrobci, pouziti);

                    }
                    case KAZETA -> {

                            int vzacnost = Integer.parseInt(vzacnostF.getText());
                            KazetaTypCivky typCivky = typCivkyCB.getValue();
                            nosic = new Kazeta(nos.getId(), kapacita, rokVyroby, vzacnost, typCivky);

                    }
                    case VINYLOVA_DESKA -> {

                            VinylovaDeskaRychlost rychlost = rychlostCB.getValue();
                            VinylovaDeskaVelikost velikost = velikostCB.getValue();
                            nosic = new VinylovaDeska(nos.getId(), kapacita, rokVyroby, velikost, rychlost);

                    }
                }
                stage.close();
            } catch (NumberFormatException e) {
                Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage());
                alert.showAndWait();
                event.consume();
            }
        });
        buttonBox.getChildren().addAll(cancelButton, okButton);
        buttonBox.setSpacing(10);
        gridPane.add(buttonBox, 1, 5);

        scene = new Scene(gridPane);
        stage.setScene(scene);
        stage.showAndWait();
    }

    private void zakladniNastaveni(int id){
        Label idLabel = new Label("ID:");
        gridPane.add(idLabel, 0, 0);

        Label idField = new Label(String.valueOf(id));
        gridPane.add(idField, 1, 0);

        Label kapacitaLabel = new Label("Kapacita:");
        gridPane.add(kapacitaLabel, 0, 1);

        kapacitaF = new TextField();
        gridPane.add(kapacitaF, 1, 1);

        Label rokVyrobyLabel = new Label("Rok vyroby: ");
        gridPane.add(rokVyrobyLabel, 0, 2);

        rokVyrobyF = new TextField();
        gridPane.add(rokVyrobyF, 1, 2);
    }

    private void zakladniNastaveni(HudebniNosice nosic){
        Label idLabel = new Label("ID:");
        gridPane.add(idLabel, 0, 0);

        Label idField = new Label(String.valueOf(nosic.getId()));
        gridPane.add(idField, 1, 0);

        Label kapacitaLabel = new Label("Kapacita:");
        gridPane.add(kapacitaLabel, 0, 1);

        kapacitaF = new TextField();
        kapacitaF.setText(String.valueOf(nosic.getKapacita()));
        gridPane.add(kapacitaF, 1, 1);

        Label rokVyrobyLabel = new Label("Rok vyroby: ");
        gridPane.add(rokVyrobyLabel, 0, 2);

        rokVyrobyF = new TextField();
        rokVyrobyF.setText(String.valueOf(nosic.getRokVyroby()));
        gridPane.add(rokVyrobyF, 1, 2);
    }

    private void cdNastaveni() {
        Label vyrobciLabel = new Label("Vyrobci:");
        gridPane.add(vyrobciLabel, 0, 3);

        vyrobciCB = new ComboBox<>();
        vyrobciCB.getItems().addAll(CDVyrobci.values());
        vyrobciCB.getSelectionModel().selectFirst();
        gridPane.add(vyrobciCB, 1, 3);

        Label pouzitiLabel = new Label("Pouziti: ");
        gridPane.add(pouzitiLabel, 0, 4);

        pouzitiCB = new ComboBox<>();
        pouzitiCB.getItems().addAll(CDPouziti.values());
        pouzitiCB.getSelectionModel().selectFirst();
        gridPane.add(pouzitiCB, 1, 4);
    }

    private void cdNastaveni(CD cd) {
        Label vyrobciLabel = new Label("Vyrobci:");
        gridPane.add(vyrobciLabel, 0, 3);

        vyrobciCB = new ComboBox<>();
        vyrobciCB.getItems().addAll(CDVyrobci.values());
        vyrobciCB.getSelectionModel().select(cd.getVyrobec());
        gridPane.add(vyrobciCB, 1, 3);

        Label pouzitiLabel = new Label("Pouziti: ");
        gridPane.add(pouzitiLabel, 0, 4);

        pouzitiCB = new ComboBox<>();
        pouzitiCB.getItems().addAll(CDPouziti.values());
        pouzitiCB.getSelectionModel().select(cd.getPouziti());
        gridPane.add(pouzitiCB, 1, 4);
    }



    private void kazetaNastaveni() {
        Label typCivkyLabel = new Label("Typ civky:");
        gridPane.add(typCivkyLabel, 0, 3);

        typCivkyCB = new ComboBox<>();
        typCivkyCB.getItems().addAll(KazetaTypCivky.values());
        typCivkyCB.getSelectionModel().selectFirst();
        gridPane.add(typCivkyCB, 1, 3);

        Label vzacnostLabel = new Label("Vzacnost: ");
        gridPane.add(vzacnostLabel, 0, 4);

        vzacnostF = new TextField();
        gridPane.add(vzacnostF, 1, 4);
    }

    private void kazetaNastaveni(Kazeta kazeta) {
        Label typCivkyLabel = new Label("Typ civky:");
        gridPane.add(typCivkyLabel, 0, 3);

        typCivkyCB = new ComboBox<>();
        typCivkyCB.getItems().addAll(KazetaTypCivky.values());
        typCivkyCB.getSelectionModel().select(kazeta.getTypCivky());
        gridPane.add(typCivkyCB, 1, 3);

        Label vzacnostLabel = new Label("Vzacnost: ");
        gridPane.add(vzacnostLabel, 0, 4);

        vzacnostF = new TextField();
        vzacnostF.setText(String.valueOf(kazeta.getVzacnost()));
        gridPane.add(vzacnostF, 1, 4);
    }


    private void vinylNastaveni() {
        Label rychlostLabel = new Label("Rychlost:");
        gridPane.add(rychlostLabel, 0, 3);

        rychlostCB = new ComboBox<>();
        rychlostCB.getItems().addAll(VinylovaDeskaRychlost.values());
        rychlostCB.getSelectionModel().selectFirst();
        gridPane.add(rychlostCB, 1, 3);

        Label velikostLabel = new Label("Velikost:");
        gridPane.add(velikostLabel, 0, 4);

        velikostCB = new ComboBox<>();
        velikostCB.getItems().addAll(VinylovaDeskaVelikost.values());
        velikostCB.getSelectionModel().selectFirst();
        gridPane.add(velikostCB, 1, 4);
    }

    private void vinylNastaveni(VinylovaDeska vinyl) {
        Label rychlostLabel = new Label("Rychlost:");
        gridPane.add(rychlostLabel, 0, 3);

        rychlostCB = new ComboBox<>();
        rychlostCB.getItems().addAll(VinylovaDeskaRychlost.values());
        rychlostCB.getSelectionModel().select(vinyl.getRychlost());
        gridPane.add(rychlostCB, 1, 3);

        Label velikostLabel = new Label("Velikost:");
        gridPane.add(velikostLabel, 0, 4);

        velikostCB = new ComboBox<>();
        velikostCB.getItems().addAll(VinylovaDeskaVelikost.values());
        velikostCB.getSelectionModel().select(vinyl.getVelikost());
        gridPane.add(velikostCB, 1, 4);
    }

    public HudebniNosice getNosic() {
        return nosic;
    }
}
