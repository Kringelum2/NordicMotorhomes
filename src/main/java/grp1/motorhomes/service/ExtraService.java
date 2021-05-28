package grp1.motorhomes.service;

import grp1.motorhomes.model.Extra;
import grp1.motorhomes.repository.ExtraRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

/**
 * @author Patrick
 */
@Service
public class ExtraService {


    @Autowired
    ExtraRepo extraRepo;

    /**
     * @author Patrick
     */
    public List<Extra> fetchAllExtras(){
        return extraRepo.fetchAllExtras();
    }

    /**
     * @author Patrick
     * @param extra
     */
    public void createExtras(Extra extra) {
        extraRepo.createExtra(extra);
    }

    /**
     * @author Patrick
     * @param extraId
     */
    public Extra findExtra(int extraId) {
        return extraRepo.findExtra(extraId);
    }

    /**
     * @author Patrick
     * @param extra
     */
    public void editExtra(Extra extra) {
        extraRepo.editExtra(extra);
    }

    /**
     * @author Patrick
     * @param extraId
     */
    public void deleteExtra(int extraId) {
        extraRepo.deleteExtra(extraId);
    }
}