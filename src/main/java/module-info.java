module br.com.vrfortaleza.integracaoapi {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires org.kordamp.ikonli.javafx;
    requires atlantafx.base;
    requires org.kordamp.ikonli.core;
    requires org.kordamp.ikonli.feather;
    requires org.jetbrains.annotations;

    opens br.com.vrfortaleza.integracaoapi.pages to javafx.fxml;
    opens br.com.vrfortaleza.integracaoapi.pages.components.controllers to javafx.fxml;
    exports br.com.vrfortaleza.integracaoapi;
    exports br.com.vrfortaleza.integracaoapi.pages;
    exports br.com.vrfortaleza.integracaoapi.pages.components.controllers;
}