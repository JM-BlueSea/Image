package code;

import java.awt.AlphaComposite;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.imageio.ImageIO;

public class ImageUtils {
	final static Random RANDOM=new Random();
	final static int PAOPAONUMBER=15;
	final static int VERIFYCODESIZE=4;
	final static int width=102;
	final static int height=38;
	public static boolean GenerateAnImage(String VerifyCode,String format,String OutputPath) throws FileNotFoundException{
		OutputStream outputStream=new FileOutputStream(OutputPath+VerifyCode+".png");
		try {
			BufferedImage bufferedImage=new BufferedImage(width, height,BufferedImage.TYPE_INT_RGB);
			Graphics2D g2d=(Graphics2D)bufferedImage.getGraphics();
			g2d.setColor(getARandomBackgroundColor());
			g2d.fillRect(0,0,width,height);
			//随机画干扰的蛋蛋圈
			for(int i=0;i<PAOPAONUMBER;i++){
				g2d.setColor(getARandomBackOvalColor());
				g2d.drawOval(RANDOM.nextInt(width),RANDOM.nextInt(height),5 + RANDOM.nextInt(10),5 + RANDOM.nextInt(10));
			}
			Font font=new Font("Tunga",Font.BOLD, 40);
			g2d.setFont(font);
			int h=height-((height-font.getSize())>>1),w=width/VERIFYCODESIZE,size=w-font.getSize()+1;
			/* 画字符串 */
			AlphaComposite ac3;
			for(int i=0;i<VERIFYCODESIZE;i++){
				ac3=AlphaComposite.getInstance(AlphaComposite.SRC_OVER, 0.7f);// 指定透明度
				g2d.setComposite(ac3);
				g2d.setColor(new Color(20+RANDOM.nextInt(110),20+RANDOM.nextInt(110),20+RANDOM.nextInt(110)));
				int x=(width-(VERIFYCODESIZE-i)* w)+size+15;
				int y=h-4;
				int jiaoDu=huoQuYiGeFuSanShiDaoSanShiDeShuZhi();
				g2d.rotate(jiaoDu*Math.PI/180.0,x,y);
				g2d.drawString(VerifyCode.charAt(i)+"",x,y);
				g2d.rotate(-jiaoDu*Math.PI/180.0,x,y);
				ac3=null;
			}
			//画干扰线
			//设置线条粗细
			g2d.setStroke(new BasicStroke(3.0f));
			//设置线条颜色
			g2d.setColor(new Color(49, 47, 48));
			//随机-15°~15°旋转角
			int r=RANDOM.nextInt(30)-15;
			//随机-10到10 旋转中心偏移值
			int x=RANDOM.nextInt(20)-10;
			//随机一个数：0或者1
			int tob=RANDOM.nextInt(2);
			//圆弧干扰线所在的矩形的左上角纵坐标
			int y=tob==0?-20:20;
			//圆弧干扰线的结束弧度
			int huaDeJieWeiJiaoDu=tob==0?-180:180;
			//旋转坐标系
			g2d.rotate(r*Math.PI/180.0,54+x,20);
			//圆弧干扰线所在的矩形的左上角横坐标
			int x1s=RANDOM.nextInt(54)+54;
			//绘制圆弧干扰线
			g2d.drawArc(-x1s,y,108+x1s,40,0,huaDeJieWeiJiaoDu);
			//复原坐标系
			g2d.rotate(-r*Math.PI/180.0,54+x,20);
			//收起画笔
			g2d.dispose();
			//输入图片文件
			ImageIO.write(bufferedImage,format,outputStream);
			//刷出输出流
			outputStream.flush();
		}catch(IOException e){
			return false;
		}finally{
			try{
				outputStream.close();
			}catch(IOException e){
				e.printStackTrace();
			}
		}
		return true;
	}
	/**
	 * 随机一个适合的背景颜色
	 * @return
	 */
  	public static Color getARandomBackgroundColor(){
  		final int fc=175;
  		final int bc=200;
  		int r = fc + new Random().nextInt(bc - fc);
  		int g = fc + new Random().nextInt(bc - fc);
  		int b = fc + new Random().nextInt(bc - fc);
  		return new Color(r, g, b);
  	}
  	/**
  	 * 随机一个适合的蛋蛋颜色
  	 * @return
  	 */
  	public static Color getARandomBackOvalColor(){
  		final int fc=150;
  		final int bc=250;
  		int r = fc + new Random().nextInt(bc - fc);
  		int g = fc + new Random().nextInt(bc - fc);
  		int b = fc + new Random().nextInt(bc - fc);
  		return new Color(r, g, b);
  	}
  	/**
  	 * 判断给定的字符串是否是属于数值字符串
  	 * @param data
  	 * @return 如果是数值字符串就返回true，否则返回false
  	 */
  	public static boolean isNumber(String data){
		Pattern pattern = Pattern.compile("[0-9]*"); 
		Matcher isNum = pattern.matcher(data);
		if (!isNum.matches()) {
			return false;
		}
		return true; 
	}
  	/**
	 * 获取一个-30~30的数值
	 * @return 一个-30~30的数值
	 */
	public static int huoQuYiGeFuSanShiDaoSanShiDeShuZhi(){
		return RANDOM.nextInt(60)-30;
	}
}
