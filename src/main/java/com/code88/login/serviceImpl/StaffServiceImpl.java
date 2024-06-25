package com.code88.login.serviceImpl;

import com.code88.login.dao.StaffRepository;
import com.code88.login.entity.Staff;
import com.code88.login.service.StaffService;
import org.hibernate.query.SortDirection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



import java.util.List;
import java.util.Optional;

@Service
public class StaffServiceImpl implements StaffService {


    @Autowired
    private StaffRepository staffRepository;


    @Override
    public List<Staff> getAllSatff() {
        return staffRepository.findAll();
    }



    @Override
    public void savedStaff(Staff staff) {
        staffRepository.save(staff);
    }

    @Override
    public Staff getStaffById(Long id) {
        Optional<Staff> optionalStaff=staffRepository.findById(id);
        Staff staff;
        if(optionalStaff.isPresent()){
            staff=optionalStaff.get();
        }else{
            throw new RuntimeException("This id"+id+"does not exist");
        }
        return staff;

    }

    @Override
    public void deleteStaff(Long id) {
        staffRepository.deleteById(id);
    }



    @Override
    public Page<Staff> findPaginated(int pageNo, int pageSize,String sortField,String sortDirection,String keyword) {
        Sort sort= sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())? Sort.by(sortField).ascending():
                Sort.by(sortField).descending();

        Pageable pageable= PageRequest.of(pageNo - 1,pageSize,sort);
        if(keyword!=null){
            return staffRepository.findAll(keyword,pageable);
        }
        return this.staffRepository.findAll(pageable);
    }
}
