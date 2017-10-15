package application;
	
import java.io.IOException;

import com.cust.scholar.controller.DetailController;
import com.cust.scholar.controller.NavigateController;
import com.cust.scholar.controller.SearchController;
import com.cust.scholar.model.Paper;
import com.cust.scholar.util.KeyWordsEngine;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class Main extends Application {
	//	方便其他类调用该Stage
	public static Stage primaryStage;
	private AnchorPane anchorPane;
	
	@Override
	public void start(Stage primaryStage) {
		Main.primaryStage=primaryStage;
		Main.primaryStage.setTitle("光电子技术分类检索系统");
		/*try {
			KeyWordsEngine.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			System.out.println("文件未找到");
			e.printStackTrace();
		}*/
		//initRootPane();
		initNavPane();
	}
	
	public void initNavPane(){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(NavigateController.class.getResource("/com/cust/scholar/view/NavigateView.fxml"));
		try {
			anchorPane = (AnchorPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Scene scene=new Scene(anchorPane);
		Main.primaryStage.setX(100);
		Main.primaryStage.setScene(scene);
		Main.primaryStage.setResizable(false);
		Main.primaryStage.show();
	}
	
	public void initRootPane(String keyword){
		//load fxml
		FXMLLoader loader =new FXMLLoader();
		loader.setLocation(SearchController.class.getResource("/com/cust/scholar/view/SearchView.fxml"));
		try {
			anchorPane = (AnchorPane)loader.load();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Scene scene=new Scene(anchorPane);
		primaryStage.setScene(scene);
		primaryStage.show();
		
	}
	
	public static void showDeatil(Paper paper){
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(SearchController.class.getResource("/com/cust/scholar/view/PaperDetail.fxml"));
		Stage stage=new Stage();
		stage.setTitle("论文详情");
		
		try {
			AnchorPane detailPane=(AnchorPane)loader.load();
			Scene scene=new Scene(detailPane);
			stage.setScene(scene);
			
			DetailController detailController = loader.getController();
			detailController.showDetail(paper);
			stage.show();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
