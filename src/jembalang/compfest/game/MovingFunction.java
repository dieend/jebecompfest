package jembalang.compfest.game;


import java.util.Random;

import android.graphics.RectF;
import android.util.Log;

public class MovingFunction {
	
	public static final int STAY = 0;//x1,y1,boolstay,timestay
	public static final int LINE = 1;//x1,y1,x2,y2,speed
	public static final int ZIGZAG = 2;//x1,y1,a,b,speed
	public static final int ARC = 3;//x1,y1,x2,y2,o1,o2,t1,dir(1/-1),speed
	public static final int FASTARC = 4;//x1,y1,x0,y0,xspeed,yspeed,time
	
	
	public static int gcd(int a,int b){
		if (a==0){
		  a=b;
		}
		if (b==0){
			b=a;
		}
		
		if (a<b){
			int temp=a;
			a=b;
			b=temp;
		}
		while (a%b!=0){
			int temp=b;
			b=(a%b);
			a=temp;
		}
		return b;
	}
	
	private float[]is = new float[3000];
	private float length=0;
	private float xcurr;
	private float ycurr;
	
	public void setStatus(int status){
		is[0]=status;
	}
	
	Bug bug;
	
	
	////////
	public static final int bound=10;
	
	private static Random random = new Random(System.currentTimeMillis());
	
    private static int getRandom()
    {
        return Math.abs(random.nextInt()%bound);
    }
    
	public static int getEasy(){
		return getRandom();
	}
	
	public static int getMedium(){
		return getRandom()+bound;
	}
	
	public static int getHard(){
		return getRandom()+2*bound;
	}
	///////
	
