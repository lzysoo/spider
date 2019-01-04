package qqnews;

/**
 * 腾讯新闻 国际频道 新闻标题、链接、正文爬取
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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

public class Spider {
    public static void main(String[] args) throws IOException {
        List<NewsModel> newsList = new ArrayList<NewsModel>();
        HashMap<String,String> newsMap = getNewsUrl("http://news.qq.com/world_index.shtml");
        Set<String> title = newsMap.keySet();
        for (String s : title) {
            NewsModel news = new NewsModel();
            String newsUrl = newsMap.get(s);
            if (!newsUrl.contains("http:"))
                newsUrl = "http:" + newsUrl;
            if (!newsUrl.contains("http://"))
                continue;
//            System.out.println(s + "\t" + newsUrl);
            news.setNewsTitle(s);
            news.setNewsUrl(newsUrl);

            Elements contentsDom = getContents(newsUrl);
            for (Element ele : contentsDom){
                String newsContents = ele.text();
                System.out.println(newsContents);
                news.setNewsContents(newsContents);
            }
            newsList.add(news);
        }
        //将新闻的标题、链接、正文存入Excel中
        NewsToExcel.writenListToExcel("C:/Users/moon/Desktop/news.xlsx","news",newsList);

    }

    //根据URL获取该页所有的新闻标题和对应的地址链接
    public static HashMap<String,String> getNewsUrl(String url) throws IOException {
        HashMap<String,String> newsMap = new HashMap<String, String>();
        //模拟浏览器访问
        CloseableHttpClient httpClient = HttpClients.createDefault();
        //String url = "https://news.163.com/";
        HttpGet httpGet = new HttpGet(url);
        httpGet.setHeader("User-Agent","Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/71.0.3578.98 Safari/537.36");
        CloseableHttpResponse httpResponse = httpClient.execute(httpGet);
        String html = EntityUtils.toString(httpResponse.getEntity());

        //Document document = Jsoup.connect(url).get();
        Document document = Jsoup.parse(html);
        Elements titleDom = document.select(".linkto");
        for (Element ele : titleDom) {
            String newsTitle = ele.text();
            String newsUrl = ele.attr("href");
            newsMap.put(newsTitle,newsUrl);
        }
        return newsMap;
    }

    //根据每个新闻的URL获得该新闻的正文内容
    public static Elements getContents(String newsUrl){
        Elements contentsDom = new Elements();
        try {
            Document document = Jsoup.connect(newsUrl).get();
            Elements ele = document.select(".one-p");
            contentsDom = ele;
        }catch (IOException e) {
            e.printStackTrace();
        }
        return contentsDom;
    }
}
