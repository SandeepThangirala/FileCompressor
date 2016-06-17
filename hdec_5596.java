//Sandeep Thangirala cs610 PP 5596
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Map;


public class hdec_5596 {
	static int [] bufferinputint =null;
	static Map<Integer, Integer> inputCounter;
	static pqueue_5596 Pqueue = new pqueue_5596();	
	static int countTree=0;
	static int total=0;
	
	private static void decode_5596(String fileName)
	{		 
		 String encodedbinary="";
		 try {
			Pqueue.findPrefixCodes(inputCounter);
			encodedbinary=readEncodedFile_5596(fileName);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}		 
	    	try {	    		
				traverseCodedString_5596(Pqueue.root,new StringBuilder(encodedbinary),fileName.replace(".huf", ""));	    		
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//Pqueue.display();		
	}
	
	

	public static void traverseCodedString_5596(node_5596 root,StringBuilder code,String fileTo) throws IOException
	{
		node_5596 loopHead=root;
		node_5596 current=root;
		DataOutputStream outstr;
		try{
				outstr = new DataOutputStream(new BufferedOutputStream(new FileOutputStream( fileTo)));
				int len=code.length();
				int i=0,counterBytes=0;
					while(i<len && counterBytes<total)
					{
							if(current!=null)
							{
								if(current.isLeaf() && current!=null)
					            {																	
									outstr.write((byte)current.ch); // write a byte
									current=loopHead;
									counterBytes++;
					            }
								else if(code.charAt(i)=='0')
								{				
									current=current.leftNode; i++;
								}
								else if(code.charAt(i)=='1')
								{
									current=current.rightNode; i++;
								}
								else
								{
									System.out.println("Wrong Input");
								}
							}
					}
					if(current.isLeaf() && current!=null)
			        {
						outstr.write(current.ch); // write a byte
						current=loopHead;
			        }
					outstr.close();
			}
			finally{}
    }
	
	
	
	private static void readFileInputStream_5596(String filename) throws IOException 
	{
		DataInputStream  instr;
		try
		{
		File a_file = new File(filename);
		inputCounter = new HashMap<Integer, Integer>();
		readFile_5596(filename);
		if(inputCounter.entrySet().size()==1)
		{
			int key=0,value=0;
			for (Map.Entry<Integer,Integer> entry : inputCounter.entrySet()) {
	    	    		key=entry.getKey();
	    	    		value=entry.getValue();
	    	}
			OutputStream outp = null;
			 try
			 {
				 outp = new FileOutputStream(filename.replace(".huf", ""));
				 for (int i = 0; i < value; i++) 
				 {			 		
					        outp.write(key); // write a byte
				 }
			 }catch(Exception e){e.printStackTrace();}
			 finally
			 {
			    if(outp !=null)
			    	outp.close();
			 }
		}
		else
		{
		int length = (int)a_file.length();
		bufferinputint = new int[length];
		
			instr = new DataInputStream(new BufferedInputStream(new FileInputStream( filename )));			
			try
		    {
			        for(int i=0;i<bufferinputint.length;i++)
			        {
			        	bufferinputint[i] = instr.readByte();		          
			        }
			        instr.close();
		    }
		    catch ( EOFException  eof )
		    {		        
		        instr.close();
		        return;
		    }
			decode_5596(filename);
		}
		}finally{}
		
	}
	
	private static void readFile_5596(String aFileName) throws IOException {	
		try
		{
		FileInputStream fstream = new FileInputStream(new File(aFileName));
        DataInputStream in = new DataInputStream(fstream);
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String strLine="";
        String[] tokens=new String[512];
        int counter=0;
        while ( counter==0 && (strLine = br.readLine()) != null )   {
        	tokens = strLine.split(" ");
        	counter++;
	  }
         countTree=strLine.length()+1;
        for(int i=0;i<tokens.length;i=i+2)
        {
        	int key=Integer.parseInt(tokens[i]);
        	int value=Integer.parseInt(tokens[i+1]);
        		inputCounter.put(key,value);
        		total+=value;
        }	
        br.close();
        }catch(Exception e){e.printStackTrace();}
		
	  }
	
	
	@SuppressWarnings("resource")
	private static String readEncodedFile_5596(String aFileName)  throws IOException 
	{
		DataInputStream  instr;
		File a_file = new File(aFileName);
		int length = (int)a_file.length();
		byte[] data = new byte [length];
		int freq[] = new int[data.length-countTree];
		try
		{
			instr = new DataInputStream(new BufferedInputStream(new FileInputStream( aFileName )));			
			try
			{
		        for(int i=0;i<data.length;i++)
		        {
		        	data[i] = instr.readByte();		          
		        }
		        StringBuilder encodedStringafterDecode=new StringBuilder();
				for (int i = 0; i < data.length-countTree;i++) 
				{
			    	  freq[i]=data[countTree+i] & 0xff;
			    	  encodedStringafterDecode.append("00000000".substring(Integer.toBinaryString(freq[i]).length())+Integer.toBinaryString(freq[i]));
			    }
               //System.out.println(encodedStringafterDecode.toString());
				return encodedStringafterDecode.toString();				
			}finally{}
		}finally{}
	}

	public static void main(String args[])
	{
		try 
		{
			if(args[0].contains(".huf") && !args[0].isEmpty())
			{
			readFileInputStream_5596(args[0]);
			}
			else
			{
				System.out.println("This file does not have .huf extenstion. Make sure if the file is correct.");
				System.exit(0);
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
