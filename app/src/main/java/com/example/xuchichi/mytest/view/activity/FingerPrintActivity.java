package com.example.xuchichi.mytest.view.activity;

import android.hardware.fingerprint.FingerprintManager;
import android.os.Bundle;
import android.os.CancellationSignal;
import android.security.keystore.KeyGenParameterSpec;
import android.security.keystore.KeyProperties;
import android.util.Base64;
import android.util.Log;
import android.widget.Button;

import com.example.xuchichi.mytest.R;

import java.security.KeyStore;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class FingerPrintActivity extends BaseActivity {


    @BindView(R.id.btn)
    Button btn;

    public static final String keyName = "key";

    @Override
    public int initLayout() {
        return R.layout.activity_finger_print;
    }

    CancellationSignal mCancellationSignal;
    FingerprintManager manager;

    @Override
    public void initView() {
        initFingerPrint();
        manager = getSystemService(FingerprintManager.class);
        generateKey(keyName);
        FingerprintManager.CryptoObject object;
        object = getCryptoObject(Cipher.ENCRYPT_MODE, null);
        mCancellationSignal = new CancellationSignal();

        manager.authenticate(object, mCancellationSignal, 0, new FingerprintManager.AuthenticationCallback() {
            @Override
            public void onAuthenticationSucceeded(FingerprintManager.AuthenticationResult result) {
                super.onAuthenticationSucceeded(result);
                try {
                    final Cipher cipher = result.getCryptoObject().getCipher();
                    byte[] restults = cipher.doFinal();
                    Log.e("onAuthenticationSucceeded", restults.toString());

                } catch (BadPaddingException | IllegalBlockSizeException e) {

                    e.printStackTrace();
                }
            }

            @Override
            public void onAuthenticationFailed() {
                super.onAuthenticationFailed();
                Log.e("onAuthenticationFailed", "onAuthenticationFailed");

            }
        }, null);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // TODO: add setContentView(...) invocation
        ButterKnife.bind(this);
    }

    KeyStore mStore;

    public void initFingerPrint() {
        try {
            mStore = KeyStore.getInstance("AndroidKeyStore");

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    void generateKey(String keyAlias) {
        //这里使用AES + CBC + PADDING_PKCS7，并且需要用户验证方能取出
        try {
            final KeyGenerator generator = KeyGenerator.getInstance(KeyProperties.KEY_ALGORITHM_AES, "AndroidKeyStore");
            mStore.load(null);
            final int purpose = KeyProperties.PURPOSE_DECRYPT | KeyProperties.PURPOSE_ENCRYPT;
            final KeyGenParameterSpec.Builder builder = new KeyGenParameterSpec.Builder(keyAlias, purpose);
            builder.setUserAuthenticationRequired(true);
            builder.setBlockModes(KeyProperties.BLOCK_MODE_CBC);
            builder.setEncryptionPaddings(KeyProperties.ENCRYPTION_PADDING_PKCS7);
            generator.init(builder.build());
            generator.generateKey();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    FingerprintManager.CryptoObject getCryptoObject(int purpose, byte[] IV) {//生成密钥的对象
        try {
            mStore.load(null);
            final SecretKey key = (SecretKey) mStore.getKey(keyName, null);
            if (key == null) {
                return null;
            }
            final Cipher cipher = Cipher.getInstance(KeyProperties.KEY_ALGORITHM_AES + "/" + KeyProperties.BLOCK_MODE_CBC
                    + "/" + KeyProperties.ENCRYPTION_PADDING_PKCS7);
            if (purpose == KeyProperties.PURPOSE_ENCRYPT) {
                cipher.init(purpose, key);
                cipher.doFinal("123456".getBytes());
            }
            return new FingerprintManager.CryptoObject(cipher);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @OnClick(R.id.btn)
    public void onViewClicked() {


    }
}
