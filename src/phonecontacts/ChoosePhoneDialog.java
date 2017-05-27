/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecontacts;

import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 *
 * @author abed
 */
public class ChoosePhoneDialog extends Application {

    private Contact contact;

    public ChoosePhoneDialog(Contact c) {
        this.contact = c;
    }
    private List<String> phones = new ArrayList<>();

    @Override
    public void start(Stage primaryStage) {

        VBox vbox = new VBox(10, new Label(contact.getName()));
        List<CheckBox> list = new ArrayList<>();
        for (String phone : contact.getPhones()) {
            CheckBox cb = new CheckBox(phone);
            list.add(cb);
            vbox.getChildren().add(cb);
        }
        Button btn = new Button("ok");

        btn.setOnAction(e -> {
            for (CheckBox cb : list) {
                if (cb.isSelected()) {
                    phones.add(cb.getText());
                }
            }
            primaryStage.hide();
            primaryStage.close();
        });

        vbox.getChildren().add(btn);

        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(10));

        Scene scene = new Scene(vbox, 250, 300);
        primaryStage.setTitle("Choose Phones");
        primaryStage.setScene(scene);
        primaryStage.showAndWait();

    }

    public List<String> getPhones() {
        return phones;
    }
}
