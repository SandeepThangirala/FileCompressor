//Sandeep Thangirala cs610 PP 5596
public class node_5596 {
	int ch;
    int freqency;
    node_5596 nextNode,leftNode,rightNode;
    node_5596(){
    	ch=0;
        freqency='0';
        nextNode=leftNode=rightNode=null;
    }
    node_5596(node_5596 t){
        this.ch=t.ch;
        this.freqency=t.freqency;
        this.leftNode=t.leftNode;
        this.rightNode=t.rightNode;
        this.nextNode=null;
    }
    node_5596(int ch,int freq,node_5596 left,node_5596 right){
        this.ch=ch;
        this.freqency=freq;
        this.leftNode=left;
        this.rightNode=right;
        this.nextNode=null;
    }    
    public boolean isLeaf(){
        return leftNode==null && rightNode == null;
    }

}

