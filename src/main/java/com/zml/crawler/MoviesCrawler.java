package com.zml.crawler;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.jsoup.helper.StringUtil;

import com.zml.email.EmailSender;
import com.zml.entity.Movie;
import com.zml.persistence.MovieDao;

import cn.edu.hfut.dmic.webcollector.model.CrawlDatums;
import cn.edu.hfut.dmic.webcollector.model.Page;
import cn.edu.hfut.dmic.webcollector.plugin.berkeley.BreadthCrawler;

public class MoviesCrawler extends BreadthCrawler {

		private static MovieDao movieDao = new MovieDao();
		
	    public MoviesCrawler(String crawlPath, boolean autoParse) {
	        super(crawlPath, autoParse);
	    }

	    @Override
	    public void visit(Page page, CrawlDatums next) {
	    	if(page.matchUrl("http://dianying.2345.com/detail/.*html")) {
	    		Movie m = getMovieDate(page);
	    		int count = movieDao.findMovieByName(m.getName());
	    		if (count == 0){
	    			movieDao.saveMovice(m);
	    		}
	    	}
	    }
	    
	    private Movie getMovieDate(Page page) {
	    	Movie movie = new Movie();
	    	String name = page.select("div[class=tit]>h1").text();
	    	String score = page.select("em[class=emScore]").text();
	    	System.out.println(page.select("span[class=sUpdate]").text());
	    	Date releaseTime = null;
			try {
				releaseTime = parseDateStr(page.select("span[class=sUpdate]").text());
			} catch (ParseException e) {
				System.out.println(page.select("span[class=sUpdate]").text());
				e.printStackTrace();
			}
	    	String duration = page.select("em:contains(时长)~em").text();
	    	String actors = page.select("em:contains(主演)~em").text();
	    	String years = page.select("em:contains(年代)~em").text();
	    	String director = page.select("em:contains(导演)~em").text();
	    	String type = page.select("em:contains(类型)~em").text();
	    	String area = page.select("em:contains(地区)~em").text();
	    	String summary = page.select("em:contains(简介)~em").text();
	    	String cover = page.select("div[class=pic]").first().attr("src");
	    	movie.setName(name);
	    	movie.setScore(score);
	    	movie.setReleaseTime(releaseTime);
	    	movie.setActors(actors);
	    	movie.setDuration(duration);
	    	movie.setYears(years);
	    	movie.setDirector(director);
	    	movie.setType(type);
	    	movie.setArea(area);
	    	movie.setSummary(summary);
	    	movie.setCover(cover);
	    	return movie;
	    }
	    
	    private Date parseDateStr(String text) throws ParseException {
	    	if (StringUtil.isBlank(text)) {
	    		return null;
	    	}
	    	String dateStr = text.trim();
	    	int index = dateStr.indexOf("日");
	    	String date = dateStr.substring(1, index+1);
	    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
	    	return sdf.parse(date);
	    }
	    
	    public static void main(String[] args) throws Exception {
	    	MoviesCrawler crawler = new MoviesCrawler("crawler", true);
	        crawler.addSeed("http://dianying.2345.com/xinpian");
	        crawler.addRegex("http://dianying.2345.com/detail/.*html");
	        
	        crawler.setThreads(10);
	        crawler.start(2);	
	        
	        EmailSender.sendEmailByTemplate(movieDao.findUpcomingMovies());
	    }
}
