package com.testWebApp.io;


import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;

/**
 *
 * @author Mohsen Parisay <mohsenparisay@gmail.com>
 * @version 1.0 <31.Dec.2020>
 */
public class FileOperation {

    private static final Logger LOG = Logger.getLogger(FileOperation.class.getName());
    private final String outputDirectory;
    private final String screenShotDirectory;

    public FileOperation() {
        outputDirectory = getCurrentDirectory() + "/results/";
        screenShotDirectory = outputDirectory + "screenshots/";
        createFolders();
    }

    private void createFolders() {
        File outputDir = new File(outputDirectory);
        File screenDir = new File(screenShotDirectory);

        try {
            if (!outputDir.exists()) {
                outputDir.mkdirs();
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }
        try {
            if (!screenDir.exists()) {
                screenDir.mkdirs();
            }
        } catch (Exception e) {
            LOG.info(e.getMessage());
        }

    }


    private String getCurrentDirectory() {
//        Path currentRelativePath = Paths.get("");
//        String directory = currentRelativePath.toAbsolutePath().toString();
        String directory = "/Users/negar/IdeaProjects";
        LOG.info("Working Directory = " + directory);

        return directory;
    }

    public void writeOutput(String fileName, String data) {
        Writer writer;

        fileName = outputDirectory + getTimeStamp() + "_" + fileName;
        try {
            writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
            writer.write(data);
            writer.flush();
            writer.close();

            LOG.info("Results saved to: " + fileName);
        } catch (IOException ex) {
            System.err.println(ex.getMessage());
        }
    }

    public String getTimeStamp() {
        return new SimpleDateFormat("ddMMyyyy_HHmmss").format(new Date());
    }

//    public void captureScreenshot(int trial) {
//        Rectangle rect = component.getBounds();
//
//        try {
//            String format = "png";
//            String fileName = screenShotDirectory + "screenshot_trial_" + trial + "_" + getTimeStamp() + "." + format;
//            BufferedImage captureImage = new BufferedImage(rect.width, rect.height, BufferedImage.TYPE_INT_ARGB);
//            component.paint(captureImage.getGraphics());
//
//            ImageIO.write(captureImage, format, new File(fileName));
//            LOG.info("Screenshot saved to: " + fileName);
//
//        } catch (IOException ex) {
//            System.err.println(ex);
//        }
//    }

    public  void  saveScreenshots(int trial, ArrayList<String> screenShotHtmls){
        String format = "txt";

        for (int i=0; i<trial;i++){
            String fileName = screenShotDirectory + "screenshot_trial_" + i + "_" + getTimeStamp() + "." + format;
            Writer writer;
            try {
                writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(fileName), StandardCharsets.UTF_8));
                writer.write(screenShotHtmls.get(i));
                writer.flush();
                writer.close();

            } catch (IOException ex) {
                System.err.println(ex.getMessage());
            }
        }
    }

}
