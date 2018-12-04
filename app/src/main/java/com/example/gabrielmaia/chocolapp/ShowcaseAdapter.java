package com.example.gabrielmaia.chocolapp;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import java.util.ArrayList;

import model.Candy;
import model.ShowcaseItem;
import persistence.CandyDAO;

public class ShowcaseAdapter extends RecyclerView.Adapter<ShowcaseAdapter.ShowcaseViewHolder> {

    private ArrayList<ShowcaseItem> showcase;
    private ShowcaseItem currentItem;

    public ShowcaseAdapter(ArrayList<ShowcaseItem> showcase) {
        this.showcase = showcase;
    }

    public static class ShowcaseViewHolder extends RecyclerView.ViewHolder {
        public TextView itemName;
        public Button itemSellBtn;

        public ShowcaseViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName =  itemView.findViewById(R.id.item_name);
            itemSellBtn =  itemView.findViewById(R.id.item_sell_btn);
        }
    }
    @NonNull
    @Override
    public ShowcaseViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.showcase_item, viewGroup, false);
        return new ShowcaseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ShowcaseViewHolder viewHolder, int i) {
        currentItem = this.showcase.get(i);

        viewHolder.itemSellBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = viewHolder.getAdapterPosition();

                Intent intent = new Intent(v.getContext(), PaymentMethodActivity.class);
                intent.putExtra("SHOWCASE_ID", getShowcaseItemId(position));
                v.getContext().startActivity(intent);
            }
        });

        CandyDAO candyDAO = new CandyDAO(viewHolder.itemName.getContext());
        Candy candy = candyDAO.read(currentItem.getCandyId());
        viewHolder.itemName.setText(candy.getType() +": "+ candy.getName());
        viewHolder.itemSellBtn.setText(String.valueOf(currentItem.getQuantity()));

    }

    private int getShowcaseItemId(int position) {
        ShowcaseItem s = this.showcase.get(position);
        return s.getShowcaseItemId();
    }

    @Override
    public int getItemCount() {
        return this.showcase.size();
    }
}