package com.paracelsoft.teamsport.service;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional(rollbackFor = { Exception.class, ParseException.class })
@Service("mailService")
public class MailService {
	
	public final String STR_HEADER = "<!DOCTYPE html>\n" + 
			"<html>\n" + 
			"<head>\n" + 
			"    \n" + 
			"</head>\n" + 
			"<style type=\"text/css\">\n" + 
			"html{\n" + 
			"    background-color:white;\n" + 
			"    font-family: 'Lato', 'Arial', sans-serif;\n" + 
			"    font-weight: 300;\n" + 
			"    font-size: 20px;\n" + 
			"    text-rendering: optimizeLegibility;\n" + 
			"    overflow-x: hidden;}\n" + 
			"</style>\n" + 
			"<body>\n" + 
			"	<table border=\"0\" width=\"100%\" style=\"margin:auto;padding:30px;background-color:#F7F7F7;\">\n" + 
			"		<tr>\n" + 
			"			<td style=\"text-align: center;\" >\n" + 
			"				<img style=\"width: 100px;\" src='cid:image1' />\n" + 
			"            </td>\n" + 
			"            \n" + 
			"        </tr>\n";
	
	public final String STR_FOOTER = "<div style='margin-bottom: 15px;'> \n" + 
    		"                            <a href='#' style='margin: 0 5px;'><img src='cid:image2' \n" + 
    		"								style='width: 100px' ></a>\n" + 
    		"								 <a href='#' style='margin: 0 5px;' ><img src='cid:image3' style='width: 100px;'></a> \n" + 
    		"							</div>\n" + 
    		"                        </td>\n" + 
    		"					</tr>\n" + 
    		"					<tr>\n" + 
    		"						<td style='color: gray; font-size: 14px;' >\n" + 
    		"							<p>Need help getting started?</p>\n" + 
    		"							<p>Check out <b>Team sport</b>  <a href='#'>Help Document</a></p>\n" + 
    		"							<p>Meet us on social network</p>\n" + 
    		"							<div style='margin-top:20px;' >\n" + 
    		"								<a href='#' ><img style='width: 30px;' src='cid:image4'></a>\n" + 
    		"								<a href='#' ><img style='width: 30px;' src='cid:image5'></a>\n" + 
    		"								<a href='#' ><img style='width: 30px;' src='cid:image6' ></a>\n" + 
    		"							</div>\n" + 
    		"						</td>\n" + 
    		"					</tr>\n" + 
    		"				</table>\n" + 
    		"			</td>\n" + 
    		"		</tr>\n" + 
    		"	</table>\n" + 
    		"</body>\n" + 
    		"</html>";
	
	@Autowired
	Environment evn;
	
