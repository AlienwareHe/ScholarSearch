package com.cust.scholar.model;

public class SearchItem {
	//��������
    private String keyword;
    //������Դ
    private String source;
    //����ҳ��
    private Integer pageNum;
    //����
    private String author;
    //����ʼ����
    private String startYear;
    //�����������
    private String endYear;
    //����
    private Integer language;
    //��һҳ
    private String prePage;
    //��һҳ
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
