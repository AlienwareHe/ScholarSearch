package com.cust.scholar.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.jsoup.Connection.Response;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.cust.scholar.model.SearchItem;
import com.google.common.base.Strings;

public class SearchUtil {
	// 拼接关键词获取url
	public static String getURL(SearchItem searchItem) {
		String search = searchItem.getSource();
		String url = null;
		switch (search) {
		case "baidu":
			url = assembleBaiduUrl(searchItem);
			break;
		case "cnki":
			url = searchItem.getKeyword();
			break;
		}
		return url;
	}
	private static String assembleBaiduUrl(SearchItem searchItem) {
		StringBuilder stringBuilder = new StringBuilder("http://xueshu.baidu.com/s?wd=");
		try {
			if (!Strings.isNullOrEmpty(searchItem.getKeyword())) {
				stringBuilder.append(URLEncoder.encode(searchItem.getKeyword(), "UTF-8"));
			}
			if (!Strings.isNullOrEmpty(searchItem.getAuthor())) {
				stringBuilder.append("+author%3A%28").append(URLEncoder.encode(searchItem.getAuthor(), "UTF-8"))
						.append("%29");
			}
			// 加上页码
			// stringBuilder.append("&pn=").append((searchItem.getPageNum()-1)*10);
			if (!Strings.isNullOrEmpty(searchItem.getStartYear()) || !Strings.isNullOrEmpty(searchItem.getEndYear())) {
				stringBuilder.append("&filter=sc_year%3D%7B")
						.append(searchItem.getStartYear() == null ? "-" : searchItem.getStartYear()).append("%2C")
						.append(searchItem.getStartYear() == null ? "+" : searchItem.getStartYear()).append("%7D");
				if (searchItem.getLanguage() != null && searchItem.getLanguage() != 0) {
					stringBuilder.append("sc_la%3D%7B").append(searchItem.getLanguage()).append("%7D");
				}
			} else if (searchItem.getLanguage() != null && searchItem.getLanguage() != 0) {
				stringBuilder.append("&filter=sc_la%3D%7B").append(searchItem.getLanguage()).append("%7D");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		return stringBuilder.toString();
	}

	// 根据论文url获得该论文是否有可免费下载的pdf
	public static String getPDFURL(String url) {
		try {
			Document doc = Jsoup.connect(url).get();
			Elements elements = doc.select("a.dl_item");
			for (Element element : elements) {
				if (element.attr("href").contains(".pdf")) {
					return element.attr("href");
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// 提取百度学术中论文摘要
	public static String getAbstract(String source) {
		Integer i = source.lastIndexOf("<div");
		return source.substring(0, i == -1 ? source.length() : i);
	}

	// 下载文件
	public static void downloadFile(File file, String url) throws IOException {
		// 利用jsoup下载文件
		try {
			// 若不ignoreContentType会抛出无法处理内容类型异常Unhandled content type. Must be
			// text/*, application/xml, or application/xhtml+xml.
			Response resultReponse = Jsoup.connect(url).ignoreContentType(true).execute();
			FileOutputStream out = new FileOutputStream(file);
			out.write(resultReponse.bodyAsBytes());
			out.close();
		} catch (IOException e) {
			throw e;
		}
	}
	public static void downloadFile(File file, String url,Map<String,String> cookies) throws IOException {
		// 利用jsoup下载文件
		try {
			// 若不ignoreContentType会抛出无法处理内容类型异常Unhandled content type. Must be
			// text/*, application/xml, or application/xhtml+xml.
			Response resultReponse = Jsoup.connect(url).ignoreContentType(true).cookies(cookies).execute();
			FileOutputStream out = new FileOutputStream(file);
			out.write(resultReponse.bodyAsBytes());
			out.close();
		} catch (IOException e) {
			throw e;
		}
	}
}
