package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TbCategory implements Serializable {
    private Integer id;

    private String name;

    private Integer goodsNum;

    private String isShow;

    private String isMenu;

    private Integer seq;

    private Integer parentId;

}