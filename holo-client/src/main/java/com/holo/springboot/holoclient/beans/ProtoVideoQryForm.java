package com.holo.springboot.holoclient.beans;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProtoVideoQryForm extends OsvBaseForm {
    private String business_kind;//业务类别
    private String acpt_id;//受理单号
    private String finance_type;//金融品种
    private String prod_type;//产品类别
    private String prodta_no;//产品TA编号
    private String prod_code;//产品代码

}
