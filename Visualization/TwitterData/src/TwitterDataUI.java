import java.awt.Color;
import java.awt.Desktop;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.util.StringTokenizer;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import org.apache.commons.collections.Buffer;
import org.apache.commons.collections.BufferUtils;
import org.apache.commons.collections.buffer.CircularFifoBuffer;


public class TwitterDataUI {

	/**
	 * @param args
	 */
	public static void showWordCloud(String inputFile_FinalOutputOfMR,String outputHtmlFile)
	{
		try {
			FileOutputStream outputFile=new FileOutputStream("/home/cloudera/DIC_Twitter/HTMLFiles/"+outputHtmlFile,false);
			//FileInputStream inputFile=new FileInputStream("/home/cloudera/DIC_Twitter/OutputFiles/SwappingOutput_WordCount");
			BufferedReader brReadOutput=new BufferedReader(new FileReader("/home/cloudera/DIC_Twitter/OutputFiles/"+inputFile_FinalOutputOfMR));
			PrintWriter pr=new PrintWriter( (outputFile)); 		
			outputFile.write("<html><script type=\"text/javascript\">function autosubmitfunc () { var frm = document.getElementById(\"f1\"); frm.submit();} window.onload = autosubmitfunc;</script>".getBytes());
			outputFile.write("<form id=\"f1\" action=\"http://www.wordle.net//advanced\" method=\"POST\">  <textarea name=\"text\" style=\"display:none\">".getBytes());			String s1;
			byte[] b=new byte[50000000];
			Buffer buffer = BufferUtils.synchronizedBuffer(new CircularFifoBuffer(350));
			//BoundedFifoBuffer buffer=new BoundedFifoBuffer();
			while((s1=brReadOutput.readLine())!=null)
			{
				
				buffer.add(s1);
				
			}
			String readFromBuffer=null;
			StringTokenizer st=null;
			boolean firstWord=true;
			int scaling=1;
			//while(buffer.isEmpty()==false)
			for(int k=0;k<348;k++)
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
			    numberOfOccurances=numberOfOccurances/100+1;
			    if(numberOfOccurances>10)
			    {
			        numberOfOccurances=10+numberOfOccurances/10;
			    }
			    for(int j=0;j<numberOfOccurances;j++)
			    {
			    	b=word.getBytes();
		 			outputFile.write(b);
		 			b=null;
		 			
			    }
				
				//System.out.println(numberOfOccurances +" | "+word);
			}		
 		outputFile.write("</textarea>  </form>".getBytes());	
 		//Automatically launch default web browser and show the word cloud
		try
		  {
			Desktop desktop=Desktop.getDesktop();
			InetAddress ad=InetAddress.getLocalHost();
			if( java.awt.Desktop.isDesktopSupported() ) {
  			    try {
          			 java.net.URI uri = new java.net.URI("file:///home/cloudera/DIC_Twitter/HTMLFiles/"+outputHtmlFile);
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
	
	
	
	public static void main(String[] args) {
		// TODO Auto-generated method stub

		File currentDirectory = new File("/home/cloudera/DIC_Twitter/InputFiles/"); 

		File[] filesInSharedDir=currentDirectory.listFiles();

		//String [] availableFiles=new String[100];
		int sizeOfDir=0;
		for (File f : filesInSharedDir) {
			
			sizeOfDir++;
			//System.out.println(f.getName());
		}
		
		String[] availableFiles=new String[sizeOfDir];
		int i=0;
		
		for (File f : filesInSharedDir) {
			
			availableFiles[i]=f.getName();
			i++;
		}
		
		final JLabel title=new JLabel("Data Intensive Computing on Twitter Data");
		final JLabel jlabelInput=new JLabel("Input File:");
		
		final JLabel jlabelProgram=new JLabel("Program :");
		final JButton jGo=new JButton("Calculate");
		
		@SuppressWarnings("rawtypes")
		String[] items={"SimpleWordCount","HashTagCount","@Count"}; 
		final JComboBox jcb_InputFiles=new JComboBox(availableFiles);
		final JComboBox jcb_program=new JComboBox(items);
		//final JTextArea //jta=new JTextArea(10, 23);
		//jta.setForeground(Color.GREEN);
		//JScrollPane sp = new JScrollPane(jta);
		
		//sp.setVisible(true);
		final DIC_TwitterFrame frame=new DIC_TwitterFrame();
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		title.setForeground(Color.BLUE);
		
		
		frame.add(title);
		
	    frame.setLayout(new FlowLayout());
	    
	    frame.add(jlabelInput);
	    frame.add(jcb_InputFiles);
	    
	    frame.add(jlabelProgram);
	    frame.add(jcb_program);
	    
	    frame.add(jGo);
	    //frame.add(sp);
	    final ProcessingFrame processFrame=new ProcessingFrame();
		processFrame.add(new JLabel("Please wait while processing"));
		
	    jGo.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				String fileSelected=jcb_InputFiles.getSelectedItem().toString().trim();
				String programSelected=jcb_program.getSelectedItem().toString().trim();
				String nameOfJarFile=null,nameOfIntermediateOutput=null,nameOfFinalOutput=null,nameOfFinalOutputOnLocalFS=null,nameOfClass=null,cmdToCopy=null;
				String inputFile_FinalOutputOfMR=null,outputHtmlFile=null;
				
				if(programSelected.equals("SimpleWordCount"))
				{
					nameOfJarFile="SimpleWordCount.jar";
					nameOfIntermediateOutput="IntermediateOutputSimpleWordCount";
					nameOfFinalOutput="FinalOutputSimpleWordCount";
					nameOfFinalOutputOnLocalFS="ResultSimpleWordCount";
					nameOfClass="TweetCount";
					//cmdToCopy="./home/cloudera/DIC_Twitter/JavaFiles/HashTagCopyOutput.sh";
					outputHtmlFile="wordleCloud_WordCount.html";
				}
				else if(programSelected.equals("HashTagCount"))
				{
					nameOfJarFile="HashWordCount.jar";
					nameOfIntermediateOutput="IntermediateOutputHashWordCount";
					nameOfFinalOutput="FinalOutputHashWordCount";
					nameOfFinalOutputOnLocalFS="ResultHashWordCount";
					nameOfClass="HashWordCount";
					//cmdToCopy="./home/cloudera/DIC_Twitter/JavaFiles/HashTagCopyOutput.sh";
					outputHtmlFile="wordleCloud_HashTags.html";
				}
				else if(programSelected.equals("@Count"))
				{
					nameOfJarFile="AtTheRateWordCount.jar";
					nameOfIntermediateOutput="IntermediateOutputAtTheRateWordCount";
					nameOfFinalOutput="FinalOutputAtTheRateWordCount";
					nameOfFinalOutputOnLocalFS="ResultAtTheRateWordCount";
					nameOfClass="AtTheRateWordCount";
					//cmdToCopy="./home/cloudera/DIC_Twitter/JavaFiles/HashTagCopyOutput.sh";
					outputHtmlFile="wordleCloud_AtTheRate.html";
				}
				else
				{
					System.out.println("exiting 1");
					//System.exit(-1);
				}
					
					//copy the files which user has selected to the hadoop fs
					
					processFrame.setVisible(true);
					
					String cmd = "hadoop fs -put /home/cloudera/DIC_Twitter/InputFiles/"+fileSelected+" /user/cloudera/DIC_Twitter/Input ";
					
					Runtime run = Runtime.getRuntime();
					
					Process prCopying;
					try {
						
						//jta.append("1.Putting the selected file in HDFS\n");
						
						prCopying = run.exec(cmd);
						prCopying.waitFor();
						if(prCopying.exitValue()==0)
						{
							System.out.println("Putting the selected file in HDFS.Operation Completed Successfully");
						     //jta.append("Operation Completed Successfully \n");
						}
						else
						{
							prCopying = run.exec("hadoop fs -rm /user/cloudera/DIC_Twitter/Input/*");
							//jta.append("Some error has occured. Exiting. \n");
							System.out.println("exiting 2");
							System.exit(-1);
						}
						
					} catch (Exception e1) {
						
						//jta.append("Exception. Exiting");
						//System.exit(-1);
						e1.printStackTrace();
					}
					
					
					
					//run the jar file
					
					cmd ="hadoop jar /home/cloudera/DIC_Twitter/JarFiles/"+nameOfJarFile+" com.satish.hadoop."+nameOfClass+" /user/cloudera/DIC_Twitter/Input/"+ fileSelected+" /user/cloudera/DIC_Twitter/"+nameOfIntermediateOutput;
					
					Process prRun;
					try {
						//jta.append("2.Calculating count.May take few minutes.\n");
						prRun = run.exec(cmd);
						prRun.waitFor();
						if(prRun.exitValue()==0)
						{
							System.out.println("Run the Mappreduce.");
							//jta.append("Operation Completed Successfully \n");
						}
						else
						{
							prRun = run.exec("hadoop fs -rm /user/cloudera/DIC_Twitter/Input/*");
							prRun = run.exec("hadoop fs -rm -r /user/cloudera/DIC_Twitter/Inter*");//+nameOfIntermediateOutput);
							//jta.append("Some error has occured. Exiting. \n");
							System.out.println("exiting 3");
							System.exit(-1);
						}
						
					} catch (Exception e1) {
						//jta.append("Exception. Exiting");
						//System.exit(-1);
						e1.printStackTrace();
					}
					//--------------------------------------------------------
					
					
					//sort intermediate results
					Process prSort;
										
					cmd ="hadoop jar /home/cloudera/DIC_Twitter/JarFiles/SortingResults.jar com.satish.hadoop.SortingResults /user/cloudera/DIC_Twitter/"+nameOfIntermediateOutput+"/part* /user/cloudera/DIC_Twitter/"+nameOfFinalOutput;
					
					try {
						//jta.append("3.Sorting the count.\n");
						prSort = run.exec(cmd);
						prSort.waitFor();
						if(prSort.exitValue()==0)
						{
							System.out.println("Sort intermediate results.Operation Completed Successfully ");
							//jta.append("Operation Completed Successfully \n");
						}
						else
						{
							prSort = run.exec("hadoop fs -rm /user/cloudera/DIC_Twitter/Input/*");
							prSort = run.exec("hadoop fs -rm -r /user/cloudera/DIC_Twitter/Inter*");//+nameOfIntermediateOutput);
							prSort = run.exec("hadoop fs -rm -r /user/cloudera/DIC_Twitter/Final*");//+nameOfFinalOutput);
							//jta.append("Some error has occured. Exiting. \n");
							System.out.println("exiting 4");
							System.exit(-1);
						}
					} catch (Exception e1) {
						//jta.setText("Exception. Exiting");
						//System.exit(-1);
						e1.printStackTrace();
					}
					//----------------------------------------------------
					
					
			
					//Copying Output to Local File System from HDFS
					
					cmd="hadoop fs -copyToLocal /user/cloudera/DIC_Twitter/"+nameOfFinalOutput+"/part* /home/cloudera/DIC_Twitter/OutputFiles/"+nameOfFinalOutputOnLocalFS;
					
					
					Process prCopyOutput;
					try {
						//jta.append("5.Copying Output to Local File System from HDFS.\n");
						//Process prremoveOutput = run.exec("rm -r /home/cloudera/DIC_Twitter/OutputFiles/"+nameOfFinalOutputOnLocalFS);
						String[] b = new String[] {"/bin/sh", "-c", "rm -r /home/cloudera/DIC_Twitter/OutputFiles/*"};  
						Runtime.getRuntime().exec(b);  

						//prremoveOutput.waitFor();
						prCopyOutput = run.exec(cmd);
						prCopyOutput.waitFor();
						if(prCopyOutput.exitValue()==0)
						{
							System.out.println("Copying Output to Local File System from HDFS.Operation Completed Successfully.");
							//jta.append("Operation Completed Successfully \n");
						}
						else
						{
							//prCopyOutput = run.exec(cmd);
							prCopyOutput = run.exec("hadoop fs -rm /user/cloudera/DIC_Twitter/Input/*");
							prCopyOutput = run.exec("hadoop fs -rm -r /user/cloudera/DIC_Twitter/Inter*");//+nameOfIntermediateOutput);
							prCopyOutput = run.exec("hadoop fs -rm -r /user/cloudera/DIC_Twitter/Final*");//+nameOfFinalOutput);
							//jta.append("Some error has occured. Exiting. \n");
							System.out.println("exiting 5" + prCopyOutput.getErrorStream());
							System.exit(-1);
						}
						
					} catch (Exception e1) {
						//jta.append("Exception. Exiting");
						//System.exit(-1);
						e1.printStackTrace();
					}
					//----------------------------------------------------------------------
					
					
					
					//delete files and folders
					
					cmd="hadoop fs -rm -r /user/cloudera/DIC_Twitter/Inter*";//+nameOfIntermediateOutput;
					Process prDelete1;
					try {
						//jta.append("6.Deleting files from HDFS.\n");
						prDelete1 = run.exec(cmd);
						prDelete1.waitFor();
						
						cmd="hadoop fs -rm -r /user/cloudera/DIC_Twitter/Final*";//+nameOfIntermediateOutput;
						Process prDelete2 = run.exec(cmd);
						prDelete2.waitFor();
						
						cmd="hadoop fs -rm /user/cloudera/DIC_Twitter/Input/*";
						Process prDelete3 = run.exec(cmd);
						prDelete3.waitFor();
						if(prDelete1.exitValue()==0 ||prDelete2.exitValue()==0||prDelete3.exitValue()==0)
						{
							System.out.println("Deleting files from HDFS.Operation Completed Successfully.");
							//jta.append("Operation Completed Successfully \n");
						}
						else
						{
							//prCopyOutput = run.exec(cmd);
							prDelete1 = run.exec("hadoop fs -rm /user/cloudera/DIC_Twitter/Input/*");
							prDelete2 = run.exec("hadoop fs -rm -r /user/cloudera/DIC_Twitter/Inter*");//+nameOfIntermediateOutput);
							prDelete3 = run.exec("hadoop fs -rm -r /user/cloudera/DIC_Twitter/Final*");//+nameOfFinalOutput);
							//jta.append("Some error has occured. Exiting. \n");
							System.out.println("exiting ");
							System.exit(-1);
						}
					} catch (Exception e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					//---------------------------------------------------------
					
					
					showWordCloud(nameOfFinalOutputOnLocalFS, outputHtmlFile);
					
					processFrame.dispose();
				}
		

		});
		
	}

}