package com.example.introfirestore;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FieldValue;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "MainActivity" ;
    private EditText enterText;
  private EditText enterThought;
  private Button saveButton;
  private  Button showButton;
  private Button updateButton;
  private Button deleteButton;
  TextView rectitle;

public static final String KEY_TITLE = "title";
public static final String KEY_THOUGHT = "thought";
  private FirebaseFirestore db = FirebaseFirestore.getInstance();
  private DocumentReference journalRef = db.collection("Journal")
          .document("First Thought");
  private CollectionReference collectionReference = db.collection("Journal");
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        enterText = findViewById(R.id.title);
        enterThought = findViewById(R.id.thoughts);
        saveButton = findViewById(R.id.savebutton);
        showButton  = findViewById(R.id.showbutton);
        rectitle = findViewById(R.id.rectitle);

        updateButton = findViewById(R.id.updatabutton);
        deleteButton = findViewById(R.id.deletebutton);
        updateButton.setOnClickListener(this);
        deleteButton.setOnClickListener(this);
        showButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getThought();
                   /*journalRef.get()
                           .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
                               @Override
                               public void onSuccess(DocumentSnapshot documentSnapshot) {
                                   if(documentSnapshot.exists())
                                   {
                                       Journal journal = documentSnapshot.toObject(Journal.class);
                                      *//* String title = documentSnapshot.getString(KEY_TITLE);
                                       String thought = documentSnapshot.getString(KEY_THOUGHT);*//*
                                       rectitle.setText(journal.getTitle());
                                       recthought.setText(journal.getThought());

                                   }else
                                   {
                                       Toast.makeText(MainActivity.this, "No data Exist", Toast.LENGTH_SHORT).show();
                                   }

                               }
                           })
                           .addOnFailureListener(new OnFailureListener() {
                               @Override
                               public void onFailure(@NonNull Exception e) {
                                   Log.d(TAG,"failure"+e.toString());
                               }
                           });*/
            }
        });
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTthought();
               /* String title  = enterText.getText().toString().trim();
                String thought = enterThought.getText().toString().trim();
                Journal journal = new Journal();
                journal.setTitle(title);
                journal.setThought(thought);
              *//*  Map<String, Object> data = new HashMap<>();
                data.put(KEY_TITLE,title);
                data.put(KEY_THOUGHT,thought);*//*


                        journalRef.set(journal)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void aVoid) {
                                Toast.makeText(MainActivity.this, "Success", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Log.d(TAG,"onfailure"+e.toString());
                            }
                        });*/
            }
        });
    }
    private void addTthought()
    {
        String title  = enterText.getText().toString().trim();
        String thought = enterThought.getText().toString().trim();
        Journal journal = new Journal(title,thought);
       /* journal.setTitle(title);
        journal.setThought(thought);*/


        collectionReference.add(journal);
    }
    private void getThought()
    {
        collectionReference.get()
                .addOnSuccessListener(new OnSuccessListener<QuerySnapshot>() {
                    @Override
                    public void onSuccess(QuerySnapshot queryDocumentSnapshots) {
                        String data = " ";
                        for(QueryDocumentSnapshot snapshots:queryDocumentSnapshots)
                        {
                        //    Log.d(TAG,"msg:"+snapshots.getId());

                   /*String title = value.getString(KEY_TITLE);
                   String thought = value.getString(KEY_THOUGHT);
                   rectitle.setText(title);
                   recthought.setText(thought);*/
                            Journal journal = snapshots.toObject(Journal.class);
                            data += "Title:"+journal.getTitle()+" \n"+"Thought:"+journal.getThought()+"\n\n";


                        }
                        rectitle.setText(data);

                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {

            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        collectionReference.addSnapshotListener(this, new EventListener<QuerySnapshot>() {
            @Override
            public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                if(error!=null)
                {
                    Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
                }
                String data = " ";
                for(QueryDocumentSnapshot snapshots:value)
                {
                    //    Log.d(TAG,"msg:"+snapshots.getId());

                   /*String title = value.getString(KEY_TITLE);
                   String thought = value.getString(KEY_THOUGHT);
                   rectitle.setText(title);
                   recthought.setText(thought);*/
                    Journal journal = snapshots.toObject(Journal.class);
                    data += "Title:"+journal.getTitle()+" \n"+"Thought:"+journal.getThought()+"\n\n";


                }
                rectitle.setText(data);

            }
        });
       /* journalRef.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot value, @Nullable FirebaseFirestoreException error) {
               if(error!=null)
               {
                   Toast.makeText(MainActivity.this, "something went wrong", Toast.LENGTH_SHORT).show();
               }
               if(value!=null && value.exists() )
               {
                   String data = " ";
                   *//*String title = value.getString(KEY_TITLE);
                   String thought = value.getString(KEY_THOUGHT);
                   rectitle.setText(title);
                   recthought.setText(thought);*//*
                   Journal journal = value.toObject(Journal.class);
                   data = "Title:"+journal.getTitle()+" \n"+"Thought:"+journal.getThought();
                   rectitle.setText(data);

               }else
               {
                   rectitle.setText("");

               }
            }
        });*/
    }

    @Override
    public void onClick(View v) {
           switch (v.getId())
           {
               case R.id.updatabutton:
               {
                   updateData();
                   break;
               }
               case R.id.deletebutton:
               {
                   deleteData();
                   break;
               }
           }
    }
// for deleting any one feild the code is journalRef.update(KEY_THOUGHT, FeildValue.delete());



    private void updateData() {
        String title  = enterText.getText().toString().trim();
        String thought = enterThought.getText().toString().trim();

        Map<String, Object> data = new HashMap<>();
        data.put(KEY_TITLE,title);
        data.put(KEY_THOUGHT,thought);
        journalRef.update(data).addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                Toast.makeText(MainActivity.this, "Update done", Toast.LENGTH_SHORT).show();
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG,"onfailure"+e.toString());
            }
        });

    }
    private void deleteData() {
        journalRef.delete();
       // journalRef.update(KEY_THOUGHT, FieldValue.delete());
    }
}