import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;

import acm.graphics.*;
import acm.program.*;

import java.awt.event.KeyEvent;
import java.util.Arrays;

import javax.swing.JButton;

public class ar43b extends GraphicsProgram implements MouseListener , MouseMotionListener , MouseWheelListener
{
 Thread th;

 GPolygon[] rect = new GPolygon[805000];
		
 		JButton play = new JButton("Play");
 		JButton reset = new JButton("Reset");
	    JButton right = new JButton("Y-");
	    JButton left = new JButton("Y+");
	    
	    JButton up = new JButton("Z+");
	    JButton down = new JButton("Z-");
	    
	    JButton front = new JButton("X+");
	    JButton back = new JButton("X-");
	    
	    JButton move_Y_up = new JButton("move_Y+");
	    JButton move_Y_down = new JButton("move_Y-");
	    
	    JButton move_Z_up = new JButton("move_Z+");
	    JButton move_Z_down = new JButton("move_Z-");
	    
	    JButton move_X_up = new JButton("move_X+");
	    JButton move_X_down = new JButton("move_X-");
	    
	    JButton arrowU = new JButton("^");
	    JButton arrowD = new JButton("v");
	    JButton arrowR = new JButton(">");
	    JButton arrowL = new JButton("<");
	    
	    JButton in = new JButton("in");
	    JButton out = new JButton("out");
	    
	    JButton hXup = new JButton("H_X+");
	    JButton hXdown = new JButton("H_X-");
	    JButton hYup = new JButton("H_Y+");
	    JButton hYdown = new JButton("H_Y-");
	    JButton hZup = new JButton("H_Z+");
	    JButton hZdown = new JButton("H_Z-");
	    
	    double hMove = 5;
	    
	    JButton hXCW = new JButton("H_X_CW");
	    JButton hXACW = new JButton("H_X_ACW");
	    JButton hYCW = new JButton("H_Y_CW");
	    JButton hYACW = new JButton("H_Y_ACW");
	    JButton hZCW = new JButton("H_Z_CW");
	    JButton hZACW = new JButton("H_Z_ACW");
	
	    double hRotate = 5;	//	in Degree
	    
	        
	    static double[] xn3={0,0,0};
	    static double[] yn3={0,0,0};
	    static double[] zn3={0,0,0};
	    
	    static double[] xx3={200,0,0};
	    static double[] yx3={0,200,0};
	    static double[] zx3={0,0,200};
	    
	    static double xnx,xny ,xxx,xxy,ynx,yny,yxx,yxy,znx,zny,zxx,zxy;
	    
	    Color myColor=new Color(0,216,216);//(64,224,208);
	    
	    static double[] loc=new double[805000];
	    static double[][][] buff=new double[805000][3][2];
	    
	    static double[][] p= new double[805000][3];
	  
	    static double[] a= { -1800.1, 0.1, 700.1 };
	    
	    static double[] d= { 1.001, 0.0001, 0.0001 };
	    
	    double dchange=0.1;
		
		double schange=20;	//5
		
		static double zoom=500;
	    
	    static double[][] v=new double[805000][2];
	    
	    static double tx,ty;
	    
	    static double[] radii = new double[805000];
	    
	    static double[] angle = new double[805000];

	    static int control;
	    
	    static int j;
	    
	    GImage blank= new GImage("plain.jpg");
	    
	    GPoint gp=new GPoint(0,0);
	    
	    static double focus=90;
	    
	    int mouseflag=0;	// init state
	    int mousecounter=0;
	    int mouse_counter_controller=5;
	    
	    int mousefX, mousefY, mousetX, mousetY;
	    
	    int move=5;
	    
	    static int pointCount=0;
	    static int triCount=0;
	    
	    static double[][] COM = new double[805000][3];
	    static double[][] comDist = new double[805000][2];
	    
	    int arrowMagni;
	    
	    int lockLift=0;
	    
	    boolean playFlag = false;
	    
	    Triangle3D xAxis = new Triangle3D(new double[][] {  
			{ 0,0,0 },
			{ 0,0,0 },
			{ 200,0,0 } 	});
	    
	    Triangle3D yAxis = new Triangle3D(new double[][] {  
			{ 0,0,0 },
			{ 0,0,0 },
			{ 0,200,0 } 	});
	    
	    Triangle3D zAxis = new Triangle3D(new double[][] {  
			{ 0,0,0 },
			{ 0,0,0 },
			{ 0,0,200 } 	});
	    
	    Box3D bx2 = new Box3D(new double[][] {  
			{ 0,0+420,0 },	//base
			{ 100,0+420,0 },
			{ 100,100+420,0 },
			{ 0,100+420,0},	
			
			{ 0,0+420,500 },//top
			{ 100,0+420,500 },
			{ 100,100+420,500 },
			{ 0,100+420,500}	});
	    
	    Plane3D pyrbase2 = new Plane3D(new double[][] {  
			{ 200,0+420,0 },
			{ 300,0+420,0 },
			{ 300,100+420,0 },
			{ 200,100+420,0}		});
	    
	    Triangle3D pyrt12 = new Triangle3D(new double[][] {  
			{ 200,0+420,0 },
			{ 300,0+420,0 },
			{ 250,50+420,500 },		});
	    
	    Triangle3D pyrt22 = new Triangle3D(new double[][] {  
			{ 200,0+420,0 },
			{ 200,100+420,0 },
			{ 250,50+420,500 },		});
	    
	    Triangle3D pyrt32 = new Triangle3D(new double[][] {  
			{ 200,100+420,0 },
			{ 300,100+420,0 },
			{ 250,50+420,500 },		});
	    
	    Triangle3D pyrt42 = new Triangle3D(new double[][] {  
			{ 300,0+420,0 },
			{ 300,100+420,0 },
			{ 250,50+420,500 },		});
	    //clone
	    Box3D bx1 = new Box3D(new double[][] {  
			{ 0,0,0 },	//base
			{ 100,0,0 },
			{ 100,100,0 },
			{ 0,100,0},	
			
			{ 0,0,500 },//top
			{ 100,0,500 },
			{ 100,100,500 },
			{ 0,100,500}	});
	    
	    Plane3D pyrbase = new Plane3D(new double[][] {  
			{ 200,0,0 },
			{ 300,0,0 },
			{ 300,100,0 },
			{ 200,100,0}		});
	    
	    Triangle3D pyrt1 = new Triangle3D(new double[][] {  
			{ 200,0,0 },
			{ 300,0,0 },
			{ 250,50,500 },		});
	    
	    Triangle3D pyrt2 = new Triangle3D(new double[][] {  
			{ 200,0,0 },
			{ 200,100,0 },
			{ 250,50,500 },		});
	    
	    Triangle3D pyrt3 = new Triangle3D(new double[][] {  
			{ 200,100,0 },
			{ 300,100,0 },
			{ 250,50,500 },		});
	    
	    Triangle3D pyrt4 = new Triangle3D(new double[][] {  
			{ 300,0,0 },
			{ 300,100,0 },
			{ 250,50,500 },		});
	    ///////
	    Plane3D p1 = new Plane3D(new double[][] {  
			{ -1000,200,0 },
			{ 100000,200,0 },
			{ 100000,220,0 },
			{ -1000,220,0}		});
	    
	    Plane3D p2 = new Plane3D(new double[][] {  
			{ -1000,200+100,0 },
			{ 100000,200+100,0 },
			{ 100000,220+100,0 },
			{ -1000,220+100,0}		});
	    //
	    //Helicopter
	    Box3D body = new Box3D(new double[][] {  
			{ 0,0,0 },	//base
			{ 100,0,0 },
			{ 100,100,0 },
			{ 0,100,0},	
			
			{ 0,0,100 },//top
			{ 100,0,100 },
			{ 100,100,100 },
			{ 0,100,100}	});
	    
	    Triangle3D[] headTriangle = {
	    		new Triangle3D(new double[][] {  
	    			{ 0,100,0 },
	    			{ 0,100,100 },
	    			{ 50,200,50 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 100,100,0 },
	    			{ 100,100,100 },
	    			{ 50,200,50 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 0,100,0 },
	    			{ 100,100,0 },
	    			{ 50,200,50 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 0,100,100 },
	    			{ 100,100,100 },
	    			{ 50,200,50 },		})
	    };
	    
	    Triangle3D[] bladeLift = {
	    		new Triangle3D(new double[][] {  
	    			{ 50,50,110 },
	    			{ -200,55,110 },
	    			{ -200,45,110 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 50,50,110 },
	    			{ 300,45,110 },
	    			{ 300,55,110 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 50,0,110 },
	    			{ 45,-200,110 },
	    			{ 55,-200,110 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 50,100,110 },
	    			{ 55,300,110 },
	    			{ 45,300,110 },		})
	    };
	    
	    Box3D tail = new Box3D(new double[][] {  
	    	{ 45,0,45 },
			{ 55,0,45},
			{ 55,0,55 },
			{ 40,0,55 },
			
			{ 48,-150,48},
			{ 52,-150,48 },			
	    	{ 52,-150,52 },	
			{ 48,-150,52 },	});
	    
	    Triangle3D[] bladeRotate = {
	    		new Triangle3D(new double[][] {  
	    			{ 45,-150,50 },//down
	    			{ 45,-148,20 },
	    			{ 45,-152,20 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 45,-150,50 },//up
	    			{ 45,-152,80 },
	    			{ 45,-148,80 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 45,-150,50 },//right
	    			{ 45,-180,48 },
	    			{ 45,-180,52 },		}),
	    		new Triangle3D(new double[][] {  
	    			{ 45,-150,50 },//left
	    			{ 45,-120,52 },
	    			{ 45,-120,48 },		})
	    };
	    
	    Helicopter3D h1 = new Helicopter3D(body, headTriangle, bladeLift, tail, bladeRotate);
	    
	    //////////////////////////////////////////////////
	    
	    Sphere sp1= new Sphere( 100, new double[] {350,0,0}, 30, 30);
	    PartialSphere psp1 = new PartialSphere( 250, new double[] {100,200,0}, 160, 30, 89.9, 90.1, 0, 360);
	    SliceSphere ssp1 = new SliceSphere( 250, new double[] {600,200,200}, 21, 20, 0, 180, 0, 360);
	    SliceSphere ssp2 = new SliceSphere( 200, new double[] {600,200,200}, 21, 20, 0, 180, 0, 360);
	 
	    Toroid trd1 = new Toroid(new double[]{0,0,0},500,150,40,40);
	    
