package edu.byu.cs.tweeter.client.view.main;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.squareup.picasso.Picasso;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;

import edu.byu.cs.tweeter.R;
import edu.byu.cs.tweeter.client.cache.Cache;
import edu.byu.cs.tweeter.client.presenter.MainPresenter;
import edu.byu.cs.tweeter.client.presenter.view.MainView;
import edu.byu.cs.tweeter.client.view.login.LoginActivity;
import edu.byu.cs.tweeter.client.view.login.StatusDialogFragment;
import edu.byu.cs.tweeter.model.domain.Status;
import edu.byu.cs.tweeter.model.domain.User;

/**
 * The main activity for the application. Contains tabs for feed, story, following, and followers.
 */
public class MainActivity extends AppCompatActivity implements StatusDialogFragment.Observer, MainView {

    private static final String LOG_TAG = "MainActivity";

    public static final String CURRENT_USER_KEY = "CurrentUser";

    private Toast logOutToast;
    private Toast postingToast;
    private TextView followeeCount;
    private TextView followerCount;
    private Button followButton;
    private MainPresenter presenter = new MainPresenter(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        presenter.setSelectedUser((User) getIntent().getSerializableExtra(CURRENT_USER_KEY));
        if (presenter.getSelectedUser() == null) {
            throw new RuntimeException("User not passed to activity");
        }

        SectionsPagerAdapter sectionsPagerAdapter = new SectionsPagerAdapter(this, getSupportFragmentManager(), presenter.getSelectedUser());
        ViewPager viewPager = findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(1);
        viewPager.setAdapter(sectionsPagerAdapter);
        TabLayout tabs = findViewById(R.id.tabs);
        tabs.setupWithViewPager(viewPager);

        FloatingActionButton fab = findViewById(R.id.fab);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                StatusDialogFragment statusDialogFragment = new StatusDialogFragment();
                statusDialogFragment.show(getSupportFragmentManager(), "post-status-dialog");
            }
        });

        presenter.updateSelectedUserFollowingAndFollowers();

        TextView userName = findViewById(R.id.userName);
        userName.setText(presenter.getSelectedUser().getName());

        TextView userAlias = findViewById(R.id.userAlias);
        userAlias.setText(presenter.getSelectedUser().getAlias());

        ImageView userImageView = findViewById(R.id.userImage);
        Picasso.get().load(presenter.getSelectedUser().getImageUrl()).into(userImageView);

        followeeCount = findViewById(R.id.followeeCount);
        followeeCount.setText(getString(R.string.followeeCount, "..."));

        followerCount = findViewById(R.id.followerCount);
        followerCount.setText(getString(R.string.followerCount, "..."));

        followButton = findViewById(R.id.followButton);

        presenter.isFollower();


        followButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                followButton.setEnabled(false);
                presenter.toFollowOrNotToFollow(followButton.getText().toString().equals(v.getContext().getString(R.string.following)));
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.logoutMenu) {
            logOutToast = Toast.makeText(this, "Logging Out...", Toast.LENGTH_LONG);
            logOutToast.show();
            presenter.logout();
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onStatusPosted(String post) {
        postingToast = Toast.makeText(this, "Posting Status...", Toast.LENGTH_LONG);
        postingToast.show();
        try {
            presenter.postStatus(post);

        } catch (Exception ex) {
            setLogTag(ex);
            displayMessage("Failed to post the status because of exception: " + ex.getMessage());
        }
    }

    public String getFormattedDateTime() throws ParseException {
        SimpleDateFormat userFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        SimpleDateFormat statusFormat = new SimpleDateFormat("MMM d yyyy h:mm aaa");

        return statusFormat.format(userFormat.parse(LocalDate.now().toString() + " " + LocalTime.now().toString().substring(0, 8)));
    }

    @Override
    public void displayMessage(String message) {
        Toast.makeText(MainActivity.this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void displaySuccess(User currentUser) {}

    @Override
    public void enableButton() {
        followButton.setText(R.string.following);
        followButton.setBackgroundColor(getResources().getColor(R.color.white));
        followButton.setTextColor(getResources().getColor(R.color.lightGray));
    }
    @Override
    public void disableButton() {
        followButton.setText(R.string.follow);
        followButton.setBackgroundColor(getResources().getColor(R.color.colorAccent));
    }

    @Override
    public void disableFollowButton() {
        followButton.setVisibility(android.view.View.GONE);
    }

    @Override
    public void enableFollowButton() {
        followButton.setVisibility(android.view.View.VISIBLE);
    }

    @Override
    public void setFollowButton(boolean value) {
        followButton.setEnabled(value);
    }

    @Override
    public void SetFollowerCount(int count) {
        followerCount.setText(getString(R.string.followerCount, String.valueOf(count)));
    }

    @Override
    public void SetFolloweeCount(int count) {
        followeeCount.setText(getString(R.string.followeeCount, String.valueOf(count)));
    }

    @Override
    public void logoutSuccess() {
        logOutToast.cancel();
        //Revert to login screen.
        Intent intent = new Intent(this, LoginActivity.class);
        //Clear everything so that the main activity is recreated with the login page.
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }

    @Override
    public void displaySuccess() {
        postingToast.cancel();
        Toast.makeText(MainActivity.this, "Successfully Posted!", Toast.LENGTH_LONG).show();
    }

    @Override
    public void setLogTag(Exception ex) {
        Log.e(LOG_TAG, ex.getMessage(), ex);
    }
}

