package model;

import org.apache.commons.lang.time.StopWatch;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class ParserManager {
    private String initialPath;
    private String fileName;
    private String newFileName;
    private String folderWithParsedFiles;
    private Parser parser;
    private WriterToFiles writerToFiles;
    private StopWatch stopWatch;
    private Map<String, Item> parsedList;

    public void start() throws IOException {
        stopWatch = new StopWatch();
        stopWatch.start();

        findFolderPathAndMakeNewFolder();
        List<String> filesList = new ArrayList<>();

        File dir = new File(initialPath);
        for (File file : dir.listFiles()) {
            if (file.getName().endsWith(".xlsx")) {
                filesList.add(file.getName());
            }
        }
        System.out.println("Files for parsing: " + filesList + "\n");
        for (String s : filesList) {
            StopWatch stopWatch1 = new StopWatch();
            stopWatch1.start();

            fileName = s;
            newFileName = "Parsed_" + s;

            try {
                parser = new Parser(initialPath + "\\" + fileName);
                parsedList = parser.parse();
                writerToFiles = new WriterToFiles();
                writerToFiles.writeToFiles(parsedList, parser.getNonameMaterial(), folderWithParsedFiles, newFileName);
            } catch (Exception e) {
                e.printStackTrace();
                System.out.println(fileName + " can't be parsed"+"\n"+"error at row "+ parser.getCurrentRow());
            }

            stopWatch1.split();
            System.out.println("Work time for " + fileName + " " + stopWatch1.toSplitString() + "\n");
        }
        stopWatch.split();
        System.out.println("Total work time: " + stopWatch.toSplitString());
    }

    public void findFolderPathAndMakeNewFolder() throws IOException {
        initialPath = System.getProperty("user.dir");
        folderWithParsedFiles = initialPath + "\\parsed_files";
        File folder = new File(folderWithParsedFiles);
        folder.mkdir();
        folder.mkdirs();
    }
}
