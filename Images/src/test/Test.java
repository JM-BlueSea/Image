package test;

import java.io.FileNotFoundException;

import code.ImageUtils;

public class Test {

	public static void main(String[] args) throws FileNotFoundException {
		//测试VerifyCodeGeneratorUtils.GenerateAnImage(int d,int height,int paopao,String code)
		boolean flag=ImageUtils.GenerateAnImage("KDSD", "png", "./");
		System.out.println(flag);
	}

}
