package com.mrkenobii.ecommerceapp.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopicDTO {
    private Integer id;
    private String title;
    private String subHeading;
    private String article;
    private String startDate;
    private String closingDate;
}
