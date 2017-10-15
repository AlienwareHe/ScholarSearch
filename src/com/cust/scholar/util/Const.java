package com.cust.scholar.util;

public class Const {
	public static String getCommanWords(int num) {
		switch (num) {
		case 0:
			return "����Ӽ���";
		case 1:
			return "���Ӽ���";
		case 2:
			return "�ռ��ͨ��";
		case 3:
			return "��缼��";
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
