package com.cust.scholar.controller;

import com.cust.scholar.model.PaperVO;
import com.cust.scholar.model.SearchItem;
import com.cust.scholar.util.Const;
import com.cust.scholar.util.SearchTask;
import com.cust.scholar.util.SearchUtil;
import com.cust.scholar.view.SearchProcess;
import com.google.common.base.Strings;

import application.Main;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class SearchController {

	// �������ݿ�
	@FXML // ֻ��private���Բ���ҪFXMLע��
	private TextField searchKeyword;
	@FXML
	private TextField searchAuthor;
	@FXML
	private TextField searchStartYear;
	@FXML
	private TextField searchEndYear;
	@FXML
	private ChoiceBox searchLanguage;
	@FXML
	private ChoiceBox commonWords;
	// ����չʾ��ͼ
	@FXML
	private TableView<PaperVO> papersView;
	@FXML
	private TableColumn<PaperVO, String> titleColumn;
	@FXML
	private TableColumn<PaperVO, String> dateColumn;
	@FXML
	private TableColumn<PaperVO, Button> downloadColumn;
	@FXML
	private TableColumn<PaperVO, Button> detailColumn;
	@FXML
	private Label condition;// ������������չʾ
	// ��ǰҳ��
	@FXML
	private Label pageNum;
	@FXML
	private RadioButton baidu;
	@FXML
	private RadioButton cnki;

	private SearchItem searchItem = new SearchItem();
	// ��ǰ����ҳ��stage
	private Stage stage;

	// fxml�ļ�������������ִ�еĳ�ʼ������This method is automatically called after the fxml
	// file has been loaded.
	@FXML
	private void initialize() {
		searchLanguage.getSelectionModel().selectedIndexProperty().addListener((ov, oldv, newv) -> {
			searchItem.setLanguage(newv.intValue());
			System.out.println("language" + searchItem.getLanguage());
		});
		commonWords.getSelectionModel().selectedIndexProperty().addListener((ov, oldv, newv) -> {
			searchItem.setKeyword(Const.getCommanWords(newv.intValue()));
			HandleSearch();
		});
		titleColumn.setCellValueFactory(cellData -> cellData.getValue().getTitle());
		downloadColumn.setCellValueFactory(cellData -> cellData.getValue().getDownload_link());
		detailColumn.setCellValueFactory(cellData -> cellData.getValue().getDetail_link());
		dateColumn.setCellValueFactory(cellData -> cellData.getValue().getDate());
		condition.setText("��ǰ��������Ϊ��");
	}

	// �ⲿ��������󴥷�����������
	public void searchKeywords(SearchItem searchItem, Stage stage) {
		this.searchItem = searchItem;
		this.stage = stage;
		HandleSearch();
	}

	// ���������������
	@FXML
	private void doSearch() {
		// �����������
		searchItem.setAuthor("");
		searchItem.setEndYear("");
		// searchItem.setKeyword("");
		searchItem.setStartYear("");
		searchItem.setPageNum(1);
		pageNum.setText("1");
		HandleSearch();
	}

	// ��������������ʾ
	public void showCondition(SearchItem searchItem) {
		StringBuilder sb = new StringBuilder("��ǰ��������Ϊ��");
		if (!Strings.isNullOrEmpty(searchItem.getKeyword())) {
			sb.append(searchItem.getKeyword()).append(" ");
		}
		if (!Strings.isNullOrEmpty(searchItem.getAuthor())) {
			sb.append(searchItem.getAuthor()).append(" ");
		}
		if (!Strings.isNullOrEmpty(searchItem.getStartYear())) {
			sb.append(searchItem.getStartYear()).append(" ");
		}
		if (!Strings.isNullOrEmpty(searchItem.getEndYear())) {
			sb.append(searchItem.getEndYear()).append(" ");
		}
		switch (searchItem.getLanguage()) {
		case 0:
			sb.append("���Բ���").append(" ");
			break;
		case 1:
			sb.append("Ӣ��").append(" ");
			break;
		case 2:
			sb.append("����").append(" ");
			break;
		}
		condition.setText(sb.toString());
	}

	// ����ʵ�ʵ��÷���
	private void HandleSearch() {
		if (isInputValid()) {
			showCondition(searchItem);
			String url = SearchUtil.getURL(searchItem);
			// �����߳�ִ�л�ȡԴ���̣߳����߳̿�������������ȴ��߳�ִ�н��
			SearchTask task = new SearchTask(this, url);
			SearchProcess process = new SearchProcess(task, stage);
			process.activateProgressBar();
		}
	}

	// �ж������Ƿ�Ϸ�
	private boolean isInputValid() {
		// TODO ��Ҫ�����û��������
		if (!Strings.isNullOrEmpty(searchKeyword.getText())) {
			searchItem.setKeyword(searchKeyword.getText());
		}
		if (!Strings.isNullOrEmpty(searchAuthor.getText())) {
			searchItem.setAuthor(searchAuthor.getText());
		}
		if (!Strings.isNullOrEmpty(searchStartYear.getText())) {
			searchItem.setStartYear(searchStartYear.getText());
		}
		if (!Strings.isNullOrEmpty(searchEndYear.getText())) {
			searchItem.setEndYear(searchEndYear.getText());
		}
		if (!Strings.isNullOrEmpty(searchEndYear.getText())) {
			searchItem.setEndYear(searchEndYear.getText());
		}
		if (searchItem.getPageNum() == null || searchItem.getPageNum() < 1) {
			searchItem.setPageNum(1);
		}
		if (Strings.isNullOrEmpty(searchItem.getSource())) {
			searchItem.setSource("baidu");
		}
		if (searchItem.getLanguage() == null) {
			searchItem.setLanguage(2);
		}
		return true;
	}

	// ��һҳ,Ӧ������pagination��ҳ�ؼ�ʵ�֣���Ϊ����ʵ�ֲ��ٷ���ԭҳ���п���ʾ����ҳ
	@FXML
	private void nextPage() {
		if (searchItem == null) {
			return;
		}
		pageNum.setText(String.valueOf(searchItem.getPageNum() + 1));
		searchItem.setPageNum(searchItem.getPageNum() + 1);
		if (Strings.isNullOrEmpty(searchItem.getNextPage())) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "����һҳ��");
			alert.setTitle("��ҳ֪ͨ");
			alert.showAndWait();
		} else {
			switch (searchItem.getSource()) {
			case "baidu":
				SearchTask task = new SearchTask(this, searchItem.getNextPage());
				SearchProcess process = new SearchProcess(task, Main.primaryStage);
				process.activateProgressBar();
				break;
			case "cnki":
				SearchTask task2 = new SearchTask(this, searchItem.getPageNum().toString());
				SearchProcess process2 = new SearchProcess(task2, Main.primaryStage);
				process2.activateProgressBar();
				break;
			}
		}

	}

	// ��һҳ
	@FXML
	private void prePage() {
		if (searchItem == null) {
			return;
		}
		int page = searchItem.getPageNum() - 1 < 1 ? 1 : searchItem.getPageNum() - 1;
		pageNum.setText(String.valueOf(page));
		searchItem.setPageNum(page);
		if (Strings.isNullOrEmpty(searchItem.getPrePage())) {
			Alert alert = new Alert(Alert.AlertType.INFORMATION, "����һҳ��");
			alert.setTitle("��ҳ֪ͨ");
			alert.showAndWait();
		} else {
			SearchTask task = new SearchTask(this, searchItem.getPrePage());
			SearchProcess process = new SearchProcess(task, Main.primaryStage);
			process.activateProgressBar();
		}
	}

	// �л�������ԴΪ�ٶ�ѧ��
	@FXML
	private void changeToBaidu() {
		searchItem.setSource("baidu");
		System.out.println("baidu");
	}

	// �л�������ԴΪ�й�֪��
	@FXML
	private void changeToCnki() {
		searchItem.setSource("cnki");
		System.out.println("cnki");
	}

	public Stage getStage() {
		return stage;
	}

	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public TextField getSearchKeyword() {
		return searchKeyword;
	}

	public void setSearchKeyword(TextField searchKeyword) {
		this.searchKeyword = searchKeyword;
	}

	public TextField getSearchAuthor() {
		return searchAuthor;
	}

	public void setSearchAuthor(TextField searchAuthor) {
		this.searchAuthor = searchAuthor;
	}

	public TextField getSearchStartYear() {
		return searchStartYear;
	}

	public void setSearchStartYear(TextField searchStartYear) {
		this.searchStartYear = searchStartYear;
	}

	public TextField getSearchEndYear() {
		return searchEndYear;
	}

	public void setSearchEndYear(TextField searchEndYear) {
		this.searchEndYear = searchEndYear;
	}

	public ChoiceBox getSearchLanguage() {
		return searchLanguage;
	}

	public void setSearchLanguage(ChoiceBox searchLanguage) {
		this.searchLanguage = searchLanguage;
	}

	public TableView<PaperVO> getPapersView() {
		return papersView;
	}

	public void setPapersView(TableView<PaperVO> papersView) {
		this.papersView = papersView;
	}

	public TableColumn<PaperVO, String> getTitleColumn() {
		return titleColumn;
	}

	public void setTitleColumn(TableColumn<PaperVO, String> titleColumn) {
		this.titleColumn = titleColumn;
	}

	public TableColumn<PaperVO, Button> getDownloadColumn() {
		return downloadColumn;
	}

	public void setDownloadColumn(TableColumn<PaperVO, Button> downloadColumn) {
		this.downloadColumn = downloadColumn;
	}

	public TableColumn<PaperVO, Button> getDetailColumn() {
		return detailColumn;
	}

	public void setDetailColumn(TableColumn<PaperVO, Button> detailColumn) {
		this.detailColumn = detailColumn;
	}

	public Label getPageNum() {
		return pageNum;
	}

	public void setPageNum(Label pageNum) {
		this.pageNum = pageNum;
	}

	public SearchItem getSearchItem() {
		return searchItem;
	}

	public void setSearchItem(SearchItem searchItem) {
		this.searchItem = searchItem;
	}

}
