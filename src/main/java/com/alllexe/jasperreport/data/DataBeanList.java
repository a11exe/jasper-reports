package com.alllexe.jasperreport.data;

import com.alllexe.jasperreport.model.Bean;
import com.alllexe.jasperreport.model.DataBean;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class DataBeanList implements BeanList {

    @Override
    public List<Bean> getDataBeanList() {
        List<Bean> dataBeanList = new ArrayList<Bean>();

        for (int i = 0; i < 50; i++) {
            dataBeanList.add(produce("Manisha", ""));
            dataBeanList.add(produce("Manisha", "India"));
            dataBeanList.add(produce("Dennis Ritchie", "USA"));
            dataBeanList.add(produce("V.Anand", "India"));
            dataBeanList.add(produce("Shrinath", "California"));
        }

        return dataBeanList;
    }

    /**
     * This method returns a DataBean object,
     * with name and country set in it.
     */
    private DataBean produce(String name, String country) {
        DataBean dataBean = new DataBean();
        dataBean.setName(name);
        dataBean.setCountry(country);

        return dataBean;
    }
}
