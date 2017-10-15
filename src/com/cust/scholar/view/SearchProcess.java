package com.cust.scholar.view;


import javafx.concurrent.Task;
import javafx.concurrent.WorkerStateEvent;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.Background;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class SearchProcess {
	private Stage processStage;
	private ProgressIndicator progressIndicator;

	public SearchProcess(Task<?> task, Stage primaryStage) {
		processStage = new Stage();
		progressIndicator = new ProgressIndicator();
		// 设置窗口父子关系，否则会出现两个窗口
		processStage.initOwner(primaryStage);
		processStage.initStyle(StageStyle.UNDECORATED);
		processStage.initModality(Modality.APPLICATION_MODAL);
		// 设置进度条
		Label label = new Label();
		label.setText("正在搜索中~请稍等");
		label.setTextFill(Color.BLUE);

		progressIndicator.setProgress(-1F);
		progressIndicator.progressProperty().bind(task.progressProperty());
		VBox vBox = new VBox();
		vBox.setSpacing(10);
		vBox.setBackground(Background.EMPTY);
		vBox.getChildren().addAll(progressIndicator, label);

		Scene scene = new Scene(vBox);
		scene.setFill(null);
		processStage.setScene(scene);


		Thread inner = new Thread(task);
		inner.start();
		
		//设置成功事件，当任务执行完后关闭进度条窗口
		task.setOnSucceeded(new EventHandler<WorkerStateEvent>() {
			public void handle(WorkerStateEvent event) {
				processStage.close();
			}
		});
	}

	public void activateProgressBar() {
		processStage.show();
	}

	public Stage getDialogStage() {
		return processStage;
	}

	public void cancelProgressBar() {
		processStage.close();
	}
}
