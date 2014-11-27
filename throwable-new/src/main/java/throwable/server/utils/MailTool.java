package throwable.server.utils;

import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;


/**
 * @author WaterHsu@xiu8.com
 * @version 2014年11月19日
 */
public class MailTool {

	public static class Email{
		/**
		 * 发送邮件的服务器的地址
		 */
		private String mailServerHost = "smtp.163.com";
		/**
		 * 发送邮件的服务器端口
		 */
		private String mailServerPort = "25";
		/**
		 * 邮件发送者地址
		 */
		private String fromAddress = "hsupangfei1989@163.com";
		/**
		 * 邮箱用户名
		 */
		private String username = "hsupangfei1989@163.com";
		/**
		 * 邮箱密码
		 */
		private String password = "china374256336qq";
		/**
		 * 用于读取配置文件
		 */
		private Properties properties;
		/**
		 * 是否需要身份验证
		 */
		private boolean validate = true;
		/**
		 * 收件人地址，可以同时发给多个人，多个地址用;隔开
		 */
		private String toAddress;
		/**
		 * 邮件抄送地址   多个地址用;隔开
		 */
		private String ccAddress;
		/**
		 * 邮件密送地址  多个地址用;隔开
		 */
		private String bccAddress;
		/**
		 * 邮件主题
		 */
		private String subject;
		/**
		 * 邮件内容
		 */
		private String content;
		/**
		 * 邮件附件
		 */
		private String[] attachFileName;
		
		public Email(){
			
		}
		public Email(String mailServerHost, String mailServerPort, String fromAddress, String username, String password, 
				Properties properties, boolean validate,String toAddress, String ccAddress, String bccAddress,
				String subject, String content, String[] attachFileName){
			this.mailServerHost = mailServerHost;
			this.mailServerPort = mailServerPort;
			this.fromAddress = fromAddress;
			this.username = username;
			this.password = password;
			this.properties = properties;
			this.validate = validate;
			this.toAddress = toAddress;
			this.ccAddress = ccAddress;
			this.bccAddress = bccAddress;
			this.subject = subject;
			this.content = content;
			this.attachFileName = attachFileName;
		}
		
		public String getMailServerHost() {
			return mailServerHost;
		}

		public void setMailServerHost(String mailServerHost) {
			this.mailServerHost = mailServerHost;
		}

		public String getMailServerPort() {
			return mailServerPort;
		}

		public void setMailServerPort(String mailServerPort) {
			this.mailServerPort = mailServerPort;
		}

		public String getFromAddress() {
			return fromAddress;
		}

		public void setFromAddress(String fromAddress) {
			this.fromAddress = fromAddress;
		}

		public String getUsername() {
			return username;
		}

		public void setUsername(String username) {
			this.username = username;
		}

		public String getPassword() {
			return password;
		}

		public void setPassword(String password) {
			this.password = password;
		}

		public String getToAddress() {
			return toAddress;
		}

		public void setToAddress(String toAddress) {
			this.toAddress = toAddress;
		}

		public String getCcAddress() {
			return ccAddress;
		}

		public void setCcAddress(String ccAddress) {
			this.ccAddress = ccAddress;
		}

		public String getBccAddress() {
			return bccAddress;
		}

		public void setBccAddress(String bccAddress) {
			this.bccAddress = bccAddress;
		}

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getContent() {
			return content;
		}

		public void setContent(String content) {
			this.content = content;
		}

		public String[] getAttachFileName() {
			return attachFileName;
		}

		public void setAttachFileName(String[] attachFileName) {
			this.attachFileName = attachFileName;
		}

		public Properties getProperties() {
			properties = new Properties();
			properties.put("mail.smtp.host", this.mailServerHost);
			properties.put("mail.smtp.port", this.mailServerPort);
			properties.put("mail.smtp.auth", validate ? "true" : "false");
			properties.put("mail.smtp.socketFactory.port", this.mailServerPort);
			properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
			return properties;
		}

		public void setProperties(Properties properties) {
			this.properties = properties;
		}

		public boolean isValidate() {
			return validate;
		}

		public void setValidate(boolean validate) {
			this.validate = validate;
		}	
		
