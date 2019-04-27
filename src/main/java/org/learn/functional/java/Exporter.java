package org.learn.functional.java;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Paths;
import java.util.function.Consumer;

public class Exporter {

    public void exportToFile(String fileName, Consumer<Writer> contentWriter) {
        File target = Paths.get("/","Users", "disen", "Desktop", fileName).toFile();
        try(Writer writer = new FileWriter(target)) {
            if (!target.exists()) {
                target.createNewFile();
            }
            contentWriter.accept(writer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
