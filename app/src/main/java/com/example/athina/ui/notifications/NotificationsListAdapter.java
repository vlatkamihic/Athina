package com.example.athina.ui.notifications;

import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.jetbrains.annotations.NotNull;

public class NotificationsListAdapter extends RecyclerView.Adapter<NotificationsListAdapter.NotificationViewHolder> {




    @NonNull
    @NotNull
    @Override
    public NotificationViewHolder onCreateViewHolder(@NonNull @NotNull ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(@NonNull @NotNull NotificationViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class NotificationViewHolder extends RecyclerView.ViewHolder {


        public NotificationViewHolder(View itemView) {
            super(itemView);
        }
    }
}
