package com.example.metro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ExpandableListView;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;

public class MainActivity extends AppCompatActivity {


    //MainActivity activity_for_outside;
    public static byte GetPrice(byte number, byte size) {
        byte price;
        byte extra;
        if (Math.abs(number) <= 9) {
            price = 5;
            extra = 2;
        } else if (Math.abs(number) > 9 && Math.abs(number) <= 16) {
            price = 7;
            extra = 3;
        } else {
            price = 10;
            extra = 3;
        }
        if (size > 5 || size == 1) {
            price = (byte) (price + extra);
        }
        return price;
    }

    public static List<String> GetSubList(String firstStation, String lastStation, List<MetroStations> stations) {
        List<MetroStations> subStations = null;
        List<String> StationsList = new ArrayList<>();
        int startIndex = stations.indexOf(new MetroStations(firstStation, 1f, 1f));
        int lastIndex = stations.indexOf(new MetroStations(lastStation, 1f, 1f));


        int number = (lastIndex - startIndex);


        if (number < 0) {
            subStations = stations.subList(lastIndex, startIndex);
            Collections.reverse(subStations);

        } else {
            subStations = stations.subList(startIndex + 1, lastIndex + 1);
        }
        for (int i = 0; i < subStations.size(); i++) {
            StationsList.add(subStations.get(i).getName());
        }
        return StationsList;
    }

    public static String GetSDirection(String firstStation, String lastStation, List stations) {
        String direction = null; //35 20 11


        int startIndex = stations.indexOf(new MetroStations(firstStation, 1f, 1f));
        int lastIndex = stations.indexOf(new MetroStations(lastStation, 1f, 1f));
        int number = (lastIndex - startIndex);
        if (stations.size() > 25) {
            if (number < 0) direction = "Helwan";
            else direction = "EL-Marg";
        }
        if (stations.size() > 15 && stations.size() < 25) {
            if (number < 0) direction = "Giza";
            else direction = "Shoubra elkhema";
        }
        if (stations.size() < 15) {
            if (number < 0) direction = "Absaya";
            else direction = "Attaba";
        }


        return direction;
    }


    List<MetroStations> HelwanLineStations = Arrays.asList(
            new MetroStations("Helwan", 29.84920531493854f, 31.334220169243945f),
            new MetroStations("Ain Helwan", 29.862643919223554f, 31.32487262650598f),
            new MetroStations("Helwan University", 29.870825025852238f, 31.320557549891983f),
            new MetroStations("Wadi Hof", 29.88057463307682f, 31.313519433501263f),
            new MetroStations("Hadayek Helwan", 29.89917810844048f, 31.30450721129364f),
            new MetroStations("El-Maasara", 29.90587087648795f, 31.299632238839738f),
            new MetroStations("Tora El-Asmant", 29.92580243529439f, 31.287480768090333f),
            new MetroStations("Kozzika", 29.936066631198265f, 31.281866480971935f),
            new MetroStations("Tora El-Balad", 29.946680914794953f, 31.273033027604487f),
            new MetroStations("Sakanat El-Maadi", 29.953915217267614f, 31.262713699484447f),
            new MetroStations("Maadi", 29.96045814976947f, 31.25760094150214f),
            new MetroStations("Hadayek El-Maadi", 29.970048254227567f, 31.25065884797916f),
            new MetroStations("Dar El-Salam", 29.982051629708796f, 31.242077546044847f),
            new MetroStations("El-Zahraa", 29.995295853941006f, 31.231148439434833f),
            new MetroStations("Mar Girgis", 30.006070738820792f, 31.229634649492223f),
            new MetroStations("El-Malek El-Saleh", 30.017758242275733f, 31.23114843940438f),
            new MetroStations("Al-Sayeda Zeinab", 30.029230359490995f, 31.23541376551634f),
            new MetroStations("Saad Zaghloul", 30.037057423847287f, 31.238327786348297f),
            new MetroStations("Sadat", 30.04410772113708f, 31.23439759417152f),
            new MetroStations("Nasser", 30.053459910033588f, 31.23880598464518f),
            new MetroStations("Orabi", 30.05675818920587f, 31.2420637104842f),
            new MetroStations("Al-Shohadaa", 30.061000520107076f, 31.24590424050748f),
            new MetroStations("Ghamra", 30.068928535330837f, 31.264434424270412f),
            new MetroStations("El-Demerdash", 30.07742490363055f, 31.278078019227408f),
            new MetroStations("Manshiet El-Sadr", 30.0818966551618f, 31.28732276804677f),
            new MetroStations("Kobri El-Qobba", 30.087211123629586f, 31.29361406432767f),
            new MetroStations("Hammamat El-Qobba", 30.091320036210785f, 31.29840596431311f),
            new MetroStations("Saray El-Qobba", 30.09770709943946f, 31.30381558246481f),
            new MetroStations("Hadayeq El-Zaitoun", 30.105950232537605f, 31.310022929065216f),
            new MetroStations("Helmeyet El-Zaitoun", 30.11344039813448f, 31.314651343586352f),
            new MetroStations("El-Matareyya", 30.121335301054813f, 31.31340956955709f),
            new MetroStations("Ain Shams", 30.131034567547925f, 31.318873369377073f),
            new MetroStations("Ezbet El-Nakhl", 30.13933427116574f, 31.32389280533653f),
            new MetroStations("El-Marg", 30.152100159048615f, 31.335353049491985f),
            new MetroStations("New El-Marg", 30.163643781337818f, 31.33864865237082f));

