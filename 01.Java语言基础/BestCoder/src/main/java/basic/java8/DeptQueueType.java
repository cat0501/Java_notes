package basic.java8;

/**
 * 部门队列枚举类
 */
public enum DeptQueueType {

    SJPT("sjpt","数据平台事业部","root.sjpt"),
    SJZN("sjzn","数据智能事业部","root.sjzn"),
    DCSY("dcsy","电池溯源事业部","root.dcsy"),
    XPLAN("xplan","X计划事业部","root.xplan"),
    SJZT("sjzt","数据中台项目专用","root.sjzt"),
    ALL("all","数据中台全部资源","root");

    private final String deptCode;
    private final String deptName;
    private final String deptQueuesName;

    DeptQueueType(String deptCode, String deptName, String deptQueuesName) {
        this.deptCode=deptCode;
        this.deptName = deptName;
        this.deptQueuesName = deptQueuesName;
    }

    public String getDeptCode() {
        return deptCode;
    }

    public String getDeptName() {
        return deptName;
    }

    public String getDeptQueuesName() {
        return deptQueuesName;
    }


    public static DeptQueueType fromDeptName(String deptName) {
        for(DeptQueueType dept : DeptQueueType.values()) {
            if (dept.getDeptName().contains(deptName)) {
                return dept;
            }
        }
        return SJZT;
    }
}
