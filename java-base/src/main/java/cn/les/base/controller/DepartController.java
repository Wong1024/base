package cn.les.base.controller;

import cn.les.base.dto.DepartDTO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.service.IDepartService;
import cn.les.base.utils.RequestResult;
import cn.les.base.utils.ValidatorUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class DepartController {
    @Resource
    private IDepartService departService;

    @GetMapping("/departs/{id}")
    RequestResult fetchDepartById(@PathVariable("id") Long id) throws ResourceNotFoundException {
        return RequestResult.ok(departService.fetchDepartById(id));
    }

    @GetMapping("/departs")
    RequestResult fetchAllDeparts() {
        return RequestResult.ok(departService.fetchAllDeparts());
    }

    @PostMapping("/departs")
    RequestResult addDepart(@RequestBody DepartDTO depart) {
        ValidatorUtils.getInstance().notBlank(depart.getDepartName(), "部门名不能为空");
        return RequestResult.ok(departService.addDepart(depart));
    }

    @PutMapping("/departs/{id}")
    RequestResult updateDepart(@PathVariable("id") Long id, @RequestBody DepartDTO depart) throws ResourceNotFoundException {
        ValidatorUtils.getInstance().notBlank(depart.getDepartName(), "部门名不能为空");
        depart.setId(id);
        return RequestResult.ok(departService.updateDepart(depart));
    }

    @DeleteMapping("/departs/{id}")
    RequestResult removeDepart(@PathVariable("id") Long id) throws ResourceNotFoundException {
        departService.removeDepart(id);
        return RequestResult.ok();
    }
}
