package sinanews;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Random;

public class Spider {
    public static void main(String[] args) throws IOException, InterruptedException {
        long waitLoadBaseTime = 10000;
        int waitLoadRandomTime = 3000;
        Random random = new Random(System.currentTimeMillis());
        // ���� chrome ��·��
        System.setProperty(
                "webdriver.chrome.driver",
                "E:\\software\\Workspaces\\crawler\\src\\main\\resources\\chromedriver.exe");
        // ����һ�� ChromeDriver �Ľӿڣ��������� Chrome
        @SuppressWarnings("deprecation")
        ChromeDriverService service = new ChromeDriverService.Builder()
                .usingDriverExecutable(
                        new File(
                                "E:\\software\\Workspaces\\crawler\\src\\main\\resources\\chromedriver.exe"))
                .usingAnyFreePort().build();
        service.start();
        // ����һ�� Chrome �������ʵ��
        WebDriver driver = new RemoteWebDriver(service.getUrl(),
                DesiredCapabilities.chrome());
        // �����������΢����ҳ
        driver.get("http://weibo.com/338303018");
        //�ȴ�ҳ�涯̬�������
        Thread.sleep(waitLoadBaseTime+random.nextInt(waitLoadRandomTime));
        //ѡ��ÿ��΢����������ģ��
        List<WebElement> elements = driver.findElements(By.cssSelector("div[action-type=feed_list_item]"));
        //ѡ��ÿ��΢�����ı�����ģ��
        List<WebElement> elements2 = driver.findElements(By.cssSelector("div[node-type=feed_list_reason],div[node-type=feed_list_content]"));
        System.out.println(elements.size());
        for (int i = 0; i < elements.size(); i++) {
            //չ������
            elements.get(i).findElement(By.cssSelector("a[action-type=fl_comment]")).click();
            Thread.sleep(1000);
        }
        //�����б�
        List<WebElement> elements3 = driver.findElements(By.cssSelector("div[node-type=feed_list_commentList]"));
        System.out.println(elements3.size());
        int a = 0;
        for (int i =0;i<elements2.size()&&a<elements3.size();i++) {
            //ץȡ����
            String content = elements2.get(i).getText();
            if (!content.contains("ת��΢��")) {
                System.out.println("content:"+content);
                //ץȡ����
                if (elements3.get(a).getText().isEmpty()) {
                    System.out.println("comment:no comment");
                }else{
                    System.out.println("comment:"+elements3.get(a).getText());
                }
                a++;
            }
        }
        driver.quit();
        // �ر� ChromeDriver �ӿ�
        service.stop();
    }
}
