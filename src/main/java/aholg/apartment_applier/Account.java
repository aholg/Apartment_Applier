
package aholg.apartment_applier;

import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.CookieManager;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlAnchor;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import java.io.IOException;

/**
 * Account class to be used for applying for apartments and logging in to bostadsformedlingen.
 * @author Anton
 */
class Account {

    Account() {

    }

    /**
     * Logs in to website and applies for apartment by clicking the "I agree to
     * terms" box and clicks on apply button.
     *
     * @param url Url to retrieve and use for applying.
     * @throws IOException
     */
    void apply(String url) throws IOException {

        WebClient webClient = getAuthenticatedClient();

        HtmlPage aPage = webClient.getPage(url);
        HtmlInput checkbox = aPage.getFirstByXPath("//input[@name='cbTerms']");
        checkbox.click();
        HtmlInput btn = aPage.getFirstByXPath("//input[@value='Anmäl intresse']");
        HtmlPage returnPage = btn.click();
    }
/**
     * Logs into the agency website.
     * @return  WebClient - authenticated client to be used for applying and for access to full website content.
     * @throws IOException 
     */
    WebClient getAuthenticatedClient() throws IOException {
        java.util.logging.Logger.getLogger("com.gargoylesoftware").setLevel(java.util.logging.Level.OFF);

        try (final WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
            String loginUrl = "https://bostad.stockholm.se/Minasidor/login/";
            HtmlPage page = (HtmlPage) webClient.getPage(loginUrl);
            CookieManager cM = new CookieManager();
            cM.setCookiesEnabled(true);
            webClient.setCookieManager(cM);

            HtmlPage nextpage = null;
            //enter your own account details
            String usrname = "";
            String pwd = "";

            final HtmlForm form = page.getFirstByXPath("//form[@action='https://login001.stockholm.se/siteminderagent/forms/login.fcc']");
            HtmlInput submitButton = form.getInputByValue("Logga in");
            HtmlAnchor enableButton = form.getFirstByXPath("//a[@id='btn-password']");
            enableButton.click();

            final HtmlTextInput username = form.getFirstByXPath("//input[@id='txtEmail']");
            final HtmlPasswordInput password = form.getInputByName("PASSWORD");
            username.setValueAttribute(usrname);
            password.setValueAttribute(pwd);

            try {
                nextpage = submitButton.click();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return webClient;
        }
    }
}
