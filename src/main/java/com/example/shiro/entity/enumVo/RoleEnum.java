package com.example.shiro.entity.enumVo;

public enum RoleEnum {
    SUPER_ADMIN(1l, 0),
    OPERATOR(4l, 1),
    GENERAL_USER(3l, 2),
    ADMIN(2l, 3);

    private Long roleId;
    private int index;

    RoleEnum(Long roleId, int index) {
        this.roleId = roleId;
        this.index = index;
    }

    public Long getRoleId() {
        return roleId;
    }

    public int getIndex() {
        return index;
    }

    // 根据时间字符串获取对应的枚举值
    public static RoleEnum getByTime(String roleId) {
        for (RoleEnum value : values()) {
            if (value.roleId.equals(roleId)) {
                return value;
            }
        }
        return null; // 或者抛出异常，表示未找到对应时间
    }

    public static void main(String[] args) {
        // 通过时间字符串获取索引
        int index0800 = RoleEnum.getByTime("08:00").getIndex();
        System.out.println("Index for 08:00: " + index0800);

        // 通过索引获取时间字符串
        Long roleIdAtIndex1 = RoleEnum.values()[1].getRoleId();
        System.out.println("Time at index 1: " + roleIdAtIndex1);
    }
}
