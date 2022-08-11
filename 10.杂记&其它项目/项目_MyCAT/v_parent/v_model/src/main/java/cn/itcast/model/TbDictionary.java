package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@NoArgsConstructor
public class TbDictionary implements Serializable {

    private Integer id;

    private Integer codeid;

    private String codetype;

    private String codename;

    private String codevalue;

    private String description;

    private Date createtime;

    private Date updatetime;

    private Integer createuser;

    private Integer updateuser;

}