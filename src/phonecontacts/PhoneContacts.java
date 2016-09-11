/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecontacts;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
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
    private Label addedCount;
    private Label nonAddedCount;
    private Button btnAdd;
    private Button btnRemove;
    private HBox hboxList;

    private FileChooser dirChooser;
    private TextField tfPath;
    private Button btnChoose;

    private HBox hboxPath;
    private VBox vbox;

    private TextArea textArea;
    private Button btnProcess;
    private HBox hboxProcess;
    private Stage primaryStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;

        Scene scene = new Scene(vbox, 600, 400);
        primaryStage.setScene(scene);
        primaryStage.setTitle("Testing");
        primaryStage.show();

    }

    @Override
    public void init() {
        dirChooser = new FileChooser();
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
        added.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
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
        nonAdded.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        nonAdded.setCellFactory(callback);

        addedCount = new Label(0 + "");
        nonAddedCount = new Label(0 + "");

        btnAdd = new Button(">>");
        btnRemove = new Button("<<");

        VBox vbox = new VBox(20, nonAddedCount, btnAdd, btnRemove, addedCount);
        vbox.setAlignment(Pos.CENTER);
        hboxList = new HBox(15, nonAdded, vbox, added);

    }

    private void bindLists() {
        addedList = FXCollections.observableArrayList();
        nonAddedList = FXCollections.observableArrayList();
        added.setItems(addedList);
        nonAdded.setItems(nonAddedList);
    }

    private void initProcessBox() {
        textArea = new TextArea();
        textArea.setWrapText(true);
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

            folder = dirChooser.showOpenDialog(new Stage());
            if (folder != null) {
                tfPath.setText(folder.getPath());
            }

            if (folder != null) {
                ContactFolderProcessing contactFolder = new ContactFolderProcessing(folder);
                List<Contact> contacts = contactFolder.getContacts();
                nonAddedList.setAll(contacts);
            } else {
                textArea.setText("please choose folder directory....");
            }

        });

        btnProcess.setOnAction(e -> {

            StringBuilder sb = new StringBuilder();
            for (Contact c : addedList) {
                List<String> phones = c.getPhones();
                if (c.getPhones().size() > 1) {
                    Stage stage = new Stage();
                    stage.initModality(Modality.WINDOW_MODAL);
                    stage.initOwner(primaryStage);
                    ChoosePhoneDialog ch = new ChoosePhoneDialog(c);
                    ch.start(stage);
                    phones = ch.getPhones();
                }

                List<String> newPhones = new ArrayList<>();
                for (String phone : phones) {
                    newPhones.add(phone);
                    if (!phone.startsWith("059")) {
                        newPhones.remove(phone);
                        if (phone.startsWith("082")) {
                        } else if (phone.startsWith("+97")) {
                            phone = "0" + phone.substring(4);
                            newPhones.add(phone);
                        } else {
                            phone = "059" + phone;
                            newPhones.add(phone);
                        }

                    }
                }
                for (String phone : newPhones) {
                    sb.append(phone).append(";");
                }
            }
            textArea.setText(sb.toString());
        });

        btnAdd.setOnAction(e -> {
            ObservableList<Contact> list = nonAdded.getSelectionModel().getSelectedItems();
            addedList.addAll(list);
            nonAddedList.removeAll(list);
            addedCount.setText(addedList.size() + "");
            nonAddedCount.setText(nonAddedList.size() + "");
        });
        btnRemove.setOnAction(e -> {
            ObservableList<Contact> list = added.getSelectionModel().getSelectedItems();
            nonAddedList.addAll(list);
            addedList.removeAll(list);
            addedCount.setText(addedList.size() + "");
            nonAddedCount.setText(nonAddedList.size() + "");
        });
        nonAdded.setOnMouseClicked(e -> {
            if (e.getClickCount() == 2) {
                addedList.add(nonAdded.getSelectionModel().getSelectedItem());
                nonAddedList.remove(nonAdded.getSelectionModel().getSelectedItem());
                addedCount.setText(addedList.size() + "");
                nonAddedCount.setText(nonAddedList.size() + "");
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
