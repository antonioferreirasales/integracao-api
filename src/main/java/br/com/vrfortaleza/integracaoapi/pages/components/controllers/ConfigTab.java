package br.com.vrfortaleza.integracaoapi.pages.components.controllers;

import javafx.scene.control.TabPane;

public class ConfigTab extends TabPane {
    public ConfigTab() {
        super();
        setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
        setMinWidth(450);
    }
}
