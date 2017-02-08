
import java.util.Map;
import java.util.TreeMap;
import java.util.HashMap;
import java.io.IOException;

public class pqueue_5596 {
		int count,n=0;
	  	node_5596 initialNode,root;
	    static Map<Integer, String> byteBinStringHashMap= new HashMap<Integer,String>();
	    pqueue_5596(){
	    	initialNode=null;
	    }
	    public void insertNode(node_5596 insertNode){
	        count++;
	        if(initialNode==null){
	            initialNode=insertNode;
	        }
	        else if(initialNode.freqency>insertNode.freqency){
	        	insertNode.nextNode=initialNode;
	            initialNode=insertNode;
	        }
	        else{
	        	node_5596 temp = initialNode;
	        	node_5596 prev = null;
	            while(temp.nextNode!=null && temp.freqency<=insertNode.freqency){
	                prev=temp;
	                temp=temp.nextNode;
	            }
	            if(temp.freqency>insertNode.freqency){
	                prev.nextNode=insertNode;
	                insertNode.nextNode=temp;
	            }
	            else{
	             temp.nextNode=insertNode;
	            }

	        }
	    }
	    public node_5596 deleteNode(){
	        count--;
	        if(initialNode==null){
	            System.out.println("Cannot Delete the node");
	            return null;
	        }
	        else{
	        	node_5596 temp= new node_5596(initialNode);
	            initialNode=initialNode.nextNode;
	            return temp;
	        }
	    }
	    
	    public void display(){
	    	for (Map.Entry<Integer,String> entry : byteBinStringHashMap.entrySet()) {
	    	    System.out.println(entry.getKey()+" : "+entry.getValue());
	    	}	    	
	    }

	    public node_5596 findPrefixCodes(Map<Integer, Integer> byteFreqHashMap)throws IOException
	    {	     
	                initialNode=null;
	                Map<Integer, Integer> sortedMap = new TreeMap<Integer, Integer>(byteFreqHashMap);
	                
	                for (Map.Entry<Integer, Integer> entry : sortedMap.entrySet()) {
	                	Integer key = entry.getKey();
	                    Integer value = entry.getValue();
	                    insertNode(new node_5596(key,value,null,null));
	                }
	           
	                while(count>1){
	                	node_5596 x1=deleteNode();
	                	node_5596 x2=deleteNode();
	                     insertNode(new node_5596(0,x1.freqency + x2.freqency,x1,x2));
	                 }
	                root=deleteNode();
	                traverseTree(root,"");
	                return root;
	    }
	    
	    	public void traverseTree(node_5596 root,String prefixcode){
	        if(root!=null){
	            if(root.isLeaf())
	            {
	            		byteBinStringHashMap.put(root.ch,prefixcode);	            
	            }
	            traverseTree(root.leftNode,prefixcode+"0");
	            traverseTree(root.rightNode,prefixcode+"1");
	        }
	    }
}
