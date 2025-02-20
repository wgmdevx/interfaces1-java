package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

import java.time.Duration;

public class RentalService {
    private double valuePerHour;
    private double valuePerDay;

    private TaxService taxService;

    public RentalService(double valuePerHour, double valuePerDay, TaxService taxService) {
        this.valuePerHour = valuePerHour;
        this.valuePerDay = valuePerDay;
        this.taxService = taxService;
    }

    public void processInvoice(CarRental carRental) {
        double minutes = Duration.between(carRental.getStart(), carRental.getFinish()).toMinutes();
        double hours = minutes * 60.0;

        double basicPayment;
        if(hours <= 12.0) {
            basicPayment = valuePerHour * Math.ceil(hours);
        } else {
            basicPayment = valuePerDay * Math.ceil(hours / 24);
        }

        double tax = taxService.tax(basicPayment);
        carRental.setInvoice(new Invoice(basicPayment, tax));
    }

}