	//is[0]=status,is[1]=length param,is[2]=start param
	public MovingFunction(Bug bug,int moved,int viewWidth,int viewHeight){
		this.bug=bug;
		idx=2;
		counterxcount=0;
		
		for(int i=is.length;i<this.is.length;++i){
			this.is[i]=0;
		}
		
		for(int i=0;i<xcount.length;++i){
			xcount[i]=0;
		}
		if (moved%bound==0){//STRIKE-1
			bug.setPosition(30, 30);
			is[0]=0;
			is[1]=1;
			is[2]=MovingFunction.LINE;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=bug.getX();
			is[6]=viewHeight+30;
			
			//speed
			if (moved==0){
				is[7]=2;//speed
			}else if (moved==0+bound){
				is[7]=4;
			}else if (moved==0+2*bound){
				is[7]=6;
			}
			length=8;
			
		}else if (moved%bound==1){//STRIKE-2
			bug.setPosition(viewWidth/2-15, 30);
			is[0]=0;
			is[1]=1;
			is[2]=MovingFunction.LINE;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=bug.getX();
			is[6]=viewHeight+30;
			
			//speed
			if (moved==1){
				is[7]=2;//speed
			}else if (moved==1+bound){
				is[7]=4;
			}else if (moved==1+2*bound){
				is[7]=6;
			}
			length=8;
			
		}else if (moved%bound==2){//STRIKE-3
			bug.setPosition(viewWidth-30, 30);
			is[0]=0;
			is[1]=1;
			is[2]=MovingFunction.LINE;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=bug.getX();
			is[6]=viewHeight+30;
			
			//speed
			if (moved==2){
				is[7]=2;//speed
			}else if (moved==2+bound){
				is[7]=4;
			}else if (moved==2+2*bound){
				is[7]=6;
			}
			length=8;
			
		}else if (moved%bound==3){//STRIKE-4
			int speedmoved3=1;
			if (moved==3){
				speedmoved3=1;
			}else if (moved==3+bound){
				speedmoved3=2;
			}else if (moved==3+2*bound){
				speedmoved3=3;
			}
			int tempheight=viewHeight/4;
			
			bug.setPosition(viewWidth/3, 30);
			is[0]=0;
			is[1]=1;
			is[2]=MovingFunction.LINE;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=2*viewWidth/3;
			is[6]=tempheight+30;
			
			is[7]=speedmoved3;//speed
			
			
			is[8]=MovingFunction.LINE;
			is[9]=0;
			is[10]=0;
			is[11]=viewWidth/3;
			is[12]=2*tempheight+30;
			
			is[13]=speedmoved3;//speed
			
			
			is[14]=MovingFunction.LINE;
			is[15]=0;
			is[16]=0;
			is[17]=2*viewWidth/3;
			is[18]=3*tempheight+30;
			
			is[19]=speedmoved3;//speed
			
			is[20]=MovingFunction.LINE;
			is[21]=0;
			is[22]=0;
			is[23]=viewWidth/3;
			is[24]=4*tempheight+30;
			
			is[25]=speedmoved3;//speed
			
			length=26;
			
//			
//			is[26]=MovingFunction.LINE;
//			is[27]=0;
//			is[28]=0;
//			is[29]=viewWidth-30;
//			is[30]=5*tempheight+30;
//			
//			is[31]=speedmoved3;//speed
//			
//			is[32]=MovingFunction.LINE;
//			is[33]=0;
//			is[34]=0;
//			is[35]=30;
//			is[36]=6*tempheight+30;
//			
//			is[37]=speedmoved3;//speed
//			
//			is[38]=MovingFunction.LINE;
//			is[39]=0;
//			is[40]=0;
//			is[41]=viewWidth-30;
//			is[42]=7*tempheight+30;
//			
//			is[43]=speedmoved3;//speed
//			
//			is[44]=MovingFunction.LINE;
//			is[45]=0;
//			is[46]=0;
//			is[47]=30;
//			is[48]=8*tempheight+30;
//			
//			is[49]=speedmoved3;//speed
//			
//			is[50]=MovingFunction.LINE;
//			is[51]=0;
//			is[52]=0;
//			is[53]=viewWidth-30;
//			is[54]=9*tempheight+30;
//			
//			is[55]=speedmoved3;//speed
			
			
		}else if (moved%bound==4){//STRIKE-5
			int speedmoved3=1;
			if (moved==4){
				speedmoved3=1;
			}else if (moved==4+bound){
				speedmoved3=2;
			}else if (moved==4+2*bound){
				speedmoved3=3;
			}
			int tempheight=viewHeight/4;
			
			bug.setPosition(2*viewWidth/3, 30);
			is[0]=0;
			is[1]=1;
			is[2]=MovingFunction.LINE;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=viewWidth/3;
			is[6]=tempheight+30;
			
			is[7]=speedmoved3;//speed
			
			
			is[8]=MovingFunction.LINE;
			is[9]=0;
			is[10]=0;
			is[11]=2*viewWidth/3;
			is[12]=2*tempheight+30;
			
			is[13]=speedmoved3;//speed
			
			
			is[14]=MovingFunction.LINE;
			is[15]=0;
			is[16]=0;
			is[17]=viewWidth/3;
			is[18]=3*tempheight+30;
			
			is[19]=speedmoved3;//speed
			
			is[20]=MovingFunction.LINE;
			is[21]=0;
			is[22]=0;
			is[23]=2*viewWidth/3;
			is[24]=4*tempheight+30;
			
			is[25]=speedmoved3;//speed
			
			length=26;
			
		}else if (moved%bound==5){//STRIKE-5
			int speedmoved3=1;
			if (moved==5){
				speedmoved3=1;
			}else if (moved==5+bound){
				speedmoved3=2;
			}else if (moved==5+2*bound){
				speedmoved3=3;
			}
			
			
			bug.setPosition(viewWidth/4,30);
			is[0]=0;
			is[1]=1;
			
			is[2]=MovingFunction.LINE;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=viewWidth/4;
			is[6]=viewHeight/3;
			       
			is[7]=speedmoved3;//speed
			
			is[8]=MovingFunction.LINE;
			is[9]=0;
			is[10]=0;
			is[11]=viewWidth/2;
			is[12]=30;
			
			is[13]=speedmoved3;//speed
			
			is[14]=MovingFunction.LINE;
			is[15]=0;
			is[16]=0;
			is[17]=viewWidth/2;
			is[18]=viewHeight/3;
			
			is[19]=speedmoved3;
			
			is[20]=MovingFunction.LINE;
			is[21]=0;
			is[22]=0;
			is[23]=3*viewWidth/4;
			is[24]=30;
			
			is[25]=speedmoved3;
			
			is[26]=MovingFunction.LINE;
			is[27]=0;
			is[28]=0;
			is[29]=3*viewWidth/4;
			is[30]=viewHeight+60;
			
			is[31]=speedmoved3*2;
			
			length=32;
		}else if (moved%bound==6){//STRIKE-5
			int speedmoved3=1;
			if (moved==6){
				speedmoved3=1;
			}else if (moved==6+bound){
				speedmoved3=2;
			}else if (moved==6+2*bound){
				speedmoved3=3;
			}
			
			bug.setPosition(3*viewWidth/4,30);
			is[0]=0;
			is[1]=1;
			
			is[2]=MovingFunction.LINE;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=3*viewWidth/4;
			is[6]=viewHeight/3;
			
			is[7]=speedmoved3;//speed
			
			is[8]=MovingFunction.LINE;
			is[9]=0;
			is[10]=0;
			is[11]=viewWidth/2;
			is[12]=30;
			
			is[13]=speedmoved3;//speed
			
			is[14]=MovingFunction.LINE;
			is[15]=0;
			is[16]=0;
			is[17]=viewWidth/2;
			is[18]=viewHeight/3;
			
			is[19]=speedmoved3;
			
			is[20]=MovingFunction.LINE;
			is[21]=0;
			is[22]=0;
			is[23]=viewWidth/4;
			is[24]=30;
			
			is[25]=speedmoved3;
			
			is[26]=MovingFunction.LINE;
			is[27]=0;
			is[28]=0;
			is[29]=viewWidth/4;
			is[30]=viewHeight+60;
			
			is[31]=speedmoved3*2;
			
			length=32;
			
		}else if (moved%bound==7){//STRIKE-7
			int speedmoved3=1;
			if (moved==7){
				speedmoved3=1;
			}else if (moved==7+bound){
				speedmoved3=2;
			}else if (moved==7+2*bound){
				speedmoved3=3;
			}
//			public static final int ARC = 3;//x1,y1,x2,y2,t1,dir(1/-1),speedz
			
			bug.setPosition(viewWidth/2-15, 100);
			
			is[0]=0;
			is[1]=1;
			
			is[2]=MovingFunction.ARC;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=viewWidth-30;
			is[6]=100;
			is[7]=viewWidth/2-30;
			is[8]=1;
			is[9]=2;
			
			is[10]=MovingFunction.ARC;
			is[11]=bug.getX();
			is[12]=bug.getY();
			is[13]=0;
			is[14]=100;
			is[15]=viewWidth/2-30;
			is[16]=-1;
			is[17]=2;
			
			length=18;
		}else if (moved%bound==8){//STRIKE-8
			int speedmoved3=1;
			if (moved==8){
				speedmoved3=1;
			}else if (moved==8+bound){
				speedmoved3=2;
			}else if (moved==8+2*bound){
				speedmoved3=3;
			}
			
			bug.setPosition(50, 50);
			
			is[0]=0;
			is[1]=1;
			
			is[2]=MovingFunction.LINE;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=250;
			is[6]=100;
			
			is[7]=speedmoved3;
			
			is[8]=MovingFunction.FASTARC;
			is[9]=250;
			is[10]=100;
			is[11]=200;
			is[12]=100;
			is[13]=speedmoved3-1;
			is[14]=speedmoved3+14;
			is[15]=800;
			
			length=16;
		}else if (moved%bound==9){//STRIKE-9
			int speedmoved3=3;
			if (moved==9){
				speedmoved3=3;
			}else if (moved==9+bound){
				speedmoved3=5;
			}else if (moved==9+2*bound){
				speedmoved3=7;
			}
			
			bug.setPosition(160, 50);
			is[0]=0;
			is[1]=1;
			
			is[2]=MovingFunction.ARC;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=160;
			is[6]=150;
			is[7]=100;
			is[8]=1;
			is[9]=speedmoved3;//speed
			
			is[10]=MovingFunction.ARC;
			is[11]=160;
			is[12]=150;
			is[13]=160;
			is[14]=250;
			is[15]=100;
			is[16]=-1;
			is[17]=speedmoved3;//speed
			
			is[18]=MovingFunction.ARC;
			is[19]=160;
			is[20]=250;
			is[21]=160;
			is[22]=350;
			is[23]=100;
			is[24]=1;
			is[25]=speedmoved3;
			
			is[26]=MovingFunction.ARC;
			is[27]=160;
			is[28]=350;
			is[29]=160;
			is[30]=500;
			is[31]=100;
			is[32]=-1;
			is[33]=speedmoved3;
			
			length=34;
		}else if (moved%bound==10){//STRIKE-10
			int speedmoved3=3;
			if (moved==10){
				speedmoved3=3;
			}else if (moved==10+bound){
				speedmoved3=5;
			}else if (moved==10+2*bound){
				speedmoved3=7;
			}
			
			bug.setPosition(160, 50);
			is[0]=0;
			is[1]=1;
			
			is[2]=MovingFunction.ARC;
			is[3]=bug.getX();
			is[4]=bug.getY();
			is[5]=160;
			is[6]=150;
			is[7]=100;
			is[8]=-1;
			is[9]=speedmoved3;//speed
			
			is[10]=MovingFunction.ARC;
			is[11]=160;
			is[12]=150;
			is[13]=160;
			is[14]=250;
			is[15]=100;
			is[16]=1;
			is[17]=speedmoved3;//speed
			
			is[18]=MovingFunction.ARC;
			is[19]=160;
			is[20]=250;
			is[21]=160;
			is[22]=350;
			is[23]=100;
			is[24]=-1;
			is[25]=speedmoved3;
			
			is[26]=MovingFunction.ARC;
			is[27]=160;
			is[28]=350;
			is[29]=160;
			is[30]=500;
			is[31]=100;
			is[32]=1;
			is[33]=speedmoved3;
			
			length=34;
		}
		
	}
	
//	public static final int STAY = 0;//x1,y1,boolstay,timestay
//	public static final int LINE = 1;//x1,y1,x2,y2,speed
//	public static final int ZIGZAG = 2;//x1,y1,a,b,speed
//	public static final int ARC = 3;//x1,y1,x2,y2,t1,dir(1/-1),speed
//	public static final int FASTARC = 4;//x1,y1,x0,y0,xspeed,yspeed,time
	
