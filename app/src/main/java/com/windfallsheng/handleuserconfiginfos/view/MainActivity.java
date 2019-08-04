package com.windfallsheng.handleuserconfiginfos.view;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.windfallsheng.handleuserconfiginfos.Constants;
import com.windfallsheng.handleuserconfiginfos.R;
import com.windfallsheng.handleuserconfiginfos.db.manager.UserConfigManager;
import com.windfallsheng.handleuserconfiginfos.db.UserConfigService;
import com.windfallsheng.handleuserconfiginfos.db.entity.UserConfigEntity;
import com.windfallsheng.handleuserconfiginfos.widget.SwitchButton;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private SwitchButton mSwitchBtnNotify, mSwitchBtnVoice, mSwitchBtnVibrate;
    private TextView mTvNotify, mTvVoice, mTvVibrate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //
        mSwitchBtnNotify = findViewById(R.id.switchbutton_notify);
        mSwitchBtnVoice = findViewById(R.id.switchbutton_voice);
        mSwitchBtnVibrate = findViewById(R.id.switchbutton_vibrate);
        mTvNotify = findViewById(R.id.textview_notify_status);
        mTvVoice = findViewById(R.id.textview_voice_status);
        mTvVibrate = findViewById(R.id.textview_vibrate_status);

        // 初始化相关配置信息
        UserConfigService userConfigService = new UserConfigService(this);
        userConfigService.initUserConfig();

        // 通知设置
        final UserConfigEntity notifyConfig = UserConfigManager.getInstance().queryUserConfigByKey(Constants.CONFIG_KEY_NOTIFY);
        Log.d(TAG, "method:onCreate#notifyConfig=" + notifyConfig);
        if (notifyConfig == null) {
            mTvNotify.setText(getResources().getString(R.string.config_delete));
        } else {
            int notifyValue = notifyConfig.getValue();
            Log.d(TAG, "method:onCreate#notifyValue=" + notifyValue);
            final boolean notifyChecked = notifyValue == Constants.SWITCH_ON ? true : false;
            mSwitchBtnNotify.setChecked(notifyChecked);
            // 监听事件
            mSwitchBtnNotify.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    Log.d(TAG, "method:onCreate#notifyChecked=" + notifyChecked + ", isChecked=" + isChecked);
                    int switchStatus = isChecked == true ? Constants.SWITCH_ON : Constants.SWITCH_OFF;
                    notifyConfig.setValue(switchStatus);
                    UserConfigManager.getInstance().updateUserConfig(notifyConfig);
                }
            });
        }

        // 声音设置
        final UserConfigEntity voiceConfig = UserConfigManager.getInstance().queryUserConfigByKey(Constants.CONFIG_KEY_VOICE);
        Log.d(TAG, "method:onCreate#voiceConfig=" + voiceConfig);
        if (voiceConfig == null) {
            mTvVoice.setText(getResources().getString(R.string.config_delete));
        } else {
            final int voiceValue = voiceConfig.getValue();
            Log.d(TAG, "method:onCreate#voiceValue=" + voiceValue);
            final boolean voiceChecked = voiceValue == Constants.SWITCH_ON ? true : false;
            mSwitchBtnVoice.setChecked(voiceChecked);
            // 监听事件
            mSwitchBtnVoice.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    Log.d(TAG, "method:onCreate#voiceChecked=" + voiceChecked + ", isChecked=" + isChecked);
                    int switchStatus = isChecked == true ? Constants.SWITCH_ON : Constants.SWITCH_OFF;
                    voiceConfig.setValue(switchStatus);
                    UserConfigManager.getInstance().updateUserConfig(voiceConfig);
                }
            });
        }

        // 震动设置
        final UserConfigEntity vibrateConfig = UserConfigManager.getInstance().queryUserConfigByKey(Constants.CONFIG_KEY_VIBRATE);
        Log.d(TAG, "method:onCreate#vibrateConfig=" + vibrateConfig);
        if (vibrateConfig == null) {
            mTvVibrate.setText(getResources().getString(R.string.config_delete));
        } else {
            final int vibrateValue = vibrateConfig.getValue();
            Log.d(TAG, "method:onCreate#vibrateValue=" + vibrateValue);
            final boolean vibrateChecked = vibrateValue == Constants.SWITCH_ON ? true : false;
            mSwitchBtnVibrate.setChecked(vibrateChecked);
            // 监听事件
            mSwitchBtnVibrate.setOnCheckedChangeListener(new SwitchButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(SwitchButton view, boolean isChecked) {
                    Log.d(TAG, "method:onCreate#vibrateChecked=" + vibrateChecked + ", isChecked=" + isChecked);
                    int switchStatus = isChecked == true ? Constants.SWITCH_ON : Constants.SWITCH_OFF;
                    vibrateConfig.setValue(switchStatus);
                    UserConfigManager.getInstance().updateUserConfig(vibrateConfig);
                }
            });
        }
    }
}
