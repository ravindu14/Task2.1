package com.example.unitconverter;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;

public class MainActivity extends AppCompatActivity {

    private EditText sourceAmount;
    private TextView sourceView, destinationView, sourceText, supportText;
    private Spinner sourceUnits, destinationUnits;
    private String selectedSource, selectedDestination;
    private ArrayAdapter<CharSequence> sourceAdapter, destinationAdapter;
    private Button convert_button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //instantiate the widgets
        sourceAmount = findViewById(R.id.sourceAmount);
        sourceView = findViewById(R.id.sourceView);
        destinationView = findViewById(R.id.destinationView);
        sourceUnits = findViewById(R.id.sourceUnits);
        sourceText = findViewById(R.id.textView2);
        supportText = findViewById(R.id.support);


        sourceAdapter = ArrayAdapter.createFromResource(this, R.array.source_units, android.R.layout.simple_spinner_item);
        sourceAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        sourceUnits.setAdapter(sourceAdapter);
        sourceUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int i, long l) {
                if (sourceText.getText().equals("Please select source unit")) {
                    sourceText.setText("Please select source unit");
                    sourceText.setTextColor(Color.BLACK);
                }


                destinationUnits = findViewById(R.id.destinationUnits);

                selectedSource = sourceUnits.getSelectedItem().toString();

                int parentId = parent.getId();
                if(parentId == R.id.sourceUnits) {
                    switch ( selectedSource) {
                        case "Select source unit":
                            destinationAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.defaultDestinationUnits, android.R.layout.simple_spinner_item);
                            break;
                        case "cm":
                        case "inch":
                        case "foot":
                        case "yard":
                            destinationAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.distanceUnits, android.R.layout.simple_spinner_item);
                            break;
                        case "g":
                        case "kg":
                        case "pound":
                        case "ounce":
                        case "ton":
                            destinationAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.weightUnits, android.R.layout.simple_spinner_item);
                            break;
                        case "celsius":
                        case "fahrenheit":
                        case "kelvin":
                            destinationAdapter = ArrayAdapter.createFromResource(parent.getContext(), R.array.temperatureUnits, android.R.layout.simple_spinner_item);
                            break;
                        default:
                            break;

                    }
                    destinationAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
                    destinationUnits.setAdapter(destinationAdapter);
                    destinationUnits.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                            selectedDestination = destinationUnits.getSelectedItem().toString();
                        }

                        @Override
                        public void onNothingSelected(AdapterView<?> adapterView) {

                        }
                    });
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        convert_button = findViewById(R.id.button);
        convert_button.setOnClickListener(v -> {
            if(selectedSource.equals("Select source unit") ) {
                sourceText.setText("Please select source unit");
                sourceText.setTextColor(Color.RED);
                return;

            }
            if (sourceAmount.getText().toString().equals("")) {
                sourceAmount.setError("Please enter amount to convert");
                return;
            }

            String convertedValue = this.getConvertedValue(selectedSource, selectedDestination, sourceAmount.getText().toString() );

            sourceView.setText(sourceAmount.getText().toString() + " " + selectedSource.toString());
            supportText.setText("is equal to");
            destinationView.setText(convertedValue + " " + selectedDestination.toString());
        });
    }

    private String getConvertedValue(String selectedSource, String selectedDestination, String amount) {
        double rawAmount = Double.parseDouble(amount);
        double convertedAmount = 0.0;
        DecimalFormat df = new DecimalFormat("#.##");

        double cmPerInch = 2.54;
        double cmPerFoot = 30.48;
        double cmPerYard = 91.44;
        double cmPerMile = 160934.4;
        double gPerKg = 1000;
        double gPerPound = 453.5925;
        double gPerOunce = 28.3495;
        double gPerTon = 907185;
        double celsiusToFahrenheit = 9.0 / 5.0;
        double celsiusToKelvin = 273.15;
        double fahrenheitToCelsius = 5.0 / 9.0;
        double fahrenheitToKelvin = 459.67 * 5.0 / 9.0;
        double kelvinToCelsius = -273.15;
        double kelvinToFahrenheit = 9.0 / 5.0 * (-459.67);

        double inchPerCm = 1 / cmPerInch;
        double footPerCm = 1 / cmPerFoot;
        double yardPerCm = 1 / cmPerYard;
        double milePerCm = 1 / cmPerMile;
        double kgPerG = 1/ gPerKg;
        double poundPerG = 1 / gPerPound;
        double ouncePerG = 1 / gPerOunce;
        double tonPerG = 1 / gPerTon;

        switch (selectedSource) {
            case "cm":
            case "g":
                convertedAmount = rawAmount;
                break;
            case "inch":
                convertedAmount = rawAmount * cmPerInch;
                break;
            case "foot":
                convertedAmount = rawAmount * cmPerFoot;
                break;
            case "YARD":
                convertedAmount = rawAmount * cmPerYard;
                break;
            case "MILE":
                convertedAmount = rawAmount * cmPerMile;
                break;
            case "kg":
                convertedAmount = rawAmount * gPerKg;
                break;
            case "pound":
                convertedAmount = rawAmount * gPerPound;
                break;
            case "ounce":
                convertedAmount = rawAmount * gPerOunce;
                break;
            case "ton":
                convertedAmount = rawAmount * gPerTon;
                break;
            case "celsius":
                switch (selectedDestination) {
                    case "celsius":
                        convertedAmount = rawAmount;
                        break;
                    case "fahrenheit":
                        convertedAmount = rawAmount * celsiusToFahrenheit;
                        break;
                    case "kelvin":
                        convertedAmount = rawAmount * celsiusToKelvin;
                        break;
                    default:
                        break;
                }
                break;
            case "fahrenheit":
                switch (selectedDestination) {
                    case "celsius":
                        convertedAmount = rawAmount * fahrenheitToCelsius;
                        break;
                    case "fahrenheit":
                        convertedAmount = rawAmount;
                        break;
                    case "kelvin":
                        convertedAmount = rawAmount * fahrenheitToKelvin;
                        break;
                    default:
                        break;
                }
                break;
            case "kelvin":
                switch (selectedDestination) {
                    case "celsius":
                        convertedAmount = rawAmount * kelvinToCelsius;
                        break;
                    case "fahrenheit":
                        convertedAmount = rawAmount * kelvinToFahrenheit;
                        break;
                    case "kelvin":
                        convertedAmount = rawAmount;
                        break;
                    default:
                        break;
                }
            default:
                System.out.println("Invalid source unit");
                break;
        }

        switch (selectedDestination) {
            case "cm":
            case "g":
            case "celsius":
            case "fahrenheit":
            case "kelvin":
                break;
            case "inch":
                convertedAmount = convertedAmount * inchPerCm;
                break;
            case "foot":
                convertedAmount = convertedAmount * footPerCm;
                break;
            case "yard":
                convertedAmount = convertedAmount * yardPerCm;
                break;
            case "mile":
                convertedAmount = convertedAmount * milePerCm;
                break;
            case "kg":
                convertedAmount = convertedAmount * kgPerG;
                break;
            case "pound":
                convertedAmount = convertedAmount * poundPerG;
                break;
            case "ounce":
                convertedAmount = convertedAmount * ouncePerG;
                break;
            case "ton":
                convertedAmount = convertedAmount * tonPerG;
                break;
            default:
                System.out.println("Invalid destination unit");
                break;
        }

        double formattedValue = Double.parseDouble(df.format(convertedAmount));

        return String.valueOf(formattedValue);
    }
}