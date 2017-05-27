/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecontacts;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author abed
 */
public class ContactFolderProcessing {

    private List<File> contactsFiles;
    private List<Contact> contacts;

    public ContactFolderProcessing(File folder) {
        this.contactsFiles = Arrays.asList(folder.listFiles());
        this.contacts = new ArrayList<>();
        fillContacts();
    }

    private void fillContacts() {
        contactsFiles.stream().forEach(file -> {
            contacts.add(filter(file));
        });
    }

    private Contact filter(File file) {
        String name = getContactName(file);
        Contact contact = new Contact(name);
        try {

            Scanner scanner = new Scanner(file);

            List<String> phones = new ArrayList<>();
            while (scanner.hasNextLine()) {
                String line;
                if ((line = scanner.nextLine()).matches(".*(TEL|CELL).*")) {
                    phones.add(line.split(":")[1]);
                }
            }
            contact.setPhones(phones);

        } catch (FileNotFoundException ex) {
            Logger.getLogger(ContactFolderProcessing.class.getName()).log(Level.SEVERE, null, ex);
        }
        return contact;
    }

    private String getContactName(File file) {
        String fileName = file.getName();
        return fileName.substring(0, fileName.indexOf(".vcf"));
    }

    public List<Contact> getContacts() {
        return contacts;
    }

}
