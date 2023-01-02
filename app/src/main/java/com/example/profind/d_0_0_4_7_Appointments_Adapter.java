package com.example.profind;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class d_0_0_4_7_Appointments_Adapter extends  RecyclerView.Adapter<d_0_0_4_7_Appointments_Adapter.appointments_holder> {

    String asapro;
    View view;
    Intent intent;
    ArrayList<d_0_0_4_6_Appointments_model> apnmnt_list;
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    public d_0_0_4_7_Appointments_Adapter(ArrayList<d_0_0_4_6_Appointments_model> apnmnt_list, String asapro) {
        this.apnmnt_list=apnmnt_list;
        this.asapro = asapro;
    }

    @Override
    public void onBindViewHolder(@NonNull appointments_holder holder, @SuppressLint("RecyclerView") int position) {
        try {
            int layout_width = LinearLayout.LayoutParams.MATCH_PARENT;
            int layout_height = LinearLayout.LayoutParams.WRAP_CONTENT;
            LinearLayout.LayoutParams lout_empty = new LinearLayout.LayoutParams(0, 0);
            LinearLayout.LayoutParams lout_not_empty = new LinearLayout.LayoutParams(layout_width, layout_height);

            if (asapro.equals("true")) {
                holder.profession_layout.setLayoutParams(lout_empty);
                db.collection("users")
                        .document(apnmnt_list.get(position).getClients_phone())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    if(documentSnapshot.getString("Profile_Photo").length()>0) {
                                        Picasso.get()
                                                .load(documentSnapshot.getString("Profile_Photo"))
                                                .placeholder(R.drawable.profile_img)
                                                .into(holder.profile_img);
                                    }
                                    holder.name.setText(documentSnapshot.getString("Name"));
                                }
                            }
                        });
            } else {
                holder.profession_layout.setLayoutParams(lout_not_empty);
                db.collection("users")
                        .document(apnmnt_list.get(position).getProfessionals_phone())
                        .get()
                        .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                            @Override
                            public void onSuccess(DocumentSnapshot documentSnapshot) {
                                if (documentSnapshot.exists()) {
                                    Picasso.get()
                                            .load(documentSnapshot.getString("Profile_Photo"))
                                            .placeholder(R.drawable.profile_img)
                                            .into(holder.profile_img);
                                    holder.name.setText(documentSnapshot.getString("Name"));
                                    holder.client.setText("");
                                    holder.profession_name.setText(documentSnapshot.getString("Profession"));
                                    if (documentSnapshot.getString("Profession").equals("Doctor")) {
                                        holder.profession_logo.setImageResource(R.drawable.doctor_icon);
                                    } else if (documentSnapshot.getString("Profession").equals("Builder")) {
                                        holder.profession_logo.setImageResource(R.drawable.builder_icon);
                                    } else if (documentSnapshot.getString("Profession").equals("Teacher")) {
                                        holder.profession_logo.setImageResource(R.drawable.teacher_icon);
                                    } else if (documentSnapshot.getString("Profession").equals("Lawyer")) {
                                        holder.profession_logo.setImageResource(R.drawable.lawyer_icon);
                                    }
                                }
                            }
                        });
            }
            holder.Time.setText("Date : "+apnmnt_list.get(position).getAppointment_date()+"  |  Time : "+apnmnt_list.get(position).getAppointment_time());

            holder.linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    intent = new Intent(view.getContext(), d_0_0_4_8_Appointments_detailes.class);
                    intent.putExtra("appointment_id", apnmnt_list.get(position).getAppointment_id().trim());
                    intent.putExtra("client_phone", apnmnt_list.get(position).getClients_phone().trim());
                    intent.putExtra("professionals_phone", apnmnt_list.get(position).getProfessionals_phone().trim());
                    intent.putExtra("asapro", asapro);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    view.getContext().startActivity(intent);
                }
            });

        } catch (Exception e) {
            Toast.makeText(view.getContext(), e + "", Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public int getItemCount() {
        return apnmnt_list.size();
    }

    @NonNull
    @Override
    public d_0_0_4_7_Appointments_Adapter.appointments_holder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.sample_g_9_appointments, parent, false);
        return new appointments_holder(view);
    }

    class appointments_holder extends RecyclerView.ViewHolder {

        LinearLayout linearLayout, profession_layout;
        ImageView profile_img, profession_logo;
        TextView name,client, profession_name, Time;

        public appointments_holder(View itemview) {
            super(itemview);
            view = itemview;

            linearLayout = itemview.findViewById(R.id.appointment_layout_id);
            profession_layout = itemview.findViewById(R.id.appointment_layout_profession_id);
            profile_img = itemview.findViewById(R.id.appointment_imgvw_id);
            profession_logo = itemview.findViewById(R.id.appointment_profession_imgvw_id);
            name = itemview.findViewById(R.id.appointment_name_txtvw_id);
            client= itemview.findViewById(R.id.appointment_client_txtvw_id);
            profession_name = itemview.findViewById(R.id.appointment_professiontxtvw_id);
            Time = itemview.findViewById(R.id.appointment_ratingtxtvw_id);

        }
    }
}
