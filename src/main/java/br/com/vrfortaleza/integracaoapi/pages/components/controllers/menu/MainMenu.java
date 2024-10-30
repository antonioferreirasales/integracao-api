package br.com.vrfortaleza.integracaoapi.pages.components.controllers.menu;

import static javafx.scene.input.KeyCombination.CONTROL_DOWN;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import org.jetbrains.annotations.Nullable;
import org.kordamp.ikonli.Ikon;
import org.kordamp.ikonli.feather.Feather;
import org.kordamp.ikonli.javafx.FontIcon;

public class MainMenu extends MenuBar {
    private static final EventHandler<ActionEvent> SYSTEM_OUT = System.out::println;
    @FXML
    private MenuItem configuracaoMenu;

    public MainMenu() {
        getMenus().addAll(
                sistemaMenu(),
                sobreMenu()
        );
    }

    public Menu findMenuByName(String menuName) {
            for (Menu menu : this.getMenus()) {
                if (menuName.equals(menu.getText())) {
                    return menu;
                }
            }
            throw new RuntimeException("Menu não encontrado: " + menuName);
        }

    public MenuItem findMenuItemByName(Menu menu, String menuItemName) {
        for (MenuItem menuItem : menu.getItems()) {
            if (menuItemName.equals(menuItem.getText())) {
                return menuItem;
            }
        }
        throw new RuntimeException("MenuItem não encontrado: " + menuItemName);
    }

    private Menu sistemaMenu() {
        var menu = new Menu("_Sistema");
        menu.setMnemonicParsing(true);
        menu.setOnAction(SYSTEM_OUT);

        var exportarMenu = createItem(
                "_Exportar", null, new KeyCodeCombination(KeyCode.A, CONTROL_DOWN)
        );
        exportarMenu.setMnemonicParsing(true);
        exportarMenu.setOnAction(SYSTEM_OUT);

        configuracaoMenu = createItem(
                "Configuração", Feather.TOOL, new KeyCodeCombination(KeyCode.U, CONTROL_DOWN)
        );
        configuracaoMenu.setMnemonicParsing(true);

        var sairMenu = createItem(
                "Sair", null, new KeyCodeCombination(KeyCode.Q, CONTROL_DOWN)
        );
        sairMenu.setMnemonicParsing(true);

        menu.getItems().addAll(
                exportarMenu,
                new SeparatorMenuItem(),
                configuracaoMenu,
                new SeparatorMenuItem(),
                sairMenu
        );
        return menu;
    }

    private Menu sobreMenu() {
        var menu = new Menu(
                "_Sobre", new FontIcon(Feather.HELP_CIRCLE)
        );
        menu.setMnemonicParsing(true);
        menu.setOnAction(SYSTEM_OUT);

        var sobreMenu = new MenuItem("Sobre");

        menu.getItems().addAll(
                sobreMenu
        );
        return menu;
    }

    private MenuItem createItem(@Nullable String text,
                                @Nullable Ikon graphic,
                                @Nullable KeyCombination accelerator) {

        var item = new MenuItem(text);

        if (graphic != null) {
            item.setGraphic(new FontIcon(graphic));
        }

        if (accelerator != null) {
            item.setAccelerator(accelerator);
        }

        return item;
    }
}
