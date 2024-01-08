import java.util.Scanner;

class User{
    private String username;
    //pk
    //sk

    void keygen(){
        ///pk,sk
    }

    // String encrypt(String m){
    //     //return c;
    // }

    // String decrypt(String c){
    //     //return m;
    // }

    void menu(){
        //action
        //1. send message
        //2. view message
        //3. logout
        //choice

        //if(choice == 1)
        //createMsg()
        //else if(choice == 2)
        //viewMsg()
        //else
        //logout()
    }

    void createMsg(){
        //send to: receiver
        //enter your message: xxxxxx... = m
        //c = encrypt(m)
        //successfully sent
        //menu()
    }

    void viewMsg(){
        //getmsglist
        //no  from   date
        //1.  alice  20240101 23:11
        //2.  bob    20240102 11:12
        //c = choice
        //display(c)
    }

    void display(String c){
        //m = decrypt(c)
        //print(m)
        //viewMsg()
    }
}