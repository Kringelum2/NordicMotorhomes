package grp1.motorhomes.service;

import grp1.motorhomes.model.AutoService;
import grp1.motorhomes.repository.AutoServiceRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Christian
 */
@Service
public class AutoServiceService {

    @Autowired
    AutoServiceRepo autoServiceRepo;

    /**
     * @author Christain
     */
    public List<AutoService> fetchAllAutoServices() {
        return autoServiceRepo.fetchAllAutoServices();
    }

    /**
     * @author Patrick
     */
    public AutoService findAutoService(int autoServiceId) {
        return autoServiceRepo.findAutoService(autoServiceId);
    }

    /**
     * @author Joachim
     */
    public void createAutoService(AutoService autoService) {
        autoServiceRepo.createAutoService(autoService);
    }

    /**
     * @author Joachim
     */
    public void editAutoService(AutoService autoService) {
        autoServiceRepo.editAutoService(autoService);
    }

    /**
     * @author Christian
     */
    public void markDone(AutoService autoService) {
        autoServiceRepo.markDone(autoService);
    }

}
