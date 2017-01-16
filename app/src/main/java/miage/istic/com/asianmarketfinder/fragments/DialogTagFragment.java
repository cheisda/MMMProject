package miage.istic.com.asianmarketfinder.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import miage.istic.com.asianmarketfinder.R;
import miage.istic.com.asianmarketfinder.adapters.TagAdapter;
import miage.istic.com.asianmarketfinder.database.sto_tag.Sto_tag;
import miage.istic.com.asianmarketfinder.database.tag.Tag;

/**
 * Created by Rom on 1/15/2017.
 */
public class DialogTagFragment extends android.support.v4.app.DialogFragment {

    private final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();
    private List<String> tagSelected = new ArrayList<>();
    private List<String> tagIdSelected = new ArrayList<>();
    private String storeId;
    private Context ctx;

    static DialogTagFragment newInstance(String storeId) {
        DialogTagFragment f = new DialogTagFragment();
        System.out.println("ss: " + storeId);
        Bundle args = new Bundle();
        args.putString("storeId", storeId);
        f.setArguments(args);

        return f;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.storeId = getArguments().getString("storeId");
        System.out.println("ss: " + storeId);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        ctx = this.getActivity();
        View v = inflater.inflate(R.layout.dialog_add_tag, container, false);

        Button button = (Button) v.findViewById(R.id.add_tag_button);
        button.setVisibility(View.GONE);

        final EditText newTag = (EditText) v.findViewById(R.id.new_tag);
        final ListView listView = (ListView) v.findViewById(R.id.tag_listview);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                if (!tagSelected.contains(((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).getText().toString())) {
                    tagSelected.add(((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).getText().toString());
                    tagIdSelected.add(((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).getTag().toString());
                    ((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).setTextColor(getResources().getColor(R.color.colorWashedWhite));
                    ((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).setBackgroundColor(getResources().getColor(R.color.colorLightGray));
                } else {
                    tagSelected.remove(((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).getText().toString());
                    tagIdSelected.remove(((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).getTag().toString());
                    ((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).setTextColor(getResources().getColor(R.color.colorGrayText));
                    ((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).setBackgroundColor(getResources().getColor(R.color.colorWashedWhite));
                }

                System.out.println(((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).getText());
            }
        });

        final TagListFragment tagListFragment = (TagListFragment) getFragmentManager().findFragmentById(R.id.tag_fragment);

        if (storeId != null && storeId != "") {
            tagListFragment.tagListNotAddedPerStore(storeId, listView, ctx);
        }

        Button button1 = (Button) v.findViewById(R.id.create_new_tag_button);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase databaseReference = mFirebaseDatabaseReference.getDatabase();
                final String newTagValue = newTag.getText().toString().toLowerCase();
                if (!Objects.equals(newTagValue, "")) {
                    databaseReference.getReference("tag").orderByChild("name").startAt(newTagValue).endAt(newTagValue).addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            String msg;
                            if (!dataSnapshot.exists()) {
                                DatabaseReference newRef = databaseReference.getReference("tag").push();
                                String newId = newRef.getKey();
                                Tag tag = new Tag(newId, newTagValue);
                                newRef.setValue(tag);
                                msg = "Tag created !";
                                /*newRef = databaseReference.getReference("sto_tag").push();
                                String stoTagId = newRef.getKey();
                                System.out.println("ss: " + storeId);
                                Sto_tag sto_tag = new Sto_tag(stoTagId, storeId, newId);
                                newRef.setValue(sto_tag);*/
                            }else{
                                msg = "Tag already exists !";
                            }
                            Toast.makeText(getActivity(), msg,
                                    Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }
        });
        final DialogTagFragment dtf = this;
        Button button2 = (Button) v.findViewById(R.id.new_tag_button);
        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final FirebaseDatabase databaseReference = mFirebaseDatabaseReference.getDatabase();
                String msg;
                if (!tagIdSelected.isEmpty()) {
                    for (final String tag : tagIdSelected
                            ) {
                        databaseReference.getReference("sto_tag").orderByChild("sto_id").startAt(tag).endAt(tag).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                if (!dataSnapshot.exists()) {
                                    DatabaseReference newRef = databaseReference.getReference("sto_tag").push();
                                    String newId = newRef.getKey();
                                    Sto_tag sto_tag = new Sto_tag(newId, storeId, tag);
                                    newRef.setValue(sto_tag);
                                }
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }
                    msg = "Tag added !";
                    dtf.dismiss();
                }else{
                    msg = "No tags selected";
                }
                Toast.makeText(getActivity(), msg,
                        Toast.LENGTH_LONG).show();
            }
        });
        System.out.println("az: " + listView.getCount());


        return v;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        TagListFragment f = (TagListFragment) getFragmentManager()
                .findFragmentById(R.id.tag_fragment);
        if (f != null)
            getFragmentManager().beginTransaction().remove(f).commit();
    }

}
