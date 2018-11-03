package server;
import java.net.*;
import java.io.*;
import java.util.*;
public class GraphServer
{
	private static final int PORT=9988;
	private PointReceiveListener prl;
private Runnable listen=new Runnable()
{
public void run()
{
	try
	{
	ServerSocket ss1=new ServerSocket(PORT);
	while(true)
	{
	Socket s1=ss1.accept();
	Scanner sc1=new Scanner(s1.getInputStream());
	while(sc1.hasNext())
	{
	try
	{
	double x=sc1.nextDouble();
	if(!sc1.hasNext())
	break;
	double y=sc1.nextDouble();
	System.out.println("_______________");
	if(prl!=null)
	prl.onPointReceive(x,y);
	}
	catch(Exception e)
	{
		e.printStackTrace();
		break;
	}
	}
	sc1.close();
	s1.close();
}
}
catch(IOException ioexcepn)
{
	ioexcepn.printStackTrace();
}
}
};
public GraphServer()
{
start();
}
public void start()
{
	Thread thrd1=new Thread(listen);
	thrd1.start();
}
public void setPointReceiveListener(PointReceiveListener prl)
{
this.prl=prl;
}
};
