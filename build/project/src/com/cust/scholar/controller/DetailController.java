package com.cust.scholar.controller;

import com.cust.scholar.model.Paper;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class DetailController {
	private Paper paper;
	
	@FXML
	private Label titleField;
	@FXML
	private Label sourceField;
	@FXML
	private Label countsField;
	@FXML
	private Label authorField;
	@FXML
	private Label abstractField;
	
	@FXML
	private void initialize(){
		
	}
	
	public void showDetail(Paper paper){
		this.paper=paper;
		titleField.setText(paper.getTitle());
		sourceField.setText(paper.getPublish());
		countsField.setText(paper.getCounts());
		authorField.setText(paper.getAuthor());
		abstractField.setText(paper.getSc_abstract());
	}

	
	
	
	//get and set
	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public Label getAbstractField() {
		return abstractField;
	}

	public void setAbstractField(Label abstractField) {
		this.abstractField = abstractField;
	}

	public Label getTitleField() {
		return titleField;
	}

	public void setTitleField(Label titleField) {
		this.titleField = titleField;
	}

	public Label getSourceField() {
		return sourceField;
	}

	public void setSourceField(Label sourceField) {
		this.sourceField = sourceField;
	}

	public Label getCountsField() {
		return countsField;
	}

	public void setCountsField(Label countsField) {
		this.countsField = countsField;
	}

	public Label getAuthorField() {
		return authorField;
	}

	public void setAuthorField(Label authorField) {
		this.authorField = authorField;
	}
	
	
}
