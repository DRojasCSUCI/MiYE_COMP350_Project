import java.util.Scanner;

class Example{
    public static void main(String[] args){

        System.out.println("Hello World!");
        Scanner scan = new Scanner(System.in);
        String name = "";

        while(!name.equals("EXIT")) {
            System.out.println("Enter name \"EXIT\" to exit: ");
            name = scan.nextLine();
            System.out.println("Entered \"" + name + "\"");
        }

        System.out.println("Process Ended Execution Succesfully...");

    }
}