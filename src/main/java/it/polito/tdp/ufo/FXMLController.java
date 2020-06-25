package it.polito.tdp.ufo;

import java.net.URL;
import java.util.Map;
import java.util.ResourceBundle;

import it.polito.tdp.ufo.model.AnnoAvvistamento;
import it.polito.tdp.ufo.model.Model;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;

public class FXMLController {
	
	private Model model;

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ComboBox<AnnoAvvistamento> boxAnno;

    @FXML
    private ComboBox<String> boxStato;

    @FXML
    private TextArea txtResult;

    @FXML
    void handleAnalizza(ActionEvent event) {
    	this.txtResult.clear();
    	
    	String stato = this.boxStato.getValue();
    	
    	if(model.precedenti(stato).size()>0) {
	    	this.txtResult.appendText("Stati precedenti a "+stato+":\n");
	    	for(String s : model.precedenti(stato)) {
	    		this.txtResult.appendText(s+"\n");
	    	}
    	}else {
    		txtResult.appendText("Nessuno stato precedente a "+stato+"\n");
    	}

    	if(model.successivi(stato).size()>0) {
	    	this.txtResult.appendText("\nStati successivi a "+stato+":\n");
	    	for(String s : model.successivi(stato)) {
	    		this.txtResult.appendText(s+"\n");
	    	}
    	}else {
    		txtResult.appendText("Nessuno stato successivo a "+stato+"\n");
    	}
    	
    	this.txtResult.appendText("\nCi sono "+model.statiRaggiungibili(stato).size()+" stati raggiungibili da "+stato+":\n");
    	for(String s : model.statiRaggiungibili(stato)) {
    		this.txtResult.appendText(s+"\n");
    	}
    	
    }

    @FXML
    void handleAvvistamenti(ActionEvent event) {
    	this.txtResult.clear();
    	
    	Integer anno = this.boxAnno.getValue().getAnno();
    	model.creaGrafo(anno);
    	this.txtResult.appendText(String.format("Creato grafo!\n#vertici: %d\n#archi: %d\n", model.nVertici(), model.nArchi()));
    	
    	this.boxStato.getItems().clear();
    	this.boxStato.getItems().addAll(model.vertici(anno));
    	this.boxStato.setValue(model.vertici(anno).get(0));
    	
    	

    }

    @FXML
    void handleSequenza(ActionEvent event) {

    }

    @FXML
    void initialize() {
        assert boxAnno != null : "fx:id=\"boxAnno\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert boxStato != null : "fx:id=\"boxStato\" was not injected: check your FXML file 'Ufo.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Ufo.fxml'.";

    }

	public void setModel(Model model) {
		this.model = model;
		this.boxAnno.getItems().addAll(model.tendina());
		this.boxAnno.setValue(model.tendina().get(0));
		
		
	}
}
