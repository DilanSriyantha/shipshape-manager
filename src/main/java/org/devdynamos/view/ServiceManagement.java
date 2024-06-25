package org.devdynamos.view;

import org.devdynamos.contollers.ServiceController;
import org.devdynamos.interfaces.DeleteServiceCallback;
import org.devdynamos.interfaces.GetServicesListCallback;
import org.devdynamos.interfaces.InsertServiceCallback;
import org.devdynamos.interfaces.UpdateServiceCallback;
import org.devdynamos.models.Service;
import org.devdynamos.utils.ArrayUtils;
import org.devdynamos.utils.AssetsManager;
import org.devdynamos.utils.Console;
import org.devdynamos.tableModels.ServicesTableModel;
import org.devdynamos.utils.NavPath;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ServiceManagement {

    private JPanel pnlRoot;
    private JTextField txtSearch;
    private JButton btnSearch;
    private JTable tblServices;
    private JLabel lblTotalEmp;
    private JButton btnBack;
    private JPanel pnlAllocate;
    private JButton btnInsert;
    private JButton btnUpdate;
    private JButton btnDelete;
    private JButton btnCreatedJobs;

    private final ServiceController serviceController;
    private ServicesTableModel servicesTableModel;
    private final RootView rootView;

    private List<Service> serviceList = new ArrayList<>();

    public ServiceManagement(RootView rootView){
        this.rootView = rootView;
        this.serviceController = new ServiceController();

        loadServices();
        renderTable();
        initButtons();
    }

    public JPanel getRootPanel() {
        return pnlRoot;
    }

    private void loadServices() {
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Loading...");

        serviceController.getServicesList(new GetServicesListCallback() {
            @Override
            public void onSuccess(List<Service> _serviceList) {
                serviceList = _serviceList;

                for (Service service : _serviceList){
                    Console.log(service.toString());
                }

                loadingSpinner.stop();
                renderTable();
            }

            @Override
            public void onFailed(Exception ex) {
                ex.printStackTrace();
                loadingSpinner.stop();
            }
        });
    }

    private void renderTable() {
        tblServices.removeAll();

        servicesTableModel = new ServicesTableModel(serviceList);
        tblServices.setModel(servicesTableModel);

        TableRowSorter<ServicesTableModel> sorter = new TableRowSorter<>(servicesTableModel);
        tblServices.setRowSorter(sorter);

        tblServices.setFocusable(true);
        tblServices.getTableHeader().setReorderingAllowed(false);
        tblServices.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void initButtons() {
        btnBack.setIcon(AssetsManager.getImageIcon("BackIcon"));
        btnBack.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.navigate(NavPath.HOME, new HomeView(rootView).getRootPanel());
            }
        });

        btnCreatedJobs.setIcon(AssetsManager.getImageIcon("NextIcon"));
        btnCreatedJobs.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rootView.navigate(NavPath.CREATED_JOBS, new CreatedJobs(rootView).getRootPanel());
            }
        });

        btnInsert.setIcon(AssetsManager.getImageIcon("AddIcon"));
        btnInsert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleInsertService();
            }
        });

        btnUpdate.setIcon(AssetsManager.getImageIcon("UpdateIcon"));
        btnUpdate.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleUpdateService();
            }
        });

        btnDelete.setIcon(AssetsManager.getImageIcon("DeleteIcon"));
        btnDelete.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                handleDeleteService();
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

        tblServices.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                int selectedRowIndex = getSelectedRowIndex();

                behaveUpdateButton(selectedRowIndex);
                behaveDeleteButton(selectedRowIndex);
            }
        });
    }

    private void handleInsertService() {
        Service service = new Service(getMaxServiceId() + 1);
        InsertServiceDialog insertServiceDialog = new InsertServiceDialog(
                service,
                (dialog, _service) -> {
                    insertService(_service);
                    dialog.dispose();
                },
                Window::dispose
        );
        insertServiceDialog.showDialog();
    }

    private void insertService(Service service){
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Insertion in progress...");

        serviceController.insertService(
                service,
                new InsertServiceCallback() {
                    @Override
                    public void onSuccess() {
                        loadingSpinner.stop();
                        JOptionPane.showMessageDialog(null, "Service insertion successful.", "Successful", JOptionPane.INFORMATION_MESSAGE);

                        loadServices();
                    }

                    @Override
                    public void onFailed(Exception ex) {
                        ex.printStackTrace();
                        loadingSpinner.stop();

                        JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    }
                }
        );
    }

    private void handleUpdateService() {
        Service service = servicesTableModel.getServiceAt(getSelectedRowIndex());
        InsertServiceDialog updateServiceDialog = new InsertServiceDialog(
                service,
                (dialog, _service) -> {
                    updateService(_service);
                    dialog.dispose();
                },
                Window::dispose
        );
        updateServiceDialog.showDialog();
    }

    private void updateService(Service service){
        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Update in progress");

        serviceController.updateService(service, new UpdateServiceCallback() {
            @Override
            public void onSuccess() {
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, service.getServiceName() + " updated successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);

                loadServices();
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, "Error occurred!", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    private void handleDeleteService() {
        int selectedRowIndex = getSelectedRowIndex();
        Service service = servicesTableModel.getServiceAt(selectedRowIndex);

        final int confirm = JOptionPane.showConfirmDialog(null, "Are sure you want to delete " + service.getServiceName() + " from the system?", "Are you sure?", JOptionPane.YES_NO_OPTION);
        if(confirm != 0) return;

        LoadingSpinner loadingSpinner = new LoadingSpinner();
        loadingSpinner.start("Deletion in progress");

        serviceController.deleteService(servicesTableModel.getServiceAt(selectedRowIndex), new DeleteServiceCallback() {
            @Override
            public void onSuccess() {
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, "Service deleted successfully.", "Successful", JOptionPane.INFORMATION_MESSAGE);
                loadServices();
            }

            @Override
            public void onFailed(Exception ex) {
                loadingSpinner.stop();
                JOptionPane.showMessageDialog(null, "Service deletion failed due to : " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                loadServices();
            }
        });
    }

    private int getSelectedRowIndex() {
        int viewSelectedIndex = tblServices.getSelectedRow();
        if(viewSelectedIndex == -1) return -1;

        int modelSelectedIndex = tblServices.convertRowIndexToModel(viewSelectedIndex);
        return modelSelectedIndex;
    }

    private void behaveUpdateButton(int selectedRow){
        btnUpdate.setEnabled(selectedRow > -1);
    }

    private void behaveDeleteButton(int selectedRow){
        btnDelete.setEnabled(selectedRow > -1);
    }

    private void filterTableData(String key){
        TableRowSorter<?> sorter = (TableRowSorter<?>) this.tblServices.getRowSorter();

        RowFilter<Object, Object> idFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 0);
        RowFilter<Object, Object> nameFilter = RowFilter.regexFilter("(?i)" + key.toLowerCase(), 1);

        if(!key.isEmpty()){
            sorter.setRowFilter(RowFilter.orFilter(Arrays.asList(idFilter, nameFilter)));
        }else{
            sorter.setRowFilter(null);
        }
    }

    private int getMaxServiceId() {
        if(serviceList.isEmpty()) return 0;

        Service service = ArrayUtils.find(serviceList, (lastElement, element) -> {
            if(element.getServiceId() > lastElement.getServiceId())
                return element;
            return null;
        });

        return service.getServiceId();
    }
 }
