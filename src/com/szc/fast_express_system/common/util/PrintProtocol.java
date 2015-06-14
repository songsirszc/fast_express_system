package com.szc.fast_express_system.common.util;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.io.ObjectInputStream.GetField;


public class PrintProtocol {
	public static void main(String[] args) {
	    String filePath = "/text.txt";
        readTxtFile(filePath);
	}
	
	 public static void readTxtFile(String filePath){
	        try {
	                String encoding="GBK";
	                File file=new File(filePath);
	                if(file.isFile() && file.exists()){ //�ж��ļ��Ƿ����?
	                    InputStreamReader read = new InputStreamReader(
	                    new FileInputStream(file),encoding);//���ǵ�������?
	                    BufferedReader bufferedReader = new BufferedReader(read);
	                    String lineTxt = null;
	                    while((lineTxt = bufferedReader.readLine()) != null){
	                    	String[] line =  lineTxt.trim().split("	");
	                        System.out.println("	private String	"+line[0] +"; //" +line[1]);
	                    }
	                    read.close();
	        }else{
	            System.out.println("�Ҳ���ָ�����ļ�");
	        }
	        } catch (Exception e) {
	            System.out.println("��ȡ�ļ����ݳ���");
	            e.printStackTrace();
	        }
	     
	    }
}
