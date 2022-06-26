package com.example.bajrangbalitimber;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class SizeWood extends AppCompatActivity {

    private ArrayList<String> SerialNoSize = new ArrayList<String>();
    private ArrayList<String> CubicFootSize = new ArrayList<String>();
    private ArrayList<String> LengthSize = new ArrayList<String>();
    private ArrayList<String> WidthSize = new ArrayList<String>();
    private ArrayList<String> HeightSize = new ArrayList<String>();
    private ArrayList<String> AmountSize = new ArrayList<String>();

    private Button Add1;
    private Button Clear1;

    private TableLayout Table;

    private EditText LengthSizeInp;
    private EditText WidthSizeInp;
    private EditText HeightSizeInp;
    private EditText RateSizeInp;
    private TextView TotalSizeAmount;
    private TextView TotalSizeCubicFoot;

    int SerialNoSize1 = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_size_wood);

        Table = findViewById(R.id.tbLayoutSizeBilling);

        LengthSizeInp = findViewById(R.id.lenSizeInput);
        WidthSizeInp = findViewById(R.id.widthSizeInput);
        RateSizeInp = findViewById(R.id.rateSizeInput);
        HeightSizeInp = findViewById(R.id.heightSizeInput);
        TotalSizeAmount = findViewById(R.id.TotalSizeBill);
        TotalSizeCubicFoot = findViewById(R.id.SizeTotalCft);

        Add1 = findViewById(R.id.calcSizeBtn);
        Add1.setEnabled(false);

        Clear1 = findViewById(R.id.clearLastSize);
        Clear1.setEnabled(false);

        LengthSizeInp.addTextChangedListener(ButtonEnabled);
        WidthSizeInp.addTextChangedListener(ButtonEnabled);
        HeightSizeInp.addTextChangedListener(ButtonEnabled);
        RateSizeInp.addTextChangedListener(ButtonEnabled);


        Add1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add1();
            }
        });

        Clear1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearData();
            }
        });

    }

    private TextWatcher ButtonEnabled = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }
        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            String LenInput = LengthSizeInp.getText().toString().trim();
            String WidthInput =  WidthSizeInp.getText().toString().trim();
            String HeightInput =  HeightSizeInp.getText().toString().trim();
            String RateInput = RateSizeInp.getText().toString().trim();

            Add1.setEnabled(!LenInput.isEmpty() && !WidthInput.isEmpty() && !RateInput.isEmpty() && !HeightInput.isEmpty());

        }
        @Override
        public void afterTextChanged(Editable editable) {

        }
    };



    double roundTwoDecimals(double d)
    {
        DecimalFormat twoDForm = new DecimalFormat("#.##");
        return Double.valueOf(twoDForm.format(d));
    }

    public  void  Add1()
    {
        if(SerialNoSize.size() != 0 )
            {
            SerialNoSize1 = Integer.parseInt(SerialNoSize.get(SerialNoSize.size() - 1)) + 1;
            }
        else
            {
            SerialNoSize1 = 1;
            }
        double Amt;
        double cft;
        double Len1 = Double.parseDouble(LengthSizeInp.getText().toString());
        double Width1 = Double.parseDouble(WidthSizeInp.getText().toString());
        double Height1 = Double.parseDouble(HeightSizeInp.getText().toString());
        double Rate1 = Double.parseDouble(RateSizeInp.getText().toString());

        cft = roundTwoDecimals(((Len1 * Width1 * Height1)/144));
        Amt = roundTwoDecimals(((Len1 * Width1 * Height1)/144) * Rate1);

        SerialNoSize.add(String.valueOf(SerialNoSize1));
        LengthSize.add(String.valueOf(Len1));
        WidthSize.add(String.valueOf(Width1));
        HeightSize.add(String.valueOf(Height1));
        CubicFootSize.add(String.valueOf(cft));
        AmountSize.add(String.valueOf(Amt));

        TableLayout Table = (TableLayout) findViewById(R.id.tbLayoutSizeBilling);

        TableRow TableRow1 = new TableRow(this);

        TextView TRSlNo = new TextView(this);
        TextView TRLength = new TextView(this);
        TextView TRWidth = new TextView(this);
        TextView TRHeight = new TextView(this);
        TextView TRRate = new TextView(this);
        TextView TRcft = new TextView(this);
        TextView TRAmount = new TextView(this);

        double TotalBill = 0.0;
        double TotalCft = 0.0;
        for(int i = 0; i < LengthSize.size(); i++)
        {
            String SlNoLoop = "  " + SerialNoSize.get(i);
            String LenLoop = LengthSize.get(i);
            String WidthLoop = WidthSize.get(i);
            String HeightLoop = HeightSize.get(i);
            String cftLoop = CubicFootSize.get(i);
            String AmountLoop = AmountSize.get(i);


            TRSlNo.setText(SlNoLoop);
            TRLength.setText(LenLoop);
            TRWidth.setText(WidthLoop);
            TRHeight.setText(HeightLoop);
            TRcft.setText(cftLoop);
            TRAmount.setText(AmountLoop);

            TotalBill += Double.parseDouble(AmountLoop);
            TotalCft += Double.parseDouble(cftLoop);
        }
        TableRow1.addView(TRSlNo);
        TableRow1.addView(TRLength);
        TableRow1.addView(TRWidth);
        TableRow1.addView(TRHeight);
        TableRow1.addView(TRcft);
        TableRow1.addView(TRAmount);

        Table.addView(TableRow1);
        Clear1.setEnabled(true);

        TotalSizeAmount.setText("Rs. " + String.valueOf(TotalBill));
        TotalSizeCubicFoot.setText("" + String.valueOf(TotalCft));

        LengthSizeInp.setText("");
        WidthSizeInp.setText("");
        HeightSizeInp.setText("");
    }

    public void ClearData()
        {
            int LastIndex = SerialNoSize.size() -1;
            SerialNoSize.remove(LastIndex);
            LengthSize.remove(LastIndex);
            WidthSize.remove(LastIndex);
            HeightSize.remove(LastIndex);
            CubicFootSize.remove(LastIndex);
            AmountSize.remove(LastIndex);

            TableLayout Table1 = (TableLayout) findViewById(R.id.tbLayoutSizeBilling);
            TextView TRcft = new TextView(this);
            TextView TRAmount = new TextView(this);


            double TotalBill = 0.0;
            double TotalCft = 0.0;
            for(int i = 0; i < LengthSize.size(); i++)
            {
                String cftLoop = CubicFootSize.get(i);
                String AmountLoop = AmountSize.get(i);

                TRcft.setText(cftLoop);
                TRAmount.setText(AmountLoop);

                TotalBill += Double.parseDouble(AmountLoop);
                TotalCft += Double.parseDouble(cftLoop);
            }

            int childCount = Table1.getChildCount();
            if (childCount >= 1) {
                Table1.removeViewAt(childCount-1);
            }

            if(SerialNoSize.size() < 1)
            {
                Clear1.setEnabled(false);
            }


            TotalSizeAmount.setText("Rs. " + String.valueOf(roundTwoDecimals(TotalBill)));
            TotalSizeCubicFoot.setText(String.valueOf(roundTwoDecimals(TotalCft)));

            LengthSizeInp.setText("");
            WidthSizeInp.setText("");
            HeightSizeInp.setText("");

        }
}