package com.alllexe.jasperreport.controller;

import com.alllexe.jasperreport.service.JasperReportFill;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.awt.image.BufferedImage;

@RestController
public class ApiController {

    JasperReportFill jasperReportFill;

    public ApiController(JasperReportFill jasperReportFill) {
        this.jasperReportFill = jasperReportFill;
    }

    @GetMapping(value = "/api/report/simple",
            produces = {MediaType.APPLICATION_PDF_VALUE})
    public byte[] getReport() throws Exception {
        return jasperReportFill.fill("/jasper_report_template.jrxml");
    }

    @GetMapping(value = "/api/report/fonts",
            produces = {MediaType.APPLICATION_PDF_VALUE})
    public byte[] getReportFonts() throws Exception {
        return jasperReportFill.fill("/jasper_report_fonts.jrxml");
    }

    @GetMapping(value = "/api/report/styles",
            produces = {MediaType.APPLICATION_PDF_VALUE})
    public byte[] getReportStyles() throws Exception {
        return jasperReportFill.fill("/jasper_report_styles.jrxml");
    }

    @GetMapping(value = "/api/report/scriplet",
            produces = {MediaType.APPLICATION_PDF_VALUE})
    public byte[] getReportScriplets() throws Exception {
        return jasperReportFill.fill("/jasper_report_scriplet.jrxml");
    }

    @GetMapping(value = "/api/report/subreports",
            produces = {MediaType.APPLICATION_PDF_VALUE})
    public byte[] getReportSubreports() throws Exception {
        return jasperReportFill.fillSubreports("/jasper_report_subreports.jrxml");
    }

    @GetMapping(value = "/api/report/groups",
            produces = {MediaType.APPLICATION_PDF_VALUE})
    public byte[] getReportGroups() throws Exception {
        return jasperReportFill.fill("/jasper_report_groups.jrxml");
    }


}
