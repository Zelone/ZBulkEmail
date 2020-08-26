package carl;

import java.util.Date;
import java.util.Properties;

import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Store;
import javax.mail.internet.MimeMultipart;
import javax.mail.Session;
import javax.mail.Message;
import javax.mail.Folder;

import com.sun.mail.pop3.POP3Store;
//Testing purposes
import javax.swing.JOptionPane;
//Exceptions
import java.security.NoSuchAlgorithmException;
import java.security.InvalidKeyException;

import javax.mail.NoSuchProviderException;
import javax.mail.MessagingException;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
/**
 * Testing Mail recieveing Code.
 */
public class ReceiveMail
{
    

    public static void receiveEmail(String pop3Host, String storeType,
            String user, String password)
    {
        try {
            //1) get the session object  
            Properties properties = new Properties();
            properties.put("mail.pop3.host", pop3Host);
            Session emailSession = Session.getDefaultInstance(properties);

            //2) create the POP3 store object and connect with the pop server  
            POP3Store emailStore = (POP3Store) emailSession.getStore(storeType);
            emailStore.connect(user, password);

            //3) create the folder object and open it  
            Folder emailFolder = emailStore.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);
            boolean want = true;
            //4) retrieve the messages from the folder in an array and print it  
            Message[] messages = emailFolder.getMessages();
            String a = "";
            for (int i = 0; i < messages.length; i++) {
                int j = JOptionPane.showConfirmDialog(null, "Do u wish to see message no " + (i + 1));
                want = j == JOptionPane.YES_OPTION;

                if (want) {
                    Message message = messages[i];
                    System.out.println("---------------------------------");
                    a = a + "\n---------------------------------";
                    System.out.println("Email Number " + (i + 1));
                    a = a + "\n" + "Email Number " + (i + 1);
                    System.out.println("Subject: " + message.getSubject());
                    a = a + "\n" + "Subject: " + message.getSubject();
                    for (Address s : message.getFrom()) {
                        System.out.println("From: " + s);
                        a = a + "\n" + "From: " + s;
                    }
                    System.out.println("Text: " + message.getContent().toString());
                    a = a + "\n" + "Text: " + message.getContent().toString();
                }
            }

            //5) close the store and folder objects  
            emailFolder.close(false);
            emailStore.close();
            /*basiccodes.BasicCodes b = new basiccodes.BasicCodes();
            b.close();
            b.Save(a, "E:\\hi1.txt");*/
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static String toString(Address[] adds)
    {
        String output = "";
        for (Address add : adds) {
            output = output + add.toString() + "; ";
        }

        return output;
    }

    public static void main(String[] args)
    {
        /*
         String host = "smtp.gmail.com";//change accordingly
         String mailStoreType = "pop3";
         String username= "zelonetd@gmail.com";
         String password= ""+JOptionPane.showInputDialog("Password of"+username);//change accordingly
         
         receiveEmail(host, mailStoreType, username, password);*/
        String host = "pop.gmail.com";// change accordingly
        String mailStoreType = "pop3";
        String username = "user@zelone.com";// change accordingly
        String password = "Passabc1234";// change accordingly
        check(host, mailStoreType, username, password);
        read();
    }

    /**
     * @param host
     * @param password
     * @param storeType
     * @param user
     */
    public static void check(final String host, String storeType, final String user, final String password)
    {
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "995");
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);

            //create the folder object and open it
            Folder emailFolder = store.getFolder("Inbox");

            emailFolder.open(Folder.READ_ONLY);

            // retrieve the messages from the folder in an array and print it
            Message messages = emailFolder.getMessage(emailFolder.getMessageCount());//0, emailFolder.getMessageCount());
            //System.out.println("messages.length---" + messages.length);

            for (int i = 0, n = 1; i < n; i++) {
                Message message = messages;

                if (JOptionPane.showConfirmDialog(null, "Do u wish to see this llll" + i) == JOptionPane.YES_OPTION) {
                    Object obj = message.getContent();

                    System.out.println("---------------------------------");
                    System.out.println("Email Number " + (i + 1));
                    System.out.println("Subject: " + message.getSubject());
                    System.out.println("From: " + message.getFrom()[0]);
                    System.out.println("To: " + toString(message.getAllRecipients()));

                    Date d = message.getReceivedDate();

                    System.out.println("Received Date:" + ((d == null) ? "null" : "" + d.toString()));

                    if (obj instanceof Multipart) {

                        Multipart mp = (Multipart) obj;
                        BodyPart bp = mp.getBodyPart(0);
                        System.out.println("Text: " + bp.getContent().toString());

                    } else if (obj instanceof MimeMultipart) {
                        MimeMultipart mmp = (MimeMultipart) obj;
                        int count = mmp.getCount();
                        for (int ii = 0; ii < count; ++ii) {
                            BodyPart bp = mmp.getBodyPart(ii);
                            System.out.println("Text" + ii + ":" + bp.getContent().toString());

                        }
                    } else if (obj instanceof String) {
                        System.out.println("Text:" + obj.toString());
                    }
                }
            }

