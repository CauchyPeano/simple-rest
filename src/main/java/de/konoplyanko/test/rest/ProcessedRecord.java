package de.konoplyanko.test.rest;

public class ProcessedRecord {

    private final String vin;
    private final String make;

    public ProcessedRecord(String vin, String make) {
        this.vin = vin;
        this.make = make;
    }

    public String getVin() {
        return vin;
    }

    public String getMake() {
        return make;
    }

    @Override
    public String toString() {
        return vin + "," + make;
    }
}
