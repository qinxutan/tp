package seedu.address.ui;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.OS;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.model.module.ModuleCode;

public class ModuleListPanelTest {

    private ModuleListPanel moduleListPanel;
    private ObservableList<ModuleCode> moduleCodes;

    @BeforeAll
    public static void initializeJavaFX() {
        // Initialize JavaFX only if not on Linux
        if (!OS.LINUX.isCurrentOs()) {
            javafx.embed.swing.JFXPanel jfxPanel = new javafx.embed.swing.JFXPanel();
        }
    }

    @BeforeEach
    public void setUp() {
        moduleCodes = FXCollections.observableArrayList();
        moduleListPanel = new ModuleListPanel(moduleCodes);
    }

    @Test
    public void constructor_withListOfModuleCodes_initializesListView() {
        assertNotNull(moduleListPanel.moduleListView);
    }

    @Test
    public void constructor_withListOfModuleCodes_setsCellFactory() {
        assertNotNull(moduleListPanel.moduleListView.getCellFactory());
    }

    @Test
    public void constructor_withListOfModuleCodes_displaysCorrectModuleCards() {
        // Disable GUI testing on Linux
        if (System.getProperty("os.name").toLowerCase().contains("linux")) {
            System.out.println("Skipping GUI test on Linux.");
            return;
        }

        moduleCodes.add(new ModuleCode("CS2103"));
        assertEquals(1, moduleListPanel.moduleListView.getItems().size());
    }
}


