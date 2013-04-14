//package twitter.satish.project1;

import java.awt.Desktop;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
//import java.nio.Buffer;
import java.util.*;

import javax.swing.BoundedRangeModel;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.*;
//import org.apache.commons.collections.buffer.CircularFifoBuffer;

public class CircularBufferTrial {

	/**
	 * @param args
	 */
	public static void main(String[] args)  {
		// TODO Auto-generated method stub
		
		try {
			FileOutputStream outputFile=new FileOutputStream("/home/cloudera/DIC_Twitter/HTMLFiles/wordleCloud_WordCount.html",false);
			//FileInputStream inputFile=new FileInputStream("/home/cloudera/DIC_Twitter/OutputFiles/SwappingOutput_WordCount");
			BufferedReader brReadOutput=new BufferedReader(new FileReader("/home/cloudera/DIC_Twitter/OutputFiles/SwappingOutput_WordCount"));
			PrintWriter pr=new PrintWriter( (outputFile)); 
			

			
			outputFile.write("<html>".getBytes());
			outputFile.write("<form action=\"http://www.wordle.net//advanced\" method=\"POST\">  <textarea name=\"text\" style=\"display:none\">".getBytes());
			String s1;
			byte[] b=new byte[50000000];
			Buffer buffer = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(20));
			//BoundedFifoBuffer buffer=new BoundedFifoBuffer();
			while((s1=brReadOutput.readLine())!=null)
			{
				
				buffer.add(s1);
				
			}
			String readFromBuffer=null;
			StringTokenizer st=null;
			boolean firstWord=true;
			int scaling=1;
			while(buffer.isEmpty()==false)
			{
				readFromBuffer=buffer.remove().toString();
			    st=new StringTokenizer(readFromBuffer);
			    
			    int numberOfOccurances=Integer.parseInt(st.nextToken().trim());
			    String word=st.nextToken().trim()+" \n";
			    if(firstWord==true)
			    	{
			    		scaling=numberOfOccurances;
			    		firstWord=false;
			    	}
			    numberOfOccurances=numberOfOccurances/scaling;
			    for(int j=0;j<numberOfOccurances;j++)
			    {
			    	b=word.getBytes();
		 			outputFile.write(b);
		 			b=null;
		 			
			    }
				
				System.out.println(numberOfOccurances +" | "+word);
			}
		
 		outputFile.write("</textarea> <input type=\"submit\" name=\"submit\"> </form>".getBytes());
		
		try
		  {
			Desktop desktop=Desktop.getDesktop();
			InetAddress ad=InetAddress.getLocalHost();
			if( java.awt.Desktop.isDesktopSupported() ) {
  			    try {
          			 java.net.URI uri = new java.net.URI("file:///home/cloudera/DIC_Twitter/HTMLFiles/wordleCloud_WordCount.html");
          			 desktop.browse( uri );
      				}
      				catch ( Exception e ) {
					    e.printStackTrace();
  							    }

		          }
	           }catch(Exception e){ e.printStackTrace();}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
		
	}
		
		
		
		
	}

