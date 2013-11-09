
public class DocumentPartition {

	public static void main(String[] args){
		Partitioner partitioner = PartitionerFactory.getPartitioner("random");
		partitioner.partition();
	}
}
