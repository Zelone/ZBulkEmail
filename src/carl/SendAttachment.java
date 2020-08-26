package carl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.*;
import javax.mail.internet.*;
import javax.swing.JOptionPane;
/**
 * Testing sending mail Code.
 */
class SendAttachment
{

    private static String user = "carlabsrm@gmail.com";
    private static String password = "Passabc1234";

    public static void main(String[] args)
    {
        //mail.setGoogleAll("info.carl.srm@gmail.com", password);

        //      sendHTML("carlabsrm@gmail.com", password, new String[]{"adityajyotipaul007@gmail.com"}, "Invitation for CARL ExploreML Workshop Beginner Track","ADITYA");
        /**/
        {
            ArrayList<String> to = new ArrayList<String>();
            int i = -1;
            FileInputStream fos;
            try {
                fos = new FileInputStream(new File("Book.id"));
                Scanner sc = new Scanner(fos);
                while (sc.hasNext()) {
                    to.add(sc.next().trim().toLowerCase());
                    i++;

                }
                sendMail(user,
                        password, to,
                        "Invitation for CARL ExploreML Workshop",
                        "CODEE", i + 1);

            } catch (FileNotFoundException ex) {
                Logger.getLogger(SendAttachment.class.getName()).log(Level.SEVERE, null, ex);
            }

        }/**/
    }
    private static final String googlehost = "smtp.gmail.com";

    public static void sss()
    {
        BodyPart bp = new MimeBodyPart();

    }