		/**
		 * 将所有收件人地址取出放入数组中
		 * @return
		 */
		public String[] getToAddresses(){
			if(null == this.toAddress || this.toAddress.trim().length() <= 0){
				return null;
			}
			String address = this.toAddress.trim();
			address.replaceAll("；", ";");
			address.replaceAll(" ", ";");
			address.replaceAll("|", ";");
			address.replace("，", ";");
			return address.split(";");
		}
		/**
		 * 将所有抄送人地址取出放入数组中
		 * @return
		 */
		public String[] getCcAddresses(){
			if(null == this.ccAddress || this.ccAddress.trim().length() <= 0){
				return null;
			}
			String address = this.ccAddress.trim();
			address.replaceAll("；", ";");
			address.replaceAll(" ", ";");
			address.replaceAll("|", ";");
			address.replace("，", ";");
			return address.split(";");
		}
		/**
		 * 将所有密送人地址取出放入数组中
		 * @return
		 */
		public String[] getBccAddresses(){
			if(null == this.bccAddress || this.bccAddress.trim().length() <= 0){
				return null;
			}
			String address = this.bccAddress.trim();
			address.replaceAll("；", ";");
			address.replaceAll(" ", ";");
			address.replaceAll("|", ";");
			address.replace("，", ";");
			
			return address.split(";");
		}
	}
	
	/**
	 * 同步发送邮件
	 * @param email
	 */
	public void sendEmailSyn(Email email){
		try{
			send(email);
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	/**
	 * 异步发送邮件
	 * @param email
	 */
	public void sendEmailAsyn(final Email email){
		new Thread(new Runnable() {
			@Override
			public void run() {
				try{
					send(email);
				}catch(Exception e){
					e.printStackTrace();
				}
			}
		}).start();
	}
	
	/**
	 * 具体发邮件的处理
	 * 
	 * @param email
	 * @throws Exception
	 */
	private static void send(final Email email) throws Exception{
		Session mailSession = Session.getDefaultInstance(email.getProperties(), new Authenticator() {
			@Override
			protected PasswordAuthentication getPasswordAuthentication() {
				// TODO Auto-generated method stub
				return new PasswordAuthentication(email.username, email.password);
			}
			
		});
		mailSession.setDebug(false);
		MimeMessage  message = new MimeMessage(mailSession);
		message.setSubject(email.getSubject());
		message.setFrom(new InternetAddress(email.getFromAddress()));
		/**
		 * 设置收件人
		 */
		message.setRecipients(Message.RecipientType.TO, getAllAddress(email.getToAddresses()));
		message.setRecipients(Message.RecipientType.CC, getAllAddress(email.getCcAddresses()));
		message.setRecipients(Message.RecipientType.BCC, getAllAddress(email.getBccAddresses()));
		MimeBodyPart messageBodyPart = new MimeBodyPart();
		messageBodyPart.setContent(email.getContent(), "text/html; charset=utf-8");
		Multipart multipart = new MimeMultipart();
		multipart.addBodyPart(messageBodyPart);
		if(null != email.getAttachFileName() && email.getAttachFileName().length > 0){
			for(String attachFile : email.getAttachFileName()){
				MimeBodyPart fileBodyPart = new MimeBodyPart();
				DataSource source = new FileDataSource(attachFile);
				fileBodyPart.setDataHandler(new DataHandler(source));
				String fileName = new String(attachFile.getBytes("UTF-8"), "UTF-8");
				int pos = fileName.lastIndexOf("//");
				fileName = fileName.substring(pos + 1); 
				fileBodyPart.setFileName(fileName);
				multipart.addBodyPart(fileBodyPart);
			}		
		}
		message.setContent(multipart);
		Transport.send(message);
	}
	/**
	 * 将收件人地址由String转为InternetAddress类型
	 * @param strs
	 * @return
	 */
	private static InternetAddress[] getAllAddress(String[] strs){
		if(null == strs || strs.length <= 0){
			return null;
		}
		InternetAddress[] addrs = new InternetAddress[strs.length];
		for(int i = 0; i < strs.length; i++){
			try{
				addrs[i] = new InternetAddress(strs[i]);
			}catch(AddressException e){
				e.printStackTrace();
			}
		}
		return addrs;
	}
	
	public static void main(String[] args){
		MailTool.Email email = new MailTool.Email();
		email.setToAddress("374299447@qq.com");
		email.setSubject("Hello World");
		email.setContent("Nihaoma");
		MailTool mailTool = new MailTool();
		mailTool.sendEmailAsyn(email);
		System.out.println("发送完毕");
	}
}
