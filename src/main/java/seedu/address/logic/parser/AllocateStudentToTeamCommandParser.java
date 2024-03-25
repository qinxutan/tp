package seedu.address.logic.parser;

import static seedu.address.logic.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MODULECODE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_STUDENTID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TEAMNAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TUTORIALCLASS;

import java.util.stream.Stream;

import seedu.address.logic.commands.AllocateStudentToTeamCommand;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.module.ModuleCode;
import seedu.address.model.module.TutorialClass;

/**
 * Parses input arguments and creates a new AllocateStudentToTeamCommand object.
 */
public class AllocateStudentToTeamCommandParser implements Parser<AllocateStudentToTeamCommand> {

    /**
     * Parses the given {@code String} of arguments in the context of the AllocateStudentToTeamCommand
     * and returns an AllocateStudentToTeamCommand object for execution.
     *
     * @throws ParseException if the user input does not conform the expected format
     */
    public AllocateStudentToTeamCommand parse(String args) throws ParseException {
        ArgumentMultimap argMultimap =
            ArgumentTokenizer.tokenize(args, PREFIX_STUDENTID, PREFIX_INDEX, PREFIX_EMAIL,
                PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_TEAMNAME);

        if (!arePrefixesPresent(argMultimap, PREFIX_MODULECODE, PREFIX_TUTORIALCLASS, PREFIX_TEAMNAME)
            || !argMultimap.getPreamble().isEmpty()) {
            throw new ParseException(String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                AllocateStudentToTeamCommand.MESSAGE_USAGE));
        }

        Prefix allocationType;
        String allocationValue;
        ModuleCode module;
        TutorialClass tutorialClass;
        String teamName;

        allocationType = getAllocationType(argMultimap);
        allocationValue = argMultimap.getValue(allocationType)
            .orElseThrow(() -> new ParseException("Allocation value is missing"));

        module = ParserUtil.parseModuleCode(argMultimap.getValue(PREFIX_MODULECODE)
            .orElseThrow(() -> new ParseException("Module code is missing")));
        tutorialClass = ParserUtil.parseTutorialClass(argMultimap.getValue(PREFIX_TUTORIALCLASS)
            .orElseThrow(() -> new ParseException("Tutorial class is missing")));
        teamName = ParserUtil.parseTeamName(argMultimap.getValue(PREFIX_TEAMNAME)
            .orElseThrow(() -> new ParseException("Team name is missing")));

        return new AllocateStudentToTeamCommand(allocationType, allocationValue, module, tutorialClass, teamName);
    }

    /**
     * Returns the allocation type specified in the argument multimap.
     * @throws ParseException if no valid allocation type is found
     */
    private Prefix getAllocationType(ArgumentMultimap argMultimap) throws ParseException {
        if (argMultimap.getValue(PREFIX_STUDENTID).isPresent()) {
            return PREFIX_STUDENTID;
        } else if (argMultimap.getValue(PREFIX_INDEX).isPresent()) {
            return PREFIX_INDEX;
        } else if (argMultimap.getValue(PREFIX_EMAIL).isPresent()) {
            return PREFIX_EMAIL;
        } else {
            throw new ParseException("Allocation type is missing");
        }
    }

    /**
     * Returns true if none of the prefixes contains empty {@code Optional} values in the given
     * {@code ArgumentMultimap}.
     */
    private boolean arePrefixesPresent(ArgumentMultimap argumentMultimap, Prefix... prefixes) {
        return Stream.of(prefixes).allMatch(prefix -> argumentMultimap.getValue(prefix).isPresent());
    }
}
