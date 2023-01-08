package pt.isec.pa.apoio_poe.src.utils;

import pt.isec.pa.apoio_poe.src.log.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileUtils {

    public static List<List<String>> importCSVFileAsListOfList(File file){
        List<List<String>> list = new ArrayList<>();
        try{
            Scanner sc = new Scanner(file);
            List<String> temp;
            while (sc.hasNextLine()){
                temp = parseStringIntoList(sc.nextLine());
                list.add(temp);
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
        return list;
    }

    public static List<String> parseStringIntoList(String inputString){
        List<String> list = new ArrayList<>();
        Scanner sc = new Scanner(inputString); //file to be scanned
        sc.useDelimiter(",");
        String temp;
        while (sc.hasNext()) {
            temp = sc.next();
            list.add(temp);
        }
        return list;
    }

    public static void exportStringToCSV(String filename, String string2dump) {
        try (FileWriter fw = new FileWriter(filename)) {
            BufferedWriter bw = new BufferedWriter(fw);
            PrintWriter pw = new PrintWriter(bw);
            pw.println(string2dump);
            pw.close();
            bw.close();
        } catch (IOException e) {
            Logger.appendMessage("Erro a exportar CSV!");
        }
    }

    public static boolean isStringValidAsBool(String candidate){
        Pattern pattern = Pattern.compile("true|false", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(candidate);

        return matcher.matches();
    }

}
