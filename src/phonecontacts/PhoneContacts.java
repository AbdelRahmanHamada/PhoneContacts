/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecontacts;

import java.io.File;
import java.util.List;

/**
 *
 * @author abed
 */
public class PhoneContacts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        ContactFolderProcessing contactFolder = new ContactFolderProcessing(
                new File("path"));
        List<Contact> contacts = contactFolder.getContacts();
        contacts.forEach(contact -> {
            System.out.println(contact.getName() + ":");
            System.out.println("\t" + contact.getPhones());
        });
    }

}
