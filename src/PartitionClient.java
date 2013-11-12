/**
 * main method entrance for partition corpus
 *
 */
public class PartitionClient {

	public static void main(String[] args){
		String test = "-n 50 -t random";
		args = test.split(" ");
		
		CommandLineInterface cli= new CommandLineInterface();
		int splits = Integer.parseInt(cli.parseCommandLine(args).getOptionValue("n"));
		String name = cli.parseCommandLine(args).getOptionValue("t");
		
		Corpus corpus = new Corpus();
		
		Partitioner partitioner = PartitionerFactory.getPartitioner(name);
		partitioner.partition(corpus, splits);
		
	}
	
}
