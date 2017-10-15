package com.cust.scholar.model;

public class SearchItem {
	//搜索内容
    private String keyword;
    //搜索来源
    private String source;
    //搜索页数
    private Integer pageNum;
    //作者
    private String author;
    //发表开始日期
    private String startYear;
    //发表截至日期
    private String endYear;
    //语言
    private Integer language;
    //上一页
    private String prePage;
    //下一页
    private String nextPage;
    
    public String getPrePage() {
		return prePage;
	}

	public void setPrePage(String prePage) {
		this.prePage = prePage;
	}

	public String getNextPage() {
		return nextPage;
	}

	public void setNextPage(String nextPage) {
		this.nextPage = nextPage;
	}

	public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getStartYear() {
        return startYear;
    }

    public void setStartYear(String startYear) {
        this.startYear = startYear;
    }

    public String getEndYear() {
        return endYear;
    }

    public void setEndYear(String endYear) {
        this.endYear = endYear;
    }

    public Integer getLanguage() {
        return language;
    }

    public void setLanguage(Integer language) {
        this.language = language;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(int pageNum) {
        this.pageNum = pageNum;
    }
    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

	@Override
	public String toString() {
		return "SearchItem [keyword=" + keyword + ", source=" + source + ", pageNum=" + pageNum + ", author=" + author
				+ ", startYear=" + startYear + ", endYear=" + endYear + ", language=" + language + "]";
	}
    
}
