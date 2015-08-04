package com.example.FilePersistenceTest;

import android.app.Activity;
import android.content.Context;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.EditText;
import android.widget.Toast;

import java.io.*;

public class MyActivity extends Activity {
    /**
     * Called when the activity is first created.
     */
    private EditText edit;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        edit = (EditText) findViewById(R.id.edit);
        //����load������ȡ�ļ�����
        String inputText = load();
   //  TextUtils.isEmpty�ж��ַ������� null ���ߵ��� ���ַ��� ����true��ͬʱ�ж�����ֵ
        if(!TextUtils.isEmpty(inputText)){
            edit.setText(inputText);
            //setSelection�趨���λ��
            edit.setSelection(inputText.length());
            Toast.makeText(this,"Restoring succedded",Toast.LENGTH_SHORT).show();
        }
    }
    public String load(){
        FileInputStream in = null;
        BufferedReader reader = null;
        StringBuilder content = new StringBuilder();
        try{
            in = openFileInput("data");
            reader = new BufferedReader(new InputStreamReader(in));
            String line = "";
            while((line = reader.readLine()) != null){
                content.append(line);
            }
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            if(reader != null){
                try{
                    reader.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
        }
        return content.toString();
    }
//ִ�е�onDestroyʱ����save��������
    @Override
    protected void onDestroy() {
        super.onDestroy();
        String inputText = edit.getText().toString();
        save(inputText);
  //      System.out.print(inputText);
    }

    public void save(String inputText) {
        FileOutputStream out = null;
        BufferedWriter writer = null;
        try {
         //�����ļ�data������private
            //ϵͳĬ��/data/data/<package name>/files/Ŀ¼
            out = openFileOutput("data", Context.MODE_PRIVATE);
            writer = new BufferedWriter(new OutputStreamWriter(out));
            writer.write(inputText);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                if (writer != null) {
                    writer.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
