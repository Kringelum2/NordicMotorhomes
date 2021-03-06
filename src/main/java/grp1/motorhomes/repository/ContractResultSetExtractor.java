package grp1.motorhomes.repository;

import grp1.motorhomes.model.Contract;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.ResultSetExtractor;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * @author Sverri
 * contract ResultSetExtractor so that we can map multiple rows in a join query to a single object.
 * made wtih help from:
 * https://stackoverflow.com/questions/32093350/rowmapper-put-multiple-results-into-list-attribute
 */
public class ContractResultSetExtractor implements ResultSetExtractor {

    /**
     * the method that is run when the class tries to map data from a resultSet from the JdbcTemplate
     * made with help from https://stackoverflow.com/questions/32093350/rowmapper-put-multiple-results-into-list-attribute
     */
    @Override
    public List<Contract> extractData(ResultSet resultSet) throws SQLException, DataAccessException {

        // we use hash map to make sure there is only one object of a contract with a given contract id
        HashMap<Integer, Contract> contracts = new HashMap<>();

        // we run trough all the rows of the query
        while(resultSet.next()){

            // we see if the contract is already in the hash map
            Contract contract = contracts.get(resultSet.getInt("contract_id"));

            // if it is not we make a new contract object and put it in the map
            if(contract == null){

                contract = new Contract();
                contract.setContractId(resultSet.getInt("contract_id"));
                contract.setFromDate(resultSet.getTimestamp("from_Date").toString());
                contract.setToDate(resultSet.getTimestamp("to_Date").toString());
                contract.setOdometer(resultSet.getInt("odometer"));
                contract.setCustomerParams(resultSet.getInt("customer_number"),
                        resultSet.getString("customers.name"), resultSet.getString("licence_number"),
                        resultSet.getString("street"), resultSet.getString("city"), resultSet.getInt("post_code"));
                contract.setMotorhomeParams(resultSet.getString("motorhome"),
                        resultSet.getString("type"), resultSet.getString("brand"),
                        resultSet.getString("model"), resultSet.getString("motorhomes.description"),
                        resultSet.getInt("motorhomes.price"), resultSet.getBoolean("available"));
                contract.setExcessKm(resultSet.getInt("excess_km"));
                contract.setTransferKm(resultSet.getInt("transfer_km"));
                contract.setDeliveryPoint(resultSet.getString("delivery_point"));
                contract.setDelivered(resultSet.getBoolean("delivered"));
                contract.setPickupPoint(resultSet.getString("pickup_point"));
                contract.setPickedUp(resultSet.getBoolean("picked_up"));
                contract.setClosed(resultSet.getBoolean("closed"));
                contract.setFinalPrice(resultSet.getInt("total_price"));
                contract.setUnderHalfFuelTank(resultSet.getBoolean("under_half_fuel_tank"));

                contracts.put(contract.getContractId(), contract);

            }

            // lastly we add the extra of the current row of the result set
            contract.addExtra(resultSet.getInt("extra_id"), resultSet.getInt("extras.price"),
                    resultSet.getString("extras.name"), resultSet.getString("extras.description"));
        }
        return new ArrayList<>(contracts.values());
    }
}
