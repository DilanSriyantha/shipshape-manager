package org.devdynamos.contollers;

import org.devdynamos.interfaces.DeleteServiceCallback;
import org.devdynamos.interfaces.GetServicesListCallback;
import org.devdynamos.interfaces.InsertServiceCallback;
import org.devdynamos.interfaces.UpdateServiceCallback;
import org.devdynamos.models.RequiredSkill;
import org.devdynamos.models.Service;
import org.devdynamos.utils.ArrayUtils;
import org.devdynamos.utils.Console;
import org.devdynamos.utils.DBManager;

import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ServiceController {
    public ServiceController() {
        if(DBManager.getConnection() != null) return;

        DBManager.establishConnection("localhost", 4000, "shipshape", "root", "");
        if(DBManager.getConnection() == null){
            JOptionPane.showMessageDialog(null, "Database connection failure. Falling back.", "Error", JOptionPane.ERROR_MESSAGE);
            System.exit(-1);
        }
    }

    public void insertService(Service service, InsertServiceCallback callback){
        Thread serviceInsertionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                String[] columns = new String[] { "serviceId", "serviceName", "unitPrice" };
                Object[] values = service.toObjectArray();

                DBManager.insert("services", columns, values);
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread skillsInsertionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String[] columns = new String[] { "serviceId", "requiredSkillDescription" };
                    Object[][] values = service.getSkillsAs2dObjectsArray();

                    if(values.length == 0) return;

                    DBManager.insertBatch("requiredSkills", columns, values);

                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    serviceInsertionThread.join();
                    skillsInsertionThread.join();
                    callback.onSuccess();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        serviceInsertionThread.start();
        skillsInsertionThread.start();
        waitingThread.start();
    }

    public void getServicesList(GetServicesListCallback callback) {
        final ServicesRetrievalResultSet resultSet = new ServicesRetrievalResultSet();
        Thread loadServicesThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    resultSet.serviceList = DBManager.getAll(
                            Service.class,
                            "services"
                    );
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

        Thread loadRequiredSkillsThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadServicesThread.join();

                    List<RequiredSkill> requiredSkills = DBManager.getAll(RequiredSkill.class, "requiredskills");
                    for(Service service : resultSet.serviceList){
                        RequiredSkill[] relevantSkills = ArrayUtils.filter(requiredSkills, (element) -> {
                            if(element.getServiceId() == service.getServiceId())
                                return element;
                            return null;
                        });

                        service.setRequiredSkills(new ArrayList<>(List.of(relevantSkills)));

                        Thread.sleep(1000);
                    }
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    loadRequiredSkillsThread.join();
                    callback.onSuccess(resultSet.serviceList);
                }catch (Exception ex){
                    ex.printStackTrace();
                    callback.onFailed(ex);
                }
            }
        });

        loadServicesThread.start();
        loadRequiredSkillsThread.start();
        waitingThread.start();
    }

    public void updateService(Service service, UpdateServiceCallback callback){
        Thread serviceUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                HashMap<String, Object> newValues = service.toHashMap();
                DBManager.update("services", newValues, "serviceId=" + service.getServiceId());
            }
        });

        Thread skillsUpdateThread = new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager.delete("requiredskills", "serviceId=" + service.getServiceId());

                String[] columns = { "serviceId", "requiredSkillDescription" };
                Object[][] values = service.getSkillsAs2dObjectsArray();

                if(values.length == 0) return;

                DBManager.insertBatch("requiredskills", columns, values);
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    serviceUpdateThread.join();
                    skillsUpdateThread.join();
                    callback.onSuccess();
                }catch (Exception ex){
                    ex.printStackTrace();
                    callback.onFailed(ex);
                }
            }
        });

        serviceUpdateThread.start();
        skillsUpdateThread.start();
        waitingThread.start();
    }

    public void deleteService(Service service, DeleteServiceCallback callback){
        Thread serviceDeletionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                DBManager.delete("services", "serviceId=" + service.getServiceId());

                try {
                    Thread.sleep(1000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        Thread skillsDeletetionThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    DBManager.delete("requiredskills", "serviceId=" + service.getServiceId());

                    Thread.sleep(1000);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });

        Thread waitingThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try{
                    serviceDeletionThread.join();
                    skillsDeletetionThread.join();
                    callback.onSuccess();
                }catch (Exception ex){
                    callback.onFailed(ex);
                }
            }
        });

        serviceDeletionThread.start();
        skillsDeletetionThread.start();
        waitingThread.start();
    }

    private class ServicesRetrievalResultSet {
        public List<Service> serviceList;

        public ServicesRetrievalResultSet() {}
    }
}
