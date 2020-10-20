package cn.les.base.utils;

import org.springframework.data.domain.Sort;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;

public class SortUtils {
    public static Sort buildSort(String order, String sortBy, String[] fields) {
        List<String> sortFields = Arrays.asList(fields);
        Sort sort = Sort.unsorted();
        if (!StringUtils.isEmpty(sortBy) && sortFields.contains(sortBy)) {
            sort = Sort.by("desc".equalsIgnoreCase(order) ? Sort.Direction.DESC : Sort.Direction.ASC, sortBy);
        }
        return sort;
    }
}
