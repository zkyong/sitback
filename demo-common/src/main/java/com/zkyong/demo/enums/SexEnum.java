package com.zkyong.demo.enums;

/**
 * 性别枚举
 * 
 * @author zkyong
 * @version v 0.1 
 * @date 2019年04月12日 14:51:37
 */
public enum SexEnum {
                     /** 性别-男 */
                     MALE(0, "男"),
                     /** 性别-女 */
                     FEMALE(1, "女"),
                     /** 性别-其他 */
                     OTHER(2, "其他"),;

    /** 编码 */
    private Integer code;

    /** 描述 */
    private String  desc;

    SexEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    /**
     * 根据code查找枚举
     * 
     * @param code
     * @return SexEnum
     */
    public static SexEnum getByCode(Integer code) {
        for (SexEnum value : values()) {
            if (value.getCode().intValue() == code) {
                return value;
            }
        }
        return null;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
