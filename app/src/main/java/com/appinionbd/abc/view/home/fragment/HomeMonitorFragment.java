package com.appinionbd.abc.view.home.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.IMonitorHome;
import com.appinionbd.abc.interfaces.recyclerAdapterMonitorHomeInterface.IPatientSelection;
import com.appinionbd.abc.model.dataModel.Monitor;
import com.appinionbd.abc.model.dataModel.MonitorsPatientList;
import com.appinionbd.abc.presenter.MonitorPresenter;
import com.appinionbd.abc.view.PatientInfo.PatientInfoActivity;
import com.appinionbd.abc.view.adapter.RecyclerAdapterMonitor;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class HomeMonitorFragment extends Fragment implements IMonitorHome.View {

    private RecyclerView recyclerViewMonitor;

    IMonitorHome.Presenter monitorHomePresenter;

    private List<MonitorsPatientList> monitorsPatientLists;

    public HomeMonitorFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        monitorHomePresenter = new MonitorPresenter(this);
        return inflater.inflate(R.layout.fragment_home_monitor, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        startMonitor();
    }

    private void startMonitor() {

        recyclerViewMonitor = getActivity().findViewById(R.id.recyclerView_monitor);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerViewMonitor.setLayoutManager(layoutManager);
        recyclerViewMonitor.setHasFixedSize(true);

        monitorHomePresenter.getMonitorList();

    }


//    private void showTrackList(Monitor monitor) {
//        if(monitor.getMonitorsPatientList().size() > 0) {
//
//            RecyclerAdapterMonitor recyclerAdapterMonitor = new RecyclerAdapterMonitor(monitor.getMonitorsPatientList(), new IPatientSelection() {
//                @Override
//                public void selectedPatient() {
//                    patientSelected();
//                }
//            });
//
//            recyclerViewMonitor.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
//            recyclerViewMonitor.setAdapter(recyclerAdapterMonitor);
//            recyclerAdapterMonitor.notifyDataSetChanged();
//        }
//        else
//            Toast.makeText(getActivity() , "No List !" ,Toast.LENGTH_LONG).show();
//    }


    @Override
    public void showTrackList(Monitor monitor) {
        if(monitor.getMonitorsPatientList().size() > 0) {

            RecyclerAdapterMonitor recyclerAdapterMonitor = new RecyclerAdapterMonitor(monitor.getMonitorsPatientList(), new IPatientSelection() {
                @Override
                public void selectedPatient(String userId) {
                    patientSelected(userId);
                }
            });

            recyclerViewMonitor.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
            recyclerViewMonitor.setAdapter(recyclerAdapterMonitor);
            recyclerAdapterMonitor.notifyDataSetChanged();
        }
        else
            Toast.makeText(getActivity() , "No List !" ,Toast.LENGTH_LONG).show();
    }

    private void patientSelected(String patientId) {
        Intent intent = new Intent(getActivity() , PatientInfoActivity.class);
        intent.putExtra("patient_id" , patientId);
        startActivity(intent);
    }
}
