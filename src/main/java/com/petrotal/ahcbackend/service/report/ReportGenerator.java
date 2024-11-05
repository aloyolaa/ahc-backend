package com.petrotal.ahcbackend.service.report;

import com.petrotal.ahcbackend.entity.Data;
import com.petrotal.ahcbackend.exception.ReportGeneratorException;
import com.petrotal.ahcbackend.service.data.DataAccessService;
import com.petrotal.ahcbackend.service.data.SignatoryService;
import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.util.JRLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class ReportGenerator {
    private final DataAccessService dataAccessService;
    private final SignatoryService signatoryService;
    private final ResourceLoader resourceLoader;

    public String generateReport(String voucherNumber) {
        try {
            Data data = dataAccessService.findByVoucherNumber(voucherNumber);

            Resource reportFile = resourceLoader
                    .getResource("classpath:templates/report/FuelVoucher.jasper");

            Resource logo = resourceLoader
                    .getResource("classpath:static/images/logo.png");

            Resource signature = resourceLoader
                    .getResource("classpath:static/images/firma.svg");

            Map<String, Object> reportParameters = new HashMap<>();


            reportParameters.put("logo", logo.getInputStream());

            reportParameters.put("voucherNumber", data.getVoucherNumber());
            reportParameters.put("dispatchDate", data.getDispatchDate().toString());
            reportParameters.put("materialStatus", data.getMaterialStatus());
            reportParameters.put("usageDetail", data.getUsageDetail());
            reportParameters.put("observations", data.getObservations());
            reportParameters.put("area", data.getArea().getName());
            reportParameters.put("contractor", data.getContractor().getName());
            reportParameters.put("equipment", data.getEquipment().getName());
            reportParameters.put("ds", new JRBeanCollectionDataSource(data.getDataDetails()));
            /*reportParameters.put("signature01", signatoryService.getByUser(data.getDataSignatories().get(0).getUser().getUsername()));
            reportParameters.put("signature02", signatoryService.getByUser(data.getDataSignatories().get(1).getUser().getUsername()));
            reportParameters.put("signature03", signatoryService.getByUser(data.getDataSignatories().get(2).getUser().getUsername()));
            reportParameters.put("signature04", signatoryService.getByUser(data.getDataSignatories().get(3).getUser().getUsername()));*/

            /*reportParameters.put("signature01", signature.getInputStream());
            reportParameters.put("signature02", signature.getInputStream());
            reportParameters.put("signature03", signature.getInputStream());
            reportParameters.put("signature04", signature.getInputStream());*/

            JasperReport report = (JasperReport) JRLoader.loadObject(reportFile.getInputStream());

            JasperPrint reportPrint = JasperFillManager.fillReport(report, reportParameters, new JREmptyDataSource());

            byte[] reportPdf = JasperExportManager.exportReportToPdf(reportPrint);

            return Base64.getEncoder().encodeToString(reportPdf);
        } catch (Exception e) {
            throw new ReportGeneratorException("No se puedo generar el reporte. Inténtelo más tarde." + e.getMessage());
        }
    }
}
