package sinanews;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatum;
import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.net.HttpRequest;
import cn.edu.hfut.dmic.webcollector.net.HttpResponse;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class WeiboSpider extends BreadthCrawler {
    String cookie;
    public WeiboSpider(String crawlPath, boolean autoParse) throws Exception {
        super(crawlPath, autoParse);
        /*获取新浪微博的cookie，账号密码以明文形式传输，请使用小号*/
        cookie = WeiboCN.getSinaCookie("小号的账号", "小号的密码");
    }

    @Override
    public HttpResponse getResponse(CrawlDatum crawlDatum) throws Exception {
        HttpRequest request = new HttpRequest(crawlDatum);
        request.setCookie(cookie);
        return request.getResponse();
    }

    @Override
    public void visit(Page page, CrawlDatums next) {
        int pageNum = Integer.valueOf(page.getMetaData("pageNum"));
        /*抽取微博*/
        Elements weibos = page.select("div.c");
        for (Element weibo : weibos) {
            System.out.println("第" + pageNum + "页\t" + weibo.text());
        }
    }

    public static void main(String[] args) throws Exception {
        WeiboSpider crawler = new WeiboSpider("weibo_crawler", false);
        crawler.setThreads(3);
        /*对某人微博前5页进行爬取*/
        for (int i = 1; i <= 50; i++) {
            crawler.addSeed(new CrawlDatum("http://weibo.cn/u/3865692567?vt=4&page=" + i)
                    .putMetaData("pageNum", i + ""));
        }
        crawler.start(1);
    }
}
