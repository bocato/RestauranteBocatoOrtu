package comp.ufu.restaurante.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedList;

import comp.ufu.restaurante.R;
import comp.ufu.restaurante.model.Food;
import comp.ufu.restaurante.model.User;

/**
 * Created by bocato on 5/26/15.
 */
public class FoodDatabase{

    private LinkedList<Food> cardapio, entradas, carnes, porcoes, bebidas;
    private static FoodDatabase instance = null;

    private FoodDatabase(){
        populateFoodDataBase();
    }

    public static FoodDatabase getInstance() {
        if (instance == null ) {
           instance = new FoodDatabase();
        }
        return instance;
    }

    private void populateFoodDataBase(){
        cardapio = new LinkedList<>();
        entradas = new LinkedList<>();
        carnes = new LinkedList<>();
        porcoes = new LinkedList<>();
        bebidas = new LinkedList<>();

        // Entry
        Food entrada1 = new Food();
        entrada1.setId(1);
        entrada1.setType("entrada");
        entrada1.setName("Tábua de Frios");
        entrada1.setDescription("Tábua de Frios com: Mussarela, Presunto, Azeitonas Verdes e Palmito.");
        entrada1.setImageResource(R.drawable.tabua_de_frios);
        entrada1.setPrice((float) 11.99);

        Food entrada2 = new Food();
        entrada2.setId(2);
        entrada2.setType("entrada");
        entrada2.setName("Tábua de Frios 2");
        entrada2.setDescription("Tábua de Frios com: Mussarela, Queijo Prato, Presunto, Azeitonas Verdes, Azeitonas Pretas, Palmito, Salame, Lombinho Canadense, Champignom, ");
        entrada2.setImageResource(R.drawable.tabua_de_frios_2);
        entrada2.setPrice((float) 24.99);

        entradas.add(entrada1);
        entradas.add(entrada2);

        cardapio.add(entrada1);
        cardapio.add(entrada2);

        // Carnes
        Food carne1 = new Food();
        carne1.setId(3);
        carne1.setType("carne");
        carne1.setName("Picanha na Chapa");
        carne1.setDescription("Picanha na Chapa e acompanhamentos.");
        carne1.setImageResource(R.drawable.picanha_na_chapa);
        carne1.setPrice((float) 39.99);

        Food carne2 = new Food();
        carne2.setId(4);
        carne2.setType("carne");
        carne2.setName("Costela");
        carne2.setDescription("Costela assada e acompanhamentos.");
        carne2.setImageResource(R.drawable.costela);
        carne2.setPrice((float) 20.99);

        carnes.add(carne1);
        carnes.add(carne2);

        cardapio.add(carne1);
        cardapio.add(carne2);

        // Porcoes
        Food porcao1 = new Food();
        porcao1.setId(5);
        porcao1.setType("porcao");
        porcao1.setName("Batata Frita");
        porcao1.setDescription("Batatas Fritas com queijo.");
        porcao1.setImageResource(R.drawable.batata_frita);
        porcao1.setPrice((float) 9.99);

        Food porcao2 = new Food();
        porcao2.setId(6);
        porcao2.setType("porcao");
        porcao2.setName("Bacon");
        porcao2.setDescription("Bacon, com bacon e adicional de bacon.");
        porcao2.setImageResource(R.drawable.bacon);
        porcao2.setPrice((float) 19.99);

        porcoes.add(porcao1);
        porcoes.add(porcao2);

        cardapio.add(porcao1);
        cardapio.add(porcao2);

        // Bebidas
        Food bebida1 = new Food();
        bebida1.setId(7);
        bebida1.setType("bebida");
        bebida1.setName("Refrigerante");
        bebida1.setDescription("Refrigerante 2L");
        bebida1.setImageResource(R.drawable.refrigerante);
        bebida1.setPrice((float) 2.99);

        Food bebida2 = new Food();
        bebida2.setId(8);
        bebida2.setType("bebida");
        bebida2.setName("Agua");
        bebida2.setDescription("Água mineral gelada.");
        bebida2.setImageResource(R.drawable.agua);
        bebida2.setPrice((float) 1.99);

        bebidas.add(bebida1);
        bebidas.add(bebida2);

        cardapio.add(bebida1);
        cardapio.add(bebida2);
    }

    public LinkedList<Food> getCardapio() {
        return cardapio;
    }

    public LinkedList<Food> getCarnes() {
        return carnes;
    }

    public LinkedList<Food> getBebidas() {
        return bebidas;
    }

    public LinkedList<Food> getEntradas() {
        return entradas;
    }

    public LinkedList<Food> getPorcoes() { return porcoes; }

    public float getPriceById(int id){
        for(Food food: cardapio){
            if(food.getId() == id){
                return food.getPrice();
            }
        }
        return 0;
    }
}
