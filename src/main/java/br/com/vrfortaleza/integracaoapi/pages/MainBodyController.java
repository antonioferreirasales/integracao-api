package br.com.vrfortaleza.integracaoapi.pages;

import br.com.vrfortaleza.integracaoapi.pages.components.controllers.SucessButton;
import javafx.fxml.FXML;
import org.controlsfx.control.CheckComboBox;

public class MainBodyController {
    @FXML
    public SucessButton helloButton;
    @FXML
    private CheckComboBox<String> selecionarCampos;
}
