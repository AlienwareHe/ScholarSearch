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
	// ƴ�ӹؼ��ʻ�ȡurl
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
			// ����ҳ��
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

	// ��������url��ø������Ƿ��п�������ص�pdf
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

	// ��ȡ�ٶ�ѧ��������ժҪ
	public static String getAbstract(String source) {
		Integer i = source.lastIndexOf("<div");
		return source.substring(0, i == -1 ? source.length() : i);
	}

	// �����ļ�
	public static void downloadFile(File file, String url) throws IOException {
		// ����jsoup�����ļ�
		try {
			// ����ignoreContentType���׳��޷��������������쳣Unhandled content type. Must be
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
		// ����jsoup�����ļ�
		try {
			// ����ignoreContentType���׳��޷��������������쳣Unhandled content type. Must be
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
