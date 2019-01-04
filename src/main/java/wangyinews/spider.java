package wangyinews;

/**
 * 网易新闻首页抓取
 */

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;

public class spider {
    public static void main(String[] args) throws IOException {

        CloseableHttpClient httpClient = HttpClients.createDefault();
        String url = "https://news.163.com/";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String html = EntityUtils.toString(httpResponse.getEntity());

        Document document = Jsoup.parse(html);
        Elements eleDom = document.select("div[class = main_center_news]").select("div").select("ul").select("li");
        for (Element ele : eleDom){
            String title = ele.select("a").text();
            String newsUrl = ele.select("a").attr("href");
            System.out.println(title + "\t" + newsUrl);
        }
    }
}
