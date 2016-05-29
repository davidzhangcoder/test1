package com.test1;

import android.view.View;
import android.widget.Toast;

/**
 * Created by David on 14/05/2016.
 */
public class EventHandlers {
    public void handleClick(View view) {
        Toast.makeText(view.getContext(), "you clicked the view", Toast.LENGTH_LONG).show();
    }
}