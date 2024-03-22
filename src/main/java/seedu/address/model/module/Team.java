package seedu.address.model.module;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import seedu.address.model.person.Person;

/**
 * Represents a Team in the system.
 */
public class Team {
    private final String teamName;
    private final List<Person> members;
    private final List<ModuleCode> associatedModules;
    private final List<TutorialClass> associatedTutorialClasses;

    /**
     * Creates a Team with the given name.
     * @param teamName The name of the team.
     */
    public Team(String teamName) {
        this.teamName = teamName;
        this.members = new ArrayList<>();
        this.associatedModules = new ArrayList<>();
        this.associatedTutorialClasses = new ArrayList<>();
    }

    /**
     * Adds a member to the team.
     * @param person The person to add to the team.
     */
    public void addMember(Person person) {
        members.add(person);
    }

    /**
     * Removes a member from the team.
     * @param person The person to remove from the team.
     */
    public void removeMember(Person person) {
        members.remove(person);
    }

    /**
     * Retrieves the members of the team.
     * @return The list of members in the team.
     */
    public List<Person> getMembers() {
        return new ArrayList<>(members);
    }

    /**
     * Retrieves the name of the team.
     * @return The name of the team.
     */
    public String getTeamName() {
        return teamName;
    }

    /**
     * Associates a module with the team.
     * @param moduleCode The module code to associate.
     */
    public void associateModule(ModuleCode moduleCode) {
        associatedModules.add(moduleCode);
    }

    /**
     * Disassociates a module from the team.
     * @param moduleCode The module code to disassociate.
     */
    public void disassociateModule(ModuleCode moduleCode) {
        associatedModules.remove(moduleCode);
    }

    /**
     * Retrieves the modules associated with the team.
     * @return The list of associated modules.
     */
    public List<ModuleCode> getAssociatedModules() {
        return new ArrayList<>(associatedModules);
    }

    /**
     * Associates a tutorial class with the team.
     * @param tutorialClass The tutorial class to associate.
     */
    public void associateTutorialClass(TutorialClass tutorialClass) {
        associatedTutorialClasses.add(tutorialClass);
    }

    /**
     * Disassociates a tutorial class from the team.
     * @param tutorialClass The tutorial class to disassociate.
     */
    public void disassociateTutorialClass(TutorialClass tutorialClass) {
        associatedTutorialClasses.remove(tutorialClass);
    }

    /**
     * Retrieves the tutorial classes associated with the team.
     * @return The list of associated tutorial classes.
     */
    public List<TutorialClass> getAssociatedTutorialClasses() {
        return new ArrayList<>(associatedTutorialClasses);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Team)) {
            return false;
        }
        Team team = (Team) o;
        return Objects.equals(teamName, team.teamName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(teamName);
    }

    @Override
    public String toString() {
        return "Team{"
            + "teamName='" + teamName + '\''
            + ", members=" + members + '}';
    }
}

