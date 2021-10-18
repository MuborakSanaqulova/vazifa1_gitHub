package uz.pdp.vazifa1.dto;

import lombok.Data;

import javax.persistence.Column;

@Data
public class RoomDto {

    private Integer number;
    private Integer floor;
    private String size;
    private Integer universityId;
}
