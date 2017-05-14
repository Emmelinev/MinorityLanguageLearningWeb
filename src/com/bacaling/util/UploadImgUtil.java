package com.bacaling.util;

import java.io.File;
import java.io.*;
import java.util.List;
import org.apache.commons.fileupload.FileItem;

public class UploadImgUtil {
	 public static int uplaod(String path,List<FileItem> list){
		int result = 0;
        try {
            for(FileItem item : list){
                String name = item.getFieldName();
                if(item.isFormField()){
                    String value = item.getString() ;
                }else{
                    String value = item.getName() ;
                    int start = value.lastIndexOf("\\");
                    String filename = value.substring(start+1);
//                    request.setAttribute(name, filename);
                    OutputStream out = new FileOutputStream(new File(path,filename));
                    InputStream in = item.getInputStream() ;
                    int length = 0 ;
                    byte [] buf = new byte[1024] ;
                    System.out.println("获取上传文件的总共的容量："+item.getSize());
                    while( (length = in.read(buf) ) != -1){
                        out.write(buf, 0, length);
                    }
                    result = 1;
                    in.close();
                    out.close();
                }
            }
        }catch (Exception e) {
        	result = 0;
            e.printStackTrace();
	    }
        return result;
	 }	 
}