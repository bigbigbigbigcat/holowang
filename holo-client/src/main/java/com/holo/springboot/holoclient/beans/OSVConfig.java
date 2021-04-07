package com.holo.springboot.holoclient.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OSVConfig {
    private String asr_app_id;
    private String asr_company;
    private String asr_token_id;

}
