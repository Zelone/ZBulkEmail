/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carl;

import static carl.SendAttachment.sendMail;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.mail.Folder;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Store;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author Jitesh Jhawar aka Zelone
 */
public class CARL
{

    private static final String user = "carlabsrm@gmail.com";
    private static final String password = "Passabc1234";
    private static final String googlehost = "smtp.gmail.com";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args)
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
        //SendAttachment.main(args);
        // TODO code application logic here
    }
/**
 * 'MsgCode' is the subject of the message in `from`'s MAIL to be sent to List
    'to' of 'toN' people with the Subject 'sub' by the account 'from' with password 'password'.
 *  
 * To Test To send mail with Attachments.  
 */
    public static void sendMail(String from, String password, ArrayList<String> to, String sub, String msgCode, int toN)
    {//STEP 1: set connection to the gmail servers.
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
//STEP 2: Connect to INBOX
            store.connect(googlehost, from, password);
            //3) create the folder object and open it  
            Folder emailFolder = store.getFolder("INBOX");
            emailFolder.open(Folder.READ_ONLY);
            //int d = emailFolder.getMessageCount();
//STEP 3: Get only Latest 6 messages
            int n = emailFolder.getMessageCount();
            //4) retrieve the messages from the folder in an array and print it  
            Message[] messages = emailFolder.getMessages(n - 6, n);

            for (int i = 0; i < messages.length; i++) {
//STEP 4: finding Subject with msgCode.
                Message message = messages[i];
                if (message.getSubject().equals(msgCode)) {
//STEP 5: saving found message temperely.
                    out = (MimeMultipart) message.getContent();
                    System.out.println("Found Message. Going to be sent to:" + toN + " Mailing Ids");
                    break;
                }
                System.out.println(i + "next" + message.getSubject());

            }

            /* try{
//STEP 6: Trying to output the MIME MESSAGE ONTO A FILE ~~~~~~~~~~~~~~~~~~~~ERROR~~~~~~~~~~
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("j.g")));
  Object oh=ois.readObject();
  out=oh;
  }catch(Exception e){e.printStackTrace();
  }*/
//STEP 6: Composing sendng Message 
            //compose message    
            if (out != null) {
                for (int i = 0; i < toN; ++i) {
                    try {

                        MimeMessage msg = new MimeMessage(session);
                        msg.setFrom(new InternetAddress(from));
                        msg.setRecipient(Message.RecipientType.TO, new InternetAddress(to.get(i)));
                        msg.setSubject(sub);
//STEP 7: Adding Found message to the MimeMessage
                        msg.setContent(out, "text/html");
//STEP 8: ending the message
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
}
