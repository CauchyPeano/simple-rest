package de.konoplyanko.test;

import spark.Request;
import spark.Response;

import static spark.Spark.post;

public class WebApplication {

    public static void main(String[] args) {
        post("/process-record", WebApplication::processRecord);
    }

    static void processRecord(Request request, Response response) {

    }

}
