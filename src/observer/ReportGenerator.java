package observer;

import java.io.*;
import java.nio.channels.FileChannel;
public class ReportGenerator {
    private ReportGenerator(){}

    public static void createReport(String outputPath){
        try{
            String directoryName = "JGomokuReport" + System.currentTimeMillis();
            String dicrectoryPath = outputPath + "/"+ directoryName;

            //making new directory
            File directory = new File(outputPath, directoryName);
            directory.mkdir();

            //transfer images to the new directory
            transferFile(new File("src/observer/assets/black.png"), new File(dicrectoryPath, "black.png"));
            transferFile(new File("src/observer/assets/white.png"), new File(dicrectoryPath, "white.png"));
            transferFile(new File("src/observer/assets/chessboard.jpg"), new File(dicrectoryPath, "chessboard.jpg"));
            transferFile(new File("src/observer/assets/forward.png"), new File(dicrectoryPath, "forward.png"));
            transferFile(new File("src/observer/assets/backward.png"), new File(dicrectoryPath, "backward.png"));

            //generate report html file
            File report = new File(dicrectoryPath, "report.html");
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
    private static String readTemplate(){
        String templatePath = "src/observer/assets/template.html";
        StringBuilder builder = new StringBuilder();
        try {
            FileInputStream stream = new FileInputStream(templatePath);
            BufferedReader reader = new BufferedReader(new InputStreamReader(stream));

            while (reader.ready()){
                builder.append(reader.readLine());
                builder.append("\n");
            }

            stream.close();
        } catch (IOException e) {
            e.printStackTrace();

        }
        return builder.toString();
    }


    /**
     * Copies the source file and paste to the target location
     * @param originFile Original source file
     * @param targetFile Target file need to be overwritten
     */
    private static void transferFile(File originFile, File targetFile){
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

}
