package grp1.motorhomes.service;

import grp1.motorhomes.model.Motorhome;
import grp1.motorhomes.repository.MotorhomeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author Patrick
 */
@Service
public class MotorhomeService {

    @Autowired
    MotorhomeRepo motorhomeRepo;

    /**
     * @author Patrick
     */
    public List<Motorhome> fetchAllMotorhomes() {
        return motorhomeRepo.fetchAllMotorhomes();
    }

    /**
     * @author Patrick
     */
    public void createMotorhome(Motorhome motorhome) throws Exception {
        motorhomeRepo.createMotorhome(motorhome);
    }

    /**
     * @author Patrick
     */
    public Motorhome findMotorhome(String licencePlate) {
        return motorhomeRepo.findMotorhome(licencePlate);
    }

    /**
     * @author Patrick
     */
    public void editMotorhome(Motorhome motorhome) {
        motorhomeRepo.editMotorhome(motorhome);
    }

    /**
     * @author Patrick
     */
    public void deleteMotorhome(String licencePlate) {
        motorhomeRepo.deleteMotorhome(licencePlate);
    }

    /**
     * @author Christian
     */
    public void setAvailable(String licencePlate, boolean status) {
        motorhomeRepo.setAvailable(licencePlate, status);
    }

    /**
     * @author Sverri
     */
    public List<Motorhome> fetchMotorhomesBetween(LocalDateTime from, LocalDateTime to) {
        return motorhomeRepo.fetchMotorhomesBetween(from, to);
    }

    /**
     * @author Joachim
     */
    public List<Motorhome> fetchAllUnavailableMotorhomes() {
        return motorhomeRepo.fetchAllUnavailableMotorhomes();
    }
}