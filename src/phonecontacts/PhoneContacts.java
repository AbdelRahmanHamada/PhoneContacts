/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecontacts;

import java.io.File;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.Stage;
import javafx.util.Callback;

/**
 *
 * @author abed
 */
public class PhoneContacts extends Application {

    private File folder;
    private ObservableList<Contact> addedList;
    private ObservableList<Contact> nonAddedList;

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
        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Testing");
        primaryStage.show();

    }

    @Override
    public void init() {
        dirChooser = new DirectoryChooser();
        initPathBox();
        initListBox();
        bindLists();
        initProcessBox();
        initContainer();
        initActions();
    }

    private void initPathBox() {
        tfPath = new TextField();
        tfPath.setMinWidth(450);
        btnChoose = new Button("choose");
        hboxPath = new HBox(10, tfPath, btnChoose);

    }

    private void initListBox() {
        added = new ListView<>();
        Callback callback = param -> new ListCell<Contact>() {
            @Override
            protected void updateItem(Contact item, boolean empty) {
                super.updateItem(item, empty);

                if (empty || item == null || item.getName() == null) {
                    setText(null);
                } else {
                    setText(item.getName());
                }
            }
        };
        added.setCellFactory(callback);
        nonAdded = new ListView<>();
        nonAdded.setCellFactory(callback);
        hboxList = new HBox(15, nonAdded, added);

    }

    private void bindLists() {
        addedList = FXCollections.observableArrayList();
        nonAddedList = FXCollections.observableArrayList();
        added.setItems(addedList);
        nonAdded.setItems(nonAddedList);
    }

    private void initProcessBox() {
        textArea = new TextArea();
        btnProcess = new Button("start");
        btnProcess.setMinWidth(80);
        hboxProcess = new HBox(10, textArea, btnProcess);

    }

    public void initContainer() {
        vbox = new VBox(15, hboxPath, hboxList, hboxProcess);
        vbox.setAlignment(Pos.CENTER);
        vbox.setPadding(new Insets(15));
    }

    private void initActions() {
        btnChoose.setOnAction(e -> {
            folder = dirChooser.showDialog(new Stage());
            tfPath.setText(folder.getPath());
        });

        btnProcess.setOnAction(e -> {
            if (folder != null) {
                ContactFolderProcessing contactFolder = new ContactFolderProcessing(folder);
                List<Contact> contacts = contactFolder.getContacts();
                nonAddedList.setAll(contacts);
            } else {
                textArea.setText("please choose folder directory....");
            }
        });
        nonAdded.setOnMouseClicked(e -> {
            if (e.getClickCount() > 1) {
                nonAddedList.remove(nonAdded.getSelectionModel().getSelectedItem());
                addedList.add(nonAdded.getSelectionModel().getSelectedItem());
            }
        });

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
