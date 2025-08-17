
package practiceassignment;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Ellipse2D;
import static java.lang.System.out;

class MyDialog extends Dialog
{
	Button btnyes,btnno;
	MyDialog(Myframe1 ref)
	{
		super(ref,true);
		MyDialog temp = this;
		setSize(400,300);
		setTitle("Please confirm");
		setLayout(null);
		setLocationRelativeTo(null);
		
		btnyes = new Button("YES");
		btnyes.setBounds(20,230,120,40);
		btnyes.setBackground(Color.cyan);
		btnyes.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				temp.dispose();
				new Thread(new Runnable()
				{
					public void run()
					{
						try{
							Thread.sleep(300);
							ref.dispose();
                                                        System.exit(0);
						}
						catch(InterruptedException e)
						{
							e.printStackTrace();
						}
					}
				}).start();
			}
		});
		add(btnyes);
		
		btnno = new Button("NO");
		btnno.setBounds(230,230,120,40);
		btnno.setBackground(Color.cyan);
		btnno.addMouseListener(new MouseAdapter()
		{
			public void mouseClicked(MouseEvent e)
			{
				temp.dispose();
			}
		});
		add(btnno);
		setVisible(true);
	}
	
	public void paint(Graphics g)
	{
		g.setFont(new Font("arial",Font.BOLD,20));
		g.drawString("Do you want to close the game?",20,70);
	}
}

class Myframe1 extends Frame implements Runnable
{
	Ellipse2D.Double circle;
	Thread t;
	int x,y;
	boolean flag;
	int i = 0;
	Button btnstart,btnstop;
	Panel pnl;
	Label lbl1,lbl2;
    Myframe1()
    {
	Myframe1 ref = this;
        setSize(600,600);
        setTitle("Hit the ball");
        setLocationRelativeTo(null);
        setBackground(Color.gray);
		setLayout(null);
		
        addWindowListener(new WindowAdapter(){
            @Override
            public void windowClosing(WindowEvent e) {
                new MyDialog(ref);
            }
        });
        addMouseListener(new MyMouseListener(this));
        
		pnl = new Panel();
		pnl.setBounds(25,450,550,180);
		pnl.setBackground(Color.red);
		pnl.setLayout(null);
		add(pnl);
	
		lbl1 = new Label("");
		lbl1.setBounds(20,100,500,40);
		lbl1.setFont(new Font("arial",Font.BOLD,20));
		pnl.add(lbl1);
		
		lbl2 = new Label("Hit The Ball Game");
		lbl2.setBounds(200,10,500,40);
		lbl2.setFont(new Font("arial",Font.BOLD,15));
		pnl.add(lbl2);
		
		btnstart = new Button("Start");
		btnstart.setBounds(100,40,80,50);
		btnstart.addMouseListener(new MouseAdapter()
		{
			public void mousePressed(MouseEvent e)
			{
				lbl1.setText("");		//reset the labels on panel
				synchronized(ref)
				{
					if(!ref.flag)
					{	
						ref.flag = true;
						t = new Thread(ref);
						t.start();
					}
				}
			}
		});
		pnl.add(btnstart);
		
		btnstop = new Button("Stop");
		btnstop.setBounds(300,40,80,50);
		btnstop.addMouseListener(new MouseAdapter(){
			public void mousePressed(MouseEvent e)
			{
				ref.flag = false;
				if(i==0)
				{
					lbl1.setText("Oops!You hit the ball 0 times");
				}
				else
				{
					out.println("You hit the ball "+i+" times");
					lbl1.setText("Congratulations! You hit the ball "+i+" times");
				}
				i=0;
			}			
		});
		pnl.add(btnstop);
		
        setVisible(true);
    }

    @Override
    public void paint(Graphics g) {
        Graphics2D g2d = (Graphics2D)g;
        circle = new Ellipse2D.Double(x,y,120,120);
		g2d.fill(circle);	
    }
    
	public void run()
	{
		while(flag)
		{
			x = (int)(Math.random()*400);
			y = (int)(Math.random()*300);
			repaint();
			try
			{
				Thread.sleep(700);
			}
			catch(InterruptedException ie)
			{
				ie.printStackTrace();
			}
		}
	}
    
}

class MyMouseListener extends MouseAdapter
{
	Myframe1 ref;
	MyMouseListener(Myframe1 temp)
	{
		ref = temp;
	}
	public void mousePressed(MouseEvent e)
	{
		Point p = e.getPoint();
		if(ref.circle.contains(p))
		{
			out.println("HIT");
			ref.i++;
		}
		else
		{
			out.println("MISS!");
		}
	}
}

public class Hit_the_ball {
    public static void main(String[] args) {
        Myframe1 frame = new Myframe1();
    }
}

