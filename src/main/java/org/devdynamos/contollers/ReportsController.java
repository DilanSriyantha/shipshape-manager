package org.devdynamos.contollers;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.*;
import org.devdynamos.Main;
import org.devdynamos.interfaces.GetRequestCallback;
import org.devdynamos.interfaces.InsertRequestCallback;
import org.devdynamos.models.GetRequestResultSet;
import org.devdynamos.models.ReportRecord;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.DBManager;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ReportsController {
    private final String REPORT_FILE_DIR = System.getProperty("user.dir") + "/Reports/";
    private final String REPORT_FILE_NAME = "ShipShape_Manager_Report_" + (int)(Math.random() * 10000) + ".pdf";
    private final String REPORT_FILE_PATH = REPORT_FILE_DIR + "/" + REPORT_FILE_NAME;

    public ReportsController() {

    }

    public void getOrderRecords (String startDateString, String endDateString, GetRequestCallback<ReportRecord> callback) {
        GetRequestResultSet<ReportRecord> resultSet = new GetRequestResultSet<>();
        resultSet.setResultList(new ArrayList<>());

        Thread loadServiceRecords = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<ReportRecord> recordList = DBManager.executeQuery(ReportRecord.class, "select * from (select (row_number() over (order by cos.customerOrderId)) as id, s.serviceName as caption, cos.quantity, (cos.quantity * s.unitPrice) as total, co.datePlaced from customerorderservices as cos join services as s on cos.serviceId = s.serviceId join customerorders as co on cos.customerOrderId = co.customerOrderId) as recs where recs.datePlaced between '" + startDateString + "' and '" + endDateString + "';");
                    for(ReportRecord record : recordList){
                        resultSet.getResultList().add(record);
                    }
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread loadProductRecords = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    List<ReportRecord> recordList = DBManager.executeQuery(ReportRecord.class, "select * from (select (row_number() over (order by cop.customerOrderId)) as id, sp.partName as caption, cop.quantity, (cop.quantity * sp.sellingPrice) as total, co.datePlaced from customerorderproducts as cop join spareparts as sp on cop.sparePartId = sp.partId join customerorders as co on cop.customerOrderId = co.customerOrderId) as recs where recs.datePlaced between '" + startDateString + "' and '" + endDateString + "';");
                    for(ReportRecord record : recordList){
                        resultSet.getResultList().add(record);
                    }
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread rearrangeIds = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadServiceRecords.join();
                    loadProductRecords.join();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }

                int i = 1;
                for(ReportRecord record : resultSet.getResultList()){
                    record.setId(i);
                    i++;
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    rearrangeIds.join();

                    callback.onSuccess(resultSet);
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        loadServiceRecords.start();
        loadProductRecords.start();
        rearrangeIds.start();
        waitingThread.start();
    }

    public void createReportPDF(String startDateString, String endDateString, List<ReportRecord> reportRecordList, double total, InsertRequestCallback callback){
        Thread createPDF = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    createPDF(startDateString, endDateString, reportRecordList, total);
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    createPDF.join();

                    callback.onSuccess();
                } catch (InterruptedException ex) {
                    callback.onFailed(ex);
                }
            }
        });

        createPDF.start();
        waitingThread.start();
    }

    private void createPDF(String startDateString, String endDateString, List<ReportRecord> reportRecordList, double total) throws Exception {
        File dir = new File(REPORT_FILE_DIR);
        dir.mkdir();

        File file = new File(dir, REPORT_FILE_NAME);

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        OutputStream outputStream = new FileOutputStream(file);

        Document document = new Document(PageSize.A4);

        PdfWriter writer = PdfWriter.getInstance(document, outputStream);
        writer.setPageEvent(new CustomPdfPageEventListener());
        document.open();

        InputStream is = Main.class.getResourceAsStream("/images/shipshapelogo.png");
        if(is == null)
            throw new IllegalArgumentException("invalid image file!");

        BufferedImage bi = ImageIO.read(is);
        ByteArrayOutputStream buffer = new ByteArrayOutputStream();
        ImageIO.write(bi, "png", buffer);

        Image logo = Image.getInstance(buffer.toByteArray());
        logo.setAlignment(Image.ALIGN_CENTER);
        logo.scalePercent(5);

        document.add(logo);

        Paragraph p = new Paragraph("The ShipShape Company");
        p.setAlignment(Element.ALIGN_CENTER);

        Paragraph p2 = new Paragraph("Income Report");
        p2.setAlignment(Element.ALIGN_CENTER);

        document.add(p);
        document.add(p2);

        document.add(new Paragraph(" "));
        document.add(new Paragraph(" "));

        PdfPTable table0 = new PdfPTable(2);
        table0.setWidthPercentage(50);
        table0.setHorizontalAlignment(Element.ALIGN_RIGHT);

        PdfPCell cell1 = new PdfPCell();
        cell1.addElement(new Paragraph("Start Date"));

        PdfPCell cell2 = new PdfPCell();
        cell2.addElement(new Paragraph("End Date"));

        table0.addCell(cell1);
        table0.addCell(cell2);

        table0.addCell(startDateString);
        table0.addCell(endDateString);

        table0.setSpacingAfter(5f);

        PdfPTable table1 = new PdfPTable(5);
        table1.setWidthPercentage(100);

        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(new Paragraph("#"));

        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(new Paragraph("Caption"));

        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table1.addCell(new Paragraph("Quantity"));

        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table1.addCell(new Paragraph("Total"));

        table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
        table1.addCell(new Paragraph("Date"));

        for (int i = 0; i < reportRecordList.size(); i++) {
            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table1.addCell(new Paragraph(String.valueOf(reportRecordList.get(i).getId())));

            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(new Paragraph(reportRecordList.get(i).getCaption()));

            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table1.addCell(new Paragraph(String.valueOf(reportRecordList.get(i).getQuantity())));

            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
            table1.addCell(new Paragraph(String.valueOf(reportRecordList.get(i).getTotal())));

            table1.getDefaultCell().setHorizontalAlignment(Element.ALIGN_CENTER);
            table1.addCell(new Paragraph(formatter.format(reportRecordList.get(i).getDatePlaced())));
        }

        PdfPTable table2 = new PdfPTable(2);
        table2.setSpacingBefore(5f);
        table2.setWidthPercentage(100);
        table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_LEFT);
        table2.addCell(new Paragraph("Total Income (LKR)"));
        table2.getDefaultCell().setHorizontalAlignment(Element.ALIGN_RIGHT);
        table2.addCell(new Paragraph(String.valueOf(total)));

        document.add(table0);
        document.add(table1);
        document.add(table2);

        document.close();
    }

    private class CustomPdfPageEventListener extends PdfPageEventHelper {
        @Override
        public void onEndPage(PdfWriter writer, Document document) {
            try{
                PdfContentByte cb = writer.getDirectContent();

                Phrase footer = new Phrase("POWERED BY DevDynamos | SLIIT City Uni | 2022 July Batch");

                ColumnText.showTextAligned(cb, Element.ALIGN_CENTER, footer, (document.right() - document.left()) / 2 + document.leftMargin(), document.bottom() - 10, 0);
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }

    }
}