	public static void transform(float[] ftx,float[] fty,int tx,int ty,int length){
		for(int i=0;i<length;++i){
			ftx[i]+=tx;
			fty[i]+=ty;
		}
	}
	
	
	public void rotate(float[] ftx,float[] fty,double theta,int length){
		double d=(theta*Math.PI)/180;
		for(int i=0;i<length;++i){
			float y=fty[i];
			float x=ftx[i];
			ftx[i]=(x*(float)Math.cos(d))-(y*(float)Math.sin(d)); 
			fty[i]=(x*(float)Math.sin(d))+(y*(float)Math.cos(d));
			if (i==51){
				System.out.println("Nilai fx : " + ftx[i] + "  Nilai fy : " + fty[i]);
			}
		}
	}
	
	
	private void createAll(){
		
	}
	
//	public static final int STAY = 0;//x1,y1,boolstay,timestay
//	public static final int LINE = 1;//x1,y1,x2,y2,speed
//	public static final int ZIGZAG = 2;//x1,y1,a,b,speed
//	public static final int ARC = 3;//x1,y1,x2,y2,t1,dir(1/-1),speed
//	public static final int FASTARC = 4;//x1,y1,x0,y0,xspeed,yspeed,time

	private int idx=2;
	
	private int[] xcount = new int[3000];
	private int counterxcount=0;
	
