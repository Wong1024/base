package cn.les.base.service;

import cn.les.base.dto.DepartDTO;
import cn.les.base.exception.ResourceNotFoundException;

import java.util.List;

public interface IDepartService {
    DepartDTO fetchDepartById(Long id) throws ResourceNotFoundException;

    List<DepartDTO> fetchAllDeparts();

    DepartDTO addDepart(DepartDTO depart);

    DepartDTO updateDepart(DepartDTO depart) throws ResourceNotFoundException;

    void removeDepart(Long id) throws ResourceNotFoundException;
}
