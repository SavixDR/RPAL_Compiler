//package src;

import java.io.*;
//import com.neeraj2608.rpalinterpreter.csem.CSEMachine;
//import com.neeraj2608.rpalinterpreter.parser.ParseException;
//import com.neeraj2608.rpalinterpreter.parser.Parser;
//import com.neeraj2608.rpalinterpreter.scanner.Scanner;

public class Main {

    public static String fileName;

    public static void main(String[] args) {
       if (args.length != 1) {
           System.err.println("Usage: java rpal20 <input_file>");
           return;
       }

       String fileName = args[0];
        // String fileName = "test.txt";
        AST ast = null;

        ast = buildAST(fileName);
        ast.standardize();
        evaluateST(ast);
    }


    private static void evaluateST(AST ast){
        CSEMachine csem = new CSEMachine(ast);
        csem.evaluateProgram();
        System.out.println();
    }

    private static void printInputListing(String fileName){
        BufferedReader buffer = null;
        try{
            buffer = new BufferedReader(new InputStreamReader(new FileInputStream(new File(fileName))));
            String s = "";
            while((s = buffer.readLine())!=null){
                System.out.println(s);
            }
        }catch(FileNotFoundException e){
            throw new ParseException("File "+fileName+" not found.");
        }catch(IOException e){
            throw new ParseException("Error reading from file "+fileName);
        }finally{
            try{
                if(buffer!=null) buffer.close();
            }catch(IOException e){
            }
        }
    }

    private static AST buildAST(String fileName){
        AST ast = null;
        try{
            Scanner scanner = new Scanner(fileName);
            Parser parser = new Parser(scanner);
            ast = parser.buildAST();
        }catch(IOException e){
            throw new ParseException("ERROR: Could not read from file: " + fileName);
        }
        return ast;
    }

}
