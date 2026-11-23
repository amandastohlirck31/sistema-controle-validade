package br.edu.ifrs.trabalholp;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class App extends Application {
 @Override public void start(Stage stage) throws Exception {
  FXMLLoader loader = new FXMLLoader(App.class.getResource("/br/edu/ifrs/trabalholp/tela-principal.fxml"));
  stage.setTitle("Trabalho LP - Controle de Validade");
  stage.setScene(new Scene(loader.load(), 1000, 680)); stage.setMinWidth(850); stage.setMinHeight(600); stage.show();
 }
 public static void main(String[] args) { launch(args); }
}
