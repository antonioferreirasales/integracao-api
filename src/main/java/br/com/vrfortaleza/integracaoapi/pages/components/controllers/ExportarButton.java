package br.com.vrfortaleza.integracaoapi.pages.components.controllers;

import javafx.scene.control.Button;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

public class ExportarButton extends Button {
    public ExportarButton(String text) {
        super(text, new FontIcon(Feather.DOWNLOAD));
        getStyleClass().add("success");
    }

    public ExportarButton() {
        this("Exportar");
    }
}
