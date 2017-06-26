package dk.brics.tajs;


import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

/**
 * Created by scc_ubuntu on 6/1/17.
 */
public class JSParser {

    public void parseFile(String fileName, String[] searchStr) throws FileNotFoundException, UnsupportedEncodingException {
        Scanner scan = new Scanner(new File(fileName));
        PrintWriter writer = new PrintWriter("/home/scc_ubuntu/Documents/out.js", "UTF-8"); // Output File with only relevant functions
        int num = 0;
        while(scan.hasNext()){
            String line = scan.nextLine().toString();
            for(int j=0; j<searchStr.length; j++){
                if(line.contains(searchStr[j])){
                    while (scan.hasNext()){
                        for(int i=0; i<line.length(); i++){
                            if(line.charAt(i) == '{'){
                                num++;
                            }
                            if(line.charAt(i) == '}'){
                                num--;
                            }
                        }
                        System.out.println(line);
                        writer.println(line);
                        if(num == 0){
                            break;
                        } else {
                            line = scan.nextLine().toString();
                        }
                    }
                }
            }
        }
        writer.close();
    }

//    public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
//        JSParser fileSearch = new JSParser();
//        String[] fn = {"function silly", "function stranger"}; // Functions Array
//        fileSearch.parseFile("G:/CO421 - Final Year Project I/JSParser/src/com/company/simple.js", fn); // Input File
//    }
}
