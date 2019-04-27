package org.learn.functional.java;

import com.jasongoodwin.monads.Try;
import org.jooq.lambda.Unchecked;

import java.io.Writer;

public class UserExportContentWriter {
    private final String LS = System.lineSeparator();
    private final UserRepository userRepo = new UserRepository();

    public void writeContent(Writer writer) {
        writeHeader(writer);
        userRepo.fetchAll()
                .stream()
                .forEach(Unchecked.consumer(user -> writer.write(getContentLine(user))));
    }

    private void writeHeader(Writer writer) {
        String header = String.join(";", "name", "place") + LS;
        Try.ofFailable(() -> {
            writer.write(header);
            return Void.TYPE;
        });
    }

    private String getContentLine(User aUser) {
        return String.join(";", aUser.getName(), aUser.getPlace()) + LS;
    }

    public static void main(String[] args) {
        Exporter exporter = new Exporter();
        UserExportContentWriter contentWriter = new UserExportContentWriter();
        exporter.exportToFile("users.csv", contentWriter::writeContent);
    }
}
