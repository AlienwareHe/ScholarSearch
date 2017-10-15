package com.cust.scholar.util;

public class Const {
	public static String getCommanWords(int num) {
		switch (num) {
		case 0:
			return "光电子技术";
		case 1:
			return "光子技术";
		case 2:
			return "空间光通信";
		case 3:
			return "光电技术";
		default:
			return "";
		}
	}

	public enum SearchPrefix {
		BAIDU("baidu", "http://xueshu.baidu.com"), GOOGLE("google", "");

		private String prefix;
		private String source;

		SearchPrefix(String source, String prefix) {
			this.setPrefix(prefix);
			this.setSource(source);
		}

		public String getSource() {
			return source;
		}

		public void setSource(String source) {
			this.source = source;
		}

		public String getPrefix() {
			return prefix;
		}

		public void setPrefix(String prefix) {
			this.prefix = prefix;
		}

		public static SearchPrefix getPre(String source) {
			for (SearchPrefix s : values()) {
				if (source.equals(s.getSource())) {
					return s;
				}
			}
			return null;
		}
	}

}
