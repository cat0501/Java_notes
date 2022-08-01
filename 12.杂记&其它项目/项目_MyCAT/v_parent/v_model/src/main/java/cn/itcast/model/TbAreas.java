package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import java.io.Serializable;

@Data
@NoArgsConstructor
public class TbAreas implements Serializable {
    private String areaid;

    private String area;

    private String cityid;

}