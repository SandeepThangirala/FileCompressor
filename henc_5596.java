//Sandeep Thangirala cs610 PP 5596
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class henc_5596 {	
	static int [] bufferinputint =null;
	static Map<Integer, Integer> inputCounter;
	static pqueue_5596 Pqueue = new pqueue_5596();
	
	private static void encode_5596(String filename)
	{
		StringBuilder contentString = new StringBuilder();
		inputCounter = new HashMap<Integer, Integer>();
		 for(int i=0;i<bufferinputint.length;i++)
		 {
			 if (inputCounter.get(bufferinputint[i]) == null) {
				 inputCounter.put(bufferinputint[i], 1);
			    } else {
			      int val = inputCounter.get(bufferinputint[i]);
			      inputCounter.put(bufferinputint[i], ++val);
			    }
		 }		 
		 	saveMap_5596(inputCounter,filename);
		 	try {
				Pqueue.findPrefixCodes(inputCounter);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	//Pqueue.display();	    	
	    	for(int i=0;i<bufferinputint.length;i++)
	    	{
	    		contentString.append(Pqueue.byteBinStringHashMap.get(bufferinputint[i]));	    		
	    	}
	    	//System.out.println(contentString.toString());
			try {
				WriteArray_5596(filename+".huf",contentString);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}		
	}

	private static void readFileInputStream_5596(String filename) throws IOException 
	{
		DataInputStream  instr;
		File a_file = new File(filename);
		int length = (int)a_file.length();
		bufferinputint = new int[length];
		try
		{
			instr = new DataInputStream(new BufferedInputStream(new FileInputStream( filename )));			
			try
		      {
		        for(int i=0;i<bufferinputint.length;i++)
		        {
		        	bufferinputint[i] = instr.read();		          
		        }
		        instr.close();
		      }
		      catch ( EOFException  eof )
		      {		        
		        instr.close();
		        return;
		      }
				encode_5596(filename);				
		}
		catch(Exception e){ e.printStackTrace();}finally{}		 
	}
	
	public static void saveMap_5596(Map<Integer, Integer> inputCounter2, String filename)
	{
		StringBuilder savedMapString=new StringBuilder();
		Iterator it = inputCounter2.entrySet().iterator();
	    while (it.hasNext()) {
	        Map.Entry pair = (Map.Entry)it.next();
	        savedMapString.append(pair.getKey()+" "+ pair.getValue()+" ");
	    }
	    try {
	    	WriteArrayOriginal_5596(filename+".huf",savedMapString.toString().getBytes());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}	  
	 
	public static void WriteArrayOriginal_5596(String fileTo, byte[] output) throws IOException{
		  OutputStream outp = null;		  
		  outp = new BufferedOutputStream(new FileOutputStream(fileTo));
			 try
			 {				 
				 for (int i = 0; i < output.length; i ++) {
					 outp.write(output[i]);
				 }			    
		     }
			 finally
			 {
				 outp.write('\n'); 
				 if(outp !=null)
					 outp.close();
		     }			 
	  }

	
	public static void WriteArray_5596(String fileTo, StringBuilder output) throws IOException
	{
		while (output.length() % 8 != 0)
			output.append("0"); 
		OutputStream outp = null;
		 try
		 {
			 outp = new FileOutputStream(fileTo,true);
			 int len=output.length();
			 for (int i = 0; i < len; i += 8) 			 
			 {				 
				 		int parsedByte=0;
				        String byteString = output.substring(i, i + 8);
				        parsedByte = 0xFF & Integer.parseInt(byteString, 2);
				        outp.write((byte)parsedByte);
			 }
		 }
		 finally
		 {
		    if(outp !=null)
		    	outp.close();
		 }
	}
	
	public static void main(String args[])
	{
		try {
			if(!args[0].contains(".huf") && !args[0].isEmpty())
			{
			readFileInputStream_5596(args[0]);
			}
			else
			{
				System.out.println("This file has .huf extenstion. Make sure if the file is correct.");
				System.exit(0);
			}			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
