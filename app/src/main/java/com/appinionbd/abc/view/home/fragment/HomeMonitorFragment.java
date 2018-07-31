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

import com.appinionbd.abc.R;
import com.appinionbd.abc.interfaces.presenterInterface.IMonitorHome;
import com.appinionbd.abc.interfaces.recyclerAdapterMonitorHomeInterface.IPatientSelection;
import com.appinionbd.abc.model.dataModel.MonitorsPatientList;
import com.appinionbd.abc.presenter.MonitorPresenter;
import com.appinionbd.abc.view.PatientInfo.PatientInfoActivity;
import com.appinionbd.abc.view.adapter.RecyclerAdapterMonitor;

import java.util.ArrayList;
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

        monitorsPatientLists = new ArrayList<>();
        monitorsPatientLists.add(new MonitorsPatientList("0"
        , "tahmid"
        , "tahmid@abc.com"
        ,"1970-01-01"
        , "66"
        ,"80.000"
        , "0"
        , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("1"
                , "Ahanaf"
                , "ahanaf@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("2"
                , "Tanvir"
                , "tanvir@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));
        monitorsPatientLists.add(new MonitorsPatientList("3"
                , "Ahanaf"
                , "ahanaf@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("4"
                , "Tanvir"
                , "tanvir@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("5"
                , "Tanvir"
                , "tanvir@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("6"
                , "Tanvir"
                , "tanvir@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));
        monitorsPatientLists.add(new MonitorsPatientList("7"
                , "tahmid"
                , "tahmid@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("8"
                , "Ahanaf"
                , "ahanaf@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("9"
                , "Tanvir"
                , "tanvir@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));
        monitorsPatientLists.add(new MonitorsPatientList("10"
                , "Ahanaf"
                , "ahanaf@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("11"
                , "Tanvir"
                , "tanvir@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("12"
                , "Tanvir"
                , "tanvir@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        monitorsPatientLists.add(new MonitorsPatientList("13"
                , "Tanvir"
                , "tanvir@abc.com"
                ,"1970-01-01"
                , "66"
                ,"80.000"
                , "0"
                , "tah-1005"));

        recyclerViewMonitor = getActivity().findViewById(R.id.recyclerView_monitor);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(getActivity());
        recyclerViewMonitor.setLayoutManager(layoutManager);
        recyclerViewMonitor.setHasFixedSize(true);


        RecyclerAdapterMonitor recyclerAdapterMonitor = new RecyclerAdapterMonitor(monitorsPatientLists, new IPatientSelection() {
            @Override
            public void selectedPatient() {
                patientSelected();
            }
        });

        recyclerViewMonitor.addItemDecoration(new DividerItemDecoration(getActivity(), LinearLayout.VERTICAL));
        recyclerViewMonitor.setAdapter(recyclerAdapterMonitor);
        recyclerAdapterMonitor.notifyDataSetChanged();

    }

    private void patientSelected() {
        Intent intent = new Intent(getActivity() , PatientInfoActivity.class);
        startActivity(intent);
    }
}
