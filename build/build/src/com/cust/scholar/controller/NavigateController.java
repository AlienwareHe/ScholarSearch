package com.cust.scholar.controller;

import java.io.IOException;
import java.util.List;

import com.cust.scholar.model.NavButton;
import com.cust.scholar.model.SearchItem;
import com.cust.scholar.util.KeyWordsEngine;
import com.google.common.base.Strings;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class NavigateController {
	@FXML
	private GridPane mainGrid;
	@FXML // 只有private属性才需要FXML注解
	private TextField searchKeyword;
	@FXML
	private TextField searchAuthor;
	@FXML
	private TextField searchStartYear;
	@FXML
	private TextField searchEndYear;
	@FXML
	private void initialize() {
		
		List<String> list=KeyWordsEngine.keyWordList;
		if(list.size()<=0){
			try {
				KeyWordsEngine.load();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		int index=0;
		FlowPane flow=new FlowPane();
		flow.setPadding(new Insets(5));
		//填充界面
		for(String s:list){
			String word=list.get(index++);
			NavButton button=new NavButton(word);
			button.setText(word+"  ");
			flow.getChildren().add(button);
		}
		/*GridPane grid=new GridPane();
		for(int i=0;i<10;i++){
			for(int j=0;j<12;j++){
				if(index==list.size()){
					break;
				}
				String word=list.get(index++);
				NavButton button=new NavButton(word);
				button.setText(word);
				grid.add(button, i, j);
			}
		}*/
		mainGrid.add(flow, 0, 3);
	}
	
	//导航界面的搜索方法
	@FXML
	private void doSearch(){
		SearchItem searchItem=new SearchItem();
		//获取输入参数
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
		//显示舞台
		Stage primaryStage=new Stage();
		FXMLLoader loader =new FXMLLoader();
		loader.setLocation(SearchController.class.getResource("/com/cust/scholar/view/SearchView.fxml"));
		AnchorPane anchorPane=null;
		try {
			anchorPane = (AnchorPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		primaryStage.setX(300);
		SearchController controller=loader.getController();
		Scene scene=new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		controller.searchKeywords(searchItem,primaryStage);
	}
	
	public GridPane getMainGrid() {
		return mainGrid;
	}

	public void setMainGrid(GridPane mainGrid) {
		this.mainGrid = mainGrid;
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

	
	
}
