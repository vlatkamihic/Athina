package com.example.athina.ui.planner;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.athina.ui.SimpleItemTouchHelperCallback;
import com.example.athina.ui.notifications.Notifications;
import com.example.athina.R;
import com.example.athina.database_plan.AppDatabasePlan;
import com.example.athina.database_plan.Plan;
import com.example.athina.databinding.FragmentPlannerBinding;

import java.util.List;

public class PlannerFragment extends Fragment implements PlanListAdapter.OnItemRemoved{
    private PlannerViewModel dashboardViewModel;
    private FragmentPlannerBinding binding;
    private PlanListAdapter planListAdapter;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        dashboardViewModel =
                new ViewModelProvider(this).get(PlannerViewModel.class);

        binding = FragmentPlannerBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        Button addPlanBtn = (Button) root.findViewById(R.id.add_plann_button);
        addPlanBtn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View arg0)
            {
                // TODO Auto-generated method stub
                Intent redirect = new Intent(getActivity(), AddPlan.class);
                startActivity(redirect);
            }
        });


        //action_bar_menu called
        setHasOptionsMenu(true);

        initPlanRecyclerView(root);
        loadPlanList();

        return root;
    }


    private void initPlanRecyclerView(View root) {
        RecyclerView planRecyclerView = root.findViewById(R.id.recyclerView_Plan);
        planRecyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        planListAdapter = new PlanListAdapter(this.requireActivity());
        planRecyclerView.setAdapter(planListAdapter);

        planListAdapter.setDeleteListener(this);
        ItemTouchHelper.Callback callback = new SimpleItemTouchHelperCallback(planListAdapter);
        ItemTouchHelper touchHelper = new ItemTouchHelper(callback);

        touchHelper.attachToRecyclerView(planRecyclerView);
        //new ItemTouchHelper(simpleCallback).attachToRecyclerView(planRecyclerView);

    }

    private void loadPlanList(){
        AppDatabasePlan databasePlan = AppDatabasePlan.getDBInstance(this.getActivity().getApplicationContext());
        List<Plan> planList = databasePlan.planDao().getAllPlans();
        planListAdapter.setPlanList(planList);
    }

    private void deletePlan(Plan plan){
        AppDatabasePlan databasePlan = AppDatabasePlan.getDBInstance(this.getActivity().getApplicationContext());

        databasePlan.planDao().delete(plan);
    }



    //when clicked on bell icon
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_notify:  {
                Intent intent = new Intent(getActivity(), Notifications.class);
                startActivity(intent);
                return true;
            }
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    //action_bar_menu
    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.action_bar_menu, menu);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemRemoved(Plan plan) {
        deletePlan(plan);
    }

    @Override
    public void onStart() {
        super.onStart();
        loadPlanList(); //request method
    }



}