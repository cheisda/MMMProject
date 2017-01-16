package miage.istic.com.asianmarketfinder.fragments;

import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.security.auth.callback.Callback;

import miage.istic.com.asianmarketfinder.MapsActivity;
import miage.istic.com.asianmarketfinder.R;
import miage.istic.com.asianmarketfinder.adapters.TagAdapter;
import miage.istic.com.asianmarketfinder.database.sto_tag.Sto_tag;
import miage.istic.com.asianmarketfinder.database.tag.Tag;

/**
 * Created by Rom on 1/15/2017.
 */
public class TagListFragment extends Fragment {

    private ListView listView;
    private Button addTagButton;
    private String idStore;
    private final DatabaseReference mFirebaseDatabaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Bundle bundle = getArguments();
        if (this.idStore == null) {
            if (bundle != null) {
                this.idStore = getArguments().getString("id_store");
            } else {
                this.idStore = "";
            }
        }

        View rootView = inflater.inflate(R.layout.tag_list, container, false);
        this.listView = (ListView) rootView.findViewById(R.id.tag_listview);
        this.addTagButton = (Button) rootView.findViewById(R.id.add_tag_button);

        /*
        ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, listItems);
        this.listView.setAdapter(adapter);*/

        return rootView;
    }

    @Override
    public void onViewCreated(final View view, Bundle savedInstanceState) {
        final Context ctx = this.getActivity();
        final FirebaseDatabase databaseReference = mFirebaseDatabaseReference.getDatabase();
        this.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                System.out.println("azre: " + ((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).getText().toString());
                Uri uri = Uri.parse("http://www.google.com/#q=" + ((TextView) ((RelativeLayout) view).findViewById(R.id.tag_libelle)).getText().toString());
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
                return true;
            }
        });

        if (this.idStore != null && this.idStore != "") {
            databaseReference.getReference("sto_tag").orderByChild("sto_id").startAt(this.idStore).endAt(this.idStore).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        final List<Tag> tagList = new ArrayList<Tag>();
                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                            final Sto_tag sto_tag = snapshot.getValue(Sto_tag.class);
                            System.out.println("tre: " + sto_tag.getTag_id());
                            databaseReference.getReference("tag").orderByChild("id").startAt(sto_tag.getTag_id()).endAt(sto_tag.getTag_id()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(DataSnapshot dataSnapshot) {
                                    if (dataSnapshot.exists()) {
                                        System.out.println("sqd: " + dataSnapshot);
                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                            Tag tag = snapshot.getValue(Tag.class);
                                            tagList.add(tag);
                                            System.out.println("tag: " + tag);
                                        }
                                        TagAdapter tagListFragment = new TagAdapter(ctx, tagList);
                                        listView.setAdapter(tagListFragment);
                                    }
                                }

                                @Override
                                public void onCancelled(DatabaseError databaseError) {

                                }
                            });
                        }

                    }
                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });

            this.addTagButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*final Dialog dialog = new Dialog(ctx);
                    dialog.setContentView(R.layout.dialog_add_tag);
                    dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {

                        @Override
                        public boolean onKey(DialogInterface arg0, int keyCode,
                                             KeyEvent event) {
                            // TODO Auto-generated method stub
                            if (keyCode == KeyEvent.KEYCODE_BACK) {
                                dialog.dismiss();
                            }
                            return true;
                        }
                    });

                    Button button = (Button) dialog.findViewById(R.id.add_tag_button);
                    button.setVisibility(View.GONE);

                    Button button2 = (Button) dialog.findViewById(R.id.new_tag_button);
                    button2.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                        }
                    });*/
                    Log.e("tag", "handleSignInResult:" + ((MapsActivity) getActivity()).getUser());
                    if(((MapsActivity)getActivity()).getUser() != null ){
                        FragmentTransaction ft = getFragmentManager().beginTransaction();
                        Fragment prev = getFragmentManager().findFragmentByTag("dialogTag");
                        if (prev != null) {
                            ft.remove(prev);
                        }
                        ft.addToBackStack(null);
                        DialogTagFragment dialogTagFragment = DialogTagFragment.newInstance(idStore);
                        dialogTagFragment.show(ft, "dialogTag");
                    }else{
                        ((MapsActivity)getActivity()).signIn();
                    }

                }
            });
        } else if (Objects.equals(this.idStore, "")) {

        }

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        getFragmentManager().beginTransaction().remove(this).commit();
    }

    public void tagListNotAddedPerStore(String storeId, final ListView listView, final Context ctx) {
        final FirebaseDatabase databaseReference = mFirebaseDatabaseReference.getDatabase();
        databaseReference.getReference("sto_tag").orderByChild("sto_id").startAt(storeId).endAt(storeId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.exists()) {
                    final List<String> tagListString = new ArrayList<String>();
                    for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                        final Sto_tag sto_tag = snapshot.getValue(Sto_tag.class);
                        tagListString.add(sto_tag.getTag_id());
                        System.out.println("aa: " + sto_tag.getTag_id());
                    }
                    databaseReference.getReference("tag").orderByChild("name").addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(DataSnapshot dataSnapshot) {
                            if (dataSnapshot.exists()) {
                                final List<Tag> tagList = new ArrayList<Tag>();
                                for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                    final Tag tag = snapshot.getValue(Tag.class);
                                    System.out.println(("tagggg: " + tag.getId()));
                                    if (!tagListString.contains(tag.getId())) {
                                        System.out.println("test: " + tagList.contains(tag.getId()) + " /// " + tag.getId());
                                        tagList.add(tag);
                                    }
                                }
                                listView.setAdapter(null);
                                TagAdapter tagListFragment = new TagAdapter(ctx, tagList);
                                listView.setAdapter(tagListFragment);
                            }
                        }

                        @Override
                        public void onCancelled(DatabaseError databaseError) {

                        }
                    });
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }

}
