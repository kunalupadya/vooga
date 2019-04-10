/*
package GameAuthoringEnvironment.AuthoringScreen.main.PropertySettings;

import GameAuthoringEnvironment.AuthoringScreen.main.Editors.EnemiesEditor;
import javafx.event.ActionEvent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;



public class EnemyPropertySetting{

    private Stage popupwindow;
    private MenuButton gameTypeMenu;
    private Label healthLabel, nameLabel;
    private TextField nameTf, healthTf;
    private EnemiesEditor myEnemiesEditor;

    public EnemyPropertySetting(double width, double height, EnemiesEditor enemiesEditor) {
        popupwindow = new Stage();
        myEnemiesEditor = enemiesEditor;
        popupwindow.initModality(Modality.APPLICATION_MODAL);
        popupwindow.setTitle("EnemyConfig Property Setting");

        nameLabel = new Label("Arsenal Name");
        nameTf = new TextField();

        healthLabel = new Label("Arsenal Price");
        healthTf = new TextField();

        MenuButton projectile = new MenuButton("Choose EnemyConfig");
        MenuButton projectileSpeed = new MenuButton("Choose EnemyConfig Speed");
        MenuButton health = new MenuButton("Choose EnemyConfig Health");



        Button saveButton = new Button("Save properties");

        //TODO When this button pressed, check if all required fields are filled
        saveButton.setOnAction(this::handleSaveArsenalProperty);

        VBox layout= new VBox();
        layout.getChildren().addAll(projectile, projectileSpeed, health, saveButton);
        initiate(width, height, popupwindow, layout);
    }

    private void handleSaveArsenalProperty(ActionEvent event) {
        Enemy newEnemy = new Enemy(nameTf.getText());
        myEnemiesEditor.addEnemies(newEnemy);
        myEnemiesEditor.getSourceView().getItems().add(newEnemy);
        popupwindow.close();

    }


    private void initiate(double width, double height, Stage popupwindow, VBox layout) {
        Scene scene= new Scene(layout, width/2, height/2);
        popupwindow.setScene(scene);
        popupwindow.showAndWait();

    }

}
*/