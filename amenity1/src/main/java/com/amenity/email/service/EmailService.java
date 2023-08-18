package com.amenity.email.service;

import java.util.Random;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
public class EmailService {
	@Autowired
	private JavaMailSenderImpl sendemail;
	private int authNumber; 
	
	/// ������ȣ ������ �����
	public void makeRandomNumber() {
		Random rand = new Random();
		int randNum = rand.nextInt(888888) + 111111; // 000000 ~ 999999 
		System.out.println("������ȣ : " + randNum);
		authNumber = randNum;
	}
	
	//�̸��� ���� ���! 
	public String sendEmail(String email) {
		makeRandomNumber();
		String setFrom = "qjarbrin@naver.com"; 
		String toMail = email;
		String title =   "ȸ�� ���� ���� �̸��� �Դϴ�."; 
		String content = "���� ��ȣ�� " + 	 
		                 "<br><br>" + 
					      authNumber + "�Դϴ�." + 
					     "<br>" + 
					     "�ش� ������ȣ�� ������ȣ" + 
					     "Ȯ�ζ��� �ۼ� �ϼ���.";
		sendEmail(setFrom, toMail, title, content);
		return Integer.toString(authNumber);
	}

	//�̸��� ���� �޼ҵ�
	public void sendEmail(String setFrom, String toMail, String title, String content) { 
		MimeMessage message = sendemail.createMimeMessage();
		try {
			MimeMessageHelper helper = new MimeMessageHelper(message,true,"utf-8");
			helper.setFrom(setFrom);
			helper.setTo(toMail);
			helper.setSubject(title);
			helper.setText(content,true); // true ���� �ۼ����� ������ �ܼ� �ؽ�Ʈ�� ����.
			sendemail.send(message);
		} 
		catch (MessagingException e) {
				e.printStackTrace();
			}
		}
	
	
	
	
	
	
	
}
