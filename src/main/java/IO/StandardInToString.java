package IO;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Scanner;

public class StandardInToString {

    public String inToString() throws IOException {
        Scanner sc = new Scanner(System.in);
//        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        StringBuilder sb = new StringBuilder();
        String input = null;

         while (sc.hasNextLine()) {
            input= sc.nextLine();
            sb.append(input);
            if (input.equals(""))
                break;
        }
        System.out.println("===========================================");
        return sb.toString();
    }
}
