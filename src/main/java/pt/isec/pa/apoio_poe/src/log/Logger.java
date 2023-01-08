package pt.isec.pa.apoio_poe.src.log;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.Writer;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class Logger {

    private static final String SEPARATOR = " | ";
    private static Logger instance = null;
    private static String filename = "log.txt";
    private static List<String> listMessages;

    public static Logger getInstance() {
        if (instance == null){
            instance = new Logger(filename);
            listMessages = new ArrayList<>();
        }
        return instance;
    }

    protected Logger(String filename) {
        Logger.filename = filename;
    }

    private String getDatetime(){
        LocalDateTime currentDateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return currentDateTime.format(formatter);
    }

    public static void log(String message) {
        Logger log = Logger.getInstance();

        try{
            Writer output = new BufferedWriter(new FileWriter(filename, true));
            output
                .append(log.getDatetime())
                .append(SEPARATOR)
                .append(LogLevelEnum.INFO.toString())
                .append(SEPARATOR)
                .append(message)
                .append("\n");
            output.close();
        }catch (Exception e) {
            listMessages.add("Error occurred while trying to log");
        }
    }

    public static void logAndPrint(String message) {
        System.out.println(message);
        log(message);
    }

    public static void print(String message) {
        System.out.println(message);
    }

    public static void logError(String message) {
        Logger log = Logger.getInstance();

        try{
            Writer output = new BufferedWriter(new FileWriter(filename, true));
            output
                .append(log.getDatetime())
                .append(SEPARATOR)
                .append(LogLevelEnum.ERROR.toString())
                .append(SEPARATOR)
                .append(message)
                .append("\n");
            output.close();
        }catch (Exception e) {
            listMessages.add("Error occurred while trying to log");
        }
    }

    public static void printData(String data){
        print(Objects.requireNonNullElse(data, "Sem dados para mostrar"));
    }
    

    public static void appendMessage(String message) {
        listMessages.add(message);
    }

    public static void dumpMessages() {
        for(String message : listMessages)
            System.out.println(message);
        listMessages.clear();
    }
}