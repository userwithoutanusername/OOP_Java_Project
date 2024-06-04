package gui;

import data.HudebniNosice;
import data.TypHudebnihoNosice;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import spravce.OvladaniException;
import spravce.SpravaNosice;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;

import java.util.*;

public class GUI extends Application {

    private final SpravaNosice seznam = new SpravaNosice();
    private final ListView<String> list = newListView(14, 43, 537, 317);
    ComboBox<String> comboBoxNovyNosic;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        AnchorPane root = new AnchorPane();
        ObservableList<Node> children = root.getChildren();
        Scene scene = new Scene(root, 650, 420);
        primaryStage.setTitle("Hudebni noscie - Kopytin Makar");
        primaryStage.setResizable(false);
        primaryStage.setScene(scene);

        children.add(newButton("Generuj", 14, 378, generujData));
        children.add(newButton("Uloz", 80, 378, ulozText));
        children.add(newButton("Nacti", 130, 378, nactiText));
        comboBoxNovyNosic = newComboBox("Novy", 180, 378, TypHudebnihoNosice.values(), zadejNovyNosic);
        children.add(comboBoxNovyNosic);
        children.add(newButton("Zrus",325,378, zrusSeznam));
        ComboBox<String> comboBoxFiltr = newComboBox("Filtr", 370, 378, TypHudebnihoNosice.values(), nastavFiltr);
        comboBoxFiltr.getItems().add(0,"No filtr");
        children.add(comboBoxFiltr);
        children.add(newButton("Zalohuj", 520, 378, handlerZalohuj));
        children.add(newButton("Obnov", 590, 378, handlerObnov));

        children.add(newButton("Prvni", 560, 43, nastavPrvni));
        children.add(newButton("Dalsi", 560, 73, nastavDalsi));
        children.add(newButton("Predchozi", 560, 103, nastavPredchozi));
        children.add(newButton("Posledni", 560, 133, nastavPosledni));

        children.add(newButton("Edituj", 560, 170, editNosic));
        children.add(newButton("Vyjmi", 560, 200, vyjmiNosic));
        children.add(newButton("Zobraz", 560, 230, zobrazNosicu));
        children.add(newButton("Clear", 560, 260, clearList));

