package com.appium.utils;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.filefilter.TrueFileFilter;
import org.im4java.core.ConvertCmd;
import org.im4java.core.IM4JavaException;
import org.im4java.core.IMOperation;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * Created by saikrisv on 17/03/16.
 */
public class ImageUtils {
    public void wrapDeviceFrames(String deviceFrame, String deviceScreenToBeFramed, String framedDeviceScreen)
            throws InterruptedException, IOException, IM4JavaException {
        IMOperation op = new IMOperation();
        ConvertCmd cmd = new ConvertCmd();
        op.addImage(deviceFrame);
        op.addImage(deviceScreenToBeFramed);
        op.gravity("center");
        op.composite();
        op.opaque("none");
        op.addImage(framedDeviceScreen);
        cmd.run(op);
    }


    public static void main(String[] arg) throws IOException {
        File dir = new File(System.getProperty("user.dir")+"/target/screenshot/");
        System.out.println("Getting all files in " + dir.getCanonicalPath() + " including those in subdirectories");
        List<File> files = (List<File>) FileUtils.listFiles(dir, TrueFileFilter.INSTANCE, TrueFileFilter.INSTANCE);
        JsonArray mainObj  = new JsonArray();

        JsonObject jsonObject = new JsonObject();

        String deviceName;
        FileWriter file1 = new FileWriter("test.json");
        for (File file : files) {
            JsonObject ja;

            if(file.getCanonicalFile().toString().contains("results")){
                ja=new JsonObject();
                deviceName=file.getName().split("_")[0];
                String deviceModel=file.getName().split("_")[1];
                String screenName=file.getName().split("_")[2];
                String imagePath=file.getPath().toString();
                ja.addProperty("Device Name",deviceName);
                ja.addProperty("Device Model",deviceModel);
                ja.addProperty("Screen Name",screenName);
                ja.addProperty("Image Path",imagePath);
                jsonObject.add(deviceName, ja);

            }

        }
        mainObj.add(jsonObject);
        file1.write(mainObj.toString());
        file1.flush();
        file1.close();
    }
}