    List<MetroStations> GizaLineStations = Arrays.asList(
            new MetroStations("El-Mounib", 29.981266527586477f, 31.212593710865697f),
            new MetroStations("Sakiat Mekky", 29.99547434325122f, 31.20854544879743f),
            new MetroStations("Omm El-Masryeen", 30.005748862998527f, 31.207732996630792f),
            new MetroStations("Giza", 30.010738891030442f, 31.207282561210036f),
            new MetroStations("Faisal", 30.017429976673565f, 31.203888763375865f),
            new MetroStations("Cairo University", 30.02603132160158f, 31.20091876498759f),
            new MetroStations("El Bohoth", 30.0357550545074f, 31.19987449034718f),
            new MetroStations("Dokki", 30.038520886776116f, 31.211817062873944f),
            new MetroStations("Opera", 30.04192846381055f, 31.22485279757901f),
            new MetroStations("Sadat", 30.04418832427827f, 31.234265656573665f),
            new MetroStations("Mohamed Naguib", 30.04527502373242f, 31.244129440878226f),
            new MetroStations("Attaba", 30.052361622391434f, 31.24663719855049f),
            new MetroStations("Al-Shohadaa", 30.061023195829844f, 31.245861020473125f),
            new MetroStations("Masarra", 30.07094351077604f, 31.245091415098887f),
            new MetroStations("Rod El-Farag", 30.080587383185712f, 31.245249834798297f),
            new MetroStations("St.Teresa", 30.08797590743583f, 31.245233830741814f),
            new MetroStations("Khalafawy", 30.09786462240225f, 31.24523867670665f),
            new MetroStations("Mezallat", 30.10427855141114f, 31.246025606878735f),
            new MetroStations("Kolleyyet El-Zeraa", 30.113644967338217f, 31.248400319560048f),
            new MetroStations("Shubra El-Kheima", 30.12252470670171f, 31.245055619956315f)
    );
    List<MetroStations> AbasyaLineStations = Arrays.asList(
            new MetroStations("Heliopolis Square", 30.109185515769454f, 31.340108119655994f),
            new MetroStations("Haroun", 30.101414298099677f, 31.332403353691316f),
            new MetroStations("Al-Ahram", 30.09207574205727f, 31.3288882760869f),
            new MetroStations("Koleyet El-Banat", 30.084548961203527f, 31.330876602795282f),
            new MetroStations("Stadium", 30.073518888519555f, 31.320082829249863f),
            new MetroStations("Fair Zone", 30.073672519056373f, 31.301903842138934f),
            new MetroStations("Abbassiya", 30.07265855248292f, 31.28603273425731f),
            new MetroStations("Abdou Pasha", 30.065068836556236f, 31.273712209812608f),
            new MetroStations("El-Geish", 30.0620266412166f, 31.26685958382156f),
            new MetroStations("Bab El-Shaaria", 30.054313181686155f, 31.256136821948328f),
            new MetroStations("Attaba", 30.052745831566636f, 31.247757445078786f)
    );

    outSideMetroActivity outSideMetroActivity;
    private  String Start_fromOutSide, End_fromOutside, result_fromOutside;

    private  ExpandableListView listView;
    private  TableLayout table;
    private TextView StartStationName, EndStationName, RoadMap, RoadMap2, Choose;
    private Button From_BT, TO_BT, Result_BT;
    private ArrayList<String> lines = new ArrayList<>();
    private HashMap<String, List<MetroStations>> Lines_stations = new HashMap<>();
    private  List FirstStationList = null, SecendStaionList = null;
    private  List<MetroStations> intersectedStaions, intersectedStaions2;
    private  List<String> SubList1, SubList2, SubList3;
    private String firstStation, lastStation, SelectedStation = null, direction1, direction, direction2;
    private byte NumberOfStations = 0, index, index2;
    private  MainAdabtor adabtor;
    private  Boolean fromTO = false, newTrip = false, StationChange = true;
    private  StringBuilder RoadString = new StringBuilder();
    private SharedPreferences pre;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        listView = findViewById(R.id.expandedList);
        lines.add("Helwan");

