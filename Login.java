package com.Chu.Chat_easy_Client;

import java.awt.EventQueue;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import java.awt.Color;

public class Login {

	private JFrame frame;
	private JTextField txt_UserName;
	private JPasswordField txt_Password;
//	private String url = "jdbc:mysql://localhost:3306/chat";
	private String url = "jdbc:mysql://*****/Chat";
//	private String user = "root";
	private String user = "*****";
//	private String password = "*****";//it need to be changed
	private String password = "*****";//it need to be changed
	private String driver = "com.mysql.jdbc.Driver";
	private Connection con;
	private ResultSet res;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					Login window1 = new Login();
					window1.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public Login() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 744, 571);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(null);
		
		JLabel lblNewLabel = new JLabel("UserName");
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel.setBounds(109, 118, 117, 48);
		frame.getContentPane().add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Password");
		lblNewLabel_1.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 20));
		lblNewLabel_1.setBounds(109, 259, 117, 41);
		frame.getContentPane().add(lblNewLabel_1);
		
		txt_UserName = new JTextField();
		txt_UserName.setBounds(269, 129, 170, 32);
		frame.getContentPane().add(txt_UserName);
		txt_UserName.setColumns(20);
		
		txt_Password = new JPasswordField();
		txt_Password.setBounds(269, 266, 170, 32);
		frame.getContentPane().add(txt_Password);
		

		JLabel lbl_Warning = new JLabel("");
		lbl_Warning.setHorizontalAlignment(SwingConstants.CENTER);
		lbl_Warning.setForeground(Color.RED);
		lbl_Warning.setBounds(128, 347, 407, 32);
		frame.getContentPane().add(lbl_Warning);
		
		JButton btnLogin = new JButton("Login");
		btnLogin.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				
//				login_Process(lbl_Warning);
				login_Process(lbl_Warning,url,user,password,"select * from User_info");
				
				
			}
		});
		btnLogin.setFont(new Font("宋体", Font.PLAIN, 20));
		btnLogin.setBounds(126, 402, 127, 41);
		frame.getContentPane().add(btnLogin);
		
		JButton btnCancel = new JButton("Cancel");
		btnCancel.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				txt_UserName.setText("");
				txt_Password.setText("");
			}
		});
		btnCancel.setFont(new Font("宋体", Font.PLAIN, 20));
		btnCancel.setBounds(373, 402, 127, 41);
		frame.getContentPane().add(btnCancel);
		
	}
	
	public void login_Process(JLabel lbl_warning) {
		FileReader fr;
		try {
			fr = new FileReader("d:/Userinfo.txt");
			BufferedReader br=new BufferedReader(fr);
			String UserInfo="UserName:"+txt_UserName.getText().toString()+",Password:"+txt_Password.getText();
			String tmp;
			boolean flag=false;
			while((tmp=br.readLine())!=null) 
			{
				if(UserInfo.equals(tmp)) {
					flag=true;
					break;
				}
			}
			if(flag) {
				ChatRoom window = new ChatRoom(txt_UserName.getText().toString());
				frame.setVisible(false);
				window.getChatroom().setVisible(true);
			}
			else {
				lbl_warning.setText("用户名或者密码输入错误！");
				txt_UserName.setText("");
				txt_Password.setText("");
			}
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void login_Process(JLabel lbl_warning,String url,String user,String pass,String Sql) {

		try {
			Class.forName(driver);
			con=DriverManager.getConnection(url,user ,pass);
			Statement sta=con.createStatement();
			res=sta.executeQuery(Sql);
			String UserName;
			String Passsword;
			boolean flag=false;
			while(res.next()) {
				UserName=res.getString("UserName");
				Passsword=res.getString("Userpassword");
				if(txt_UserName.getText().equals(UserName) && txt_Password.getText().equals(Passsword)) {
					flag=true;
					break;
				}
			}
			if(flag) {
				ChatRoom window = new ChatRoom(txt_UserName.getText().toString());
				frame.setVisible(false);
				window.getChatroom().setVisible(true);
			}
			else {
				lbl_warning.setText("用户名或者密码输入错误！");
				txt_UserName.setText("");
				txt_Password.setText("");
			}
			
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}finally {
			if(con!=null)
				try {
					con.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			if(res!=null) {
				try {
					res.close();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
