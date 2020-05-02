package com.example.xuchichi.mytest.view.activity;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.AppCompatAutoCompleteTextView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.MultiAutoCompleteTextView;

import com.example.xuchichi.mytest.R;
import com.example.xuchichi.mytest.service.MyBluetoothService;
import com.example.xuchichi.mytest.view.adapter.MyBluetoothAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MyBluetoothActivity extends AppCompatActivity {
//
//    @BindView(R.id.btnScan)
//    Button btnScan;
//    @BindView(R.id.tvDeviceInfo)
//    TextView tvDeviceInfo;
//    @BindView(R.id.btnStop)
//    Button btnStop;
//    @BindView(R.id.btnConnect)
//    Button btnConnect;
//    @BindView(R.id.tvBluetoothInfo)
//    TextView tvBluetoothInfo;
//    @BindView(R.id.listview)
//    ListView listview;
//    @BindView(R.id.tvEmpty)
//    TextView tvEmpty;

    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
    MyBluetoothAdapter adapter;
    List<BluetoothDevice> list = new ArrayList<>();


    //hanlder消息标识 message.what
    public static final int MESSAGE_STATE_CHANGE = 1; // 状态改变
    public static final int MESSAGE_READ = 2;          // 读取数据
    public static final int MESSAGE_WRITE = 3;         // 给硬件传数据，暂不需要，看具体需求
    public static final int MESSAGE_DEVICE_NAME = 4;  // 设备名字
    public static final int MESSAGE_TOAST = 5;

    //传感器 ,这里默认同时需要和三个硬件连接，分别设置id 1,2,3进行区分，demo中实际只用到 MAGIKARE_SENSOR_DOWN = 1
    //可以根据情况自行添加删除
    public static final int MAGIKARE_SENSOR_UP = 2;
    public static final int MAGIKARE_SENSOR_DOWN = 1;
    public static final int MAGIKARE_SENSOR_CENTER = 3;


    MyBluetoothService mBluetoothService;
    @BindView(R.id.ma_auto_text_new)
    AppCompatAutoCompleteTextView maAutoTextNew;
    @BindView(R.id.atv_content)
    AutoCompleteTextView atvContent;
    @BindView(R.id.matv_content)
    MultiAutoCompleteTextView matvContent;

    private String mConnectedDeviceName = null; //连接设备的名称


    @Override

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bluetooth);
        ButterKnife.bind(this);
        initViews();
//        View.SYSTEM_UI_FLAG_LAYOUT_STABLE
//                |View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
//                | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
        // 设置导航栏透明
    }
    private static final String[] data = new String[]{
            "小猪猪", "小狗狗", "小鸡鸡", "小猫猫", "小咪咪"
    };
    private void initViews() {
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line, data);

        atvContent.setAdapter(adapter);

        ArrayAdapter<String> adapter2 = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_dropdown_item_1line, data);
        matvContent.setAdapter(adapter2);
        matvContent.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
    }

    public final int ACTION_REQUEST_ENABLE = 1;

