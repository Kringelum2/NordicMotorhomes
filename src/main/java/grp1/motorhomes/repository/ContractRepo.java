package grp1.motorhomes.repository;

import grp1.motorhomes.model.Contract;
import grp1.motorhomes.model.Extra;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

/**
 * @author Christian
 */
@Repository
public class ContractRepo {

    @Autowired
    JdbcTemplate template;

    /**
     * @author Christian
     */
    public List<Contract> fetchAllContracts() {
        String sqlStatement =
                "SELECT contract_id, from_date, to_date, odometer, excess_km, transfer_km, customer_number, " +
                        "motorhome, delivery_point, delivered, pickup_point, picked_up, closed, total_price, under_half_fuel_tank, " +
                        "extra_id, extras.price, extras.name, extras.description, " +
                        "type, motorhomes.description, model_id, motorhomes.price, available, model, brand, " +
                        "customers.name, licence_number, address_id, street, post_code, city " +
                        "FROM contracts " +
                        "LEFT JOIN contracts_extras using(contract_id) " +
                        "LEFT JOIN extras using(extra_id) " +
                        "JOIN customers using(customer_number) " +
                        "JOIN addresses using(customer_number) " +
                        "JOIN cities using(city_id) " +
                        "JOIN motorhomes on contracts.motorhome = motorhomes.registration " +
                        "JOIN models using(model_id) WHERE closed = false ORDER BY from_date";

        ContractResultSetExtractor extractor = new ContractResultSetExtractor();

        return (List<Contract>) template.query(sqlStatement, extractor);
    }

    /**
     * @return
     * @author Sverri
     */
    public List<Contract> fetchAllClosedContracts() {
        String sqlStatement =
                "SELECT contract_id, from_date, to_date, odometer, excess_km, transfer_km, customer_number, " +
                        "motorhome, delivery_point, delivered, pickup_point, picked_up, closed, total_price, under_half_fuel_tank, " +
                        "extra_id, extras.price, extras.name, extras.description, " +
                        "type, motorhomes.description, model_id, motorhomes.price, available, model, brand, " +
                        "customers.name, licence_number, address_id, street, post_code, city " +
                        "FROM contracts " +
                        "LEFT JOIN contracts_extras using(contract_id) " +
                        "LEFT JOIN extras using(extra_id) " +
                        "JOIN customers using(customer_number) " +
                        "JOIN addresses using(customer_number) " +
                        "JOIN cities using(city_id) " +
                        "JOIN motorhomes on contracts.motorhome = motorhomes.registration " +
                        "JOIN models using(model_id) WHERE closed = true ORDER BY to_date";
        ContractResultSetExtractor extractor = new ContractResultSetExtractor();
        return (List<Contract>) template.query(sqlStatement, extractor);
    }

