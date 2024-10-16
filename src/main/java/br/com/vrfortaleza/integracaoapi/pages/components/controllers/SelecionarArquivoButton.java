package br.com.vrfortaleza.integracaoapi.pages.components.controllers;

import javafx.scene.control.Button;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

public class SelecionarArquivoButton extends Button {
    public SelecionarArquivoButton() {
        super(null, new FontIcon(Feather.FOLDER_PLUS));
        getStyleClass().addAll("button_icon","accent");
    }
}
