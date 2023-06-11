/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package carl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Scanner;
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
public class CARL {

    private static final String googlehost = "smtp.gmail.com";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        String user = "carlabsrm@gmail.com";
        String password = "Passabc1234";
        String subjectinemail = "CODEE";
        String subject = "Invitation for CARL ExploreML Workshop";
        String filename = "Book.id";
        ArrayList<String> to = new ArrayList<String>();
        int i = -1;
        FileInputStream fos;
        if (args.length == 0) {
            helpoutput(filename);
            System.exit(0);
        }
        try {

            int j = 0;
            boolean u = false, p = false, si = false, so = false;

            for (String arg : args) {
                ++j;
                if (arg.equalsIgnoreCase("-h")) {
                    helpoutput(filename);
                } else if (arg.equalsIgnoreCase("-u")) {
                    user = args[j];
                    u = true;
                    System.out.println("Found user:" + user);
                } else if (arg.equalsIgnoreCase("-p")) {
                    password = args[j];
                    p = true;
                    System.out.println("Found password: ****(secret)");
                } else if (arg.equalsIgnoreCase("-si")) {
                    subjectinemail = args[j];
                    si = true;
                    System.out.println("Found subject to search:" + subjectinemail);
                } else if (arg.equalsIgnoreCase("-so")) {
                    subject = args[j];
                    so = true;
                    System.out.println("Found subject for bulk email:" + subject);
                } else if (arg.equalsIgnoreCase("-f")) {
                    filename = args[j];
                    System.out.println("Found file:" + filename);
                }
            }
            fos = new FileInputStream(new File(filename));
            Scanner sc = new Scanner(fos);
            if (!u || !p || !si || !so) {
                System.out.print("\n ERROR: ");
                if (!u) {
                    System.out.print(" userid ");
                }
                if (!p) {
                    System.out.print(" password ");
                }
                if (!si) {
                    System.out.print(" subject to search ");
                }
                if (!so) {
                    System.out.print(" subject for bulk email ");
                }
                System.out.print(" not found in console \n");
                sc.close();
                //helpoutput(filename);
                System.exit(0);
            }

            while (sc.hasNext()) {
                to.add(sc.next().trim().toLowerCase());
                i++;
            }
            sendMail(user,
                    password, to,
                    subject,
                    subjectinemail, i + 1);
            sc.close();
        } catch (FileNotFoundException ex) {
            try {
                FileOutputStream os = new FileOutputStream(new File(filename));
                os.write("".getBytes());
                os.close();
            } catch (Exception ex1) {

            }
            System.out.println("ERROR : File :" + filename + " NOT FOUND (Now created a file to be edited)");

        }
        //SendAttachment.main(args);
        // TODO code application logic here
    }

    private static void helpoutput(String filename) {
        System.out.println("ARGUMENTS:\n -h : help\n -u : username\n -p : password\n -si : subject to search in inbox\n -so : subject to use for bulk messaging\n  all above ARGUMENT required\n -f : file name [DEFAULT:" + filename + "] {Will be created if not found }\n  the above argument not required as default is available\n\n please read README.md file for more information");
    }

    /**
     * 'MsgCode' is the subject of the message in `from`'s MAIL to be sent to
     * List 'to' of 'toN' people with the Subject 'sub' by the account 'from'
     * with password 'password'.
     *
     * To Test To send mail with Attachments.
     */
    public static void sendMail(String from, String password, ArrayList<String> to, String sub, String msgCode, int toN) {//STEP 1: set connection to the gmail servers.
        try { //String host = "";
            //Get properties object    
            Properties props = new Properties();
            props.put("mail.smtp.host", googlehost);
            props.put("mail.smtp.socketFactory.port", "465");
            props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
            props.put("mail.smtp.auth", "true");
            props.put("mail.smtp.port", "465");
            //get Session   
            Session session = Session.getDefaultInstance(props, new javax.mail.Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
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