            //close the store and folder objects
            emailFolder.close(false);
            store.close();

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void read()
    {
        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imap");
        MailID m=new MailID(9);
        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imap");
            store.connect("mail.google.com", m.getUser(), m.getPassword());

            Folder sent = store.getFolder("[Gmail]/Sent Mail");
            int totalSentMails = sent.getMessageCount();

            sent.open(Folder.READ_ONLY);

            Message sentmesage = sent.getMessage(totalSentMails);
            System.out.println("---------------------------------");
            System.out.println("Email Number " + (1));
            System.out.println("Subject: " + sentmesage.getSubject());
            System.out.println("From: " + sentmesage.getFrom()[0]);
            System.out.println("To: " + toString(sentmesage.getAllRecipients()));

        } catch (NoSuchProviderException e) {
            e.printStackTrace();
            System.exit(1);
        } catch (MessagingException ex) {
            ex.printStackTrace();
        }
    }
}

class MailID
{

    private int port;
    private String host;
    private String user;
    private String password;
    private String protocol = "pop3s";

    public void setGoogle()
    {
        this.port = 995;
        this.host = "pop.gmail.com";
        //   this.user="zelonetd@gmail.com";
//    this.password="Passabc1234";
        this.protocol = "pop3s";
    }

    private void setUser()
    {
        this.user = "cacrlabsrm@gmail.com";
        this.password = "Passabc1234";
        setGoogle();
    }

    public void setGoogle(String user, String password) throws Exception
    {
        setGoogle();
        setUserPassword(user, password);
    }

    public void setHost(String host)
    {
        this.host = host;
    }

    public String getHost()
    {
        return host;
    }

    public String getPassword()
    {
        return password;
    }

    public int getPort()
    {
        return port;
    }

    public String getProtocol()
    {
        return protocol;
    }

    public String getUser()
    {
        return user;
    }

    public void setPort(int port)
    {
        this.port = port;
    }

    public void setProtocol(String protocol)
    {
        this.protocol = protocol;
    }

    public void setUserPassword(String user, String password) throws Exception
    {
        this.user = user;
        this.password = new String(Key.enCrypt(password, Key.getKey("jj")));
    }
    String googlehost = "smtp.gmail.com";
    int SendPort = 465;

    public MailID(String ss)
    {/*
    Sending MAil
         */
        Properties props = new Properties();
        props.put("mail.smtp.host", googlehost);
        props.put("mail.smtp.socketFactory.port", "" + SendPort);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "" + SendPort);
        //get Session   
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
        {
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(user, password);
            }
        });
    }

    public MailID(int j)
    {
        /*
    IMAP Recieving Mail
         */

        Properties props = System.getProperties();
        props.setProperty("mail.store.protocol", "imap");

        try {
            Session session = Session.getDefaultInstance(props, null);
            Store store = session.getStore("imap");
            store.connect("mail.google.com", "carlabsrm@gmail.com", "Passabc1234");

        } catch (Exception e) {
        }
    }

    public MailID()
    {/*
        POP Recieving mail
         */
        try {

            //create properties field
            Properties properties = new Properties();

            properties.put("mail.pop3.host", host);
            properties.put("mail.pop3.port", "" + port);
            properties.put("mail.pop3.starttls.enable", "true");
            Session emailSession = Session.getDefaultInstance(properties);

            //create the POP3 store object and connect with the pop server
            Store store = emailSession.getStore(protocol);

            store.connect(host, user, password);

        } catch (Exception e) {
        }
    }

}

class Key
{

    private static final String Key = "Bar12345Bar12345"; // 128 bit key

    public Key()
    {

    }

    public static String getKey(String K) throws IllegalAccessException
    {
        if (K.equals("jj")) {
            return Key;
        } else {
            throw new IllegalAccessException("invalid try to get the Key");
        }
    }

    static byte[] enCrypt(String text, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException
    {
        java.security.Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, aesKey);
        byte[] encrypted = cipher.doFinal(text.getBytes());
        return encrypted;

    }

    static String deCrypt(byte[] encrypted, String key) throws NoSuchAlgorithmException, NoSuchPaddingException, IllegalBlockSizeException, InvalidKeyException, BadPaddingException
    {
        java.security.Key aesKey = new SecretKeySpec(key.getBytes(), "AES");
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, aesKey);
        return new String(cipher.doFinal(encrypted));
    }
}
