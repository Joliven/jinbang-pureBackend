package com.jinbang.mapper;

import com.jinbang.model.Knowledgepoint;

import java.util.List;

public interface KnowledgePointMapper {
    int deleteKpById(int kpid);
    int updateKpById(Knowledgepoint knowledgepoint);
    int addKnowledgePoint(Knowledgepoint knowledgepoint);
    Knowledgepoint getKpById(int kpid);
    Knowledgepoint getKpByKp(String kp);
    List<Knowledgepoint> getAll();
    List<Knowledgepoint> getKpsLikeKp(String kp);
    List<Knowledgepoint> getKpsByDepth(int depth);
    int getMaxDepth();
    List<Knowledgepoint> getKpsByPreId(int preId);
    int countAll();
    int maxKpid();
    int getKpidByName(String kp);
}
