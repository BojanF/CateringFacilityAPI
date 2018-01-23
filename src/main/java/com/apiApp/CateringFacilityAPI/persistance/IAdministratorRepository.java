package com.apiApp.CateringFacilityAPI.persistance;

import com.apiApp.CateringFacilityAPI.model.jpa.Administrator;
import com.apiApp.CateringFacilityAPI.model.jpa.Developer;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface IAdministratorRepository extends CrudRepository<Administrator, Long> {

    @Query(value =
            "select admin " +
            "from com.apiApp.CateringFacilityAPI.model.jpa.Administrator admin " +
            "where admin.user.id = :userId")
    Administrator findAdministratorByUserId(@Param("userId") Long userId);
}
