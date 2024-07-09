package com.example.td;

import android.content.DialogInterface;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {
    private EditText title;
    private EditText subtitle;

    ArrayList<ItemModel> dataModels;
    ListView listView;
    private static CustomAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView=(ListView)findViewById(R.id.list);

        dataModels= new ArrayList<>();
        getItems();
        /*dataModels.add(new ItemModel("Apple Pie", "Android 1.0", ""));
        dataModels.add(new ItemModel("Banana Bread", "Android 1.1", ""));
        dataModels.add(new ItemModel("Cupcake", "Android 1.5", ""));
        dataModels.add(new ItemModel("Donut","Android 1.6", ""));
        dataModels.add(new ItemModel("Eclair", "Android 2.0", ""));*/

        adapter= new CustomAdapter(dataModels, this);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                ItemModel dataModel= dataModels.get(position);

                Snackbar.make(view, dataModel.getTitle()+"\n"+dataModel.getSubtitle(), Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
            }
        });

        Button addButton = findViewById(R.id.btn_add_item);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showAddItemDialog();
            }
        });
    }

    public void showAddItemDialog() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater inflater = getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.dialog_item, null);
        final EditText editTextTitle = dialogView.findViewById(R.id.edit_text_title);
        final EditText editTextSubtitle = dialogView.findViewById(R.id.edit_text_subtitle);

        builder.setView(dialogView)
                .setTitle("Ajouter un nouvel élément")
                .setPositiveButton("Ajouter", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String title = editTextTitle.getText().toString().trim();
                        String subtitle = editTextSubtitle.getText().toString().trim();
                        ItemModel newItem = new ItemModel(title, subtitle, "");
                        createItem(newItem, dialogView);
                        // Mettre à jour l'adaptateur

                        adapter.notifyDataSetChanged();
                    }
                })
                .setNegativeButton("Annuler", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        AlertDialog dialog = builder.create();
        dialog.show();
    }

    public void deleteItem(int position) {
        dataModels.remove(position);
        adapter.notifyDataSetChanged();
    }

    public void createItem(ItemModel item, View view) {
        Call<Void> createCall = ApiClient.getApiService().createItem(item);
        createCall.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    getItems();
                    // Succès de la création
                } else {
                    System.out.print("ERREUR");
                    Snackbar.make(view, "Erreur.", Snackbar.LENGTH_LONG)
                            .setAction("No action", null).show();
                    // Gérer l'erreur
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                System.out.print("ERREUR");
                Snackbar.make(view, "Erreur.", Snackbar.LENGTH_LONG)
                        .setAction("No action", null).show();
                // Gérer l'échec de la requête
            }
        });
    }

    public void getItems() {
        Call<ArrayList<ItemModel>> createCall = ApiClient.getApiService().getItems();
        createCall.enqueue(new Callback<ArrayList<ItemModel>>() {


            @Override
            public void onResponse(Call<ArrayList<ItemModel>> call, Response<ArrayList<ItemModel>> response) {
                if (response.isSuccessful()) {
                    System.out.print(response);
                    dataModels.clear();
                    dataModels.addAll(response.body());
                    adapter.notifyDataSetChanged();
                    // Succès de la création
                } else {
                    Log.e("Retrofit", "Erreur : " + response.body());
                    // Gérer l'erreur
                }
            }

            @Override
            public void onFailure(Call<ArrayList<ItemModel>> call, Throwable t) {
                Log.e("Retrofit", "Erreur : " + t.getMessage());
            }
        });
    }

    public void deleteItems(String id) {
        Call<Void> createCall = ApiClient.getApiService().deleteItem(id);
        createCall.enqueue(new Callback<Void>() {


            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    // Succès de la création
                } else {
                    Log.e("Retrofit", "Erreur : " + response.body());
                    // Gérer l'erreur
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e("Retrofit", "Erreur : " + t.getMessage());
            }
        });
    }
}
