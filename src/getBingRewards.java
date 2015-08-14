import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class getBingRewards {
    private static final String MOBILE = "Mobile";
    private static final String WEB = "Web";
    public static WebDriver driver;
    public static String username;
    public static String password;
    public static Random rand = new Random();
    public static Integer randomNum;
    public static JButton runPcButton;
    public static JButton runMobileButton;
    public static JLabel passwordLabel;
    public static JLabel usernameLabel;
    public static JPasswordField passwordInput;
    public static JTextField usernameInput;
    public static JFrame frame;
    public static JPanel panel;

    public static void main(String[] args) throws IOException, InterruptedException {
        try
        {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        }
        catch (Exception ex)
        {
            ex.printStackTrace();
        }
        createFrame();
    }

    private static void createFrame() {
        frame = new JFrame("Get Bing Rewards");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        panel = new JPanel();
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setOpaque(true);
        ButtonListener buttonListener = new ButtonListener();
        JPanel inputpanel = new JPanel();
        inputpanel.setLayout(new FlowLayout());

        usernameLabel = new JLabel("Username");
        usernameInput = new JTextField(20);
        passwordLabel = new JLabel("Password");
        passwordInput = new JPasswordField(20);
        runMobileButton = new JButton("Run Mobile");
        runMobileButton.setActionCommand(MOBILE);
        runMobileButton.addActionListener(buttonListener);
        runPcButton = new JButton("Run PC");
        runPcButton.setActionCommand(WEB);
        runPcButton.addActionListener(buttonListener);

        inputpanel.add(usernameLabel);
        inputpanel.add(usernameInput);
        inputpanel.add(passwordLabel);
        inputpanel.add(passwordInput);
        inputpanel.add(runPcButton);
        inputpanel.add(runMobileButton);

        panel.add(inputpanel);
        frame.getContentPane().add(BorderLayout.CENTER, panel);
        frame.pack();
        frame.setLocationByPlatform(true);
        // Center of screen
        // frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        frame.setResizable(false);
    }

    public static class ButtonListener implements ActionListener
    {

        public void actionPerformed(final ActionEvent ev)
        {
            String cmd = ev.getActionCommand();
            username = usernameInput.getText().trim();
            char[] passwordChars = passwordInput.getPassword();
            password = new String(passwordChars);
                if (MOBILE.equals(cmd))
                {
                    try {
                        getMobileRewards();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }else if(WEB.equals(cmd)){
                    try {
                        getPcRewards();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
        }
    }

    public static void getPcRewards() throws InterruptedException {
        driver = new FirefoxDriver();

        driver.get("http://www.bing.com");
        driver.findElement(By.id("id_s")).click();
        Thread.sleep(1000);
        driver.findElement(By.cssSelector(".id_link_text")).click();
        Thread.sleep(1000);
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

    }

    public static void getMobileRewards() throws InterruptedException{
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
