package org.pavlinic.ircbot.features;

import java.io.*;

public class OpAlert {
    public static String watchChannel = "##ordona";
    public static String staffChannel = "##ordona-staff";

    public static void addName(String name)
    {
        try {
            File dir = new File("data");
            dir.mkdir();  

            PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter("data/op-alert.lst", true)));
            out.println(name);
            out.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static void delName(String name)
    {
        File inputFile = new File("data/op-alert.lst");
        File tempFile = new File(inputFile + ".tmp");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(inputFile));
            BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile));

            String lineToRemove = name;
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                // trim newline when comparing with lineToRemove
                String trimmedLine = currentLine.trim();
                if(trimmedLine.equals(lineToRemove))
                    continue;
                writer.write(currentLine);
            }

            tempFile.renameTo(inputFile);

            reader.close();
            writer.close();
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
