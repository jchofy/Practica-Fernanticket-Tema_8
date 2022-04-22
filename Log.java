package com.company;


import java.io.*;

public class Log implements Serializable {

    public  void escribir (String cadena) {

        try(FileWriter fw = new FileWriter("log.txt", true);
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter out = new PrintWriter(bw))
        {
            bw.write(cadena+"\n");
        } catch (IOException e) {
            //exception handling left as an exercise for the reader
        }
    }

}
