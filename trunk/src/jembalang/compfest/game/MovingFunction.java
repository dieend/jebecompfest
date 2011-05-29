package jembalang.compfest.game;


import android.util.Log;

public class MovingFunction {
	
	public static final int STAY = 0;//x1,y1,boolstay,timestay
	public static final int LINE = 1;//x1,y1,x2,y2,speed
	public static final int ZIGZAG = 2;//x1,y1,a,b,speed
	public static final int ARC = 3;//x1,y1,x2,y2,o1,o2,t1,dir(1/-1),speed
	
	public static int gcd(int a,int b){
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
	
	private int[]is = new int[3000];
	private int length=0;
	private int xcurr;
	private int ycurr;
	
	public void setStatus(int status){
		is[0]=status;
	}
	//is[0]=status,is[1]=length param,is[2]=start param
	public MovingFunction(int...is){
		idx=2;
		length=is.length;
		
		for(int i=0;i<is.length;++i){
			this.is[i]=is[i];
		}
		for(int i=is.length;i<this.is.length;++i){
			this.is[i]=0;
		}
		
		for(int i=0;i<xcount.length;++i){
			xcount[i]=0;
		}
	}
	
	
	public static void transform(float[] ftx,float[] fty,int tx,int ty,int length){
		for(int i=0;i<length;++i){
			ftx[i]+=tx;
			fty[i]+=ty;
		}
	}
	
	
	public static void rotate(float[] ftx,float[] fty,double theta,int length){
		for(int i=0;i<length;++i){
			float y=fty[i];
			float x=ftx[i];
			double d=(theta*Math.PI)/180;
			fty[i]=(y*(float)Math.cos(d))-(x*(float)Math.sin(d)); 
			ftx[i]=(y*(float)Math.sin(d))+(x*(float)Math.cos(d));
		}
	}
	
	
	private void createAll(){
		
	}
//	public static final int STAY = 0;//x1,y1,intisstayforever,timestay(milis)
//	public static final int LINE = 1;//x1,y1,x2,y2,speed
//	public static final int ZIGZAG = 2;//x1,y1,a,b,speed
//	public static final int ARC = 3;//x1,y1,x2,y2,t1,dir(1/-1),speed
//	public static final int COMBINE = 4;//all

	private int idx=2;
	
	private int[] xcount = new int[300];
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
							Thread.sleep(is[idx+4]);
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
					x1lessx2=(is[idx+1]<is[idx+3]);
					y1lessy2=(is[idx+2]<is[idx+4]);
					int nextx=is[idx+3]-is[idx+1];
					int nexty=is[idx+4]-is[idx+2];
					int divider = gcd((int)Math.abs(nextx), (int)Math.abs(nexty));
					nextxx=(nextx*is[idx+5])/divider;
					nextyy=(nexty*is[idx+5])/divider;
					xcount[counterxcount]++;
				}
				if (x1lessx2){
					if (is[idx+1]<is[idx+3]){
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
					if (is[idx+1]>is[idx+3]){
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
					generateNextArc(temp/2, is[idx+5], is[idx+6]);
					float b=allarcx[0];
					b=allarcy[0];
					
					float ox=(is[idx+3]-is[idx+1])/2;
					float oy=(is[idx+4]-is[idx+2])/2;
					rotate(allarcx, allarcy, Math.atan((is[idx+1]-ox)/(is[idx+2]-oy))*180/Math.PI, idxforarcx+1);
					
					transform(allarcx,allarcy, (is[idx+3]-is[idx+1])/2, (is[idx+4]-is[idx+2])/2, idxforarcx+1);
					xcount[counterxcount]++;
				}
				if (idxforarcx>0){
					idxforarcx--;
//					float c=(allarcx[idxforarcx]-allarcx[idxforarcx+1]);
					return (Math.round(allarcx[idxforarcx]-allarcx[idxforarcx+1])*is[idx+7]);
				}else{
					return 0;
				}
			}
			return 0; 
		}
		return 0;
	}

	
//	public static final int STAY = 0;//x1,y1,intisstayforever,timestay(milis)
//	public static final int LINE = 1;//x1,y1,x2,y2,speed
//	public static final int ZIGZAG = 2;//x1,y1,a,b,speed
//	public static final int ARC = 3;//x1,y1,x2,y2,t1,dir(1/-1),speed
//	public static final int COMBINE = 4;//all
	
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
							Thread.sleep(is[idx+4]);
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
					x1lessx2=(is[idx+1]<is[idx+3]);
					y1lessy2=(is[idx+2]<is[idx+4]);
					int nextx=is[idx+3]-is[idx+1];
					int nexty=is[idx+4]-is[idx+2];
					int divider = gcd((int)Math.abs(nextx), (int)Math.abs(nexty));
					nextxx=(nextx*is[idx+5])/divider;
					nextyy=(nexty*is[idx+5])/divider;
					xcount[counterxcount]++;
				}
				
				if (y1lessy2){
					if (is[idx+2]<is[idx+4]){
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
					if (is[idx+2]>is[idx+4]){
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
					generateNextArc(temp/2, is[idx+5], is[idx+6]);
					float b=allarcx[0];
					b=allarcy[0];
					
					float ox=(is[idx+3]-is[idx+1])/2;
					float oy=(is[idx+4]-is[idx+2])/2;
					rotate(allarcx, allarcy, Math.atan((is[idx+1]-ox)/(is[idx+2]-oy))*180/Math.PI, idxforarcx+1);
					
					
					transform(allarcx,allarcy, (is[idx+3]-is[idx+1])/2, (is[idx+4]-is[idx+2])/2, idxforarcy+1);
					xcount[counterxcount]++;
				}
				if (idxforarcy>0){
					idxforarcy--;
//					float c=allarcy[idxforarcy]-allarcy[idxforarcy+1];
					return (Math.round(allarcy[idxforarcy]-allarcy[idxforarcy+1])*is[idx+7]);
				}else{
					return 0;
				}
			}
			return 0;
		}
		return 0;
	}
}

//public static final int STAY = 0;//x1,y1,intisstayforever,timestay(milis)
//public static final int LINE = 1;//x1,y1,x2,y2,speed
//public static final int ZIGZAG = 2;//x1,y1,a,b,speed
//public static final int ARC = 3;//x1,y1,x2,y2,t1,dir(1/-1),speed
//public static final int COMBINE = 4;//all