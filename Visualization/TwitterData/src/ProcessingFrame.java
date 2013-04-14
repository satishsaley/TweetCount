import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JLabel;


public class ProcessingFrame extends JFrame implements Runnable{
	
	
	
	public ProcessingFrame()
	{
		this.setSize(250, 100);
		
		this.setTitle("Processing");
		//this.setLayout(new FlowLayout());
		//this.add(new JLabel("Please wait while processing..\n Please click on the Submit button\n when browser is invoked"));
		
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

	
}
