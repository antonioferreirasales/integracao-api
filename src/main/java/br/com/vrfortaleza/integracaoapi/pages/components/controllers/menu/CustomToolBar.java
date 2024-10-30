package br.com.vrfortaleza.integracaoapi.pages.components.controllers.menu;

import javafx.scene.control.Button;
import javafx.scene.control.ToolBar;
import lombok.Getter;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

@Getter
public class CustomToolBar extends ToolBar {
    private final Button criarButton;
    private final Button excluirButton;
    private final Button salvarButton;

    public CustomToolBar() {
        super();
        criarButton = new Button("Novo", new FontIcon(Feather.PLUS));
        excluirButton = new Button("Excluir", new FontIcon(Feather.TRASH_2));
        salvarButton = new Button("Salvar", new FontIcon(Feather.SAVE));
        getItems().addAll(criarButton, excluirButton, salvarButton);
    }
}
