package partition;

public class PartitionerFactory{
	
	public static Partitioner getPartitioner(String name, Corpus corpus, int splits){
		if (name.equals("random"))
			return new RandomPartitioner(corpus, splits);
		else {
			throw new IllegalArgumentException(name + "is not a valid partitioner name");
		}
	}
}
