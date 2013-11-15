package partition;
/**
 * main method entrance for partition corpus
 *
 */
public class PartitionClient {

	public static void main(String[] args) throws Exception{
		String test = "-n 50 -t batch -f /home/yyyn/partition/dataset/brown/brown.index";
		args = test.split(" ");
		
		CommandLineInterface cli= new CommandLineInterface();
		int splits = Integer.parseInt(cli.parseCommandLine(args).getOptionValue("n"));
		String name = cli.parseCommandLine(args).getOptionValue("t");

		String indexFile = cli.parseCommandLine(args).getOptionValue("f");
		Corpus corpus = new Corpus(indexFile);
		
		checkValid(corpus, splits);
		
		Partitioner partitioner = PartitionerFactory.getPartitioner(name, corpus, splits);
		partitioner.partition();
		//partitioner.writeToDisk();
		partitioner.countType();
	}

	private static void checkValid(Corpus corpus, int splits) {
		if(corpus.sentences.size() < splits){
			System.out.println("corpus size is less than splits size. No need for partition");
			System.exit(-1);
		}
	}
	
}
