package sinanews;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class SeleniumDemo {
    public static void main(String[] args) {
        String chromePath;
        chromePath = "E:\\software\\Workspaces\\crawler\\src\\main\\resources\\chromedriver.exe";
        System.setProperty("webdriver.chrome.driver",chromePath);
        WebDriver chromeDriver = new ChromeDriver();
        chromeDriver.get("http://www.baidu.com");
    }
}
