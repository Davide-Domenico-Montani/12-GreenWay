package it.unimib.greenway.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.List;
import it.unimib.greenway.CarouselItem;
import it.unimib.greenway.R;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    private final List<CarouselItem> carouselItems;

    public CarouselAdapter(List<CarouselItem> carouselItems) {
        this.carouselItems = carouselItems;
    }

    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.carousel_item_layout, parent, false);
        return new CarouselViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
        CarouselItem item = carouselItems.get(position);
        holder.imageView.setImageResource(item.getImageResId()); // Set image resource from drawable
        holder.textView.setText(item.getText()); // Set text
        // Update other views in the layout based on your data
    }

    @Override
    public int getItemCount() {
        return carouselItems.size();
    }

    public static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        TextView textView;

        public CarouselViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.carousel_image_view);
            textView = itemView.findViewById(R.id.carousel_text_view);
        }
    }
}
