package org.devdynamos.view;

import org.devdynamos.contollers.CreatedJobsController;
import org.devdynamos.customerCellRenderers.CustomBooleanCellRenderer;
import org.devdynamos.interfaces.GetJobsListCallback;
import org.devdynamos.interfaces.InsertRequestCallback;
import org.devdynamos.models.Employee;
import org.devdynamos.models.ServiceJob;
import org.devdynamos.tableModels.ServiceJobsTableModel;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.NavPath;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.event.*;
import java.security.Key;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreatedJobs {
    private JPanel pnlRoot;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable tblJobs;
    private JLabel lblTotalEmp;
    private JButton btnBack;
    private JPanel pnlAllocate;
    private JButton btnAllocate;
    private JButton btnMakeFinished;

    private final RootView rootView;

    private final CreatedJobsController createdJobsController;
    private List<ServiceJob> jobsList;
    private ServiceJobsTableModel serviceJobsTableModel;

    public CreatedJobs(RootView rootView){
        this.rootView = rootView;
        this.createdJobsController = new CreatedJobsController();

        loadJobs();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadJobs() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Loading...");

        createdJobsController.getJobsList(new GetJobsListCallback() {
            @Override
            public void onSuccess(List<ServiceJob> jobs) {
                jobsList = jobs;
                renderTable();
                loadingSpinner.stop();
            }

            @Override
            public void onFailed(Exception ex) {
                jobsList = new ArrayList<>();
                renderTable();
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void renderTable() {
        serviceJobsTableModel = new ServiceJobsTableModel(jobsList);
        tblJobs.setModel(serviceJobsTableModel);

        TableRowSorter<ServiceJobsTableModel> sorter = new TableRowSorter<>(serviceJobsTableModel);
        tblJobs.setRowSorter(sorter);

        tblJobs.getColumnModel().getColumn(3).setCellRenderer(new CustomBooleanCellRenderer("Finished"));

        tblJobs.setFocusable(true);
        tblJobs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tblJobs.getTableHeader().setReorderingAllowed(false);
    }

    private void initButtons() {
        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // this will re-construct the view
                rootView.navigate(NavPath.SERVICES, new ServiceManagement(rootView).getRootPanel());
            }
        });

        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                filterTableData(txtSearch.getText());
            }
        });

        txtSearch.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(e.getKeyCode() == KeyEvent.VK_ENTER)
                    btnSearch.doClick();
            }
        });

        btnAllocate.setIcon(AssetsManager.getImageIcon("PersonAddIcon"));
        btnAllocate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                final ServiceJob selectedJob = serviceJobsTableModel.getJobAt(getSelectedRowIndex());
                rootView.navigate(NavPath.CREATED_JOBS, new AllocateEmployees(rootView, selectedJob).getRootPanel());
            }
        });

        btnMakeFinished.setIcon(AssetsManager.getImageIcon("CheckIcon"));
        btnMakeFinished.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ServiceJob selectedJob = serviceJobsTableModel.getJobAt(getSelectedRowIndex());
                makeJobFinished(selectedJob);
            }
        });

        tblJobs.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                btnAllocate.setEnabled(getSelectedRowIndex() > -1);
                btnMakeFinished.setEnabled(getSelectedRowIndex() > -1 && !serviceJobsTableModel.getJobAt(getSelectedRowIndex()).isFinished());
            }
        });

        tblJobs.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if(e.getClickCount() == 2 && !e.isConsumed())
                    btnAllocate.doClick();
            }
        });
    }

    private int getSelectedRowIndex() {
        if(tblJobs.getSelectedRow() == -1) return -1;

        int viewSelectedIndex = tblJobs.getSelectedRow();
        int modelSelectedIndex = tblJobs.convertRowIndexToModel(viewSelectedIndex);

        return modelSelectedIndex;
    }

    private void makeJobFinished(ServiceJob job) {
        final int res = JOptionPane.showConfirmDialog(null, "Are you sure you want to make this job finished?", "Are you sure?", JOptionPane.YES_NO_OPTION);
        if(res == 0){
            LoadingSpinner loadingSpinner = new LoadingSpinner();
            loadingSpinner.start("<html>Job finishing process is in progress...</html>");

            createdJobsController.finishJob(job, new InsertRequestCallback() {
                @Override
                public void onSuccess() {
                    loadingSpinner.stop();
                    JOptionPane.showMessageDialog(null, "Updated job!", "Successful", JOptionPane.INFORMATION_MESSAGE);
                    loadJobs();
                }

                @Override
                public void onFailed(Exception ex) {
                    loadingSpinner.stop();
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(null, "Error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            });
        }
    }

    private void filterTableData(String key) {
        TableRowSorter<?> sorter = (TableRowSorter<?>) tblJobs.getRowSorter();

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key, 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key, 1);

        if(!key.isEmpty()){
            sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)));
        }else{
            sorter.setRowFilter(null);
        }
    }
}