        lines.add("Shobra El Khema");
        lines.add("Abasya");
        outSideMetroActivity = new outSideMetroActivity();

        outSideMetroActivity.activity_for_outside = this;


        Lines_stations.put(lines.get(0), HelwanLineStations);
        Lines_stations.put(lines.get(1), GizaLineStations);
        Lines_stations.put(lines.get(2), AbasyaLineStations);
        adabtor = new MainAdabtor(lines, Lines_stations, SelectedStation);
        adabtor.mainActivityDelegate = this;

        StartStationName = findViewById(R.id.txStartStation);
        EndStationName = findViewById(R.id.tx_endstation);
        RoadMap = findViewById(R.id.tx_road);
        RoadMap2 = findViewById(R.id.tx_road2);
        Choose = findViewById(R.id.tx_result);
        From_BT = findViewById(R.id.bt_from);
        TO_BT = findViewById(R.id.bt_to);
        Result_BT = findViewById(R.id.bt_result);
        table = findViewById(R.id.table);
        StartStationName.setText(SelectedStation);
        SelectedStation = MainAdabtor.SelectedStation;
        Start_fromOutSide = getIntent().getStringExtra("start");
        End_fromOutside = getIntent().getStringExtra("end");
        result_fromOutside = getIntent().getStringExtra("result");
        pre = getPreferences(MODE_PRIVATE);
        if (Start_fromOutSide != null && End_fromOutside != null) {
            StartStationName.setText(Start_fromOutSide);
            EndStationName.setText(End_fromOutside);
            result();
        } else {
            StartStationName.setText(pre.getString("start", ""));
            EndStationName.setText(pre.getString("end", ""));
            RoadMap.setText(pre.getString("road", ""));

        }


