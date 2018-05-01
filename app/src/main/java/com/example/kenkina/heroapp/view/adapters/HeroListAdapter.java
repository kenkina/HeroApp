package com.example.kenkina.heroapp.view.adapters;

import android.arch.paging.PagedListAdapter;
import android.support.annotation.NonNull;
import android.support.v7.util.DiffUtil;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.kenkina.heroapp.R;
import com.example.kenkina.heroapp.database.entities.Hero;


public class HeroListAdapter extends PagedListAdapter<Hero, HeroListAdapter.HeroViewHolder> {

    private RecyclerViewClickListener mClickListener;

    public HeroListAdapter() {
        //super(Hero.DIFF_CALLBACK);
        super(DIFF_CALLBACK);
    }

    /*public void setHeroes(List<Hero> heroes) {
        if (mHeroes == null) {
            mHeroes = new ArrayList<>();
        }

        //Hero.HeroDiffCallback diffCallback = new Hero.HeroDiffCallback(mHeroes, heroes);
        //DiffUtil.DiffResult diffResult = DiffUtil.calculateDiff(diffCallback);

        mHeroes = heroes;
        //diffResult.dispatchUpdatesTo(this);
    }*/

    public void setClickListener(RecyclerViewClickListener clickListener) {
        mClickListener = clickListener;
    }

    @NonNull
    @Override
    public HeroViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Log.d("GG", "onCreateViewHolder");
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View itemView = layoutInflater.inflate(R.layout.item_hero, parent, false);
        return new HeroViewHolder(itemView, mClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull HeroViewHolder holder, int position) {
        Log.d("GG", "onBindViewHolder: " + position);
        Hero hero = getItem(position);
        if (hero != null) {
            holder.bind(hero);
        }
    }

    class HeroViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final TextView idTextView;
        private final TextView serverIdTextView;
        private final TextView nameTextView;
        RecyclerViewClickListener mClickListener;

        private HeroViewHolder(View itemView, RecyclerViewClickListener clickListener) {
            super(itemView);
            idTextView = itemView.findViewById(R.id.idTextView);
            serverIdTextView = itemView.findViewById(R.id.serverIdTextView);
            nameTextView = itemView.findViewById(R.id.nameTextView);
            mClickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        private void bind(Hero hero) {
            //idTextView.setText(String.valueOf(hero.getId()));
            serverIdTextView.setText(hero.getServerId());
            nameTextView.setText(hero.getName());
        }

        @Override
        public void onClick(View view) {
            mClickListener.onClick(view, getAdapterPosition());
        }
    }


    private static final DiffUtil.ItemCallback<Hero> DIFF_CALLBACK =
            new DiffUtil.ItemCallback<Hero>() {
                @Override
                public boolean areItemsTheSame(@NonNull Hero oldHero, @NonNull Hero newHero) {
                    return oldHero.getServerId().equals(newHero.getServerId());
                }

                @Override
                public boolean areContentsTheSame(@NonNull Hero oldHero, @NonNull Hero newHero) {
                    return oldHero.equals(newHero);
                }
            };


    public interface RecyclerViewClickListener {

        void onClick(View view, int position);
    }
}
