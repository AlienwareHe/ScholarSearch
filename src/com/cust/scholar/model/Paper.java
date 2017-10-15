package com.cust.scholar.model;

import com.cust.scholar.util.Const;

public class Paper {
	private String title;
	private String href;
	private String downloadHref;
	private String sc_abstract;
	private String date;
	private String author;
	private String publish;//出版来源
	private String counts;//引用量
	
	
	public String getAuthor() {
		return author;
	}

	public void setAuthor(String author) {
		this.author = author;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getCounts() {
		return counts;
	}

	public void setCounts(String counts) {
		this.counts = counts;
	}

	public void setHref(String href) {
		this.href = href;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}

	public String getSc_abstract() {
		return sc_abstract;
	}

	public void setSc_abstract(String sc_abstract) {
		this.sc_abstract = sc_abstract;
	}

	public String getDownloadHref() {
		return downloadHref;
	}

	public void setDownloadHref(String downloadHref) {
		this.downloadHref = downloadHref;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getHref() {
		return href;
	}

	public void setHref(String source, String href) {
		String pre = "";
		switch(source){
		case "baidu":
			pre=Const.SearchPrefix.BAIDU.getPrefix();
			break;
		}
		if (pre != null) {
			this.href = pre + href;
		} else {
			this.href = null;
		}
	}
}
