package com.Chu;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Server {
	private static int port=6666;
	private static ServerSocket ss;
	private static Socket s;
	private static List<Socket> ClientList;
	public static DataInputStream dis;
	public static DataOutputStream dos;
	public static List<String> mes;
	private static PrintWriter pw;

	
	public static void main(String[] args) {
		try {
			ss=new ServerSocket(port);
			ClientList=new ArrayList<Socket>();
			mes=new ArrayList<String>();
			String str="123";
			boolean flag=true;
			while(true) {
				s=ss.accept();
				if(!ClientList.isEmpty()) {
				for(Socket s1:ClientList) {
					if(s1.equals(s))
						flag=false;
				}
				}
				if(flag) {
					ClientList.add(s);
				}
				dis=new DataInputStream(s.getInputStream());
				str=dis.readUTF();
				
				mes.add(str);
				//Send message to Everyone
				for(Socket s1:ClientList) {
						pw=new PrintWriter(s.getOutputStream(),true);
						int i=mes.size();
						pw.println(i);
						for(String str1:mes) {
							pw.println(str1);
						}
				}
			}
		
		
		
		
		
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			
			try {
				dos.close();
				dis.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
			try {
				s.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
	}
}



