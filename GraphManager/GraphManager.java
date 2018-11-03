import java.io.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.net.*;
import java.beans.*;
import javax.imageio.*;
import java.awt.image.*;
import java.nio.*;
import draw.ui.GraphPanel;
import server.*;
public class GraphManager implements MouseMotionListener,ActionListener
{
	private GraphPanel gp1=new GraphPanel();
private JFrame f1=new JFrame("::");
private JScrollPane sp1;
private JPopupMenu pmenu=new JPopupMenu();
	String itemData[]={"Save Result As Image","Change Colors","Exit"};
JMenuItem mitems[]=new JMenuItem[itemData.length];
	public GraphManager()
{
GraphServer gs1=new GraphServer();
gs1.setPointReceiveListener(new PointReceiveListener()
{
public void onPointReceive(double x,double y)
{
	System.out.println("Point Received "+x+" "+y);
	gp1.addPoint(x,y);
	SwingUtilities.invokeLater(new Runnable()
	{
		public void run()
		{
	f1.pack();
}
});
}
});
f1.setUndecorated(true);
f1.setBackground(new Color(10,10,10,140));
gp1.addMouseWheelListener(new MouseWheelListener()
{
public void mouseWheelMoved(MouseWheelEvent e)
{
	int uSize=gp1.getUnitSize();
	System.out.println(e.getWheelRotation());
	if(e.getWheelRotation()<0)
{
	System.out.println("::::::");
gp1.setUnitSize(uSize+2);
}
else
{
	if(uSize>=4)
	{
gp1.setUnitSize(uSize-2);
	}
}
f1.pack();
JScrollBar v=sp1.getVerticalScrollBar(),h=sp1.getHorizontalScrollBar();
v.setValue(v.getMaximum());
h.setValue(h.getMaximum());
	}
});
gp1.addMouseListener(new MouseAdapter()
{
public void mouseClicked(MouseEvent e)
{
	if(e.getButton()==MouseEvent.BUTTON1)
	{
	gp1.drawLines(!gp1.isLineEnabled());
	gp1.repaint();
}
else
{
	pmenu.show(gp1,e.getX(),e.getY());
}
}
});
f1.addWindowListener(new WindowAdapter()
{
	public void windowClosing(WindowEvent e)
	{
		System.exit(0);
	}
});
gp1.addMouseMotionListener(this);
sp1=new JScrollPane(gp1,JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
sp1.setBackground(new Color(10,10,10,140));
f1.setContentPane(sp1);
f1.pack();
addPopup();
f1.setExtendedState(Frame.MAXIMIZED_BOTH);
gp1.setOrign(500,400);
gp1.drawLines(true);
gp1.setUnitSize(2);
f1.setVisible(true);
f1.repaint();
}
private static GraphPanel gPanel;
public static void main(String args[])
{
	if(args.length>=2)
	{
		long startTime=System.currentTimeMillis();
		String fileName=args[0];
		long eta=Long.parseLong(args[1]);
		GraphServer gServer=new GraphServer();
		gPanel=new GraphPanel();
		gPanel.drawLines(true);
		Rectangle d=GraphicsEnvironment.getLocalGraphicsEnvironment().
		getDefaultScreenDevice().getDefaultConfiguration().getBounds();
		int w=(int)d.getWidth(),h=(int)d.getHeight();
		gPanel.setCustomDimensions(w,h);
		gPanel.setSize(w,h);
		gPanel.setPreferredSize(new Dimension(w,h));
		gPanel.setOrign(400,400);
		gPanel.setUnitSize(6);
		gServer.setPointReceiveListener(new PointReceiveListener()
		{
			public void onPointReceive(double x,double y)
			{
				gPanel.addPoint(x,y);
				System.out.println(gPanel.getWidth()+" "+gPanel.getHeight()+"::");
			}
		});
		while((System.currentTimeMillis()-startTime)<eta){}
			Dimension dm1=gPanel.getSize();
			w=(int)dm1.getWidth();
			h=(int)dm1.getHeight();
			BufferedImage bImg=new BufferedImage(w,h,BufferedImage.TYPE_INT_RGB);
			gPanel.paint(bImg.getGraphics());
			try
			{
			ImageIO.write(bImg,"JPEG",new File(fileName));
		}
		catch(IOException ioexcepn){}
		System.exit(0);
	}
	else
new GraphManager();
}
private void addPopup()
{
for(int i=0;i<mitems.length;i++)
{
	mitems[i]=new JMenuItem(itemData[i]);
	mitems[i].addActionListener(this);
	pmenu.add(mitems[i]);
}
}
public void mouseDragged(MouseEvent e)
{

}
public void mouseMoved(MouseEvent e)
{
	int usz=gp1.getUnitSize();
	float x=(float)(e.getX()-gp1.getOrignX())/usz,y=(float)(gp1.getOrignY()-e.getY())/usz;
gp1.setToolTipText("<html><body bgcolor='black'><h1 style='color:green;background:black;'>("+x+","+y+")</h1></body></html>");
}
public void actionPerformed(ActionEvent e)
{
if(e.getSource()==mitems[0])
{
	JFileChooser fc1=new JFileChooser();
	fc1.setDialogType(JFileChooser.SAVE_DIALOG);
	fc1.setDialogTitle("Save as Image");
	if(fc1.showSaveDialog(f1)==JFileChooser.APPROVE_OPTION)
	{
	BufferedImage img=new BufferedImage(gp1.getWidth(),gp1.getHeight(),BufferedImage.TYPE_INT_RGB);
	gp1.paint(img.getGraphics());
	try
	{
		ImageIO.write(img,"JPEG",fc1.getSelectedFile());
	}
	catch(IOException ioexcepn){}
}
}
else if(e.getSource()==mitems[1])
{
	gp1.setColors(JColorChooser.showDialog(f1,"Choose Line Color",Color.gray),JColorChooser.showDialog(f1,"Choose Graph Color",Color.blue),JColorChooser.showDialog(f1,"Choose Orign Line Color",Color.green));
	gp1.repaint();
}
else
System.exit(0);
}
};
