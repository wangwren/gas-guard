package com.gas.enums;

public enum ProvinceEnum {

    TongZhou("110112", "通州区"),
    ShunYi("110113", "顺义区"),
    ChangPing("110114", "昌平区"),
    DaXing("110115", "大兴区"),
    HuaiRou("110116", "怀柔区"),
    PingGu("110117", "平谷区"),
    MiYun("110118", "密云区"),
    YanQing("110119", "延庆区"),
    DongCheng("110101", "东城区"),
    XiCheng("110102", "西城区"),
    ChaoYang("110105", "朝阳区"),
    FengTai("110106", "丰台区"),
    ShiJingShan("110107", "石景山区"),
    HaiDian("110108", "海淀区"),
    MenTouGou("110109", "门头沟区"),
    FangShan("110111", "房山区"),
    ;

    private String code;
    private String name;

    ProvinceEnum(String code, String name) {
        this.code = code;
        this.name = name;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public static String getCodeByName(String name) {
        for (ProvinceEnum value : ProvinceEnum.values()) {
            if (value.getName().equals(name)) {
                return value.getCode();
            }
        }
        return "000";
    }
}
