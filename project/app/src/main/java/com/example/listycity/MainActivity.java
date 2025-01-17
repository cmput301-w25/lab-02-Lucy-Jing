package com.example.listycity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    // UI Components
    private ListView cityListView;
    private Button addCityButton;
    private Button deleteCityButton;
    private EditText cityInputField;

    // Data and Adapter
    private ArrayList<String> cityDataList;
    private ArrayAdapter<String> cityAdapter;

    // Track selected city for deletion
    private int selectedCityIndex = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        // Handle display insets for modern Android (optional)
        ViewCompat.setOnApplyWindowInsetsListener(
                findViewById(R.id.main),
                (v, insets) -> {
                    Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                    v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                    return insets;
                }
        );


        cityListView = findViewById(R.id.city_list);
        addCityButton = findViewById(R.id.add_city_button);
        deleteCityButton = findViewById(R.id.delete_city_button);
        cityInputField = findViewById(R.id.city_input);


        String[] initialCities = {"Edmonton", "Montreal", "Vancouver", "Toronto"};
        cityDataList = new ArrayList<>(Arrays.asList(initialCities));


        cityAdapter = new ArrayAdapter<>(
                this,
                R.layout.list_item,
                cityDataList
        );
        cityListView.setAdapter(cityAdapter);


        cityListView.setOnItemClickListener((parent, view, position, id) -> {
            selectedCityIndex = position;
            Toast.makeText(this, "Selected: " + cityDataList.get(position), Toast.LENGTH_SHORT).show();
        });


        addCityButton.setOnClickListener(v -> addCity());
        deleteCityButton.setOnClickListener(v -> deleteSelectedCity());
    }
    private void addCity() {
        String newCity = cityInputField.getText().toString().trim();
        if (!newCity.isEmpty()) {
            cityDataList.add(newCity);
            cityAdapter.notifyDataSetChanged();
            cityInputField.setText("");
            Toast.makeText(this, "City added: " + newCity, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "Please enter a city name!", Toast.LENGTH_SHORT).show();
        }
    }

    private void deleteSelectedCity() {
        if (selectedCityIndex >= 0 && selectedCityIndex < cityDataList.size()) {
            String removedCity = cityDataList.remove(selectedCityIndex);
            cityAdapter.notifyDataSetChanged();
            selectedCityIndex = -1;  // Reset selected index
            Toast.makeText(this, "City removed: " + removedCity, Toast.LENGTH_SHORT).show();
        } else {
            Toast.makeText(this, "No city selected to delete!", Toast.LENGTH_SHORT).show();
        }
    }
}
