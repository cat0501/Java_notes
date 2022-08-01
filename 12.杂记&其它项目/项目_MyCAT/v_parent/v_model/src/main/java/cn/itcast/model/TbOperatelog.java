package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TbOperatelog implements Serializable {

    private Long id;

    private String modelName;

    private String modelValue;

    private String returnValue;

    private String returnClass;

    private String operateUser;

    private String operateTime;

    private String paramAndValue;

    private String operateClass;

    private String operateMethod;

    private Long costTime;

}