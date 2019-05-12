package com.jinbang.service;

import com.jinbang.mapper.*;
import com.jinbang.model.*;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Service
public class ItemService {
    @Autowired
    ItemMapper itemMapper;
    @Autowired
    AnswerMapper answerMapper;
    @Autowired
    UserMapper userMapper;
    @Autowired
    Item_kpMapper item_kpMapper;
    @Autowired
    KnowledgePointMapper knowledgePointMapper;
    @Autowired
    KPPathService kpPathService;

//    public int deleteItemById(int iid){
//        return itemMapper.deleteItemById(iid);
//    };
//    public int upgradeItemById(Item item){
//        return itemMapper.upgradeItemById(item);
//    };
//    public int addItem(Item item){
//        return itemMapper.addItem(item);
//    };
//    public Item getItemById(int iid){
//        return itemMapper.getItemById(iid);
//    };
//    public List<Item> getAll(){
//        return itemMapper.getAll();
//    };
//    public List<Item> getItemsByType(String type){
//        return itemMapper.getItemsByType(type);
//    };
//    public List<Item> getItemsByGrade(String grade){
//        return itemMapper.getItemsByGrade(grade);
//    };
//    public List<Item> getItemsBySource(String source){
//        return itemMapper.getItemsBySource(source);
//    };
//    public List<Item> getItemsByUid(int uid){
//        return itemMapper.getItemsByUid(uid);
//    };
//    public Item getItemByAsrid(int asrid){
//        return itemMapper.getItemByAsrid(asrid);
//    };
//    public List<Item> getItemLikeContent(String content){
//        return itemMapper.getItemLikeContent(content);
//    };
    public Item_Asr_Usr_IK_Kp getItem_Asr_Usr_IK_KpByIid(int iid){
        Item_Asr_Usr_IK_Kp item_asr_usr_ik_kp = new Item_Asr_Usr_IK_Kp();
        Item item = itemMapper.getItemById(iid);
        User user = userMapper.getUserById(item.getUid());
        Answer answer = answerMapper.getAnswerById(item.getAsrid());
        List<Item_kp> item_kps = item_kpMapper.getItem_kpByIid(item.getIid());
        int [] item_kps_pids = new int [item_kps.size()];
        List<Knowledgepoint> knowledgepoints = new ArrayList<Knowledgepoint>();
        if(item_kps != null){
            for (Item_kp item_kp : item_kps) {
                int kpid = item_kp.getKpid();
                Knowledgepoint knowledgepoint = knowledgePointMapper.getKpById(kpid);
                knowledgepoints.add(knowledgepoint);
            }
        }
        String path = "";
        if(knowledgepoints != null){
            for (Knowledgepoint knowledgepoint : knowledgepoints) {
                path = kpPathService.getKPPathByKpid(knowledgepoint.getKpid()) + "," + path;
            }
            path = path.substring(0, path.length()-1);
        }
        item_asr_usr_ik_kp.setItem(item);
        item_asr_usr_ik_kp.setAnswer(answer);
        item_asr_usr_ik_kp.setUser(user);
        item_asr_usr_ik_kp.setItem_kps(item_kps);
        item_asr_usr_ik_kp.setKnowledgepoints(knowledgepoints);
        item_asr_usr_ik_kp.setkPPath(path);
        return item_asr_usr_ik_kp;
    }
    public List<Item_Asr_Usr_IK_Kp> itemall(){
        List<Item_Asr_Usr_IK_Kp> item_asr_usr_ik_kps = new ArrayList<Item_Asr_Usr_IK_Kp>();
        List<Item>  items = itemMapper.getAll();
        Item_Asr_Usr_IK_Kp item_asr_usr_ik_kp;
        for (Item item : items) {
            item_asr_usr_ik_kp = this.getItem_Asr_Usr_IK_KpByIid(item.getIid());
            item_asr_usr_ik_kps.add(item_asr_usr_ik_kp);
        }
        return item_asr_usr_ik_kps;
    }
    public List<HashMap> itemradio(){
        List<HashMap> results = new ArrayList<HashMap>();
        HashMap hashMap = new HashMap();
        HashMap hashMap2 = new HashMap();
        HashMap hashMap3 = new HashMap();
        HashMap hashMap4 = new HashMap();
        List<String> types = itemMapper.getTypes();
        List<String> sources = itemMapper.getSources();
        List<String> grades = itemMapper.getGrades();
        List<String> names = userMapper.getNames();
        hashMap.put("types", types);
        results.add(hashMap);
        hashMap2.put("sources", sources);
        results.add(hashMap2);
        hashMap3.put("grades", grades);
        results.add(hashMap3);
        hashMap4.put("names", names);
        results.add(hashMap4);
        return results;
    }
    public List<Item_Asr_Usr_IK_Kp> itemchoose(String type, String grade, String source, String name){
        int uid;
        if(name!=null){
            uid = userMapper.loadByUserName(name).getUid();
        } else {
            uid = -1;
        }
        List<Item> items = itemMapper.getItemsByGradeSourceTypeUid(type, grade, source, uid);
//        System.out.println(items.size());
        List<Integer> iids = new ArrayList<Integer>();
        for (Item item : items) {
            iids.add(item.getIid());
        }
        List<Item_Asr_Usr_IK_Kp> item_asr_usr_ik_kps = new ArrayList<Item_Asr_Usr_IK_Kp>();
        for (int iid: iids) {
            item_asr_usr_ik_kps.add(this.getItem_Asr_Usr_IK_KpByIid(iid));
        }
        return item_asr_usr_ik_kps;
    }
}
