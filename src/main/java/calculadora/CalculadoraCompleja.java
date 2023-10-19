package calculadora;


import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {
	
	private TextField firstReal;
	private TextField firstImagin;
	private TextField secondReal;
	private TextField secondImagin;
	private ComboBox<String> comboOperator;
	private TextField resultReal;
	private TextField resultImagin;

	private StringProperty operator = new SimpleStringProperty();
	
	private Complejo resultado = new Complejo();
	private Complejo firstcom = new Complejo();
	private Complejo secondcom = new Complejo();
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		firstReal = new TextField();
		firstReal.setText("0");
		firstReal.setMaxWidth(50);
		
		firstImagin = new TextField();
		firstImagin.setText("0");
		firstImagin.setMaxWidth(50);
		
		secondReal = new TextField();
		secondReal.setText("0");
		secondReal.setMaxWidth(50);
		
		secondImagin = new TextField();
		secondImagin.setText("0");
		secondImagin.setMaxWidth(50);
		
		comboOperator = new ComboBox<String>();
		comboOperator.getItems().addAll("+","-","*","/");
		
		comboOperator.setMaxWidth(60);
		
		resultReal = new TextField();
		resultReal.setText("0");
		resultReal.setMaxWidth(50);
		resultReal.setDisable(true);
		
		
		resultImagin = new TextField();
		resultImagin.setText("0");
		resultImagin.setMaxWidth(50);
		resultImagin.setDisable(true);
		
		
		
		
		
		Separator separador = new Separator();
	
		
		HBox primerComplejo = new HBox(5,firstReal,new Label("+"),firstImagin,new Label("i"));
		HBox segundoComplejo = new HBox(5,secondReal,new Label("+"),secondImagin,new Label("i"));			   
		HBox resultComplejo = new HBox(5,resultReal,new Label("+"),resultImagin,new Label("i"));
		
		VBox operation = new VBox(5,primerComplejo,segundoComplejo,separador,resultComplejo);
		operation.setAlignment(Pos.CENTER);
		operation.setFillWidth(false);
		
		VBox allSentences = new VBox(comboOperator);
		allSentences.setAlignment(Pos.CENTER);
		allSentences.setFillWidth(false);
		
		HBox root = new HBox(5,allSentences,operation);
		root.setAlignment(Pos.CENTER);
		allSentences.setFillWidth(false);
		
		
		Scene scene = new Scene(root,320,200);

		primaryStage.setTitle("Calculadora compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//bindeos
		
		Bindings.bindBidirectional(firstReal.textProperty(),firstcom.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(firstImagin.textProperty(),firstcom.imaginarioProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(secondReal.textProperty(),secondcom.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(secondImagin.textProperty(),secondcom.imaginarioProperty(), new NumberStringConverter());
		
		Bindings.bindBidirectional(resultReal.textProperty(), resultado.realProperty(), new NumberStringConverter());
		Bindings.bindBidirectional(resultImagin.textProperty(), resultado.imaginarioProperty(), new NumberStringConverter());
		
		operator.bind(comboOperator.getSelectionModel().selectedItemProperty());
		
		//listener
		
		operator.addListener((o, ov, nv) -> onOperadorChanged(nv));
		
		comboOperator.getSelectionModel().selectFirst();
	
	}

	private void onOperadorChanged(String nv) {
		
		switch(nv) {
		case "+":
			resultado.realProperty().bind(firstcom.realProperty().add(secondcom.realProperty()));
			resultado.imaginarioProperty().bind(firstcom.imaginarioProperty().add(secondcom.imaginarioProperty()));
			break;
		case "-":
			resultado.realProperty().bind(firstcom.realProperty().subtract((secondcom.realProperty())));
			resultado.imaginarioProperty().bind(firstcom.imaginarioProperty().subtract((secondcom.imaginarioProperty())));	
			break;
		case "*": 
			resultado.realProperty().bind(
					firstcom.realProperty().multiply(secondcom.realProperty()).subtract(firstcom.imaginarioProperty().multiply(secondcom.imaginarioProperty()))
					);
			resultado.imaginarioProperty().bind(
					firstcom.realProperty().multiply(secondcom.imaginarioProperty()).add(firstcom.imaginarioProperty().multiply(secondcom.realProperty()))
					);
			break;
		case "/":
			resultado.realProperty().bind(
					(firstcom.realProperty().multiply(secondcom.realProperty()).add(firstcom.imaginarioProperty().multiply(secondcom.imaginarioProperty())))
					.divide((secondcom.realProperty().multiply(secondcom.realProperty())).add(secondcom.imaginarioProperty().multiply(secondcom.imaginarioProperty())))
					);
			resultado.imaginarioProperty().bind(
					firstcom.imaginarioProperty().multiply(secondcom.realProperty()).subtract(firstcom.realProperty().multiply(secondcom.imaginarioProperty()))
					.divide((secondcom.realProperty().multiply(secondcom.realProperty())).add(secondcom.imaginarioProperty().multiply(secondcom.imaginarioProperty())))
					);
			break;
		}
		
	}

	public static void main(String[] args) {
		launch(args);
	}

}