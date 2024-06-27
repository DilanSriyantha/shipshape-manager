package org.devdynamos.view;

import org.devdynamos.components.DatePicker;
import org.devdynamos.contollers.ReportsController;
import org.devdynamos.interfaces.GetRequestCallback;
import org.devdynamos.interfaces.InsertRequestCallback;
import org.devdynamos.models.GetRequestResultSet;
import org.devdynamos.models.ReportRecord;
import org.devdynamos.tableModels.ReportTableModel;
import org.devdynamos.utils.AssetsManager;

import javax.swing.*;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class GenerateReports {
    private JTable tblReportRecords;
    private JLabel lblTotalEmp;
    private JButton btnBack;
    private JPanel pnlAllocate;
    private JButton btnGenerate;
    private JButton btnSave;
    private JPanel pnlRoot;
    private JButton btnSetStartDate;
    private JButton btnSetEndDate;
    private JLabel lblStartDate;
    private JLabel lblEndDate;
    private JLabel lblTotalSales;

    private final RootView rootView;
    private final ReportsController reportsController;
    private ReportTableModel reportTableModel;

    private List<ReportRecord> reportRecordList = new ArrayList<>();

    private String startDateString = formatDate(new Date());
    private String endDateString = formatDate(new Date());
    private double total = 0.00d;

    public GenerateReports(RootView rootView){
        this.rootView = rootView;
        this.reportsController = new ReportsController();

        renderTable();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadData() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Loading data...");

        reportsController.getOrderRecords(startDateString, endDateString, new GetRequestCallback<ReportRecord>() {
            @Override
            public void onSuccess(GetRequestResultSet<ReportRecord> resultSet) {
                loadingSpinner.stop();
                reportRecordList = resultSet.getResultList();

                if(!reportRecordList.isEmpty())
                    btnSave.setEnabled(true);
                else
                    btnSave.setEnabled(false);

                total = calculateTotalSales();
                lblTotalSales.setText(String.valueOf(total) + " LKR");
                renderTable();
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, "Error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    }

    private void renderTable() {
        tblReportRecords.removeAll();

        reportTableModel = new ReportTableModel(reportRecordList);
        tblReportRecords.setModel(reportTableModel);

        TableRowSorter<ReportTableModel> sorter = new TableRowSorter<>(reportTableModel);
        tblReportRecords.setRowSorter(sorter);

        tblReportRecords.setFocusable(false);
        tblReportRecords.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblReportRecords.getTableHeader().setReorderingAllowed(false);
    }

    private void initButtons() {
        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.goBack();
            }
        });

        btnGenerate.setIcon(AssetsManager.getImageIcon("GenerateIcon"));
        btnGenerate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadData();
            }
        });

        btnSave.setIcon(AssetsManager.getImageIcon("SaveIcon"));
        btnSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                savePDF();
            }
        });

        btnSetStartDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DatePickerDialog(
                        (dialog, date) -> {
                            lblStartDate.setText(formatDate(date));
                            startDateString = formatDate(date);
                            dialog.dispose();
                        },
                        Window::dispose
                ).showDialog();
            }
        });

        btnSetEndDate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new DatePickerDialog(
                        (dialog, date) -> {
                            lblEndDate.setText(formatDate(date));
                            endDateString = formatDate(date);
                            dialog.dispose();
                        },
                        Window::dispose
                ).showDialog();
            }
        });
    }

    private String formatDate(Date date){
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        return formatter.format(date);
    }

    private double calculateTotalSales() {
        double totalSales = 0.0d;
        for(ReportRecord record : reportRecordList){
            totalSales += record.getTotal();
        }

        return totalSales;
    }

    private void savePDF() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("<html>Please wait...</html>");

        reportsController.createReportPDF(startDateString, endDateString, reportRecordList, total, new InsertRequestCallback() {
            @Override
            public void onSuccess() {
                loadingSpinner.stop();

                JOptionPane.showMessageDialog(null, "Report generated successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();

                JOptionPane.showMessageDialog(null, "Error occurred.", "Error", JOptionPane.ERROR_MESSAGE);

                ex.printStackTrace();
            }
        });
    }
}