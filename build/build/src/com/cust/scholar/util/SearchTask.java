package com.cust.scholar.util;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cust.scholar.controller.SearchController;
import com.cust.scholar.model.Paper;
import com.cust.scholar.model.PaperVO;
import com.google.common.base.Strings;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;

//因为查询过程很耗时，如果不做任务处理，可能出现假死现象。
public class SearchTask extends Task<Integer> {
	private String url;
	private Document doc;
	private Integer page = -1;// 页码
	private SearchController controller;

	public SearchTask(SearchController controller, String url) {
		this.url = url;
		this.controller = controller;
	}

	public SearchTask(SearchController controller, String url, int page) {
		this.url = url;
		this.controller = controller;
		this.page = page;
	}

	@Override
	protected Integer call() throws Exception {
		System.out.println("获取源码解析");
		ObservableList<PaperVO> papers = FXCollections.observableArrayList();

		switch (controller.getSearchItem().getSource()) {
		case "baidu":
			handleForBaidu(papers);
			break;
		case "cnki":
			handleForCnki(papers);
			break;
		}
		return 1;
	}

	private void handleForBaidu(ObservableList<PaperVO> papers) {
		// 获取第一页
		getBaiduPapers(url, papers);
		// 为使界面丰满，显示两页内容。
		for (Element ele : doc.select("p#page > a")) {
			if (ele.text().equals("下一页>")) {
				getBaiduPapers(Const.SearchPrefix.BAIDU.getPrefix() + ele.attr("href"), papers);
			}
		}
		// 设置上下页链接
		for (Element ele : doc.select("p#page > a")) {
			if (ele.text().equals(String.valueOf(controller.getSearchItem().getPageNum()*2 - 3))) {
				controller.getSearchItem().setPrePage(Const.SearchPrefix.BAIDU.getPrefix() + ele.attr("href"));
				break;
			} else if (ele.text().equals(String.valueOf(controller.getSearchItem().getPageNum()*2 - 2))) {
				controller.getSearchItem().setPrePage(Const.SearchPrefix.BAIDU.getPrefix() + ele.attr("href"));
				break;
			} else {
				controller.getSearchItem().setPrePage("");
			}
		}
		for (Element ele : doc.select("p#page > a")) {
			if (ele.text().equals(String.valueOf(controller.getSearchItem().getPageNum()*2))) {
				controller.getSearchItem().setNextPage(Const.SearchPrefix.BAIDU.getPrefix() + ele.attr("href"));
			} else if (ele.text().equals(String.valueOf(controller.getSearchItem().getPageNum()*2 + 1))) {
				controller.getSearchItem().setNextPage(Const.SearchPrefix.BAIDU.getPrefix() + ele.attr("href"));
				break;
			} else {
				controller.getSearchItem().setNextPage("");
			}
		}
		// 组装到tableview中,有个优点就是会自动更新原列表而不会增加，因此不用删除原内容.
		controller.getPapersView().setItems(papers);
		System.out.println("执行完毕");
	}

