/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aholg.apartment_applier;

import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import javax.mail.MessagingException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 * Webscraper for the agency website. Retrieves the "first come first serve" ads
 * and collects relevant data.
 *
 * @author Anton
 */
class WebScraper {

    private String outputFile;
    private Document document;
    private Account account;

    WebScraper(String url) throws IOException, FileNotFoundException, MessagingException {

        outputFile = "C:\\Users\\Anton\\Documents\\Webscraper bostadsförmedlingen\\bostadsformedlingenhtml.txt";

        File webPage = new File("C:\\Users\\Anton\\Documents\\Webscraper bostadsförmedlingen\\bst exempel html.html");

        document = Jsoup.parse(webPage, "UTF-8");

        account = new Account();
        scan();
    }

    /**
     * Scans for new apartments and checks if they are new.
     *
     * @throws IOException
     * @throws FileNotFoundException
     * @throws MessagingException
     */
    private void scan() throws IOException, FileNotFoundException, MessagingException {
        ArrayList<Apartment> apartmentList = scanForNewApartment();
        ApartmentHandler appHandler = new ApartmentHandler();
        if (apartmentList.size() > 0) {
            appHandler.checkApartment(apartmentList.iterator());
        }
    }

    /**
     * Scans for apartments and collects relevant data about every new
     * apartment.
     *
     * @return List of found apartments.
     * @throws IOException
     * @throws FileNotFoundException
     * @throws MessagingException
     */
    ArrayList<Apartment> scanForNewApartment() throws IOException {
        ArrayList<Apartment> apartmentList = new ArrayList();
        Iterator<Element> houseContent = document.select("span:contains(Bostadssnabben)").iterator();
        while (houseContent.hasNext()) {
            Apartment apartment = new Apartment();
            Elements houseParent = houseContent.next().parent().siblingElements();
            apartment.setCity(houseParent.get(0).text());
            String address = houseParent.get(2).getAllElements().get(1).text();
            String apartmentPage = houseParent.get(2).getAllElements().get(1).attr("href");

            apartment.setApartmentPage(apartmentPage);
            apartment.setAddress(address);
            String applyPage = scanForApplyPage(apartmentPage);
            apartment.setApplyPage(applyPage);
            apartment.setLivingSpace(houseParent.get(5).text());
            apartment.setRent(houseParent.get(6).text());
            apartment.setDate(houseParent.get(7).text());

            apartmentList.add(apartment);

        }

        return apartmentList;
    }
 /**
     * Gets the url for the apply page of the apartment.
     * @param adress        Adress to scan.
     * @return
     * @throws IOException 
     */
    private String scanForApplyPage(String apartmentPage) throws IOException {
        String href;
        WebClient webClient = account.getAuthenticatedClient();
        HtmlPage aPage = webClient.getPage("https://bostad.stockholm.se" + apartmentPage);
        
        HtmlAnchor applyPage = aPage.getFirstByXPath("//div[@class='container']//a[@class='btn large right']");
        if (applyPage != null) {
            href = applyPage.getAttribute("href");
            return href;
        }
        return null;
    }

}