    /**
     * @author Christian
     */
    public void createContract(Contract contract) {
        String insertContractValues = "INSERT INTO contracts (from_date, to_date, odometer, excess_km, " +
                "transfer_km, customer_number, motorhome, delivery_point, pickup_point, total_price, under_half_fuel_tank) "
                + "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        // jdbc template does not by default support returning generated keys
        // code stub with much help from https://www.baeldung.com/spring-jdbc-autogenerated-keys
        template.update(connection -> {
            PreparedStatement preparedStatement = connection.prepareStatement(insertContractValues, Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setTimestamp(1, Timestamp.valueOf(contract.getFromDate()));
            preparedStatement.setTimestamp(2, Timestamp.valueOf(contract.getToDate()));
            preparedStatement.setInt(3, contract.getOdometer());
            preparedStatement.setInt(4, contract.getExcessKm());
            preparedStatement.setInt(5, contract.getTransferKm());
            preparedStatement.setInt(6, contract.getCustomer().getCustomerNumber());
            preparedStatement.setString(7, contract.getMotorhome().getLicencePlate());
            preparedStatement.setString(8, contract.getDeliveryPoint());
            preparedStatement.setString(9, contract.getPickupPoint());
            preparedStatement.setDouble(10, contract.getFinalPrice());
            preparedStatement.setBoolean(11, contract.isUnderHalfFuelTank());
            return preparedStatement;
        }, keyHolder);


        if (contract.getExtras() != null && contract.getExtras().size() > 0) {
            String insertExtraRelations = "INSERT INTO contracts_extras(contract_id, extra_id) VALUES(?, ?)";
            for (Extra extra : contract.getExtras()) {
                template.update(insertExtraRelations, keyHolder.getKey().intValue(), extra.getExtraId());
            }
        }
    }

    /**
     * @author Sverri
     */
    public Contract findContract(int contractId) {
        String sqlStatement =
                "SELECT contract_id, from_date, to_date, odometer, excess_km, transfer_km, customer_number, " +
                        "motorhome, delivery_point, delivered, pickup_point, picked_up, closed, total_price, under_half_fuel_tank, " +
                        "extra_id, extras.price, extras.name, extras.description, " +
                        "type, motorhomes.description, model_id, motorhomes.price, available, model, brand, " +
                        "customers.name, licence_number, address_id, street, post_code, city " +
                        "FROM contracts " +
                        "LEFT JOIN contracts_extras using(contract_id) " +
                        "LEFT JOIN extras using(extra_id) " +
                        "JOIN customers using(customer_number) " +
                        "JOIN addresses using(customer_number) " +
                        "JOIN cities using(city_id) " +
                        "JOIN motorhomes on contracts.motorhome = motorhomes.registration " +
                        "JOIN models using(model_id) WHERE contract_id = ?";
        ContractResultSetExtractor extractor = new ContractResultSetExtractor();

        List<Contract> contracts = (List<Contract>) template.query(sqlStatement, extractor, contractId);

        return contracts.get(0);

    }

    /**
     * @author Sverri
     */
    public void editContract(Contract contract) {
        String updateSql = "UPDATE contracts SET from_date = ?, to_date = ?, odometer = ?, customer_number = ?, motorhome = ?, " +
                "excess_km = ?, transfer_km = ?, delivery_point = ?, delivered = ?, pickup_point = ?, picked_up = ?, closed = ?, total_price = ?, under_half_fuel_tank = ?" +
                " WHERE contract_id = ?";

        template.update(updateSql, contract.getFromDate(), contract.getToDate(), contract.getOdometer(), contract.getCustomer().getCustomerNumber(),
                contract.getMotorhome().getLicencePlate(), contract.getExcessKm(), contract.getTransferKm(), contract.getDeliveryPoint(),
                contract.isDelivered(), contract.getPickupPoint(), contract.isPickedUp(), contract.isClosed(), contract.getFinalPrice(),
                contract.isUnderHalfFuelTank(), contract.getContractId());

        String deleteExtras = "DELETE FROM contracts_extras WHERE contract_id = ?";
        template.update(deleteExtras, contract.getContractId());

        String insertExtras = "INSERT INTO contracts_extras(contract_id, extra_id) VALUES(?,?)";

        if (contract.getExtras() != null) {
            for (Extra extra : contract.getExtras())
                template.update(insertExtras, contract.getContractId(), extra.getExtraId());
        }
    }

    /**
     * @author Sverri
     */
    public void deleteContract(int contractId) {
        String deleteSql = "DELETE FROM contracts_extras WHERE contract_id = ?";
        template.update(deleteSql, contractId);
        deleteSql = "DELETE FROM contracts WHERE contract_id = ?";
        template.update(deleteSql, contractId);
    }

    /**
     * @author Patrick
     */
    public void deliverContract(Contract contract) throws Exception{

        String updateSql = "UPDATE contracts SET delivered = true, transfer_km = ?, odometer = ?, delivery_point = ? WHERE contract_id = ?";

        try {
            template.update(updateSql, contract.getTransferKm(), contract.getOdometer(), contract.getDeliveryPoint(), contract.getContractId());
        } catch (Exception e) {
            throw new Exception("Odometer needs a value");
        }
    }

    /**
     * @author Joachim
     */
    public void pickupContract(Contract contract) throws Exception {

        String updateSql = "UPDATE contracts SET picked_up = true, transfer_km = transfer_km+?, pickup_point = ?, excess_km = ?, " +
                " under_half_fuel_tank = ? WHERE contract_id = ?";

        try {
            template.update(updateSql, contract.getTransferKm(), contract.getPickupPoint(), contract.getExcessKm(),
                    contract.isUnderHalfFuelTank(), contract.getContractId());
        } catch (Exception e) {
            throw new Exception("Odometer needs a value");
        }

    }

    /**
     * @author Joachim
     */
    public void closeContract(Contract contract) {
        String updateSql = "UPDATE contracts SET closed = true, total_price = ? WHERE contract_id = ?";
        template.update(updateSql, contract.getFinalPrice(), contract.getContractId());
    }
}
