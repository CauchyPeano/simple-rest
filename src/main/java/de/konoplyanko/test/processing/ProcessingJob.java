package de.konoplyanko.test.processing;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;

public class ProcessingJob {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingJob.class);

    public static final OkHttpClient okHttpClient = new OkHttpClient();

    public static final MediaType PLAIN_TEXT = MediaType.parse("text/plain; charset=utf-8");
    public static final String URL = "localhost:4567/process-record";

    public static void main(String[] args) {

        if (args.length != 1) {
            throw new IllegalArgumentException("Please pass file path as parameter");
        }

        String filePath = args[0];

        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(filePath);

        if (Files.exists(path)) {
            throw new IllegalStateException("File doesn't exist");
        }

    }

    public String processRecordViaRest(String record) {

        RequestBody body = RequestBody.create(PLAIN_TEXT, record);

        Request request = new Request.Builder()
                .url(URL)
                .post(body)
                .build();
        Response response = null;
        try {
            response = okHttpClient.newCall(request).execute();
            if (response.body() != null) {
                return response.body().string();
            } else {
                return "";
            }
        } catch (IOException e) {
            LOG.error("Couldn't invoke rest service " + URL, e);
            throw new RecordProcessingError("Couldn't invoke rest service " + URL, e);
        }
    }

}
