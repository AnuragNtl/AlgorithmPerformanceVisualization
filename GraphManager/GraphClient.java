import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.applet.*;
import java.net.*;
import java.beans.*;
import javax.imageio.*;
public class GraphClient
{
	private Socket sc1;
	public static final int PORT=9988;
	private PrintWriter outPoint;
public GraphClient(String host)throws IOException
	{
sc1=new Socket(host,PORT);
outPoint=new PrintWriter(new BufferedWriter(new OutputStreamWriter(sc1.getOutputStream())));
}
public void sendPoint(double x,double y)throws IOException
{
	System.out.println("Sending Point");
outPoint.println(x+" "+y);
outPoint.flush();
}
public boolean sendNextPointFrom(Scanner s1)throws IOException
{
	System.out.println("::");
	double x,y;
	if(!s1.hasNext())
	{
		return false;
	}
	if(s1.hasNextDouble())
	{
	x=s1.nextDouble();
	if(s1.hasNextDouble())
	{
	y=s1.nextDouble();
	sendPoint(x,y);
	}
	else
		s1.next();
	}
	else
	{
		s1.next();
		s1.next();
	}
	return true;
}
public static void main(String args[])
{
	Scanner s1;
	GraphClient gc=null;
	try
	{
	gc=new GraphClient("localhost");
	if(args.length>0)
	s1=new Scanner(new File(args[0]));
	else
		s1=new Scanner(System.in);
	if(args.length>=2)
		gc=new GraphClient(args[1]);

	while(gc.sendNextPointFrom(s1)){}
}
catch(FileNotFoundException fnfexcepn)
{
	System.err.println("File Not Found");
	System.exit(1);
}
catch(IOException ioexcepn)
{
	System.err.println("Error:Cannot send the data");
	System.exit(1);
}
	}
	};
