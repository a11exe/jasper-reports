package com.alllexe.jasperreport.service;

import com.alllexe.jasperreport.data.BeanList;
import com.alllexe.jasperreport.data.DataBeanList;
import com.alllexe.jasperreport.data.DataBeanListWithSubreport;
import com.alllexe.jasperreport.model.Bean;
import com.fasterxml.jackson.databind.ObjectMapper;
import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class JasperReportFill {

    DataBeanList dataBeanList;
    DataBeanListWithSubreport dataBeanListWithSubreport;
    ObjectMapper objectMapper;

    public JasperReportFill(DataBeanList dataBeanList,
                            DataBeanListWithSubreport dataBeanListWithSubreport,
                            ObjectMapper objectMapper) {
        this.dataBeanList = dataBeanList;
        this.dataBeanListWithSubreport = dataBeanListWithSubreport;
        this.objectMapper = objectMapper;
    }

    public byte[] fill(String template) throws Exception {

        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // сгенерим шаблон и заполним его данными
            JasperPrint jasperPrint = generateJasperPrint(template, dataBeanList);
            // Экспортируем ПДФ в стрим
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            // Возвращаем в виде массива байтов
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("Ошибка в формировании отчета: " + e.getLocalizedMessage());
            throw e;
        }
    }

    private JasperPrint generateJasperPrint(String template, BeanList beanList) throws Exception {
        // иницализируем datasource
        JRDataSource dataSource = new JREmptyDataSource();

        // заполняем параметры
        Map<String, Object> params = getParams();
        params.put("SUBREPORT",
                this.getClass().getClassLoader().getResourceAsStream("./reports/subreport.jrxml"));

        // компилируем шаблон
        JasperReport jasperReport = compileReport("./reports" + template);
        // заполняем шаблон данными
        List<Bean> dataList = beanList.getDataBeanList();
        JRBeanCollectionDataSource beanColDataSource =
                new JRBeanCollectionDataSource(dataList);
        return JasperFillManager.fillReport(
                jasperReport, params, beanColDataSource);
    }

    private JasperReport compileReport(final String location) throws JRException {
        return JasperCompileManager.compileReport(JRLoader.getLocationInputStream(location));
    }

    protected Map<String, Object> getParams() {
        Map<String, Object> params = new HashMap<>();
//        params.put(JsonQueryExecuterFactory.JSON_DATE_PATTERN, "dd.MM.yyyy");
//        params.put(JsonQueryExecuterFactory.JSON_NUMBER_PATTERN, "#,##0.##");
//        params.put(JsonQueryExecuterFactory.JSON_LOCALE, Locale.ENGLISH);
//        params.put(JRParameter.REPORT_LOCALE, new Locale("ru_RU"));
        /**
         * Passing ReportTitle and Author as parameters
         */
        params.put("ReportTitle", "List of Contacts");
        params.put("Author", "Prepared By Manisha");

        return params;
    }

    public byte[] fillSubreports(String template) throws Exception {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            // сгенерим шаблон и заполним его данными
            JasperPrint jasperPrint = generateJasperPrint(template, dataBeanListWithSubreport);
            // Экспортируем ПДФ в стрим
            JasperExportManager.exportReportToPdfStream(jasperPrint, outputStream);
            // Возвращаем в виде массива байтов
            return outputStream.toByteArray();
        } catch (Exception e) {
            System.err.println("Ошибка в формировании отчета: " + e.getLocalizedMessage());
            throw e;
        }
    }
}
