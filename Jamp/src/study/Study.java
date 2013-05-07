package study;

import javax.swing.*;
import java.awt.*;

public class Study extends JFrame{
	private static final long serialVersionUID = 6301578507883786209L;

	public Study(){
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		
		JLabel la = new JLabel("0");
		la.setFont(new Font("Helvetica", Font.BOLD, 80));
		la.setHorizontalAlignment(JLabel.CENTER);
		add(la);
		
		JLabel la2 = new JLabel("50");
		la2.setFont(new Font("Helvetica", Font.BOLD, 80));
		la2.setHorizontalAlignment(JLabel.CENTER);
		add(la2);	
		
		JLabel la3 = new JLabel("100");
		la3.setFont(new Font("Helvetica", Font.BOLD, 80));
		la3.setHorizontalAlignment(JLabel.CENTER);
		add(la3);	
		
		setSize(500, 150);
		setVisible(true);
		
		TimerThread tid = new TimerThread(la,10);
		tid.start();
		
		TimerThread tid2 = new TimerThread(la2,100);
		tid2.start();
		
		Thread th3 = new Thread(new ThreadRunable(la3, 1000));
		th3.start();
		
	}
	
	public static void main(String[] args) {
		new Study();
	}
}

class TimerThread extends Thread{
	JLabel a;
	int n,m;
	
	public TimerThread(JLabel a, int m){
		this.a = a;
		this.m = m;
		n = Integer.parseInt(a.getText());
	}
	
	public void run(){
		while(true){
			try{
				sleep(m);
			}catch(InterruptedException e){
				return;
			}
			n++;

			a.setText(Integer.toString(n));
		}
	}
}

class ThreadRunable implements Runnable{
	JLabel a;
	int n,m;
	
	public ThreadRunable(JLabel a, int m){
		this.a = a;
		this.m = m;
	}
	public void run(){
		while(true){
			try{
				Thread.sleep(m);
			}catch(InterruptedException e){
				return;
			}
			n++;

			a.setText(Integer.toString(n));
		}
	}
}