        children.add(list);
        primaryStage.show();
    }



    private static Button newButton(String text, int x, int y, EventHandler<ActionEvent> handler) {
        Button button = new Button(text);
        button.setOnAction(handler);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }

    private ListView<String> newListView(int x1, int y1, int x2, int y2) {
        ListView<String> listView = new ListView<>();
        listView.setLayoutX(x1);
        listView.setLayoutY(y1);
        listView.setPrefSize(x2, y2);
        return listView;
    }

    private ComboBox<String> newComboBox(String text, int x, int y, TypHudebnihoNosice[] items, EventHandler<ActionEvent> handler) {
        ComboBox<String> comboBox = new ComboBox<>();
        comboBox.setPromptText(text);
        comboBox.setLayoutX(x);
        comboBox.setLayoutY(y);
        for (TypHudebnihoNosice item : items) {
            comboBox.getItems().add(item.toString());
        }
        comboBox.setOnAction(handler);
        return comboBox;
    }

    private final EventHandler<ActionEvent> generujData = actionEvent -> {
        TextInputDialog textInputDialog = new TextInputDialog();
        textInputDialog.setTitle("Generovani nosicu");
        textInputDialog.setHeaderText("Počet nosicu:");
        Optional<String> pocet = textInputDialog.showAndWait();
        if (pocet.isPresent()) {
            try {
                int pocetHN = Integer.parseInt(textInputDialog.getEditor().getText());
                seznam.generuj(pocetHN);
                obnovList();
            } catch (NumberFormatException e) {
                showError("Zadejte prosím číslo!");
            }
        }
    };

    private final EventHandler<ActionEvent> ulozText = actionEvent -> {
        try {
            seznam.uloztext();
        } catch (OvladaniException e) {
            throw new RuntimeException(e);
        }
        showInfo("Ukladani probehlo uspesne!");
    };

    private final EventHandler<ActionEvent> nactiText = actionEvent -> {
        try {
            seznam.nactitext();
            obnovList();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> zadejNovyNosic = actionEvent -> {
        String typ = ((ComboBox) actionEvent.getSource()).getSelectionModel().getSelectedItem().toString();
        int id = seznam.generujId();
        try {
            GUIDialog dialog = new GUIDialog(typ, id);
            seznam.novy(dialog.getNosic());
            obnovList();

        } catch (OvladaniException e) {
//            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> zrusSeznam = actionEvent -> {
        seznam.zrus();
        list.getItems().clear();
    };

    private final EventHandler<ActionEvent> nastavFiltr = actionEvent -> {
        list.getItems().clear();
        try {
            String typ = ((ComboBox) actionEvent.getSource()).getSelectionModel().getSelectedItem().toString();
            switch (typ) {
                case "No filtr" -> obnovList();
                case "cd" -> {
                    for (HudebniNosice nosice : seznam.vypis()) {
                        if (nosice.getTyp().equals(TypHudebnihoNosice.CD)) {
                            list.getItems().add(nosice.toString());
                        }
                    }
                }
                case "kazeta" -> {
                    for (HudebniNosice nosice : seznam.vypis()) {
                        if (nosice.getTyp().equals(TypHudebnihoNosice.KAZETA)) {
                            list.getItems().add(nosice.toString());
                        }
                    }
                }
                case "vinylova deska" -> {
                    for (HudebniNosice nosice : seznam.vypis()) {
                        if (nosice.getTyp().equals(TypHudebnihoNosice.VINYLOVA_DESKA)) {
                            list.getItems().add(nosice.toString());
                        }
                    }
                }
            }
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> handlerZalohuj = actionEvent -> {
        try {
            seznam.zalohuj();
            showInfo("Zalohovani probehlo uspesne!");

        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> handlerObnov = actionEvent -> {
        try {
            seznam.obnov();
            obnovList();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> nastavPredchozi = actionEvent -> {
        try {
            seznam.predchozi();
            list.getSelectionModel().selectPrevious();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> nastavPrvni = actionEvent -> {
        try {
            seznam.prvni();
            list.getSelectionModel().selectFirst();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> nastavDalsi = actionEvent -> {
        try {
            seznam.dalsi();
            list.getSelectionModel().selectNext();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> nastavPosledni = actionEvent -> {
        try {
            seznam.posledni();
            list.getSelectionModel().selectLast();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> editNosic = actionEvent -> {
        String selectedItem = list.getSelectionModel().getSelectedItem();
        if (selectedItem == null) {
            showError("Neni vybran zadny nosic");
            return;
        }
        int commaIndex = selectedItem.indexOf(",");
        String id = selectedItem.substring(0, commaIndex);
        try {
            HudebniNosice nosice = seznam.najdi(Integer.parseInt(id));

            GUIDialog dialog = new GUIDialog(nosice);

            if (dialog.getNosic() != null) {
                seznam.update(Integer.parseInt(id), dialog.getNosic());
                obnovList();
            }

        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> vyjmiNosic = actionEvent -> {
        String temp = list.getSelectionModel().getSelectedItem();
        if (temp == null) {
            showError("Neni vybran zadny nosic");
            return;
        }
        int commaIndex = temp.indexOf(",");
        String id = temp.substring(0, commaIndex);
        try {
            seznam.odeber(Integer.parseInt(id));
            obnovList();
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    };

    private final EventHandler<ActionEvent> zobrazNosicu = actionEvent -> {
        obnovList();
    };

    private final EventHandler<ActionEvent> clearList = actionEvent -> {
        list.getItems().clear();
    };

    private void obnovList() {
        try {
            list.getItems().clear();
            for (HudebniNosice nosice : seznam.vypis()) {
                list.getItems().add(nosice.toString());
            }
        } catch (OvladaniException e) {
            showError(e.getMessage());
        }
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Chyba");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Info");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
