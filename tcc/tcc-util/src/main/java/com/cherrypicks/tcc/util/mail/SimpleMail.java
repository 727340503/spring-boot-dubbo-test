package com.cherrypicks.tcc.util.mail;

import java.io.File;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Message.RecipientType;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.cherrypicks.tcc.util.DateUtil;

public final class SimpleMail {

    private static final Logger logger = LoggerFactory.getLogger(SimpleMail.class);

    private SimpleMail() {
    }

    // 以文本格式发送邮件
    public static boolean sendTextMail(final MailInfo mailInfo) {
        // 判断是否需要身份认证
        MyAuthenticator authenticator = null;
        final Properties properties = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());
        }

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        final Session sendMailSession = Session.getDefaultInstance(properties, authenticator);
       // final Session sendMailSession = Session.getDefaultInstance(properties, null);
        try {
            final Message mailMessage = new MimeMessage(sendMailSession); // 根据session创建一个邮件消息
            String nick = "";
            if(StringUtils.isNotBlank(mailInfo.getFromNick())){
            	nick = javax.mail.internet.MimeUtility.encodeText(mailInfo.getFromNick()); 
            }
            final Address from = new InternetAddress(nick + mailInfo.getFromAddress()); // 创建邮件发送者地址
            mailMessage.setFrom(from); // 设置邮件消息的发送者
            final Address[] tos = new Address[mailInfo.getToAddress().length];
            for (int i = 0; i < mailInfo.getToAddress().length; i++) {
                final Address to = new InternetAddress(mailInfo.getToAddress()[i]); // 创建邮件的接收者地址
                tos[i] = to;
            }

            mailMessage.setRecipients(Message.RecipientType.TO, tos); // 设置邮件消息的接收者
            mailMessage.setSubject(mailInfo.getSubject()); // 设置邮件消息的主题
            mailMessage.setSentDate(DateUtil.getCurrentDate()); // 设置邮件消息发送的时间
            // mailMessage.setText(mailInfo.getContent());//设置邮件消息的主要内容

            // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
            final Multipart mainPart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart(); // 创建一个包含附件内容的MimeBodyPart
            // 设置HTML内容
            messageBodyPart.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(messageBodyPart);

            // 存在附件
            final String[] filePaths = mailInfo.getAttachFileNames();
            if (filePaths != null && filePaths.length > 0) {
                for (final String filePath : filePaths) {
                    messageBodyPart = new MimeBodyPart();
                    final File file = new File(filePath);
                    if (file.exists()) {// 附件存在磁盘中
                        final FileDataSource fds = new FileDataSource(file); // 得到数据源
                        messageBodyPart.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
                        messageBodyPart.setFileName(file.getName()); // 得到文件名同样至入BodyPart
                        mainPart.addBodyPart(messageBodyPart);
                    }
                }
            }

            // 将MimeMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
//            Transport.send(mailMessage); // 发送邮件

            // 发送邮件
            final Transport transport = sendMailSession.getTransport("smtp");
            transport.connect(mailInfo.getMailServerHost(), mailInfo.getUsername(), mailInfo.getPassword());
            transport.sendMessage(mailMessage, mailMessage.getRecipients(RecipientType.TO));
            transport.close();

            return true;
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }

    // 以HTML格式发送邮件
    public static boolean sendHtmlMail(final MailInfo mailInfo) {
        // 判断是否需要身份认证
        final Properties properties = mailInfo.getProperties();
        MyAuthenticator authenticator = null;
        if (mailInfo.isValidate()) {
            // 如果需要身份认证，则创建一个密码验证器
            authenticator = new MyAuthenticator(mailInfo.getUsername(), mailInfo.getPassword());
        }

        // 根据邮件会话属性和密码验证器构造一个发送邮件的session
        final Session sendMailSession = Session.getDefaultInstance(properties, authenticator);
        //final Session sendMailSession = Session.getDefaultInstance(properties, null);
        try {
            final Message mailMessage = new MimeMessage(sendMailSession); // 根据session创建一个邮件消息
            String nick = "";
            if(StringUtils.isNotBlank(mailInfo.getFromNick())){
            	nick = javax.mail.internet.MimeUtility.encodeText(mailInfo.getFromNick()); 
            }
            logger.info("---------nick------" + nick);
            final Address from = new InternetAddress(nick + mailInfo.getFromAddress()); // 创建邮件发送者地址
            mailMessage.setFrom(from); // 设置邮件消息的发送者

            final Address[] tos = new Address[mailInfo.getToAddress().length];
            for (int i = 0; i < mailInfo.getToAddress().length; i++) {
                final Address to = new InternetAddress(mailInfo.getToAddress()[i]); // 创建邮件的接收者地址
                tos[i] = to;
            }

            mailMessage.setRecipients(Message.RecipientType.TO, tos); // 设置邮件消息的接收者
            mailMessage.setSubject(mailInfo.getSubject()); // 设置邮件消息的主题
            mailMessage.setSentDate(DateUtil.getCurrentDate()); // 设置邮件消息发送的时间

            // MimeMultipart类是一个容器类，包含MimeBodyPart类型的对象
            final Multipart mainPart = new MimeMultipart();
            MimeBodyPart messageBodyPart = new MimeBodyPart(); // 创建一个包含HTML内容的MimeBodyPart
            // 设置HTML内容
            messageBodyPart.setContent(mailInfo.getContent(), "text/html; charset=utf-8");
            mainPart.addBodyPart(messageBodyPart);

            // 存在附件
            final String[] filePaths = mailInfo.getAttachFileNames();
            if (filePaths != null && filePaths.length > 0) {
                for (final String filePath : filePaths) {
                    messageBodyPart = new MimeBodyPart();
                    final File file = new File(filePath);
                    if (file.exists()) { // 附件存在磁盘中
                        final FileDataSource fds = new FileDataSource(file); // 得到数据源
                        messageBodyPart.setDataHandler(new DataHandler(fds)); // 得到附件本身并至入BodyPart
                        messageBodyPart.setFileName(file.getName()); // 得到文件名同样至入BodyPart
                        mainPart.addBodyPart(messageBodyPart);
                    }
                }
            }

            // 将MimeMultipart对象设置为邮件内容
            mailMessage.setContent(mainPart);
//            Transport.send(mailMessage); // 发送邮件

            // 发送邮件
            final Transport transport = sendMailSession.getTransport("smtp");
            transport.connect(mailInfo.getMailServerHost(), mailInfo.getUsername(), mailInfo.getPassword());
            transport.sendMessage(mailMessage, mailMessage.getRecipients(RecipientType.TO));
            transport.close();

            return true;
        } catch (final Exception e) {
            logger.error(e.getMessage(), e);
        }
        return false;
    }
}
