package com.shirtstore.service;

import com.shirtstore.dao.JPADAO;
import com.shirtstore.entity.ReportDTO;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.export.SimpleExporterInput;
import net.sf.jasperreports.export.SimpleOutputStreamExporterOutput;
import net.sf.jasperreports.export.SimpleXlsxReportConfiguration;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class JasperReportService {
    public String reportByDateRange(String format, Date startDate, Date endDate, int step, int product_id, String typeReport) throws ParseException, FileNotFoundException, JRException {
        String path = "C:\\Users\\phat\\Downloads";

        List<ReportDTO> reportDTOList = new ArrayList<>();

        if (typeReport.equals("Revenue")){
            reportDTOList = new JPADAO<>().getOrderRevenue(startDate, endDate, step, product_id);
        } else if(typeReport.equals("Profits")){
            reportDTOList = new JPADAO<>().getProfits(startDate, endDate, step, product_id);
        }

        // Tải file jrxml
        File file = ResourceUtils.getFile("classpath:revenueReports.jrxml");
        JasperReport jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());

        // Tạo dữ liệu cho báo cáo
        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(reportDTOList);

        // Tham số truyền vào báo cáo
        Map<String, Object> params = new HashMap<>();

        // Lấp đầy báo cáo
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, params, dataSource);

        // Lấy ngày và giờ hiện tại để thêm vào tên tệp
        SimpleDateFormat dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd_HH-mm-ss");
        String currentDateTime = dateTimeFormat.format(new Date());

        String outputPath = "";

        // Kiểm tra định dạng được yêu cầu
        if ("pdf".equalsIgnoreCase(format)) {
            if (product_id == 0){
                if(typeReport.equals("Revenue")) {
                    outputPath = path + "\\Revenue_All_Product_" + currentDateTime + ".pdf";
                } else if (typeReport.equals("Profits")) {
                    outputPath = path + "\\Profits_All_Product_" + currentDateTime + ".pdf";
                }
            } else {
                if(typeReport.equals("Revenue")) {
                    outputPath = path + "\\Revenue_For_Product_" + product_id + "_" + currentDateTime + ".pdf";
                } else if (typeReport.equals("Profits")) {
                    outputPath = path + "\\Profits_For_Product_" + product_id + "_" + currentDateTime + ".pdf";
                }
            }
            JasperExportManager.exportReportToPdfFile(jasperPrint, outputPath);
        } else if ("xlsx".equalsIgnoreCase(format)) {
            if (product_id == 0){
                if(typeReport.equals("Revenue")) {
                    outputPath = path + "\\Revenue_All_Product_" + currentDateTime + ".xlsx";
                } else if (typeReport.equals("Profits")) {
                    outputPath = path + "\\Profits_All_Product_" + currentDateTime + ".xlsx";
                }
            }else {
                if(typeReport.equals("Revenue")) {
                    outputPath = path + "\\Revenue_For_Product_" + product_id + "_" + currentDateTime + ".xlsx";
                } else if (typeReport.equals("Profits")) {
                    outputPath = path + "\\Profits_For_Product_" + product_id + "_" + currentDateTime + ".xlsx";
                }
            }

            JRXlsxExporter exporter = new JRXlsxExporter();

            exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
            exporter.setExporterOutput(new SimpleOutputStreamExporterOutput(outputPath));

            // Cấu hình xuất file Excel
            SimpleXlsxReportConfiguration xlsxConfig = new SimpleXlsxReportConfiguration();
            xlsxConfig.setDetectCellType(true); // Phát hiện kiểu dữ liệu trong ô
            xlsxConfig.setCollapseRowSpan(false); // Giữ nguyên định dạng row span
            xlsxConfig.setOnePagePerSheet(false); // Không ép mỗi trang thành 1 sheet

            exporter.setConfiguration(xlsxConfig);
            exporter.exportReport();
        } else {
            throw new IllegalArgumentException("Unsupported format: " + format);
        }

        return "Report generated successfully in path: " + outputPath;
    }
}
