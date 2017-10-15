package com.cust.scholar.model;

import java.io.File;
import java.io.IOException;

import com.cust.scholar.util.SearchUtil;
import com.google.common.base.Strings;

import application.Main;
import javafx.beans.InvalidationListener;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.stage.FileChooser;

public class PaperVO {
	private Paper paper;
	private ObservableValue<Button> download_link;
	private ObservableValue<Button> detail_link;
	private StringProperty title;
	private StringProperty date;

	public PaperVO() {
	}

	public PaperVO(Paper paper) {
		this.paper = paper;
		this.title = new SimpleStringProperty(paper.getTitle());
		this.date = new SimpleStringProperty(paper.getDate());
		if (!Strings.isNullOrEmpty(paper.getDownloadHref())) {
			download_link = new ObservableValue<Button>() {

				@Override
				public void removeListener(InvalidationListener listener) {
				}

				@Override
				public void addListener(InvalidationListener listener) {
				}

				@Override
				public void removeListener(ChangeListener<? super Button> listener) {
				}

				@Override
				public Button getValue() {
					Button button = new Button("免费下载");
					button.setOnAction(cellData -> {
						// TODO 下载文件
						FileChooser fileChooser = new FileChooser();
						fileChooser.setTitle("保存为pdf文件");
						File file = fileChooser.showSaveDialog(Main.primaryStage);// 返回选择的文件或null
						if (file != null) {
							try {
								SearchUtil.downloadFile(file, paper.getDownloadHref());
								Alert alert=new Alert(Alert.AlertType.INFORMATION, "下载成功");
								alert.setTitle("下载提示");
								alert.showAndWait();
							} catch (IOException e) {
								// TODO Auto-generated catch block
								Alert alert=new Alert(Alert.AlertType.INFORMATION, "下载失败。。该下载链接无法识别");
								alert.setTitle("下载提示");
								alert.showAndWait();
								e.printStackTrace();
							}
						}
					});
					return button;
				}

				@Override
				public void addListener(ChangeListener<? super Button> listener) {
				}

			};
		}
		if (!Strings.isNullOrEmpty(paper.getHref())) {
			detail_link = new ObservableValue<Button>() {
				@Override
				public void removeListener(InvalidationListener listener) {
				}

				@Override
				public void addListener(InvalidationListener listener) {
				}

				@Override
				public void removeListener(ChangeListener<? super Button> listener) {
				}

				@Override
				public Button getValue() {
					Button button = new Button("查看详情");
					button.setOnAction(cellData -> {
						// 查看详情
						/*Alert information = new Alert(Alert.AlertType.INFORMATION, paper.getSc_abstract());
						information.setTitle("摘要");
						information.setHeaderText(paper.getTitle());
						information.showAndWait();// 弹窗显示并挂起后续代码
*/					
					Main.showDeatil(paper);	
					});
					return button;
				}

				@Override
				public void addListener(ChangeListener<? super Button> listener) {
				}

			};
		}
	}

	public ObservableValue<Button> getDownload_link() {
		return download_link;
	}

	public void setDownload_link(ObservableValue<Button> download_link) {
		this.download_link = download_link;
	}

	public Paper getPaper() {
		return paper;
	}

	public void setPaper(Paper paper) {
		this.paper = paper;
	}

	public ObservableValue<Button> getDetail_link() {
		return detail_link;
	}

	public void setDetail_link(ObservableValue<Button> detail_link) {
		this.detail_link = detail_link;
	}

	public StringProperty getTitle() {
		return title;
	}

	public void setTitle(StringProperty title) {
		this.title = title;
	}

	public StringProperty getDate() {
		return date;
	}

	public void setDate(StringProperty date) {
		this.date = date;
	}

}
