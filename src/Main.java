import JSON.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.Proxy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.support.ui.Select;

import java.io.*;

import java.net.URL;
import java.nio.charset.Charset;

import java.nio.charset.Charset;
import java.util.*;

public class Main
{

    private static String readAll(Reader rd) throws IOException {
        StringBuilder sb = new StringBuilder();
        int cp;
        while ((cp = rd.read()) != -1) {
            sb.append((char) cp);
        }
        return sb.toString();
    }

    public static JSONObject readJsonFromUrl(String url) throws IOException, JSONException {
        InputStream is = new URL(url).openStream();
        try {
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, Charset.forName("UTF-8")));
            String jsonText = readAll(rd);
            JSONObject json = new JSONObject(jsonText);
            return json;
        } finally {
            is.close();
        }
    }

    public static void main(String [] args) throws IOException
    {

        System.setProperty("webdriver.gecko.driver","src/geckodriver.exe");
        //JSONObject json = readJsonFromUrl("http://api.scraperapi.com?key=4ad134b8ea5289a77e057a609e5dbc70&url=http://httpbin.org/ip");
        JSONObject json = readJsonFromUrl("http://pubproxy.com/api/proxy");
        //System.out.println("\n\n" + json.get("ip"));
        System.out.println(json);

        String PROXY = json.getJSONArray("data").getJSONObject(0).getString("ipPort");
        PROXY = "12.175.211.121:59669";
        //getJSONObject("pageInfo").getString("pageName");
        //String PROXY = json.getString("origin");
        System.out.println(PROXY);


//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy(PROXY).setFtpProxy(PROXY).setSslProxy(PROXY);
//        ChromeOptions chromeOptions = ChromeOptions();
//        chromeOptions.addArguments('--proxy-server=%s')
//        ChromeOptions options = new ChromeOptions();
//        //Proxy proxy = new Proxy();
//        //proxy.setHttpProxy(PROXY);
//        options.setProxy(proxy);
//        WebDriver driver = new ChromeDriver(options);

//        Proxy proxy = new Proxy();
//        proxy.setHttpProxy(PROXY);
//        proxy.setSslProxy(PROXY);
//        proxy.setFtpProxy(PROXY);
//        proxy.setProxyType(Proxy.ProxyType.MANUAL);
//        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
//        capabilities.setCapability("proxy", proxy);
//        ChromeOptions options = new ChromeOptions();
//        options.addArguments("start-maximized","ignore-certificate-errors");
//        capabilities.setCapability(ChromeOptions.CAPABILITY, options);

        File file = new File("src/ids.txt");

        WebDriver driver = new FirefoxDriver();
        driver.get("https://www.sslproxies.org/");

        WebElement Proxies = ((FirefoxDriver) driver).findElementById("proxylisttable");
        List<WebElement> prox1 = Proxies.findElements(By.className("odd"));
        List<String> proxies = new ArrayList<>();
        for(WebElement element : prox1){
            //System.out.println(element.getText());
            String[] ip = element.getText().split(" ");
            proxies.add(ip[0]+":"+ip[1]);
        }

        for(String element : proxies){
            System.out.println(element);
        }

        for(String element : proxies){
            try {
                element = "216.80.1.233:48758";
                Proxy proxy = new Proxy();
                proxy.setHttpProxy(element);
                proxy.setSslProxy(element);
                proxy.setFtpProxy(element);
                proxy.setProxyType(Proxy.ProxyType.MANUAL);
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("proxy", proxy);
                FirefoxOptions options = new FirefoxOptions();
                options.addArguments("start-maximized", "ignore-certificate-errors");
                capabilities.setCapability(FirefoxOptions.FIREFOX_OPTIONS, options);


                WebDriver mailDriver = new FirefoxDriver(capabilities);
                mailDriver.get("https://temp-mail.org/en/");

                if (!mailDriver.findElement(By.className("mail")).isDisplayed()) {
                    break;
                }

                WebElement Email = mailDriver.findElement(By.className("mail"));
                //System.out.println(Email.getAttribute("value"));
                String EmailID = Email.getAttribute("value");

                WebDriver spotifyDriver = new FirefoxDriver(capabilities);
                spotifyDriver.get("https://www.spotify.com/en/signup/");

                if (!spotifyDriver.findElement(By.id("register-email")).isDisplayed()) {
                    break;
                }

                spotifyDriver.findElement(By.id("register-email")).sendKeys(EmailID);
                spotifyDriver.findElement(By.id("register-confirm-email")).sendKeys(EmailID);
                String[] username = EmailID.split("@");
                spotifyDriver.findElement(By.id("register-password")).sendKeys(username[0]);
                spotifyDriver.findElement(By.id("register-displayname")).sendKeys(username[0]);
                Select drpMonth = new Select(spotifyDriver.findElement(By.id("register-dob-month")));
                drpMonth.selectByVisibleText("January");
                spotifyDriver.findElement(By.id("register-dob-day")).sendKeys("20");
                spotifyDriver.findElement(By.id("register-dob-year")).sendKeys("1990");
                spotifyDriver.findElement(By.id("register-male")).click();
                if (spotifyDriver.findElement(By.id("recaptcha-anchor-label")).isDisplayed()) {
                    Thread.sleep(50000);
                }
                spotifyDriver.findElement(By.id("register-button-email-submit")).click();


                spotifyDriver.quit();
                mailDriver.quit();
            }catch(Exception e){
                System.out.println("Error");

            }

        }
    }

}











