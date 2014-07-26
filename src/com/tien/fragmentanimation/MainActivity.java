package com.tien.fragmentanimation;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.app.ActionBar;
import android.app.Activity;
import android.graphics.Rect;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.ActionMode;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Interpolator;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tien.fragmentanimation.fragment.AddressFragment;
import com.tien.view.CircularProgressButton;

public class MainActivity extends Activity implements OnClickListener {
    
    private final static int ANIMATION_DURATION = 400;
    
    private LinearLayout containerLL;
    private FrameLayout editModeContainer;
    private RelativeLayout fromRL;
    private RelativeLayout toRL;
    private View fromToDivider;
    private View timeWayDivider;
    private TextView fromTV;
    private TextView toTV;
    private EditText fromET;
    private EditText toET;
    private TextView timeTV;
    private TextView wayTV;
    private TextView nameTV;
    private CircularProgressButton searchBtn;
    
    private AddressFragment addressFragment;
    
    private ActionMode actionMode;
    private Interpolator interpolator;
    private Handler handler;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        ActionBar actionBar = getActionBar();
        if (actionBar != null) {
            actionBar.setTitle("Tienfeek");
            actionBar.setDisplayShowHomeEnabled(false);
        }
        
        this.findView();
        this.addListener();
        
        searchBtn.setIndeterminateProgressMode(true);
        
        addressFragment = new AddressFragment();
        getFragmentManager().beginTransaction().add(R.id.edit_mode_container, addressFragment)
            .commit();
        
