package draw.ui;
import java.awt.*;
import javax.swing.*;
import java.util.*;
public class GraphPanel extends JPanel
{
	private ArrayList<Point> points=new ArrayList<Point>();
	private Point orign;
	private boolean lines=false;
	private int unitSize=2;
	private Color lineColor=Color.gray,gColor=Color.blue,olineColor=Color.green;
	private int maxx=0,maxy=0;
	private Point oorign=new Point();
	private boolean customDimensions=false;
	private int customWidth=0,customHeight=0;
public GraphPanel()
{
super();
}
public GraphPanel(int ox,int oy)
{
orign=new Point(ox,oy);
}
public void setCustomDimensions(int width,int height)
{
	customDimensions=true;
customWidth=width;
customHeight=height;
}
public void paint(Graphics g)
{
	g.clearRect(0,0,getWidth(),getHeight());
	if(orign==null)
		orign=new Point(0,getHeight());
int ox=(int)orign.getX(),oy=(int)orign.getY();
if(lines)
{
	g.setColor(lineColor);
int w=getWidth(),h=getHeight();
for(int i=ox;i<=w;i+=unitSize)
	g.drawLine(i,0,i,h);
for(int i=ox;i>=0;i-=unitSize)
	g.drawLine(i,0,i,h);	
for(int i=oy;i<=h;i+=unitSize)
	g.drawLine(0,i,w,i);
for(int i=oy;i>=0;i-=unitSize)
	g.drawLine(0,i,w,i);	
g.setColor(olineColor);
g.drawLine(ox,0,ox,h);
g.drawLine(0,oy,w,oy);
}
g.setColor(gColor);
int ppx=ox,ppy=oy;
int sz=points.size();
for(int i=0;i<sz;i++)
{
	Point p=points.get(i);
	int x=(int)p.getX(),y=(int)p.getY();
	g.drawLine(ppx,ppy,x,y);
	g.fillRect(x-unitSize/20,y-unitSize/20,unitSize/10,unitSize/10);
	ppx=x;
	ppy=y;
}
System.out.println("maxx="+maxx+"maxy="+maxy);
}
public void setColors(Color lineColor,Color gColor,Color olineColor)
{
	this.lineColor=lineColor;
	this.gColor=gColor;
	this.olineColor=olineColor;
}
public void setOrign(int x,int y)
{
	if(orign==null)
	{
			orign=new Point(x,y);
			oorign=new Point(x,y);
		}
		else
		orign.setLocation(x,y);
}
public void drawLines(boolean dl)
{
	this.lines=dl;
}
public void setUnitSize(int usize)
{
	int oox=(int)oorign.getX(),ooy=(int)oorign.getY();
	int ox=(int)orign.getX(),oy=(int)orign.getY();
	orign.setLocation(oox,ooy);
	int len=points.size();
	int puSize=unitSize;
	unitSize=usize;
	int minx=0,miny=0;
	maxx=oox;
	maxy=ooy;
	for(int i=0;i<len;i++)
	{
		Point p=points.get(i);
		double px1=p.getX(),py1=p.getY();
		px1=(px1-ox)/puSize;
		py1=(py1-oy)/puSize*-1;
		int px=(int)((px1*usize)+oox),py=(int)(ooy-(py1*usize));
		if(minx>px)
			minx=px;
		if(miny>py)
			miny=py;
		if(maxx<px)
			maxx=px;
		if(maxy<py)
			maxy=py;
		p.setLocation(px,py);
	}
	if(minx!=0 || miny!=0)
	shiftOrignBy(minx,miny);
	SwingUtilities.invokeLater(new Runnable()
	{
public void run()
{
	setPreferredSize(new Dimension(maxx,maxy));
}		
	});
		}
public void addPoint(double x,double y)
{
	if(orign!=null)
	{
	int ox=(int)orign.getX(),oy=(int)orign.getY();
	Point p=new Point();
	int px=(int)(ox+(x*unitSize)),py=(int)(oy-(y*unitSize));
	if(px<0 || py<0)
	{
	if(px<0 && py<0)
	{
	shiftOrignBy(px,py);
	px=0;
	py=0;
	}
	else if(px<0)
	{
		shiftOrignBy(px,0);
		px=0;
	}
	else if(py<0)
	{
		shiftOrignBy(0,py);
		py=0;
	}
	SwingUtilities.invokeLater(new Runnable()
	{
public void run()
{
	setPreferredSize(new Dimension(maxx,maxy));
}		
	});
}
	if(maxx<px || maxy<py)
	{
	if(maxx<px)
		maxx=px;
	if(maxy<py)
		maxy=py;
	SwingUtilities.invokeLater(new Runnable()
	{
public void run()
{
	setPreferredSize(new Dimension(maxx,maxy));
}		
	});
	}
	p.setLocation(px,py);
points.add(p);
System.out.println((ox+(x*unitSize))+","+(oy-(y*unitSize)));
}
}
public int getUnitSize()
{
return unitSize;
}
private void shiftOrignBy(int dx,int dy)
{
	dx=Math.abs(dx);
	dy=Math.abs(dy);
	double shiftByX=orign.getX()+dx,shiftByY=orign.getY()+dy;
orign.setLocation(shiftByX,shiftByY);
maxx=(int)orign.getX();
maxy=(int)orign.getY();
int len=points.size();
for(int i=0;i<len;i++)
{
	Point p=points.get(i);
	int px=(int)p.getX()+dx,py=(int)p.getY()+dy;
	if(maxx<px)
		maxx=px;
	if(maxy<py)
		maxy=py;
	p.setLocation(px,py);
}
}
public boolean isLineEnabled()
{
	return lines;
}
public int getOrignX()
{
	return (int)orign.getX();
}
public int getOrignY()
{
	return (int)orign.getY();
}/*
public int getWidth()
{
	if(customDimensions)
		return customWidth;
	else
		return super.getWidth();
}
public int getHeight()
{
	if(customDimensions)
		return customHeight;
	else
		return super.getHeight();
}*/
public void setPreferredSize(Dimension d)
{
	if(customDimensions)
		setSize(d);
	else
		super.setPreferredSize(d);
}
};
