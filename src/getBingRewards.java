import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import java.io.Console;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class getBingRewards {

    public static void main(String[] args) throws InterruptedException, IOException {
        WebDriver driver;
        String username = null;
        String password = null;
        Random rand = new Random();
        Integer randomNum;


        Console cons = System.console();
        if (cons == null) {
            System.out.println("Couldn't get Console instance");
            System.exit(0);
        }

        cons.printf("Please enter your username:");
                username = cons.readLine();

            cons.printf("Please enter your password:");
            char[] passwordChars = cons.readPassword();
            password = new String(passwordChars);


        driver = new FirefoxDriver();

        driver.get("http://www.bing.com");
        driver.findElement(By.id("id_s")).click();
        Thread.sleep(750);
        driver.findElement(By.cssSelector(".id_link_text")).click();
        Thread.sleep(750);
        driver.findElement(By.id("i0116")).sendKeys(username);
        driver.findElement(By.id("i0118")).sendKeys(password);
        driver.findElement(By.id("idSIButton9")).click();
        Thread.sleep(2500);

        //Click first item
        List<WebElement> links = driver.findElements(By.cssSelector("#crs_pane .crs_item"));
        links.get(0).click();

        //Cycle through related searches
        for(int i = 0; i <= 45; i++){
            List<WebElement> relatedLinks = driver.findElements(By.xpath("//h2[contains(text(), 'Related searches')]//following-sibling::ul//li//a"));
            Thread.sleep(500);
            if(!relatedLinks.isEmpty()){
                randomNum = rand.nextInt(relatedLinks.size());
                relatedLinks.get((randomNum)).click();
            }
        }

        driver.quit();

        FirefoxProfile profile = new FirefoxProfile();
        profile.setPreference("general.useragent.override", "Android / Chrome 34: Mozilla/5.0 (Linux; Android 4.4.2; Nexus 4 Build/KOT49H) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/34.0.1847.114 Mobile Safari/537.36");
        driver = new FirefoxDriver(profile);
        //Login
        driver.get("http://www.bing.com/rewards/signin");
        Thread.sleep(750);
        driver.findElement(By.id("i0116")).sendKeys(username);
        driver.findElement(By.id("i0118")).sendKeys(password);
        driver.findElement(By.id("idSIButton9")).click();
        Thread.sleep(500);
        driver.get("http://www.bing.com/");
        Thread.sleep(1500);

        //Click first item
        driver.findElement(By.id("pop_link1")).click();
        Thread.sleep(500);

        //Cycle through related searches
        for (int i = 0; i <= 45; i++) {
            List<WebElement> relatedLinks = driver.findElements(By.xpath("//h2[contains(text(), 'Related searches')]//following-sibling::div//div//ul//li//a"));
            Thread.sleep(500);
            if (!relatedLinks.isEmpty()) {
                randomNum = rand.nextInt(relatedLinks.size());
                relatedLinks.get((randomNum)).click();
            }
        }
        driver.quit();
    }
}
