package last;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Permission {

    private int id;

    // 授权资源类型：1库 2表
    private int type;

    // 授权资源id
    private int resourceId;

    // 授权对象类型：1用户 2权限组
    private String authType;

    // 授权对象id
    private int authId;

    // 授权对象名称
    private String authName;
    private String authNameEn;

    // 权限字符
    private String permissionStr;

    // 字段集合
    private String columnStr;

    // 策略名称
    private String policyName;

    private int policyId;

    // 权限到期日期
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date endTime;

    // 更新时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date updateTime;

    // 授权时间
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private Date authTime;

    // 标识
    private int flag;

    // 是否可以编辑/授权（1否 0是）
    private int operateFlag;

}
