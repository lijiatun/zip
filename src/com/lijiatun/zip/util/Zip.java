package com.lijiatun.zip.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Zip 
{
	public File zip(String filePath,String baseCode) {
		long startTime = System.currentTimeMillis();
        File target = null;
        File source = new File(filePath);
        if (source.exists()) {
            String sourceName = source.getName();
            String zipName = sourceName.contains(".") ? sourceName.substring(0, sourceName.indexOf(".")) : sourceName;
            target = new File(source.getParent(), zipName + ".zip");
            if (target.exists()) {
                target.delete();//删除旧的压缩包
            }
            FileOutputStream fos = null;
            ZipOutputStream zos = null;
            try {
                fos = new FileOutputStream(target);
                zos = new ZipOutputStream(new BufferedOutputStream(fos),Charset.forName(baseCode));
                addEntry(null, source, zos);  //添加对应的文件Entry
                if (zos != null) 
	        		{
	        			zos.close();
	        		}
	        		if(fos!=null)
	        		{
	        			fos.close();
	        		}
            } 
            catch (IOException e) 
            {
               e.printStackTrace();
            }
        }
        long endTime = System.currentTimeMillis();
        System.out.println("压缩成功,耗费时间： " + (endTime - startTime) + " ms");
        return target;
    }
	
	 private static void addEntry(String base, File source, ZipOutputStream zos) throws IOException {
	        String entry = (base != null) ? base + source.getName() : source.getName(); //按目录分级，形如：aaa/bbb.txt
	        if (source.isDirectory()) {
	            File[] files = source.listFiles();
	            if (files != null && files.length > 0) {
	                for (File file : files) {
	                    addEntry(entry + "/", file, zos);// 递归列出目录下的所有文件，添加文件 Entry
	                }
	            }
	        } else {
	            FileInputStream fis = null;
	            BufferedInputStream bis = null;
	            try {
	                byte[] buffer = new byte[1024 * 10];
	                fis = new FileInputStream(source);
	                bis = new BufferedInputStream(fis, buffer.length);
	                int read;
	                //如果只是想将文件夹下的所有文件压缩，不需名要压缩父目录,约定文件名长度 entry.substring(length)
	                zos.putNextEntry(new ZipEntry(entry)); 
	                while ((read = bis.read(buffer, 0, buffer.length)) != -1) {
	                    zos.write(buffer, 0, read);
	                }
	                zos.closeEntry();
	            } 
	            finally 
	            {
		            	if (bis != null) 
		        		{
			            	bis.close();
		        		}
		        		if(fis!=null)
		        		{
		        			fis.close();
		        		}
	            }
	            
	        }
	    }


	public void unzip(String filePath, String unFilePath,String baseCode) 
	{
		long startTime = System.currentTimeMillis();
		try {
			//输入源zip路径
			ZipInputStream zis = new ZipInputStream(new FileInputStream(filePath),Charset.forName(baseCode));
			BufferedInputStream bis = new BufferedInputStream(zis);
			//输出路径（文件夹目录）
			String parent = unFilePath; 
			File file = null;
			ZipEntry entry;
			try 
			{
				while ((entry = zis.getNextEntry())!=null) 
				{
					
					if(!entry.isDirectory())
					{
						file = new File(parent, entry.getName());
						if (!file.getParentFile().exists()) 
						{
							file.getParentFile().mkdirs();
						}
						FileOutputStream out = new FileOutputStream(file);
						BufferedOutputStream bout = new BufferedOutputStream(out);
						int b;
						while ((b = bis.read()) != -1) 
						{
							bout.write(b);
						}
						bout.close();
						out.close();
					}
				}
				bis.close();
				zis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("解压成功，耗费时间： " + (endTime - startTime) + " ms");
	}

}
