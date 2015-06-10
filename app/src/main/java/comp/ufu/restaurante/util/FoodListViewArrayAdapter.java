package comp.ufu.restaurante.util;

/**
 * Created by bocato on 5/28/15.
 */

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import comp.ufu.restaurante.R;
import comp.ufu.restaurante.activities.MainScreen;
import comp.ufu.restaurante.model.Food;
import comp.ufu.restaurante.model.Order;

public class FoodListViewArrayAdapter extends ArrayAdapter<Food> {

    private Button btnAddFood, btnSubtractFood;
    private ImageButton btnFoodImg;
    private TextView foodNameTxt, foodDescriptionTxt, foodPriceTxt, foodQuantityTxt;
    private Order myCurrentOrder;

    public FoodListViewArrayAdapter(Context context, List<Food> foodArrayList, Order myCurrentOrder) {
        super(context, 0, foodArrayList);
        this.myCurrentOrder = myCurrentOrder;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get the data item for this position
        final Food food = getItem(position);
        // Check if an existing view is being reused, otherwise inflate the view
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.food_listview, parent, false);
        }
        // Lookup view for data population
        btnFoodImg = (ImageButton) convertView.findViewById(R.id.btn_food_img);
        foodNameTxt = (TextView) convertView.findViewById(R.id.food_name_txt);
        foodDescriptionTxt = (TextView) convertView.findViewById(R.id.food_description_txt);
        foodPriceTxt = (TextView) convertView.findViewById(R.id.food_price);
        foodQuantityTxt = (TextView) convertView.findViewById(R.id.food_quantity);
        btnAddFood = (Button) convertView.findViewById(R.id.btn_add_food);
        btnSubtractFood = (Button) convertView.findViewById(R.id.btn_subtract_food);

        // configuring the textViews
        foodNameTxt.setText(food.getName());
        foodDescriptionTxt.setText(food.getDescription());
        foodPriceTxt.setText(""+food.getPrice());
        foodQuantityTxt.setText("0");

        // configuring the Buttons
        btnFoodImg.setBackgroundResource(food.getImageResource());

        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = myCurrentOrder.getFoodOrdered()[food.getId()] + 1;
                String quantityTxt = "" + quantity;
                foodQuantityTxt.setText(quantityTxt);
                myCurrentOrder.getFoodOrdered()[food.getId()] = quantity;
                System.out.println(quantityTxt);
            }
        });

        btnSubtractFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int quantity = myCurrentOrder.getFoodOrdered()[food.getId()] - 1;
                String quantityTxt = "" + quantity;
                foodQuantityTxt.setText(quantityTxt);
                myCurrentOrder.getFoodOrdered()[food.getId()] = quantity;
                v.invalidate();
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public ImageButton getBtnFoodImg() {
        return btnFoodImg;
    }

    public void setBtnFoodImg(ImageButton btnFoodImg) {
        this.btnFoodImg = btnFoodImg;
    }

    public Button getBtnAddFood() {
        return btnAddFood;
    }

    public void setBtnAddFood(Button btnAddFood) {
        this.btnAddFood = btnAddFood;
    }

    public Button getBtnSubtractFood() {
        return btnSubtractFood;
    }

    public void setBtnSubtractFood(Button btnSubtractFood) {
        this.btnSubtractFood = btnSubtractFood;
    }

    public TextView getFoodNameTxt() {
        return foodNameTxt;
    }

    public void setFoodNameTxt(TextView foodNameTxt) {
        this.foodNameTxt = foodNameTxt;
    }

    public TextView getFoodDescriptionTxt() {
        return foodDescriptionTxt;
    }

    public void setFoodDescriptionTxt(TextView foodDescriptionTxt) {
        this.foodDescriptionTxt = foodDescriptionTxt;
    }

    public TextView getFoodPriceTxt() {
        return foodPriceTxt;
    }

    public void setFoodPriceTxt(TextView foodPriceTxt) {
        this.foodPriceTxt = foodPriceTxt;
    }

    public TextView getFoodQuantityTxt() {
        return foodQuantityTxt;
    }

    public void setFoodQuantityTxt(TextView foodQuantityTxt) {
        this.foodQuantityTxt = foodQuantityTxt;
    }

    public Order getMyCurrentOrder() {
        return myCurrentOrder;
    }

    public void setMyCurrentOrder(Order myCurrentOrder) {
        this.myCurrentOrder = myCurrentOrder;
    }
}
