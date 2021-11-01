package com.wheel.crawler.yuque.crawler;

import com.wheel.common.util.JsonUtil;
import edu.uci.ics.crawler4j.crawler.Page;
import edu.uci.ics.crawler4j.crawler.WebCrawler;
import edu.uci.ics.crawler4j.parser.HtmlParseData;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;


/**
 * @desc 语雀爬虫
 * @author: zhouf
 */
@Slf4j
public class YuqueCrawler extends WebCrawler {

    /**
     * 处理抓取的数据
     *
     * @param page
     */
    @Override
    public void visit(Page page) {
        if (!(page.getParseData() instanceof HtmlParseData)) {
            log.error("页面错误, 中断爬取");
            return;
        }

        HtmlParseData htmlParseData = (HtmlParseData) page.getParseData();
        String html = htmlParseData.getHtml();
        log.info("当前页面文本：{}", html);

        Document doc = Jsoup.parse(html);

        // 获取所有 href 链接
        Elements links = doc.select("a[href]");

        // 因为本身文章数量不多，就干脆用一个爬虫搞定了
        for (Element src : links) {
            try {
                parseArticle(src.attr("abs:href"));
            } catch (Exception e) {
                log.error("文章解析发生错误：", e);
            }
        }
    }

    public void parseArticle(String url) throws IOException {
        Document doc = Jsoup.connect(url).get();
        log.info("article: {}", JsonUtil.toString(doc));
    }

}

