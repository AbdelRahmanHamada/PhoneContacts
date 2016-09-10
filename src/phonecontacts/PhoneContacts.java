/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecontacts;

import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

/**
 *
 * @author abed
 */
public class PhoneContacts extends Application {

    private ListView<Contact> added;
    private ListView<Contact> nonAdded;
    private HBox hboxList;

    private DirectoryChooser dirChooser;
    private TextField tfPath;
    private Button btnChoose;

    private HBox hboxPath;
    private VBox vbox;

    private TextArea textArea;
    private Button btnProcess;
    private HBox hboxProcess;

    @Override
    public void start(Stage primaryStage) throws Exception {
        dirChooser = new DirectoryChooser();

        tfPath = new TextField();
        tfPath.setMinWidth(450);
        btnChoose = new Button("choose");
        hboxPath = new HBox(10, tfPath, btnChoose);

        added = new ListView<>();
        nonAdded = new ListView<>();
        hboxList = new HBox(15, nonAdded, added);

        textArea = new TextArea();
        btnProcess = new Button("start");
        btnProcess.setMinWidth(80);
        hboxProcess = new HBox(10, textArea, btnProcess);

        vbox = new VBox(15, hboxPath, hboxList, hboxProcess);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15));

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Testing");
        primaryStage.show();

    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }

    private void test() {
        ContactFolderProcessing contactFolder = new ContactFolderProcessing(
                new File("path"));
        List<Contact> contacts = contactFolder.getContacts();
        contacts.forEach(contact -> {
            System.out.println(contact.getName() + ":");
            System.out.println("\t" + contact.getPhones());
        });
    }
}