//    public void init() {
//
//        adapter = new MyBluetoothAdapter(list, this);
//        listview.setAdapter(adapter);
//        listview.setOnItemClickListener(itemClickListener);
//
//        Set<BluetoothDevice> pairedDevice = bluetoothAdapter.getBondedDevices();
//        if (pairedDevice != null && pairedDevice.size() > 0) {
//            for (BluetoothDevice device : pairedDevice) {
//                list.add(device);
//                adapter.notifyDataSetChanged();
//            }
//        }
//
//        if (bluetoothAdapter == null) {
//            tvDeviceInfo.setText("该设备无蓝牙功能");
//        }
//        tvBluetoothInfo.setText(bluetoothAdapter.getName());
//        if (!bluetoothAdapter.isEnabled()) {
//            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
//            startActivityForResult(intent, ACTION_REQUEST_ENABLE);
//        } else {
//            tvEmpty.setVisibility(View.VISIBLE);
//        }
//
//        IntentFilter filter = new IntentFilter();
//        //发现设备
//        filter.addAction(BluetoothDevice.ACTION_FOUND);
//        //设备连接状态改变
//        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
//        //蓝牙设备状态改变
//        filter.addAction(BluetoothAdapter.ACTION_STATE_CHANGED);
//
//        registerReceiver(receiver, filter);
//
//        // 3、创建自定义蓝牙服务对象
//        if (mBluetoothService == null) {
//            mBluetoothService = new MyBluetoothService(mHandler);
//        }
//    }
//
//    public void scanDevice() {
//        if (bluetoothAdapter.isEnabled()) {
//            bluetoothAdapter.startDiscovery();//扫描设备，系统会发送广播
//        } else {
//            Log.e("else", "else");
//        }
//    }
//
//    AdapterView.OnItemClickListener itemClickListener = new AdapterView.OnItemClickListener() {
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//            BluetoothDevice device = list.get(position);
//
//            Log.e("addres", device.getAddress());
//
//            if (mBluetoothService != null) {
//                //根据MAC地址远程获取一个蓝牙设备，这里固定了，实际开发中，需要动态设置参数（MAC地址）
//                BluetoothDevice sensor_down = bluetoothAdapter.getRemoteDevice(device.getAddress());
//                if (sensor_down != null) {
//                    mBluetoothService.connect(sensor_down);
////                    if (sensor_down.getBondState() != BluetoothDevice.BOND_BONDING) {
////                        //成功获取到远程蓝牙设备（传感器），这里默认只连接MAGIKARE_SENSOR_DOWN = 1这个设备
////                        mBluetoothService.connect(sensor_down);
////                    } else {
////                        ToastUtils.showText("设备已连接");
////                    }
//
//                }
//            }
//
//        }
//    };
//    /**
//     * 连接蓝牙设备
//     */
//
//
//    BroadcastReceiver receiver = new BroadcastReceiver() {
//        @Override
//        public void onReceive(Context context, Intent intent) {
//            String action = intent.getAction();
//            Log.e("aciton", "action=" + action);
//            switch (action) {
//                case BluetoothDevice.ACTION_FOUND:
//
//                    BluetoothDevice device = intent.getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);
//
//                    if (device.getBondState() != BluetoothDevice.BOND_BONDED) {
//                        if (!list.contains(device)) {
//                            list.add(device);
//                            adapter.notifyDataSetChanged();
//                        }
//
//                        Log.e("uuid:", device.getUuids() + "");
//                        tvDeviceInfo.setText(device.getUuids() + "");
//                    }
//
//                    break;
//                case BluetoothDevice.ACTION_BOND_STATE_CHANGED:
//                    break;
//                case BluetoothAdapter.ACTION_STATE_CHANGED:
//                    break;
//
//            }
//        }
//    };
//
//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        switch (requestCode) {
//            case ACTION_REQUEST_ENABLE:
//                if (resultCode == RESULT_OK) {
//                    tvBluetoothInfo.setText("蓝牙开启成功");
//                }
//                break;
//        }
//    }
//
//    BluetoothFragment bluetoothFragment;
//
//    @OnClick({R.id.btnScan, R.id.btnStop, R.id.btnConnect, R.id.tvConnect})
//    public void onViewClicked(View view) {
//        switch (view.getId()) {
//            case R.id.tvConnect:
//                bluetoothFragment = new BluetoothFragment();
//                bluetoothFragment.show(getFragmentManager(), "");
//                break;
//            case R.id.btnScan:
//                Log.e("start", "onclick");
//                scanDevice();
//                break;
//            case R.id.btnStop:
//                bluetoothAdapter.cancelDiscovery();
//                break;
//            case R.id.btnConnect://写入数
//                String message = "test";
//                byte[] send = message.getBytes();
//                mBluetoothService.write(send);
//
//                break;
//        }
//    }
//
//
//    private Handler mHandler = new Handler(new Handler.Callback() {
//        @Override
//        public boolean handleMessage(Message msg) {
//            switch (msg.what) {
//                case MESSAGE_READ:
//                    try {
//                        String str = msg.getData().getString("index");
//                        int index = Integer.valueOf(str);
//                        switch (index) {
//                            //获取到蓝牙传输过来的数据
//                            case MAGIKARE_SENSOR_UP:
//                                Log.e("MAGIKARE_SENSOR_UP", msg.getData().getFloatArray("Data").toString());
////                                m_receive_data_up = msg.getData().getFloatArray("Data");
//                                break;
//                            //实际只用到这个case ，因为demo只连接了一个硬件设备
//                            case MAGIKARE_SENSOR_DOWN:
//                                Log.e("MAGIKARE_SENSOR_DOWN", msg.getData().getFloatArray("Data").toString());
////                                m_receive_data_down = msg.getData().getFloatArray("Data");
//                                break;
//                            case MAGIKARE_SENSOR_CENTER:
//                                Log.e("MAGIKARE_SENSOR_CENTER", msg.getData().getFloatArray("Data").toString());
////                                m_receive_data_center = msg.getData().getFloatArray("Data");
//                                break;
//
//                        }
//                    } catch (Exception e) {
//                        // TODO: handle exception
//                    }
//                    break;
//                case MESSAGE_STATE_CHANGE:
//                    //连接状态
//                    switch (msg.arg1) {
//                        case BluetoothService.STATE_CONNECTED:
//                            Log.e("STATE_CONNECTED", "STATE_CONNECTED");
//                            break;
//                        case BluetoothService.STATE_CONNECTING:
//                            Log.e("STATE_CONNECTING", "STATE_CONNECTING");
//                            break;
//                        case BluetoothService.STATE_LISTEN:
//                            Log.e("STATE_LISTEN", "STATE_LISTEN");
//                            break;
//                        case BluetoothService.STATE_NONE:
//                            Log.e("STATE_NONE", "STATE_NONE");
//                            break;
//                    }
//                    break;
//                case MESSAGE_DEVICE_NAME:
//                    mConnectedDeviceName = msg.getData().getString("device_name");
//                    Log.i("bluetooth", "成功连接到:" + mConnectedDeviceName);
//                    Toast.makeText(getApplicationContext(), "成功连接到设备" + mConnectedDeviceName, Toast.LENGTH_SHORT).show();
//
//                    break;
//                case MESSAGE_TOAST:
//                    int index = msg.getData().getInt("device_id");
//                    Toast.makeText(getApplicationContext(), msg.getData().getString("toast"), Toast.LENGTH_SHORT).show();
//                    //当失去设备或者不能连接设备时，重新连接
//                    Log.d("Magikare", "当失去设备或者不能连接设备时，重新连接");
//                    break;
//            }
//            return false;
//        }
//    });
//
//    public synchronized void onResume() {
//        super.onResume();
//
//        if (mBluetoothService != null) {
//            if (mBluetoothService.getState() == BluetoothService.STATE_NONE) {
//                mBluetoothService.start();
//            }
//        }
//    }
//
//    @Override
//    public void onDestroy() {
//        super.onDestroy();
//        unregisterReceiver(receiver);
//        if (mBluetoothService != null) mBluetoothService.stop();
//    }
}