    public static void sendMail(String from, String password, ArrayList<String> to, String sub, String msgCode, int toN)
    {
        try { //String host = "";
            //Get properties object    
            Properties props = new Properties();
            props.put("mail.smtp.host", googlehost);
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            //get Session   
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(from, password);
                }
            });
            Object out = null;
            Store store = session.getStore("pop3s");

            store.connect(googlehost, from, password);
            //3) create the folder object and open it  
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            //int d = emailFolder.getMessageCount();
            int n = emailFolder.getMessageCount();
            //4) retrieve the messages from the folder in an array and print it  
            Message[] messages = emailFolder.getMessages(n - 6, n);

            for (int i = 0; i < messages.length; i++) {

                Message message = messages[i];
                if (message.getSubject().equals(msgCode)) {
                    out = (MimeMultipart) message.getContent();
                    System.out.println("Found Message. Going to be sent to:" + toN + " Mailing Ids");
                    break;
                }
                System.out.println(i + "next" + message.getSubject());

            }

            /* try{
  ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("j.g")));
  Object oh=ois.readObject();
  out=oh;
  }catch(Exception e){e.printStackTrace();
  }*/
            //compose message    
            if (out != null) {
                for (int i = 0; i < toN; ++i) {
                    try {

                        MimeMessage msg = new MimeMessage(session);
                        msg.setFrom(new InternetAddress(from));
                        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to.get(i)));
                        msg.setSubject(sub);
                        msg.setContent(out, "text/html");
                        Transport.send(msg);
                        System.out.println("message sent successfully " + i + "> " + to.get(i));
                        //else {
                        // System.out.println("" + to);
                        // msg.setText("WE are not able to connect to u right now we will be back.");
                        // }
//send message  

                        //5) close the store and folder objects  
                        //emailFolder.close(false);
                        //store.close();
                    } catch (MessagingException e) {
                        e.printStackTrace();
                        System.out.println("Not sent to :>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>> " + to.get(i));
                    }

                }
            } else {
                System.out.println("Resend the mail");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void sendHT(String from, String password, String to, String sub, String msgCode)
    {
        try { //String host = "";
            //Get properties object    
            Properties props = new Properties();
            props.put("mail.smtp.host", googlehost);
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            //get Session   
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
            {
                @Override
                protected PasswordAuthentication getPasswordAuthentication()
                {
                    return new PasswordAuthentication(from, password);
                }
            });
            Object out = null;
            Store store = session.getStore("pop3s");

            store.connect(googlehost, from, password);
            //3) create the folder object and open it  
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            //int d = emailFolder.getMessageCount();
            //4) retrieve the messages from the folder in an array and print it  
            Message[] messages = emailFolder.getMessages();

            for (int i = 0; i < messages.length; i++) {

                Message message = messages[i];
                if (message.getSubject().equals(msgCode)) {
                    out = (MimeMultipart) message.getContent();
                    System.out.println("Found Message" + out);
                    break;
                }
                System.out.println(i + "next" + message.getSubject());

            }
            /*         try{
                
               
               
               
               
            }catch(Exception e ){}
             */
 /* try{
  ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("j.g")));
  Object oh=ois.readObject();
  out=oh;
  }catch(Exception e){e.printStackTrace();
  }*/
            //compose message    
            try {

                MimeMessage msg = new MimeMessage(session);
                msg.setFrom(new InternetAddress(from));
                msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to));
                msg.setSubject(sub);
                if (out != null) {
                    msg.setContent(out, "text/html");
                    Transport.send(msg);
                    System.out.println("message sent successfully");
                } //else {
                // System.out.println("" + to);
                // msg.setText("WE are not able to connect to u right now we will be back.");
                // }
//send message  

                //5) close the store and folder objects  
                //emailFolder.close(false);
                //store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
                throw new RuntimeException(e);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static MimeMultipart receiveMessage(Session emailSession, String host,
            String user, String password, String subcode)
    {
        MimeMultipart out = null;
        try {
            //1) get the session object  

            //2) create the POP3 store object and connect with the pop server  
            Store store = emailSession.getStore("pop3s");

            store.connect(host, user, password);
            //3) create the folder object and open it  
            Folder emailFolder = store.getFolder("Inbox");
            emailFolder.open(Folder.READ_ONLY);
            int d = emailFolder.getMessageCount();
            //4) retrieve the messages from the folder in an array and print it  
            Message[] messages = emailFolder.getMessages(((d > 10) ? (d - 10) : 0), d);

            for (int i = 0; i < messages.length; i++) {

                Message message = messages[i];
                if (message.getSubject().equals(subcode)) {
                    out = (MimeMultipart) message.getContent();
                    System.out.println("Found Message" + out);
                    break;
                }
                System.out.println(i + "next");

            }

            //5) close the store and folder objects  
            emailFolder.close(false);
            store.close();
            // basiccodes.BasicCodes b = new basiccodes.BasicCodes();
            // b.close();
            // b.Save(a, "E:\\hi1.txt");
        } catch (NoSuchProviderException e) {
            e.printStackTrace();
        } catch (MessagingException e) {
            e.printStackTrace();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return out;
    }

    public static void sendH(String from, String password, String to, String sub, String msg)
    {
        String host = "";
        //Get properties object    
        Properties props = new Properties();
        props.put("mail.smtp.host", googlehost);
        props.put("mail.smtp.socketFactory.port", "465");
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.port", "465");
        //get Session   
        Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator()
        {
            @Override
            protected PasswordAuthentication getPasswordAuthentication()
            {
                return new PasswordAuthentication(from, password);
            }
        });

        //compose message    
        try {
            MimeMessage message = new MimeMessage(session);
            message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

            message.setSubject(sub);
//            message.setContent(null);
            message.setContent(msg, "text/html");
            //send message  
            Transport.send(message);
            System.out.println("message sent successfully");
        } catch (MessagingException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }

    }

    String DD()
    {
        int t = 90876;
        while (true) {
            t = t % 100;
            t = (int) (t + (Math.random() * 10000));
            String to = "carabsrm@gmail.com";//change accordingly  
            final String user = this.user;//change accordingly  
            final String password = this.password;//change accordingly
            final String host = "smtp.gmail.com";//"smtp.gmail.com""smtp.zelone.com";  //for gmail

            /*send(user, password, to, "Code Pass", "Tried to " + t + " create it so here it is the second try.. \n " + JOptionPane.showInputDialog("type here")
                    + "\nSENT FROM APPLICATION CREATED BY JITESH");
             */
            if (t == Integer.parseInt(JOptionPane.showInputDialog("Enter the code"))) {
                System.out.println("TRUE" + t);
                break;
            } else {
                System.err.println("FALSE" + t);
                System.out.println(t);
            }
        }
        return null;
    }
    /*
    //1) get the session object
      Properties properties = System.getProperties();
      //     properties.setProperty("mail.smtp.host", "smtp.gmail.com");
      properties.put("mail.smtp.auth", "true");
      properties.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
      properties.put("mail.smtp.socketFactory.port", "465");
      properties.put("mail.smtp.starttls.enable", "true");
      properties.put("mail.smtp.host", host);
      properties.put("mail.smtp.port", "465");
      
      Session session = Session.getDefaultInstance(properties,
      new javax.mail.Authenticator() {
      protected PasswordAuthentication getPasswordAuthentication() {
      return new PasswordAuthentication(user,password);
      }
      });
      
      //2) compose message
      try{
      MimeMessage message = new MimeMessage(session);
      message.setFrom(new InternetAddress(user));
      message.addRecipient(Message.RecipientType.TO,new InternetAddress(to));
      message.setSubject("Message Aleart3"
      + "");
      
      //3) create MimeBodyPart object and set your message text
      BodyPart messageBodyPart1 = new MimeBodyPart();
      messageBodyPart1.setText("This is message body ,from app");
      
      //4) create new MimeBodyPart object and set DataHandler object to this object
      MimeBodyPart messageBodyPart2 = new MimeBodyPart();
      
      String filename ="G:\\jitesh\\E\\JavaProgram\\txtpacks\\DOit.txt";// "SendAttachment.java";//change accordingly
      DataSource source = new FileDataSource(filename);
      messageBodyPart2.setDataHandler(new DataHandler(source));
      messageBodyPart2.setFileName("Seta 1");
      
      
      //5) create Multipart object and add MimeBodyPart objects to this object
      Multipart multipart = new MimeMultipart();
      multipart.addBodyPart(messageBodyPart1);
      multipart.addBodyPart(messageBodyPart2);
      
      //6) set the multiplart object to the message object
      message.setContent(multipart );
      
      //7) send message
      Transport.send(message);
      
      System.out.println("message sent....");
      }catch (MessagingException ex) {ex.printStackTrace();}
     */
    /**
     *
     */

}
/*class Mimetomime implements Serializable{
String[] str;

    public MimeMultipart getStr()
    {
        MimeMultipart mmp=new MimeMultipart();
        MimeBodyPart bp=new MimeBodyPart();
        return mmp;
    }

    public void setStr(MimeMultipart mmp)
    {
        int n=mmp.getCount();
        for(int i=0;i<n;++i){
            try {
                BodyPart bp=mmp.getBodyPart(i);
               String contentType= bp.getContentType();
               Object ob= bp.getContent();
               if (ob instanceof Multipart) {

                        Multipart mp = (Multipart) ob;
                        BodyPart bbp = mp.getBodyPart(0);
                        System.out.println("Text: " + bbp.getContent().toString());

                    } else if (ob instanceof MimeMultipart) {
                        MimeMultipart mp = (MimeMultipart) ob;
                        int count = mp.getCount();
                        for (int ii = 0; ii < count; ++ii) {
                            BodyPart bp = mp.getBodyPart(ii);
                            System.out.println("Text" + ii + ":" + bp.getContent().toString());

                        }
                    } else if (obj instanceof String) {
                        System.out.println("Text:" + ob.toString());
                    }
            
            } catch (MessagingException ex) {
                Logger.getLogger(Mimetomime.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Mimetomime.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    

        
        
}*/
