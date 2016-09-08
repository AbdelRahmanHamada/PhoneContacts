/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package phonecontacts;

/**
 *
 * @author abed
 */
public class PhoneContacts {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String s = "TEL;CELL:0598332547";
        System.out.println(s.matches(".*(TEL|CELL).*"));

    }

}
