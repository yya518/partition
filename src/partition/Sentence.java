package partition;

public class Sentence {

	public int sId; //sentence id
	public int type;
	public int token;
	public String content;
	public int clusterId; //the cluster the sentence is assigned to
	
	public Sentence(String content, int id, int token, int type){
		this.sId = id;
		this.content = content;
		this.type = type;
		this.token = token;
	}
	
	public void setClusterId(int id){
		this.clusterId = id;
	}
}
