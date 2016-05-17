/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package aholg.apartment_applier;




import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import javax.mail.MessagingException;

/**
 * Handles new apartment entries and checks if they are new.
 * @author Anton
 */
public class ApartmentHandler {

    private File file;

   ApartmentHandler() {
        file = new File("C:/Users/Anton/Documents/Webscraper bostadsförmedlingen/apartments.txt");
    }
   /**
    * Checks apartment file for old apartments and see if given apartment is new.
    * @param apartments     List of apartments to check if they are new.
    * @return               True if new apartment was found.
    * @throws FileNotFoundException
    * @throws IOException
    * @throws MessagingException 
    */
   boolean checkApartment(Iterator<Apartment> apartments) throws FileNotFoundException, IOException, MessagingException {
        boolean result = false;
        BufferedReader br = new BufferedReader(new FileReader(file));
        try {
        while(apartments.hasNext()){
            Apartment nextAppartment=apartments.next();
            String appString = nextAppartment.toString();
        
            String line = br.readLine();
            while ( line !=null && line.equals(appString)==false) {
                line = br.readLine();
            }
            if (line == null && nextAppartment.getApplyPage()!=null) {
                addApartment(appString);
                new Account().apply(nextAppartment.getApplyPage());
                result = true;
            }
        } 
        }finally {

            br.close();
        }
        
        return result;
    }
/**
 * Adds new apartment to file and sends an email to user.
 * @param apartment     Apartment to add.
 * @throws IOException
 * @throws MessagingException 
 */
   void addApartment(String apartment) throws IOException, MessagingException {
       sendMail s=new sendMail(apartment);
      
        try (PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(file, true)))) {
            out.println(apartment);
        } catch (IOException e) {
            e.printStackTrace();
        }
  
    }
}
