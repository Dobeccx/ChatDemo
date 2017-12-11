package com.Chu.Chat_easy_Client;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.Date;
import java.text.SimpleDateFormat;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;

import java.util.*;

public class ChatRoom {

	private JFrame Chatroom;
	private String UserName;
	private String textsend;
	private Socket s;
	private int port=6666;
//	private String ip="10.35.63.111";
	public BufferedReader br;
	public DataInputStream dis;
	public DataOutputStream dos;
	public PrintWriter pw;
//	private String ip="127.0.0.1";
	private String ip="112.74.96.109";
	private Date date;


	public ChatRoom(String username) {
		this.UserName=username;
		
		initialize();
		
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		setChatroom(new JFrame());
		getChatroom().setBounds(100, 100, 1005, 749);
		getChatroom().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getChatroom().getContentPane().setLayout(null);
		
		JLabel lbl_UserName = new JLabel("User:"+this.UserName);
		lbl_UserName.setFont(new Font("ËÎÌו", Font.PLAIN, 20));
		lbl_UserName.setBounds(43, 37, 158, 33);
		Chatroom.getContentPane().add(lbl_UserName);
		
		JTextArea txtarea_Display = new JTextArea();
		txtarea_Display.setEditable(false);
		txtarea_Display.setLineWrap(true);
		txtarea_Display.setBounds(32, 80, 902, 433);
		Chatroom.getContentPane().add(txtarea_Display);
		
		JTextArea txtarea_Send = new JTextArea();
		txtarea_Send.setBounds(28, 541, 575, 120);
		Chatroom.getContentPane().add(txtarea_Send);
		
		JButton btn_Send = new JButton("Send");
		btn_Send.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
					try {
						s=new Socket(ip,port);
						dos=new DataOutputStream(s.getOutputStream());
						if(!txtarea_Send.getText().equals(""))
							dos.writeUTF(UserName+": "+txtarea_Send.getText());
						dis=new DataInputStream(s.getInputStream());
						System.out.println(UserName+": "+txtarea_Send.getText());
						Reflush(s,dis,txtarea_Display);
					} catch (UnknownHostException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}finally {
						try {
							dos.close();
							dis.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						try {
							s.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
			
			}});
		btn_Send.setFont(new Font("ËÎÌו", Font.PLAIN, 25));
		btn_Send.setBounds(639, 569, 122, 69);
		Chatroom.getContentPane().add(btn_Send);
		
		JButton btnNewButton = new JButton("Clean All");
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txtarea_Display.setText("");
			}
		});
		btnNewButton.setFont(new Font("ËÎÌו", Font.PLAIN, 25));
		btnNewButton.setBounds(803, 569, 158, 69);
		Chatroom.getContentPane().add(btnNewButton);
		
		
	}
	
	public void Reflush(Socket S,DataInputStream dis,JTextArea txt_display) { 
		try {
			dis=new DataInputStream(S.getInputStream());
			String tmp="";
			txt_display.setText("Connected");
			br=new BufferedReader(new InputStreamReader(S.getInputStream()) );
			int len=Integer.parseInt(br.readLine());
			for(int i=0;i<len;i++) {
				tmp=br.readLine();
//			if(!tmp.equals(null))
//			txt_display.setText(txt_display.getText()+"\n"+tmp+"      "+date.getTime());
				txt_display.setText(txt_display.getText()+"\n"+tmp);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
	//To be
//	public static String getStringDate() {
//	    Date currentTime = date;
//	    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//	    String dateString = formatter.format(currentTime);
//	    return dateString;
//	  }
	
	public JFrame getChatroom() {
		return Chatroom;
	}
	
	public void setChatroom(JFrame chatroom) {
		Chatroom = chatroom;
	}
	public String Text_Send() {
		return textsend;
	}
	
}