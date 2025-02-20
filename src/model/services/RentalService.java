package model.services;

import model.entities.CarRental;
import model.entities.Invoice;

import java.time.Duration;

public class RentalService {
    private double valuePerHour;
    private double valuePerDay;

    private BrazilTaxService taxService;

    public RentalService(double valuePerHour, double valuePerDay, BrazilTaxService taxService) {
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

    public double getValuePerHour() {
        return valuePerHour;
    }

    public void setValuePerHour(double valuePerHour) {
        this.valuePerHour = valuePerHour;
    }

    public double getValuePerDay() {
        return valuePerDay;
    }

    public void setValuePerDay(double valuePerDay) {
        this.valuePerDay = valuePerDay;
    }

    public BrazilTaxService getTaxService() {
        return taxService;
    }

    public void setTaxService(BrazilTaxService taxService) {
        this.taxService = taxService;
    }
}
