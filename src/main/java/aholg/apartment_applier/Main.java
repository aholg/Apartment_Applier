/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package aholg.apartment_applier;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.concurrent.TimeUnit;
import javax.mail.MessagingException;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPathExpressionException;
import org.xml.sax.SAXException;

/**
 *
 * @author Anton
 */
public class Main {

    private WebScraper ws;
    private static String url="https://bostad.stockholm.se/Lista/";

    public static void main(String[] args) throws IOException, FileNotFoundException, MessagingException, InterruptedException {
        Main main = new Main();
        main.start();

    }

    public void start() throws IOException, FileNotFoundException, MessagingException, InterruptedException  {
        while (true) {
            ws = new WebScraper(url);
            TimeUnit.MINUTES.sleep(5);
        }

    }
}
