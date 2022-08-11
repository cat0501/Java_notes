package cn.itcast.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
public class TbCities implements Serializable {
    private String cityid;

    private String city;

    private String provinceid;

}