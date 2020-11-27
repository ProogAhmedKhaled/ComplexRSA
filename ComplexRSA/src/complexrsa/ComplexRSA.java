/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complexrsa;

import java.math.BigInteger;
import java.util.Scanner;

/**
 *
 * @author pc
 */
public class ComplexRSA {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here

        System.out.println("/******** WELCOME IN COMPLEX ENC & DEC APP ************/");

        // create object from CRSA 'Complex RSA' and pass the 2 prime numbers 
        CRSA r = new CRSA(BigInteger.valueOf(17), BigInteger.valueOf(11));

        // create scanner input to help me take input from user
        Scanner input = new Scanner(System.in);
        System.out.print("Please Enter Plain Text : ");
        // take the plain text from user
        String plain = input.next();
        System.out.println("The Plain Text : " + plain);
        // encrypte the plain text and return codes of each letter to use it latter to decrypte it
        // 108 250 300 222 
        String[] codeLetter = r.encrypt(plain).split(" ");
        int index = 0;
        String decryptedMsg = "";
        for (int i = 1; i < codeLetter.length; i++) {
            // decrypte the cipher text and call dycrypte function and pass 
            index = r.decrypt(codeLetter[i]);
            // get charater from Alphapet array using getElemnt method by pass the index of this char and append it into message
            decryptedMsg += "" + r.getElement(index);
        }
        // print the message after decrypted
        System.out.println("Plain Text : " + decryptedMsg);
    }

}
