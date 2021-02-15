package com.example.mute.main;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.os.Bundle;

import com.example.mute.R;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    private ArrayList<Quest> quests;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        quests = (ArrayList<Quest>) readObject(this, "cache");
        if (quests == null) {
            quests = new ArrayList<>();
            quests.add(new Quest("休息", 15 * 60 * 1000, 45 * 60 * 1000));
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveObject(this, quests, "cache");
    }

    public ArrayList<Quest> getQuests() {
        return quests;
    }

    public Quest getSelectedQuest() {
        for(Quest quest: quests){
            if (quest.isSelected())
                return quest;
        }
        return null;
    }

    public void removeQuest(Quest quest) {
        getQuests().remove(quest);
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }


    /**
     * 保存对象
     *
     * @param ser  要保存的序列化对象
     * @param file 保存在本地的文件名
     */
    public static void saveObject(Context context, Serializable ser, String file) {
        FileOutputStream fos = null;
        ObjectOutputStream oos = null;
        try {
            fos = context.openFileOutput(file, Context.MODE_PRIVATE);
            oos = new ObjectOutputStream(fos);
            oos.writeObject(ser);
            oos.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                oos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            try {
                fos.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 读取对象
     *
     * @param file 保存在本地的文件名
     */
    public Serializable readObject(Context context, String file) {
        if (!new File(getFilesDir().getPath() + "/" + file).exists()) {
            System.out.println("not existing cache!!!");
            return null;
        }
        try (FileInputStream fis = context.openFileInput(file); ObjectInputStream ois = new ObjectInputStream(fis)) {
            return (Serializable) ois.readObject();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
            // 反序列化失败 - 删除缓存文件
            if (e instanceof InvalidClassException) {
                File data = context.getFileStreamPath(file);
                boolean status = data.delete();
            }
        }
        return null;
    }
}