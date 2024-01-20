import java.util.Scanner;

class User{
    private String username;
    //pk
    //sk

    void keygen(){
        //pk,sk
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
        Scanner scanner = new Scanner(System.in);

        //send to: receiver
        System.out.println("Enter the username that you want to send message to: ");
        String name = scanner.nextLine();

        //enter your message: xxxxxx... = m
        System.out.println("Enter your message: ");
        String m = scanner.nextLine();
        
        //c = encrypt(m)

        //successfully sent
        System.out.println("The message is successfully sent.");

        //menu()
    }

    void viewMsg(){
        //getmsglist
        //no  from   date
        //1.  alice  20240101 23:11
        //2.  bob    20240102 11:12

        // allow user to choose no to view message
        //c = choice
        //display(c)
    }

    void display(String c){
        //m = decrypt(c)
        //print(m)
        //viewMsg()
    }
}