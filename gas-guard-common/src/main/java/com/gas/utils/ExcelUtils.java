package com.gas.utils;

import com.alibaba.excel.EasyExcel;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ExcelUtils {

    public <T> void writeDataToExcel(String fileName, Class<T> modelClass, List<T> data) {
        EasyExcel.write(fileName, modelClass).sheet("Sheet1").doWrite(data);
    }
}
