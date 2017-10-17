package com.duy.finalproject.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.OrientationHelper;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.duy.finalproject.R;
import com.duy.finalproject.adapter.PhotoAdapter;
import com.duy.finalproject.api.InterfaceAPI;
import com.duy.finalproject.config.ApiClient;
import com.duy.finalproject.event.RecyclerItemClickListener;
import com.duy.finalproject.models.APIRespond;
import com.duy.finalproject.models.Post;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.iwf.photopicker.PhotoPicker;
import me.iwf.photopicker.PhotoPreview;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by DUY on 8/13/2017.
 */

public class NewPostActivity extends AppCompatActivity {

    private static final int PICK_IMAGE = 1;
    private static final int DONE_ID = 1212;
    private PhotoAdapter photoAdapter;

    @BindView(R.id.edtTitle)
    EditText edtTitle;

    @BindView(R.id.edtContent)
    EditText edtContent;

    @BindView(R.id.recycleView)
    RecyclerView recyclerView;

    private ArrayList<String> imagesPathList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newpost);
        ButterKnife.bind(this);
        initViews();
    }

    private void initViews() {
        photoAdapter = new PhotoAdapter(this, imagesPathList);

        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(4, OrientationHelper.VERTICAL));
        recyclerView.setAdapter(photoAdapter);

        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(this,
                new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        if (photoAdapter.getItemViewType(position) == PhotoAdapter.TYPE_ADD) {
                            PhotoPicker.builder()
                                    .setPhotoCount(PhotoAdapter.MAX)
                                    .setShowCamera(true)
                                    .setPreviewEnabled(false)
                                    .setSelected(imagesPathList)
                                    .start(NewPostActivity.this);
                        } else {
                            PhotoPreview.builder()
                                    .setPhotos(imagesPathList)
                                    .setCurrentItem(position)
                                    .setShowDeleteButton(true)
                                    .start(NewPostActivity.this);
                        }
                    }
                }));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == PhotoPicker.REQUEST_CODE) {
            if (data != null) {
                List<String> photos = null;
                if (data != null) {
                    photos = data.getStringArrayListExtra(PhotoPicker.KEY_SELECTED_PHOTOS);
                }
                imagesPathList.clear();

                if (photos != null) {

                    imagesPathList.addAll(photos);
                }
                photoAdapter.notifyDataSetChanged();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        TextView tv = new TextView(this);
        tv.setText(getString(R.string.done) + "   ");
        tv.setTextColor(getResources().getColor(R.color.white));
        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addNewPost();
            }
        });
        tv.setPadding(5, 0, 5, 0);
        tv.setTypeface(null, Typeface.BOLD);
        tv.setTextSize(14);
        menu.add(0, DONE_ID, 1, R.string.done).setActionView(tv).setShowAsAction(MenuItem.SHOW_AS_ACTION_ALWAYS);
        return super.onCreateOptionsMenu(menu);
    }

    private void addNewPost() {
        final ProgressDialog dialog = new ProgressDialog(this);
        dialog.setCancelable(false);
        dialog.setMessage("Waiting...");
        dialog.show();


        String title = edtTitle.getText().toString();
        String content = edtContent.getText().toString();
        if (title.isEmpty() || content.isEmpty()) {
            Toast.makeText(NewPostActivity.this, "Bạn phải hoàn thành các trường để đăng bài", Toast.LENGTH_SHORT).show();
            return;
        }
        Date date = Calendar.getInstance().getTime();
        Post post = new Post(title, content, imagesPathList, date);

        InterfaceAPI interfaceAPI = ApiClient.getClient().create(InterfaceAPI.class);
        Call<APIRespond<Post>> call = interfaceAPI.addNewPost(post);

        call.enqueue(new Callback<APIRespond<Post>>() {
            @Override
            public void onResponse(Call<APIRespond<Post>> call, Response<APIRespond<Post>> response) {
                APIRespond<Post> respond = response.body();
                switch (respond.getCode()) {
                    case 0:
                        Toast.makeText(NewPostActivity.this, "Bài viết của bạn đã được đăng", Toast.LENGTH_SHORT).show();
                        break;
                    case -4:
                        Toast.makeText(NewPostActivity.this, "", Toast.LENGTH_SHORT).show();
                        break;
                    case 500:
                        Toast.makeText(NewPostActivity.this, "Server error!", Toast.LENGTH_SHORT).show();
                        break;
                }
                dialog.dismiss();
            }

            @Override
            public void onFailure(Call<APIRespond<Post>> call, Throwable t) {
                Toast.makeText(NewPostActivity.this, "Bài viết của bạn đã được đăng", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
