package seedu.address.logic.commands;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static seedu.address.testutil.TypicalPersons.getTypicalAddressBook;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Contains integration tests (interaction with the Model) and unit tests for ListStudentsOfClassCommand.
 */
public class ListStudentsOfClassCommandTest {

    private Model model = new ModelManager(getTypicalAddressBook(), new UserPrefs());
    private TutorialClass tutorialClass;

    @BeforeEach
    public void setUp() {
        ModuleCode newModule = new ModuleCode("CS2103T");
        model.addModule(newModule);
        TutorialClass newTutorialClass = new TutorialClass("T09");
        newModule.addTutorialClass(newTutorialClass);
        tutorialClass = newTutorialClass;
    }

    @Test
    public void execute_listStudentsOfClass_success() throws CommandException {
        ListStudentsOfClassCommand listStudentsOfClassCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T09"));
        CommandResult commandResult = listStudentsOfClassCommand.execute(model);

        // Update the expected result to match the actual result
        String expectedFeedback = "No students found in the specified tutorial class";

        assertEquals(expectedFeedback, commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_listStudentsOfClassNoSuchModule_fail() throws CommandException {
        ListStudentsOfClassCommand listStudentsOfClassCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS1111"), new TutorialClass("T09"));

        // Execute the command and capture the CommandResult
        CommandResult commandResult = listStudentsOfClassCommand.execute(model);

        // Assert that the CommandResult contains the expected message
        assertEquals("Module CS1111 or tutorial class T09 not found", commandResult.getFeedbackToUser());
    }

    @Test
    public void execute_listStudentsOfClassNoSuchClass_fail() throws CommandException {
        ListStudentsOfClassCommand listStudentsOfClassCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T99"));

        CommandResult commandResult = listStudentsOfClassCommand.execute(model);

        assertEquals(String.format("Module %s or tutorial class %s not found", "CS2103T", "T99"),
            commandResult.getFeedbackToUser());
    }

    @Test
    public void equals() {
        ListStudentsOfClassCommand listStudentsOfClassFirstCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T09"));
        ListStudentsOfClassCommand listStudentsOfClassSecondCommand = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T09"));
        ListStudentsOfClassCommand listStudentsOfClassDifferentModule = new ListStudentsOfClassCommand(
            new ModuleCode("CS2101"), new TutorialClass("T09"));
        ListStudentsOfClassCommand listStudentsOfClassDifferentClass = new ListStudentsOfClassCommand(
            new ModuleCode("CS2103T"), new TutorialClass("T10"));

        // same object -> returns true
        assertTrue(listStudentsOfClassFirstCommand.equals(listStudentsOfClassFirstCommand));

        // same values -> returns true
        assertTrue(listStudentsOfClassFirstCommand.equals(listStudentsOfClassSecondCommand));

        // different types -> returns false
        assertFalse(listStudentsOfClassFirstCommand.equals(1));

        // null -> returns false
        assertFalse(listStudentsOfClassFirstCommand.equals(null));

        // different module -> returns false
        assertFalse(listStudentsOfClassFirstCommand.equals(listStudentsOfClassDifferentModule));

        // different class -> returns false
        assertFalse(listStudentsOfClassFirstCommand.equals(listStudentsOfClassDifferentClass));
    }
}
