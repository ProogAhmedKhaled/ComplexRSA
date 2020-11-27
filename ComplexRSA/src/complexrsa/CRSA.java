/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package complexrsa;

import java.math.BigInteger;
import java.util.Random;

/**
 *
 * @author pc
 */
public class CRSA {

    private BigInteger p;//first prime number
    private BigInteger q;//second prime number
    private BigInteger N;
    private BigInteger ϕ;//ϕ(n)euller
    private BigInteger e;//encryption key  Public Key
    private BigInteger d;//decryption key  Private Key
    private int complexKey; // Complex Key to make RSA more Complex to be hacked

    // convert the String to array of char
    // this our alphabit array
    public char[] Alphap = "abcdefghijklmnopqrstuvwxyz".toCharArray();

    // the constructor that take 2 prime number 
    // BigInteger  class is used for mathematical operation which involves very big integer calculations 
    //that are outside the limit of all available primitive data types
    public CRSA(BigInteger p, BigInteger q) {
        this.p = p;
        this.q = q;
        System.out.println("------ Key Generation  -----");
        N = p.multiply(q); // N = ( p * q )
        // display N
        System.out.println("N : " + N);

        //calculate ϕ(n) = (p – 1) * (q – 1)
        ϕ = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        // display the euller ϕ
        System.out.println("Euler:  " + ϕ);

        // e is the public key is randomly chooses or genertated 
        // BigInteger.valueOf convert the integer number to Biginteger to ba able to make big math operation
        // e must be prime and between 1 and ϕ euller
        // we use randomPrime method to generate random prime number
        // if we want to fix the value of e like e = 7 we raplace line ϕ.intValue() by 7
        e = BigInteger.valueOf(randomPrime(ϕ.intValue()));
        System.out.println("E Public Key : " + e);

        // calculate d by inverse e mod ϕ euler 
        d = e.modInverse(ϕ);
        System.out.println("D Private Key : " + d);

        // this is the new addition
        // create random complex prime key between 1 and the multplication of e , d
        complexKey = randomPrime((e.intValue() * d.intValue()));
        System.out.println("Complex Key : " + complexKey);
    }

    public int randomPrime(int euler) {
        int num = 0;
        Random rand = new Random();
        // generate a random number
        num = rand.nextInt(euler) + 1;
        // check if the random number is prime
        while (!isPrime(num)) {
            num = rand.nextInt(euler) + 1;
        }
        // rreturn the number if is prime
        return num;
    }

    // method to check if the number is prime or not
    public boolean isPrime(int number) {
        // loop and check if the number prime 
        // number prime is the number who divided only by self
        for (int i = 3; i < number; i++) {
            // check if reminder == 0 that mean this number can be divided by number another itself so return false ( not prime )
            if (number % i == 0) {
                return false;
            }
        }
        return true;
    }

    // method used to get index of element from alph array to use it in Encrypion and Decryption process
    public int getElementIndex(char ch) {
        // index of the char
        int index = 0;
        // loop and check if is exist or not
        for (int i = 0; i < Alphap.length; i++) {
            if (Alphap[i] == ch) {
                index = i;
                break;
            }
        }
        return index;
    }

    // get char element by index
    public char getElement(int index) {
        return Alphap[index];
    }

    public String encrypt(String message) {

        String letterCode = "";
        // message that store the encryption msg
        String encrypte_Msg = "";
        int i = 0;
        // هنلف على المسج حرف حرف ونخزنه في متغير اسمه
        // c

        while (i < message.length()) {
            // save char in c varaiable
            char c = message.charAt(i);
            // get the index of var by pass it to getElementindex(c) and this method return the index
            int index = getElementIndex(c);

            // C(P)c = (p^e mod N ) + complexKey --> this is the new equation of encryption text
            // (BigInteger.valueOf(index)) --> P (رقم الحرف في المصفوفه)
            // modPow(e, N) تحسب البور والمود
            // intValue()--> convert result of the operation into integer
            // complexKey --> randomly genertated between 1 and ( e*d)
            int operation = ((BigInteger.valueOf(index)).modPow(e, N).intValue()) + complexKey;
            // save the code of char to use it latter
            letterCode += " " + operation;
            // display the code of each char after Encryption
            System.out.print(c + " : " + operation + " , ");
            // get element from alpha Array of eache code 
            // note that this alpha array contain 26 letter so what do we do if the code more than 26 
            // so we use mod(BigInteger.valueOf(26)
            encrypte_Msg += "" + getElement(BigInteger.valueOf(operation).mod(BigInteger.valueOf(26)).intValue());

            i++;
        }
        System.out.println("\nThe Cipher Text : " + encrypte_Msg);
        return letterCode;
    }

    // this method used to decrypt the message
    public int decrypt(String message) {
        // P = (C-complexCode) ^d mod N
        int index = Integer.parseInt(message) - complexKey;
        return (BigInteger.valueOf(index).modPow(d, N).intValue());
    }
}
