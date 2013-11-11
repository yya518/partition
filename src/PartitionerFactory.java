package src;

public class PartitionerFactory{
	
	public static Partitioner getPartitioner(String name){
		if (name.equals("random"))
			return new RandomPartitioner();
		else {
			throw new IllegalArgumentException(name + "is not a valid partitioner name");
		}
	}
}
