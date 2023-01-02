package com.example.profind;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class d_0_0_4_4_asaclient extends AppCompatActivity implements View.OnClickListener {

    String phone,st,newst="";
    Button back_btn;
    d_0_0_4_7_Appointments_Adapter adapter;
    ArrayList<d_0_0_4_6_Appointments_model> apnmnt_list;

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_Profind);
        setContentView(R.layout.activity_d_0_0_4_4_asaclient);
        getSupportActionBar().hide();

        b_0_1_Session session = new b_0_1_Session(this);
        HashMap<String, String> userDetails = session.getuserdetailFromSession();
        phone = userDetails.get(b_0_1_Session.KEY_PHONENUMBER);

        back_btn = findViewById(R.id.asaclient_page__back_btn_id);
        RecyclerView recyclerView = findViewById(R.id.asaclient_page_recyclerview_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManagerWrapper(getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        FirebaseFirestore db = FirebaseFirestore.getInstance();

        st = LocalDate.now().toString();
        String[] arrOfStr1 = st.split("-");
        for(int i=0;i<arrOfStr1.length;i++){
            newst=newst+arrOfStr1[i];
        }
        int t1=Integer.parseInt(newst);
        apnmnt_list=new ArrayList<>();
        adapter = new d_0_0_4_7_Appointments_Adapter(apnmnt_list, "true");
        recyclerView.setAdapter(adapter);
        db.collection("appointments")
                .document(phone)
                .collection("appointments")
                .orderBy("booking_time", Query.Direction.ASCENDING)
                .get().addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        List<DocumentSnapshot> list = queryDocumentSnapshots.getDocuments();
                        for (DocumentSnapshot documentSnapshot : list) {
                            String tm=documentSnapshot.getString("appointment_date");
                            String[] arrOfStr2 = tm.split("-");
                            String newst2="";
                            for (int i = 2; i >= 0; i--) {
                                newst2 = newst2 + arrOfStr2[i];
                            }
                            int t2=Integer.parseInt(newst2);

                            if(documentSnapshot.getString("clients_phone").equals(phone) && t2>=t1) {
                                d_0_0_4_6_Appointments_model obj = documentSnapshot.toObject(d_0_0_4_6_Appointments_model.class);
                                obj.setAppointment_id(documentSnapshot.getId());
                                apnmnt_list.add(obj);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        back_btn.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        if (view.getId() == R.id.asaclient_page__back_btn_id) {
            finish();
        }
    }

}