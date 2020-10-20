package cn.les.base.controller;

import cn.les.base.dto.DepartDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IDepartService;
import cn.les.base.utils.ValidatorUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DepartController {
    @Resource
    private IDepartService departService;

    @GetMapping("/departs/{id}")
    DepartDTO fetchDepartById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return departService.fetchDepartById(id);
    }

    @GetMapping("/departs")
    List<DepartDTO> fetchAllDeparts() {
        return departService.fetchAllDeparts();
    }

    @PostMapping("/departs")
    void addDepart(@RequestBody DepartDTO depart) {
        ValidatorUtils.notBlank(depart.getDepartName(), "部门名不能为空");
        departService.addDepart(depart);
    }

    @PutMapping("/departs/{id}")
    void updateDepart(@PathVariable("id") Long id, @RequestBody DepartDTO depart) throws ResourceNotFoundException {
        ValidatorUtils.notBlank(depart.getDepartName(), "部门名不能为空");
        depart.setId(id);
        departService.updateDepart(depart);
    }

    @DeleteMapping("/departs/{id}")
    void removeDepart(@PathVariable("id") Long id) throws ResourceNotFoundException {
        departService.removeDepart(id);
    }
}
