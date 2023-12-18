package com.studentreg.demo.service.Impl;

import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.studentreg.demo.model.Student;
import com.studentreg.demo.service.JasperReportService;
import com.studentreg.demo.service.StudentService;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;

@Service
public class JasperReportServiceImpl implements JasperReportService {

    @Autowired
    private StudentService studentService;

    @Override
    public byte[] generateReport() {
        try {
            List<Student> students = studentService.getAllStudents();

            // Load JRXML template
            InputStream templateStream = getClass().getResourceAsStream("/jasper-reports/studentsReport.jrxml");
            JasperReport jasperReport = JasperCompileManager.compileReport(templateStream);

            // Set up data source
            JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(students);

            // Fill report
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, null, dataSource);

            // Export to PDF
            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
