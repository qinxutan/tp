package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.person.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * A command to list all students of a particular tutorial class.
 */
public class ListStudentsOfClassCommand extends Command {

    public static final String COMMAND_WORD = "/list_students_of_class";
    public static final String MESSAGE_EMPTY = "No classes available!";
    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        if (model.getAddressBook().getTutorialList().isEmpty()) {
            return new CommandResult(MESSAGE_EMPTY);
        }
        StringBuilder result = new StringBuilder();

    }

    private List<Person> getStudentsInClass(Model model) throws CommandException {
        List<Person> allStudents = model.getFilteredPersonList();
        List<Person> studentsInClass = new ArrayList<>();
        for (Person student : allStudents) {
            if (tutorialClass.hasStudent(student)) {
                studentsInClass.add(student);
            }
        }
        return studentsInClass;
    }

    private String formatStudentsList(List<Person> students) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Students in ").append(moduleCode).append(" ").append(tutorialClass).append(":\n");
        for (Person student : students) {
            stringBuilder.append("- ").append(student.getName()).append("\n");
        }
        return stringBuilder.toString().trim();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof ListStudentsOfClassCommand)) {
            return false;
        }
        ListStudentsOfClassCommand command = (ListStudentsOfClassCommand) other;
        return moduleCode.equals(command.moduleCode) && tutorialClass.equals(command.tutorialClass);
    }
}