	private boolean x1lessx2;
	private boolean y1lessy2;
	private int nextxx;
	private int nextyy;
	
	public float getDistance(float x1,float y1,float x2,float y2){
		return (float)Math.sqrt((x1-x2)*(x1-x2) + (y1-y2)*(y1-y2));
	}
	
	private float nextxarc=1;
	private float nextyarc=0;
	private float[] allarcx = new float[3000]; 
	private float[] allarcy = new float[3000];
	private int idxforarcx=0;
	private int idxforarcy=0;
	
	private float fastarctemp=0;
	private float radiusfastarc=0;
	
	private void generateNextArc(float a,float b,int dir){
		int temp=2*Math.round(a);
		for(int i=0;i<=temp;++i){
			allarcx[temp-i]=i-Math.round(a); 
			allarcy[temp-i]=dir*b*(float)Math.sqrt(1- ( (allarcx[temp-i]*allarcx[temp-i])/(a*a)));
		}
	}
	
	public float getdx(float t){
		while (idx<length)
		{
			Log.d("AA", "DD + "+idx);
			if (is[idx]==MovingFunction.STAY){
				if (xcount[counterxcount]<1){
					xcurr=is[idx+1];
					ycurr=is[idx+2];
					xcount[counterxcount]++;
				}
				
				if (is[idx+3]==0){
					if (is[idx+4]>0){

						try {
							Thread.sleep((int)is[idx+4]);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						is[idx+4]=0;
						
						return 0;
					}else{
						idx+=5;
						is[idx+1]=xcurr;
						is[idx+2]=ycurr;
						counterxcount++;
						return 0;
					}
				}else{
					return 0;
				}
			}else if (is[idx]==MovingFunction.LINE){
				if (xcount[counterxcount]<1){
					x1lessx2=(is[idx+1]<=is[idx+3]);
					y1lessy2=(is[idx+2]<=is[idx+4]);
					float nextx=is[idx+3]-is[idx+1];
					float nexty=is[idx+4]-is[idx+2];
					float divider = gcd((int)Math.abs(nextx), (int)Math.abs(nexty));
					nextxx=(int)((nextx*is[idx+5])/divider);
					nextyy=(int)((nexty*is[idx+5])/divider);
					xcount[counterxcount]++;
				}
				if (x1lessx2){
					if (is[idx+1]<=is[idx+3]){
						is[idx+1]+=nextxx;
						return (float)nextxx;
					}
					xcurr=is[idx+3];
					ycurr=is[idx+4];
					is[idx+7]=xcurr;
					is[idx+8]=ycurr;
					idx+=6;
					counterxcount++;
					return 0;
				}else{
					if (is[idx+1]>=is[idx+3]){
						is[idx+1]+=nextxx;
						return (float)nextxx;
					}
					xcurr=is[idx+3];
					ycurr=is[idx+4];
					is[idx+7]=xcurr;
					is[idx+8]=ycurr;
					idx+=6;
					counterxcount++;
					return 0;
				}
			}else if (is[idx]==MovingFunction.ARC){
				if (xcount[counterxcount]<1){
					float temp=getDistance(is[idx+1], is[idx+2], is[idx+3], is[idx+4]);
					idxforarcx=(int)temp;
					idxforarcy=(int)temp;
					generateNextArc(temp/2, is[idx+5], (int)is[idx+6]);
					float b=allarcx[0];
					b=allarcy[0];
					
					float ox=(is[idx+3]-is[idx+1])/2 + (is[idx+1]);
					float oy=(is[idx+4]-is[idx+2])/2 + (is[idx+2]);
					
					rotate(allarcx, allarcy, -Math.atan(( (is[idx+2]-oy)/(is[idx+1]-ox) ))*180/Math.PI  , idxforarcx+1);
					
					transform(allarcx,allarcy, (int)(is[idx+3]-is[idx+1])/2, (int)(is[idx+4]-is[idx+2])/2, (int)idxforarcx+1);
					xcount[counterxcount]++;
				}
				if (idxforarcx-is[idx+7]>0){
					idxforarcx-=is[idx+7];
//					float c=(allarcx[idxforarcx]-allarcx[idxforarcx+1]);
					return Math.round(allarcx[idxforarcx]-allarcx[(int)idxforarcx+(int)is[idx+7]]);
				}else{
					
					xcurr=is[idx+3];
					ycurr=is[idx+4];
					is[idx+9]=xcurr;
					is[idx+10]=ycurr;
					idx+=8;
					counterxcount++;
					
					return 0;
				}
			}else if (is[idx]==MovingFunction.FASTARC){
				
				if (xcount[counterxcount]<1){
					fastarctemp=0;
					radiusfastarc=getDistance(is[idx+1], is[idx+2], is[idx+3], is[idx+4]);
					xcount[counterxcount]++;
					xcurr=is[idx+1];
					ycurr=is[idx+2];
				}
				
				if (fastarctemp<is[idx+7]){
					double ft1 = radiusfastarc*Math.cos(fastarctemp)+is[idx+3];
					double ft = radiusfastarc*Math.cos(fastarctemp-1)+is[idx+3];
					fastarctemp++;
					float ret = (float)(ft1-ft);
					ret+=(0.1*is[idx+5]);
					xcurr+=ret;
					return ret;
				}else{
					
					is[idx+9]=xcurr;
					is[idx+10]=ycurr;
					idx+=8;
					counterxcount++;
					return 0;
				}
				
			}
			return 0; 
		}
		return 0;
	}

	
//	public static final int STAY = 0;//x1,y1,boolstay,timestay
//	public static final int LINE = 1;//x1,y1,x2,y2,speed
//	public static final int ZIGZAG = 2;//x1,y1,a,b,speed
//	public static final int ARC = 3;//x1,y1,x2,y2,t1,dir(1/-1),speed
//	public static final int FASTARC = 4;//x1,y1,x0,y0,xspeed,yspeed,time
	
	public float getdy(float t){
		while (idx<length){
			if (is[idx]==MovingFunction.STAY){
				if (xcount[counterxcount]<1){
					xcurr=is[idx+1];
					ycurr=is[idx+2];
					xcount[counterxcount]++;
				}
				if (is[idx+3]==0){
					if (is[idx+4]>0){
						
						try {
							Thread.sleep((int)is[idx+4]);
						} catch (InterruptedException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						is[idx+4]=0;
						
						return 0;
					}else{
						idx+=5;
						is[idx+1]=xcurr;
						is[idx+2]=ycurr;
						counterxcount++;
						return 0;
					}
				}else{
					return 0;
				}
			}else if (is[idx]==MovingFunction.LINE){
				if (xcount[counterxcount]<1){
					x1lessx2=(is[idx+1]<=is[idx+3]);
					y1lessy2=(is[idx+2]<=is[idx+4]);
					float nextx=is[idx+3]-is[idx+1];
					float nexty=is[idx+4]-is[idx+2];
					float divider = gcd((int)Math.abs(nextx), (int)Math.abs(nexty));
					nextxx=(int)((nextx*is[idx+5])/divider);
					nextyy=(int)((nexty*is[idx+5])/divider);
					xcount[counterxcount]++;
				}
				
				if (y1lessy2){
					if (is[idx+2]<=is[idx+4]){
						is[idx+2]+=nextyy;
						return (float)nextyy;
					}
					xcurr=is[idx+3];
					ycurr=is[idx+4];
					is[idx+7]=xcurr;
					is[idx+8]=ycurr;
					idx+=6;
					counterxcount++;
					return 0;
				}else{
					if (is[idx+2]>=is[idx+4]){
						is[idx+2]+=nextyy;
						return (float)nextyy;
					}
					xcurr=is[idx+3];
					ycurr=is[idx+4];
					is[idx+7]=xcurr;
					is[idx+8]=ycurr;
					idx+=6;
					counterxcount++;
					return 0;
				}
			}else if (is[idx]==MovingFunction.ARC){
				if (xcount[counterxcount]<1){
					float temp=getDistance(is[idx+1], is[idx+2], is[idx+3], is[idx+4]);
					idxforarcx=(int)temp;
					idxforarcy=(int)temp;
					generateNextArc(temp/2, is[idx+5], (int)is[idx+6]);
					float b=allarcx[0];
					b=allarcy[0];
					
					float ox=(is[idx+3]-is[idx+1])/2 + (is[idx+1]);
					float oy=(is[idx+4]-is[idx+2])/2 + (is[idx+2]);
					
					rotate(allarcx, allarcy, -Math.atan(( (is[idx+2]-oy)/(is[idx+1]-ox) ))*180/Math.PI  , idxforarcx+1);
					
					transform(allarcx,allarcy, (int)(is[idx+3]-is[idx+1])/2, (int)(is[idx+4]-is[idx+2])/2, (int)idxforarcy+1);
					xcount[counterxcount]++;
				}
				if (idxforarcy-is[idx+7]>0){
					idxforarcy-=is[idx+7];
//					float c=allarcy[idxforarcy]-allarcy[idxforarcy+1];
					return Math.round(allarcy[idxforarcy]-allarcy[idxforarcy+(int)is[idx+7]]);
				}else{
					
					xcurr=is[idx+3];
					ycurr=is[idx+4];
					is[idx+9]=xcurr;
					is[idx+10]=ycurr;
					idx+=8;
					counterxcount++;
					
					return 0;
				}
			}else if (is[idx]==MovingFunction.FASTARC){
				
				if (xcount[counterxcount]<1){
					fastarctemp=0;
					radiusfastarc=getDistance(is[idx+1], is[idx+2], is[idx+3], is[idx+4]);
					xcount[counterxcount]++;
					xcurr=is[idx+1];
					ycurr=is[idx+2];
				}
				
				if (fastarctemp<is[idx+7]){
					double ft1 = radiusfastarc*Math.cos(fastarctemp)+is[idx+4];
					double ft = radiusfastarc*Math.cos(fastarctemp-1)+is[idx+4];
					fastarctemp++;
					float ret = (float)(ft1-ft);
					ret+=(0.1*is[idx+6]);
					ycurr+=ret;
					return ret;
				}else{
					
					is[idx+9]=xcurr;
					is[idx+10]=ycurr;
					idx+=8;
					counterxcount++;
					
					return 0;
				}
				
			}
			return 0;
		}
		return 0;
	}
	
	public static boolean isIntersect(float p1,float q1,float p2,float q2){
		if (Math.min(p1, q1)<Math.min(p2, q2)){
			if (Math.min(p2, q2)>Math.max(p1, q1)){
				return false;
			}
			return true;
		}else{
			if (Math.min(p1, q1)>Math.max(p2, q2)){
				return false;
			}
			return true;
		}
	}
	
	public static float getStance(float alpha,float beta,float C,float x1,float y1){
		return (alpha*x1)+(beta*y1)+C;
	}
	
	public static boolean isPathInRect(RectF r,float[] x,float[] y,int length){
		int i;
		boolean b=false;
		for(i=0;i<length-1 && !b;++i){
			float x1=x[i];
			float y1=y[i];
			float x2=x[i+1];
			float y2=y[i+1];
			
			if (isIntersect(r.left, r.right, x1, x2) && isIntersect(r.bottom, r.top, y1, y2)){
				float alpha = y2-y1;
				float beta  = x1-x2;
				float C = -x1*(y2-y1)+y1*(x2-x1);
				if ( (getStance(alpha, beta, C, r.left,r.bottom)<=0) || (getStance(alpha, beta, C, r.left,r.top)<=0) || (getStance(alpha, beta, C, r.right,r.top)<=0) || (getStance(alpha, beta, C, r.right,r.bottom)<=0)){
					b=true;
				}
			}
		}
		
		return b;
	}
	
}

//public static final int STAY = 0;//x1,y1,boolstay,timestay
//public static final int LINE = 1;//x1,y1,x2,y2,speed
//public static final int ZIGZAG = 2;//x1,y1,a,b,speed
//public static final int ARC = 3;//x1,y1,x2,y2,t1,dir(1/-1),speed
//public static final int FASTARC = 4;//x1,y1,x0,y0,xspeed,yspeed,time
