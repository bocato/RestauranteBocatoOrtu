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
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;

import comp.ufu.restaurante.R;
import comp.ufu.restaurante.database.FoodDatabase;
import comp.ufu.restaurante.model.Food;
import comp.ufu.restaurante.model.Order;
import comp.ufu.restaurante.tools.AlertDialogManager;

public class FoodListViewArrayAdapter extends ArrayAdapter<Food> {

    private Button btnAddFood, btnSubtractFood;
    private ImageView foodImg;
    private TextView foodNameTxt, foodDescriptionTxt, foodPriceTxt, foodQuantityTxt;
    private Order myCurrentOrder;
    private int[] foodOrdered;

    // Alert Dialog Manager
    private AlertDialogManager alert = new AlertDialogManager();

    public FoodListViewArrayAdapter(Context context, List<Food> foodArrayList, Order myCurrentOrder) {
        super(context, 0, foodArrayList);
        this.myCurrentOrder = myCurrentOrder;
        foodOrdered = myCurrentOrder.getFoodOrdered();
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
        foodImg = (ImageView) convertView.findViewById(R.id.btn_food_img);
        foodNameTxt = (TextView) convertView.findViewById(R.id.food_name_txt);
        foodDescriptionTxt = (TextView) convertView.findViewById(R.id.food_description_txt);
        foodPriceTxt = (TextView) convertView.findViewById(R.id.food_price);
        foodQuantityTxt = (TextView) convertView.findViewById(R.id.food_quantity_txt);
        btnAddFood = (Button) convertView.findViewById(R.id.btn_add_food);
        btnSubtractFood = (Button) convertView.findViewById(R.id.btn_subtract_food);

        // configuring the textViews
        foodNameTxt.setText(food.getName());
        foodDescriptionTxt.setText(food.getDescription());
        foodPriceTxt.setText("" + food.getPrice());
        foodQuantityTxt.setText(""+myCurrentOrder.getFoodOrdered()[food.getId()-1]);

        // configuring the Buttons
        foodImg.setBackgroundResource(food.getImageResource());

        final View finalConvertView = convertView;
        btnAddFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //System.out.println("INIT ** foodOrdered["+(food.getId()-1)+"] = "+ foodOrdered[food.getId()-1]);
                
                foodOrdered[food.getId()-1]++;
                //Toast.makeText(getContext(), "Adicionou "+food.getName(), Toast.LENGTH_SHORT).show();
                alert.showAlertDialog(getContext(), "Aviso", "Adicionou "+food.getName(), false);
                String newQuantity = ""+foodOrdered[food.getId()-1];
                myCurrentOrder.setFoodOrdered(foodOrdered);

                // update food quantity
                TextView foodQuantityTextView = (TextView) finalConvertView.findViewById(R.id.food_quantity_txt);
                foodQuantityTextView.setText(newQuantity);

                //System.out.println(food.getName() + " = " + newQuantity);

                //System.out.println("END ** foodOrdered["+(food.getId()-1)+"] = "+ foodOrdered[food.getId()-1]);
            }
        });

        btnSubtractFood.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //System.out.println("INIT ** foodOrdered["+(food.getId()-1)+"] = "+ foodOrdered[food.getId()-1]);

                if(foodOrdered[food.getId()-1] > 0){
                    foodOrdered[food.getId()-1]--;
                    String newQuantity = ""+foodOrdered[food.getId()-1];
                    myCurrentOrder.setFoodOrdered(foodOrdered);
                    alert.showAlertDialog(getContext(), "Aviso", "Removeu "+food.getName(), false);
                }
                String newQuantity = "" + foodOrdered[food.getId()-1];

                // update food quantity
                TextView foodQuantityTextView = (TextView) finalConvertView.findViewById(R.id.food_quantity_txt);
                foodQuantityTextView.setText(newQuantity);

                //System.out.println(food.getName() + " = " + newQuantity);

                //System.out.println("END ** foodOrdered["+(food.getId()-1)+"] = "+ foodOrdered[food.getId()-1]);
            }
        });

        // Return the completed view to render on screen
        return convertView;
    }

    public ImageView getFoodImg() {
        return foodImg;
    }

    public void setFoodImg(ImageView foodImg) {
        this.foodImg = foodImg;
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
