package com.example.mission13;

import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class CustomerAdapter extends RecyclerView.Adapter<CustomerAdapter.ViewHolder> implements OnCustomerItemClickListener {
    ArrayList<Customer> items = new ArrayList<Customer>();

    OnCustomerItemClickListener listener;
}
