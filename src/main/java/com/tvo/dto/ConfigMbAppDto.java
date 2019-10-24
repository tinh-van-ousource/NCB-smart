package com.tvo.dto;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConfigMbAppDto {

  private Long id;

  private String code;

  private String value;

  private String name;

  private String type;

  private String sort;

  private String description;

}
