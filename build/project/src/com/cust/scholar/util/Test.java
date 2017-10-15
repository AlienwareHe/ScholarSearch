package com.cust.scholar.util;

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

public class Test {

	@SuppressWarnings("unused")
	public static void main(String[] args) throws UnsupportedEncodingException {
		String keyWord = "1";
		keyWord = URLEncoder.encode(keyWord, "utf-8");

		// cnki��ѯһ��������Ҫ�������裬��һ������Ϊdefault_result.aspx����ȡASP.NET_SessionId,LID,LID,SID_kns
		// ������cnki��Ҫ�����������󣬵�һ��Ϊ���ò�ѯ�������󣬵ڶ���Ϊ�����б����ҳ����
		String url = "http://kns.cnki.net/KNS/request/SearchHandler.ashx?action=&NaviCode=*&";// ��Ӳ�ѯ����
		String url2 = "http://kns.cnki.net/kns/brief/brief.aspx?";// ��Ӳ���ò�����ò���
		// ����httpͷ��ģ�������
		HashMap<String, String> headers = new HashMap<>();
		headers.put("Connection", "Keep-alive");
		headers.put("Accept", "text/html,*/*");
		headers.put("User-Agent",
				"Mozilla/5.0 (Windows NT 6.2; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/29.0.1547.66 Safari/537.36");
		headers.put("Referer", "http://kns.cnki.net/kns/brief/result.aspx?dbprefix=scdb&action=scdbsearch&db_opt=SCDB");
		// ������url����
		StringBuilder queryBuilder = new StringBuilder(
				"ua=1.11&formDefaultResult=&PageName=ASP.brief_default_result_aspx&DbPrefix=SCDB&DbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDBINDEX.xml&db_opt=CJFQ%2CCJRF%2CCDFD%2CCMFD%2CCPFD%2CIPFD%2CCCND&txt_1_sel=SU%24%25%3D%7C&txt_1_special1=%25&his=0&parentdb=SCDB");
		// �ؼ���
		queryBuilder.append("&txt_1_value1=").append(keyWord);
		// ʱ��
		// SimpleDateFormat dataFormat = new SimpleDateFormat("EEE MMM dd YYYY
		// hh:mm:ss");
		// String time = dataFormat.format(new Date());
		queryBuilder.append("&__=").append(
				"Mon%20Oct%2002%202017%2016%3A56%3A17%20GMT%2B0800%20(%E4%B8%AD%E5%9B%BD%E6%A0%87%E5%87%86%E6%97%B6%E9%97%B4)");
		url = url + queryBuilder.toString();
		// �����ڶ�������url
		url2 += "pagename=ASP.brief_default_result_aspx&dbPrefix=SCDB&dbCatalog=%e4%b8%ad%e5%9b%bd%e5%ad%a6%e6%9c%af%e6%96%87%e7%8c%ae%e7%bd%91%e7%bb%9c%e5%87%ba%e7%89%88%e6%80%bb%e5%ba%93&ConfigFile=SCDBINDEX.xml&research=off&t="
				+ String.valueOf(new Date().getTime()) + "&keyValue=" + keyWord + "&S=1";
		try {
			// ��һ������,α������ͷ
			// ��ȡcookie
			Map<String, String> cookies = Jsoup.connect("http://kns.cnki.net/kns/brief/default_result.aspx")
					.data(headers).execute().cookies();
			// ����cookie
			Map<String, String> map = Jsoup.connect(url).data(headers).cookies(cookies).execute().cookies();// con1.execute().cookies();
			for (String key : map.keySet()) {
				if (!cookies.containsKey(key)) {
					cookies.put(key, map.get(key));
				}
			}
			cookies.put("RsPerPage", "20");
			// ����userkey�ַ���
			String guid = "";
			for (int i = 1; i <= 32; i++) {
				String n = Integer.toHexString((int) Math.floor(Math.random() * 16.0));
				guid += n;
				if ((i == 8) || (i == 12) || (i == 16) || (i == 20))
					guid += "-";
			}
			cookies.put("cnkiUserKey", guid);
			// �����б��һҳ�����ü�������
			//Document doc = Jsoup.connect(url2).data(headers).cookies(cookies).get();
			//����ڶ�ҳ
			Document doc=Jsoup.connect("http://kns.cnki.net/kns/brief/brief.aspx?curpage=2&RecordsPerPage=20&QueryId=0&ID=&turnpage=1&tpagemode=L&dbPrefix=SCDB&Fields=&DisplayMode=listmode&PageName=ASP.brief_default_result_aspx").cookies(cookies).get();
			//parse
			Elements element=doc.select("a.fz14");
			for(Element e:element){
				System.out.println(e.text());
			}
			System.out.println(1);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
}