	public void sendMailActiveAccount(String email, String linkAccess) {
		String subj = "Team Sport Notification";
		String strBody = STR_HEADER + 
				"		<tr>\n" + 
				"            <td style=\"font-size: 30 px; text-align: center;\">\n" + 
				"                <b>Please verify your email</b> \n" + 
				"				<p td style=\"color: black; font-size: 17px;\" >\n" + 
				"                Thanks for sign in up! You're almost there!<br> <br>\n" + 
				"                Just click the button bellow to complete your registration</p>\n" + 
				"			</td>\n" + 
				"			\n" + 
				"        </tr>\n" + 
				"		<tr>\n" + 
				"		<tr>\n" + 
				"			<td>\n" + 
				"				<table border=\"0\" width=\"100%\" style=\"border-radius: 5px;text-align: center;\">\n" + 
				"					<tr>\n" + 
				"                        <td style=\"text-align:center;\">\n" + 
				"                            <br>\n" + 
				"							<a href=\""+linkAccess+"\" style=\"border-radius: 5px;padding: 10px 40px 10px 40px;background-color: #DC3642;font-size: 17px;color: white;\"> Verify your account</a> <br> <br>\n" + 
				"						</td>\n" + 
				"					</tr>\n" + 
				"					\n" + 
				"					<tr>\n" + 
        		"						<td style='background-color:white;height:100px;font-size:15px;color: #8D8C8C;text-align: center;'>\n" + 
        		"                            <div style='margin-left: 20px;margin-right: 20px;'>\n" + 
        		"                                <P>Make sure to install <b>Team Sport </b>app on your device to access the team that is share with you</P>\n" + 
        		"							</div>\n" + STR_FOOTER;
		
        try {
        	 // inline images
            Map<String, String> inlineImages = attachImagesInContentMail();
	        send(email, subj, strBody, inlineImages);
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
	}
	
	public void sendMailInviteJoinTeam(String email, String[] mailContent)  
	{
        String subject = "Team Sport Notification";
        String body = STR_HEADER + 
        		"        <tr>\n" + 
        		"            <td style='font-size: 20px; text-align: center;'>\n" + 
        		"                <b>"+mailContent[2]+" </b><a> invited you to join in </a> <b>"+mailContent[0]+"</b>\n" + 
        		"                \n" + 
        		"            </td>\n" + 
        		"        </tr>\n" + 
        		"		<tr>\n" + 
        		"			<td>\n" + 
        		"				<table border='0' width='100%' style='border-radius: 5px;text-align: center;'>\n" + 
        		"					<tr>\n" + 
        		"                        <td style='background-color:#303A3C; height:100px; font-size:15px; color:#fff;'>\n" + 
        		"                            <div style=' font-size: 16px; margin-left: 20px; margin-right: 20px; text-align: left !important;'>\n" + 
        		"                                <p style='color:#fff;' >Hi</p>\n" + 
        		"                                <p><b>"+mailContent[0]+"</b> is a "+mailContent[1]+" team.</p>\n" + 
        		"                                <p><b>"+mailContent[2]+"</b> has invited you to join our team as player,  so that you can help them win all of opponents in future.</p>\n" + 
        		"                                <p>All you have to do is click the button below to join the team</p>\n" + 
        		"                                <p></p>Hope you have fun with them.</p>\n" + 
        		"                                <p><b>Team Sport</b></p>\n" + 
        		"                            </div>\n" + 
        		"                        </td>\n" + 
        		"                        <br>\n" + 
        		"                    </tr>\n" + 
        		"					<tr>\n" + 
        		"                        <td style='text-align:center;'>\n" + 
        		"                            <br>\n" + 
        		"							<a href='sportapp://openapp?teamId="+mailContent[3]+"' style='border-radius: 5px;padding: 10px 40px 10px 40px;background-color: #DC3642;font-size: 16px;color: white;'> Open in app</a> <br> <br>\n" + 
        		"						</td>\n" + 
        		"					</tr>\n" + 
        		"					<tr>\n" + 
        		"						<td style='background-color:white;height:100px;font-size:15px;color: #8D8C8C;text-align: center;'>\n" + 
        		"                            <div style='margin-left: 20px;margin-right: 20px;'>\n" + 
        		"                                <P>Make sure to install <b>Team Sport </b>app on your device to access the team that is share with you</P>\n" + 
        		"							</div>\n" + STR_FOOTER;
            
        
        try {
        	// inline images
            Map<String, String> inlineImages = attachImagesInContentMail();
            send(email,subject, body.toString(), inlineImages);
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
	}
	
	public void sendMailForgotPass(String email, String OTP)  
	{
        String subject = "Team Sport Notification";
        String body = STR_HEADER + 
        		"		<tr>\n" + 
        		"            <td style=\"font-size: 30 px; text-align: center;\">\n" + 
        		"				<b>OTP code is </b> \n" + 
        		"				<h1>"+OTP+"</h1>\n" + 
        		"				<p td style=\"color: black; font-size: 17px;\" >\n" + 
        		"				Here your OTP verification change password code.<br>\n" + 
        		"				It will expire in 5 minutes</p>\n" + 
        		"            </td>\n" + 
        		"        </tr>\n" + 
        		"		<tr>\n" + 
        		"		<tr>\n" + 
        		"			<td>\n" + 
        		"				<table border=\"0\" width=\"100%\" style=\"border-radius: 5px;text-align: center;\">\n" + 
        		"					\n" + 
        		"					\n" + 
        		"					<tr>\n" + 
        		"						<td style='background-color:white;height:100px;font-size:15px;color: #8D8C8C;text-align: center;'>\n" + 
        		"                            <div style='margin-left: 20px;margin-right: 20px;'>\n" + 
        		"                                <P>Make sure to install <b>Team Sport </b>app on your device to access the team that is share with you</P>\n" + 
        		"							</div>\n" + STR_FOOTER;
        try {
        	 // inline images
            Map<String, String> inlineImages = attachImagesInContentMail();
            send(email,subject, body.toString(), inlineImages);
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
	}
	
	public void sendMailInviteJoinAppAndEventKendo(String email, String[] mailContent)  
	{
        String subject = "Invite Join TeamSport App";
        String body = STR_HEADER + 
        		"        <tr>\n" + 
        		"            <td style=\"font-size: 30 px; text-align: center;\">\n" + 
        		"                <b>"+ mailContent[0] +" </b> <a>invited you to join in </a> <b>TeamSport.</b>\n" + 
        		"                \n" + 
        		"            </td>\n" + 
        		"        </tr>\n" + 
        		"		<tr>\n" + 
        		"			<td>\n" + 
        		"				<table border=\"0\" width=\"100%\" style=\"border-radius: 5px;text-align: center;\">\n" + 
        		"					<tr>\n" + 
        		"                        <td style=\"background-color:#303A3C;height:100px;font-size:15px;color:#fff;\">\n" + 
        		"                            <div style=\"margin-left: 20px;margin-right: 20px; text-align: left !important;\">\n" + 
        		"                                <p>Hi, </p>\n" + 
        		"                                <p>Admin of the team <b>"+ mailContent[1] +" </b> has invited you and your team to join in to TeamSport. And your team is invited to join a kendo event, it will be happy if you join with us.\n" + 
        		"								</p>\n" + 
        		"								<p>TeamSport is a mobile application help manage Sport team.\n" + 
        		"									<br>It support almost all the famous sport in the world\n" + 
        		"									<br>For more infomation about us. Please meet our <a href=\"#\" style=\"color: #FEFF52; text-decoration: none;\" >Landding page</a>\n" + 
        		"								</p>\n" + 
        		"								<tr>	\n" + 
        		"									<td style=\"background-color:white;height:100px;font-size:15px;color: #8D8C8C;text-align: center;\">\n" + 
        		"										<div style=\"margin-left: 20px;margin-right: 20px; font-size: 25px; \">\n" + 
        		"											<p>Install <b>Team Sport </b>for enjoy the convenience of this app</p>\n" + 
        		"										</div>\n" + STR_FOOTER;
        try {
        	 // inline images
            Map<String, String> inlineImages = attachImagesInContentMail();
            send(email,subject, body.toString(), inlineImages);
        } catch (Exception ex) {
            System.out.println("Could not send email.");
            ex.printStackTrace();
        }
	}
	
	public Map<String, String> attachImagesInContentMail() throws IOException {
		Map<String, String> inlineImages = new HashMap<String, String>();
		
		File image1 = new ClassPathResource("default_logo.png").getFile();
		File image2 = new ClassPathResource("g_play.png").getFile();
		File image3 = new ClassPathResource("a_store.png").getFile();
		File image4 = new ClassPathResource("f.png").getFile();
		File image5 = new ClassPathResource("t.png").getFile();
		File image6 = new ClassPathResource("i.png").getFile();

		inlineImages.put("image1", image1.getAbsolutePath());
		inlineImages.put("image2", image2.getAbsolutePath());
		inlineImages.put("image3", image3.getAbsolutePath());
		inlineImages.put("image4", image4.getAbsolutePath());
		inlineImages.put("image5", image5.getAbsolutePath());
		inlineImages.put("image6", image6.getAbsolutePath());
		
		return inlineImages;
	}
	        
	public void send(String toAddress,
            String subject, String htmlBody,
            Map<String, String> mapInlineImages)
                throws AddressException, MessagingException {
        // sets SMTP server properties
		Properties properties = new Properties();
		properties.put("mail.smtp.host", evn.getProperty("spring.mail.host").toString());
        properties.put("mail.smtp.port", evn.getProperty("spring.mail.port").toString());
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.user", evn.getProperty("spring.mail.username").toString());
        properties.put("mail.password", evn.getProperty("spring.mail.password").toString());
		
        // creates a new session with an authenticator
        Authenticator auth = new Authenticator() {
            public PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(evn.getProperty("spring.mail.username").toString(),evn.getProperty("spring.mail.password").toString());
            }
        };
        Session session = Session.getInstance(properties, auth);
 
        // creates a new e-mail message
        Message msg = new MimeMessage(session);
 
        msg.setFrom(new InternetAddress(properties.getProperty("mail.user")));
        InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
        msg.setRecipients(Message.RecipientType.TO, toAddresses);
        msg.setSubject(subject);
        msg.setSentDate(new Date());
 
        // creates message part
        MimeBodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setContent(htmlBody, "text/html");
 
        // creates multi-part
        Multipart multipart = new MimeMultipart();
        multipart.addBodyPart(messageBodyPart);
 
        // adds inline image attachments
        if (mapInlineImages != null && mapInlineImages.size() > 0) {
            Set<String> setImageID = mapInlineImages.keySet();
             
            for (String contentId : setImageID) {
                MimeBodyPart imagePart = new MimeBodyPart();
                imagePart.setHeader("Content-ID", "<" + contentId + ">");
                imagePart.setDisposition(MimeBodyPart.INLINE);
                 
                String imageFilePath = mapInlineImages.get(contentId);
                try {
                    imagePart.attachFile(imageFilePath);
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
 
                multipart.addBodyPart(imagePart);
            }
        }
        msg.setContent(multipart);
        Transport.send(msg);
    }
}