	    Sphere sp2= new Sphere( 20, new double[] {750,0,0}, 8, 8);
	    Sphere sp3= new Sphere( 20, new double[] {-750,0,-0}, 8, 8);
	    Sphere sp4= new Sphere( 20, new double[] {0,750,0}, 8, 8);   
	    Sphere sp5= new Sphere( 20, new double[] {0,-750,0}, 8, 8);
	    
	    
public void init()
{ 
	
	th=new Thread(this);
	
	add(play, EAST);
	
	add(reset, EAST);
	
	add(in,EAST);
	add(out,EAST);
	
	add(arrowU,EAST);
	add(arrowD,EAST);
	add(arrowR,EAST);
	add(arrowL,EAST);
	
	add(front, EAST);
    add(back, EAST);
	add(left, EAST);
    add(right, EAST);    
    add(up, EAST);
    add(down, EAST);    
    
    add(move_X_up,EAST);
    add(move_X_down,EAST);
    add(move_Y_up,EAST);
    add(move_Y_down,EAST);
    add(move_Z_up,EAST);
    add(move_Z_down,EAST);
    
    add(hXup,EAST);
    add(hXdown,EAST);
    add(hYup,EAST);
    add(hYdown,EAST);
    add(hZup,EAST);
    add(hZdown,EAST);
    
    add(hXCW,EAST);
    add(hXACW,EAST);
    add(hYCW,EAST);
    add(hYACW,EAST);
    add(hZCW,EAST);
    add(hZACW,EAST);
    
    addActionListeners();     
    addMouseListener( this );
    addMouseMotionListener( this );
    addMouseWheelListener(this);
    			
	setVisible(true);
    
}

public static double dot( double[] a,double[] b )
{
	
	return ( a[0]*b[0]+a[1]*b[1]+a[2]*b[2] );
}
	
public static double[] cross(double[] a,double[] b)
{
	double[] crs = {0,0,0};
	
	crs[0]=a[1]*b[2] - b[1]*a[2] ;
	crs[1]=b[0]*a[2] - a[0]*b[2] ;
	crs[2]=a[0]*b[1] - b[0]*a[1] ;
	
	return crs;
	
}

public static double mod(double[] v)
{
	double ln=(double) Math.sqrt( v[0]*v[0] + v[1]*v[1] + v[2]*v[2] );
	
	return ln;
}

public static double[] diff(double[] a,double[] b)
{
	double[] d={ 0,0,0, };
	
	d[0]=b[0] - a[0];
	d[1]=b[1] - a[1];
	d[2]=b[2] - a[2];
	
	return d;
}

public static double per_dist(double[] p,double[] a, double[] d)
{
	double width = 0;
	
	width=( mod( cross( diff( p , a ), d ) ) )/( mod(d) );
	
	return width;
}

public static double per_dist_plane(double[] p,double[] a,double[] d)//w
{
	double D;
	
	D=a[0]*d[0]+a[1]*d[1]+a[2]* d[2];
	
	return ( (Math.abs( dot(p,d) - D ))/( mod(d) ) );
	
}


public static double[] get_ext_vect(double[] v,double dv,double[] d)
{
	double[] ext_vect={0,0,0};
	
	ext_vect[0]=v[0]+dv*d[0];
	ext_vect[1]=v[1]+dv*d[1];
	ext_vect[2]=v[2]+dv*d[2];
	
	return ext_vect;
}

public static double[] get_mirr_vect(double[] p,double[] a,double[] d)
{
	double[] mirr_vect={0,0,0};
	
	double ext=per_dist_plane(p,a,d);
	
	return get_ext_vect(p,ext*(-1),d);
}

public static double depth(double[] p,double[] a, double[] d )
{
	return (Math.abs( dot( diff( p,a ) , d ) / ( mod(d) ) ));
}

public static double displ( double[] p,double[] a, double[] d )
{	
	double dtemp=depth(p,a,d);
	
	if(dtemp==0)
		dtemp=0.0000001;	//ANY SMALL
	
	return ( per_dist(p,a,d) )/( dtemp );
}

public static double[] unit( double[] v)
{
	double md=mod(v);
	double[] unt= { 0,0,0 };
	
	unt[0]=v[0]/md;
	unt[1]=v[1]/md;
	unt[2]=v[2]/md;
	
	return unt;
}

public static double get_phi(double[] v)
{
	double phi=0;
	
	phi=Math.toDegrees( Math.acos( v[2]/(Math.sqrt( v[0]*v[0] + v[1]*v[1] + v[2]*v[2] ))) );
	
	return phi;
}

public static double get_theta(double[] v)
{
	double theta=0;
	
	if( v[0]==0 & v[1]==0)
		return theta;///////////////////////exception
	
	theta=Math.toDegrees( Math.acos( v[0]/(Math.sqrt( v[0]*v[0] + v[1]*v[1] ))) );
	
	if( v[1]<0 )
		theta=360-theta;
	
	return theta;
}


public static double[] get_diff_phi( double[] d, double[] p)
{
	double phi_d=get_phi(d);
	double phi_p=get_phi(p);
	
	double[] diff_phi={0,0};
	
	double[] use_diff_theta=get_diff_theta(d,p);   // make it public to optimize
	
	if( phi_d<90 && phi_p<90 )
	{
		if( Math.abs(use_diff_theta[0])>90 )
		{
				diff_phi[0]= phi_d + phi_p ;
				diff_phi[1]=1;
		}
		else if( Math.abs(use_diff_theta[0])<90 )
		{
			diff_phi[0]=phi_d - phi_p;
			
			if( diff_phi[0]>0 )
				diff_phi[1]=1;
			else
				diff_phi[1]=-1;
		}
	}
	else if( phi_d>90 && phi_p>90 )
	{
		if( use_diff_theta[0]>90 )
		{
				diff_phi[0]= 360 - phi_d - phi_p ;
				diff_phi[1]=-1;
		}
		else if( Math.abs(use_diff_theta[0])<90 )
		{
			diff_phi[0]=phi_d - phi_p;
			
			if( diff_phi[0]>0 )
				diff_phi[1]=1;
			else
				diff_phi[1]=-1;
		}
	}
	else
	{
		
		if( Math.abs(use_diff_theta[0])>90 )
		{
			
				diff_phi[0]= phi_d + phi_p ;
				diff_phi[1]=1;//or-1
		}
		else if( Math.abs(use_diff_theta[0])<90 )
		{
			diff_phi[0]=phi_d - phi_p;
			
			if( diff_phi[0]>0 )
				diff_phi[1]=1;
			else
				diff_phi[1]=-1;
		}
		
	}
	
	return diff_phi;
}



public static double[] get_diff_theta( double[]d, double[] p)
{
	double[] diff_theta={0,0};
	
	double theta_d=get_theta(d);
	double theta_p=get_theta(p);
	
	if( theta_d<90 && theta_p>270 )
	{
		diff_theta[0]=theta_d + 360 - theta_p;	//+ve
		diff_theta[1]=1;
	}
	else if( theta_d>270 && theta_p<90 )
	{
		diff_theta[0]=(theta_p + 360 - theta_d)*(-1);	//+ve to -ve
		diff_theta[1]=-1;
	}
	else
	{
		diff_theta[0]= theta_d - theta_p;	
		
		if( diff_theta[0]>0 )
			diff_theta[1]=1;
		else
			diff_theta[1]=-1;
	}
	
	return diff_theta;
}

public static double[] get_rel_vect(double[] d, double[] p)
{
	double[] rel_vect={0,0,0};
	
	double phi_d=get_phi(d);
	double phi_p=get_phi(p);
	
	double[] diff_phi={0,0};
	
	double[] use_diff_theta=get_diff_theta(d,p);
	
	double[] rel_unit_vect={0,0,0};
	
	if( phi_d<90 && phi_p<90 )
	{
		if( Math.abs(use_diff_theta[0])>90 )
		{
			rel_vect[0]=180 - Math.abs(use_diff_theta[0]);//ts_6_1
			
			rel_vect[0]= use_diff_theta[1]*rel_vect[0];				
			
			rel_vect[1]=phi_d + phi_p;
			
			rel_unit_vect[2]=-3;							//****MAC_UPDATE
			
		}
		else if( Math.abs(use_diff_theta[0])<90 )
		{
			diff_phi[0]=phi_d - phi_p;
			
			if( diff_phi[0]>0 )
				diff_phi[1]=1;
			else
				diff_phi[1]=-1;
			
			rel_vect[1]=diff_phi[0]; 
			
			rel_vect[0]= use_diff_theta[0];	//ts_6
		}
	}
	else if( phi_d>90 && phi_p>90 )
	{
		if( Math.abs(use_diff_theta[0])>90 )
		{			
			rel_vect[1]= (360 - phi_d - phi_p)*(-1);
			
			rel_vect[0]=180 - use_diff_theta[0];	
			
			rel_vect[0]= use_diff_theta[1]*rel_vect[0];	
			
			rel_unit_vect[2]=-3;							//***MAC_UPDATE
			
		}
		else if( Math.abs(use_diff_theta[0])<90 )
		{
			diff_phi[0]=phi_d - phi_p;
			
			if( diff_phi[0]>0 )
				diff_phi[1]=1;
			else
				diff_phi[1]=-1;
			
			rel_vect[1]=diff_phi[0];//ts_6
			
			rel_vect[0]= use_diff_theta[0];	//ts_6
		}
	}
	else
	{
		if( Math.abs(use_diff_theta[0])>90 )
		{
			
				diff_phi[0]= phi_d + phi_p ;
				diff_phi[1]=1;//or-1
				
				rel_unit_vect[2]=-3;
		}
		else if( Math.abs(use_diff_theta[0])<90 )
		{
			diff_phi[0]=phi_d - phi_p;
			
			if( diff_phi[0]>0 )
				diff_phi[1]=1;
			else
				diff_phi[1]=-1;
		}
		////////////////
		
		rel_vect[1]=diff_phi[0];//ts_6
		
		rel_vect[0]= use_diff_theta[0];	//ts_6
	}
	
	double rel_vect_magn=Math.sqrt(rel_vect[0]*rel_vect[0] + rel_vect[1]*rel_vect[1]);
	
	
	rel_unit_vect[0]=rel_vect[0]/rel_vect_magn;
	rel_unit_vect[1]=rel_vect[1]/rel_vect_magn;
	
	return rel_unit_vect;	
}

public static String get_window(double x, double y) //now find position
{
	String position="null";
	
	double theta =Math.toDegrees(Math.atan(y/x));
	
	double theta1=Math.toDegrees(Math.atan(540.0/960.0));
	double theta2=180 - theta1;
	double theta3=180 + theta1;
	double theta4=360 - theta1;
	
	
	if( x<0 && y>0 )
	{
		theta= 180 + theta;
	}
	else if( x<0 && y<0 )
	{
		theta= 180 + theta;
	}
	else if( x>0 && y<0 )
	{
		theta = 360 + theta;
	}
	
	if( theta<theta2 && theta>theta1 )
	{		
		position="up";
		
	}
	else if( theta>theta3 && theta<theta4 )
	{
		
		position="down";
	}
	else if( theta<theta3 && theta>theta2 )
	{
		position="left";
	}
	else if( theta<theta1 || theta>theta4 )
	{
		position="right";
	}
	
	
	return position;
}

public static double[] get_extended_axis_pair( double inx, double iny, double outx, double outy )
{
	String window=get_window(outx,outy);
	
	double window_selecter=1;
	
	double in_x,in_y,out_x,out_y;
	
	double[] unit_in=new double[3];
	double[] unit_out=new double[3];
	
	unit_in[0]=outx-inx;
	unit_in[1]=outy-iny;
	unit_in[2]=0;
	
	unit_out=unit(unit_in);	
	
	double[] out=new double[2];
	
	
	if( window=="up" || window=="down" )
	{
		if(window=="down")
		{
			window_selecter=-1;
		}
		
		out_y=window_selecter*540;
		
		out_x= inx + ( ( window_selecter*540 - iny )/unit_out[1] )*unit_out[0];
		
		if( Math.abs(out_x) > 960 )
		{	
			double selecter=1;
			
			if( out_x < -960 )
			{
				selecter=-1;
			}
			
			out_x=selecter*960;
			
			out_y= iny + ( ( selecter*960 - inx )/unit_out[0] )*unit_out[1];
		}
		
		out[0]=out_x;
		out[1]=out_y;
																											
	}
	else if( window=="right" || window=="left")
	{
		if( window=="left")
		{
			window_selecter=-1;
		}
		
		out_x=window_selecter*960;
		
		out_y= iny + ( ( window_selecter*960 - inx )/unit_out[0] )*unit_out[1];
		
		if( Math.abs(out_y) > 540 )
		{
			double selecter=1;
			
			if( out_y < -540 )
			{
				selecter=-1;
			}
			
			out_y=selecter*540;
			
			out_x= inx + ( ( selecter*540 - iny )/unit_out[1] )*unit_out[0];
		}
		
		out[0]=out_x;
		out[1]=out_y;
		
	}
	
	
	return out; 
}

public static double[] get_both_out(double x1, double y1, double x2, double y2)
{
	double out1[]=new double[2];
	double out2[]=new double[2];
	double out[]={0,0,0,0,0,0,0,0,0};
	
	double k_up,k_down,k_right,k_left;
	double x_up,x_down,y_right,y_left;
	
	double unit_in[]=new double[3];
	double unit_out[]=new double[3];
	
	unit_in[0]=x2-x1;
	unit_in[1]=y2-y1;
	unit_in[2]=0;
	
	unit_out=unit(unit_in);
	
	if(get_window(x1,y1).equals(get_window(x2,y2)))
	{
		out=new double[] {-3333,-3333,-3333,-3333,-3333,-3333,-3333,-3333,-3333};
		
		return out;
	}	
	
	{
		k_up= ( 540 - y1 )/unit_out[1];
		
		x_up= x1 + k_up*unit_out[0];
	}
	
	{
		k_down= ( (-1)*540 - y1 )/unit_out[1];
		
		x_down= x1 + k_down*unit_out[0];
	}
	
	{
		k_right= ( 960 - x1 )/unit_out[0];
		
		y_right= y1 + k_right*unit_out[1];
	}
	
	{
		k_left= ( (-1)*960 - x1 )/unit_out[0];
		
		y_left= y1 + k_left*unit_out[1];
	}
	
	if(get_window(x1,y1)=="up")
	{
		if( y2 > y1 && Math.abs(x_up) > 960 )// changes in ar31
		{
			if( x2 > x1 )
			{
				out1[0]=960;
				out1[1]=540;
				
				out2[0]=960;
				out2[1]=540;
				
				out[8]=-1111;
			}
			else
			{
				out1[0]=-960;
				out1[1]=540;
				
				out2[0]=-960;
				out2[1]=540;
				
				out[8]=-1111;
			}
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(y_left) < 540 ) && 
			( Math.abs(x_down) > 960 ) && ( Math.abs(y_right) > 540 ))
		{
			
			out1[0]=x_up;
			out1[1]=540;
			
			out2[0]=-960;
			out2[1]=y_left;
			
			out[8]=14;
			
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(y_right) < 540 ) && 
				( Math.abs(x_down) > 960 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out1[0]=x_up;
			out1[1]=540;
			
			out2[0]=960;
			out2[1]=y_right;
			
			out[8]=12;
			
		}
		if( ( Math.abs(x_down) < 960 ) && ( Math.abs(y_left) < 540 ) && 
				( Math.abs(x_up) > 960 ) && ( Math.abs(y_right) > 540 ))
		{
			
			out1[0]=-960;
			out1[1]=y_left;
			
			out2[0]=x_down;
			out2[1]=-540;
			
			out[8]=143;
			
		}
		else if( ( Math.abs(x_down) < 960 ) && ( Math.abs(y_right) < 540 ) && 
				( Math.abs(x_up) > 960 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out1[0]=960;
			out1[1]=y_right;
			
			out2[0]=x_down;
			out2[1]=-540;
			
			out[8]=123;
			
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(x_down) < 960 ) && 
				( Math.abs(y_right) > 540 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out1[0]=x_up;
			out1[1]=540;
			
			out2[0]=x_down;
			out2[1]=-540;
			
			out[8]=13;
						
		}		
		
	}
	else if(get_window(x1,y1)=="down")
	{
		if( y2 < y1 && Math.abs(x_down) > 960 )// changes in ar31
		{
			if( x2 > x1 )
			{
				out1[0]=960;
				out1[1]=-540;
				
				out2[0]=960;
				out2[1]=-540;
				
				out[8]=-1111;
			}
			else
			{
				out1[0]=-960;
				out1[1]=-540;
				
				out2[0]=-960;
				out2[1]=-540;
				
				out[8]=-1111;
			}
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(y_left) < 540 ) && 
			( Math.abs(x_down) > 960 ) && ( Math.abs(y_right) > 540 ))
		{
			
			out2[0]=x_up;
			out2[1]=540;
			
			out1[0]=-960;
			out1[1]=y_left;
			
			out[8]=341;
			
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(y_right) < 540 ) && 
				( Math.abs(x_down) > 960 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out2[0]=x_up;
			out2[1]=540;
			
			out1[0]=960;
			out1[1]=y_right;
			
			out[8]=321;
			
		}
		if( ( Math.abs(x_down) < 960 ) && ( Math.abs(y_left) < 540 ) && 
				( Math.abs(x_up) > 960 ) && ( Math.abs(y_right) > 540 ))
		{
			
			out2[0]=-960;
			out2[1]=y_left;
			
			out1[0]=x_down;
			out1[1]=-540;
			
			out[8]=34;
			
		}
		else if( ( Math.abs(x_down) < 960 ) && ( Math.abs(y_right) < 540 ) && 
				( Math.abs(x_up) > 960 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out2[0]=960;
			out2[1]=y_right;
			
			out1[0]=x_down;
			out1[1]=-540;
			
			out[8]=32;
			
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(x_down) < 960 ) && 
				( Math.abs(y_right) > 540 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out2[0]=x_up;
			out2[1]=540;
			
			out1[0]=x_down;
			out1[1]=-540;
			
			out[8]=31;
			
		}	
		
	}
	else if(get_window(x1,y1)=="right")
	{
		if( x2 > x1 && Math.abs(y_right) > 540 )// changes in ar31
		{
			if( y2 > y1 )
			{
				out1[0]=960;
				out1[1]=540;
				
				out2[0]=960;
				out2[1]=540;
				
				out[8]=-1111;
			}
			else
			{
				out1[0]=960;
				out1[1]=-540;
				
				out2[0]=960;
				out2[1]=-540;
				
				out[8]=-1111;
			}
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(y_left) < 540 ) && 
			( Math.abs(x_down) > 960 ) && ( Math.abs(y_right) > 540 ))
		{
			
			out1[0]=x_up;
			out1[1]=540;
			
			out2[0]=-960;
			out2[1]=y_left;
			
			out[8]=214;
			
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(y_right) < 540 ) && 
				( Math.abs(x_down) > 960 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out2[0]=x_up;
			out2[1]=540;
			
			out1[0]=960;
			out1[1]=y_right;
			
			out[8]=21;
			
		}
		if( ( Math.abs(x_down) < 960 ) && ( Math.abs(y_left) < 540 ) && 
				( Math.abs(x_up) > 960 ) && ( Math.abs(y_right) > 540 ))
		{
			
			out2[0]=-960;
			out2[1]=y_left;
			
			out1[0]=x_down;
			out1[1]=-540;
			
			out[8]=234;
			
		}
		else if( ( Math.abs(x_down) < 960 ) && ( Math.abs(y_right) < 540 ) && 
				( Math.abs(x_up) > 960 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out1[0]=960;
			out1[1]=y_right;
			
			out2[0]=x_down;
			out2[1]=-540;
			
			out[8]=23;
			
		}
		else if( ( Math.abs(x_up) > 960 ) && ( Math.abs(x_down) > 960 ) && 
				( Math.abs(y_right) < 540 ) && ( Math.abs(y_left) < 540 ))
		{
			
			out1[0]=960;
			out1[1]=y_right;
			
			out2[0]=-960;
			out2[1]=y_left;
			
			out[8]=24;
			
		}		
		
	}
	else if(get_window(x1,y1)=="left")
	{
		if( x2 < x1 && Math.abs(y_left) > 540 )// changes in ar31
		{
			if( y2 > y1 )
			{
				out1[0]=-960;
				out1[1]=540;
				
				out2[0]=-960;
				out2[1]=540;
				
				out[8]=-1111;
			}
			else
			{
				out1[0]=-960;
				out1[1]=-540;
				
				out2[0]=-960;
				out2[1]=-540;
				
				out[8]=-1111;
			}
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(y_left) < 540 ) && 
			( Math.abs(x_down) > 960 ) && ( Math.abs(y_right) > 540 ))
		{
			
			out2[0]=x_up;
			out2[1]=540;
			
			out1[0]=-960;
			out1[1]=y_left;
			
			out[8]=41;
			
		}
		else if( ( Math.abs(x_up) < 960 ) && ( Math.abs(y_right) < 540 ) && 
				( Math.abs(x_down) > 960 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out1[0]=x_up;
			out1[1]=540;
			
			out2[0]=960;
			out2[1]=y_right;
			
			out[8]=412;
			
		}
		if( ( Math.abs(x_down) < 960 ) && ( Math.abs(y_left) < 540 ) && 
				( Math.abs(x_up) > 960 ) && ( Math.abs(y_right) > 540 ))
		{
			
			out1[0]=-960;
			out1[1]=y_left;
			
			out2[0]=x_down;
			out2[1]=-540;
			
			out[8]=43;
			
		}
		else if( ( Math.abs(x_down) < 960 ) && ( Math.abs(y_right) < 540 ) && 
				( Math.abs(x_up) > 960 ) && ( Math.abs(y_left) > 540 ))
		{
			
			out2[0]=960;
			out2[1]=y_right;
			
			out1[0]=x_down;
			out1[1]=-540;
			
			out[8]=432;
			
		}
		else if( ( Math.abs(x_up) > 960 ) && ( Math.abs(x_down) > 960 ) && 
				( Math.abs(y_right) < 540 ) && ( Math.abs(y_left) < 540 ))
		{
			
			out1[0]=-960;
			out1[1]=y_left;
			
			out2[0]=960;
			out2[1]=y_right;
			
			out[8]=42;
			
		}		
		
	}
	
	out[0]=out1[0];
	out[1]=out1[1];
	
	out[2]=out2[0];
	out[3]=out2[1];
	
	out[4]=x_up;
	out[5]=x_down;
	out[6]=y_right;
	out[7]=y_left;
	
	return out;
}

public static double[] get_extended_v(double x1, double y1, double x2, double y2)
{
	double[] ret=new double[4];
	double[] rec_in=new double[2];
	double[] rec_out=new double[2];
	
	
	// 1 out && 2 out
	if( ( Math.abs(x1)>960 || Math.abs(y1)>540 ) && ( Math.abs(x2)>960 || Math.abs(y2)>540 ) ) 
	{
		
		ret=get_both_out(x1,y1,x2,y2);
		
	}	// 1 in && 2 out	
	else if( ( Math.abs(x1)<960 || Math.abs(y1)<540 ) && ( Math.abs(x2)>960 || Math.abs(y2)>540 ) )	
	{	
		rec_out=get_extended_axis_pair(x1,y1,x2,y2);
		
		ret[0]=x1;
		ret[1]=y1;
		ret[2]=rec_out[0];
		ret[3]=rec_out[1];
		
	}	// 1 out && 2 in
	else if( ( Math.abs(x1)>960 || Math.abs(y1)>540 ) && ( Math.abs(x2)<960 || Math.abs(y2)<540 ) )	
	{
		rec_out=get_extended_axis_pair(x2,y2,x1,y1);
		
		ret[0]=rec_out[0];
		ret[1]=rec_out[1];
		ret[2]=x2;
		ret[3]=y2;
	}
	
	return ret;
}


static double[] get_back_mirror(double[] a, double[] d, double[] fv, double[]tv )
{
	double[] back_mirror={0,0};
	double t=0;
	double[] unit_f_t=unit( diff(fv, tv) );
	double displacement=40;	// !!! ar34
	
	t= ( d[0]*( a[0] - fv[0] ) + d[1]*( a[1] - fv[1] ) + d[2]*( a[2] - fv[2] ) ) / 
		    ( d[0]*unit_f_t[0] + d[1]*unit_f_t[1]  + d[2]*unit_f_t[2]  );
	
	
	double[] intersect_v={ fv[0] + t*unit_f_t[0], fv[1] + t*unit_f_t[1], fv[2] + t*unit_f_t[2] };
	
	double[] compare1 = diff(fv,tv);
	double[] compare2 = diff(fv,intersect_v);
	
  if( compare1[0]*compare2[0] > 0 )
  {
	
	double backMagn = 10; 
	
	if( ( get_theta(d) > 0 && get_theta(d) < 80 ) || ( get_theta(d) > 280 && get_theta(d) < 360 ) )
	{
		if( intersect_v[0] < a[0] )
		{
			do
			{				
				intersect_v[0] = intersect_v[0] - backMagn*unit_f_t[0];
				intersect_v[1] = intersect_v[1] - backMagn*unit_f_t[1];
				intersect_v[2] = intersect_v[2] - backMagn*unit_f_t[2];
			}
			while( intersect_v[0] < a[0] );
		}
	}
	else if( get_theta(d) > 10 && get_theta(d) < 170 )
	{
		if( intersect_v[1] < a[1] )
		{
			do
			{				
				intersect_v[0] = intersect_v[0] - backMagn*unit_f_t[0];
				intersect_v[1] = intersect_v[1] - backMagn*unit_f_t[1];
				intersect_v[2] = intersect_v[2] - backMagn*unit_f_t[2];
			}
			while( intersect_v[1] < a[1] );
		}
	}
	else if( get_theta(d) > 100 && get_theta(d) < 260 )
	{
		if( intersect_v[0] > a[0] )
		{
			do
			{				
				intersect_v[0] = intersect_v[0] - backMagn*unit_f_t[0];
				intersect_v[1] = intersect_v[1] - backMagn*unit_f_t[1];
				intersect_v[2] = intersect_v[2] - backMagn*unit_f_t[2];
			}
			while( intersect_v[0] > a[0] );
		}
	}
	else if( get_theta(d) > 190 && get_theta(d) < 350 )
	{
		if( intersect_v[1] > a[1] )
		{
			do
			{				
				intersect_v[0] = intersect_v[0] - backMagn*unit_f_t[0];
				intersect_v[1] = intersect_v[1] - backMagn*unit_f_t[1];
				intersect_v[2] = intersect_v[2] - backMagn*unit_f_t[2];
			}
			while( intersect_v[1] > a[1] );
		}
	}
	//
	double[] rel_vect=get_rel_vect(d, diff(a, intersect_v));
	
	if(rel_vect[2] == 0)	
	{
		back_mirror[0] = zoom*displ(intersect_v, a, d)*rel_vect[0];	// changes in ar27
		back_mirror[1] = zoom*displ(intersect_v, a, d)*rel_vect[1];
	
	
		if( ( (Math.abs(back_mirror[0])>960) || (Math.abs(back_mirror[1])>540)  ) )	
			back_mirror = get_extended_axis_pair(0,0,back_mirror[0],back_mirror[1]);
	
	}
	else	
		back_mirror[0]=back_mirror[1]=-8888;	
  }
  else
	  back_mirror[0]=back_mirror[1]=-8888;	
  
	return back_mirror;
}

static double[][] adjust_point(int pc, int curr, int sc)
{
	double[] back_mirror={0,0};
	double[][] buf={ { -8888,-8888 },
					 { -8888,-8888 },
					 { -8888,-8888 }	};
	
		//****************************************************************** point 0
	if(loc[curr] == 0 )
	{
		buf[0][0]=buf[1][0]=buf[2][0]=v[curr][0];
		buf[0][1]=buf[1][1]=buf[2][1]=v[curr][1];
	}
	else
	{
		if( loc[curr]==-3 && loc[pc]!=-3 )
		{
			back_mirror=get_back_mirror(a, d, p[pc], p[curr]);
		
			buf[0][0] = back_mirror[0];
			buf[0][1] = back_mirror[1];
			
			buf[1][0] = buf[0][0];
			buf[1][1] = buf[0][1]; //optional from here of below
			
		}
		else if( loc[curr]==-3 && loc[pc]==-3 )
		{
			buf[0][0]=buf[0][1]=buf[1][0]=buf[1][1]=-8888;
		}
		/////////////////////////////////////////////////////////////////////
		if( loc[curr]==-3 && loc[sc]!=-3 )
		{
			back_mirror=get_back_mirror(a, d, p[sc], p[curr]);
			
			buf[2][0] = back_mirror[0];
			buf[2][1] = back_mirror[1];
			
			buf[1][0] = buf[0][0];
			buf[1][1] = buf[0][1]; //optional from here of below
			
		}		
		else if( loc[curr]==-3 && loc[sc]==-3 )
		{
			buf[1][0]=buf[1][1]=buf[2][0]=buf[2][1]=-8888;
		}	
		// * out extra
		if(loc[curr] == 1)
		{
			double rec_out_pc[]=new double[2];
			double rec_out_sc[]=new double[2];
			
			if(loc[pc]==0 && loc[sc]==0)	//pc in, sc in
			{
				
				rec_out_pc=get_extended_axis_pair(v[pc][0], v[pc][1], v[curr][0], v[curr][1]);
				rec_out_sc=get_extended_axis_pair(v[sc][0], v[sc][1], v[curr][0], v[curr][1]);
				
				buf[0][0]=rec_out_pc[0];
				buf[0][1]=rec_out_pc[1];
				
				buf[2][0]=rec_out_sc[0];
				buf[2][1]=rec_out_sc[1];
				
				if( buf[0][0]==buf[2][0] ||  buf[0][1]==buf[2][1] )
				{
					buf[1][0]=buf[0][0];
					buf[1][1]=buf[0][1];	// optional
				}
				else
				{
					if( Math.abs(buf[0][0])==960 )
					{
						buf[1][0]=buf[0][0];
					}
					else if( Math.abs(buf[2][0])==960 )
					{
						buf[1][0]=buf[2][0];
					}
					
					if( Math.abs(buf[0][1])==540 )
					{
						buf[1][1]=buf[0][1];
					}
					else if( Math.abs(buf[2][1])==540 )
					{
						buf[1][1]=buf[2][1];
					}						
				}	
			}	// changes in ar26	|| loc[sc]==-3 
			else if(loc[pc]==0 && loc[sc]==1)	//pc in, sc out
			{
				
				double[] ret=new double[9];
				
				rec_out_pc=get_extended_axis_pair(v[pc][0], v[pc][1], v[curr][0], v[curr][1]);
				
				buf[0][0]=rec_out_pc[0];
				buf[0][1]=rec_out_pc[1];
				
				int same_window=0;
				
				if( get_window(v[curr][0],v[curr][1]).equals(get_window(v[sc][0],v[sc][1])))
				{
					buf[1][0]=buf[2][0]=buf[0][0];
					buf[1][1]=buf[2][1]=buf[0][1];
					
					same_window=1;
				}
				
				if( get_window(v[curr][0],v[curr][1]).equals("up"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[sc][0],v[sc][1]);
					
			      if( same_window == 1 )
				  {
								if( buf[0][0] == 960 )
								{
									buf[2][0]=buf[1][0]=960;
									buf[2][1]=buf[1][1]=540;
								}
								else if( buf[0][0] == -960 )
								{
									buf[2][0]=buf[1][0]=-960;
									buf[2][1]=buf[1][1]=540;
								}
				  }	
			      else
			      {
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[2][0]=ret[0];
						buf[1][1]=buf[2][1]=ret[1];	// changes in ar31
					}
					else if(ret[4] > 960 && ret[5] > 960)	
					{
						buf[1][0]=buf[2][0]=960;
						buf[1][1]=buf[2][1]=540;
					}
					else if(ret[4] < -960 && ret[5] < -960)
					{
						
						buf[1][0]=buf[2][0]=-960;
						buf[1][1]=buf[2][1]=540;
					}
					else
					{
						buf[2][0]=ret[0];
						buf[2][1]=ret[1];
						
						buf[1][0]=buf[2][0];
						buf[1][1]=buf[2][1];	//optional
						
						if( ret[8] == 143 && buf[0][1] == 540 )
						{
							buf[1][0]=-960;
							buf[1][1]=540;
						}
						else if( ret[8] == 123 && buf[0][1] == 540 )
						{
							buf[1][0]=960;
							buf[1][1]=540;
						}
					}
			      }
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("down"))
				{
				  if( same_window == 1 )
				  {
						if( buf[0][0] == 960 )
						{
							buf[2][0]=buf[1][0]=960;
							buf[2][1]=buf[1][1]=-540;
						}
						else if( buf[0][0] == -960 )
						{
							buf[2][0]=buf[1][0]=-960;
							buf[2][1]=buf[1][1]=-540;
						}
				  }		
				  else
				  {
					ret=get_both_out(v[curr][0],v[curr][1],v[sc][0],v[sc][1]);
					
					if(ret[8] == -1111)
					{
						
						buf[1][0]=buf[2][0]=ret[0];
						buf[1][1]=buf[2][1]=ret[1];	// changes in ar31
					}
					else if(ret[4] > 960 && ret[5] > 960)
					{
						buf[1][0]=buf[2][0]=960;
						buf[1][1]=buf[2][1]=-540;
					}
					else if(ret[4] < -960 && ret[5] < -960)
					{
						
						buf[1][0]=buf[2][0]=-960;
						buf[1][1]=buf[2][1]=-540;
					}
					else
					{
						buf[2][0]=ret[0];
						buf[2][1]=ret[1];
						
						buf[1][0]=buf[2][0];
						buf[1][1]=buf[2][1];	//optional
						
						if( ret[8] == 341 && buf[0][1] == -540 )
						{
							buf[1][0]=-960;
							buf[1][1]=-540;
						}
						else if( ret[8] == 321 && buf[0][1] == -540 )
						{
							buf[1][0]=960;
							buf[1][1]=-540;
						}
					}
				  }
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("right"))
				{
					
				  if( same_window == 1 )
				  {
								if( buf[0][1] == 540 )
								{
									buf[2][0]=buf[1][0]=960;
									buf[2][1]=buf[1][1]=540;
								}
								else if( buf[0][1] == -540 )
								{
									buf[2][0]=buf[1][0]=960;
									buf[2][1]=buf[1][1]=-540;
								}
				  }
				  else
				  {
					
					ret=get_both_out(v[curr][0],v[curr][1],v[sc][0],v[sc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[2][0]=ret[0];
						buf[1][1]=buf[2][1]=ret[1];	// changes in ar31
					}
					else if(ret[6] > 540 && ret[7] > 540)	
					{
						buf[1][0]=buf[2][0]=960;
						buf[1][1]=buf[2][1]=540;
					}
					else if(ret[6] < -540 && ret[7] < -540)
					{
						buf[1][0]=buf[2][0]=960;
						buf[1][1]=buf[2][1]=-540;
					}
					else
					{
						buf[2][0]=ret[0];
						buf[2][1]=ret[1];
						
						buf[1][0]=buf[2][0];
						buf[1][1]=buf[2][1];	//optional
						
						// correction added in ar23.java
						if( ret[8] == 214 && buf[0][0] == 960)
						{
							buf[1][0]=960;
							buf[1][1]=540;
						}
						else if( ret[8] == 234 && buf[0][0] == 960 )
						{
							buf[1][0]=960;
							buf[1][1]=-540;
						}
					}
				  }
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("left"))
				{
			      if( same_window == 1 )
				  {
									if( buf[0][1] == 540 )
									{
										buf[2][0]=buf[1][0]=-960;
										buf[2][1]=buf[1][1]=540;
									}
									else if( buf[0][1] == -540 )
									{
										buf[2][0]=buf[1][0]=-960;
										buf[2][1]=buf[1][1]=-540;
									}
				  }
			      else
			      {
					ret=get_both_out(v[curr][0],v[curr][1],v[sc][0],v[sc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[2][0]=ret[0];
						buf[1][1]=buf[2][1]=ret[1];	// changes in ar31
					}
					else if(ret[6] > 540 && ret[7] > 540)	
					{
						buf[1][0]=buf[2][0]=-960;
						buf[1][1]=buf[2][1]=540;
					}
					else if(ret[6] < -540 && ret[7] < -540)
					{
						buf[1][0]=buf[2][0]=-960;
						buf[1][1]=buf[2][1]=-540;
					}
					else
					{
						buf[2][0]=ret[0];
						buf[2][1]=ret[1];
						
						buf[1][0]=buf[2][0];
						buf[1][1]=buf[2][1];	//optional
						
						if( ret[8] == 412 && buf[0][0] == -960 )
						{
							buf[1][0]=-960;
							buf[1][1]=540;
						}
						else if( ret[8] == 432 && buf[0][0] == -960 )
						{
							buf[1][0]=-960;
							buf[1][1]=-540;
						}
					}
			      }
				}				
				
			}	
			else if( (loc[pc]==1 ) && loc[sc]==0)	//pc out, sc in
			{
				
				double[] ret=new double[9];
				
				rec_out_pc=get_extended_axis_pair(v[sc][0], v[sc][1], v[curr][0], v[curr][1]);
				
				buf[2][0]=rec_out_pc[0];
				buf[2][1]=rec_out_pc[1];
				
				int same_window=0;
				
				if( get_window(v[curr][0],v[curr][1]).equals(get_window(v[pc][0],v[pc][1])))
				{
					
					buf[0][0]=buf[1][0]=buf[2][0];
					buf[0][1]=buf[1][1]=buf[2][1];
					
					same_window=1;
				}
				
				if( get_window(v[curr][0],v[curr][1]).equals("up"))
				{
				  if( same_window == 1 )
				  {
							if( buf[2][0] == 960 )
							{
								buf[0][0]=buf[1][0]=960;
								buf[0][1]=buf[1][1]=540;
							}
							else if( buf[2][0] == -960 )
							{
								buf[0][0]=buf[1][0]=-960;
								buf[0][1]=buf[1][1]=540;
							}
				  }
				  else
				  {					
					ret=get_both_out(v[curr][0],v[curr][1],v[pc][0],v[pc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[0][0]=ret[0];
						buf[1][1]=buf[0][1]=ret[1];	// changes in ar31
					}
					else if(ret[4] > 960 && ret[5] > 960)	
					{
						buf[1][0]=buf[0][0]=960;
						buf[1][1]=buf[0][1]=540;
					}
					else if(ret[4] < -960 && ret[5] < -960)
					{
						buf[1][0]=buf[0][0]=-960;
						buf[1][1]=buf[0][1]=540;						
					}
					else
					{
						buf[0][0]=ret[0];
						buf[0][1]=ret[1];
						
						buf[1][0]=buf[0][0];
						buf[1][1]=buf[0][1];	//optional
						
						if( ret[8] == 143 && buf[2][1] == 540 )
						{
							buf[1][0]=-960;
							buf[1][1]=540;
						}
						else if( ret[8] == 123 && buf[2][1] == 540 )
						{
							buf[1][0]=960;
							buf[1][1]=540;
						}
					}
				  }
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("down"))
				{				
				  if( same_window == 1 )
				  {
								if( buf[2][0] == 960 )
								{
									buf[0][0]=buf[1][0]=960;
									buf[0][1]=buf[1][1]=-540;
								}
								else if( buf[2][0] == -960 )
								{
									buf[0][0]=buf[1][0]=-960;
									buf[0][1]=buf[1][1]=-540;
								}
				  }
				  else
				  {
					
					ret=get_both_out(v[curr][0],v[curr][1],v[pc][0],v[pc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[0][0]=ret[0];
						buf[1][1]=buf[0][1]=ret[1];	// changes in ar31
					}
					else if(ret[4] > 960 && ret[5] > 960)
					{
						buf[1][0]=buf[0][0]=960;
						buf[1][1]=buf[0][1]=-540;
					}
					else if(ret[4] < -960 && ret[5] < -960)
					{
						buf[1][0]=buf[0][0]=-960;
						buf[1][1]=buf[0][1]=-540;
					}
					else
					{
						buf[0][0]=ret[0];
						buf[0][1]=ret[1];
						
						buf[1][0]=buf[0][0];
						buf[1][1]=buf[0][1];	//optional
						
						if( ret[8] == 341 && buf[2][1] == -540 )
						{
							buf[1][0]=-960;
							buf[1][1]=-540;
						}
						else if( ret[8] == 321 && buf[2][1] == -540 )
						{
							buf[1][0]=960;
							buf[1][1]=-540;
						}
					}
				  }
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("right"))
				{
					
				  if( same_window == 1 )
				  {
							if( buf[2][1] == 540 )
							{
								buf[0][0]=buf[1][0]=960;
								buf[0][1]=buf[1][1]=540;
							}
							else if( buf[2][1] == -540 )
							{
								buf[0][0]=buf[1][0]=960;
								buf[0][1]=buf[1][1]=-540;
							}
				  }	
				  else
				  {
					ret=get_both_out(v[curr][0],v[curr][1],v[pc][0],v[pc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[0][0]=ret[0];
						buf[1][1]=buf[0][1]=ret[1];	// changes in ar31
					}
					else if(ret[6] > 540 && ret[7] > 540)	
					{
						buf[1][0]=buf[0][0]=960;
						buf[1][1]=buf[0][1]=540;
					}
					else if(ret[6] < -540 && ret[7] < -540)
					{
						buf[1][0]=buf[0][0]=960;
						buf[1][1]=buf[0][1]=-540;
					}
					else
					{
						buf[0][0]=ret[0];
						buf[0][1]=ret[1];
						
						buf[1][0]=buf[0][0];
						buf[1][1]=buf[0][1];	//optional
						
						if( ret[8] == 214 && buf[2][0] == 960)
						{
							buf[1][0]=960;
							buf[1][1]=540;
						}
						else if( ret[8] == 234 && buf[2][0] == 960 )
						{
							buf[1][0]=960;
							buf[1][1]=-540;
						}
					}
				  }
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("left"))
				{
					
				  if( same_window == 1 )
				  {
								if( buf[2][1] == 540 )
								{
									buf[0][0]=buf[1][0]=-960;
									buf[0][1]=buf[1][1]=540;
								}
								else if( buf[2][1] == -540 )
								{
									buf[0][0]=buf[1][0]=-960;
									buf[0][1]=buf[1][1]=-540;
								}
				  }	
				  else
				  {
					
					ret=get_both_out(v[curr][0],v[curr][1],v[pc][0],v[pc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[0][0]=ret[0];
						buf[1][1]=buf[0][1]=ret[1];	// changes in ar31
					}
					else if(ret[6] > 540 && ret[7] > 540)	
					{
						buf[1][0]=buf[0][0]=-960;
						buf[1][1]=buf[0][1]=540;
					}
					else if(ret[6] < -540 && ret[7] < -540)
					{
						buf[1][0]=buf[0][0]=-960;
						buf[1][1]=buf[0][1]=-540;
					}
					else
					{
						buf[0][0]=ret[0];
						buf[0][1]=ret[1];
						
						buf[1][0]=buf[0][0];
						buf[1][1]=buf[0][1];	//optional
						
						if( ret[8] == 412 && buf[2][0] == -960 )
						{
							buf[1][0]=-960;
							buf[1][1]=540;
						}
						else if( ret[8] == 432 && buf[2][0] == -960 )
						{
							buf[1][0]=-960;
							buf[1][1]=-540;
						}
					}
				  }
				}				
				
			}	
			else if(loc[pc]==1 && loc[sc]==1 )	//pc out, sc out
			{				
				double[] ret=new double[9];			
				
				if( get_window(v[curr][0],v[curr][1]).equals(get_window(v[pc][0],v[pc][1])))// pcout..(in)
				{
					buf[0][0]=buf[1][0]=-8888;
					buf[0][1]=buf[1][1]=-8888;
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("up"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[pc][0],v[pc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[0][0]=ret[0];
						buf[1][1]=buf[0][1]=ret[1];	// changes in ar31
					}
					else if(ret[4] > 960 && ret[5] > 960)	
					{
						buf[1][0]=buf[0][0]=960;
						buf[1][1]=buf[0][1]=540;
					}
					else if(ret[4] < -960 && ret[5] < -960)
					{
						buf[1][0]=buf[0][0]=-960;
						buf[1][1]=buf[0][1]=540;
					}
					else
					{
						buf[0][0]=ret[0];
						buf[0][1]=ret[1];
						
						buf[1][0]=buf[0][0];
						buf[1][1]=buf[0][1];	//optional
					}
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("down"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[pc][0],v[pc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[0][0]=ret[0];
						buf[1][1]=buf[0][1]=ret[1];	// changes in ar31
					}
					else if(ret[4] > 960 && ret[5] > 960)
					{
						buf[1][0]=buf[0][0]=960;
						buf[1][1]=buf[0][1]=-540;
					}
					else if(ret[4] < -960 && ret[5] < -960)
					{
						buf[1][0]=buf[0][0]=-960;
						buf[1][1]=buf[0][1]=-540;
					}
					else
					{
						buf[0][0]=ret[0];
						buf[0][1]=ret[1];
						
						buf[1][0]=buf[0][0];
						buf[1][1]=buf[0][1];	//optional
					}
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("right"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[pc][0],v[pc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[0][0]=ret[0];
						buf[1][1]=buf[0][1]=ret[1];	// changes in ar31
					}
					else if(ret[6] > 540 && ret[7] > 540)	
					{
						buf[1][0]=buf[0][0]=960;
						buf[1][1]=buf[0][1]=540;
					}
					else if(ret[6] < -540 && ret[7] < -540)
					{
						buf[1][0]=buf[0][0]=960;
						buf[1][1]=buf[0][1]=-540;
					}
					else
					{
						buf[0][0]=ret[0];
						buf[0][1]=ret[1];
						
						buf[1][0]=buf[0][0];
						buf[1][1]=buf[0][1];	//optional
					}
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("left"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[pc][0],v[pc][1]);
					
					if(ret[8] == -1111)
					{
						buf[1][0]=buf[0][0]=ret[0];
						buf[1][1]=buf[0][1]=ret[1];	// changes in ar31
					}
					else if(ret[6] > 540 && ret[7] > 540)	
					{
						buf[1][0]=buf[0][0]=-960;
						buf[1][1]=buf[0][1]=540;
					}
					else if(ret[6] < -540 && ret[7] < -540)
					{
						buf[1][0]=buf[0][0]=-960;
						buf[1][1]=buf[0][1]=-540;
					}
					else
					{
						buf[0][0]=ret[0];
						buf[0][1]=ret[1];
						
						buf[1][0]=buf[0][0];
						buf[1][1]=buf[0][1];	//optional
					}
				}		
				
				/// ***
				
				if( get_window(v[curr][0],v[curr][1]).equals(get_window(v[sc][0],v[sc][1]))) //scout
				{
					buf[1][0]=buf[2][0]=-8888;
					buf[1][1]=buf[2][1]=-8888;
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("up"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[sc][0],v[sc][1]);
					
					if(ret[4] > 960 && ret[5] > 960)	
					{
						buf[1][0]=buf[2][0]=960;
						buf[1][1]=buf[2][1]=540;
					}
					else if(ret[4] < -960 && ret[5] < -960)
					{
						buf[1][0]=buf[2][0]=-960;
						buf[1][1]=buf[2][1]=540;
					}
					else
					{
						buf[2][0]=ret[0];
						buf[2][1]=ret[1];
						
						buf[1][0]=buf[2][0];
						buf[1][1]=buf[2][1];	//optional
						
						if( ret[8] == 143 && ( buf[0][1] == 540 || buf[0][1] == -8888 ) )//added in ar32
						{
							buf[1][0]=-960;
							buf[1][1]=540;
						}
						else if( ret[8] == 123 && ( buf[0][1] == 540 || buf[0][1] == -8888 ) )
						{
							buf[1][0]=960;
							buf[1][1]=540;
						}
					}
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("down"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[sc][0],v[sc][1]);
					
					if(ret[4] > 960 && ret[5] > 960)
					{
						buf[1][0]=buf[2][0]=960;
						buf[1][1]=buf[2][1]=-540;
					}
					else if(ret[4] < -960 && ret[5] < -960)
					{
						buf[1][0]=buf[2][0]=-960;
						buf[1][1]=buf[2][1]=-540;
					}
					else
					{
						buf[2][0]=ret[0];
						buf[2][1]=ret[1];
						
						buf[1][0]=buf[2][0];
						buf[1][1]=buf[2][1];	//optional
						
						if( ret[8] == 341 && ( buf[0][1] == -540 || buf[0][1] == -8888 ) )
						{
							buf[1][0]=-960;
							buf[1][1]=-540;
						}
						else if( ret[8] == 321 && ( buf[0][1] == -540 || buf[0][1] == -8888 ) )
						{
							buf[1][0]=960;
							buf[1][1]=-540;
						}
					}
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("right"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[sc][0],v[sc][1]);
					
					if(ret[6] > 540 && ret[7] > 540)	
					{
						buf[1][0]=buf[2][0]=960;
						buf[1][1]=buf[2][1]=540;
					}
					else if(ret[6] < -540 && ret[7] < -540)
					{
						buf[1][0]=buf[2][0]=960;
						buf[1][1]=buf[2][1]=-540;
					}
					else
					{
						
						buf[2][0]=ret[0];
						buf[2][1]=ret[1];
						
						buf[1][0]=buf[2][0];
						buf[1][1]=buf[2][1];	//optional
						
						if( ret[8] == 214 && ( buf[0][0] == 960 || buf[0][0] == -8888 ) )
						{
							buf[1][0]=960;
							buf[1][1]=540;
						}
						else if( ret[8] == 234 && ( buf[0][0] == 960 || buf[0][0] == -8888 ) )
						{
							buf[1][0]=960;
							buf[1][1]=-540;
						}
					}
				}
				else if( get_window(v[curr][0],v[curr][1]).equals("left"))
				{
					ret=get_both_out(v[curr][0],v[curr][1],v[sc][0],v[sc][1]);
					
					if(ret[6] > 540 && ret[7] > 540)	
					{
						buf[1][0]=buf[2][0]=-960;
						buf[1][1]=buf[2][1]=540;
					}
					else if(ret[6] < -540 && ret[7] < -540)
					{
						buf[1][0]=buf[2][0]=-960;
						buf[1][1]=buf[2][1]=-540;
					}
					else
					{
						buf[2][0]=ret[0];
						buf[2][1]=ret[1];
						
						buf[1][0]=buf[2][0];
						buf[1][1]=buf[2][1];	//optional
						
						if( ret[8] == 412 && ( buf[0][0] == -960 || buf[0][0] == -8888 ) )
						{
							buf[1][0]=-960;
							buf[1][1]=540;
						}
						else if( ret[8] == 432 && ( buf[0][0] == -960 || buf[0][0] == -8888 ) )
						{
							buf[1][0]=-960;
							buf[1][1]=-540;
						}
					}
				}				
			}
		}
	}
	
	return buf;
}

static void adjust_plane()
{
	int first,second,third;
	
	for(int t=0;t<triCount;t++)
	{
		first=t*3;
		second=t*3 + 1;
		third=t*3 + 2;
	
		
		buff[first] = adjust_point(third,first,second);
		buff[second] = adjust_point(first,second,third);
		buff[third] = adjust_point(second,third,first);
	}
	
}	// end adjust_mirrot_plane

static void adjust()
{
	int j;
	
	for(j=0;j<pointCount;j++)
	{
		if( ( Math.abs(v[j][0])>960 || Math.abs(v[j][1])>540 )  )
		{
			if(loc[j] != -3 )
				loc[j]=1;
		}
	}
	
	adjust_plane();
	
	for(j=0;j<pointCount;j++)
	{	
		if( buff[j][0][0]!=-8888 )
			buff[j][0][0] = buff[j][0][0] + 960;
		if( buff[j][1][0]!=-8888 )
			buff[j][1][0] = buff[j][1][0] + 960;
		if( buff[j][2][0]!=-8888 )
			buff[j][2][0] = buff[j][2][0] + 960;
		
		if( buff[j][0][1]!=-8888 )
			{
				buff[j][0][1] = 540 - buff[j][0][1];
			}
		if( buff[j][1][1]!=-8888 )
			{
				buff[j][1][1] = 540 - buff[j][1][1];
			}
		if( buff[j][2][1]!=-8888 )
			{
				buff[j][2][1] = 540 - buff[j][2][1];	
			}
	}
}

///////////////////////////////////////////////////////////////////////////////////////////
public static void trans()
{
	
	for(int i=0;i<pointCount;i++)
	{		
		radii[i] = displ( p[i], a, d );
		
		angle[i]=Math.atan( (a[1]-p[i][1])/(a[0]-p[i][0] ) );	
		
		double[] rel_v=get_rel_vect(d, diff(a,p[i]) );				
		
		tx= zoom*radii[i]*rel_v[0];
		ty= zoom*radii[i]*rel_v[1];
		
		if(rel_v[2]==-3)
		{
			loc[i]=-3;
			
		}
		
		v[i][0]=tx;
		v[i][1]=ty;				
	}
	
	adjust();
	
	
}

static void addTriangle(double[][] trig)
{
	p[pointCount]=new double[3];
	p[pointCount +1]=new double[3];
	p[pointCount +2]=new double[3];
	
	p[pointCount]=trig[0];
	p[pointCount+1]=trig[1];
	p[pointCount+2]=trig[2];
		
	pointCount+=3;
	triCount+=1;
}

static void addPlane(double[][] plane)
{
	double[][] triangle = new double[3][3];
	
	triangle[0] = plane[1];
	triangle[1] = plane[0];
	triangle[2] = plane[3];
	
	addTriangle(triangle);
	
	triangle[0] = plane[1];
	triangle[1] = plane[2];
	triangle[2] = plane[3];
	
	addTriangle(triangle);
	
}

static void addBox(double[][] box)
{
	double[][] plane = new double[6][3];
	
	//base
	plane[0] = box[0];
	plane[1] = box[1];
	plane[2] = box[2];
	plane[3] = box[3];
	
	addPlane(plane);
	
	//top
	plane[0] = box[4];
	plane[1] = box[5];
	plane[2] = box[6];
	plane[3] = box[7];
	
	addPlane(plane);
	
	//YZ near	
	plane[0] = box[3];
	plane[1] = box[0];
	plane[2] = box[4];
	plane[3] = box[7];
	
	addPlane(plane);
	
	//YZ far
	plane[0] = box[2];
	plane[1] = box[1];
	plane[2] = box[5];
	plane[3] = box[6];
	
	addPlane(plane);
	
	// XZ near
	plane[0] = box[0];
	plane[1] = box[1];
	plane[2] = box[5];
	plane[3] = box[4];
	
	addPlane(plane);
	
	//XZ far
	plane[0] = box[3];
	plane[1] = box[2];
	plane[2] = box[6];
	plane[3] = box[7];
	
	addPlane(plane);
}
///////////////////////////////////////////   EDIT

static void editTriangle(double[][] trig, int editLoc)
{
	p[editLoc]=trig[0];
	p[editLoc+1]=trig[1];
	p[editLoc+2]=trig[2];
	
}

static void editPlane(double[][] plane, int editLoc)
{
	double[][] triangle = new double[3][3];
	
	triangle[0] = plane[1];
	triangle[1] = plane[0];
	triangle[2] = plane[3];
	
	editTriangle(triangle, editLoc);
	
	triangle[0] = plane[1];
	triangle[1] = plane[2];
	triangle[2] = plane[3];
	
	editTriangle(triangle, editLoc + 3);
	
}

static void editBox(double[][] box, int editLoc)
{
	double[][] plane = new double[6][3];
	
	//base
	plane[0] = box[0];
	plane[1] = box[1];
	plane[2] = box[2];
	plane[3] = box[3];
	
	editPlane(plane, editLoc);
	
	//top
	plane[0] = box[4];
	plane[1] = box[5];
	plane[2] = box[6];
	plane[3] = box[7];
	
	editPlane(plane, editLoc + 6);
	
	//YZ near	
	plane[0] = box[3];
	plane[1] = box[0];
	plane[2] = box[4];
	plane[3] = box[7];
	
	editPlane(plane, editLoc + 12);
	
	//YZ far
	plane[0] = box[2];
	plane[1] = box[1];
	plane[2] = box[5];
	plane[3] = box[6];
	
	editPlane(plane, editLoc + 18);
	
	// XZ near
	plane[0] = box[0];
	plane[1] = box[1];
	plane[2] = box[5];
	plane[3] = box[4];
	
	editPlane(plane, editLoc + 24);
	
	//XZ far
	plane[0] = box[3];
	plane[1] = box[2];
	plane[2] = box[6];
	plane[3] = box[7];
	
	editPlane(plane, editLoc + 36);
}

///////////////////////////////////////////   EDIT END

public void paint(Graphics g)
{		
	blank.addMouseListener(this);
	blank.addMouseMotionListener(this);
	
	
	add(blank,0,0);	// in ar24
    
    
    for(int t=0;t<triCount;t++)
    {	
    	
    	rect[t]=new GPolygon();
    	
    	for(int m=t*3;m<t*3+3;m++)
    	{    		
    		for(int n=0;n<3;n++)
    		{
    			if(buff[m][n][0]!=-8888)
    			{
    					rect[t].addVertex(buff[m][n][0], buff[m][n][1]);
    			}
    		} 
    	}    	
    	
    }
    
    
    preCalc();
    
    for( int z=0;z<triCount;z++)
    {
    	
    	int x = (int) comDist[z][1];
    	
    	rect[x].setFillColor(myColor);
		rect[x].setFilled(true); 
		rect[x].setVisible(true);
		add(rect[x]);
    }	
}

static void sortDist()
{
	int c,d;
	double swap;
	
	for (c = 0; c < ( triCount - 1 ); c++) 
	{
	      for (d = 0; d < triCount - c - 1; d++) 
	      {
	        if (comDist[d][0] < comDist[d+1][0]) /* For descending order use < */
	        {
	          swap       = comDist[d][0];
	          comDist[d][0]   = comDist[d+1][0];
	          comDist[d+1][0] = swap;
	          
	          swap       = comDist[d][1];
	          comDist[d][1]   = comDist[d+1][1];
	          comDist[d+1][1] = swap;	          
	        }
	      }
	 }
}
	
static void calcDist()
{
	double[] comass = new double[3];
	
	for(int i=0;i<triCount;i++)
	{
		comass[0]=COM[i][0];
		comass[1]=COM[i][1];
		comass[2]=COM[i][2];
						
		comDist[i][0] = mod( diff( a, comass ) );
		comDist[i][1] = i;		
	}
}

static void calcCOM()
{
	for(int i=0;i<triCount;i++)
	{
		COM[i][0] = (p[i*3][0] + p[i*3 + 1][0] + p[i*3 + 2][0])/3;
		COM[i][1] = (p[i*3][1] + p[i*3 + 1][1] + p[i*3 + 2][1])/3;
		COM[i][2] = (p[i*3][2] + p[i*3 + 1][2] + p[i*3 + 2][2])/3;
	}
}

static void preCalc()
{
	calcCOM();
	calcDist();
	sortDist();
}

public void keyReleased(KeyEvent arg0) {}

public void keyTyped(KeyEvent ke) {
char ch=ke.getKeyChar();
}

public void actionPerformed(ActionEvent e) {
	
	
    if(e.getSource() == front ) 
    { 
      if( (d[0] + dchange )==0 )
    	  d[0]=dchange;
      else
    	  d[0]+=dchange;
    }
    else if(e.getSource() == back ) 
    {
    	if( (d[0] - dchange )==0 )
    		d[0]=0-dchange;
    	else
    		d[0]-=dchange;
    }
    else if(e.getSource() == up ) 
    {
    	if( (d[2] + dchange )==0 )
    		d[2]=dchange;
    	else
    		d[2]+=dchange;
    }
    else if(e.getSource() == down ) 
    {
    	if( (d[2] - dchange )==0 )
    		d[2]=0-dchange;
    	else
    		d[2]-=dchange;
    }
    else if(e.getSource() ==  left ) 
    {
    	if( (d[1] + dchange )==0 )
    		d[1]=dchange;
    	else
    		d[1]+=dchange;
    }
    else if(e.getSource() == right ) 
    {
    	if( (d[1] - dchange )==0 )
    		d[1]=0-dchange;
    	else
    		d[1]-=dchange;
    }//////////////////////////////////////////////
    else if(e.getSource() == move_X_up ) 
    {
    		a[0]+=schange;
    }
    else if(e.getSource() == move_X_down ) 
    {
    		a[0]-=schange;
    }
    else if(e.getSource() == move_Y_up  ) 
    {
    		a[1]+=schange;
    }
    else if(e.getSource() ==  move_Y_down ) 
    {
    		a[1]-=schange;
    }
    else if(e.getSource() == move_Z_up  ) 
    {
    		a[2]+=schange;
    }
    else if(e.getSource() == move_Z_down  ) 
    {
    		a[2]-=schange;
    }/////////////////////////////////////////////////
    else if(e.getSource() == arrowU)
    {
    	direct( 0, 0, 0, arrowMagni );
    }
    else if(e.getSource() == arrowD)
    {
    	direct( 0, 0, 0, -arrowMagni );
    }
    else if(e.getSource() == arrowR)
    {
    	direct( 0, 0, arrowMagni, 0 );
    }
    else if(e.getSource() == arrowL)
    {
    	direct( 0, 0, -arrowMagni, 0 );
    }
    else if( e.getSource() == in)
    {
    	a[0] = a[0] + move;
	    a[1] = a[1] + move;
	    a[2] = a[2] + move;
    }
    else if( e.getSource() == out)
    {    	
    	a[0] = a[0] - move;
	    a[1] = a[1] - move;
	    a[2] = a[2] - move;	    
    }/////////////////////////////////////////////////
    else if( e.getSource() == hXup )
    {
    	h1.seek(new double[] {hMove, 0, 0});
    }
    else if( e.getSource() == hXdown )
    {
    	h1.seek(new double[] {-hMove, 0, 0});
    }
    else if( e.getSource() == hYup )
    {
    	h1.seek(new double[] {0, hMove, 0});
    }
    else if( e.getSource() == hYdown )
    {
    	h1.seek(new double[] {0, -hMove, 0});
    }
    else if( e.getSource() == hZup )
    {
    	h1.seek(new double[] {0, 0, hMove});
    }
    else if( e.getSource() == hZdown )
    {
    	h1.seek(new double[] {0, 0, -hMove});
    }///////////////////////////////////////////////
    else if( e.getSource() == hXCW )
    {
    	h1.rotateX(hMove, h1.body.getCOM());
    }
    else if( e.getSource() == hXACW )
    {
    	h1.rotateX(-hMove, h1.body.getCOM());
    }
    else if( e.getSource() == hYCW )
    {
    	h1.rotateY(hMove, h1.body.getCOM());
    }
    else if( e.getSource() == hYACW )
    {
    	h1.rotateY(-hMove, h1.body.getCOM());
    }
    else if( e.getSource() == hZCW )
    {
    	h1.rotateZ(hMove, h1.body.getCOM());
    }
    else if( e.getSource() == hZACW )
    {
    	h1.rotateZ(-hMove, h1.body.getCOM());
    }//////////////////////////////////////////////
    else if( e.getSource() == play )
    {
    	if(playFlag == false)
    		playFlag = true;
    	else
    		playFlag = false;
    }
    else if( e.getSource() == reset )
    {
    	resetSpace();
    }
    
    trans();
	
	for(j=0;j<pointCount;j++)
		loc[j]=0;
	
    repaint();
}


	static void direct(int mousefX,int mousefY,int mousetX,int mousetY)
	{
		int dTheta = mousetX - mousefX;
		int dPhi = mousetY - mousefY;
		
		double theta = get_theta(d);
		double phi = get_phi(d)*(-1);	
		
		double nowTheta = 1;
		double nowPhi = 85;
		
		if( theta + dTheta > 360 )
		{
			nowTheta = 1;
		}
		else if( theta + dTheta < 0 )
		{
			nowTheta = 359;
		}
		else
		{
			nowTheta = theta + dTheta;
		}
		
		
		if( phi + dPhi > 180 )
		{
			nowTheta = theta;
		}
		else if( phi + dPhi < 0 )
		{
			nowTheta = theta;
		}
		else
		{
			nowPhi = phi + dPhi;
		}
		
		d[0] = Math.sin(Math.toRadians(nowPhi))*Math.cos(Math.toRadians(nowTheta));	// sinphi costheta
		d[1] = Math.sin(Math.toRadians(nowPhi))*Math.sin(Math.toRadians(nowTheta));	// sinphi sintheta
		d[2] = Math.cos(Math.toRadians(nowPhi));	// cosphi
		
		
	}
	
	
	public void mouseDragged(MouseEvent pointer)
	{		
		if(mousecounter < mouse_counter_controller)
		{
			
		}
		else
		{
			mousecounter=0;
			
			mousetX=pointer.getX();
			mousetY=pointer.getY();
			
			direct( mousefX, mousefY, mousetX, mousetY );
			
			trans();
			repaint();
			
			mousefX=pointer.getX();
			mousefY=pointer.getY();			
		}
		
		mousecounter++;
	}

	public void mousePressed(MouseEvent arg0)
	{		
		mousefX=arg0.getX();
		mousefY=arg0.getY();
		
		mousecounter=0;
	}

	@Override
	public void mouseWheelMoved(MouseWheelEvent e) {

		int notches = e.getWheelRotation();
	   
	    a[0] = a[0] + move*notches;
	    a[1] = a[1] + move*notches;
	    a[2] = a[2] + move*notches;
	    
	    trans();
	    repaint();
	}

	public void resetSpace()
	{
		addTriangle(xAxis.initTriangle3D(this));
		
		addTriangle(yAxis.initTriangle3D(this));
		
		addTriangle(zAxis.initTriangle3D(this));	
		
		addBox(bx1.initBox3D(this));
		
		addPlane(pyrbase.initPlane3D(this));
		
		addTriangle(pyrt1.initTriangle3D(this));
		
		addTriangle(pyrt2.initTriangle3D(this));
		
		addTriangle(pyrt3.initTriangle3D(this));
		
		addTriangle(pyrt4.initTriangle3D(this));
		
		//''''''''
		
		addBox(bx2.initBox3D(this));		
		
		addPlane(pyrbase2.initPlane3D(this));
		
		addTriangle(pyrt12.initTriangle3D(this));
		
		addTriangle(pyrt22.initTriangle3D(this));
		
		addTriangle(pyrt32.initTriangle3D(this));
		
		addTriangle(pyrt42.initTriangle3D(this));
		
		addPlane(p1.initPlane3D(this));
		addPlane(p2.initPlane3D(this));

		h1.initHelicopter3D(this);
		
		h1.seek(new double[] {1000,70,50});
		h1.rotateZ(90,h1.body.getCOM());
		
		sp1.initSphere(this);
		
		trans();		
		repaint();
	}
	
	public void run()
	{	
		/*
		addTriangle(xAxis.initTriangle3D(this));
		
		addTriangle(yAxis.initTriangle3D(this));
		
		addTriangle(zAxis.initTriangle3D(this));	
		/*
		addBox(bx1.initBox3D(this));
		//bx1.seek(new double[] {0,500,0});
		//bx1.rotateX(25, bx1.getCorner(3));
		//editBox(bx1.getBox3D(),bx1.initPoint);
		
		
		addPlane(pyrbase.initPlane3D(this));
		
		addTriangle(pyrt1.initTriangle3D(this));
		
		addTriangle(pyrt2.initTriangle3D(this));
		
		addTriangle(pyrt3.initTriangle3D(this));
		
		addTriangle(pyrt4.initTriangle3D(this));
		
		//''''''''
		
		addBox(bx2.initBox3D(this));
		//bx1.seek(new double[] {0,500,0});
		//bx1.rotateX(25, bx1.getCorner(3));
		//editBox(bx1.getBox3D(),bx1.initPoint);
		
		
		addPlane(pyrbase2.initPlane3D(this));
		
		addTriangle(pyrt12.initTriangle3D(this));
		
		addTriangle(pyrt22.initTriangle3D(this));
		
		addTriangle(pyrt32.initTriangle3D(this));
		
		addTriangle(pyrt42.initTriangle3D(this));
		
		
		addPlane(p1.initPlane3D(this));
		addPlane(p2.initPlane3D(this));
		//p1.seek(new double[] {0,50,200});
		//p1.rotateX(45);
		//editPlane(p1.getPlane3D(),p1.initPoint);
		

		//ttest.seek(new double[] {0,50,0});
		//ttest.rotateX(45, ttest.getCOM());
		//editTriangle(ttest.getTriangle3D(),ttest.initPoint);
		
		/////////////////////////////////////////////////////////////////
		h1.initHelicopter3D(this);
		
		h1.seek(new double[] {1000,70,50});
		h1.rotateZ(90,h1.body.getCOM());
		*/
		
	//	sp1.initSphere(this);
		
		sp2.initSphere(this);
		sp3.initSphere(this);
		sp4.initSphere(this);
		sp5.initSphere(this);
		
	//	sp1.rotateX(90, sp1.center);

	//	sp1.seek(new double[] {0,20,0});
	//	sp1.rotateY(-90, sp1.center);
	//	sp1.editSphere(this);
		/*
		psp1.initPartialSphere(this);
		
		sp1.seek(new double[]{50,0,50});
		psp1.seek(new double[]{50,0,50});
		//psp1.rotateX(-30, psp1.center);
		*/
		/*
		ssp1.initSliceSphere(this);
		ssp2.initSliceSphere(this);
		
		ssp1.rotateX(90, ssp1.center);
		ssp2.rotateX(90, ssp2.center);
		*/
		
		trd1.initToroid(this);
		//trd1.seek(new double[] {0,0,100});
		
		
		//trd1.rotateZ(90, trd1.center);
		
		trans();		
		repaint();
		
		boolean start = true;
		
		while(start)
		{
			if(playFlag)
			{
		//		h1.circulateLift();
		//		h1.circulateRotate();
		//		h1.seek(new double[] {0,0,0});
			
		//		sp1.rotateZ(1, sp1.center);
				
				sp2.rotateY(-5, new double[]{500,0,0});
				sp3.rotateY(5, new double[]{-500,0,0});
				sp4.rotateX(5, new double[]{0,500,0});
				sp5.rotateX(-5, new double[]{0,-500,0});
				
		//		psp1.rotateZ(10, psp1.center);
				//psp1.rotateX(10, psp1.center);
				
//				ssp1.rotateY(2, ssp1.center);
//				ssp1.expand(0.1);
				
//				ssp2.rotateY(-2, ssp2.center);
//				ssp2.expand(0.1);
				
		//		trd1.rotateY(-90, trd1.center);
			}
			
			
			////#//^System.out.println("Cntr="+Arrays.toString(sp1.center));
			
			trans();
			repaint();
			
		//	h1.circulateLiftRecover();
		//	h1.circulateRotateRecover();
			
			try	{Thread.sleep(60);}	catch(Exception e){}
		}
		
	}
}

class Triangle3D	//////////////////////////////////////////////////	TRIANGLE
{
	double[][] triangle3D=new double[3][3];
	int initPoint;
	
	Triangle3D(double[][] triangle3D)
	{
		this.triangle3D = triangle3D;
	}
	
	double[][] initTriangle3D(ar43b ar)
	{
		initPoint = ar.pointCount;
		
		return this.triangle3D;
	}
	
	double[][] getTriangle3D()
	{
		return this.triangle3D;
	}
	
	void seek(double[] move)
	{
		for(int i=0;i<3;i++)
		{
			triangle3D[i][0] += move[0];
			triangle3D[i][0] += move[0];
			triangle3D[i][0] += move[0];
			
			triangle3D[i][1] += move[1];
			triangle3D[i][1] += move[1];
			triangle3D[i][1] += move[1];
			
			triangle3D[i][2] += move[2];
			triangle3D[i][2] += move[2];
			triangle3D[i][2] += move[2];
		}
	}
	
	double[] getCorner(int n)
	{
		return triangle3D[n];
	}
	
	double[] getCOM()
	{
		double[] COM = new double[3];		

		COM[0] = (triangle3D[0][0] + triangle3D[1][0] + triangle3D[2][0])/3;
		COM[1] = (triangle3D[0][1] + triangle3D[1][1] + triangle3D[2][1])/3;
		COM[2] = (triangle3D[0][2] + triangle3D[1][2] + triangle3D[2][2])/3;		
		
		return COM;		
	}
	
	void rotateX(double degree, double[] COM)
	{
		int i;		
		
		double[][] relV = new double[3][3];
		
		for(i=0;i<3;i++)
		{
			relV[i][0] = triangle3D[i][0] - COM[0];
			relV[i][1] = triangle3D[i][1] - COM[1];
			relV[i][2] = triangle3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[3][3];
		
		for(i=0;i<3;i++)
		{
			relNewV[i][1] = relV[i][1]*Math.cos(radian) - relV[i][2]*Math.sin(radian);
			relNewV[i][2] = relV[i][1]*Math.sin(radian) + relV[i][2]*Math.cos(radian);
			relNewV[i][0] = relV[i][0];
		}
		
		for(i=0;i<3;i++)
		{
			triangle3D[i][0] = COM[0] + relNewV[i][0];
			triangle3D[i][1] = COM[1] + relNewV[i][1];
			triangle3D[i][2] = COM[2] + relNewV[i][2];
		}				
	}
	
	void rotateY(double degree, double[] COM)
	{
		int i;
		
		double[][] relV = new double[3][3];
		
		for(i=0;i<3;i++)
		{
			relV[i][0] = triangle3D[i][0] - COM[0];
			relV[i][1] = triangle3D[i][1] - COM[1];
			relV[i][2] = triangle3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[3][3];
		
		for(i=0;i<3;i++)
		{
			relNewV[i][2] = relV[i][2]*Math.cos(radian) - relV[i][0]*Math.sin(radian);
			relNewV[i][0] = relV[i][2]*Math.sin(radian) + relV[i][0]*Math.cos(radian);
			relNewV[i][1] = relV[i][1];
		}
		
		for(i=0;i<3;i++)
		{
			triangle3D[i][0] = COM[0] + relNewV[i][0];
			triangle3D[i][1] = COM[1] + relNewV[i][1];
			triangle3D[i][2] = COM[2] + relNewV[i][2];
		}				
	}
	
	void rotateZ(double degree, double[] COM)
	{
		int i;		
		
		double[][] relV = new double[3][3];
		
		for(i=0;i<3;i++)
		{
			relV[i][0] = triangle3D[i][0] - COM[0];
			relV[i][1] = triangle3D[i][1] - COM[1];
			relV[i][2] = triangle3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[3][3];
		
		for(i=0;i<3;i++)
		{
			relNewV[i][0] = relV[i][0]*Math.cos(radian) - relV[i][1]*Math.sin(radian);
			relNewV[i][1] = relV[i][0]*Math.sin(radian) + relV[i][1]*Math.cos(radian);
			relNewV[i][2] = relV[i][2];
		}
		
		for(i=0;i<3;i++)
		{
			triangle3D[i][0] = COM[0] + relNewV[i][0];
			triangle3D[i][1] = COM[1] + relNewV[i][1];
			triangle3D[i][2] = COM[2] + relNewV[i][2];
		}				
	}
}

class Plane3D	//////////////////////////////////////////////////	PLANE
{
	double[][] plane3D=new double[4][3];
	int initPoint;
	
	Plane3D(double[][] plane3D)
	{
		this.plane3D = plane3D;
	}
	
	double[][] initPlane3D(ar43b ar)
	{
		initPoint = ar.pointCount;
		
		return this.plane3D;
	}
	
	double[][] getPlane3D()
	{
		return this.plane3D;
	}
	
	void seek(double[] move)
	{
		for(int i=0;i<4;i++)
		{
			plane3D[i][0] += move[0];
			plane3D[i][0] += move[0];
			plane3D[i][0] += move[0];
			
			plane3D[i][1] += move[1];
			plane3D[i][1] += move[1];
			plane3D[i][1] += move[1];
			
			plane3D[i][2] += move[2];
			plane3D[i][2] += move[2];
			plane3D[i][2] += move[2];
		}
	}
	
	double[] getCorner(int n)
	{
		return plane3D[n];
	}
	
	double[] getCOM()
	{
		double[] COM = new double[3];
		
		COM[0] = (plane3D[0][0] + plane3D[1][0] + plane3D[2][0] + plane3D[3][0])/4;
		COM[1] = (plane3D[0][1] + plane3D[1][1] + plane3D[2][1] + plane3D[3][1])/4;
		COM[2] = (plane3D[0][2] + plane3D[1][2] + plane3D[2][2] + plane3D[3][2])/4;
		
		return COM;
	}
	
	void rotateX(double degree, double[] COM)
	{		
		int i;	
		
		double[][] relV = new double[4][3];
		
		for(i=0;i<4;i++)
		{
			relV[i][0] = plane3D[i][0] - COM[0];
			relV[i][1] = plane3D[i][1] - COM[1];
			relV[i][2] = plane3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[4][3];
		
		for(i=0;i<4;i++)
		{
			relNewV[i][1] = relV[i][1]*Math.cos(radian) - relV[i][2]*Math.sin(radian);
			relNewV[i][2] = relV[i][1]*Math.sin(radian) + relV[i][2]*Math.cos(radian);
			relNewV[i][0] = relV[i][0];
		}
		
		for(i=0;i<4;i++)
		{
			plane3D[i][0] = COM[0] + relNewV[i][0];
			plane3D[i][1] = COM[1] + relNewV[i][1];
			plane3D[i][2] = COM[2] + relNewV[i][2];
		}				
	}
	
	void rotateY(double degree, double[] COM)
	{
		int i;		
		
		double[][] relV = new double[4][3];
		
		for(i=0;i<4;i++)
		{
			relV[i][0] = plane3D[i][0] - COM[0];
			relV[i][1] = plane3D[i][1] - COM[1];
			relV[i][2] = plane3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[4][3];
		
		for(i=0;i<4;i++)
		{
			relNewV[i][2] = relV[i][2]*Math.cos(radian) - relV[i][0]*Math.sin(radian);
			relNewV[i][0] = relV[i][2]*Math.sin(radian) + relV[i][0]*Math.cos(radian);
			relNewV[i][1] = relV[i][1];
		}
		
		for(i=0;i<4;i++)
		{
			plane3D[i][0] = COM[0] + relNewV[i][0];
			plane3D[i][1] = COM[1] + relNewV[i][1];
			plane3D[i][2] = COM[2] + relNewV[i][2];
		}				
	}
	
	void rotateZ(double degree, double[] COM)
	{
		int i;		
		
		double[][] relV = new double[4][3];
		
		for(i=0;i<4;i++)
		{
			relV[i][0] = plane3D[i][0] - COM[0];
			relV[i][1] = plane3D[i][1] - COM[1];
			relV[i][2] = plane3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[4][3];
		
		for(i=0;i<4;i++)
		{
			relNewV[i][0] = relV[i][0]*Math.cos(radian) - relV[i][1]*Math.sin(radian);
			relNewV[i][1] = relV[i][0]*Math.sin(radian) + relV[i][1]*Math.cos(radian);
			relNewV[i][2] = relV[i][2];
		}
		
		for(i=0;i<4;i++)
		{
			plane3D[i][0] = COM[0] + relNewV[i][0];
			plane3D[i][1] = COM[1] + relNewV[i][1];
			plane3D[i][2] = COM[2] + relNewV[i][2];
		}				
	}

}

class Box3D	//////////////////////////////////////////////////	BOX
{
	double[][] box3D=new double[8][3];
	int initPoint;
	
	Box3D(double[][] box3D)
	{
		this.box3D = box3D;
	}
	
	double[][] initBox3D(ar43b ar)
	{
		initPoint = ar.pointCount;
		
		return this.box3D;
	}
	
	double[][] getBox3D()
	{
		return this.box3D;
	}
	
	void seek(double[] move)
	{
		for(int i=0;i<8;i++)
		{
			box3D[i][0] += move[0];
			box3D[i][0] += move[0];
			box3D[i][0] += move[0];
			
			box3D[i][1] += move[1];
			box3D[i][1] += move[1];
			box3D[i][1] += move[1];
			
			box3D[i][2] += move[2];
			box3D[i][2] += move[2];
			box3D[i][2] += move[2];
		}
	}
	
	double[] getCorner(int n)
	{
		return box3D[n];
	}
	
	double[] getCOM()
	{
		double[] COM = new double[3];
		
		COM[0] = (box3D[0][0] + box3D[1][0] + box3D[2][0] + box3D[3][0] + box3D[4][0] + box3D[5][0] + box3D[6][0] + box3D[7][0])/8;
		COM[1] = (box3D[0][1] + box3D[1][1] + box3D[2][1] + box3D[3][1] + box3D[4][1] + box3D[5][1] + box3D[6][1] + box3D[7][1])/8;
		COM[2] = (box3D[0][2] + box3D[1][2] + box3D[2][2] + box3D[3][2] + box3D[4][2] + box3D[5][2] + box3D[6][2] + box3D[7][2])/8;		
		
		return COM;
	}
	
	void rotateX(double degree, double[] COM)
	{
		int i;		
		
		double[][] relV = new double[8][3];
		
		for(i=0;i<8;i++)
		{
			relV[i][0] = box3D[i][0] - COM[0];
			relV[i][1] = box3D[i][1] - COM[1];
			relV[i][2] = box3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[8][3];
		
		for(i=0;i<8;i++)
		{
			relNewV[i][1] = relV[i][1]*Math.cos(radian) - relV[i][2]*Math.sin(radian);
			relNewV[i][2] = relV[i][1]*Math.sin(radian) + relV[i][2]*Math.cos(radian);
			relNewV[i][0] = relV[i][0];
		}
		
		for(i=0;i<8;i++)
		{
			box3D[i][0] = COM[0] + relNewV[i][0];
			box3D[i][1] = COM[1] + relNewV[i][1];
			box3D[i][2] = COM[2] + relNewV[i][2];
		}				
	}
	
	void rotateY(double degree, double[] COM)
	{
		int i;		
		
		double[][] relV = new double[8][3];
		
		for(i=0;i<8;i++)
		{
			relV[i][0] = box3D[i][0] - COM[0];
			relV[i][1] = box3D[i][1] - COM[1];
			relV[i][2] = box3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[8][3];
		
		for(i=0;i<8;i++)
		{
			relNewV[i][2] = relV[i][2]*Math.cos(radian) - relV[i][0]*Math.sin(radian);
			relNewV[i][0] = relV[i][2]*Math.sin(radian) + relV[i][0]*Math.cos(radian);
			relNewV[i][1] = relV[i][1];
		}
		
		for(i=0;i<8;i++)
		{
			box3D[i][0] = COM[0] + relNewV[i][0];
			box3D[i][1] = COM[1] + relNewV[i][1];
			box3D[i][2] = COM[2] + relNewV[i][2];
		}				
	}
	
	void rotateZ(double degree, double[] COM)
	{		
		int i;		
			
		double[][] relV = new double[8][3];
		
		for(i=0;i<8;i++)
		{
			relV[i][0] = box3D[i][0] - COM[0];
			relV[i][1] = box3D[i][1] - COM[1];
			relV[i][2] = box3D[i][2] - COM[2];
		}
		
		double radian = Math.toRadians(degree);
		
		double[][] relNewV = new double[8][3];
		
		for(i=0;i<8;i++)
		{
			relNewV[i][0] = relV[i][0]*Math.cos(radian) - relV[i][1]*Math.sin(radian);
			relNewV[i][1] = relV[i][0]*Math.sin(radian) + relV[i][1]*Math.cos(radian);
			relNewV[i][2] = relV[i][2];
		}
		
		for(i=0;i<8;i++)
		{
			box3D[i][0] = COM[0] + relNewV[i][0];
			box3D[i][1] = COM[1] + relNewV[i][1];
			box3D[i][2] = COM[2] + relNewV[i][2];
		}
				
	}
}

class Helicopter3D	//////////////////////////////////////////////////	HELICOPTER
{
	Triangle3D[] headTriangle, bladeLift, bladeRotate;
	Box3D body, tail;
	double inclinationX=0, inclinationY=0, inclinationZ=0;
	double inclinationSpecX=5, inclinationSpecZ=5;
	double bladeRotateMagni = 5;
	
	int initPoint;
	
	Helicopter3D(Box3D body,Triangle3D[] headTriangle, Triangle3D[] bladeLift, Box3D tail, Triangle3D[] bladeRotate)
	{
		this.body=body;
		this.headTriangle=headTriangle;
		this.bladeLift=bladeLift;
		this.tail=tail;
		this.bladeRotate=bladeRotate;		
	}
	
	void initHelicopter3D(ar43b ar)
	{
		this.initPoint = ar.pointCount;
		
		ar.addBox(body.initBox3D(ar));
		
		ar.addTriangle(headTriangle[0].initTriangle3D(ar));
		ar.addTriangle(headTriangle[1].initTriangle3D(ar));
		ar.addTriangle(headTriangle[2].initTriangle3D(ar));
		ar.addTriangle(headTriangle[3].initTriangle3D(ar));
		
		ar.addTriangle(bladeLift[0].initTriangle3D(ar));
		ar.addTriangle(bladeLift[1].initTriangle3D(ar));
		ar.addTriangle(bladeLift[2].initTriangle3D(ar));
		ar.addTriangle(bladeLift[3].initTriangle3D(ar));
		
		ar.addBox(tail.initBox3D(ar));
		
		ar.addTriangle(bladeRotate[0].initTriangle3D(ar));
		ar.addTriangle(bladeRotate[1].initTriangle3D(ar));
		ar.addTriangle(bladeRotate[2].initTriangle3D(ar));
		ar.addTriangle(bladeRotate[3].initTriangle3D(ar));		
	}
	
	void seek(double[] move)
	{
		int i;
		
		body.seek(move);
		
		for(i=0;i<4;i++)	// 4 HeadTriangle		
			headTriangle[i].seek(move);
		
		
		for(i=0;i<4;i++)	// 4 bladeLift		
			bladeLift[i].seek(move);
		
		
		tail.seek(move);
		
		for(i=0;i<4;i++)	// 4 bladeRotate		
			bladeRotate[i].seek(move);
		
	}
	
	void editHelicopter(ar43b ar)
	{
		int i;
		
		ar.editBox(body.getBox3D(), body.initPoint);
		
		for(i=0;i<4;i++)
			ar.editTriangle(headTriangle[i].getTriangle3D(), headTriangle[i].initPoint);
		
		for(i=0;i<4;i++)	
			ar.editTriangle(bladeLift[i].getTriangle3D(), bladeLift[i].initPoint);
		
		ar.editBox(tail.getBox3D(), tail.initPoint);
		
		for(i=0;i<4;i++)
			ar.editTriangle(bladeRotate[i].getTriangle3D(), bladeRotate[i].initPoint);
	
	}
	
	void rotateX(double degree, double[] COM)
	{
		int i;
		
		inclinationX += degree;
		
		if(Math.abs(inclinationX) == 360)
			inclinationX = 0;
	//	inclinationSpecX += degree;
		
		body.rotateX(degree, COM);
		
		for(i=0;i<4;i++)
			headTriangle[i].rotateX(degree, COM);
		
		for(i=0;i<4;i++)
			bladeLift[i].rotateX(degree, COM);
		
		tail.rotateX(degree, COM);
		
		for(i=0;i<4;i++)
			bladeRotate[i].rotateX(degree, COM);
	}
	
	void rotateY(double degree, double[] COM)
	{
		int i;
		
		inclinationY += degree;
		
		if(Math.abs(inclinationY) == 360)
			inclinationY = 0;
		
		body.rotateY(degree, COM);
		
		for(i=0;i<4;i++)
			headTriangle[i].rotateY(degree, COM);
		
		for(i=0;i<4;i++)
			bladeLift[i].rotateY(degree, COM);
		
		tail.rotateY(degree, COM);
		
		for(i=0;i<4;i++)
			bladeRotate[i].rotateY(degree, COM);
	}
	
	void rotateZ(double degree, double[] COM)
	{
		int i;
		
		inclinationZ += degree;
		
		if(Math.abs(inclinationZ) == 360)
			inclinationZ = 0;
		//inclinationSpecZ += degree;
		
		body.rotateZ(degree, COM);
		
		for(i=0;i<4;i++)
			headTriangle[i].rotateZ(degree, COM);
		
		for(i=0;i<4;i++)
			bladeLift[i].rotateZ(degree, COM);
		
		tail.rotateZ(degree, COM);
		
		for(i=0;i<4;i++)
			bladeRotate[i].rotateZ(degree, COM);
	}
	
	void circulateLift()	//	Z
	{
		for(int i=0;i<4;i++)
		{
			bladeLift[i].rotateX(-inclinationX, bladeLift[0].getCorner(0));
			bladeLift[i].rotateY(-inclinationY, bladeLift[0].getCorner(0));
			bladeLift[i].rotateZ(-inclinationZ, bladeLift[0].getCorner(0));
			
			bladeLift[i].rotateZ(inclinationSpecZ, bladeLift[0].getCorner(0));
			
			bladeLift[i].rotateZ(inclinationZ, bladeLift[0].getCorner(0));
			bladeLift[i].rotateY(inclinationY, bladeLift[0].getCorner(0));
			bladeLift[i].rotateX(inclinationX, bladeLift[0].getCorner(0));	
		}		
	}
	
	void circulateRotate()	//	X
	{
		for(int i=0;i<4;i++)
		{
			bladeRotate[i].rotateX(-inclinationX, bladeRotate[0].getCorner(0));
			bladeRotate[i].rotateY(-inclinationY, bladeRotate[0].getCorner(0));
			bladeRotate[i].rotateZ(-inclinationZ, bladeRotate[0].getCorner(0));
			
			bladeRotate[i].rotateX(inclinationSpecX, bladeRotate[0].getCorner(0));
			
			bladeRotate[i].rotateZ(inclinationZ, bladeRotate[0].getCorner(0));
			bladeRotate[i].rotateY(inclinationY, bladeRotate[0].getCorner(0));
			bladeRotate[i].rotateX(inclinationX, bladeRotate[0].getCorner(0));
		}
	}
	
	void circulateLiftRecover()	//	Z
	{
		
		for(int i=0;i<4;i++)
		{
			
			bladeLift[i].rotateX((inclinationSpecZ*inclinationX)/(360), bladeLift[0].getCorner(0));
			bladeLift[i].rotateY(inclinationY, bladeLift[0].getCorner(0));
			bladeLift[i].rotateZ(inclinationSpecZ, bladeLift[0].getCorner(0));
		}
	}
	
	void circulateRotateRecover()	//	X
	{
		for(int i=0;i<4;i++)
		{
			bladeRotate[i].rotateX(inclinationSpecX, bladeRotate[0].getCorner(0));
			bladeRotate[i].rotateY(-inclinationY, bladeRotate[0].getCorner(0));
			bladeRotate[i].rotateZ(-inclinationZ, bladeRotate[0].getCorner(0));
		}
	}
}

class Sphere
{
	Plane3D[][] shell;
	double radii, hInc, vInc;
	double[] center;
	int initPoint,hDiv, vDiv;
	
	Sphere(double radii, double[] center, int verticalDiv, int horizontalDiv)
	{
		this.radii = radii;
		this.center = center;
		this.hDiv = horizontalDiv;
		this.vDiv = verticalDiv;
		shell = new Plane3D[vDiv][hDiv];
	}
	
	void initSphere(ar43b ar)
	{
		this.initPoint = ar.pointCount;
		
		hInc = 360 / (double)hDiv;
		vInc = 180 / (double)vDiv;	
		
		for(double phi=0, i=0; i < vDiv; phi = phi + vInc, i++ )
		{
			double upperPhi = phi;
			double lowerPhi = phi - vInc;
			
			for(double theta=0, j=0; j < hDiv; theta = theta + hInc, j++)
			{
				double startTheta = theta;
				double endTheta = theta + hInc;
				
				double[] point1 = new double[3];
				point1[0] = center[0] + radii*Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
				point1[1] = center[1] + radii*Math.sin(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
				point1[2] = center[2] + radii*Math.cos(Math.toRadians(phi));
				
				double[] point2 = new double[3];
				point2[0] = center[0] + radii*Math.cos(Math.toRadians(theta + hInc))*Math.sin(Math.toRadians(phi));
				point2[1] = center[1] + radii*Math.sin(Math.toRadians(theta + hInc))*Math.sin(Math.toRadians(phi));
				point2[2] = center[2] + radii*Math.cos(Math.toRadians(phi));
				
				double[] point3 = new double[3];
				point3[0] = center[0] + radii*Math.cos(Math.toRadians(theta + hInc))*Math.sin(Math.toRadians(phi + vInc));
				point3[1] = center[1] + radii*Math.sin(Math.toRadians(theta + hInc))*Math.sin(Math.toRadians(phi + vInc));
				point3[2] = center[2] + radii*Math.cos(Math.toRadians(phi + vInc));
				
				double[] point4 = new double[3];
				point4[0] = center[0] + radii*Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(phi + vInc));
				point4[1] = center[1] + radii*Math.sin(Math.toRadians(theta))*Math.sin(Math.toRadians(phi + vInc));
				point4[2] = center[2] + radii*Math.cos(Math.toRadians(phi + vInc));
				
				double[][] planeElement = { point1, point2, point3, point4 };
				
				shell[(int) i][(int) j] = new Plane3D(planeElement);
				
				ar.addPlane(shell[(int) i][(int) j].initPlane3D(ar));
			}
		}
	}	
	
	double[] getVector(double radii, double[] center, double phi, double theta)
	{
		double[] point = new double[3];
		
		point[0] = center[0] + radii*Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
		point[1] = center[1] + radii*Math.sin(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
		point[2] = center[2] + radii*Math.cos(Math.toRadians(phi));
		
		return point;
	}
	
	double[] mulScaler(double scale, double[] vector)
	{
		double[] scaledV = new double[3];
		
		scaledV[0] = scale*vector[0];
		scaledV[1] = scale*vector[1];
		scaledV[2] = scale*vector[2];
		
		return scaledV;
	}
	
	void seek(double[] move)
	{
		int i,j;
		
		center[0] += 3*move[0];
		center[1] += 3*move[1];
		center[2] += 3*move[2];		//	HOW !???
		
		for(i=0; i < vDiv; i++)		
			for(j=0; j < hDiv; j++)
				shell[i][j].seek(move);
	}
	
	void editSphere(ar43b ar)
	{
		int i,j;
		
		for(i=0; i < vDiv; i++)		
			for(j=0; j < hDiv; j++)
				ar.editPlane(shell[i][j].getPlane3D(), shell[i][j].initPoint);
	}
	
	void rotateX(double degree, double[] COM)
	{
		int i,j;
		
		for(i=0; i < vDiv; i++)		
			for(j=0; j < hDiv; j++)
				shell[i][j].rotateX(degree, COM);
	}
	
	void rotateY(double degree, double[] COM)
	{
		int i,j;
		
		for(i=0; i < vDiv; i++)		
			for(j=0; j < hDiv; j++)
				shell[i][j].rotateY(degree, COM);
	}
	
	void rotateZ(double degree, double[] COM)
	{
		int i,j;
		
		for(i=0; i < vDiv; i++)		
			for(j=0; j < hDiv; j++)
				shell[i][j].rotateZ(degree, COM);
	}
}

class PartialSphere
{
	Plane3D[][] shell;
	double radii, hInc, vInc, phiStart, phiEnd, thetaStart, thetaEnd, currPhi, currTheta;
	double[] center, normal={0,0,1};
	int initPoint,hDiv, vDiv;
	
	PartialSphere(double radii, double[] center, int verticalDiv, int horizontalDiv,double phiStart, double phiEnd, double thetaStart, double thetaEnd)
	{
		this.radii = radii;
		this.center = center;
		this.hDiv = horizontalDiv;
		this.vDiv = verticalDiv;
		shell = new Plane3D[vDiv][hDiv];
		
		this.phiStart = phiStart;
		this.phiEnd = phiEnd;
		this.thetaStart = thetaStart; 
		this.thetaEnd = thetaEnd;
		
		currPhi = 0;
		currTheta = 0;
	}
	
	void initPartialSphere(ar43b ar)
	{
		this.initPoint = ar.pointCount;
		
		hInc = 360 / (double)hDiv;
		vInc = 180 / (double)vDiv;	
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )
		{
			double upperPhi = phi;
			double lowerPhi = phi - vInc;
			
			for(double theta=thetaStart, j=0; theta < thetaEnd; theta = theta + hInc, j++)
			{
				double startTheta = theta;
				double endTheta = theta + hInc;
				
				double[] point1 = new double[3];
				point1[0] = center[0] + radii*Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
				point1[1] = center[1] + radii*Math.sin(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
				point1[2] = center[2] + radii*Math.cos(Math.toRadians(phi));
				
				double[] point2 = new double[3];
				point2[0] = center[0] + radii*Math.cos(Math.toRadians(theta + hInc))*Math.sin(Math.toRadians(phi));
				point2[1] = center[1] + radii*Math.sin(Math.toRadians(theta + hInc))*Math.sin(Math.toRadians(phi));
				point2[2] = center[2] + radii*Math.cos(Math.toRadians(phi));
				
				double[] point3 = new double[3];
				point3[0] = center[0] + radii*Math.cos(Math.toRadians(theta + hInc))*Math.sin(Math.toRadians(phi + vInc));
				point3[1] = center[1] + radii*Math.sin(Math.toRadians(theta + hInc))*Math.sin(Math.toRadians(phi + vInc));
				point3[2] = center[2] + radii*Math.cos(Math.toRadians(phi + vInc));
				
				double[] point4 = new double[3];
				point4[0] = center[0] + radii*Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(phi + vInc));
				point4[1] = center[1] + radii*Math.sin(Math.toRadians(theta))*Math.sin(Math.toRadians(phi + vInc));
				point4[2] = center[2] + radii*Math.cos(Math.toRadians(phi + vInc));
				
				double[][] planeElement = { point1, point2, point3, point4 };
				
				shell[(int) i][(int) j] = new Plane3D(planeElement);
				
				ar.addPlane(shell[(int) i][(int) j].initPlane3D(ar));
			}
		}
	}	
	
	double[] getVector(double radii, double[] center, double phi, double theta)
	{
		double[] point = new double[3];
		
		point[0] = center[0] + radii*Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
		point[1] = center[1] + radii*Math.sin(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
		point[2] = center[2] + radii*Math.cos(Math.toRadians(phi));
		
		return point;
	}
	
	double[] mulScaler(double scale, double[] vector)
	{
		double[] scaledV = new double[3];
		
		scaledV[0] = scale*vector[0];
		scaledV[1] = scale*vector[1];
		scaledV[2] = scale*vector[2];
		
		return scaledV;
	}
	
	void seek(double[] move)
	{		
		center[0] += 3*move[0];
		center[1] += 3*move[1];
		center[2] += 3*move[2];		//	HOW !???
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			for(double theta=thetaStart, j=0; theta < thetaEnd; theta = theta + hInc, j++)
				shell[(int) i][(int) j].seek(move);
	}
	
	void editPartialSphere(ar43b ar)
	{
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			for(double theta=thetaStart, j=0; theta < thetaEnd; theta = theta + hInc, j++)
				ar.editPlane(shell[(int) i][(int) j].getPlane3D(), shell[(int) i][(int) j].initPoint);
	}
	
	void rotateX(double degree, double[] COM)
	{
		currPhi -= degree;
		
		if( currPhi > 180 )
			currPhi -= 180;
		else if( currPhi < 0 )
			currPhi = -currPhi;
		
		normal = getVector( 1, new double[]{0,0,0}, currPhi, currTheta );
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			for(double theta=thetaStart, j=0; theta < thetaEnd; theta = theta + hInc, j++)
				shell[(int) i][(int) j].rotateX(degree, COM);
		
		////#//^System.out.println("phi="+currPhi+" theta="+currTheta);
	}
	
	void rotateY(double degree, double[] COM)
	{
		currPhi -= degree;
		
		if( currPhi > 180 )
			currPhi -= 180;
		else if( currPhi < 0 )
			currPhi = -currPhi;
		
		normal = getVector( 1, new double[]{0,0,0}, currPhi, currTheta );
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			for(double theta=thetaStart, j=0; theta < thetaEnd; theta = theta + hInc, j++)
				shell[(int) i][(int) j].rotateY(degree, COM);
		
		////#//^System.out.println("phi="+currPhi+" theta="+currTheta);
	}
	
	void rotateZ(double degree, double[] COM)
	{
		currTheta -= degree;
		
		if( currTheta > 360 )
			currPhi -= 360;
		else if( currTheta < 0 )
			currTheta = 360 + currTheta;
		
		normal = getVector( 1, new double[]{0,0,0}, currPhi, currTheta );
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			for(double theta=thetaStart, j=0; theta < thetaEnd; theta = theta + hInc, j++)
				shell[(int) i][(int) j].rotateZ(degree, COM);
		
		////#//^System.out.println("phi="+currPhi+" theta="+currTheta);
	}
}

class SliceSphere
{
	Plane3D[][] shell;
	double radii, hInc, vInc, phiStart, phiEnd, thetaStart, thetaEnd, currPhi, currTheta;
	double[] center, normal={0,0,1};
	int initPoint,hDiv, vDiv;
	PartialSphere[] psp = new PartialSphere[10000];
	
	SliceSphere(double radii, double[] center, int verticalDiv, int horizontalDiv,double phiStart, double phiEnd, double thetaStart, double thetaEnd)
	{
		this.radii = radii;
		this.center = center;
		this.hDiv = horizontalDiv;
		this.vDiv = verticalDiv;
		shell = new Plane3D[vDiv][hDiv];
		
		this.phiStart = phiStart;
		this.phiEnd = phiEnd;
		this.thetaStart = thetaStart; 
		this.thetaEnd = thetaEnd;
		
		currPhi = 0;
		currTheta = 0;
	}
	
	void initSliceSphere(ar43b ar)
	{
		this.initPoint = ar.pointCount;
		
		hInc = 360 / (double)hDiv;
		vInc = 180 / (double)vDiv;	
		
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )
		{			
			psp[(int) i] = new PartialSphere(radii, center, vDiv, hDiv, phi, phi + vInc, thetaStart, thetaEnd);

			psp[(int) i].initPartialSphere(ar);			
		}
	}	
	
	double[] getVector(double radii, double[] center, double phi, double theta)
	{
		double[] point = new double[3];
		
		point[0] = center[0] + radii*Math.cos(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
		point[1] = center[1] + radii*Math.sin(Math.toRadians(theta))*Math.sin(Math.toRadians(phi));
		point[2] = center[2] + radii*Math.cos(Math.toRadians(phi));
		
		return point;
	}
	
	double[] mulScaler(double scale, double[] vector)
	{
		double[] scaledV = new double[3];
		
		scaledV[0] = scale*vector[0];
		scaledV[1] = scale*vector[1];
		scaledV[2] = scale*vector[2];
		
		return scaledV;
	}
	
	void seek(double[] move)
	{		
		center[0] += 3*move[0];
		center[1] += 3*move[1];
		center[2] += 3*move[2];		//	HOW !???
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			psp[(int) i].seek(move);			
		
	}
	
	void editSliceSphere(ar43b ar)
	{
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			psp[(int) i].editPartialSphere(ar);
	}
	
	void rotateX(double degree, double[] COM)
	{
		currPhi -= degree;
		
		if( currPhi > 180 )
			currPhi -= 180;
		else if( currPhi < 0 )
			currPhi = -currPhi;
		
		normal = getVector( 1, new double[]{0,0,0}, currPhi, currTheta );
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			psp[(int) i].rotateX(degree, COM);
	}
	
	void rotateY(double degree, double[] COM)
	{
		currPhi -= degree;
		
		if( currPhi > 180 )
			currPhi -= 180;
		else if( currPhi < 0 )
			currPhi = -currPhi;
		
		normal = getVector( 1, new double[]{0,0,0}, currPhi, currTheta );
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			psp[(int) i].rotateY(degree, COM);
	}
	
	void rotateZ(double degree, double[] COM)
	{
		currTheta -= degree;
		
		if( currTheta > 360 )
			currPhi -= 360;
		else if( currTheta < 0 )
			currTheta = 360 + currTheta;
		
		normal = getVector( 1, new double[]{0,0,0}, currPhi, currTheta );
		
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )	
			psp[(int) i].rotateZ(degree, COM);
	}
	
	void expand(double magn)
	{
		for(double phi=phiStart, i=0; phi < phiEnd; phi = phi + vInc, i++ )			
			if( phi < 90 )			
				psp[(int) i].seek(mulScaler(magn*(90-phi), new double[]{0,-1,0}));
			
			else			
				psp[(int) i].seek(mulScaler(-magn*(phi-90), new double[]{0,-1,0}));
	}
}

class Toroid
{
	Plane3D[][] shell;
	double[] center;
	double[][][] shellPoint;
	double mainRadii, subRadii, mainInc, subInc;
	int mainDiv, subDiv;
	
	Toroid(double[] center, double mainRadii, double subRadii, int mainDiv, int subDiv)
	{
		this.center = center;
		this.mainRadii = mainRadii;
		this.subRadii = subRadii;
		this.mainDiv = mainDiv;
		this.subDiv = subDiv;
		
		shellPoint = new double[subDiv][mainDiv][3];
		shell = new Plane3D[subDiv][mainDiv];
		mainInc = 360/mainDiv;
		subInc = 360/subDiv;
	}
	
	void initToroid(ar43b ar)
	{
		double innerA, outerA;
		int in, out;
		
		for(innerA = 0,in=0; in < subDiv; innerA += subInc, in++)
		{			
			for(outerA = 0,out=0; out < mainDiv; outerA += mainInc, out++)//c=mainRadii a=subRadii u=outerA v=innerA
			{
				shellPoint[in][out][0] = ( mainRadii + subRadii*Math.cos(Math.toRadians(innerA)) )*Math.cos(Math.toRadians(outerA));
				shellPoint[in][out][1] = ( mainRadii + subRadii*Math.cos(Math.toRadians(innerA)) )*Math.sin(Math.toRadians(outerA));
				shellPoint[in][out][2] = subRadii*Math.sin(Math.toRadians(innerA));
			}
		}
		
		double[][] p = new double[4][3];
		
		for(in=0; in < subDiv - 1;in++)	//subDiv - 1 
		{
			p[0] = shellPoint[in][0];
			p[1] = shellPoint[in + 1][0];
					
			double[][] temp1 = new double[2][3];
			
			for(out=0; out < mainDiv -1 ; out++)	//mainDiv -1
			{				
				p[2] = shellPoint[in + 1][out + 1];
				p[3] = shellPoint[in][out + 1];
				
				shell[in][out] = new Plane3D(p);
				ar.addPlane(shell[in][out].initPlane3D(ar));
				
				p[0] = p[3];
				p[1] = p[2];
			}			
			
			shell[in][mainDiv - 1] = new Plane3D (new double[][] { shellPoint[in][mainDiv - 1], shellPoint[in + 1][mainDiv - 1], 
										               			   shellPoint[in + 1][0], shellPoint[in][0]});
			ar.addPlane(shell[in][out].initPlane3D(ar));
		}
		
		in = subDiv - 1;
		
		p[0] = shellPoint[in][0];
		p[1] = shellPoint[0][0];
		
		for(out=0; out < mainDiv -1 ; out++)
		{
			p[2] = shellPoint[0][out + 1];
			p[3] = shellPoint[in][out + 1];
			
			shell[in][out] = new Plane3D(p);
			ar.addPlane(shell[in][out].initPlane3D(ar));
			
			p[0] = p[3];
			p[1] = p[2];
		}
		
		shell[in][mainDiv - 1] = new Plane3D (new double[][] { shellPoint[in][mainDiv - 1], shellPoint[0][mainDiv - 1], 
															   shellPoint[0][0], shellPoint[in][0]});
		ar.addPlane(shell[in][out].initPlane3D(ar));	
	}
	
	void seek(double[] move)
	{
		int i,j;
		
		center[0] += move[0];
		center[1] += move[1];
		center[2] += move[2];		//	HOW !???
		
		for(i=0; i < subDiv; i++)		
			for(j=0; j < mainDiv; j++)
				shell[i][j].seek(move);
	}
	
	void editToroid(ar43b ar)
	{
		int i,j;
		
		for(i=0; i < subDiv; i++)		
			for(j=0; j < mainDiv; j++)
				ar.editPlane(shell[i][j].getPlane3D(), shell[i][j].initPoint);
	}
	
	void rotateX(double degree, double[] COM)
	{
		int i,j;
		
		for(i=0; i < subDiv; i++)		
			for(j=0; j < mainDiv; j++)
				shell[i][j].rotateX(degree, COM);
	}
	
	void rotateY(double degree, double[] COM)
	{
		int i,j;
		
		for(i=0; i < subDiv; i++)		
			for(j=0; j < mainDiv; j++)
				shell[i][j].rotateY(degree, COM);
	}
	
	void rotateZ(double degree, double[] COM)
	{
		int i,j;
		
		for(i=0; i < subDiv; i++)		
			for(j=0; j < mainDiv; j++)
				shell[i][j].rotateZ(degree, COM);
	}
}