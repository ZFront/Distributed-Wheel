package com.wheel.crawler.yuque.manager;

import com.wheel.crawler.common.CrawlerAgent;
import com.wheel.crawler.yuque.crawler.YuqueCrawler;
import edu.uci.ics.crawler4j.crawler.CrawlConfig;
import edu.uci.ics.crawler4j.crawler.CrawlController;
import edu.uci.ics.crawler4j.fetcher.PageFetcher;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtConfig;
import edu.uci.ics.crawler4j.robotstxt.RobotstxtServer;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.message.BasicHeader;

import java.util.HashSet;

/**
 * @desc 语雀爬虫控制器
 * <p>
 * TIPS：由于返回语雀做了一次更新，直接通过链接已经查看不了文章，所以这个功能已经没用了
 * @author: zhouf
 */
@Slf4j
public class YuqueManager {

    /**
     * 知识库地址-1
     */
    private final static String URL_1 = "https://www.yuque.com/zfront/rb6xfk";

    /**
     * 知识库地址-2
     */
    private final static String URL_2 = "https://www.yuque.com/zfront/fsnkl5";

    public static void main(String[] args) {
        YuqueManager yuqueManager = new YuqueManager();
        try {
            yuqueManager.start();
        } catch (Exception e) {
            log.error("语雀爬取出现异常，e:", e);
        }
    }

    public CrawlConfig initDefaultConfig(String storageFolder) {
        CrawlConfig config = new CrawlConfig();
        config.setCrawlStorageFolder(storageFolder);

        HashSet<BasicHeader> collections = new HashSet<>();
        CrawlerAgent crawlerAgent = new CrawlerAgent();
        collections.add(new BasicHeader("user-agent", crawlerAgent.getDefaultAgent().get("user-agent")));
        config.setDefaultHeaders(collections);
        return config;
    }

    public CrawlController initDefaultController(String storageFolder) throws Exception {
        CrawlConfig config = initDefaultConfig(storageFolder);

        PageFetcher pageFetcher = new PageFetcher(config);

        RobotstxtConfig robotstxtConfig = new RobotstxtConfig();
        RobotstxtServer robotstxtServer = new RobotstxtServer(robotstxtConfig, pageFetcher);

        CrawlController controller = new CrawlController(config, pageFetcher, robotstxtServer);
        return controller;
    }

    /**
     *
     */
    public void start() throws Exception {
        CrawlController controller = initDefaultController("/crawler/yuque");

        controller.addSeed(URL_1);
        controller.addSeed(URL_2);

        controller.start(YuqueCrawler::new, 1);
    }
}
