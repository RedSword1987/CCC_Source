package com.ccc.dreammail;

import java.io.File;
import java.util.Date;
import java.util.Properties;
import java.util.Map.Entry;

import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

/**
 * @author
 * @date 2012-02-07 22:59:46
 */

public final class MailDream {
    public static boolean sendMail(MailBean mailBean) {
        Properties props = new Properties();
        props.put("mail.smtp.host", "");
        props.put("mail.smtp.auth", "true");
        Session session = Session.getInstance(props);
        MimeMessage mimeMessage = new MimeMessage(session);
        try {
            mimeMessage.setFrom(new InternetAddress(mailBean.getSendMailUserName()));
            InternetAddress[] to_internetAddress = new InternetAddress[mailBean.getToMailUserName().size()];
            int index = 0;
            for (String toMail : mailBean.getToMailUserName()) {
                to_internetAddress[index++] = new InternetAddress(toMail);
            }
            mimeMessage.setRecipients(Message.RecipientType.TO, to_internetAddress);
            mimeMessage.setSubject(mailBean.getSubject());
            mimeMessage.setText(mailBean.getContent());
            mimeMessage.setSentDate(new Date());
            if (mailBean.getFiles() != null && mailBean.getFiles().size() != 0) {
                Multipart m = new MimeMultipart();
                MimeBodyPart mbpContent = new MimeBodyPart();
                mbpContent.setContent(mailBean.getContent(), "text/plain;charset=UTF-8");
                m.addBodyPart(mbpContent);
                for (Entry<String, File> file : mailBean.getFiles().entrySet()) {
                    MimeBodyPart body = new MimeBodyPart();
                    FileDataSource source = new FileDataSource(file.getValue());
                    body.setDataHandler(new DataHandler(source));
                    body.setFileName(MimeUtility.encodeText(file.getKey()));
                    m.addBodyPart(body);

                }
                mimeMessage.setContent(m);
            }
            mimeMessage.saveChanges();
            Transport transport = session.getTransport("smtp");
            transport.connect(mailBean.getMailHost(), mailBean.getSendMailUserName(), mailBean.getSendMailPassword());
            transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
            transport.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

}