        interpolator = new AccelerateDecelerateInterpolator();
        handler = new Handler();
    }
    
    private void findView() {
        containerLL = (LinearLayout) findViewById(R.id.container_ll);
        editModeContainer = (FrameLayout) findViewById(R.id.edit_mode_container);
        fromRL = (RelativeLayout) findViewById(R.id.from_rl);
        toRL = (RelativeLayout) findViewById(R.id.to_rl);
        fromToDivider = findViewById(R.id.from_to_divider);
        timeWayDivider = findViewById(R.id.time_way_divider);
        
        fromTV = (TextView) findViewById(R.id.from_tv);
        toTV = (TextView) findViewById(R.id.to_tv);
        fromET = (EditText) findViewById(R.id.from_et);
        toET = (EditText) findViewById(R.id.to_et);
        timeTV = (TextView) findViewById(R.id.time_tv);
        wayTV = (TextView) findViewById(R.id.way_tv);
        nameTV = (TextView) findViewById(R.id.name_tv);
        searchBtn = (CircularProgressButton) findViewById(R.id.search_btn);
    }
    
    private void addListener() {
        fromTV.setOnClickListener(this);
        toTV.setOnClickListener(this);
        timeTV.setOnClickListener(this);
        wayTV.setOnClickListener(this);
        nameTV.setOnClickListener(this);
        searchBtn.setOnClickListener(this);
        
    }
    
    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.from_tv) {
            focusOn(v, fromRL);
            focusOn(v, fromToDivider);
            focusOn(v, toRL);
            stickTo(timeTV, v);
            stickTo(timeWayDivider, v);
            stickTo(wayTV, v);
            stickTo(nameTV, v);
            stickTo(searchBtn, v);
            
            slideInToTop();
            
            actionMode = startActionMode(mCallback);
            actionMode.setTitle("From");
            
        } else if (v.getId() == R.id.to_tv) {
        	focusOn(v, fromRL);
            focusOn(v, fromToDivider);
            focusOn(v, toRL);
            stickTo(timeTV, v);
            stickTo(timeWayDivider, v);
            stickTo(wayTV, v);
            stickTo(nameTV, v);
            stickTo(searchBtn, v);
            
            slideInToTop();
            
            actionMode = startActionMode(mCallback);
            actionMode.setTitle("To");
            
        } else if (v.getId() == R.id.time_tv) {
        	focusOn(v, fromRL);
            focusOn(v, fromToDivider);
            focusOn(v, toRL);
            focusOn(v, timeTV);
            focusOn(v, timeWayDivider);
            stickTo(wayTV, v);
            stickTo(nameTV, v);
            stickTo(searchBtn, v);
            
            slideInToTop();
            
            actionMode = startActionMode(mCallback);
            actionMode.setTitle("Time");
        } else if (v.getId() == R.id.way_tv) {
        	focusOn(v, fromRL);
            focusOn(v, fromToDivider);
            focusOn(v, toRL);
            focusOn(v, timeTV);
            focusOn(v, timeWayDivider);
            focusOn(v, wayTV);
            stickTo(nameTV, v);
            stickTo(searchBtn, v);
            
            slideInToTop();
            
            actionMode = startActionMode(mCallback);
            actionMode.setTitle("Return");
            actionMode.setTitle("Time");
        } else if (v.getId() == R.id.name_tv) {
        	focusOn(v, fromRL);
        	focusOn(v, fromToDivider);
        	focusOn(v, toRL);
        	focusOn(v, timeTV);
        	focusOn(v, timeWayDivider);
        	focusOn(v, wayTV);
        	focusOn(v, nameTV);
        	
        	stickTo(searchBtn, v);
        	
        	slideInToTop();
        	
        	actionMode = startActionMode(mCallback);
        	actionMode.setTitle("Return");
        } else if (v.getId() == R.id.search_btn) {
        	searchBtn.setEnabled(false);
        	
        	handler.post(new Runnable() {
				
				@Override
				public void run() {
					if(searchBtn.getProgress() == 100){
						searchBtn.setEnabled(true);
						return;
					} 
					
					searchBtn.setProgress(searchBtn.getProgress() + 10);
					handler.postDelayed(this, 400);
				}
			});
        	
            if (searchBtn.getProgress() == 0) {
                searchBtn.setProgress(50);
            } else if (searchBtn.getProgress() == 100) {
                searchBtn.setProgress(0);
            } else {
                searchBtn.setProgress(100);
            }
        }
    }
    
    private void focusOn(View v, View movableView) {
        Rect rect = new Rect();
        v.getDrawingRect(rect);
        
        containerLL.offsetDescendantRectToMyCoords(v, rect);
        movableView.animate().translationY(-rect.top).setDuration(ANIMATION_DURATION)
            .setInterpolator(interpolator)
            .start();
    }
    
    private void stickTo(View v, View viewToStickTo) {
        Rect rect = new Rect();
        viewToStickTo.getDrawingRect(rect);
        containerLL.offsetDescendantRectToMyCoords(viewToStickTo, rect);
        v.animate().translationY(containerLL.getHeight() - rect.top)
            .setDuration(ANIMATION_DURATION).setInterpolator(interpolator)
            .start();
        
    }
    
    private void stickIn(View v){
    	v.animate().translationY(0)
        .setDuration(ANIMATION_DURATION).setInterpolator(interpolator)
        .setStartDelay(ANIMATION_DURATION/3)
        .start();
    }
    
    private void slideInToTop() {
        View fragment = addressFragment.getView();
        
        fragment.setTranslationY(editModeContainer.getHeight());
        editModeContainer.setVisibility(View.VISIBLE);
        fragment.setAlpha(0);
        
        fragment.animate().translationY(0).alpha(1).setDuration(ANIMATION_DURATION)
            .setInterpolator(interpolator)
            .setStartDelay(ANIMATION_DURATION/3)
            .setListener(new AnimatorListenerAdapter() {
                
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    
                    fromTV.setVisibility(View.GONE);
                    fromET.setVisibility(View.VISIBLE);
                    fromET.requestFocus();
                    
                }
            }).start();
        
    }
    
    
    private void focusOff(View movableView){
    	movableView.animate().translationY(0).setDuration(ANIMATION_DURATION)
        .setInterpolator(interpolator)
        .setStartDelay(ANIMATION_DURATION/3)
        .start();
    }
    
    private void fadeOutToBottom() {
         View fragment = addressFragment.getView();
         fragment.animate().translationY(editModeContainer.getHeight()).alpha(0).setDuration(ANIMATION_DURATION)
             .setInterpolator(interpolator)
             .setListener(new AnimatorListenerAdapter() {
                 
                 @Override
                 public void onAnimationEnd(Animator animation) {
                     super.onAnimationEnd(animation);
                     
                     editModeContainer.setVisibility(View.VISIBLE);
                     
                 }
             }).start();
    }
    
    private ActionMode.Callback mCallback = new ActionMode.Callback() {
        
        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }
        
        @Override
        public void onDestroyActionMode(ActionMode mode) {
        	Log.i("wanges", "onDestroyActionMode ");
        	 focusOff(fromRL);
        	 focusOff(fromToDivider);
        	 focusOff(toRL);
        	 fadeOutToBottom();
        	 stickIn(timeTV);
        	 stickIn(wayTV);
        	 stickIn(timeWayDivider);
        	 stickIn(nameTV);
        	 stickIn(searchBtn);
        	 
        	 fromTV.setVisibility(View.VISIBLE);
             fromET.setVisibility(View.GONE);
        }
        
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            MenuInflater inflater = mode.getMenuInflater();
//            inflater.inflate(R.menu.from_actionmode, menu);
            
            return true;
        }
        
        @Override
        public boolean onActionItemClicked(ActionMode mode, MenuItem item) {
            return false;
        }
        
    };
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
}
