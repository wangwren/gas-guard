package com.gas.service.count.impl;

import com.gas.dao.DataDictDao;
import com.gas.dao.MonitorDeviceDao;
import com.gas.dao.WarnInfoDao;
import com.gas.dto.FeedbackCountDto;
import com.gas.dto.MonitorDeviceDto;
import com.gas.dto.WarnInfoDto;
import com.gas.dto.WarnTimeDto;
import com.gas.entity.DataDict;
import com.gas.entity.MonitorDevice;
import com.gas.entity.WarnDealInfo;
import com.gas.entity.WarnInfo;
import com.gas.model.WarnCountRequest;
import com.gas.service.count.FeedbackCountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@Slf4j
public class FeedbackCountServiceImpl implements FeedbackCountService {

    @Autowired
    private WarnInfoDao warnInfoDao;
    @Autowired
    private MonitorDeviceDao deviceDao;
    @Autowired
    private DataDictDao dataDictDao;

    @Override
    public List<FeedbackCountDto> feedBackCount(WarnCountRequest request) {

        //查询数据字典处置反馈
        List<DataDict> dataDictList = dataDictDao.selectByTypeName("处置反馈");
        if (CollectionUtils.isEmpty(dataDictList)) {
            return null;
        }
        //将dataDictList中的dictValue转成Map，key为dictValue，Value为0
        Map<String, Integer> feedbackMap = new HashMap<>();
        for (DataDict dataDict : dataDictList) {
            feedbackMap.put(dataDict.getDictValue(), 0);
        }

        //查询数据字典设备厂家
        List<DataDict> factoryList = dataDictDao.selectByTypeName("设备厂家");
        if (CollectionUtils.isEmpty(factoryList)) {
            return null;
        }
        Map<String, Integer> factoryMap = new HashMap<>();
        for (DataDict dataDict : factoryList) {
            factoryMap.put(dataDict.getDictValue(), 0);
        }

        WarnInfoDto warnInfoDto = new WarnInfoDto();
        BeanUtils.copyProperties(request, warnInfoDto);
        List<WarnInfoDto> infoDtoList = warnInfoDao.selectList(warnInfoDto);
        if (CollectionUtils.isEmpty(infoDtoList)) {
            return null;
        }
        List<Integer> deviceIds = infoDtoList.stream().map(WarnInfoDto::getDeviceId).collect(Collectors.toList());

        MonitorDeviceDto deviceDto = new MonitorDeviceDto();
        BeanUtils.copyProperties(request, deviceDto);

        //处置反馈 -> 设备厂家
        Map<String, String> feedback2factoryMap = new HashMap<>();

        List<MonitorDevice> monitorDevices = deviceDao.selectByIdsCond(deviceIds,deviceDto);
        List<FeedbackCountDto> list = new ArrayList<>();
        for (MonitorDevice device : monitorDevices) {
            List<WarnInfo> warnInfos = warnInfoDao.selectByDeviceId(device.getId());
            if (CollectionUtils.isEmpty(warnInfos)) {
                continue;
            }
            List<Integer> warnInfoIds = warnInfos.stream().map(WarnInfo::getId).collect(Collectors.toList());
            List<WarnDealInfo> warnDealInfos = warnInfoDao.selectWarnDealInfoByWarnIds(warnInfoIds);
            if (CollectionUtils.isEmpty(warnDealInfos)) {
                continue;
            }
            for (WarnDealInfo dealInfo : warnDealInfos) {
                feedbackMap.merge(dealInfo.getFeedback(), 1, Integer::sum);
                factoryMap.merge(device.getDeviceFactory(), 1, Integer::sum);

                feedback2factoryMap.put(dealInfo.getFeedback(), device.getDeviceFactory());
            }
        }

        for (Map.Entry<String, Integer> feedback : feedbackMap.entrySet()) {
            FeedbackCountDto feedbackCountDto = new FeedbackCountDto();
            feedbackCountDto.setFeedback(feedback.getKey());
            feedbackCountDto.setNum(feedback.getValue());

            String factory = feedback2factoryMap.get(feedback.getKey());

            List<FeedbackCountDto.FactoryCount> factoryCounts = new ArrayList<>();
            for (Map.Entry<String, Integer> map : factoryMap.entrySet()) {
                FeedbackCountDto.FactoryCount factoryCount = new FeedbackCountDto.FactoryCount();
                if (map.getKey().equals(factory)) {
                    factoryCount.setFactory(factory);
                    factoryCount.setFactoryNum(map.getValue());
                    factoryCounts.add(factoryCount);
                }
            }

            feedbackCountDto.setFactoryCounts(factoryCounts);
            list.add(feedbackCountDto);
        }
        return list;
    }
}
