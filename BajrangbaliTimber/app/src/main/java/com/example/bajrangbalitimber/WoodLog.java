package com.example.bajrangbalitimber;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.database.Cursor;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collections;

public class WoodLog extends AppCompatActivity {

    private ArrayList<String> SerialNo = new ArrayList<String>();
    private ArrayList<String> CustName = new ArrayList<String>();
    private ArrayList<String> CubicFoot = new ArrayList<String>();
    private ArrayList<String> Length = new ArrayList<String>();
    private ArrayList<String> Girth = new ArrayList<String>();
    private ArrayList<String> Rate = new ArrayList<String>();
    private ArrayList<String> Amount = new ArrayList<String>();

    private Button Add;
    private Button Clear;
    private Button Open;
    private Button PDF;


    private TableLayout Table;

    private EditText CustNameInp;
    private EditText LengthInp;
    private EditText GirthInp;
    private EditText RateInp;
    private TextView TotalAmount;
    private TextView TotalCubicFoot;
    DBHelper DB;

    int SerialNo1 = 0;

    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wood_log);

        Table = findViewById(R.id.tbLayoutBilling);

        CustNameInp = findViewById(R.id.eTcustName);
        LengthInp = findViewById(R.id.lenLogInput);
        GirthInp = findViewById(R.id.girthLogInput);
        RateInp = findViewById(R.id.rateLogInput);
        TotalAmount = findViewById(R.id.TotalBill);
        TotalCubicFoot = findViewById(R.id.TotalCft);


        Add = findViewById(R.id.calcLogBtn);
        Add.setEnabled(false);

        Clear = findViewById(R.id.clearLastLog);
        Clear.setEnabled(false);

        Open = findViewById(R.id.OpenBtn);
        Open.setEnabled(false);

        PDF = findViewById(R.id.pdfBtn);
        PDF.setEnabled(false);


        LengthInp.addTextChangedListener(ButtonEnabled);
        GirthInp.addTextChangedListener(ButtonEnabled);
        RateInp.addTextChangedListener(ButtonEnabled);
        CustNameInp.addTextChangedListener(ButtonEnabled);

        DB = new DBHelper(this);



        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Add();
                }
        });

        Open.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                OpenBill();
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ClearDataLog();
            }
        });
    }

    private TextWatcher ButtonEnabled = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

        }

        @Override
        public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
           String LenInput = LengthInp.getText().toString().trim();
           String GirthInput =  GirthInp.getText().toString().trim();
           String RateInput = RateInp.getText().toString().trim();
           String NameInput = CustNameInp.getText().toString().trim();

           Add.setEnabled(!LenInput.isEmpty() && !GirthInput.isEmpty() && !RateInput.isEmpty());
           Open.setEnabled(!NameInput.isEmpty());
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

    public  void  Add()
    {
        if(SerialNo.size() != 0 )
        {
            SerialNo1 = Integer.parseInt(SerialNo.get(SerialNo.size() - 1)) + 1;
        }
        else
        {
            SerialNo1 = 1;
        }

        double Amt;
        double cft;
        String Name1 = CustNameInp.getText().toString();
        double Len1 = Double.parseDouble(LengthInp.getText().toString());
        double Girth1 = Double.parseDouble(GirthInp.getText().toString());
        double Rate1 = Double.parseDouble(RateInp.getText().toString());



        cft = roundTwoDecimals(((Len1 * Girth1 * Girth1)/2304));
        Amt = roundTwoDecimals(((Len1 * Girth1 * Girth1)/2304) * Rate1);


        Boolean checkInsertData = DB.insertBillingData(String.valueOf(SerialNo1),Name1,String.valueOf(Len1), String.valueOf(Girth1), String.valueOf(Rate1), String.valueOf(cft), String.valueOf(Amt));
        if(checkInsertData==true)
            {
                Toast.makeText(this, "New Entry Inserted", Toast.LENGTH_SHORT).show();
            }

        SerialNo.add(String.valueOf(SerialNo1));
        CustName.add(Name1);
        Length.add(String.valueOf(Len1));
        Girth.add(String.valueOf(Girth1));
        Rate.add(String.valueOf(Rate1));
        CubicFoot.add(String.valueOf(cft));
        Amount.add(String.valueOf(Amt));

        TableLayout Table = (TableLayout) findViewById(R.id.tbLayoutBilling);

        TableRow TableRow1 = new TableRow(this);

        TextView TRSlNo = new TextView(this);
        TextView TRLength = new TextView(this);
        TextView TRGirth = new TextView(this);
        TextView TRRate = new TextView(this);
        TextView TRcft = new TextView(this);
        TextView TRAmount = new TextView(this);

        double TotalBill = 0.0;
        double TotalCft = 0.0;
        for(int i = 0; i < Length.size(); i++)
            {
                String SlNoLoop = "  " + SerialNo.get(i);
                String LenLoop = Length.get(i);
                String GirthLoop = Girth.get(i);
                String RateLoop = Rate.get(i);
                String cftLoop = CubicFoot.get(i);
                String AmountLoop = Amount.get(i);


                TRSlNo.setText(SlNoLoop);
                TRLength.setText(LenLoop);
                TRGirth.setText(GirthLoop);
                TRRate.setText(RateLoop);
                TRcft.setText(cftLoop);
                TRAmount.setText(AmountLoop);

                TotalBill += Double.parseDouble(AmountLoop);
                TotalCft += Double.parseDouble(cftLoop);
            }
        TableRow1.addView(TRSlNo);
        TableRow1.addView(TRLength);
        TableRow1.addView(TRGirth);
        TableRow1.addView(TRRate);
        TableRow1.addView(TRcft);
        TableRow1.addView(TRAmount);
        Table.addView(TableRow1);

        Clear.setEnabled(true);

        TotalAmount.setText("Rs. " + String.valueOf(roundTwoDecimals(TotalBill)));
        TotalCubicFoot.setText(String.valueOf(roundTwoDecimals(TotalCft)));

        LengthInp.setText("");
        GirthInp.setText("");
    }


    public void ClearDataLog()
    {
        int LastIndex = SerialNo.size() -1;
        SerialNo.remove(LastIndex);
        CustName.remove(LastIndex);
        Length.remove(LastIndex);
        Girth.remove(LastIndex);
        Rate.remove(LastIndex);
        CubicFoot.remove(LastIndex);
        Amount.remove(LastIndex);

        TableLayout Table1 = (TableLayout) findViewById(R.id.tbLayoutBilling);
        TextView TRcft = new TextView(this);
        TextView TRAmount = new TextView(this);


        double TotalBill = 0.0;
        double TotalCft = 0.0;
        for(int i = 0; i < Length.size(); i++)
        {
            String cftLoop = CubicFoot.get(i);
            String AmountLoop = Amount.get(i);

            TRcft.setText(cftLoop);
            TRAmount.setText(AmountLoop);

            TotalBill += Double.parseDouble(AmountLoop);
            TotalCft += Double.parseDouble(cftLoop);
        }

        int childCount = Table1.getChildCount();
        if (childCount >= 1) {
            Table1.removeViewAt(childCount-1);
        }

        if(SerialNo.size() < 1)
        {
            Clear.setEnabled(false);
        }


        TotalAmount.setText("Rs. " + String.valueOf(TotalBill));
        TotalCubicFoot.setText(String.valueOf(TotalCft));

        LengthInp.setText("");
        GirthInp.setText("");

    }


    public void OpenBill()
    {
        SerialNo.clear();
        CustName.clear();
        Length.clear();
        Girth.clear();
        Rate.clear();
        CubicFoot.clear();
        Amount.clear();

        TableLayout Table = (TableLayout) findViewById(R.id.tbLayoutBilling);
//        int childCount = Table.getChildCount();
//        if (childCount >= 1) {
//            Table.removeViews(1, childCount-1);
//        }

        Cursor res = DB.getBillingData();
        if(res.getCount()==0)
            {
                Toast.makeText(WoodLog.this, "No Entry Exist", Toast.LENGTH_SHORT).show();
            }

        while(res.moveToNext())
            {
                SerialNo1 = Integer.parseInt(res.getString(0));
                String Name1 = res.getString(1);
                double Len1 = Double.parseDouble(res.getString(2));
                double Girth1 = Double.parseDouble(res.getString(3));
                double Rate1 = Double.parseDouble(res.getString(4));
                double cft = Double.parseDouble(res.getString(5));
                double Amt = Double.parseDouble(res.getString(6));

                SerialNo.add(String.valueOf(SerialNo1));
                CustName.add(String.valueOf(Name1));
                Length.add(String.valueOf(Len1));
                Girth.add(String.valueOf(Girth1));
                Rate.add(String.valueOf(Rate1));
                CubicFoot.add(String.valueOf(cft));
                Amount.add(String.valueOf(Amt));
            }


        //TableLayout Table = (TableLayout) findViewById(R.id.tbLayoutBilling);

        TableRow TableRow1 = new TableRow(this);

        TextView TRSlNo = new TextView(this);
        TextView TRLength = new TextView(this);
        TextView TRGirth = new TextView(this);
        TextView TRRate = new TextView(this);
        TextView TRcft = new TextView(this);
        TextView TRAmount = new TextView(this);

        double TotalBill = 0.0;
        double TotalCft = 0.0;
        for(int i = 0; i < Length.size(); i++)
        {
            String SlNoLoop = "  " + SerialNo.get(i);
            String LenLoop = Length.get(i);
            String GirthLoop = Girth.get(i);
            String RateLoop = Rate.get(i);
            String cftLoop = CubicFoot.get(i);
            String AmountLoop = Amount.get(i);


            TRSlNo.setText(SlNoLoop);
            TRLength.setText(LenLoop);
            TRGirth.setText(GirthLoop);
            TRRate.setText(RateLoop);
            TRcft.setText(cftLoop);
            TRAmount.setText(AmountLoop);

            TotalBill += Double.parseDouble(AmountLoop);
            TotalCft += Double.parseDouble(cftLoop);
        }
        TableRow1.addView(TRSlNo);
        TableRow1.addView(TRLength);
        TableRow1.addView(TRGirth);
        TableRow1.addView(TRRate);
        TableRow1.addView(TRcft);
        TableRow1.addView(TRAmount);
        Table.addView(TableRow1);

        Clear.setEnabled(true);

        TotalAmount.setText("Rs. " + String.valueOf(roundTwoDecimals(TotalBill)));
        TotalCubicFoot.setText(String.valueOf(roundTwoDecimals(TotalCft)));

        LengthInp.setText("");
        GirthInp.setText("");



    }
}