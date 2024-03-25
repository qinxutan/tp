package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static seedu.address.commons.util.CollectionUtil.requireAllNonNull;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.function.Predicate;

import javafx.collections.ObservableList;
import seedu.address.commons.core.index.Index;
import seedu.address.logic.Messages;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.messages.TutorialTeamMessages;
import seedu.address.logic.parser.Prefix;
import seedu.address.model.Model;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;
import seedu.address.model.module.TutorialTeam;
import seedu.address.model.person.Email;
import seedu.address.model.person.Person;
import seedu.address.model.person.StudentId;

/**
 * A class that handles the /allocate_team command execution.
 */
public class AllocateStudentToTeamCommand extends Command {
    public static final String MESSAGE_PERSON_NOT_FOUND = "Person with %1$s %2$s not found!";
    public static final String COMMAND_WORD = "/allocate_team";

    public static final String MESSAGE_USAGE = COMMAND_WORD
        + ": Allocate a person to a team based on their ID, index, or email\n"
        + "Parameters: (id/ID | index/INDEX | email/EMAIL) <value> "
        + PREFIX_MODULECODE + "MODULE_CODE "
        + PREFIX_TUTORIALCLASS + "TUTORIAL_CLASS "
        + PREFIX_TEAMNAME + "TEAM_NAME\n"
        + "Example: " + COMMAND_WORD + " " + PREFIX_STUDENTID + "A1234567K "
        + PREFIX_MODULECODE + "CS2103 " + PREFIX_TUTORIALCLASS + "T09 " + PREFIX_TEAMNAME + "Team 1";

    private final Prefix allocationType;
    private final String allocationValue;
    private final ModuleCode module;
    private final TutorialClass tutorialClass;
    private final String teamName;

    /**
     * Constructs an AllocateTeamCommand to allocate a person to a team based on their ID, index, or email.
     * @param allocationType The type of allocation (ID, index, or email).
     * @param allocationValue The value of the allocation (ID, index, or email).
     * @param module The module code of the tutorial class.
     * @param tutorialClass The tutorial class to allocate to.
     * @param teamName The name of the team.
     */
    public AllocateStudentToTeamCommand(Prefix allocationType, String allocationValue, ModuleCode module,
                               TutorialClass tutorialClass, String teamName) {
        requireAllNonNull(allocationType, allocationValue, module, tutorialClass, teamName);
        this.allocationType = allocationType;
        this.allocationValue = allocationValue;
        this.module = module;
        this.tutorialClass = tutorialClass;
        this.teamName = teamName;
    }

    @Override
    public CommandResult execute(Model model) throws CommandException {
        requireNonNull(model);

        Person personToAllocate;
        if (allocationType.equals(PREFIX_STUDENTID)) {
            personToAllocate = findPersonById(model, new StudentId(allocationValue));
        } else if (allocationType.equals(PREFIX_INDEX)) {
            personToAllocate = findPersonByIndex(model, Index.fromZeroBased(Integer.parseInt(allocationValue)));
        } else if (allocationType.equals(PREFIX_EMAIL)) {
            personToAllocate = findPersonByEmail(model, new Email(allocationValue));
        } else {
            throw new CommandException("Invalid allocation type specified!");
        }

        if (personToAllocate == null) {
            throw new CommandException(String.format(MESSAGE_PERSON_NOT_FOUND, allocationType, allocationValue));
        }
        TutorialTeam team = model.findOrCreateTeam(tutorialClass, module,
            new TutorialTeam(teamName, Integer.MAX_VALUE));
        if (team.hasStudent(personToAllocate)) {
            throw new CommandException(String.format(TutorialTeamMessages.MESSAGE_DUPLICATE_STUDENT_IN_TEAM,
                Messages.format(personToAllocate), team));
        } else {
            model.addPersonToTeam(personToAllocate, module, tutorialClass, team);
            String commandResultMessage = String.format("Added student %s; Email: %s; Student ID: %s; ",
                personToAllocate.getName(), personToAllocate.getEmail(), personToAllocate.getStudentId());

            if (teamName != null && !teamName.isEmpty()) {
                commandResultMessage += String.format("to %s %s Team: %s", module, tutorialClass, teamName);
            } else {
                commandResultMessage += String.format("to %s %s", module, tutorialClass);
            }

            return new CommandResult(commandResultMessage);
        }
    }

    private Person findPersonById(Model model, StudentId id) {
        Predicate<Person> idPredicate = person -> person.getStudentId().equals(id);
        return model.searchPersonByPredicate(idPredicate);
    }

    private Person findPersonByIndex(Model model, Index index) {
        Predicate<Person> indexPredicate = person -> {
            ObservableList<Person> filteredList = model.getFilteredPersonList();
            return index.getZeroBased() >= 0 && index.getZeroBased() < filteredList.size()
                && filteredList.indexOf(person) == index.getZeroBased();
        };
        return model.searchPersonByPredicate(indexPredicate);
    }

    private Person findPersonByEmail(Model model, Email email) {
        Predicate<Person> emailPredicate = person -> person.getEmail().equals(email);
        return model.searchPersonByPredicate(emailPredicate);
    }

    private TutorialTeam findOrCreateTeam(Model model, String teamName, TutorialClass tutorialClass)
            throws CommandException {
        for (TutorialTeam team : tutorialClass.getTeams()) {
            if (team.getTeamName().equals(teamName)) {
                return team;
            }
        }
        // If team doesn't exist, create a new one
        TutorialTeam newTeam = new TutorialTeam();
        tutorialClass.addTeam(newTeam);
        return newTeam;
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof AllocateStudentToTeamCommand)) {
            return false;
        }
        AllocateStudentToTeamCommand otherAllocateCommand = (AllocateStudentToTeamCommand) other;
        return module.equals(otherAllocateCommand.module)
            && tutorialClass.equals(otherAllocateCommand.tutorialClass)
            && teamName.equals(otherAllocateCommand.teamName);
    }
}
