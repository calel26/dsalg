package com.lawsmat.rw;

import java.util.*;
import java.io.*;

public class ReadWrite {
    Scanner sc=new Scanner(System.in);
    String filename = "thetextfile.txt";

    public void write(){//add elements via scanner

        File f = new File(filename);
        boolean append = false;
        try{
            PrintWriter pw = null;
            if ( f.exists() ) {
                append = true; //file exist and this gives the user permission to append to the file
                pw = new PrintWriter(new FileWriter(new File(filename), append));
            }else {
                append = false; //won't be appending to the file - creating a new file
                pw = new PrintWriter(new FileWriter(new File(filename))); //creates the new file
            }
            boolean autoFlush = false; //keeps the file in memory
            String data = "";
            while(!data.equals("x")){
                System.out.println("Enter data to store. \"x\" to exit");
                data=sc.nextLine();
                if(!data.equals("x"))
                    pw.println (data); //prints one line of data to the file
            }
            // The data includes two variables separated by commas
            pw.close();//closes the file

        }catch(Exception e){
            System.out.println("There was an error: " + e);
        }

    }

    public ArrayList<String> read(){
        ArrayList<String> lines = new ArrayList<String>();
        try{
            BufferedReader f = new BufferedReader(new FileReader(filename));
            String s;
            while ((s=f.readLine())!=null){
                lines.add(s);
            }

        }catch(Exception e){
            System.out.println("There was an error: " + e);
        }

        return lines;
    }

    public void deleteFile(){//delete file

        File f = new File(filename);
        boolean append = false;
        try{
            PrintWriter pw = null;

            append = false; //won't be appending to the file - creating a new file
            pw = new PrintWriter(new FileWriter(new File(filename)));
            pw.close();//closes the file

        }catch(Exception e){
            System.out.println("There was an error: " + e);
        }
        //readFromFile();
    }

    public void writeToFile(String data){//add one more line to existing text file

        File f = new File(filename);
        boolean append = false;
        try{
            PrintWriter pw = null;
            if ( f.exists() ) {
                append = true; //file exist and this gives the user permission to append to the file
                pw = new PrintWriter(new FileWriter(new File(filename), append));
            }else {
                append = false; //won't be appending to the file - creating a new file
                pw = new PrintWriter(new FileWriter(new File(filename))); //creates the new file
            }
            boolean autoFlush = false; //keeps the file in memory

            pw.println (data); //prints one line of data to the file
            // The data includes two variables separated by commas
            pw.close();//closes the file

        }catch(Exception e){
            System.out.println("There was an error: " + e);
        }
        // readFromFile();
    }


}
