package observer;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.util.Stack;

/**
 * This class is used to automatically generate the report
 *
 * @author Cirun Zhang
 * @version 1.1
 */
public class ReportGenerator {
    public ReportGenerator(){}

    public void createReport(String outputPath){
        try{
            String directoryName = "JGomokuReport" + System.currentTimeMillis();
            String directoryPath = outputPath + File.separator + directoryName;

            //making new directory
            File directory = new File(outputPath, directoryName);
            directory.mkdir();

            //transfer images to the new directory
            transferFile(this.getClass().getResourceAsStream("/assets/white.png"), new File(directoryPath, "white.png"));
            transferFile(this.getClass().getResourceAsStream("/assets/black.png"), new File(directoryPath, "black.png"));
            transferFile(this.getClass().getResourceAsStream("/assets/chessboard.jpg"), new File(directoryPath, "chessboard.jpg"));
            transferFile(this.getClass().getResourceAsStream("/assets/forward.png"), new File(directoryPath, "forward.png"));
            transferFile(this.getClass().getResourceAsStream("/assets/backward.png"), new File(directoryPath, "backward.png"));

            //generates report file
            File report = new File(directoryPath, "report.html");
            BufferedWriter writer = new BufferedWriter(new FileWriter(report));
            writer.write(readTemplate());
            writer.close();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Reads the template report file and return its content
     * @return String contains report's source code
     */
    private String readTemplate(){
        String templatePath = "/assets/template.html";
        StringBuilder builder = new StringBuilder();
        try {
            InputStream stream = this.getClass().getResourceAsStream(templatePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            while (reader.ready()){
                builder.append(reader.readLine());
                builder.append("\n");
            }
            stream.close();
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return writeGameHistory(builder.toString());
    }


    /**
     * Copies the source file and paste to the target location
     * @param originFile Original source file
     * @param targetFile Target file need to be overwritten
     */
    @Deprecated
    private void transferFile(File originFile, File targetFile){
        try{
            FileInputStream inputStream = new FileInputStream(originFile);
            FileOutputStream outputStream = new FileOutputStream(targetFile);
            FileChannel inputChannel = inputStream.getChannel();
            FileChannel outputChannel = outputStream.getChannel();
            inputChannel.transferTo(0, inputChannel.size(), outputChannel);
            inputStream.close();
            outputStream.close();
            inputChannel.close();
            outputChannel.close();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * Copies the source file and paste to the target location
     * @param originFile Original source file
     * @param targetFile Target file need to be overwritten
     */
    private void transferFile(InputStream originFile, File targetFile){
        try{
            Files.copy(originFile, targetFile.toPath());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //For test use
    private String replaceGameHistory(String content){
        return content.replace("#history#", "[{x:0,y:0,t:1}, {x:1,y:1,t:-1}, {x:2,y:2,t:-1}, {x:3,y:3,t:-1},\n"
            + "            {x:4,y:4,t:-1}, {x:5,y:5,t:-1}, {x:6,y:6,t:-1}, {x:7,y:7,t:-1},\n"
            + "            {x:8,y:8,t:-1}, {x:9,y:9,t:-1}, {x:10,y:10,t:1}, {x:13,y:11,t:-1},\n"
            + "            {x:12,y:12,t:-1}, {x:13,y:13,t:-1}, {x:14,y:14,t:-1},]");
    }

    /**
     * Rewrite the game history
     * @param template Source code of the report template
     * @return Souce code of the report with history updated
     */
    private String writeGameHistory(String template){
        Stack<int[]> history = HistoryObserver.getHistory();
        StringBuilder builder = new StringBuilder();
        builder.append("[");
        try{
            for(int[] move : history){
                builder.append("{x:");
                builder.append(move[0]);
                builder.append(",y:");
                builder.append(move[1]);
                builder.append(",t:");
                builder.append(move[2]);
                builder.append("},");
            }
        }catch (IndexOutOfBoundsException e){
            e.printStackTrace();
        }
        builder.append("]");
        return template.replace("#history#", builder.toString());
    }

}
