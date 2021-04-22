package com.example.notes;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class NameNotesFirebaseImpi implements NotesSource {

    private static final String CARDS_COLLECTION = "cards";
    private static final String TAG = "[NNFirebaseLMPI]";

    private FirebaseFirestore store = FirebaseFirestore.getInstance();

    private CollectionReference collection = store.collection(CARDS_COLLECTION);

    private List<NameNotes> namesNotes = new ArrayList<NameNotes>();

    @Override
    public NotesSource init(final NameNotesResponse nameNotesResponse) {
        collection.orderBy(NameNotesMapping.Fields.DATE,
                Query.Direction.DESCENDING).get()
                .addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
                    @Override
                    public void onComplete(@NonNull Task<QuerySnapshot> task) {
                        if (task.isSuccessful()){
                            namesNotes = new ArrayList<NameNotes>();
                            for (QueryDocumentSnapshot document : task.getResult()){
                                Map<String, Object> doc = document.getData();
                                String id = document.getId();
                                NameNotes nameNotes = NameNotesMapping.toNameNotes(id, doc);
                                namesNotes.add(nameNotes);
                            }
                            Log.d(TAG, "success "+ namesNotes.size() + " qnt");
                            nameNotesResponse.initialized(NameNotesFirebaseImpi.this);
                        }else {
                            Log.d(TAG, "get failed with ", task.getException());
                        }
                    }
                }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Log.d(TAG, "get failed with ", e);
            }
        });
        return this;
    }

    @Override
    public NameNotes getNameNotes(int position) {
        return namesNotes.get(position);
    }

    @Override
    public int size() {
        if (namesNotes == null) {
            return 0;
        }
        return namesNotes.size();
    }

    @Override
    public void deleteNotesData(int position) {
    collection.document(namesNotes.get(position).getId()).delete();
    namesNotes.remove(position);
    }

    @Override
    public void updateNotesData(int position, NameNotes nameNotes) {
    String id = nameNotes.getId();
    collection.document(id).set(NameNotesMapping.toDocument(nameNotes));
    }

    @Override
    public void addNotesData(final NameNotes nameNotes) {
        collection.add(NameNotesMapping.toDocument(nameNotes)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                nameNotes.setId(documentReference.getId());
            }
        });

    }

    @Override
    public void clearNotesData() {
        for (NameNotes nameNotes : namesNotes){
            collection.document(nameNotes.getId()).delete();
        }
        namesNotes = new ArrayList<NameNotes>();
    }
}
