package com.example.profind;

import android.os.Build;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QuerySnapshot;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class d_0_0_4_5_previous_appointment_fragment_1 extends Fragment {


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    private String mParam1;
    private String mParam2;


    String phone,st,newst="";
    RecyclerView recyclerView;
    d_0_0_4_7_Appointments_Adapter adapter;
    ArrayList<d_0_0_4_6_Appointments_model> apnmnt_list;

    public d_0_0_4_5_previous_appointment_fragment_1() {
    }

    public static d_0_0_4_5_previous_appointment_fragment_1 newInstance(String param1, String param2) {
        d_0_0_4_5_previous_appointment_fragment_1 fragment = new d_0_0_4_5_previous_appointment_fragment_1();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_d_0_0_4_6_previous_appointment_page1, container, false);


        b_0_1_Session session = new b_0_1_Session(view.getContext());
        HashMap<String, String> userDetails = session.getuserdetailFromSession();
        phone = userDetails.get(b_0_1_Session.KEY_PHONENUMBER);

        recyclerView = view.findViewById(R.id.previous_appointment_1_fragment_recyclerview_id);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManagerWrapper(view.getContext(), LinearLayoutManager.VERTICAL, false);
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

                            if(documentSnapshot.getString("professionals_phone").equals(phone) && t2<t1) {
                                d_0_0_4_6_Appointments_model obj = documentSnapshot.toObject(d_0_0_4_6_Appointments_model.class);
                                obj.setAppointment_id(documentSnapshot.getId());
                                apnmnt_list.add(obj);
                            }
                        }
                        adapter.notifyDataSetChanged();
                    }
                });

        return view;
    }

}