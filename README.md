# ZBulkEmail

The bulk mailing system 

Sending Bulk Emails That do not go in spam. Last tested on  26thAug2020

## Requirements:
* An speedy internet connection.
* An Mail account(preffered gmail). 
* Allow third party to login you need to switch it on in the email.
* To be of sane mind not to send spam.
* java 8

Please note: Netbeans IDE is used to edit the code

## Steps for mailing:  


* Write the email to be sent  
* send it to yourself(-u user , -p password) with a dummy subject (-si Subject input) example "CODEE" 
* Run jar file with -h argument and follow the commnd.
* Create the file (-f filename) "Book.id" \<Case sensitive\> in the dist folder \< Netbeans jar files go to dist folder after being built with the lib files\>
* Reciever Email ids are to be written in a file using new line as delimiter. 
* Reciever Subject Line (-so subject output) is to be written in the argument
* Run the Jar file with arguments 
        -u "username@gmail.com" -p "password" -si "CODEE" -so "Invitation to CARL lab workshop ."
* Optional argument -f "Book.id"


## External jar used:
 Jar files included in dist/lib folder
 * activation 1.1.1
 * java mail 1.4.4
 * javax mail 
 
