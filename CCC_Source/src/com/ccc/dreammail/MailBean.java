package com.ccc.dreammail;

import java.io.File;
import java.util.List;
import java.util.Map;


/**
 * @author RedSword
 * @date 2012-02-07 23:00:57
 */
public class MailBean {
    private String sendMailUserName;
    private String sendMailPassword;
    private String mailHost="smtp.163.com";
    private List<String> toMailUserName;
    private String subject;
    private String content;
    private Map<String, File> files=null;
    /**
     * @return the sendMailUserName
     */
    public String getSendMailUserName() {
        return this.sendMailUserName;
    }
    /**
     * @param sendMailUserName the sendMailUserName to set
     */
    public void setSendMailUserName(String sendMailUserName) {
        this.sendMailUserName = sendMailUserName;
    }
    /**
     * @return the sendMailPassword
     */
    public String getSendMailPassword() {
        return this.sendMailPassword;
    }
    /**
     * @param sendMailPassword the sendMailPassword to set
     */
    public void setSendMailPassword(String sendMailPassword) {
        this.sendMailPassword = sendMailPassword;
    }
    /**
     * @return the mailHost
     */
    public String getMailHost() {
        return this.mailHost;
    }
    /**
     * @param mailHost the mailHost to set
     */
    public void setMailHost(String mailHost) {
        this.mailHost = mailHost;
    }
    /**
     * @return the toMailUserName
     */
    public List<String> getToMailUserName() {
        return this.toMailUserName;
    }
    /**
     * @param toMailUserName the toMailUserName to set
     */
    public void setToMailUserName(List<String> toMailUserName) {
        this.toMailUserName = toMailUserName;
    }
    /**
     * @return the subject
     */
    public String getSubject() {
        return this.subject;
    }
    /**
     * @param subject the subject to set
     */
    public void setSubject(String subject) {
        this.subject = subject;
    }
    /**
     * @return the content
     */
    public String getContent() {
        return this.content;
    }
    /**
     * @param content the content to set
     */
    public void setContent(String content) {
        this.content = content;
    }
    /**
     * @return the files
     */
    public Map<String, File> getFiles() {
        return this.files;
    }
    /**
     * @param files the files to set
     */
    public void setFiles(Map<String, File> files) {
        this.files = files;
    }
    
    

}