	private void getBaiduPapers(String uurl, ObservableList<PaperVO> papers) {
		try {
			doc = Jsoup.connect(uurl).get();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Elements titles = doc.select("div.sc_content");
		for (Element element : titles) {
			Element title = element.select("h3.t.c_font > a").first();
			Paper paper = new Paper();
			// 不同搜索引擎添加不同前缀
			paper.setHref(controller.getSearchItem().getSource(), title.attr("href"));
			paper.setTitle(title.text());
			// 进入论文详情页面寻找是否有可免费下载pdf
			paper.setDownloadHref(SearchUtil.getPDFURL(paper.getHref()));
			// 添加论文摘要
			paper.setSc_abstract(SearchUtil.getAbstract(
					element.select("div.c_abstract").first().html().replaceAll("<em>", "").replaceAll("</em>", "")));
			// 添加论文日期
			paper.setDate(element.select("span.sc_time").attr("data-year"));
			// 设置作者
			StringBuilder author = new StringBuilder("");
			for (Element ele : element.select("div.sc_info > span").first().select("a")) {
				author.append(ele.text()).append(" ");
			}
			paper.setAuthor(author.toString().replaceAll("<em>", "").replaceAll("</em>", ""));
			// 设置出版来源
			paper.setPublish(element.select("div.sc_info > span").get(1).select("a").text().replaceAll("<em>", "")
					.replaceAll("</em>", ""));
			// 设置引用量
			paper.setCounts(element.select("a.sc_cite_cont").text());
			PaperVO paperVO = new PaperVO(paper);
			papers.add(paperVO);
		}

	}

	private void handleForCnki(ObservableList<PaperVO> papers) throws UnsupportedEncodingException {
		System.out.println("获取cnki源码");
		String keyWord = "";
		keyWord = URLEncoder.encode(url, "utf-8");
		// cnki查询一次文献需要三个步骤，第一次请求为default_result.aspx，获取ASP.NET_SessionId,LID,LID,SID_kns
		// 接下来cnki需要发送两次请求，第一次为设置查询参数请求，第二次为请求列表具体页请求
		String url = "http://kns.cnki.net/KNS/request/SearchHandler.ashx?action=&NaviCode=*&";// 后接查询参数
		String url2 = "http://kns.cnki.net/kns/brief/brief.aspx?";// 后接参数貌似作用不大
		// 构建http头，模拟浏览器
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Connection", "Keep-alive");
		headers.put("Accept", "text/html,*/*");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
		headers.put("Referer", "http://kns.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB");
		// 将参数url编码
		StringBuilder queryBuilder = new StringBuilder(
				"ua=1.11&formDefaultResult=&PageName=ASP.brief_default_result_aspx&DbPrefix=SCDB&DbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDBINDEX.xml&db_opt=CJFQ%2CCJRF%2CCDFD%2CCMFD%2CCPFD%2CIPFD%2CCCND&txt_1_sel=SU%24%25%3D%7C&txt_1_special1=%25&his=0&parentdb=SCDB");
		// 关键词
		queryBuilder.append("&txt_1_value1=").append(keyWord);
		// 时间
		// SimpleDateFormat dataFormat = new SimpleDateFormat("EEE MMM dd YYYY
		// hh:mm:ss");
		// String time = dataFormat.format(new Date());
		queryBuilder.append("&__=").append(
				"Mon%20Oct%2002%202017%2016%3A56%3A17%20GMT%2B0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)");
		url = url + queryBuilder.toString();
		// 构建第二次请求url
		url2 += "pagename=ASP.brief_default_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDBINDEX.xml&research=off&t="
				+ String.valueOf(new Date().getTime()) + "&keyValue=" + keyWord + "&S=1";
		try {
			// 第一次请求,伪造请求头
			// 获取cookie
			Map<String, String> cookies = Jsoup.connect("http://kns.cnki.net/kns/brief/default_result.aspx")
					.data(headers).execute().cookies();
			// 更新cookie
			Map<String, String> map = Jsoup.connect(url).data(headers).cookies(cookies).execute().cookies();// con1.execute().cookies();
			for (String key : map.keySet()) {
				if (!cookies.containsKey(key)) {
					cookies.put(key, map.get(key));
				}
			}
			cookies.put("RsPerPage", "20");
			// 生成userkey字符串
			String guid = "";
			for (int i = 1; i <= 32; i++) {
				String n = Integer.toHexString((int) Math.floor(Math.random() * 16.0));
				guid += n;
				if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
					guid += "-";
			}
			cookies.put("cnkiUserKey", guid);
			Document doc = null;
			if (page != -1) {
				// 请求某一页
				doc = Jsoup
						.connect(
								"http://kns.cnki.net/kns/brief/brief.aspx?curpage=2&RecordsPerPage=20&QueryId=0&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_default_result_aspx")
						.cookies(cookies).get();

			} else {
				// 请求列表第一页，设置检索参数
				doc = Jsoup.connect(url2).data(headers).cookies(cookies).get();
			}
			// parse
			Elements element = doc.select("table.GridTableContent > tbody > tr");
			for (int i = 1; i < element.size(); i++) {// 第一行为表头，省去
				Paper paper = new Paper();
				Elements columns = element.get(i).select("td");
				paper.setTitle(columns.get(1).text());
				paper.setAuthor(columns.get(2).text());
				paper.setPublish(columns.get(3).text());
				paper.setDate(columns.get(4).text());
				paper.setCounts(columns.get(7).text());
				// 获取摘要
				Document abs = Jsoup.connect("http://kns.cnki.net" + columns.get(1).attr("href")).get();
				paper.setSc_abstract("无");// abs.select("span#ChDivSummary").text()
				paper.setHref("1");
				File file = new File("D:/test.pdf");
				file.createNewFile();
				paper.setDownloadHref("http://kns.cnki.net/kns/brief/" + columns.get(7).select("a").attr("href"));
				SearchUtil.downloadFile(file, "http://kns.cnki.net/kns/brief/" + paper.getDownloadHref(), cookies);
				PaperVO paperVO = new PaperVO(paper);
				papers.add(paperVO);
			}
			controller.getPapersView().setItems(papers);
			System.out.println("执行完毕");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
