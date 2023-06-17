package com.gas.service.count.impl;

import cn.hutool.core.util.RandomUtil;
import cn.hutool.core.util.StrUtil;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.MonitorPointDao;
import com.gas.dto.OverviewDto;
import com.gas.entity.MonitorDevice;
import com.gas.entity.MonitorPoint;
import com.gas.enums.ProvinceEnum;
import com.gas.service.count.OverviewService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Slf4j
public class OverviewServiceImpl implements OverviewService {

    @Autowired
    private MonitorPointDao pointDao;
    @Autowired
    private MonitorDeviceDao deviceDao;

    @Override
    public OverviewDto getOverviewAll() {
        OverviewDto dto = new OverviewDto();
        //查询居民用户数，查询所有点位的点位类型的居民用户数量
        List<MonitorPoint> residents = pointDao.selectByPointType("居民");
        dto.setResident(CollectionUtils.isEmpty(residents) ? 0 : residents.size());

        //非居民用户数，点位类型为不是居民的点位数量
        List<MonitorPoint> noResidents = pointDao.selectByNePointType("居民");
        dto.setNoResident(CollectionUtils.isEmpty(noResidents) ? 0 : noResidents.size());

        //天然气用户数，查询点位中燃气种类为天然气的数量
        List<MonitorPoint> naturalResident = pointDao.selectByGasType("天然气");
        dto.setNaturalResident(CollectionUtils.isEmpty(naturalResident) ? 0 : naturalResident.size());
        //液化气用户数
        List<MonitorPoint> liquefyResident = pointDao.selectByGasType("液化石油气");
        dto.setLiquefyResident(CollectionUtils.isEmpty(liquefyResident) ? 0 : liquefyResident.size());


        //居民设备数，查询设备对应点位的点位类型是否居民
        List<MonitorDevice> deviceList = deviceDao.getDeviceList();
        int residentDevice = 0;
        int noResidentDevice = 0;
        int naturalDevice = 0;
        int liquefyDevice = 0;
        Map<String, Integer> gasCompanyMap = new HashMap<>();
        Map<String, Integer> gasTypeMap = new HashMap<>();
        Map<String, Integer> userTypeMap = new HashMap<>();
        for (MonitorDevice monitorDevice : deviceList) {
            MonitorPoint monitorPoint = pointDao.getById(monitorDevice.getPointId());
            if (monitorPoint != null && Objects.equals(monitorPoint.getPointType(), "居民")) {
                //居民设备数
                residentDevice++;
            } else if (monitorPoint != null && !Objects.equals(monitorPoint.getPointType(), "居民")) {
                //非居设备数
                noResidentDevice++;
            }

            if (monitorPoint != null && Objects.equals(monitorPoint.getGasType(), "天然气")) {
                //天然气设备数
                naturalDevice++;
            } else if (monitorPoint != null && Objects.equals(monitorPoint.getGasType(), "液化石油气")) {
                liquefyDevice++;
            }

            if (monitorPoint != null) {
                //供气企业
                String gasCompany = monitorPoint.getGasCompany();
                if (StrUtil.isBlank(gasCompany)) {
                    gasCompany = "其他";
                }

                if (!Objects.isNull(gasCompanyMap.get(gasCompany))) {
                    Integer gasCompanyNum = gasCompanyMap.get(gasCompany);
                    gasCompanyNum++;
                    gasCompanyMap.put(gasCompany, gasCompanyNum);
                } else {
                    gasCompanyMap.put(gasCompany, 1);
                }

                //燃气种类
                String gasType = monitorPoint.getGasType();
                if (StrUtil.isBlank(gasType)) {
                    gasType = "其他";
                }

                if (!Objects.isNull(gasTypeMap.get(gasType))) {
                    Integer gasTypeNum = gasTypeMap.get(gasType);
                    gasTypeNum++;
                    gasTypeMap.put(gasType, gasTypeNum);
                } else {
                    gasTypeMap.put(gasType, 1);
                }

                //用户种类
                String userType = monitorPoint.getUserType();
                if (StrUtil.isBlank(userType)) {
                    userType = "其他";
                }

                if (!Objects.isNull(userTypeMap.get(userType))) {
                    Integer userTypeNum = userTypeMap.get(userType);
                    userTypeNum++;
                    userTypeMap.put(userType, userTypeNum);
                } else {
                    userTypeMap.put(userType, 1);
                }
            }


        }
        dto.setResidentDevice(residentDevice);
        dto.setNoResidentDevice(noResidentDevice);
        dto.setNaturalDevice(naturalDevice);
        dto.setLiquefyDevice(liquefyDevice);

        //行政区划
        Map<String, List<MonitorDevice>> provinces = deviceList.stream().collect(Collectors.groupingBy(MonitorDevice::getProvince));
        List<OverviewDto.DataView> governDeviceDistributions = new ArrayList<>();

        for (Map.Entry<String, List<MonitorDevice>> entry : provinces.entrySet()) {
            OverviewDto.DataView dataView = new OverviewDto.DataView();
            String prefix = "北京市市辖区";
            String key = entry.getKey();
            key = key.substring(prefix.length());
            String code = ProvinceEnum.getCodeByName(key);

            dataView.setCode(code);
            dataView.setName(key);
            dataView.setNum(entry.getValue().size());

            governDeviceDistributions.add(dataView);
        }
        dto.setGovernDeviceDistributions(governDeviceDistributions);

        //供气企业分布
        List<OverviewDto.DataView> coTypeDeviceDistributions = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : gasCompanyMap.entrySet()) {
            OverviewDto.DataView dataView = new OverviewDto.DataView();
            dataView.setCode(RandomUtil.randomNumbers(6));
            dataView.setName(entry.getKey());
            dataView.setNum(entry.getValue());

            coTypeDeviceDistributions.add(dataView);
        }
        dto.setCoTypeDeviceDistributions(coTypeDeviceDistributions);

        //燃气种类分布
        List<OverviewDto.DataView> gasTypeDeviceDistributions = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : gasTypeMap.entrySet()) {
            OverviewDto.DataView dataView = new OverviewDto.DataView();
            dataView.setCode(RandomUtil.randomNumbers(6));
            dataView.setName(entry.getKey());
            dataView.setNum(entry.getValue());

            gasTypeDeviceDistributions.add(dataView);
        }
        dto.setGasTypeDeviceDistributions(gasTypeDeviceDistributions);

        //用户种类分布
        List<OverviewDto.DataView> userTypeDeviceDistributions = new ArrayList<>();
        for (Map.Entry<String, Integer> entry : userTypeMap.entrySet()) {
            OverviewDto.DataView dataView = new OverviewDto.DataView();
            dataView.setCode(RandomUtil.randomNumbers(6));
            dataView.setName(entry.getKey());
            dataView.setNum(entry.getValue());

            userTypeDeviceDistributions.add(dataView);
        }
        dto.setUserTpyeDeviceDistributions(userTypeDeviceDistributions);

        return dto;
    }
}
