package de.konoplyanko.test.rest;

import spark.Request;
import spark.Response;

import static spark.Spark.get;
import static spark.Spark.post;

public class WebApplication {

    public static void main(String[] args) {
        post("/process-record", WebApplication::processRecord);
        get("/health-check", (req, res) -> "Up and running!");
    }

    private static Object processRecord(Request request, Response response) {
        String body = request.body();

        String[] split = body.split(",");
        if (split.length != 4) {
            response.status(400);
            return "wrong format";
        }

        String vin = split[0];
        String make = split[3];
        return new ProcessedRecord(vin, make);
    }

}
