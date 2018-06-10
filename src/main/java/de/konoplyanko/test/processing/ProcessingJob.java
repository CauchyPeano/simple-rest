package de.konoplyanko.test.processing;

import okhttp3.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.io.IOException;
import java.nio.file.FileSystem;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class ProcessingJob {

    private static final Logger LOG = LoggerFactory.getLogger(ProcessingJob.class);

    public static final OkHttpClient okHttpClient = new OkHttpClient();

    public static final MediaType PLAIN_TEXT = MediaType.parse("text/plain; charset=utf-8");
    public static final String URL = "http://localhost:4567/process-record";

    public static void main(String[] args) {

        Path path = validateArguments(args);

        List<String> strings;
        try {
            strings = Files.readAllLines(path);
        } catch (IOException e) {
            throw new JobExecutionException("Can't read file " + path, e);
        }

        List<String> output = processFile(strings);
        writeResult(path, output);

    }

    private static void writeResult(Path path, List<String> output) {
        Path outputPath = path.getParent().resolve("result.csv");

        try {
            Files.write(outputPath, output);
        } catch (IOException e) {
            throw new JobExecutionException("can't write to output file " + outputPath , e);
        }
    }

    private static List<String> processFile(List<String> strings) {
        ArrayList<String> results = new ArrayList<>();
        for (String record : strings) {
            String processedRecord = processRecordViaRest(record);
            results.add(processedRecord);
        }
        return results;
    }

    private static Path validateArguments(String[] args) {
        if (args.length != 1) {
            throw new JobExecutionException("Please pass file path as parameter");
        }

        String filePath = args[0];

        FileSystem fs = FileSystems.getDefault();
        Path path = fs.getPath(filePath);

        if (!Files.exists(path)) {
            throw new JobExecutionException("File doesn't exist");
        }
        return path;
    }

    public static String processRecordViaRest(String record) {

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
            throw new JobExecutionException("Couldn't invoke rest service " + URL, e);
        }
    }

}
