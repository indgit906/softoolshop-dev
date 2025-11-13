package com.softoolshop.adminpanel.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.softoolshop.adminpanel.entity.OfficeHours;

import java.util.List;

public interface OfficeHoursRepository extends JpaRepository<OfficeHours, Integer> {

    // Fetch all hours for a given office
    //List<OfficeHours> findByOffice_OfficeId(Integer officeId);

    // Optionally fetch hours for a given office + day
    //List<OfficeHours> findByOffice_OfficeIdAndDayOfWeek(Integer officeId, String dayOfWeek);
}
