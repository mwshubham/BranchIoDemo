package com.example.branchiodemo;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import com.example.branchiodemo.databinding.ActivityBranchDeepLinkingBinding;
import io.branch.referral.Branch;
import io.branch.referral.BranchError;
import org.json.JSONObject;

public class BranchDeepLinkingActivity extends AppCompatActivity {

    private static final String TAG = BranchDeepLinkingActivity.class.getSimpleName();
    private ActivityBranchDeepLinkingBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(TAG, "onCreate()");
        binding = DataBindingUtil.setContentView(this, R.layout.activity_branch_deep_linking);
        binding.progressCircular.setVisibility(View.VISIBLE);
    }

    @Override
    public void onNewIntent(Intent intent) {
        this.setIntent(intent);
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.d(TAG, "onStart()");
        Branch.getInstance().initSession(new Branch.BranchReferralInitListener() {
            @Override
            public void onInitFinished(JSONObject referringParams, BranchError error) {
                if (error == null) {
                    Log.d(TAG, "referringParams: " + referringParams.toString());
//                    if (referringParams.optString(BranchConstants.ANDROID_DEEP_LINK_PATH).isEmpty()) {
//                        Intent intent = new Intent(BranchDeepLinkingActivity.this, HomePageActivity.class);
//                        startActivity(intent);
//                        finish();
//                    } else {
//                        Intent deepLinkIntent = new Intent(BranchDeepLinkingActivity.this, DeepLinkingHandler.class);
//                        String data = referringParams.optString(BranchConstants.ANDROID_DEEP_LINK_PATH);
//                        if (data.contains("http")) {
//                            deepLinkIntent.setData(Uri.parse(data));
//                            startActivity(deepLinkIntent);
//                            finish();
//                        } else {
//                            String nData = "http://" + data;
//                            deepLinkIntent.setData(Uri.parse(nData));
//                            startActivity(deepLinkIntent);
//                            finish();
//                        }
//                    }
                    // Retrieve deeplink keys from 'referringParams' and evaluate the values to determine where to route the user
                    // Check '+clicked_branch_link' before deciding whether to use your Branch routing logic
                } else {
                    Log.d(TAG, "BranchError: " + error.getMessage());

                }
            }
        }, this.getIntent().getData(), this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (binding.progressCircular.getVisibility() == View.VISIBLE) {
            binding.progressCircular.setVisibility(View.GONE);
        }
    }
}