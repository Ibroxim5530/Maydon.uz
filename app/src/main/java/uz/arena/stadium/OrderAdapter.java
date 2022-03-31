package uz.arena.stadium;

import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class OrderAdapter extends FirebaseRecyclerAdapter<OrderItem, OrderAdapter.myViewHolder> {

    public OrderAdapter(@NonNull FirebaseRecyclerOptions<OrderItem> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull myViewHolder holder, int position, @NonNull OrderItem model) {
        holder.arena_name.setText(model.getArena_name());
        holder.location.setText(model.getLocation());
        holder.summa.setText(model.getSumma());
        Picasso.get().load(model.getRasim1()).into(holder.img);

        holder.card.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(holder.card.getContext(), DetailsActivity.class);
                intent.putExtra("location", model.getLocation());
                intent.putExtra("arena_name", model.getArena_name());
                intent.putExtra("rasim1", model.getRasim1());
                intent.putExtra("rasim2", model.getRasim2());
                intent.putExtra("rasim3", model.getRasim3());
                intent.putExtra("rasim4", model.getRasim4());
                intent.putExtra("check_1", model.getCheck_1());
                intent.putExtra("check_2", model.getCheck_2());
                intent.putExtra("start_time", model.getStartTime());
                intent.putExtra("start_end", model.getEndTime());
                intent.putExtra("start_time2", model.getStartTime2());
                intent.putExtra("start_end2", model.getEndTime2());
                intent.putExtra("choosing", model.getChoosing());
                intent.putExtra("dimensions", model.getDimensions());
                intent.putExtra("summa", model.getSumma());
                intent.putExtra("radio", model.getRadio());

                holder.card.getContext().startActivity(intent);
            }
        });
    }

    @NonNull
    @Override
    public myViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card, parent, false);
        return new myViewHolder(view);
    }

    public class myViewHolder extends RecyclerView.ViewHolder {
        TextView arena_name, location, summa;
        ImageView img;
        CardView card;

        public myViewHolder(@NonNull View itemView) {
            super(itemView);

            arena_name = itemView.findViewById(R.id.home_card1_name);
            location = itemView.findViewById(R.id.home_card1_location);
            summa = itemView.findViewById(R.id.home_card1_summa);
            img = itemView.findViewById(R.id.img_home_1);
            card = itemView.findViewById(R.id.home_card_item);
        }
    }
}
