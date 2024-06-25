package com.code88.login.service;

import com.code88.login.entity.Staff;
import org.springframework.data.domain.Page;

import java.util.List;

public interface StaffService {

    List<Staff> getAllSatff();


    void savedStaff(Staff staff);
    Staff getStaffById(Long id);
    void deleteStaff(Long id);

    Page<Staff> findPaginated(int pageNo, int pageSize, String sortField, String sortDirection, String keyword);

}
