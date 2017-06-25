package com.lijiatun.zip.main;


import com.lijiatun.zip.util.Zip;

/**
 * @author liguangwen
 *
 */
public class ZipMain {

	public static void main(String[] args) 
	{
		Zip zip = new Zip();
		zip.unzip("/Users/xxxx/Downloads/某某某.zip", "/Users/liguangwen/Downloads","GBK");
		
		//zip.zip("/Users/xxxxx/Downloads/某某某","GBK");
		
	}

}
