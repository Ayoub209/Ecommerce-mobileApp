package ViewHolder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerce.R;

import Interface.ItemClick;

public class ProductViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView txtName,txtDes,txtPrice;
    public ImageView txtImage;
    public ItemClick listener;

    public ProductViewHolder(@NonNull View itemView) {
        super(itemView);
        txtImage=(ImageView) itemView.findViewById(R.id.pro_image);
        txtName=(TextView) itemView.findViewById(R.id.pro_name);
        txtDes=(TextView) itemView.findViewById(R.id.pro_des);
        txtPrice=(TextView) itemView.findViewById(R.id.pro_price);
    }

    public void setItemClick(ItemClick listener){
        this.listener=listener;
    }

    @Override
    public void onClick(View v) {
        listener.onClick(v,getAdapterPosition(),false);
    }
}