        StartStationName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StationChange = true;
            }

        });
        EndStationName.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                StationChange = true;

            }
        });

    }

    public void from(View view) {

    }

    public void CL_FORM(View view) {
        fromTO = false;
        listView.setAdapter(adabtor);
        RoadMap.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        Choose.setVisibility(View.VISIBLE);
        RoadMap.setText("");
        table.setVisibility(View.GONE);

    }


    public void CL_to(View view) {
        fromTO = true;
        listView.setAdapter(adabtor);

        RoadMap.setVisibility(View.GONE);
        listView.setVisibility(View.VISIBLE);
        Choose.setVisibility(View.VISIBLE);
        RoadMap.setText("");
        table.setVisibility(View.GONE);


    }

    public void setTExt(String station) {
        if (fromTO == false) {
            StartStationName.setText(station);
        } else EndStationName.setText(station);
        listView.setVisibility(View.GONE);
        RoadMap.setVisibility(View.VISIBLE);
        Choose.setVisibility(View.GONE);
        table.setVisibility(View.VISIBLE);

    }

    public StringBuilder result() {
        if (StationChange == true) {


            firstStation = StartStationName.getText().toString();
            lastStation = EndStationName.getText().toString();
            if (StartStationName.getText().toString().isEmpty() || EndStationName.getText().toString().isEmpty()) {
                Toast.makeText(this, "Please select your destination", Toast.LENGTH_SHORT).show();
                return null;
            }
            if (HelwanLineStations.contains(new MetroStations(firstStation, 1f, 1f)))
                FirstStationList = HelwanLineStations;
            else if (GizaLineStations.contains(new MetroStations(firstStation, 1f, 1f)))
                FirstStationList = GizaLineStations;
            else if (AbasyaLineStations.contains(new MetroStations(firstStation, 1f, 1f)))
                FirstStationList = AbasyaLineStations;


            if (HelwanLineStations.contains(new MetroStations(lastStation, 1f, 1f)))
                SecendStaionList = HelwanLineStations;
            else if (GizaLineStations.contains(new MetroStations(lastStation, 1f, 1f)))
                SecendStaionList = GizaLineStations;
            else if (AbasyaLineStations.contains(new MetroStations(lastStation, 1f, 1f)))
                SecendStaionList = AbasyaLineStations;

            intersectedStaions = new ArrayList<>(FirstStationList);
            intersectedStaions.retainAll(SecendStaionList);


            if (intersectedStaions.size() > 6) {
                RoadString.setLength(0);
                //start printing


                if (FirstStationList.size() < 15) {
                    RoadString.append("your Estimated price is  = " + GetPrice((byte) GetSubList(firstStation, lastStation, SecendStaionList).size(), (byte) 10) + " EGP" + "\n\n");

                } else {
                    RoadString.append("your Estimated price is  = " + GetPrice((byte) GetSubList(firstStation, lastStation, SecendStaionList).size(), (byte) 2) + " EGP" + "\n\n");
                }
                RoadString.append("Go To Direction " + GetSDirection(firstStation, lastStation, SecendStaionList) + "\n");
                RoadString.append("Stations\n" + GetSubList(firstStation, lastStation, SecendStaionList) + "\n" + "\n");
                RoadString.append("Summary\n\n" + "Number of Stations = " + ((byte) GetSubList(firstStation, lastStation, SecendStaionList).size()) + " Station" + "\n");
                RoadString.append("Estimated time for this trip  = " + ((byte) GetSubList(firstStation, lastStation, SecendStaionList).size()) * 2 + " min" + "\n");

                //end printing

            } else if (intersectedStaions.size() <= 4 && intersectedStaions.size() > 0) {
                for (int i = 0; i < intersectedStaions.size(); i++) {
                    if (i == 1) {
                        RoadString.append("\n" + "\n\n" + "----Anther Road you can take-----\n\n\n ");
                    }
                    direction1 = GetSDirection(firstStation, intersectedStaions.get(i).name, FirstStationList);
                    SubList1 = GetSubList(firstStation, intersectedStaions.get(i).name, FirstStationList);
                    direction = GetSDirection(intersectedStaions.get(i).name, lastStation, SecendStaionList);
                    SubList2 = GetSubList(intersectedStaions.get(i).name, lastStation, SecendStaionList);
                    NumberOfStations = (byte) (SubList1.size() + SubList2.size());

                    {
                        //start printing
                        RoadString.append("your Estimated price is  = " + GetPrice(NumberOfStations, (byte) intersectedStaions.size()) + " EGP" + "\n\n");
                        RoadString.append("Go To Direction " + direction1 + "\n");
                        RoadString.append("Stations\n" + SubList1 + "\nDop off and change your Direction to " + direction + "\n");
                        RoadString.append("Ride Stations\n" + SubList2 + "\n\n");
                        RoadString.append("\nSummary\n\nNumber of Stations = " + (NumberOfStations) + "\n");
                        RoadString.append("your Estimated time is  = " + (NumberOfStations) * 2 + " min" + "\n");
                        //end printing
                    }
                }


            } else {
                intersectedStaions = new ArrayList<>(FirstStationList);
                intersectedStaions2 = new ArrayList<>(SecendStaionList);
                intersectedStaions.retainAll(GizaLineStations);
                intersectedStaions2.retainAll(GizaLineStations);

                for (byte i = 0; i < 2; i++) {
                    if (i == 1) {
                        RoadString.append("\n" + "\n\n" + "----Anther Road you can take-----\n\n\n ");
                    }
                    if (intersectedStaions.size() == 2) index = i;
                    else index2 = i;
                    direction1 = GetSDirection(firstStation, intersectedStaions.get(index).name, FirstStationList);
                    SubList1 = GetSubList(firstStation, intersectedStaions.get(index).name, FirstStationList);
                    direction = GetSDirection(intersectedStaions.get(index).name, intersectedStaions2.get(index2).name, GizaLineStations);
                    SubList2 = GetSubList(intersectedStaions.get(index).name, intersectedStaions2.get(index2).name, GizaLineStations);
                    direction2 = GetSDirection(intersectedStaions2.get(index2).name, lastStation, SecendStaionList);
                    SubList3 = GetSubList(intersectedStaions2.get(index2).name, lastStation, SecendStaionList);
                    NumberOfStations = (byte) (SubList1.size() + SubList2.size() + SubList3.size());

                    //start printing
                    RoadString.append("your Estimated price is  = " + GetPrice(NumberOfStations, (byte) 10) + " EGP" + "\n\n");

                    RoadString.append("Go To Direction  " + direction1 + "\n");
                    RoadString.append("Stations\n" + SubList1 + "\nDop off and change your Direction to " + direction + "\n");
                    RoadString.append("Stations\n" + SubList2 + "\nDop off and change your Direction to " + direction2 + "\n");
                    RoadString.append("Stations\n" + SubList3 + "\n");
                    RoadString.append("\n" +
                            "Summary\n\nNumber of Stations = " + (NumberOfStations) + "\n");
                    RoadString.append("your Estimated time is  = " + (NumberOfStations) * 2 + " min" + "\n");
                    //end printing
                }

            }
            RoadMap.setText(RoadString);
            StationChange = false;

            return RoadString;
        } else {
            return null;
        }
    }

    public void CL_result(View view) {
        if (StationChange == true) {
            result();
            RoadString.setLength(0);
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("road", RoadMap.getText().toString());
        editor.putString("start", firstStation);
        editor.putString("end", lastStation);
        editor.apply();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        SharedPreferences.Editor editor = pre.edit();
        editor.putString("road", RoadMap.getText().toString());
        editor.putString("start", firstStation);
        editor.putString("end", lastStation);
        editor.apply();
    }

    public void done(View view) {
        RoadMap.setText("");
        StartStationName.setText("");
        EndStationName.setText("");
        onBackPressed();

    }
}