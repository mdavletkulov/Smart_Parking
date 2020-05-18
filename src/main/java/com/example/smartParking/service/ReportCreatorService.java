package com.example.smartParking.service;

import com.example.smartParking.model.ReportEntity;
import org.apache.poi.xwpf.usermodel.ParagraphAlignment;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFParagraph;
import org.apache.poi.xwpf.usermodel.XWPFRun;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTP;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTR;
import org.openxmlformats.schemas.wordprocessingml.x2006.main.CTText;
import org.springframework.stereotype.Service;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.UUID;

@Service
public class ReportCreatorService {

    public String createDocxReport(List<ReportEntity> reportEntities) {
        try {
            // создаем модель docx документа,
            // к которой будем прикручивать наполнение (колонтитулы, текст)
            XWPFDocument docxModel = new XWPFDocument();

            XWPFParagraph bodyParagraph = docxModel.createParagraph();
            bodyParagraph.setAlignment(ParagraphAlignment.LEFT);
            int i = 1;
            for (ReportEntity reportEntity : reportEntities) {
                XWPFRun paragraphConfig = bodyParagraph.createRun();
                paragraphConfig.setFontSize(16);
                paragraphConfig.setFontFamily("Times New Roman");
                paragraphConfig.setBold(true);
                paragraphConfig.setText("Парковочное событие номер " + i);
                paragraphConfig = bodyParagraph.createRun();
                paragraphConfig.setFontSize(14);
                paragraphConfig.setFontFamily("Times New Roman");
                // HEX цвет без решетки #
                generateReportText(reportEntity, paragraphConfig, bodyParagraph);
                paragraphConfig = bodyParagraph.createRun();
                generateViolationText(reportEntity, paragraphConfig, bodyParagraph);
                paragraphConfig = bodyParagraph.createRun();
                paragraphConfig.addBreak();
                paragraphConfig.addBreak();
                i++;
            }

            // сохраняем модель docx документа в файл
            String home = System.getProperty("user.home");
            String path = home + "/Downloads/report" + UUID.randomUUID().toString() + ".docx";
            FileOutputStream outputStream = new FileOutputStream(path);
            docxModel.write(outputStream);
            outputStream.close();
            return path.replace("/", "\\");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private void generateReportText(ReportEntity reportEntity, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        String parkingName = reportEntity.getParkingName();
        String placeNum = reportEntity.getPlaceNum().toString();
        String autoNum = reportEntity.getAutoNum();
        String personName = reportEntity.getPersonName();
        String startTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(reportEntity.getStartTime());
        String endTime = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(reportEntity.getEndTime());
        String division = reportEntity.getDivision();
        String subdivision = reportEntity.getSubdivision();
        String jobPosition = reportEntity.getJobPosition();
        String group = reportEntity.getGroup();
        String course = reportEntity.getCourse();
        String autoModel = reportEntity.getAutoModel();
        writeLine("Название парковки: ", parkingName, paragraphConfig, bodyParagraph);
        writeLine("Номер места: ", placeNum, paragraphConfig, bodyParagraph);
        writeLine("Номер автомобиля: ", autoNum, paragraphConfig, bodyParagraph);
        writeLine("Марка автомобиля: ", autoModel, paragraphConfig, bodyParagraph);
        writeLine("Время начала парковки: ", startTime, paragraphConfig, bodyParagraph);
        writeLine("Время окончания парковки: ", endTime, paragraphConfig, bodyParagraph);
        writeLine("Владелец автомобиля: ", personName, paragraphConfig, bodyParagraph);
        appendIfNotNull("Институт: ", division, paragraphConfig, bodyParagraph);
        appendIfNotNull("Кафедра: ", subdivision, paragraphConfig, bodyParagraph);
        appendIfNotNull("Должность: ", jobPosition, paragraphConfig, bodyParagraph);
        appendIfNotNull("Группа: ", group, paragraphConfig, bodyParagraph);
        appendIfNotNull("Курс: ", course, paragraphConfig, bodyParagraph);
    }

    private void writeLine(String name, String value, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.addBreak();
        paragraphConfig.setBold(true);
        paragraphConfig.setText(name);
        paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.setBold(false);
        paragraphConfig.setText(value);
    }

    private void writeViolationLine(String name, String value, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.setColor("FF0000");
        paragraphConfig.addBreak();
        paragraphConfig.setBold(true);
        paragraphConfig.setText(name);
        paragraphConfig = bodyParagraph.createRun();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        paragraphConfig.setColor("FF0000");
        paragraphConfig.setBold(false);
        paragraphConfig.setText(value);
    }

    private void generateViolationText(ReportEntity reportEntity, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        String passViolation = reportEntity.getPassViolation();
        String statusViolation = reportEntity.getStatusViolation();
        paragraphConfig.setFontSize(14);
        paragraphConfig.setFontFamily("Times New Roman");
        if (passViolation != null || statusViolation != null) {
            paragraphConfig.setColor("FF0000");
            writeViolationLine("Нарушения: ", "", paragraphConfig, bodyParagraph);
            if (passViolation != null)
                writeViolationLine("", passViolation, paragraphConfig, bodyParagraph);
            if (statusViolation != null)
                writeViolationLine("", statusViolation, paragraphConfig, bodyParagraph);
        }
    }

    private void appendIfNotNull(String name, String value, XWPFRun paragraphConfig, XWPFParagraph bodyParagraph) {
        if (value != null && !value.isBlank()) {
            writeLine(name, value, paragraphConfig, bodyParagraph);
        }
    }

    private static CTP createFooterModel(String footerContent) {
        // создаем футер или нижний колонтитул
        CTP ctpFooterModel = CTP.Factory.newInstance();
        CTR ctrFooterModel = ctpFooterModel.addNewR();
        CTText cttFooter = ctrFooterModel.addNewT();

        cttFooter.setStringValue(footerContent);
        return ctpFooterModel;
    }

    private static CTP createHeaderModel(String headerContent) {
        // создаем хедер или верхний колонтитул
        CTP ctpHeaderModel = CTP.Factory.newInstance();
        CTR ctrHeaderModel = ctpHeaderModel.addNewR();
        CTText cttHeader = ctrHeaderModel.addNewT();

        cttHeader.setStringValue(headerContent);
        return ctpHeaderModel;
    }

    private String chooseSaveFolder() {
        JFrame parentFrame = new JFrame();

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setDialogTitle("Specify a file to save");

        int userSelection = fileChooser.showSaveDialog(parentFrame);

        if (userSelection == JFileChooser.APPROVE_OPTION) {
            File fileToSave = fileChooser.getSelectedFile();
            return fileToSave.getAbsolutePath();
        }
        return null;
    }

}