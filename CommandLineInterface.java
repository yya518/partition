import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.cli.PosixParser;


public class CommandLineInterface {
	protected static final String DEFAULT_USAGE = "[-h] [-n] [-t]";
    private final Options options = new Options();

	public void addOption(){
		options.addOption("h", false, "Print help for the corpus partition application");
		options.addOption("n", "size", true, "The number of partitions");
		options.addOption("t", "type", true, "The partitioner type");
	}
    /**
     * Print out application usage and exit with return code -1.
     */
    protected void printHelpAndExit() {
        final int screenWidth = 80;
        HelpFormatter formatter = new HelpFormatter();
        formatter.printHelp("corpus partition", this.options, true);
        System.exit(-1);
    }
	
    protected void validateCommandLine(final CommandLine cmdLine) throws ParseException{
    	if ( cmdLine.hasOption("h") )
    		printHelpAndExit();
    	if ( !cmdLine.hasOption("n") || !cmdLine.hasOption("t"))
    		throw new ParseException(
                    "n, t are required arguments");
    }
    
    /**
     * Parse the command line, show help if required, exit if error detected.
     *
     * @param args the original command line args we want parsed
     * @return CommandLine object or show help and System.exit if error
     */
    public CommandLine parseCommandLine(final String[] args) {
    	addOption();
        CommandLineParser parser = new PosixParser();
        try {
            CommandLine cmdLine = parser.parse(this.options, args);
            validateCommandLine(cmdLine);

            return cmdLine;
        } catch (ParseException e) {
            System.err.println(e.getMessage());
            printHelpAndExit();
        }
        return null;
    }
    
}
