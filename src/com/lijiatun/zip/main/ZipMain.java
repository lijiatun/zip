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
		zip.unzip("/Users/xxxx/Downloads/国家局信息采集考核.zip", "/Users/liguangwen/Downloads","GBK");
		
		//zip.zip("/Users/xxxxx/Downloads/国家局信息采集考核","GBK");
		
	}

}
