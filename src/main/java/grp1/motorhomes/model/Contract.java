package grp1.motorhomes.model;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Christian
 */
@Entity
public class Contract {

    @Id
    private int contractId;
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int odometer;
    private int excessKm;
    private int transferKm;
    private double finalPrice;
    private String deliveryPoint;
    private String pickupPoint;
    private boolean underHalfFuelTank;
    private boolean delivered;
    private boolean pickedUp;
    private boolean closed;

    @ManyToMany
    private List<Extra> extras;

    @OneToOne
    Customer customer;

    @OneToOne
    Motorhome motorhome;


    /**
     * @author Christian
     */
    public Contract() {

    }

    /**
     * @author Christian
     */
    public Contract(int contractId, LocalDateTime fromDate, LocalDateTime toDate, int odometer, Customer customer,
                    Motorhome motorhome, List<Extra> extras) {
        this.contractId = contractId;
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.odometer = odometer;
        this.customer = customer;
        this.motorhome = motorhome;
        this.extras = extras;
    }

    /**
     * @author Sverri
     */
    public void addExtra(int id, int price, String name, String description) {
        if (extras == null) {
            extras = new ArrayList<>();
        }
        extras.add(new Extra(id, price, name, description));
    }

    public List<Extra> getExtras() {
        return extras;
    }

    public void setExtras(List<Integer> extras) {
        for (int i : extras) {
            addExtra(i, 0, null, null);
        }
    }

    public boolean isDelivered() {
        return delivered;
    }

    public void setDelivered(boolean delivered) {
        this.delivered = delivered;
    }

    public boolean isPickedUp() {
        return pickedUp;
    }

    public void setPickedUp(boolean pickedUp) {
        this.pickedUp = pickedUp;
    }

    public boolean isClosed() {
        return closed;
    }

    public void setClosed(boolean closed) {
        this.closed = closed;
    }

    public int getContractId() {
        return contractId;
    }

    public void setContractId(int contractId) {
        this.contractId = contractId;
    }

    public LocalDateTime getFromDate() {
        return fromDate;
    }


    /**
     * @author Sverri
     * Format a date and time to a string
     */
    public String getFromDateAsString(){
        return String.format("%02d. %s %02d:%02d",fromDate.getDayOfMonth(),fromDate.getMonth().name().toLowerCase(),
                fromDate.getHour(),fromDate.getMinute());
    }

    /**
     * @author Sverri
     * Format a date and time to a string
     */
    public String getToDateAsString(){
        return String.format("%02d. %s %02d:%02d",toDate.getDayOfMonth(),toDate.getMonth().name().toLowerCase(),
                toDate.getHour(),toDate.getMinute());
    }

    /**
     * @author Sverri
     *  Since there is a difference between how dates are handles from the database and data handle form the
     *  HTML datetime field it was necessary to have two different ways to parse it.
     */
    public void setFromDate(String from) {
        if (from.contains("T")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            this.fromDate = LocalDateTime.parse(from, formatter);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            this.fromDate = LocalDateTime.parse(from, formatter);
        }
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    /**
     * @author Sverri
     * Since there is a difference between how dates are handles from the database and data handle form the
     * HTML datetime field it was necessary to have two different ways to parse it.
     */
    public void setToDate(String to) {
        LocalDateTime localDateTime;
        if (to.contains("T")) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm");
            localDateTime = LocalDateTime.parse(to, formatter);
        } else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.S");
            localDateTime = LocalDateTime.parse(to, formatter);
        }
        this.toDate = localDateTime;
    }

    public int getOdometer() {
        return odometer;
    }

    public void setOdometer(int odometer) {
        this.odometer = odometer;
    }

    public int getExcessKm() {
        return excessKm;
    }

    public void setExcessKm(int excessKm) {
        this.excessKm = excessKm;
    }

    public int getTransferKm() {
        return transferKm;
    }

    public void setTransferKm(int transferKm) {
        this.transferKm = transferKm;
    }

    public String getFinalPriceAsString() {
        return String.format("%.2f", finalPrice);
    }

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    public boolean isUnderHalfFuelTank() {
        return underHalfFuelTank;
    }

    public void setUnderHalfFuelTank(boolean underHalfFuelTank) {
        this.underHalfFuelTank = underHalfFuelTank;
    }

    public String getDeliveryPoint() {
        return deliveryPoint;
    }

    public void setDeliveryPoint(String deliveryPoint) {
        this.deliveryPoint = deliveryPoint;
    }

    public String getPickupPoint() {
        return pickupPoint;
    }

    public void setPickupPoint(String pickupPoint) {
        this.pickupPoint = pickupPoint;
    }

    public Motorhome getMotorhome() {
        return motorhome;
    }

    public void setMotorhomeParams(String licencePlate, String type, String brand, String model, String description, int price, boolean available) {
        this.motorhome = new Motorhome(licencePlate, type, brand, model, description, price, available);
    }

    public void setMotorhome(String licencePlate) {
        this.motorhome = new Motorhome();
        this.motorhome.setLicencePlate(licencePlate);
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomerParams(int customerNumber, String name, String licenceNumber, String street, String city, int postCode) {
        this.customer = new Customer(customerNumber, name, licenceNumber, street, city, postCode);
    }

    public void setCustomer(int customer) {
        this.customer = new Customer();
        this.customer.setCustomerNumber(customer);
    }

    @Override
    public String toString() {
        return "Contract (" + "contractId: " + contractId + ", fromDate: " + fromDate + ", toDate: " + toDate +
                ", odometer: " + odometer + ", excessKm: " + excessKm + ", transferKm: " + transferKm +
                ", finalPrice: " + finalPrice + ", deliveryPoint: " + deliveryPoint + ", pickupPoint: " + pickupPoint +
                ", underHalfFuelTank: " + underHalfFuelTank + ", delivered: " + delivered + ", pickedUp: " + pickedUp +
                ", closed: " + closed + ", extras: " + extras + ", customer: " + customer + ", motorhome: " + motorhome + ")";
    }
}
