package com.cust.scholar.model;

import java.io.IOException;

import com.cust.scholar.controller.SearchController;

import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class NavButton extends Label{
	public NavButton(String keyword){
		this.setUnderline(true);
		this.setTextFill(Color.BLUE);
		this.setFont(new Font(17));
		this.setPadding(new Insets(3));
		this.setOnMouseClicked(cellData->{
			//ÏÔÊ¾ËÑË÷½çÃæ
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
			SearchItem searchItem=new SearchItem();
			searchItem.setKeyword(keyword);
			controller.searchKeywords(searchItem,primaryStage);
		});
	}
	
}
