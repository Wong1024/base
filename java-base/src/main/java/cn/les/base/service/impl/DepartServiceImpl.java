package cn.les.base.service.impl;

import cn.les.base.dto.DepartDTO;
import cn.les.base.entity.DepartDO;
import cn.les.base.exception.ResourceNotFoundException;
import cn.les.base.repository.IDepartDao;
import cn.les.base.service.IDepartService;
import cn.les.base.utils.SnowflakeUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class DepartServiceImpl implements IDepartService {
    @Resource
    private IDepartDao departDao;

    @Override
    public DepartDTO fetchDepartById(Long id) throws ResourceNotFoundException {
        Optional<DepartDO> opt = departDao.findByIdAndDeleteFlagFalse(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("找不到部门");
        }
        return DepartDTO.fromDepartDO(opt.get());
    }

    @Override
    public List<DepartDTO> fetchAllDeparts() {
        return departDao.findByDeleteFlagFalse()
                .stream().map(DepartDTO::fromDepartDO).collect(Collectors.toList());
    }

    @Override
    public void addDepart(DepartDTO depart) {
        DepartDO departDO = depart.toDepartDO();
        departDO.setId(SnowflakeUtils.genId());
        departDao.save(departDO);
    }

    @Override
    public void updateDepart(DepartDTO depart) throws ResourceNotFoundException {
        Optional<DepartDO> opt = departDao.findByIdAndDeleteFlagFalse(depart.getId());
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("找不到部门");
        }
        departDao.save(depart.toDepartDO());
    }

    @Override
    public void removeDepart(Long id) throws ResourceNotFoundException {
        Optional<DepartDO> opt = departDao.findByIdAndDeleteFlagFalse(id);
        if (!opt.isPresent()) {
            throw new ResourceNotFoundException("找不到部门");
        }
        DepartDO depart = opt.get();
        depart.setDeleteFlag(true);
        departDao.save(depart);

        List<DepartDO> all = departDao.findAll();
        List<DepartDO> children = fetchDepartChildren(id, all)
                .stream()
                .filter(child -> !child.getDeleteFlag())
                .peek(item -> item.setDeleteFlag(true))
                .collect(Collectors.toList());
        departDao.saveAll(children);
    }

    //获得指定部门所有子部门，遍历所有层级
    private List<DepartDO> fetchDepartChildren(Long parentId, List<DepartDO> all) {
        List<DepartDO> result = new ArrayList<>();
        Queue<DepartDO> tmp = new LinkedList<>();
        Iterator<DepartDO> it = all.iterator();
        DepartDO depart, itemDepart;
        Long itemId;
        while(it.hasNext()) {
            depart = it.next();
            if (depart.getId().equals(parentId)) {
                it.remove();
            }
            if (depart.getParentId().equals(parentId)) {
                tmp.add(depart);
                it.remove();
            }
        }
        while(tmp.size() > 0) {
            itemDepart = tmp.poll();
            result.add(itemDepart);
            it = all.iterator();
            itemId = itemDepart.getId();
            while(it.hasNext()) {
                depart = it.next();
                if (depart.getParentId().equals(itemId)) {
                    tmp.add(depart);
                    it.remove();
                }
            }
        }

        return result;
    }
}
