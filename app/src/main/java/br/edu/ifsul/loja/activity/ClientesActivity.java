package br.edu.ifsul.loja.activity;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.edu.ifsul.loja.R;
import br.edu.ifsul.loja.adapter.ClientesAdapter;
import br.edu.ifsul.loja.model.Cliente;

public class ClientesActivity extends AppCompatActivity {

    private static final String TAG = "ClientesActivity";
    private ListView lvClientes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_produtos);

        //mapeia o componente da view
        lvClientes = findViewById(R.id.lv_clientes);

        //buscar os dados no RealTimeDataBase
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference myRef = database.getReference("vendas/clientes");
        myRef.orderByChild("nome").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Log.d(TAG, "dataSnapshot=" + dataSnapshot);
                List<Cliente> listCliente = new ArrayList<>();
                for(DataSnapshot ds : dataSnapshot.getChildren()){
                    Log.d(TAG, "dataSnapshot=" + ds);
                    Cliente cliente = ds.getValue(Cliente.class);
                    cliente.setKey(ds.getKey());
                    cliente.setIndex(listCliente.size());
                    listCliente.add(cliente);
                }

                lvClientes.setAdapter(new ClientesAdapter(ClientesActivity.this, listCliente));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}
